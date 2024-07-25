package com.sparta.eduwithme.domain.question;

import com.sparta.eduwithme.domain.question.entity.LearningStatus;
import com.sparta.eduwithme.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningStatusRepository extends JpaRepository<LearningStatus, Long> {
    List<LearningStatus> findByUser(User user);
}