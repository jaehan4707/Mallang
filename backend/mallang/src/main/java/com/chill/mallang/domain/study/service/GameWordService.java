package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.dto.core.WordDTO;
import com.chill.mallang.domain.study.dto.core.WordMeanDTO;
import com.chill.mallang.domain.study.errors.CustomStudyErrorCode;
import com.chill.mallang.domain.study.model.WordMean;
import com.chill.mallang.domain.study.repository.WordMeanRepository;
import com.chill.mallang.errors.exception.RestApiException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GameWordService {
    private static final Logger logger = LoggerFactory.getLogger(GameWordService.class);
    private final WordMeanRepository wordMeanRepository;

    @Autowired
    public GameWordService(WordMeanRepository wordMeanRepository) {
        this.wordMeanRepository = wordMeanRepository;
    }

    public WordMean getRandomUnusedWordMean(Long userId) {
        Optional<WordMean> unusedWordMean = wordMeanRepository.findUnusedWordMeansByUserId(userId);
        logger.info(unusedWordMean.toString());
        if (unusedWordMean.isPresent()) {
            WordMean wordMean = unusedWordMean.get();
            return wordMean;
        }
        throw new RestApiException(CustomStudyErrorCode.WORD_IS_SOLD_OUT);
    }

    @Transactional
    public WordMeanDTO convertToDTO(WordMean wordMean) {
        WordDTO wordDTO = WordDTO.builder()
                .id(wordMean.getWord().getId())
                .word(wordMean.getWord().getWord())
                .build();
        WordMeanDTO wordMeanDTO = WordMeanDTO.builder()
                .id(wordMean.getId())
                .mean(wordMean.getMean())
                .type(wordMean.getType())
                .level(wordMean.getLevel())
                .word(wordDTO)
                .build();
        return wordMeanDTO;
    }

    @Transactional
    public WordMeanDTO getWordMeanDTOById(Long wordMeanId) {
        WordMean wordMean = wordMeanRepository.findById(wordMeanId)
                .orElseThrow(() -> new RestApiException(CustomStudyErrorCode.WORDMEAN_IS_NOT_FOUND));
        return convertToDTO(wordMean);
    }
}
