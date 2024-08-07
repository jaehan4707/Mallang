package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.model.Problem;
import com.chill.mallang.domain.study.model.Question;
import com.chill.mallang.domain.study.model.StudyGame;
import com.chill.mallang.domain.study.model.WordMean;
import org.springframework.stereotype.Service;

@Service
public class CreateGameService {

    public StudyGame initializeStudyGame(WordMean wordMean) {
        StudyGame studyGame = new StudyGame();
        studyGame.setWordMean(wordMean);
        studyGame.setQuestionText("What is the meaning of this word?");
        return studyGame;
    }

    public Question initializeQuestion(StudyGame studyGame) {
        Question question = new Question();
        question.setStudyGame(studyGame);
        return question;
    }

    public void addProblemsToQuestion(Question question, WordMean wordMean) {
        question.addProblem(createProblem("ExampleWord1", "활용1", "ExampleMean1", 1));
        question.addProblem(createProblem("ExampleWord2", "활용2", "ExampleMean2", 2));
        question.addProblem(createProblem("ExampleWord3", "활용3", "ExampleMean3", 3));
        question.addProblem(createProblem(wordMean.getWord().getWord(), "활용4", wordMean.getMean(), 4));
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
