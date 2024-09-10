package com.landroute.calculator.api.core.service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.landroute.calculator.api.core.exception.RouteNotFoundException;
import com.landroute.calculator.api.core.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RoutingServiceTest {

    private Map<String, Country> cca3CountryMap;
    private RoutingService routingService;

    @BeforeEach
    void setUp() throws StreamReadException, DatabindException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Country>> typeReference = new TypeReference<>() {};
        InputStream inputStream = new ClassPathResource("countries.json")
        .getInputStream();
        var countries = objectMapper.readValue(inputStream, typeReference);
        cca3CountryMap = countries.stream()
            .collect(Collectors.toMap(Country::getCca3, Function.identity()));
        routingService = new RoutingService(cca3CountryMap);
    }

    @Test
    void findRoute_DirectNeighbors_ReturnsDirectPath() {
        List<String> route = routingService.findRoute("USA", "CAN");
        assertEquals(Arrays.asList("USA", "CAN"), route);
    }

    @Test
    void findRoute_IndirectPath_ReturnsCorrectPath() {
        List<String> route = routingService.findRoute("USA", "GTM");
        assertEquals(Arrays.asList("USA", "MEX", "GTM"), route);
    }

    @Test
    void findRoute_NoPath_ThrowsRouteNotFoundException() {
        assertThrows(RouteNotFoundException.class, () -> routingService.findRoute("USA", "GBR"));
    }

    @Test
    void findRoute_InvalidCountry_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> routingService.findRoute("USA", "XYZ"));
    }

    @Test
    void findRoute_SameCountry_ReturnsSingleCountry() {
        List<String> route = routingService.findRoute("USA", "USA");
        assertEquals(Arrays.asList("USA"), route);
    }
}
