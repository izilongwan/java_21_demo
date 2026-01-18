package com.java_21_demo.web.enums;

import java.lang.reflect.Field;

import com.java_21_demo.web.util.RequestLanguageUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum MessageEnum {
    SUCCESS(0, "成功", "ok", "成功です", "성공 실패", "Le succès l’échec"),
    ERROR(-1, "失败", "failed", "失敗です", "알 수", "Une"),
    UNKNOWN_ERROR(-2, "未知错误", "Unknown error", "未知の誤りです", "없는 오류", "erreur inconnue"),
    INPUT_INVALID_PARAM(-4, "参数校验错误", "Invalidate check param"),

    APICODE_IS_INVALID_OR_NOT_FOUND(1001, "ApiCode 无效或者未找到", "ApiCode is invalid or not found"),
    APICODE_CACHE_NOT_FOUND_OR_CLEAN_ERROR(1002, "ApiCode 缓存未找到或者清理失败", "ApiCode cache not found or clear error"),
    APICODE_QUERY_ERROR(1003, "ApiCode 查询失败", "ApiCode query error"),

    CAPTCHA_NOT_MATCH(2001, "验证码校验失败", "Captcha not match"),
    CAPTCHA_NOT_FOUND_OR_INVALID(2002, "验证码失效或不存在", "Captcha not found or invalid"),

    LOGIN_USERNAME_NOT_EXIST_OR_PASSWORD_NOT_MATCH(3001, "用户名不存在或密码不匹配", "Username password not match or not exist"),
    LOGIN_GITHUB_TOKEN_GET_USER_INFO_ERROR(3002, "[Github] token获取用户信息失败", "[Github] token get user info error"),
    LOGIN_USERNAME_IS_EMPTY(3003, "用户名为空", "Username is empty"),
    LOGIN_TYPE_NOT_FOUND(3004, "登录方式未找到", "Login type not found"),
    LOGIN_GITHUB_REGISTER_USER_ERROR(3005, "[Github] 注册用户失败", "[Github] Register user error"),
    LOGIN_GITHUB_GET_USER_INFO_WITH_CODE_ERROR(3006, "[Github] 令牌获取用户信息失败", "[Github] Get user info with code error"),
    LOGIN_GITHUB_CODE_IS_EMPTY(3007, "[Github] Code为空", "[Github] Code is empty"),

    TOKEN_HAS_EXPIRE(4001, "Token已过期", "Token has expire"),
    TOKEN_PARSE_ERROR(4002, "Token解析失败", "Token parse error"),
    TOKEN_IS_INVALID(4003, "Token无效", "Token is invalid"),

    TOKEN_IS_EMPTY(4004, "Token 为空", "Token is empty"),

    EXCEL_READ_TYPE_ERROR(5001, "Excel 读取[type]参数错误", "Excel read type error"),

    REQUEST_RESTRICTED(-10001, "请求被限流了", "The request is restricted", "", "", ""),
    REQUEST_DEMOTED(-10003, "请求被降级了！", "Request demoted", "", "", ""),
    TRAFFIC_LIMITING(-10004, "热点参数限流！", "Hotspot parameter traffic limiting", "", "", ""),
    REQUEST_DENY(-10005, "请求没有权限！", "Request without permission", "", "", "");

    final Integer code;
    final String messageCN;
    final String messageEN;
    String messageJP;
    String messageKR;
    String messageFR;

    public String getMessage(String langType) {
        try {
            String message = String.format("message%s", langType);
            Field field = MessageEnum.class.getDeclaredField(message);

            field.setAccessible(true);

            return (String) field.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messageEN;
    }

    public String getMessage() {
        return getMessage(RequestLanguageUtil.getHeaderLanguage());
    }
}
