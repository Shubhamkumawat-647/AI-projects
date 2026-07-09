package com.aiagent.enterprise_ai_agent.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.aiagent.enterprise_ai_agent.config.DTO.Location;
import com.aiagent.enterprise_ai_agent.config.DTO.Current;
import com.aiagent.enterprise_ai_agent.config.DTO.GeoResponse;
import com.aiagent.enterprise_ai_agent.config.DTO.WeatherResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherTool {

    private final RestClient restClient;

    @Tool(description = "Get current weather information for any city.")
    public String getWeather(
            @ToolParam(description = "City name")
            String city) {

        GeoResponse geoResponse = restClient.get()
                .uri("https://geocoding-api.open-meteo.com/v1/search?name="
                        + city
                        + "&count=1")
                .retrieve()
                .body(GeoResponse.class);

        if (geoResponse == null
                || geoResponse.getResults() == null
                || geoResponse.getResults().isEmpty()) {

            return "City not found.";
        }

        Location location = geoResponse.getResults().get(0);

        WeatherResponse weather = restClient.get()
                .uri("https://api.open-meteo.com/v1/forecast"
                        + "?latitude=" + location.getLatitude()
                        + "&longitude=" + location.getLongitude()
                        + "&current=temperature_2m,relative_humidity_2m")
                .retrieve()
                .body(WeatherResponse.class);

        if (weather == null || weather.getCurrent() == null) {
            return "Weather data unavailable.";
        }

        Current current = weather.getCurrent();

        return """
                City : %s

                Temperature : %.1f °C

                Humidity : %d %%
                """.formatted(
                location.getName(),
                current.getTemperature(),
                current.getHumidity());

    }

}