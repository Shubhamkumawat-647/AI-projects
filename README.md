docker ps
Output:
CONTAINER ID
 -----------Docker Ready 
________________________________________
Step 2: Pull pgvector Image
Run:
docker pull pgvector/pgvector:pg17
________________________________________
Step 3: Run PostgreSQL + pgvector
Run:
docker run -d ^
--name pgvector-db ^
-e POSTGRES_USER=postgres ^
-e POSTGRES_PASSWORD=postgres ^
-e POSTGRES_DB=ai_interview ^
-p 5432:5432 ^
pgvector/pgvector:pg17

-------------------------------------------------------------------------------------


# Enterprise AI Agent

Production-ready Enterprise AI Agent built with **Spring Boot**, **Spring AI**, **Ollama**, **PostgreSQL + pgvector**, **JWT Authentication**, **RAG**, and **Docker**.

---

# Features

* JWT Authentication
* User Registration & Login
* Forgot Password
* Conversation Management
* Auto Conversation Creation
* Chat History
* AI Chat using Ollama
* RAG (Retrieval Augmented Generation)
* PDF Upload
* PDF Download
* PDF Delete
* PDF Replace
* Vector Search using pgvector
* Spring AI Integration
* Swagger Documentation
* Spring Boot Actuator
* Global Exception Handling
* Bean Validation
* Production Logging
* Docker Support

---

# Tech Stack

* Java 21
* Spring Boot 3.5.x
* Spring AI 1.1.x
* Spring Security
* JWT
* PostgreSQL
* pgvector
* Ollama
* Maven
* Docker
* Swagger (OpenAPI)
* Lombok

---

# Project Structure

```text
enterprise-ai-agent
│
├── src
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   ├── security
│   ├── config
│   ├── util
│   └── exception
│
├── uploads
├── logs
├── Dockerfile
├── docker-compose.yml
├── .env
├── pom.xml
└── README.md
```

---

# Prerequisites

Install the following software before running the project:

* Java 21
* Maven 3.9+
* PostgreSQL 17
* Docker Desktop
* Ollama

---

# Install Ollama

Download and install Ollama.

Start Ollama:

```bash
ollama serve
```

Pull the chat model:

```bash
ollama pull qwen2.5:7b
```

Pull the embedding model:

```bash
ollama pull nomic-embed-text
```

Verify:

```bash
ollama list
```

---

# PostgreSQL

Create Database

```sql
CREATE DATABASE enterprise_ai_agent;
```

Enable pgvector

```sql
CREATE EXTENSION vector;
```

---

# Clone Project

```bash
git clone https://github.com/yourusername/enterprise-ai-agent.git

cd enterprise-ai-agent
```

---

# Configure Environment

Create `.env`

```properties
DB_HOST=localhost
DB_PORT=5433
DB_NAME=enterprise-ai-agent
DB_USERNAME=postgres
DB_PASSWORD=postgres

JWT_SECRET=YourSecretKey

OLLAMA_BASE_URL=http://localhost:11434
```

---

# Configure application.properties

```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}

spring.datasource.username=${DB_USERNAME}

spring.datasource.password=${DB_PASSWORD}

spring.ai.ollama.base-url=${OLLAMA_BASE_URL}
```

---

# Build Project

```bash
mvn clean package
```

or

```bash
mvnw clean package
```

---

# Run Application

```bash
mvn spring-boot:run
```

or

```bash
java -jar target/enterprise-ai-agent.jar
```

---

# Docker

Build Image

```bash
docker build -t enterprise-ai-agent .
```

Check Images

```bash
docker images
```

Run Container

```bash
docker run -p 8082:8082 enterprise-ai-agent
```

Run with Custom Name

```bash
docker run --name enterprise-ai-agent-container -p 8082:8082 enterprise-ai-agent
```

View Running Containers

```bash
docker ps
```

View All Containers

```bash
docker ps -a
```

View Logs

```bash
docker logs enterprise-ai-agent-container
```

Follow Logs

```bash
docker logs -f enterprise-ai-agent-container
```

Stop Container

```bash
docker stop enterprise-ai-agent-container
```

Start Container

```bash
docker start enterprise-ai-agent-container
```

Remove Container

```bash
docker rm -f enterprise-ai-agent-container
```

Remove Image

```bash
docker rmi enterprise-ai-agent
```

---

# Docker Compose

Start All Services

```bash
docker compose up -d
```

Stop All Services

```bash
docker compose down
```

Restart

```bash
docker compose restart
```

View Logs

```bash
docker compose logs -f
```

List Containers

```bash
docker compose ps
```

Rebuild

```bash
docker compose up --build
```

---

# Swagger

```
http://localhost:8082/swagger-ui/index.html
```

---

# Actuator

Health

```
http://localhost:8082/actuator/health
```

Info

```
http://localhost:8082/actuator/info
```

Metrics

```
http://localhost:8082/actuator/metrics
```

---

# API Modules

## Authentication

* Register
* Login
* Forgot Password
* Reset Password

## Conversation

* Create Conversation
* Rename Conversation
* Get All Conversations
* Delete Conversation

## Chat

* AI Chat
* Chat History

## Document

* Upload PDF
* Download PDF
* Delete PDF
* Replace PDF
* List Documents

---

# RAG Flow

```text
PDF Upload
      │
      ▼
Extract Text
      │
      ▼
Chunk Text
      │
      ▼
Generate Embeddings
      │
      ▼
Store in pgvector
      │
      ▼
Similarity Search
      │
      ▼
Context + LLM
      │
      ▼
Final AI Response
```

---

# Project Architecture

```text
Client
   │
   ▼
Spring Boot REST API
   │
   ├──────── JWT Security
   │
   ├──────── Conversation Service
   │
   ├──────── Document Service
   │
   ├──────── AI Service
   │
   ├──────── Spring AI
   │
   ├──────── Ollama
   │
   └──────── PostgreSQL + pgvector
```

---

# Logging

Logs Location

```text
logs/
```

---

# Default Ports

| Service     | Port  |
| ----------- | ----- |
| Spring Boot | 8082  |
| PostgreSQL  | 5433  |
| Ollama      | 11434 |

---

# Future Improvements

* Redis Cache
* Streaming Responses
* Image OCR
* Excel RAG
* Word Document RAG
* Multi-Model Support
* Kubernetes Deployment
* CI/CD Pipeline
* Monitoring with Prometheus & Grafana

---

# Author

**Shubham Kumawat**

Java Full Stack Developer

Enterprise AI Agent using Spring Boot, Spring AI, Ollama, PostgreSQL, pgvector and Docker.

---

# License

This project is created for learning, portfolio and interview demonstration purposes.








