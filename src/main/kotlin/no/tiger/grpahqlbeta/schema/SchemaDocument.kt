package no.tiger.grpahqlbeta.schema

data class SchemaDocument (
    var schema : Schema,
    val scalars : List<Scalar>,
    val enums : List<EnumType>,
    val interfaces : List<InterfaceDef>,
    val outputTypes : List<TypeDef>,
    val inputTypes : List<InputDef>
) {

    fun print(buf : Appendable): Appendable {
        val doc = TextDocBuilder(buf)
        schema.appendToDoc(doc)
        scalars.forEach { it.appendToDoc(doc) }
        enums.forEach { it.appendToDoc(doc) }
        interfaces.forEach { it.appendToDoc(doc) }
        outputTypes.forEach { it.appendToDoc(doc) }
        inputTypes.forEach { it.appendToDoc(doc) }
        return buf
    }

    override fun toString(): String {
        return print(StringBuilder()).toString()
    }
}
