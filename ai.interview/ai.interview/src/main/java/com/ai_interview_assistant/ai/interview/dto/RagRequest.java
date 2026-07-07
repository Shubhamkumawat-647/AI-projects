package com.ai_interview_assistant.ai.interview.dto;

public class RagRequest {

    private String question;

    private String fileName;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}