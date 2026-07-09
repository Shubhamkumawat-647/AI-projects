package com.aiagent.enterprise_ai_agent.config.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Current {

    @JsonProperty("temperature_2m")
    private double temperature;

    @JsonProperty("relative_humidity_2m")
    private int humidity;

}