package org.objectstyle.graphql.cayenne.it;

import org.junit.AfterClass;

import org.objectstyle.graphql.cayenne.orm.DefaultSchemaTranslator;
import org.objectstyle.graphql.cayenne.orm.SchemaTranslator;

import graphql.GraphQL;
import org.objectstyle.graphql.test.TestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GraphQLTestFactory {
    private static GraphQL graphQL;
    private static TestFactory testFactory = new TestFactory();
    private static final Logger LOGGER = LoggerFactory.getLogger(GraphQLTestFactory.class);

    GraphQLTestFactory() {
        graphQL = createGraphQL(createSchemaTranslator());
    }

    @AfterClass
    public static void tearDownClass() {
        testFactory.stopServerRuntime();
    }

    private static GraphQL createGraphQL(SchemaTranslator translator) {
        return new GraphQL(translator.toGraphQL());
    }

    private static SchemaTranslator createSchemaTranslator() {
        return new DefaultSchemaTranslator(testFactory.getObjectContext());
    }

    String post_graphql_request(String request) {
        LOGGER.info(request);
        Object r = graphQL.execute("query " + request).getData();
        return r == null ? "" : r.toString();
    }
}
