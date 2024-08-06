package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.StudyGameLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGameLogRepository extends JpaRepository<StudyGameLog, Long> {

}
