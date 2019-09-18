package no.tiger.grpahqlbeta.schema.parser

import no.tiger.grpahqlbeta.schema.Defer
import no.tiger.grpahqlbeta.schema.DeprecatedDirective
import no.tiger.grpahqlbeta.schema.Description
import no.tiger.grpahqlbeta.schema.Directive
import no.tiger.grpahqlbeta.schema.EnumElement
import no.tiger.grpahqlbeta.schema.EnumType
import no.tiger.grpahqlbeta.schema.Field
import no.tiger.grpahqlbeta.schema.FieldArgument
import no.tiger.grpahqlbeta.schema.InputDef
import no.tiger.grpahqlbeta.schema.InterfaceDef
import no.tiger.grpahqlbeta.schema.Scalar
import no.tiger.grpahqlbeta.schema.Schema
import no.tiger.grpahqlbeta.schema.SchemaDocument
import no.tiger.grpahqlbeta.schema.TypeDef
import no.tiger.grpahqlbeta.schema.TypeRef

class Parser(lexTokens : List<Token>, debug : Boolean = false) {

    val scalars = mutableListOf<Scalar>()
    val enums = mutableListOf<EnumType>()
    val interfaces = mutableListOf<InterfaceDef>()
    val outputTypes = mutableListOf<TypeDef>()
    val inputTypes = mutableListOf<InputDef>()
    var schema : Schema? = null

    val tokens = Tokens(lexTokens, debug)

    companion object {
        fun lexAndParse(text : String, debug : Boolean = false): SchemaDocument {
            val lexer = Lexer()
            lexer.parse(text)
            return Parser(lexer.tokens, debug).parse()
        }
    }

    fun parse() : SchemaDocument {
        var descrRead : Description? = null
        var descrInput : Description? = null

        while (tokens.hasNext()) {
            tokens.next()
            when(tokens.type()) {
                TokenType.SCHEMA        -> parseSchema()
                TokenType.TEXT          -> descrRead = Description(tokens.text())
                TokenType.SCALAR        -> parseScalar(descrInput)
                TokenType.DIRECTIVE     -> parseDirective(descrInput)
                TokenType.ENUM          -> parseEnum(descrInput)
                TokenType.INTERFACE     -> parseInterface(descrInput)
                TokenType.TYPE          -> parseType(descrInput)
                TokenType.INPUT         -> parseInput(descrInput)
                TokenType.LINE_COMMENT  -> ignore()
                TokenType.NEW_LINE      -> ignore()
                else                    -> System.err.println(
                    "WARN | Parsing token ignored: " + tokens.type()
                )
            }
            // Make sure the description only is available in the following iteration
            descrInput = descrRead
            descrRead = null
        }
        if(schema == null) error("No schema found in input document")

        scalars.sortBy { it.name }
        enums.sortBy { it.name }
        interfaces.sortBy { it.name }
        inputTypes.sortBy { it.name }
        outputTypes.sortBy { it.name }

        return SchemaDocument(schema!!, scalars, enums, interfaces, outputTypes, inputTypes)
    }

    private fun ignore() {}

    private fun parseOptDescriptionAndGoNext() : Description? {
        if(!tokens.typeIs(TokenType.TEXT)) return null;
        val d = Description(tokens.text())
        tokens.next()
        return d
    }

    private fun parseSchema() {
        tokens.assertTypeIs(TokenType.SCHEMA)
        tokens.next().assertCurlyOpen()
        val queryId = tokens.next().assertIdentifier().text()
        tokens.next().assertColumn().next()
        val queryType = parseTypeRefThenNext()
        tokens.assertCurlyClose()

        schema = Schema(queryId, queryType)
    }

    private fun parseScalar(description : Description?) {
        tokens.assertTypeIs(TokenType.SCALAR)
        val name = tokens.next().assertIdentifier().text()
        scalars.add(Scalar(name, description))
    }

    private fun parseDirective(description : Description?) {
        tokens.assertTypeIs(TokenType.DIRECTIVE)
        // We ignore directives, only support @defer and @deprecated(build in)
        tokens.next().assertTypeIs(TokenType.DEFER)
        tokens.next().assertTypeIs(TokenType.ON)
        tokens.next().assertIdentifier()
    }

    private fun parseEnum(description : Description?) {
        tokens.assertTypeIs(TokenType.ENUM)
        // We ignore directives
        val name = tokens.next().assertIdentifier().text()
        val elements = mutableListOf<EnumElement>()

        tokens.next().assertCurlyOpen().next()

        do {
            elements.add(parseEnumElementThenNext())
        }
        while (!tokens.typeIs(TokenType.CURLY_CLOSE))

        enums.add(EnumType(name, description, elements))
    }

    private fun parseEnumElementThenNext(): EnumElement {
        val elementDescription = parseOptDescriptionAndGoNext()
        val elementName = tokens.assertIdentifier().text()
        tokens.next().skipOptListSeparators()

        return EnumElement(elementName, elementDescription)
    }

    private fun parseInterface(description : Description?) {
        tokens.assertTypeIs(TokenType.INTERFACE)
        val name = tokens.next().assertIdentifier().text()
        tokens.next()

        val fields = parseFields()

        interfaces.add(InterfaceDef(name, description, fields))
        tokens.assertCurlyClose()
    }

