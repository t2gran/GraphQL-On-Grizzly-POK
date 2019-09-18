package no.tiger.grpahqlbeta.schema

data class FieldArgument(
    val name : String,
    val type : TypeRef,
    val description : Description?,
    val defaultValue :String? = null
) {

    fun appendToDoc(doc : TextDocBuilder) {
        doc.append(description)
        doc.appendLine(toString())
    }

    override fun toString() : String {
        return if(defaultValue == null) "$name : $type"
        else "$name : $type = $defaultValue"
    }
}