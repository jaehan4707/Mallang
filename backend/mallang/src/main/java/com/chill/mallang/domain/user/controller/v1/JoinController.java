package com.chill.mallang.domain.user.controller.v1;

import com.chill.mallang.domain.user.dto.JoinRequestDTO;
import com.chill.mallang.domain.user.dto.JoinResponseDTO;
import com.chill.mallang.domain.user.jwt.JoinFilter;
import com.chill.mallang.domain.user.service.JoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("api/v1/user")
public class JoinController {
    private static final Logger logger = LoggerFactory.getLogger(JoinController.class);
    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }
    @PostMapping("/join")
    public ResponseEntity<JoinResponseDTO> joinProcess(@RequestBody JoinRequestDTO joinRequestDTO) {
        logger.info("joindto 리퀘 :"+joinRequestDTO);
        JoinResponseDTO joinResponseDTO = joinService.joinProcess(joinRequestDTO);
        logger.info("joindto :"+joinResponseDTO);
        return new ResponseEntity<>(joinResponseDTO, HttpStatus.CREATED);
    }

}
