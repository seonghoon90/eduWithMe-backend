package com.sparta.eduwithme.domain.question.repository;

import com.sparta.eduwithme.domain.question.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Modifying
    @Query("DELETE FROM Answer a WHERE a.question.id = :questionId")
    void deleteByQuestionId(Long questionId);
}
