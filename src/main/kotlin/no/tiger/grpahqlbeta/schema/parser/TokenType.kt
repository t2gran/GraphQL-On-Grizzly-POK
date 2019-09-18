package no.tiger.grpahqlbeta.schema.parser

enum class TokenType(val id : String) {
    UNRESOLVED("<UNRESOLVED>"),
    LINE_COMMENT("#"),
    TEXT("<TEXT>"),
    SCHEMA("schema"),
    TYPE("type"),
    ENUM("enum"),
    SCALAR("scalar"),
    DIRECTIVE("directive"),
    ON("on"),
    INPUT("input"),
    DEFER("@defer"),
    DEPRECATED("@deprecated"),
    IMPLEMENTS("implements"),
    INTERFACE("interface"),
    PARENTHESIS_OPEN("("),
    PARENTHESIS_CLOSE(")"),
    CURLY_OPEN("{"),
    CURLY_CLOSE("}"),
    ARRAY_OPEN("["),
    ARRAY_CLOSE("]"),
    COLUMN(":"),
    COMMA(","),
    AND("&"),
    REQ("!"),
    EQ("="),
    BOOLEAN("Boolean"),
    INT("Int"),
    FLOAT("Float"),
    STRING("String"),
    IDENTIFIER("<IDENTIFIER>"),
    NEW_LINE("\n")
    ;

    companion object {
        fun types() = setOf(BOOLEAN, INT, FLOAT, STRING)
        fun reservedWords() = setOf(
            SCHEMA, TYPE, ENUM, SCALAR, DIRECTIVE, ON, INPUT,
            DEFER, DEPRECATED, IMPLEMENTS, INTERFACE
        ).plus(types())
        fun punctuations() = setOf(
            CURLY_OPEN, CURLY_CLOSE, PARENTHESIS_OPEN, PARENTHESIS_CLOSE,
            ARRAY_OPEN, ARRAY_CLOSE,
            COLUMN, COMMA, AND, REQ, EQ, NEW_LINE
        )
    }
}