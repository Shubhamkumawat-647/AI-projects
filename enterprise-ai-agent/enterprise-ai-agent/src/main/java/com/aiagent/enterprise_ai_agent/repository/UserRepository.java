package com.aiagent.enterprise_ai_agent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiagent.enterprise_ai_agent.entity.User;

public interface UserRepository
extends JpaRepository<User,Long>{

    Optional<User>
    findByEmail(String email);

}