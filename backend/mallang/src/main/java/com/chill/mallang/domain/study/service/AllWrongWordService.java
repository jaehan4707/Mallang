package com.chill.mallang.domain.study.service;

import com.chill.mallang.domain.study.dto.user.WordCardDto;
import com.chill.mallang.domain.study.dto.user.WrongWordDto;
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
public class AllWrongWordService {

    private static final Logger logger = LoggerFactory.getLogger(StudiedWordService.class);

    @Autowired
    StudyGameLogRepository studyGameLogRepository;

    public Map<String, Object> getWrongWord(Long areaId){
        List<StudyGameLog> studyGameLogs = studyGameLogRepository.getWrongStudyGameLogByUserId(areaId);

        ArrayList<WrongWordDto> wrongWords = new ArrayList<>();
        for (StudyGameLog studyGameLog : studyGameLogs) {
            WrongWordDto wrongWord = WrongWordDto.builder()
                    .StudyId(studyGameLog.getStudyGame().getId())
                    .word(studyGameLog.getStudyGame().getQuestion())
                    .build();

            wrongWords.add(wrongWord);
        }


        return new HashMap<>(){{
            put("data",wrongWords);
        }};
    }
}
