package com.sparta.eduwithme.domain.question;

import com.sparta.eduwithme.domain.question.entity.LearningStatus;
import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.question.entity.QuestionType;
import com.sparta.eduwithme.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LearningStatusRepository extends JpaRepository<LearningStatus, Long> {
    Page<LearningStatus> findByUserAndQuestionType(User user, QuestionType questionType, Pageable pageable);

    Optional<LearningStatus> findByQuestionAndUser(Question question, User user);
}