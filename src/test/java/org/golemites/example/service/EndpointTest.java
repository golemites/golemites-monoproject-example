package org.golemites.example.service;

import org.assertj.core.api.Assertions;
import org.golemites.testsupport.GolemitesExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * No fuuzz JUnit5 extension for testing Golemites Apps.
 */
@ExtendWith(GolemitesExtension.class)
class EndpointTest {

    /**
     * Note that this service is only visible when the package of {@link RESTEndpoint}
     * is actually exported in package-info.java.
     */
    @Test
    void testServiceIsAvailable(RESTEndpoint endpoint) {
        Assertions.assertThat(endpoint.sayHello()).isEqualTo("{\"message\" : \"Mono1\"}");
    }

    /**
     * For this test, no export is required because
     * the service is being accessed as a web-resource (http).
     */
    @Test
    void testWebEndpointIsAvailable() {
        get("/foo").then()
                .statusCode(200)
                .body("message", equalTo("Mono1"));
    }

}
