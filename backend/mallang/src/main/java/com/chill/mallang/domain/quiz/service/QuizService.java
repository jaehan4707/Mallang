package com.chill.mallang.domain.quiz.service;


import com.chill.mallang.domain.quiz.dto.ChatGPTRequest;
import com.chill.mallang.domain.quiz.dto.ChatGPTResponse;
import com.chill.mallang.domain.quiz.dto.RequestQuizAnswer;
import com.chill.mallang.domain.quiz.dto.ResponseQuiz;
import com.chill.mallang.domain.quiz.error.QuizErrorCode;
import com.chill.mallang.domain.quiz.model.Answer;
import com.chill.mallang.domain.quiz.model.Quiz;
import com.chill.mallang.domain.quiz.repository.AnswerRepository;
import com.chill.mallang.domain.quiz.repository.QuizRepository;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.errors.exception.RestApiException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
public class QuizService {

    private final OpenAiService openAiService;
    private Logger logger = LoggerFactory.getLogger(QuizService.class);

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate template;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    public QuizService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }


    public void createQuizFromPrompt() {
        String prompt = "너는 지금부터 문해력 문제 출제자야.\n" +
                "18세 ~ 23세 청소년들이 문해력이 부족한 것에 대해 향상 시키기 위해 문단을 제공해야해.\n" +
                "난이도를 1~10으로 나눈다면 난이도 10 정도의 수준에서 문제를 만들어줘.\n" +
                "문단은 250 ~ 300자 범위 안에서 만들어줘.\n" +
                "결과는 Json 형식으로 답변해.\n" +
                "difficulty를 key로 가지면서 난이도를 표현해.\n" +
                "question을 key로 가지면서 문단을 알려줘";

        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse = template.postForObject(apiUrl, request, ChatGPTResponse.class);

        ChatGPTResponse.Choice choice = chatGPTResponse.getChoices().get(0);

        String jsonString = chatGPTResponse.getChoices().get(0).getMessage().getContent();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonString);

            int difficulty = rootNode.get("difficulty").asInt();
            String question = rootNode.get("question").asText();

            logger.info(Integer.toString(difficulty));
            logger.info(question);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


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

    public Map<String, Object> getById(Long quizID) {
        Optional<Quiz> quiz = quizRepository.findById(quizID);
        logger.info(String.valueOf("QuizDTO : "), quiz);
        if (quiz.isPresent()) {

            ResponseQuiz responseQuiz = ResponseQuiz.builder()
                    .id(quiz.get().getId())
                    .question(quiz.get().getQuestion())  // 추가된 부분
                    .answer(quiz.get().getAnswer())
                    .difficulty(quiz.get().getDifficulty())
                    .type(quiz.get().getType())
                    .build();

            return new HashMap<String, Object>() {{
                    put("Data", responseQuiz);
            }};
        } else {
            throw new RestApiException(QuizErrorCode.INVALID_QUIZ_PK);
        }
    }

    public void submitAnswer(RequestQuizAnswer requestQuizAnswer){
        logger.info(String.valueOf(requestQuizAnswer));
        saveAnswer(requestQuizAnswer);
        logger.info("Success Save Answer");
    }

    public Answer saveAnswer(RequestQuizAnswer requestQuizAnswer){

        Answer answer = Answer.builder()
                .user(userRepository.findById(requestQuizAnswer.getUserId()).orElseThrow(() -> new RestApiException(QuizErrorCode.USER_NOT_FOUND)))
                .quiz(quizRepository.findById(requestQuizAnswer.getQuizId()).orElseThrow(() -> new RestApiException(QuizErrorCode.QUIZ_NOT_FOUND)))
                .answer(requestQuizAnswer.getUserAnswer())
                .answerTime(requestQuizAnswer.getAnswerTime())
                .score(100)
                .check_fin(0) // 기본값 설정
                .build();

        logger.info("Add answer data : " , answer.toString());
        return answerRepository.save(answer);
    }
}
