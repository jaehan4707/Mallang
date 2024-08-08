package com.chill.mallang.domain.study.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonBackReference
    private StudyGame studyGame;

//    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Problem> problems = new ArrayList<>();
//
//    public void addProblem(Problem problem) {
//        problems.add(problem);
//        problem.setQuestion(this);
//    }
//
//    public void setProblems(List<Problem> problems) {
//        this.problems.clear();
//        if (problems != null) {
//            problems.forEach(this::addProblem);
//        }
//    }

}
