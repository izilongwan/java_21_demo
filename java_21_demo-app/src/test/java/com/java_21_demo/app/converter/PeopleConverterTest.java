package com.java_21_demo.app.converter;

import org.junit.jupiter.api.Test;

import com.java_21_demo.app.domain.entity.People;
import com.java_21_demo.app.domain.vo.PeopleVO;
import com.java_21_demo.app.enums.ColorEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PeopleConverterTest {
    @Test
    void testToPeople() {
        PeopleVO peopleVO = PeopleVO.builder()
                .id(1L)
                .name("John Doe")
                .favoriteColor(ColorEnum.RED)
                .build();

        People people = PeopleConverter.INSTANCE.toPeople(peopleVO);
        log.info("Converted People: {}", people);
    }

    @Test
    void testToVPeopleVO() {
        People people = People.builder()
                .id(1L)
                .name("John Doe")
                .favoriteColorEnum(ColorEnum.BLACK)
                .build();

        PeopleVO peopleVO = PeopleConverter.INSTANCE.toVPeopleVO(people);
        log.info("Converted PeopleVO: {}", peopleVO);
    }
}
