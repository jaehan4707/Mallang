package com.chill.mallang.domain.quiz.controller.v1;

import com.chill.mallang.domain.quiz.dto.request.RequestQuizAnswer;
import com.chill.mallang.domain.quiz.dto.request.RequestResult;
import com.chill.mallang.domain.quiz.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
@Tag(name = "Quiz API", description = "퀴즈 관련 API")
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @Operation(summary = "퀴즈 상제 정보 조회", description = "특정 퀴즈에 대한 상제 정보를 조회합니다.")
    @GetMapping("/{quizID}")
    public ResponseEntity<?> getQuiz(@PathVariable Long quizID) {
        return new ResponseEntity<>(quizService.getById(quizID), HttpStatus.OK);
    }

    @Operation(summary = "퀴즈 정답 제출", description = "특정 퀴즈에 대한 정답을 제출합니다.")
    @PostMapping("/submit")
    public ResponseEntity<?> postQuizAnswer(@RequestBody RequestQuizAnswer requestQuizAnswer) {
        quizService.submitAnswer(requestQuizAnswer);
        return new ResponseEntity<>("정답 제출 완료", HttpStatus.OK);
    }
    @Operation(summary ="전체 결과 확인", description = "라운드 최종 결과를 확인합니다.")
    @GetMapping("/result")
    public ResponseEntity<?> getQuizResult(@RequestBody RequestResult requestResult) {
        return new ResponseEntity<>("정답을 조회합니다.", HttpStatus.OK);
    }
    @Operation(summary = "정답 확인", description = "특정 문제의 AI 기준 정답 확인")
    @GetMapping("/{quizID}/corret-answer")
    public ResponseEntity<?> getQuizAnswer(@PathVariable Long quizID) {
        return null;
    }
    
}
