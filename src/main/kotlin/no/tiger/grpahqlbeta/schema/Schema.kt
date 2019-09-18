package no.tiger.grpahqlbeta.schema

data class Schema(val queryId : String, val queryType : TypeRef) {
    fun appendToDoc(doc : TextDocBuilder) {
        doc.appendLine("schema {")
        doc.indent {
            doc.appendLine("$queryId : $queryType")
        }
        doc.appendLine("}")
        doc.newLine()
    }
}