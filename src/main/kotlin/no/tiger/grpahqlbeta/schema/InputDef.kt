package no.tiger.grpahqlbeta.schema

data class InputDef(
    val name : String,
    val description : Description?,
    val fields : List<Field>
) {
    fun appendToDoc(doc : TextDocBuilder) {
        doc.append(description)
        doc.appendLine("input $name {")
        doc.indent {
            fields.forEach { it.appendToDoc(doc) }
        }
        doc.appendLine("}")
        doc.newLine()
    }
}