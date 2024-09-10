package com.landroute.calculator.api.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.landroute.calculator.api.core.service.RoutingService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RoutingController {

    private final RoutingService routingService;

    @GetMapping("/routing/{origin}/{destination}")
    public List<String> getRoute(@PathVariable String origin, @PathVariable String destination) {
        return routingService.findRoute(origin, destination);
    }
}
