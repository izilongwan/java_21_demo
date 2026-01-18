package com.java_21_demo.web.domain.vo;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.java_21_demo.web.enums.MessageEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement
public class ExceptionVO extends RuntimeException {
    RVO<Object> reason;

    HttpStatus status;

    public ExceptionVO(RVO<Object> reason) {
        super(reason.getMessage());
        this.reason = reason;
    }

    public ExceptionVO(String message, HttpStatus status) {
        super(message);
        this.reason = RVO.error(null, message);
        this.status = status;
    }

    public ExceptionVO(String message, HttpStatus status, int code) {
        super(message);
        this.reason = RVO.error(null, message, code);
        this.status = status;
    }

    public static ExceptionVO error(Object data, String message, int code) {
        return new ExceptionVO(RVO.error(data, message, code));
    }

    public static ExceptionVO error(Object data, String message) {
        return error(data, message, MessageEnum.ERROR.getCode());
    }

    public static ExceptionVO error(String message) {
        return error(null, message);
    }

    public static ExceptionVO error(Object data, MessageEnum messageEnum) {
        return new ExceptionVO(RVO.error(data, messageEnum));
    }

    public static ExceptionVO error(MessageEnum messageEnum) {
        return error(null, messageEnum);
    }
}
