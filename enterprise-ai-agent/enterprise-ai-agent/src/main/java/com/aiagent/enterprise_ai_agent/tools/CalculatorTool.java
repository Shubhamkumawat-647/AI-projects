package com.aiagent.enterprise_ai_agent.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTool {

    @Tool(
            description = "Perform mathematical calculations"
    )
    public double calculate(String expression){

        expression = expression.replace(" ", "");

        try{

            if(expression.contains("+")){

                String[] values = expression.split("\\+");

                return Double.parseDouble(values[0])
                        +
                        Double.parseDouble(values[1]);

            }

            if(expression.contains("-")){

                String[] values = expression.split("-");

                return Double.parseDouble(values[0])
                        -
                        Double.parseDouble(values[1]);

            }

            if(expression.contains("*")){

                String[] values = expression.split("\\*");

                return Double.parseDouble(values[0])
                        *
                        Double.parseDouble(values[1]);

            }

            if(expression.contains("/")){

                String[] values = expression.split("/");

                return Double.parseDouble(values[0])
                        /
                        Double.parseDouble(values[1]);

            }

        }

        catch(Exception e){

            return 0;

        }

        return 0;

    }

}