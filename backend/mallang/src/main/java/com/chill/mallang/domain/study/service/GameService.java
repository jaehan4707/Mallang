package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.dto.StudyGameDTO;
import com.chill.mallang.domain.study.dto.UserStudyLogResponseDTO;
import com.chill.mallang.domain.study.dto.core.WordMeanDTO;
import com.chill.mallang.domain.study.errors.CustomStudyErrorCode;
import com.chill.mallang.domain.study.model.*;
import com.chill.mallang.domain.study.repository.ProblemRepository;
import com.chill.mallang.domain.study.repository.StudyGameLogRepository;
import com.chill.mallang.domain.study.repository.StudyGameRepository;
import com.chill.mallang.domain.study.repository.WordMeanRepository;
import com.chill.mallang.domain.user.errors.CustomUserErrorCode;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.domain.user.service.UserSettingService;
import com.chill.mallang.errors.errorcode.CustomErrorCode;
import com.chill.mallang.errors.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {
    private static final Logger logger = LoggerFactory.getLogger(UserSettingService.class);
    private final StudyGameLogRepository studyGameLogRepository;
    private final GameWordService gameWordService;
    private final StudyGameRepository studyGameRepository;
    private final CreateGameService createGameService;
    private final UserRepository userRepository;
    private final WordMeanRepository wordMeanRepository;
    private final ProblemRepository problemRepository;
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

        StudyGame studyGame = createGameService.initializeStudyGame(wordMean);
        return studyGameRepository.save(studyGame);
    }

    // 단어 뜻 관련 게임 조회 및 없으면 생성 후 return
    private StudyGame getOrCreateStudyGame(WordMean wordMean) {
        StudyGame existingStudyGame = studyGameRepository.findByWordMeanId(wordMean.getId());
        logger.info("existingStudyGame"+existingStudyGame);
        if (existingStudyGame != null) {
            return existingStudyGame;
        }
        StudyGame newStudyGame = createStudyGameWithWordMean(wordMean.getId());
        return newStudyGame;
    }

    private StudyGameDTO createUserStudyLogRequestDTO(User user, StudyGame studyGame, WordMean wordMean) {
        WordMeanDTO wordMeanDTO = gameWordService.convertToDTO(wordMean);
        List<Map<String, String>> wordList = new ArrayList<>();
        List<Problem> problemList = problemRepository.findWordListByStudentId(studyGame.getId());
        problemList.stream()
                .sorted(Comparator.comparingInt(Problem::getIdx))
                .map(problem -> {
                    Map<String, String> wordMap = new HashMap<>();
                    wordMap.put("word", problem.getOption());
                    return wordMap;
                })
                .forEach(wordList::add);
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
        studyGameDTO.setQuizTitle("빈칸을 채워 주세요");
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
        WordMean wordMean = studyGame.getWordMean();
        Map<String, Object> response = new HashMap<>();
        Boolean isAnswer = false;
        List<Problem> problems = problemRepository.findWordListByStudentId(studyId);
        Problem selectedAnswerWord = problems.stream()
                .filter(problem -> problem.getIdx() == answer)
                .findFirst().orElseThrow(()->new RestApiException(CustomErrorCode.RESOURCE_NOT_FOUND));
        String selectedAnswer = selectedAnswerWord.getBasic_type();
        if (answer >= 0 && answer < problems.size()) {
            String correctWord = studyGame.getWordMean().getWord().getWord();
            if (correctWord.equals(selectedAnswer)) {
                isAnswer = true;
            }
        } else {
            logger.info("Invalid answer index: " + answer);
        }
        response.put("data", isAnswer);
        // StudyGameLog 기록 저장
        StudyGameLog newLog = StudyGameLog.builder()
                .user(user)
                .studyGame(studyGame)
                .wordMean(studyGame.getWordMean())
                .created_at(LocalDateTime.now())
                .result(isAnswer)
                .build();
        studyGameLogRepository.save(newLog);  // 새로운 레코드를 저장
        logger.info("Created new StudyGameLog: " + newLog);
        return response;
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
        // wordList 생성
        List<Map<String, String>> wordList = problemRepository.findWordListByStudentId(studyId).stream()
                .sorted(Comparator.comparingInt(Problem::getIdx)) // 문제들을 idx 값 기준으로 정렬
                .map(problem -> {
                    Map<String, String> wordMap = new HashMap<>();
                    wordMap.put("word", problem.getBasic_type()); // assuming 'word' refers to 'basic_type'
                    wordMap.put("meaning", problem.getMean());
                    return wordMap;
                }) // 정렬된 문제들을 wordList에 추가
                .collect(Collectors.toList());
        // 특정 조건을 만족하는 Problem 객체의 idx 값을 찾기
        Optional<Problem> answerOpt = problemRepository.findWordListByStudentId(studyId).stream()
                .filter(problem -> problem.getBasic_type().equals(studyGame.getWordMean().getWord().getWord()))
                .findFirst();
        Problem answer = answerOpt.orElse(null);
        Map<String, Object> response = new HashMap<>();
        // 유저 게임 기록 확인
        Optional<StudyGameLog> existingLogOpt = studyGameLogRepository.findByStudyGameAndUserForLastResult(userId, studyId);
        logger.info("showResultGame log: "+existingLogOpt);
        if (existingLogOpt.isPresent() && answerOpt.isPresent()) {
            StudyGameLog existingLog = existingLogOpt.get();
            UserStudyLogResponseDTO dto = UserStudyLogResponseDTO.builder()
                    .quizTitle("빈칸을 채워 주세요")
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
