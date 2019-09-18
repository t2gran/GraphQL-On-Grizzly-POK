package no.tiger.grpahqlbeta.schema

data class TypeRef(
    val type : String,
    val required : Boolean = false,
    val array : Boolean = false,
    val arrayRequired : Boolean = false,
    val arrayOfArrays : Boolean = false,
    val arrayOfArraysRequired : Boolean = false
) {
    override fun toString(): String {
        return pad(pad(pad(type, required), array, arrayRequired), arrayOfArrays, arrayOfArraysRequired)
    }

    private fun pad(text : String, isArray : Boolean, isReq : Boolean) : String {
        return if(isArray) pad("[" + text + "]", isReq) else text
    }
    private fun pad(text : String, isRequired : Boolean) = if(isRequired) text + "!" else text
}