package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.APIresponse;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.user.dto.TryCountDTO;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private UserRepository userRepository;

    // 점령지 상세정보 2. 사용자 잔여 도전 횟수 조회
    public APIresponse<TryCountDTO> getUserTryCountById(Long id){
        Optional<User> user = userRepository.findById(id);
        TryCountDTO data = user.map(this::convertToCountDto).orElse(null);
        return APIresponse.<TryCountDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .build();
    }

    private TryCountDTO convertToCountDto(User user) {
        return TryCountDTO.builder()
                .try_count(user.getTry_count())
                .build();
    }
}
