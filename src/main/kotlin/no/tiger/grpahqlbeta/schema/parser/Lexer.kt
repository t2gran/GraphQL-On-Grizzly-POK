package no.tiger.grpahqlbeta.schema.parser


class Lexer {
    val IDENTIFIER_REGEX = Regex("[-+.\\w]+")

    val newTokens = mutableListOf<Token>()
    val tokens = mutableListOf<Token>()

    fun parse(text : String) {
        parseLineComments(text)
        parseUnresolvedTokens(this::parseDocTripleQuote)
        parseUnresolvedTokens(this::parseDocSingleQuote)
        TokenType.punctuations().forEach { type ->
            parseUnresolvedTokens { parseOpIdentifier(it, type) }
        }
        parseUnresolvedTokens {
            it.split(Regex("\\s+")).forEach {
                parseWordIdentifier(it.trim())
            }
        }
    }

    private fun parseLineComments(text : String) {
        var current  = ""
        for (line in text.lines()) {
            if(line.trim().startsWith("#")) {
                addUnresolvedToken(current)
                current = ""
                addNewToken(line, TokenType.LINE_COMMENT)
            }
            else {
                current += line + "\n"
            }
        }
        addUnresolvedToken(current)
        swapTokenLists()
    }

    private tailrec fun parseDocTripleQuote(text : String) {
        val pos0 = text.indexOf("\"\"\"")
        if(pos0 < 0) {
            addUnresolvedToken(text)
            return
        }
        val pos1 = text.indexOf("\"\"\"", pos0 + 3)

        if(pos1 == -1) error("Unable to find end of doc in: '$text'")

        addUnresolvedToken(text.substring(0, pos0))
        addNewToken(text.substring(pos0+3, pos1), TokenType.TEXT)
        val sufix = text.substring(pos1 + 3)
        parseDocTripleQuote(sufix)
    }

    private tailrec fun parseDocSingleQuote(text : String) {
        val pos0 = text.indexOf("\"")
        if(pos0 < 0) {
            addUnresolvedToken(text)
            return
        }
        val pos1 = text.indexOf("\"", pos0 + 1)

        if(pos1 == -1) error("Unable to find end of doc in: '$text'")

        addUnresolvedToken(text.substring(0, pos0))
        addNewToken(text.substring(pos0+1, pos1), TokenType.TEXT)
        parseDocSingleQuote(text.substring(pos1 + 1))
    }

    private tailrec fun parseOpIdentifier(text : String, type : TokenType) {
        val pos0 = text.indexOf(type.id)

        if(pos0 < 0) {
            addUnresolvedToken(text)
            return
        }

        val pos1 = pos0 + type.id.length
        addUnresolvedToken(text.substring(0, pos0))
        addNewToken(text.substring(pos0, pos1), type)
        parseOpIdentifier(text.substring(pos1), type)
    }

    private fun parseWordIdentifier(token : String) {
        for (lexType in TokenType.reservedWords()) {
            if(token == lexType.id) {
                addNewToken(token, lexType)
                return
            }
        }
        mapToIdentifier(token)
    }

    private fun mapToIdentifier(id : String) {
        if(!id.matches(IDENTIFIER_REGEX)) {
            error("Text is not parsed properly: '$id'")
        }
        addToken(Token(id, TokenType.IDENTIFIER))
    }

    private fun parseUnresolvedTokens(parseFunction: (String)-> Any) {
        for (token in this.tokens) {
            if(token.isFinal()) addToken(token)
            else parseFunction.invoke(token.text)
        }
        swapTokenLists()
    }

    private fun addUnresolvedToken(value : String) {
        if(value.isNotBlank()) {
            addNewToken(value, TokenType.UNRESOLVED)
        }
    }
    private fun addNewToken(value : String, type : TokenType) {
        addToken(Token(value.trim(), type))
    }
    private fun addToken(token : Token) {
        newTokens.add(token)
    }

    private fun swapTokenLists() {
        tokens.clear()
        tokens.addAll(newTokens)
        newTokens.clear()
    }

    override fun toString(): String {
        return tokens.map { it.toLineString() }.joinToString("\n")
    }
}
