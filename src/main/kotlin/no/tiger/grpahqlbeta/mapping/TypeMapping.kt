package no.tiger.grpahqlbeta.mapping

class TypeMapping {
    private val REQUEST = "request"
    private val TYPE = "type"
    private val NA = "na"

    val requests = mutableListOf<TwoWayMapping>()
    val types = mutableListOf<TwoWayMapping>()

    fun parse(text : String) {
        var current: TwoWayMapping = TwoWayMapping(NA, NA, NA)

        for (line in text.lines()) {
            if (line.trim().isBlank()) continue

            val tokens = line.split(Regex("[\\s]+"))
            val firstToken = tokens[0]

            if (firstToken == REQUEST) {
                current = newMapping(
                    REQUEST,
                    requests,
                    tokens
                    )
            }
            else if (firstToken == TYPE) {
                current = newMapping(
                    TYPE,
                    types,
                    tokens
                    )
            }
            else {
                val t = tokens.filter { it.isNotEmpty() }
                if (t.size != 2) error("Two arguments expected at: $tokens")
                val a = t[0]
                val b = t[1]
                if (na(a) || na(b)) continue
                current.add(a, b)
            }
        }

        requests.removeIf { na(it) }
        types.removeIf { na(it) }

    }

    fun print(out : Appendable) {
        requests.filter { it.isEmpty() }.forEach { it.printHeader(out) }
        requests.filter { it.isNotEmpty() }.forEach { it.print(out) }
        out.append('\n')
        types.filter { it.isEmpty() }.forEach { it.printHeader(out) }
        types.filter { it.isNotEmpty() }.forEach { it.print(out) }
    }

    private fun newMapping(mapping: String, mappings: MutableList<TwoWayMapping>, tokens: List<String>):
            TwoWayMapping {
        if (tokens.size != 3) error("Expected 3 tokens: $tokens")
        val gtfsType = tokens[1]
        val netexType = tokens[2]


        val m = TwoWayMapping(mapping, gtfsType, netexType)
        mappings.add(m)
        return m
    }

    private fun na(a: String) = NA == a.toLowerCase()

    private fun na(m: TwoWayMapping) = (na(m.netexName) || na(m.gtfsName)) && m.left.isEmpty()
}