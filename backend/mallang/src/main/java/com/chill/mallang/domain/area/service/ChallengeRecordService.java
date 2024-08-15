package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.ChallengeRecordDTO;
import com.chill.mallang.domain.area.dto.ResponseTotalTeamInfo;
import com.chill.mallang.domain.area.dto.ResponseTotalUserInfo;
import com.chill.mallang.domain.faction.model.FactionType;
import com.chill.mallang.domain.quiz.model.Answer;
import com.chill.mallang.domain.quiz.model.TotalScore;
import com.chill.mallang.domain.quiz.repository.AnswerRepository;
import com.chill.mallang.domain.quiz.repository.TotalScoreRepository;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeRecordService {
    private static final Logger logger = LoggerFactory.getLogger(ChallengeRecordService.class);

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final TotalScoreRepository totalScoreRepository;

    public Map<String, Object> getChallengeRecord(Long areaId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Long myteamId = user.get().getFaction().getId();
        Long oppoteamId = (myteamId == 1) ? 2L : 1L;

        List<TotalScore> myteamtotalScores =  totalScoreRepository.findByAreaAndFactionWithHighestScore(areaId,myteamId);
        List<TotalScore> oppoteamtotalScores =  totalScoreRepository.findByAreaAndFactionWithHighestScore(areaId,oppoteamId);

        if (user.isEmpty()) {
            throw new RestApiException(AreaErrorCode.INVALID_PARAMETER);
        }

        // totalScores 순회하면서 dto에 넣고 리스트에 담기
        // 그러면서 userid랑 같은 값 있으면 userplace 저장해두기. 플레이타임은 어떻게 앎?

        //아군
        ArrayList<ResponseTotalTeamInfo> myTeamRecords = new ArrayList<>();
        int userPlace = 0;
        float userScore = 0;

        for (int i = 0; i<myteamtotalScores.size(); i++) {
            if (userId == myteamtotalScores.get(i).getUser().getId()) {
                userPlace = i+1;
                userScore = myteamtotalScores.get(i).getTotalScore();
            }
            ResponseTotalTeamInfo totalTeamInfo = ResponseTotalTeamInfo.builder()
                    .userPlace(i+1)
                    .userId(myteamtotalScores.get(i).getUser().getId())
                    .userName(myteamtotalScores.get(i).getUser().getNickname())
                    .userScore(myteamtotalScores.get(i).getTotalScore())
                    .userPlayTime(null) // total_score에서 확인할 방법 없음
                    .build();

            myTeamRecords.add(totalTeamInfo);
        }

        // 적군
        ArrayList<ResponseTotalTeamInfo> oppoTeamRecords = new ArrayList<>();
        for (int i = 0; i<oppoteamtotalScores.size(); i++) {
            ResponseTotalTeamInfo totalTeamInfo = ResponseTotalTeamInfo.builder()
                    .userPlace(i+1)
                    .userId(oppoteamtotalScores.get(i).getUser().getId())
                    .userName(oppoteamtotalScores.get(i).getUser().getNickname())
                    .userScore(oppoteamtotalScores.get(i).getTotalScore())
                    .userPlayTime(null) // total_score에서 확인할 방법 없음
                    .build();

            oppoTeamRecords.add(totalTeamInfo);
        }

        ResponseTotalUserInfo userRecord = null;
        if (userPlace != 0) {
            userRecord = ResponseTotalUserInfo.builder()
                    .userScore(userScore)
                    .userPlayTime(null)
                    .userPlace(userPlace)
                    .build();
        }

        ChallengeRecordDTO challengeRecordInfo = ChallengeRecordDTO.builder()
                .userRecord(userRecord != null ? userRecord : null)
                .myTeamRecords(myTeamRecords)
                .oppoTeamRecords(oppoTeamRecords)
                .build();

        return new HashMap<>(){{
            put("data",challengeRecordInfo);
        }};
    }
}
