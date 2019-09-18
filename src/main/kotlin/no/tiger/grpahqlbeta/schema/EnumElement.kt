package no.tiger.grpahqlbeta.schema

data class EnumElement(
    val name : String,
    val description : Description?
){
    fun appendToDoc(doc : TextDocBuilder) = doc.append(description).appendLine(name)
}
