package no.tiger.grpahqlbeta.schema

data class TypeDef(
    val name : String,
    val description : Description?,
    val implements : List<TypeRef>,
    val fields : List<Field>
) {
    fun appendToDoc(doc : TextDocBuilder) {
        doc.append(description)
        if(implements.isEmpty()) {
            doc.appendLine("type $name {")
        }
        else {
            doc.appendLine("type $name implements ${implements.joinToString(" & ")} {")
        }
        doc.indent {
            fields.forEach { it.appendToDoc(doc) }
        }
        doc.appendLine("}")
        doc.newLine()
    }
}