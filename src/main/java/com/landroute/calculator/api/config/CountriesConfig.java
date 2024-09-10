package com.landroute.calculator.api.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.landroute.calculator.api.core.model.Country;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class CountriesConfig {

    @Bean
    public Map<String, Country> cca3CountryMap() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Country>> typeReference = new TypeReference<>() {};
        InputStream inputStream = new ClassPathResource("countries.json")
        .getInputStream();
        var countries = objectMapper.readValue(inputStream, typeReference);
        return countries.stream()
            .collect(Collectors.toMap(Country::getCca3, Function.identity()));
    }
}
