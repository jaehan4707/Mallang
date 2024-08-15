package com.chill.mallang.domain.quiz.service.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIService {

    private final Logger logger = LoggerFactory.getLogger(OpenAIService.class);
    private final OpenAiChatModel openAiChatModel;

    public void makeNewQuestion(){
        String prompt = "너는 지금부터 문해력 문제 출제자야.\n" +
                "18세 ~ 23세 청소년들이 문해력이 부족한 것을 향상시키기 위해 문단을 제공해야 해.\n" +
                "난이도를 1~10으로 나눈다면 난이도 10 정도의 수준에서 문제를 만들어줘.\n" +
                "문단은 250 ~ 300자 범위 안에서 작성해줘.\n" +
                "응답은 반드시 JSON 형식으로 작성해야 해. 응답은 {로 시작해서 }로 끝나야 해.\n" +
                "JSON 객체는 다음의 세 가지 key를 포함해야 해:\n" +
                "1. \"difficulty\": 난이도를 나타내는 숫자 (1~10)\n" +
                "2. \"question\": 문해력 문제의 문단\n" +
                "3. \"answer\": 문단을 1줄로 요약한 내용\n" +
                "주제는 10대 토픽에 대해 검색하고 정해줘.\n" +
                "예시:\n" +
                "{\n" +
                "  \"difficulty\": 10,\n" +
                "  \"question\": \"문단 내용...\",\n" +
                "  \"answer\": \"요약 내용...\"\n" +
                "}\n" +
                "위 형식을 꼭 지켜서 답변해줘.";


        String answer =openAiChatModel.call(prompt);
        // Json Parsing
        logger.info("Start Parsing GPT Response");
        JSONObject jsonObject = new JSONObject(answer);

        int difficulty = jsonObject.getInt("difficulty");
        String question = jsonObject.getString("question");
        String gptAnswer = jsonObject.getString("answer");
        logger.info("Difficulty: {}", difficulty);
        logger.info("Question {}", question);
        logger.info("Gpt Answer {}", gptAnswer);

        logger.info("End Parsing GPT Response");
    }

    public String getScoreUseAI(String question, String answer){
        String prompt = "너는 지금부터 문제를 채점하는 채점자야. " +
                "내가 문제와 정답을 주면 평가를 내려줘. " +
                "평가 기준은 Question을 얼마나 잘 이해하고 요약해서 UserAnswer을 작성했는지 문해력 확인이야. " +
                "이해도를 기준으로 냉정하게 평가해줘. " +
                "응답은 Double형으로 숫자인 점수만 알려줘. " +
                "0 ~ 100 점 사이 점수로 표현해.";

        prompt += "Question : " + question;
        prompt += "Answer : " + answer;
        return openAiChatModel.call(prompt);
    }
}
