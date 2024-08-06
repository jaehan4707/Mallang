package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.area.service.AreaErrorCode;
import com.chill.mallang.domain.study.dto.user.OneWrongWordDto;
import com.chill.mallang.domain.study.errors.CustomStudyErrorCode;
import com.chill.mallang.domain.study.model.StudyGame;
import com.chill.mallang.domain.study.model.StudyGameLog;
import com.chill.mallang.domain.study.repository.ProblemRepository;
import com.chill.mallang.domain.study.repository.QuestionRepository;
import com.chill.mallang.domain.study.repository.StudyGameLogRepository;
import com.chill.mallang.errors.exception.RestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OneWrongWordService {

    private static final Logger logger = LoggerFactory.getLogger(OneWrongWordService.class);

    @Autowired
    StudyGameLogRepository studyGameLogRepository;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    QuestionRepository questionRepository;

    public Map<String, Object> getOneWrongWord(Long userId, Long studyId){  // studyId : 문제 번호
        Optional<StudyGameLog> studyGameLog = studyGameLogRepository.getOneWrongStudyGameLogByUserIdStudyId(userId,studyId);
        Long questionId = questionRepository.findIdByStudyGameId(studyId);
        logger.info("questionId : " + questionId.toString());

        if (!studyGameLog.isPresent()) {
            throw new RestApiException(CustomStudyErrorCode.NOT_WRONG_WORD);
        }
        List<String> wordList = problemRepository.findWordListByQuestionId(questionId);
        wordList.add(studyGameLog.get().getStudyGame().getWordMean().getWord().getWord());

        logger.info("wordList : " + wordList.toString());

        OneWrongWordDto oneWrongWordDto = OneWrongWordDto.builder()
                .studyId(studyId)
                .quizTitle("빈 칸을 채워주세요")
                .quizScript(studyGameLog.get().getStudyGame().getQuestionText())
                .wordList(wordList)
                .build();

        return new HashMap<>(){{
            put("data",oneWrongWordDto);
        }};
    }
}
