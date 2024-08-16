package com.sparta.eduwithme.domain.room.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedRoomResponse {
    private List<RoomWithNickNameDto> content;
    private int totalPages;
    private long totalElements;
}
