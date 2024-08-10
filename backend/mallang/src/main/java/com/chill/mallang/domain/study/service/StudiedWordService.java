package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.dto.user.WordCardDto;
import com.chill.mallang.domain.study.model.StudyGameLog;
import com.chill.mallang.domain.study.repository.StudyGameLogRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudiedWordService {
    private static final Logger logger = LoggerFactory.getLogger(StudiedWordService.class);

    private final StudyGameLogRepository studyGameLogRepository;

    public Map<String, Object> getStudiedWord(Long userId){
        List<StudyGameLog> studyGameLogs = studyGameLogRepository.getStudyGameLogByUserId(userId);
        logger.info("userId : " + userId);
        logger.info("studyGameLogs : " + studyGameLogs.toString());

        ArrayList<WordCardDto> wordCards = new ArrayList<>();
        for (StudyGameLog studyGameLog : studyGameLogs) {
            WordCardDto wordCardDto = WordCardDto.builder()
                    .word(studyGameLog.getStudyGame().getWordMean().getWord().getWord())
                    .meaning(studyGameLog.getStudyGame().getWordMean().getMean())
                    .pos(studyGameLog.getStudyGame().getWordMean().getType())
                    .example(studyGameLog.getStudyGame().getWordMean().getExample()) // 예문 데이터 추가
                    .build();

            wordCards.add(wordCardDto);
        }

        return new HashMap<>(){{
            put("data",wordCards);
        }};
    }
}
