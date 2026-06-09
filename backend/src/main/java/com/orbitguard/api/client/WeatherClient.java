package com.orbitguard.api.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.orbitguard.api.dto.WeatherDataResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * Cliente responsável por buscar dados climáticos de uma fonte externa.
 */
@Component
public class WeatherClient {

    private final RestTemplate restTemplate;

    public WeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Busca informações climáticas com base em coordenadas geográficas.
     *
     * @param latitude  latitude geográfica do local
     * @param longitude longitude geográfica do local
     * @return um {@link WeatherDataResponse} com dados de temperatura,
     *         velocidade do vento, probabilidade de chuva e condição descritiva
     */
    public WeatherDataResponse getWeather(Double latitude, Double longitude) {
        Objects.requireNonNull(latitude, "latitude must not be null");
        Objects.requireNonNull(longitude, "longitude must not be null");

        URI uri = UriComponentsBuilder
            .fromUriString("https://api.open-meteo.com/v1/forecast")
            .queryParam("latitude", latitude)
            .queryParam("longitude", longitude)
            .queryParam("current", "temperature_2m,wind_speed_10m,weather_code")
            .queryParam("hourly", "precipitation_probability")
            .queryParam("forecast_days", 1)
            .queryParam("timezone", "auto")
            .build()
            .toUri();

        OpenMeteoResponse response = restTemplate.getForObject(uri, OpenMeteoResponse.class);
        if (response == null || response.current() == null) {
            throw new IllegalStateException("Weather API returned no current weather data");
        }

        OpenMeteoCurrent current = response.current();
        return new WeatherDataResponse(
            current.temperature(),
            current.windSpeed(),
            currentRainProbability(response),
            weatherCondition(current.weatherCode())
        );
    }

    private Double currentRainProbability(OpenMeteoResponse response) {
        if (response.current() == null || response.hourly() == null) {
            return null;
        }

        List<String> times = response.hourly().times();
        List<Double> probabilities = response.hourly().precipitationProbability();
        if (times == null || probabilities == null || probabilities.isEmpty()) {
            return null;
        }

        int currentIndex = times.indexOf(response.current().time());
        if (currentIndex >= 0 && currentIndex < probabilities.size()) {
            return probabilities.get(currentIndex);
        }

        return probabilities.get(0);
    }

    private String weatherCondition(Integer weatherCode) {
        if (weatherCode == null) {
            return "Unknown";
        }

        return switch (weatherCode) {
            case 0 -> "Clear";
            case 1, 2, 3 -> "Cloudy";
            case 45, 48 -> "Fog";
            case 51, 53, 55, 56, 57 -> "Drizzle";
            case 61, 63, 65, 66, 67, 80, 81, 82 -> "Rain";
            case 71, 73, 75, 77, 85, 86 -> "Snow";
            case 95, 96, 99 -> "Thunderstorm";
            default -> "Unknown";
        };
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record OpenMeteoResponse(
        OpenMeteoCurrent current,
        OpenMeteoHourly hourly
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record OpenMeteoCurrent(
        String time,
        @JsonProperty("temperature_2m") Double temperature,
        @JsonProperty("wind_speed_10m") Double windSpeed,
        @JsonProperty("weather_code") Integer weatherCode
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record OpenMeteoHourly(
        @JsonProperty("time") List<String> times,
        @JsonProperty("precipitation_probability") List<Double> precipitationProbability
    ) {}
}
