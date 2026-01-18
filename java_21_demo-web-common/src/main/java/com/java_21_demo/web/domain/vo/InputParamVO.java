package com.java_21_demo.web.domain.vo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JacksonXmlRootElement
public class InputParamVO {
    String field;
    String rule;
    String message;
}
