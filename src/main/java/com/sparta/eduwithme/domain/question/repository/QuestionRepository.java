package com.sparta.eduwithme.domain.question.repository;

import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByRoom(Room room, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.room = :room AND LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Question> findByRoomAndTitleContainingIgnoreCase(@Param("room") Room room, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT MAX(q.orderInRoom) FROM Question q WHERE q.room.id = :roomId")
    Long findMaxOrderInRoom(@Param("roomId") Long roomId);
}
