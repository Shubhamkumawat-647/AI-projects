package com.aiagent.enterprise_ai_agent.config.DTO;

import java.util.List;
import lombok.Data;

@Data
public class GeoResponse {

    private List<Location> results;

}