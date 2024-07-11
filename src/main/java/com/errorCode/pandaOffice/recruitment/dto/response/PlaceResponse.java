package com.errorCode.pandaOffice.recruitment.dto.response;

import com.errorCode.pandaOffice.recruitment.domain.entity.Place;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceResponse {

    /* 장소 코드 */
    private final int id;

    /* 장소 이름 */
    private final String name;

    public static PlaceResponse from(final Place place) {
        return new PlaceResponse(
                place.getId(),
                place.getName()
        );
    };
}
