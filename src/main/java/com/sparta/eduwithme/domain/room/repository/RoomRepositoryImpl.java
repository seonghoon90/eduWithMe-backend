package com.sparta.eduwithme.domain.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.eduwithme.domain.room.entity.QRoom;
import com.sparta.eduwithme.domain.room.entity.Room;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Room> getRoomListWithPage(long page, int pageSize) {
        QRoom room = QRoom.room;

        return jpaQueryFactory.selectFrom(room)
                .offset(page)
                .limit(pageSize)
                .fetch();
    }
}
