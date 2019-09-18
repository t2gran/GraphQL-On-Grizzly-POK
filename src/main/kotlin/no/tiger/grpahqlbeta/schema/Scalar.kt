package no.tiger.grpahqlbeta.schema

data class Scalar(val name : String, val description : Description?) {
    fun appendToDoc(doc : TextDocBuilder) {
        doc.append(description)
        doc.appendLine("scalar $name")
        doc.newLine()
    }
}
