package com.sparta.eduwithme.domain.question;

import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByRoom(Room room, Pageable pageable);
}
