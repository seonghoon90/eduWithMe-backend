package com.sparta.eduwithme.domain.question;

import com.sparta.eduwithme.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
