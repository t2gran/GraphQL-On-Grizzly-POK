package no.tiger.grpahqlbeta.schema

data class Description(val text : String) {
    val multiline = text.contains("\n")
    val useTripleQuote = text.contains("\"")

    override fun toString() : String {
        return if(multiline) "\"\"\"\n$text\n\"\"\""
            else if(useTripleQuote) "\"\"\"$text\"\"\""
            else "\"$text\""
    }
}