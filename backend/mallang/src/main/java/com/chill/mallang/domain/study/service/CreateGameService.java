package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.model.Problem;
import com.chill.mallang.domain.study.model.Question;
import com.chill.mallang.domain.study.model.StudyGame;
import com.chill.mallang.domain.study.model.WordMean;
import com.chill.mallang.domain.study.repository.StudyGameRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateGameService {
    private final StudyGameRepository studyGameRepository;
    private final StudyOpenAIService studyOpenAIService;

    public StudyGame initializeStudyGame(WordMean wordMean) {
        JSONObject result = studyOpenAIService.makeNewStudyQuiz(wordMean.getWord().getWord(),wordMean.getMean());
        StudyGame studyGame = new StudyGame();
        studyGame.setWordMean(wordMean);
        studyGame.setQuestionText(result.getString("question"));
        initializeQuestion(studyGame, result);
        return studyGame;
    }

    public Question initializeQuestion(StudyGame studyGame, JSONObject result) {
        Question question = new Question();
        question.setStudyGame(studyGame);
        addProblemsToQuestion(question, result);
        studyGame.setQuestion(question);
        return question;
    }

    public void addProblemsToQuestion(Question question, JSONObject result) {
        JSONArray jsonArray = result.getJSONArray("options");
        // JSONArray의 각 JSONObject를 처리
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String basic_type = jsonObject.getString("basic_type");
            String option = jsonObject.getString("option");
            String mean = jsonObject.getString("mean");
            int idx = jsonObject.getInt("idx");
            question.addProblem(createProblem(basic_type,option, mean, idx));
        }
    }

    public Problem createProblem(String basicType, String obtion, String mean, int idx) {
        Problem problem = new Problem();
        problem.setBasic_type(basicType);
        problem.setObtion(obtion);
        problem.setMean(mean);
        problem.setIdx(idx);
        return problem;
    }
}
