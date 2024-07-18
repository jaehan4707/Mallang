#FROM ubuntu:latest
#LABEL authors="SSAFY"
#
#ENTRYPOINT ["top", "-b"]

## 어떤 image를 사용할 것인가
FROM eclipse-temurin:21-jdk-alpine
## jar 파일로 만들어라
ARG JAR_FILE=build/libs/*.jar
## 실행
COPY ${JAR_FILE} mallang-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/mallang-0.0.1-SNAPSHOT.jar"]
