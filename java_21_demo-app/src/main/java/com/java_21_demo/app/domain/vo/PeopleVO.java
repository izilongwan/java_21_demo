package com.java_21_demo.app.domain.vo;

import com.java_21_demo.app.domain.entity.People;
import com.java_21_demo.app.enums.ColorEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
public class PeopleVO extends People {
    ColorEnum favoriteColor;

    Integer colorId;
}
