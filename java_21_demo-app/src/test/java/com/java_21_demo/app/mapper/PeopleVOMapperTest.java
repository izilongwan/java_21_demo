package com.java_21_demo.app.mapper;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PeopleVOMapperTest {
    // Assuming you have a PeopleVOMapper bean injected here
    @Autowired
    PeopleVOMapper peopleVOMapper;

    @Test
    void testSelectById() {
        Optional.ofNullable(peopleVOMapper.selectById(1L)).ifPresent(System.out::println);
    }

    @Test
    void testSelectList() {
        peopleVOMapper.selectList().forEach(System.out::println);
    }
}
