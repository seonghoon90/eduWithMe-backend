package com.sparta.eduwithme.domain.room.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.eduwithme.domain.room.dto.RoomWithNickNameDto;
import com.sparta.eduwithme.domain.room.entity.QRoom;
import com.sparta.eduwithme.domain.room.entity.Room;
import com.sparta.eduwithme.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

//    @Override
//    public List<Room> getRoomListWithPage(long page, int pageSize) {
//        QRoom room = QRoom.room;
//
//        return jpaQueryFactory.selectFrom(room)
//                .offset(page)
//                .limit(pageSize)
//                .fetch();
//    }

    @Override
    public List<RoomWithNickNameDto> getRoomListWithPage(long page, int pageSize) {
        QRoom room = QRoom.room;
        QUser user = QUser.user;

        return jpaQueryFactory
                .select(Projections.constructor(
                        RoomWithNickNameDto.class,
                        room.id,
                        room.roomName,
                        room.roomPassword,
                        room.managerUserId,
                        user.nickName))
                .from(room)
                .leftJoin(user).on(room.managerUserId.eq(user.id))
                .offset(page)
                .limit(pageSize)
                .fetch();
    }

}