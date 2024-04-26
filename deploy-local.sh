./gradlew --exclude-task test clean build  -Dspring.profiles.active=local

docker build -t backend-kingofsettlement:test -f local.Dockerfile .
docker rm -f project-backend
docker run -dit -p 8000:8080 --name project-backend backend-kingofsettlement:test

