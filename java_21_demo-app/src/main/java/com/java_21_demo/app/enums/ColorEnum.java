package com.java_21_demo.app.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ColorEnum {
    RED("red", 1, "Red Color Description"),
    GREEN("green", 2, "Green Color Description"),
    BLUE("blue", 3, "Blue Color Description"),
    YELLOW("yellow", 4, "Yellow Color Description"),
    BLACK("black", 5, "Black Color Description"),
    WHITE("white", 6, "White Color Description");

    String value;
    @EnumValue
    int id;
    @JsonValue
    String desc;
}
