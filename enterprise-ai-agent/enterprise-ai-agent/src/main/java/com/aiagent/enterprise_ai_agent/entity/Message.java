package com.aiagent.enterprise_ai_agent.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;
    
}