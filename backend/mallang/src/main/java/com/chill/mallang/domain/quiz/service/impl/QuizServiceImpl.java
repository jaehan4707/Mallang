package com.chill.mallang.domain.quiz.service.impl;


import com.chill.mallang.domain.quiz.dto.QuizDto;
import com.chill.mallang.domain.quiz.repository.QuizRepository;
import com.chill.mallang.domain.quiz.service.QuizService;
import com.theokanning.openai.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final OpenAiService openAiService;
    private Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, OpenAiService openAiService) {
        this.quizRepository = quizRepository;
        this.openAiService = openAiService;
    }

    @Override
    public QuizDto createQuizFromPrompt() {
        String prompt = "너는 지금부터 문해력 문제 출제자야. 10대 청소년들이 문해력이 부족한 것에 대해 향상 시키기 위해 간단한 문단을 제공해야해. 난이도를 1~10으로 나눈다면 난이도 1 정도의 수준에서 문제를 만들어줘. 결과는 Json 형식으로 답변해.";

        return null;

    }

    @Override
    public void checkModels() {
        // write code only here
        String endpoint = "https://api.openai.com/v1/models";
        String apiKey = "sk-proj-oLYCOoeRhgUADZ8DBRyWT3BlbkFJttWKXgetMoclVmY0DzbS";  // 실제 키를 여기 입력하세요.

        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 응답 데이터를 로그에 출력
                logger.info("Response: {}", response.toString());

            } else {
                logger.error("GET request not worked, Response Code: {}", responseCode);
            }
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException: ", e);
        } catch (IOException e) {
            logger.error("IOException: ", e);
        }
    }
}
