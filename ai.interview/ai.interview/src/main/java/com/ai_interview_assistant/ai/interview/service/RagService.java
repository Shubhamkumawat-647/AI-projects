package com.ai_interview_assistant.ai.interview.service;

import java.util.List;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class RagService {

    private final VectorStore vectorStore;
    private final ChatModel chatModel;

    public RagService(VectorStore vectorStore,
                      ChatModel chatModel) {

        this.vectorStore = vectorStore;
        this.chatModel = chatModel;
    }

    public String askQuestion(String question,String fileName) {

    	SearchRequest.Builder builder = SearchRequest.builder()
                .query(question)
                .topK(3);
        
              if(fileName != null && !fileName.isBlank()){
                	builder.filterExpression("fileName == '" + fileName + "'");
                }
              SearchRequest request =builder.build();

        List<Document> documents =vectorStore.similaritySearch(request);
        if(documents.isEmpty()){
            return "No relevant document found.";
        }

        if (documents == null || documents.isEmpty()) {
            return "I couldn't find any relevant information in the uploaded documents.";
        }

        StringBuilder context = new StringBuilder();

        for (Document doc : documents) {
            context.append(doc.getText());
            context.append("\n\n");
            context.append("Source : ");
            context.append(doc.getMetadata().get("fileName"));
            context.append("\n\n");
        }

        String messages = """
You are an AI assistant.

Answer ONLY using the provided context.

If the answer is not available in the context, reply exactly:

"I couldn't find the answer in the provided document."

Context:
%s

Question:
%s
""".formatted(context.toString(), question);
        Prompt prompt = new Prompt(messages);

        return chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }
}