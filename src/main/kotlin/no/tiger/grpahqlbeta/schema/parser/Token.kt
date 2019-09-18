package no.tiger.grpahqlbeta.schema.parser

data class Token(val text : String, val type : TokenType) {

    fun isFinal () = type != TokenType.UNRESOLVED

    override fun toString(): String {
        if(type == TokenType.TEXT) {
            return String.format("%s", "\"" + text + "\"")
        }
        return String.format("%s   %s", text, type)
    }

    fun toLineString(): String {
        if (isFinal()) {
            if(type == TokenType.TEXT) {
                return String.format("%-80s | %s", "\"" + text.replace("\"", "\\\"") + "\"", type)
            }
            else {
                return String.format("%-80s | %s", text, type)
            }
        }
        return text
    }
}