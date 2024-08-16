package com.sparta.eduwithme.domain.room.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.eduwithme.domain.room.dto.RoomWithNickNameDto;
import com.sparta.eduwithme.domain.room.entity.QRoom;
import com.sparta.eduwithme.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;

public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public RoomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<RoomWithNickNameDto> getRoomListWithPage(Pageable pageable) {
        QRoom room = QRoom.room;
        QUser user = QUser.user;

        List<RoomWithNickNameDto> content = queryFactory
            .select(Projections.constructor(RoomWithNickNameDto.class,
                room.id,
                room.roomName,
                room.roomPassword,
                room.managerUserId,
                user.nickName))
            .from(room)
            .leftJoin(user).on(room.managerUserId.eq(user.id))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory
            .selectFrom(room)
            .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }
}