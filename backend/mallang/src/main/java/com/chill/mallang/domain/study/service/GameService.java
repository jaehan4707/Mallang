package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.dto.StudyGameDTO;
import com.chill.mallang.domain.study.dto.UserStudyLogResponseDTO;
import com.chill.mallang.domain.study.dto.core.WordMeanDTO;
import com.chill.mallang.domain.study.errors.CustomStudyErrorCode;
import com.chill.mallang.domain.study.model.*;
import com.chill.mallang.domain.study.repository.StudyGameLogRepository;
import com.chill.mallang.domain.study.repository.StudyGameRepository;
import com.chill.mallang.domain.study.repository.WordMeanRepository;
import com.chill.mallang.domain.user.errors.CustomUserErrorCode;
import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.domain.user.service.UserSettingService;
import com.chill.mallang.errors.errorcode.CustomErrorCode;
import com.chill.mallang.errors.exception.RestApiException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        logger.info("userId"+userId);
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
        problem1.setBasic_type("ExampleWord1");
        problem1.setObtion("EEE");
        problem1.setMean("ExampleMean1");
        problem1.setIdx(0);
        Problem problem2 = new Problem();
        problem2.setBasic_type("ExampleWord2");
        problem2.setObtion("22222");
        problem2.setMean("ExampleMean2");
        problem2.setIdx(1);
        Problem problem3 = new Problem();
        problem3.setBasic_type("ExampleWord3");
        problem3.setObtion("333333333");
        problem3.setMean("ExampleMean3");
        problem3.setIdx(2);
        Problem problem4 = new Problem();
        problem4.setBasic_type(wordMean.getWord().getWord());
        problem4.setObtion("4444");
        problem4.setMean(wordMean.getMean());
        problem4.setIdx(3);
        question.addProblem(problem1);
        question.addProblem(problem2);
        question.addProblem(problem3);
        question.addProblem(problem4);
        studyGame.setQuestion(question);
        return studyGameRepository.save(studyGame);
    }

    // 단어 뜻 관련 게임 조회 및 없으면 생성 후 return
    private StudyGame getOrCreateStudyGame(WordMean wordMean) {
        StudyGame existingStudyGame = studyGameRepository.findByWordMeanId(wordMean.getId());
        logger.info("existingStudyGame"+existingStudyGame);
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
        studyGame.getQuestion().getProblems().stream()
                .sorted(Comparator.comparingInt(Problem::getIdx))
                .forEach(problem -> {
            Map<String, String> wordMap = new HashMap<>();
            wordMap.put(problem.getObtion(), problem.getMean());
            wordList.add(wordMap);
        });
        return StudyGameDTO.builder()
                .studyId(studyGame.getId())
                .quizScript(studyGame.getQuestionText())
                .wordList(wordList)
                .build();
    }


    public Map<String, Object> startGame(Long userId) {
        User user = getUserFromRequest(userId);
        logger.info("StartGame User: " + user);
        WordMean selectedWordMean = gameWordService.getRandomUnusedWordMean(user.getId());
        StudyGame studyGame = getOrCreateStudyGame(selectedWordMean);
        StudyGameDTO studyGameDTO = createUserStudyLogRequestDTO(user, studyGame, selectedWordMean);
        Map<String, Object> response = new HashMap<>();
        response.put("data",studyGameDTO);
        return response;
    }
    @Transactional
    public Map<String, Object> submitGame(Long userId, Long studyId, Long answer) {
        User user = getUserFromRequest(userId);
        logger.info("submitGame User: " + user);
        if (studyId == null) {
            throw new RestApiException(CustomStudyErrorCode.STUDYID_IS_NULL);
        }
        StudyGame studyGame = studyGameRepository.findById(studyId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.RESOURCE_NOT_FOUND));
        logger.info("submitGame StudyGame: " + studyGame.getId());
        WordMean wordMean = studyGame.getWordMean();
        Map<String, Object> response = new HashMap<>();
        Boolean isAnswer = false;
        List<Problem> problems = studyGame.getQuestion().getProblems();
        if (answer >= 0 && answer < problems.size()) {
            String correctWord = studyGame.getWordMean().getWord().getWord();
            String selectedAnswerWord = problems.get(answer.intValue()).getBasic_type();
            if (correctWord.equals(selectedAnswerWord)) {
                isAnswer = true;
            }
        } else {
            logger.info("Invalid answer index: " + answer);
        }
        response.put("data", isAnswer);
        // 중복 데이터 확인 및 업데이트 로직
        Optional<StudyGameLog> existingLogOpt = studyGameLogRepository.findByStudyGameAndUserForUpdate(userId, studyId);
        logger.info("existingLogOpt: "+existingLogOpt);
        if (existingLogOpt.isPresent()) {
            StudyGameLog existingLog = existingLogOpt.get();
            existingLog.setResult(isAnswer);
            studyGameLogRepository.save(existingLog);  // 기존 레코드를 업데이트
            logger.info("Updated existing StudyGameLog: " + existingLog);
        } else {
            // StudyGameLog 기록 저장
            StudyGameLog newLog = new StudyGameLog();
            newLog.setUser(user);
            newLog.setStudyGame(studyGame);
            newLog.setWordMean(studyGame.getWordMean());
            newLog.setResult(isAnswer);
            studyGameLogRepository.save(newLog);  // 새로운 레코드를 저장
            logger.info("Created new StudyGameLog: " + newLog);
        }
        return response;
    }

    public Optional<Problem> findProblemByWord(StudyGame studyGame, String word) {
        return studyGame.getQuestion().getProblems().stream()
                .filter(problem -> word.equals(problem.getBasic_type()))
                .findFirst();
    }

    public Map<String, Object> showResultGame(Long userId, Long studyId) {
        User user = getUserFromRequest(userId);
        logger.info("showResultGame User: " + user);
        if (studyId == null) {
            throw new RestApiException(CustomStudyErrorCode.STUDYID_IS_NULL);
        }
        StudyGame studyGame = studyGameRepository.findById(studyId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.RESOURCE_NOT_FOUND));
        logger.info("showResultGame StudyGame: " + studyGame);
        List<Map<String, String>> wordList = new ArrayList<>();
        studyGame.getQuestion().getProblems().stream()
                .sorted(Comparator.comparingInt(Problem::getIdx))
                .forEach(problem -> {
                    Map<String, String> wordMap = new HashMap<>();
                    wordMap.put(problem.getObtion(), problem.getMean());
                    wordList.add(wordMap);
                });
        // 특정 조건을 만족하는 Problem 객체의 idx 값을 찾기
        System.out.printf("111erer"+studyGame.getWordMean().getWord().getWord());
        Optional<Problem> answerOpt = studyGame.getQuestion().getProblems().stream()
                .filter(problem -> problem.getBasic_type().equals(studyGame.getWordMean().getWord().getWord()))
                .findFirst();
        Problem answer = answerOpt.orElse(null);
        Map<String, Object> response = new HashMap<>();
        // 유저 게임 기록 확인
        Optional<StudyGameLog> existingLogOpt = studyGameLogRepository.findByStudyGameAndUserForUpdate(userId, studyId);
        logger.info("showResultGame log: "+existingLogOpt);
        if (existingLogOpt.isPresent() && answerOpt.isPresent()) {
            StudyGameLog existingLog = existingLogOpt.get();
            UserStudyLogResponseDTO dto = UserStudyLogResponseDTO.builder()
                    .quizScript(studyGame.getQuestionText())
                    .result(existingLog.isResult())
                    .wordList(wordList)
                    .systemAnswer(answer.getIdx())
                    .build();
            response.put("data", dto);
        } else {
            throw new RestApiException(CustomErrorCode.RESOURCE_NOT_FOUND);
        }
        return response;
    }
}
