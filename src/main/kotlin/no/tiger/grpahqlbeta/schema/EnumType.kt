package no.tiger.grpahqlbeta.schema

data class EnumType(
    val name : String,
    val description : Description?,
    val elements : List<EnumElement>
) {
    fun appendToDoc(doc : TextDocBuilder) {
        doc.append(description).appendLine("enum $name {")
        doc.indent{
            elements.forEach { it.appendToDoc(doc) }
        }
        doc.appendLine("}")
        doc.newLine()
    }
}
