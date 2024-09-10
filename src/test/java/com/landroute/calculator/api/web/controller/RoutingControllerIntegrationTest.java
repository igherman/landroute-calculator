package com.landroute.calculator.api.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoutingControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testFindRoute_ValidCountries_ReturnsRoute() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                createURLWithPort("/routing/CZE/ITA"),
                List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0);
        assertEquals("CZE", response.getBody().get(0));
        assertEquals("ITA", response.getBody().get(response.getBody().size() - 1));
    }

    @Test
    public void testFindRoute_InvalidCountry_ReturnsBadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                createURLWithPort("/routing/CZE/XYZ"),
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindRoute_NoRoute_ReturnsNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                createURLWithPort("/routing/USA/NZL"),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
