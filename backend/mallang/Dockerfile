## 어떤 image를 사용할 것인가
FROM eclipse-temurin:21-jdk-alpine
## 시간대 설정 추가
# 환경 변수로 시간대 설정
ENV TZ=Asia/Seoul
# 시간대 파일을 설정
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
## jar 파일로 만들어라
ARG JAR_FILE=build/libs/*.jar
## 실행
COPY ${JAR_FILE} mallang-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/mallang-0.0.1-SNAPSHOT.jar"]
