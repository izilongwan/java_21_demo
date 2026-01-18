package com.java_21_demo.app.controller;

import java.util.List;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esotericsoftware.minlog.Log;
import com.java_21_demo.app.aspectj.DoAspecj;
import com.java_21_demo.app.aspectj.DosAspectj;
import com.java_21_demo.app.domain.entity.People;
import com.java_21_demo.app.domain.vo.PeopleVO;
import com.java_21_demo.app.mapper.PeopleMapper;
import com.java_21_demo.app.mapper.PeopleVOMapper;
import com.java_21_demo.app.property.DoProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/do")
@RequiredArgsConstructor
@Slf4j
public class DoController {
    private final DoProperty doProperty;
    private final Environment environment;
    private final PeopleVOMapper peopleVOMapper;
    private final PeopleMapper peopleMapper;

    @GetMapping("/hello")
    @DoAspecj("Get hello message")
    public String hello() {
        return "Hello from DoController";
    }

    @GetMapping("/info")
    @DoAspecj("Get DoProperty info")
    @DoAspecj("Another DoAspecj annotation")
    public DoProperty info() {
        DoProperty doPropertyObject = new DoProperty();
        BeanUtils.copyProperties(doProperty, doPropertyObject);
        return doPropertyObject;
    }

    @DosAspectj({
            @DoAspecj("Get environment info"),
            @DoAspecj("Environment endpoint in DoController")
    })
    @GetMapping("/env")
    public String env() {
        return "DoProperty toString: " + doProperty +
                "\nInfo: " + doProperty.getInfo() +
                "\nGirls: " + (doProperty.getGirls() != null ? doProperty.getGirls().length : "null") +
                "\nEnv property.do.info.name: " + environment.getProperty("property.do.info.name");
    }

    @GetMapping("peopleVO/list")
    public List<PeopleVO> peopleVOList() {
        return peopleVOMapper.selectList();
    }

    @GetMapping("peopleVO/{id}")
    public PeopleVO peopleVOOne(@Valid @Positive @Range(min = 1, max = 100) @PathVariable("id") Long id) {
        return peopleVOMapper.selectById(id);
    }

    @GetMapping("people/list")
    public List<People> peopleList() {
        return peopleMapper.selectList(null);
    }

    @GetMapping("people/{id}")
    public People peopleOne(
            @Valid @Positive @Range(min = 1, max = 100, message = "ID must be between 1 and 100") @PathVariable("id") Long id) {
        return peopleMapper.selectById(id);
    }

}
