package com.java_21_demo.app.domain.vo;

import com.java_21_demo.app.enums.ColorEnum;
import com.java_21_demo.web.aspect.anno.PrivacyEncrypt;
import com.java_21_demo.web.enums.PrivacyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeopleVO {
    Long id;
    String name;

    @PrivacyEncrypt(PrivacyType.EMAIL)
    String email;

    ColorEnum favoriteColorEnum;

    ColorEnum favoriteColor;

    Integer colorId;
}
