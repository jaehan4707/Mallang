package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    // 오답 단어 3개 찾아서 리스트에 넣기
    @Query(value = "SELECT option From problem WHERE question = :questionId",nativeQuery = true)
    List<String> findWordListByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT p FROM Problem p JOIN p.question q JOIN q.studyGame s WHERE s.id = :studyId")
    List<Problem> findWordListByStudentId(@Param("studyId") Long studyId);
}