package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.dto.UserStudyLogRequestDTO;
import com.chill.mallang.domain.study.errors.CustomStudyErrorCode;
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
    private User getUserFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String email = jwtUtil.extractEmail(token.substring(7));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public StudyGame createStudyGameWithWordMean(Long wordMeanId) {
        WordMean wordMean = wordMeanRepository.findById(wordMeanId)
                .orElseThrow(() ->  new RestApiException(CustomStudyErrorCode.WORDMEAN_IS_NOT_FOUND));

        StudyGame studyGame = new StudyGame();
        String question = "What is the meaning of this word?";
        studyGame.setWordMean(wordMean);
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

    private UserStudyLogRequestDTO createUserStudyLogRequestDTO(User user, StudyGame studyGame, WordMean wordMean) {
        return UserStudyLogRequestDTO.builder()
                .userId(user.getId())
                .studyGame(studyGame)
                .wordMeanId(wordMean.getId())
                .result(false)
                .build();
    }


    public Map<String, Object> StartGame(HttpServletRequest request) {
        if (!request.getMethod().equalsIgnoreCase("GET")) {
            throw new RestApiException(CustomUserErrorCode.METHOD_NOT_ALLOWED);
        }

        User user = getUserFromRequest(request);
        logger.info("StartGame User: " + user);

        WordMean selectedWordMean = gameWordService.getRandomUnusedWordMean(user.getId());
        StudyGame studyGame = getOrCreateStudyGame(selectedWordMean);
        UserStudyLogRequestDTO userStudyLogRequestDTO = createUserStudyLogRequestDTO(user, studyGame, selectedWordMean);
        Map<String, Object> response = new HashMap<>();
        response.put("data", userStudyLogRequestDTO);
        return response;
    }
}
