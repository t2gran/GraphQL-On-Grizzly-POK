package no.tiger.grpahqlbeta.schema.parser

class Tokens (val tokens : List<Token>, val debug : Boolean) {
    var token = tokens[0]
    var i = 0

    /* basic functions - doing one thing */

    fun next() : Tokens {
        token = tokens[i]
        if(debug) println(token.toLineString())
        ++i
        return this
    }

    fun hasNext() = i < tokens.size
    fun text() = token.text
    fun type(): TokenType = token.type
    fun typeIs(lexType: TokenType): Boolean = token.type == lexType

    fun assertArrayClose() = assertTypeIs(TokenType.ARRAY_CLOSE)
    fun assertParenthesisOpen() = assertTypeIs(TokenType.PARENTHESIS_OPEN)
    fun assertParenthesisClose() = assertTypeIs(TokenType.PARENTHESIS_CLOSE)
    fun assertCurlyOpen() = assertTypeIs(TokenType.CURLY_OPEN)
    fun assertCurlyClose() = assertTypeIs(TokenType.CURLY_CLOSE)
    fun assertColumn() = assertTypeIs(TokenType.COLUMN)
    fun assertIdentifier() = assertTypeIs(TokenType.IDENTIFIER)
    fun assertIdentifier(text : String) = assertTypeIs(TokenType.IDENTIFIER).assertTextIs(text)

    fun assertTypeIdentifier() : Tokens {
        val isType = type() in TokenType.types() || typeIs(TokenType.IDENTIFIER)
        if(!isType) error("ERROR |  expected, but got: '$token' ($i)")
        return this
    }

    fun assertTypeIs(type: TokenType): Tokens {
        if (!typeIs(type)) error("ERROR | $type expected, but got: '$token' ($i)")
        return this
    }

    fun assertTypeIs(type1 : TokenType, type2 : TokenType): Tokens {
        if (!(typeIs(type1) ||typeIs(type2))) error("ERROR | $type1 or $type2 expected, but got: '$token' ($i)")
        return this
    }

    fun assertTextIs(text: String): Tokens {
        if (text() != text) error("ERROR | $text expected, but got: '$token' ($i)")
        return this
    }


    fun skipOptListSeparators() {
        while (type() in arrayOf(TokenType.COMMA, TokenType.NEW_LINE, TokenType.AND)) next()
    }

    /** on type: do and goto next */
    fun onTypeThenNext(type: TokenType) : Boolean {
        if(!typeIs(type)) return false

        next()
        return true
    }
}