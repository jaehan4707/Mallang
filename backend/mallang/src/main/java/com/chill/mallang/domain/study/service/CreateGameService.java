package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.model.Problem;
import com.chill.mallang.domain.study.model.Question;
import com.chill.mallang.domain.study.model.StudyGame;
import com.chill.mallang.domain.study.model.WordMean;
import com.chill.mallang.domain.study.repository.ProblemRepository;
import com.chill.mallang.domain.study.repository.QuestionRepository;
import com.chill.mallang.domain.study.repository.StudyGameRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateGameService {
    private final StudyGameRepository studyGameRepository;
    private final StudyOpenAIService studyOpenAIService;
    private final QuestionRepository questionRepository;
    private final ProblemRepository problemRepository;

    public StudyGame initializeStudyGame(WordMean wordMean) {
        JSONObject result = studyOpenAIService.makeNewStudyQuiz(wordMean.getWord().getWord(),wordMean.getMean());
        StudyGame studyGame = StudyGame.builder()
                .wordMean(wordMean)
                .questionText(result.getString("question"))
                .build();
        studyGameRepository.save(studyGame);
        initializeQuestion(studyGame, result);
        return studyGame;
    }

    public Question initializeQuestion(StudyGame studyGame, JSONObject result) {
        Question question = Question.builder()
                .studyGame(studyGame)
                .build();
        questionRepository.save(question);
        addProblemsToQuestion(question, result);
        return question;
    }

    public void addProblemsToQuestion(Question question, JSONObject result) {
        JSONArray jsonArray = result.getJSONArray("options");
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String basic_type = jsonObject.getString("basic_type");
            String option = jsonObject.getString("word");
            String mean = jsonObject.getString("meaning");
            int idx = numbers.get(i); // 섞인 numbers 리스트의 값을 사용

            Problem problem = Problem.builder()
                    .question(question)
                    .basic_type(basic_type)
                    .option(option)
                    .mean(mean)
                    .idx(idx)
                    .build();

            problemRepository.save(problem);
        }
    }
}
