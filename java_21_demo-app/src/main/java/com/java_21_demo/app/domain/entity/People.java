package com.java_21_demo.app.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.java_21_demo.app.enums.ColorEnum;
import com.java_21_demo.database.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@TableName("t_people")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString(callSuper = true)
public class People extends BaseEntity {
    @TableId
    Long id;
    String name;
    String email;

    @TableField("color_id")
    ColorEnum favoriteColorEnum;
}
