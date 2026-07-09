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
