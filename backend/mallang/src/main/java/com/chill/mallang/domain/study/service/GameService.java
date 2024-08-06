package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.dto.StudyGameDTO;
import com.chill.mallang.domain.study.dto.UserStudyLogRequestDTO;
import com.chill.mallang.domain.study.dto.core.WordMeanDTO;
import com.chill.mallang.domain.study.errors.CustomStudyErrorCode;
import com.chill.mallang.domain.study.model.Problem;
import com.chill.mallang.domain.study.model.Question;
import com.chill.mallang.domain.study.model.StudyGame;
import com.chill.mallang.domain.study.model.WordMean;
import com.chill.mallang.domain.study.repository.StudyGameLogRepository;
import com.chill.mallang.domain.study.repository.StudyGameRepository;
import com.chill.mallang.domain.study.repository.WordMeanRepository;
import com.chill.mallang.domain.user.errors.CustomUserErrorCode;
import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.domain.user.service.UserSettingService;
import com.chill.mallang.errors.exception.RestApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {
    private static final Logger logger = LoggerFactory.getLogger(UserSettingService.class);
    private final JWTUtil jwtUtil;
    private final StudyGameLogRepository studyGameLogRepository;
    private final GameWordService gameWordService;
    private final StudyGameRepository studyGameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WordMeanRepository wordMeanRepository;

    public GameService(JWTUtil jwtUtil, StudyGameRepository studyGameRepository, StudyGameLogRepository studyGameLogRepository, GameWordService gameWordService) {
        this.jwtUtil = jwtUtil;
        this.studyGameLogRepository = studyGameLogRepository;
        this.studyGameRepository = studyGameRepository;
        this.gameWordService = gameWordService;
    }
    //사용자 조회
    private User getUserFromRequest(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
    }
    //wordmean에 대한 게임 유뮤 조회 -> 없으면 생성
    @Transactional
    public StudyGame createStudyGameWithWordMean(Long wordMeanId) {
        WordMean wordMean = wordMeanRepository.findById(wordMeanId)
                .orElseThrow(() ->  new RestApiException(CustomStudyErrorCode.WORDMEAN_IS_NOT_FOUND));

        StudyGame studyGame = new StudyGame();
        String questionText = "What is the meaning of this word?";
        studyGame.setWordMean(wordMean);
        studyGame.setQuestionText(questionText);
        Question question = new Question();
        question.setStudyGame(studyGame);
        studyGame.setQuestion(question);
        Problem problem1 = new Problem();
        problem1.setWord("ExampleWord1");
        problem1.setMean("ExampleMean1");
        Problem problem2 = new Problem();
        problem2.setWord("ExampleWord2");
        problem2.setMean("ExampleMean2");
        Problem problem3 = new Problem();
        problem3.setWord("ExampleWord3");
        problem3.setMean("ExampleMean3");

        question.addProblem(problem1);
        question.addProblem(problem2);
        question.addProblem(problem3);

        studyGame.setQuestion(question);
        return studyGameRepository.save(studyGame);
    }

    // 단어 뜻 관련 게임 조회 및 없으면 생성 후 return
    private StudyGame getOrCreateStudyGame(WordMean wordMean) {
        StudyGame existingStudyGame = studyGameRepository.findByWordMeanId(wordMean.getId());
        if (existingStudyGame != null) {
            logger.info("Existing study game: {}", existingStudyGame);
            return existingStudyGame;
        }
        StudyGame newStudyGame = createStudyGameWithWordMean(wordMean.getId());
        logger.info("Created new study game: {}", newStudyGame);
        return newStudyGame;
    }

    private StudyGameDTO createUserStudyLogRequestDTO(User user, StudyGame studyGame, WordMean wordMean) {
        WordMeanDTO wordMeanDTO = gameWordService.convertToDTO(wordMean);
        List<Map<String, String>> wordList = new ArrayList<>();
        studyGame.getQuestion().getProblems().forEach(problem -> {
            Map<String, String> wordMap = new HashMap<>();
            wordMap.put(problem.getWord(), problem.getMean());
            wordList.add(wordMap);
        });
        Map<String, String> answerMap = new HashMap<>();
        answerMap.put(wordMean.getWord().getWord(), wordMean.getMean());
        wordList.add(answerMap);
        return StudyGameDTO.builder()
                .studyId(studyGame.getId())
                .quizScript(studyGame.getQuestionText())
                .wordList(wordList)
                .build();
    }


    public Map<String, Object> StartGame(Long userId) {
        User user = getUserFromRequest(userId);
        logger.info("StartGame User: " + user);

        WordMean selectedWordMean = gameWordService.getRandomUnusedWordMean(user.getId());
        StudyGame studyGame = getOrCreateStudyGame(selectedWordMean);
        StudyGameDTO studyGameDTO = createUserStudyLogRequestDTO(user, studyGame, selectedWordMean);
        Map<String, Object> response = new HashMap<>();
        response.put("data",studyGameDTO);
        return response;
    }
}