    private fun parseType(description : Description?) {
        tokens.assertTypeIs(TokenType.TYPE)
        val name = tokens.next().assertIdentifier().text()
        tokens.next()
        val implements = parseImplementsListThenNext()
        val fields = parseFields()
        outputTypes.add(TypeDef(name, description, implements, fields))
        tokens.assertCurlyClose()
    }

    private fun parseInput(description : Description?) {
        tokens.assertTypeIs(TokenType.INPUT)
        val name = tokens.next().assertIdentifier().text()
        tokens.next()
        val fields = parseFields()
        inputTypes.add(InputDef(name, description, fields))
        tokens.assertCurlyClose()
    }

    private fun parseImplementsListThenNext() : List<TypeRef> {
        if(!tokens.typeIs(TokenType.IMPLEMENTS)) return emptyList()

        val implements = mutableListOf<TypeRef>()
        tokens.next()

        do {
            tokens.assertIdentifier()
            implements.add(TypeRef(tokens.text()))
            tokens.next().skipOptListSeparators()
        }
        while (tokens.typeIs(TokenType.IDENTIFIER))

        return implements
    }

    private fun parseFields() : List<Field> {
        tokens.assertCurlyOpen().next()
        val fields = mutableListOf<Field>()

        do {
            fields.add(parseFieldThenNext())
        }
        while (!tokens.typeIs(TokenType.CURLY_CLOSE))
        return fields
    }

    /** parse <Description> fieldName <Args>? : <TypeRef> <Directives>? */
    private fun parseFieldThenNext() : Field {
        val description = parseOptDescriptionAndGoNext()
        // Using "type" as a field name is allowed
        val name  = tokens.assertTypeIs(TokenType.IDENTIFIER, TokenType.TYPE).text()
        tokens.next()
        val fieldArguments = parseFieldArgumentsThenNext()
        tokens.assertColumn().next()
        val type = parseTypeRefThenNext()
        val defaultValue = parseDefaultValueThenNext()
        val directives = parseDirectivesThenNext()

        tokens.skipOptListSeparators()

        return Field(name, type, fieldArguments, defaultValue, directives, description)
    }

    private fun parseFieldArgumentsThenNext(): List<FieldArgument> {
        if(!tokens.typeIs(TokenType.PARENTHESIS_OPEN)) return emptyList()
        val fieldArguments = mutableListOf<FieldArgument>()
        tokens.next()

        do {
            fieldArguments.add(parseFieldArgumentThenNext())
            tokens.skipOptListSeparators()
        }
        while (!tokens.typeIs(TokenType.PARENTHESIS_CLOSE))

        tokens.next()
        return fieldArguments
    }

    private fun parseFieldArgumentThenNext(): FieldArgument {
        val description = parseOptDescriptionAndGoNext()
        val name = tokens.assertIdentifier().text()
        tokens.next().assertColumn().next()
        val type = parseTypeRefThenNext()
        val defaultValue = parseDefaultValueThenNext()
        return FieldArgument(name, type, description, defaultValue)
    }

    private fun parseTypeRefThenNext() : TypeRef {
        val isArray = tokens.onTypeThenNext(TokenType.ARRAY_OPEN)
        val isArrayOfArrays = tokens.onTypeThenNext(TokenType.ARRAY_OPEN)

        val name = tokens.assertTypeIdentifier().text()
        tokens.next()

        val valueRequired = tokens.onTypeThenNext(TokenType.REQ)

        if(!isArray) return TypeRef(name, valueRequired)

        tokens.assertArrayClose().next()
        val arrayReq = tokens.onTypeThenNext(TokenType.REQ)

        if(!isArrayOfArrays) return TypeRef(name, valueRequired, true, arrayReq)

        tokens.assertArrayClose().next()
        val arrayOfArraysReq = tokens.onTypeThenNext(TokenType.REQ)

        return TypeRef(name, valueRequired, true, arrayReq, true, arrayOfArraysReq)
    }

    private fun parseDefaultValueThenNext() : String? {
        if(!tokens.typeIs(TokenType.EQ)) return null

        val defaultValue : String?
        tokens.next()

        if(tokens.typeIs(TokenType.ARRAY_OPEN)) {
            val elements = mutableListOf<String>()
            tokens.next()
            while (!tokens.typeIs(TokenType.ARRAY_CLOSE)) {
                elements.add(tokens.assertIdentifier().text())
                tokens.next().skipOptListSeparators()
            }
            elements.sort()
            defaultValue = elements.toString()
        }
        else {
            defaultValue = tokens.assertIdentifier().text()
        }
        tokens.next()
        return defaultValue
    }

    private fun parseDirectivesThenNext() : List<Directive> {
        val directives = mutableListOf<Directive>()

        do {
            if(tokens.typeIs(TokenType.DEFER)) {
                directives.add(Defer())
            }
            else if(tokens.typeIs(TokenType.DEPRECATED)) {
                tokens.next().assertParenthesisOpen().next().assertIdentifier("reason").next().assertColumn()
                val reason = tokens.next().assertTypeIs(TokenType.TEXT).text()
                tokens.next().assertParenthesisClose()
                directives.add(DeprecatedDirective(reason))
            }
            else break
            tokens.next()
        }
        while (true)

        return directives
    }

    override fun toString(): String {
        return "Parser(schema=$schema, types=\n" +
               "\t${outputTypes.joinToString("\n\t")})"
    }
}