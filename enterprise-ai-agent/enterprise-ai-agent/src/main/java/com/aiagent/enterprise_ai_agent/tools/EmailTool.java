//package com.aiagent.enterprise_ai_agent.tools;
//
//import org.springframework.ai.tool.annotation.Tool;
//import org.springframework.ai.tool.annotation.ToolParam;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Component;
//
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class EmailTool {
//
//    private final JavaMailSender mailSender;
//
//    @Tool(description = "Send an email to any recipient")
//    public String sendEmail(
//
//            @ToolParam(description = "Recipient email address")
//            String to,
//
//            @ToolParam(description = "Email subject")
//            String subject,
//
//            @ToolParam(description = "Email body")
//            String body
//
//    ){
//
//        try{
//
//            SimpleMailMessage message =
//                    new SimpleMailMessage();
//
//            message.setTo(to);
//
//            message.setSubject(subject);
//
//            message.setText(body);
//
//            mailSender.send(message);
//
//            return "Email sent successfully.";
//
//        }
//
//        catch(Exception ex){
//
//            return "Failed to send email : "
//                    + ex.getMessage();
//
//        }
//
//    }
//
//}