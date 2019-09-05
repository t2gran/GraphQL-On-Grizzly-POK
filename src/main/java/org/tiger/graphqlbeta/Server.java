package org.tiger.graphqlbeta;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLHttpServlet;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.WebappContext;
import org.jetbrains.annotations.NotNull;
import org.tiger.graphqlbeta.endpoint.Query;

import javax.servlet.Servlet;

public class Server {
    public static void main(String[] args) {
        try {
            WebappContext webCtx = new WebappContext("My App");

            Servlet endpoint;


            //endpoint = new MyGraphQLHttpServlet(createSchema());

            endpoint = GraphQLHttpServlet.with(createSchema());

            webCtx.addServlet("GraphQL Endpoint", endpoint).addMapping("/graphql");

            HttpServer server = HttpServer.createSimpleServer();

            // Static Content, not used for now
            //server.getServerConfiguration().addHttpHandler(
            //        new CLStaticHttpHandler(Thread.currentThread().getContextClassLoader(), "/")
            //);

            webCtx.deploy(server);

            server.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @NotNull
    private static GraphQLSchema createSchema() {
        return SchemaParser.newParser()
                .file("schema.graphql")
                .resolvers(new Query())
                .build()
                .makeExecutableSchema();
    }
}
