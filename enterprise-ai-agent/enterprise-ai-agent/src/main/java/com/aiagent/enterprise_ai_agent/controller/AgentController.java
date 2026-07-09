package com.aiagent.enterprise_ai_agent.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.aiagent.enterprise_ai_agent.config.DTO.ChatRequest;
import com.aiagent.enterprise_ai_agent.config.DTO.ChatResponse;
import com.aiagent.enterprise_ai_agent.config.DTO.StreamResponse;
import com.aiagent.enterprise_ai_agent.service.AgentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class AgentController {
	

    private final AgentService agentService;

//    @PostMapping("/chat")
//    public ChatResponse chat(
//            @RequestBody ChatRequest request){
//
//        String response =
//                agentService.chat(
//                        request.getMessage());
//
//        return new ChatResponse(response);
//
//    }

//    @GetMapping(value="/stream/{conversationId}",produces =MediaType.TEXT_EVENT_STREAM_VALUE)
//    		public Flux<String> stream(@PathVariable String conversationId,@RequestParam String message){
//    		return agentService.streamChat(conversationId,message);
//
//    	}
    
    
    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return agentService.chat(request);
    }
    @PostMapping(
            value = "/chat/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<StreamResponse> streamChat(
            @RequestBody ChatRequest request) {

        return agentService.streamChat(request);

    }
}