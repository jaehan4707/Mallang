package com.chill.mallang.domain.quiz.controller.v1;

import com.chill.mallang.domain.quiz.dto.ResponseWrapper;
import com.chill.mallang.domain.quiz.dto.request.RequestQuizAnswer;
import com.chill.mallang.domain.quiz.dto.request.RequestQuizResult;
import com.chill.mallang.domain.quiz.dto.response.ResponseQuiz;
import com.chill.mallang.domain.quiz.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
@Tag(name = "Quiz API", description = "퀴즈 관련 API")
public class QuizController {
    private final QuizService quizService;

    @Operation(summary = "퀴즈 상제 정보 조회", description = "특정 퀴즈에 대한 상제 정보를 조회합니다.")
    @GetMapping("/{quizID}")
    public ResponseEntity<ResponseWrapper<ResponseQuiz>> getQuiz(@PathVariable Long quizID) {
        return ResponseEntity.ok().body(quizService.getById(quizID));
    }

    @Operation(summary = "퀴즈 정답 제출", description = "특정 퀴즈에 대한 정답을 제출합니다.")
    @PostMapping("/submit")
    public ResponseEntity<String> postQuizAnswer(@RequestBody @Valid RequestQuizAnswer requestQuizAnswer) {
        quizService.submitAnswer(requestQuizAnswer);
        return ResponseEntity.ok().body("정답 제출 완료");
    }

    @Operation(summary ="전체 결과 확인", description = "라운드 최종 결과를 확인합니다.")
    @PostMapping("/result")
    public ResponseEntity<?> getQuizResult(@RequestBody @Valid RequestQuizResult requestQuizResult) {
        return ResponseEntity.ok().body(quizService.quizResult(requestQuizResult));
    }

    @Operation(summary = "정답 확인", description = "특정 문제의 AI 기준 정답 확인")
    @GetMapping("/{quizID}/correct-answer")
    public ResponseEntity<?> getQuizAnswer(@PathVariable @NotNull Long quizID) {
        return null;
    }

    @Operation(summary ="점령 시작을 위한 Quiz 호출", description = "해당 점령지가 가지고 있는 Quiz PK 3개 조회")
    @GetMapping("/start/{areaID}")
    public ResponseEntity<?> startQuiz(@PathVariable Long areaID) {
        return ResponseEntity.ok().body(quizService.getAreaQuiz(areaID));
    }

}
