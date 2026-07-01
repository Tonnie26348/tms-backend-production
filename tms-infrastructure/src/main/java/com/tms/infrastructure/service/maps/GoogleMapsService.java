package com.tms.infrastructure.service.maps;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Double getDistance(String origin, String destination) {
        // TODO: Implement Google Directions API call
        // 1. Construct URL with origin, destination, and apiKey
        // 2. Call Google API
        // 3. Parse JSON response to extract distance
        System.out.println("Calculating distance between " + origin + " and " + destination);
        return 100.0; // Placeholder
    }
}
