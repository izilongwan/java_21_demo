package com.java_21_demo.app.mapper;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PeopleMapperTest {
    // Assuming you have a PeopleMapper bean injected here
    @Resource
    PeopleMapper peopleMapper;

    @Test
    void testSelectList() {
        peopleMapper.selectList(null).forEach(System.out::println);
    }
}
