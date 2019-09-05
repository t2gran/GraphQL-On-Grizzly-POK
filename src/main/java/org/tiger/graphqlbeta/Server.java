package org.tiger.graphqlbeta;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.servlet.GraphQLHttpServlet;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.WebappContext;
import org.tiger.graphqlbeta.api.ApiWiring;
import org.tiger.graphqlbeta.db.Repository;

import javax.servlet.Servlet;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Server {
    public static void main(String[] args) {
        try {
            WebappContext webCtx = createContext();

            HttpServer server = HttpServer.createSimpleServer();

            webCtx.deploy(server);

            server.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static WebappContext createContext() {
        WebappContext webCtx = new WebappContext("My App");

        Servlet endpoint = GraphQLHttpServlet.with(createSchema(false));
        webCtx.addServlet("GraphQL Endpoint", endpoint).addMapping("/graphql");


        Servlet endpointNo = GraphQLHttpServlet.with(createSchema(true));
        webCtx.addServlet("Norsk GraphQL Endepunkt", endpointNo).addMapping("/graphql-no");

        return webCtx;
    }

    private static GraphQLSchema createSchema(boolean useNo) {
        final String SCHEMA_NAME = (useNo ? "schema-no" : "schema") + ".graphql";

        SchemaParser parser =  new SchemaParser();
        SchemaGenerator schemaGenerator = new SchemaGenerator();

        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(SCHEMA_NAME);
        TypeDefinitionRegistry typeRegistry = parser.parse(new InputStreamReader(input));
        ApiWiring wiring = new ApiWiring(useNo, new Repository());

        return schemaGenerator.makeExecutableSchema(typeRegistry,  wiring.buildWiring());
    }
}
