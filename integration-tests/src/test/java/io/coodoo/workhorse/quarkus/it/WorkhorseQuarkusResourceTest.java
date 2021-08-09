package io.coodoo.workhorse.quarkus.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class WorkhorseQuarkusResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/workhorse-quarkus")
                .then()
                .statusCode(200)
                .body(is("Hello workhorse-quarkus"));
    }
}
