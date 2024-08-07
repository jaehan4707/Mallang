package com.chill.mallang.domain.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import static net.minidev.json.JSONValue.isValidJson;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyOpenAIService {

    private final Logger logger = LoggerFactory.getLogger(StudyOpenAIService.class);
    private final OpenAiChatModel openAiChatModel;

    public JSONObject makeNewStudyQuiz(String word, String meaning) {
        String prompt = String.format(
                "역할: 문해력 문제 출제자\n" +
                        "response 형식: json 형식\n" +
                        "요구사항 상세:\n" +
                        "정답이 될 단어와 그 뜻이 주어지면, \n" +
                        "문제(question), 정답이 될 주어진 단어(word), 그리고 3개의 오답 단어와 정답 단어가 섞여있는 option들과 그 뜻(meaning), 그리고 기본형(basic_type) 생성\n" +
                        "\n" +
                        "'question'\n" +
                        "1. 정답 단어를 사용한 하나의 예제 문장. 정답 단어의 자리는 언더바('_') 2개로 '__'와 같이 표시\n" +
                        "2. 주어진 단어가 용언일 경우에는 활용형을 적용하여 생성\n" +
                        "3. 빈칸 뒤에, 앞 단어의 선행 단어의 말음 종성 여부에 영향을 받는 조사나 어미(예컨대 '을/를', '로/으로') 가 오면, 두 가지의 형태를 모두 표시할 것\n" +
                        "5. 빈칸은 최소 단위로 비울 것\n" +
                        "\n" +
                        "'options'\n" +
                        "1. 주어진 단어가 용언일 경우에는 활용형을 적용하여 생성\n" +
                        "2. 기본형은 사전적 기본형\n" +
                        "3. 각 option에는 1-4까지의 숫자를 겹치지 않게 'idx' 키에 무작위로 추가함.\n" +
                        "4. '오답 단어'는 정답 단어와 품사와 같으며, 문장에의 역할이 같지만 의미하는 바가 완전히 다른 단어.\n" +
                        "5. '오답 단어'는 정답 단어의 유의어나 동음이의어일 수 없음.\n" +
                        "6. 오답단어를 정답자리에 넣은 question문장이 자연스러운 문장이 된다면, 그 단어는 오답 단어로 적절하지 않으므로 제외하고 다시 생성함\n" +
                        "\n" +
                        "'word'\n" +
                        "1. word는 빈칸에 들어갈 형태 그대로 출력.\n" +
                        "\n" +
                        "'meaning'\n" +
                        "1. 표준국어대사전에 해당 단어의 기본형(basic_type)을 검색해 나오는 뜻으로 함\n" +
                        "2. 각 options 리스트 각 항목마다 추가\n" +
                        "예시:\n" +
                        "{\n" +
                        "  \"question\": \"내일 있을 발표를 위해 철저히 __했다.\",\n" +
                        "  \"word\": \"%s\",\n" +
                        "  \"options\": [\n" +
                        "    {\n" +
                        "      \"idx\": 0,\n" +
                        "      \"word\": \"%s\",\n" +
                        "      \"meaning\": \"%s\",\n" +
                        "      \"basic_type\": \"%s\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"idx\": 1,\n" +
                        "      \"word\": \"청소\",\n" +
                        "      \"meaning\": \"더러운 것을 깨끗하게 함.\",\n" +
                        "      \"basic_type\": \"청소\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"idx\": 2,\n" +
                        "      \"word\": \"연습\",\n" +
                        "      \"meaning\": \"기술이나 동작을 잘하기 위해 반복하여 익힘.\",\n" +
                        "      \"basic_type\": \"연습\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"idx\": 3,\n" +
                        "      \"word\": \"정리\",\n" +
                        "      \"meaning\": \"어수선한 상태를 가지런히 바로잡음.\",\n" +
                        "      \"basic_type\": \"정리\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}", word, word, meaning, word);
        String answer = openAiChatModel.call(prompt);

        // 응답 로그 출력
        logger.info("OpenAI 응답: {}", answer);

        // 응답이 JSON 형식인지 확인
        if (isValidJson(answer)) {
            try {
                JSONObject response = new JSONObject(answer);
                return response;
            } catch (JSONException e) {
                logger.error("JSON 파싱 오류: {}", answer, e);
                throw new RuntimeException("JSON 파싱 중 오류가 발생했습니다.", e);
            }
        } else {
            logger.error("응답이 JSON 형식이 아님: {}", answer);
            throw new RuntimeException("응답이 JSON 형식이 아닙니다.");
        }
    }

    private boolean isValidJson(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }
}
