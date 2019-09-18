package no.tiger.grpahqlbeta

import no.tiger.grpahqlbeta.Consts.ENTUR_FILE
import no.tiger.grpahqlbeta.Consts.HSL_FILE
import no.tiger.grpahqlbeta.Consts.OUTPUT_DIR
import no.tiger.grpahqlbeta.Consts.TRANSMODEL_FILE
import no.tiger.grpahqlbeta.schema.SchemaDocument
import no.tiger.grpahqlbeta.schema.parser.Parser
import java.io.File


object Consts {
    val OUTPUT_DIR = "/Users/thomas/code/ruter/graphqlbeta/target/betaschemas"
    val EX_FILE = "otp/ex.graphql"
    val ENTUR_FILE = "oyp/entur-gtfs-schema.graphql"
    val TRANSMODEL_FILE = "otp/transmodel-schema.graphql"
    val HSL_FILE = "otp/hsl-gtfs-schema.graphql"
}

fun main(args: Array<String>) {
    val debugParser = false


    val textEntur = resourceAsText(ENTUR_FILE)
    val textSrc = resourceAsText(HSL_FILE)
    val textTrg = resourceAsText(TRANSMODEL_FILE)

    val docEntur = Parser.lexAndParse(textEntur, debugParser)
    val docSrc = Parser.lexAndParse(textSrc, debugParser)
    val docTrg = Parser.lexAndParse(textTrg, debugParser)

    File(OUTPUT_DIR).mkdirs()

    printDoc(ENTUR_FILE, docSrc, "ENTUR")
    printDoc(HSL_FILE, docSrc, "HSL")
    printDoc(TRANSMODEL_FILE, docTrg, "NETEX")
}

fun printDoc(name : String, doc : SchemaDocument, dest : String? = null) {
    val out : Appendable = if(dest == null) System.out else File(OUTPUT_DIR, dest).writer()
    println("------------------------------------- [ Start: $name ]")
    doc.print(out)
    println("--------------------------------------- [ End: $name ]")
}

fun resourceAsText(name : String) : String {
    return Thread.currentThread().contextClassLoader.getResource(name)!!.readText()
}

