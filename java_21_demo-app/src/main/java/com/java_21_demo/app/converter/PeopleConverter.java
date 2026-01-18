package com.java_21_demo.app.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.java_21_demo.app.domain.entity.People;
import com.java_21_demo.app.domain.vo.PeopleVO;
import com.java_21_demo.app.enums.ColorEnum;

@Mapper(componentModel = "spring")
public interface PeopleConverter {
    PeopleConverter INSTANCE = Mappers.getMapper(PeopleConverter.class);

    @Mapping(target = "favoriteColor", source = "favoriteColorEnum")
    @Mapping(target = "colorId", source = "favoriteColorEnum", qualifiedByName = "mapColorEnumToId")
    PeopleVO toVPeopleVO(People people);

    @Mapping(target = "favoriteColorEnum", source = "favoriteColor")
    People toPeople(PeopleVO peopleVO);

    @Named("mapColorEnumToId")
    default Integer mapColorEnumToId(ColorEnum colorEnum) {
        return colorEnum != null ? colorEnum.getId() : null;
    }

    @Named("mapIdToColorEnum")
    default ColorEnum mapIdToColorEnum(Integer id) {
        if (id == null) {
            return null;
        }
        for (ColorEnum color : ColorEnum.values()) {
            if (color.getId() == id) {
                return color;
            }
        }
        return null;
    }
}
