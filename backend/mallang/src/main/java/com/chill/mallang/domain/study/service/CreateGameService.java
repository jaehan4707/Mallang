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
        question.addProblem(createProblem("ExampleWord1", "EEE", "ExampleMean1", 4));
        question.addProblem(createProblem("ExampleWord2", "22222", "ExampleMean2", 1));
        question.addProblem(createProblem("ExampleWord3", "333333333", "ExampleMean3", 2));
        question.addProblem(createProblem(wordMean.getWord().getWord(), "4444", wordMean.getMean(), 3));
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
