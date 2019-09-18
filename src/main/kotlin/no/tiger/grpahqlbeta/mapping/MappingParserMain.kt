package no.tiger.grpahqlbeta.mapping

import java.io.File


/**
 * Use this to visually test the parsing of the mapping file
 */
fun main(args: Array<String>) {
    val PATH = "/Users/thomas/code/ruter/graphqlbeta/src/main/resources"
    val MAPPING_FILE = "gtfs-transmodel-mapping.txt"
    val mapping = TypeMapping()

    val text = File(PATH, MAPPING_FILE).readText()

    mapping.parse(text)
    mapping.print(System.out)
}

