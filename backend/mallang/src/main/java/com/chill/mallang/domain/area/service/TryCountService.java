package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.APIresponse;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.user.dto.TryCountDTO;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.errors.exception.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TryCountService {
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private UserRepository userRepository;

    // 점령지 상세정보 2. 사용자 잔여 도전 횟수 조회
    public Map<String,Object> getUserTryCountById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {

            TryCountDTO tryCountInfo = TryCountDTO.builder()
                    .try_count(user.get().getTry_count())
                    .build();

            return new HashMap<>(){{
                put("data",tryCountInfo);
            }};
        } else {
            throw new RestApiException(AreaErrorCode.INVALID_PARAMETER);
        }
    }
}
