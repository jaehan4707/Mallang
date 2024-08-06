package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.dto.user.WordCardDto;
import com.chill.mallang.domain.study.model.StudyGameLog;
import com.chill.mallang.domain.study.repository.StudyGameLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudiedWordService {
    private static final Logger logger = LoggerFactory.getLogger(StudiedWordService.class);

    @Autowired
    StudyGameLogRepository studyGameLogRepository;

    public Map<String, Object> getStudiedWord(Long areaId){
        List<StudyGameLog> studyGameLogs = studyGameLogRepository.getStudyGameLogByUserId(areaId);
        logger.info(studyGameLogs.toString());

        ArrayList<WordCardDto> wordCards = new ArrayList<>();
        for (StudyGameLog studyGameLog : studyGameLogs) {
            WordCardDto wordCardDto = WordCardDto.builder()
                    .word(studyGameLog.getStudyGame().getWordmean().getWord().getWord())
                    .meaning(studyGameLog.getStudyGame().getWordmean().getMean())
                    .example(null) // 아직 예문 데이터가 없음
                    .build();

            wordCards.add(wordCardDto);
        }

        return new HashMap<>(){{
            put("body",wordCards);
        }};
    }
}
