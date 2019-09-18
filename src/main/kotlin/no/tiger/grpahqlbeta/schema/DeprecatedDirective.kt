package no.tiger.grpahqlbeta.schema

data class DeprecatedDirective(val reason : String) : Directive("@deprecated") {
    override fun toString() = "$name(reason : '$reason')"
}