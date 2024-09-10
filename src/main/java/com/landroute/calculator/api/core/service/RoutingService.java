package com.landroute.calculator.api.core.service;

import com.landroute.calculator.api.core.exception.RouteNotFoundException;
import com.landroute.calculator.api.core.model.Country;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoutingService {
	private final Map<String, Country> cca3CountryMap;

	public List<String> findRoute(String origin, String destination) {
		Country start = cca3CountryMap.get(origin);
		Country end = cca3CountryMap.get(destination);

		if (start == null || end == null) {
			throw new IllegalArgumentException("Invalid origin or destination country code");
		}

		Queue<String> queue = new LinkedList<>();
		Map<String, String> parentMap = new HashMap<>();
		Set<String> visited = new HashSet<>();

		queue.offer(start.getCca3());
		visited.add(start.getCca3());

		while (!queue.isEmpty()) {
			String current = queue.poll();

			if (current.equals(end.getCca3())) {
				return reconstructPath(parentMap, start.getCca3(), end.getCca3());
			}

			Country currentCountry = cca3CountryMap.get(current);
			for (String neighbor : currentCountry.getBorders()) {
				if (!visited.contains(neighbor)) {
					queue.offer(neighbor);
					visited.add(neighbor);
					parentMap.put(neighbor, current);
				}
			}
		}

		throw new RouteNotFoundException("No route found between " + origin + " and " + destination);
	}

	private List<String> reconstructPath(Map<String, String> parentMap, String start, String end) {
		List<String> path = new ArrayList<>();
		String current = end;

		while (!current.equals(start)) {
			path.add(current);
			current = parentMap.get(current);
		}
		path.add(start);

		Collections.reverse(path);
		return path;
	}
}
