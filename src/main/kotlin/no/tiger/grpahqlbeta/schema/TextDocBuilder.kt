package no.tiger.grpahqlbeta.schema

class TextDocBuilder(val buf : Appendable) {
    private var level : Int = 0
    private var firstIndentedLine = false

    fun indent(body : (TextDocBuilder) -> Any) {
        ++level
        firstIndentedLine = true
        body.invoke(this)
        --level
    }

    fun append(description : Description?): TextDocBuilder {
        if(description != null) {
            if(!firstIndentedLine && level > 0) newLine()
            appendLine(description.toString())
        }
        return this
    }

    fun appendLine(text : String): TextDocBuilder {
        indent()
        buf.append(
            when(level) {
                0    -> text
                1    -> text.replace("\n", "\n   ")
                else -> text.replace("\n", "\n      ")
            }
        )
        newLine()
        firstIndentedLine = false
        return this
    }

    fun newLine() : TextDocBuilder {
        buf.append('\n')
        return this
    }

    private fun indent() {
        when(level) {
            0 -> {}
            1 -> buf.append("   ")
            2 -> buf.append("      ")
            else -> buf.append("         ")
        }
    }
}