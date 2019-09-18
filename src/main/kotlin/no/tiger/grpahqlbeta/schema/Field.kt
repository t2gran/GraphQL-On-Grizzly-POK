package no.tiger.grpahqlbeta.schema

open class Field(
    val name : String,
    val type : TypeRef,
    val arguments : List<FieldArgument>,
    val defaultValue : String? = null,
    val directives : List<Directive>,
    val description : Description?
) {
    fun appendToDoc(doc : TextDocBuilder) {
        doc.append(description)
        if(arguments.isEmpty()) {
            doc.appendLine("$name : $type")
        }
        else if(arguments.size < 4 && arguments.all { it.description == null }) {
            doc.appendLine("$name(${arguments.joinToString()}) : ${type_defaultValue_and_directive()}")
        }
        else {
            doc.appendLine("$name(")
            doc.indent {
                arguments.forEach { it.appendToDoc(doc) }
            }
            doc.appendLine(") : ${type_defaultValue_and_directive()}")
        }
    }

    private fun type_defaultValue_and_directive(): String {
        val str = StringBuilder("$type")
        if(defaultValue != null) str.append(defaultValue)
        if(directives.isNotEmpty()) directives.joinTo(str, " ")
        return str.toString()
    }
}