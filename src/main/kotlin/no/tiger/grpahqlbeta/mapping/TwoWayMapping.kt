package no.tiger.grpahqlbeta.mapping

class TwoWayMapping(val type: String, val gtfsName: String, val netexName: String) {
    val left = mutableMapOf<String, String>()
    val right = mutableMapOf<String, String>()

    fun add(rhs: String, lhs: String) {
        right.put(rhs, lhs)
        left.put(lhs, rhs)
    }

    fun printHeader(out : Appendable) {
        out.append("$type $gtfsName $netexName\n")
    }

    fun print(out : Appendable) {
        out.append("\n")
        printHeader(out)
        for (e in left.entries) {
            out.append("  %-30s  %s%n".format(e.key, e.value))
        }
    }

    fun isEmpty() : Boolean = left.isEmpty()
    fun isNotEmpty() : Boolean = left.isNotEmpty()
}