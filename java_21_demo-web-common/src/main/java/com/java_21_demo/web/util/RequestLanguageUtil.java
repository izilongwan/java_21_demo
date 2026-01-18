package com.java_21_demo.web.util;

import java.util.Objects;

import org.springframework.http.HttpHeaders;

public class RequestLanguageUtil {

    public enum Language {
        CN("中文"),
        EN("英文"),
        JP("日文"),
        KR("韩文"),
        FR("法语"),
        ;

        String lang;

        private Language(String lang) {
            this.lang = lang;
        }
    }

    public static String getHeader(String name) {
        String value = HttpRequestUtil.getRequest().map(o -> o.getHeader(name)).orElse(null);
        return getHeaderLanguage(value);
    }

    public static String getHeaderLanguage() {
        return getHeader(HttpHeaders.ACCEPT_LANGUAGE);
    }

    public static String getHeaderLanguage(String lang) {
        if (Objects.isNull(lang)) {
            return Language.EN.name();
        }

        // zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
        switch (lang) {
            // 不同地区
            case "zh-CN":
            case "zh-TW":
            case "zh-HK":
            case "zh":
                return Language.CN.name();

            // 不同地区
            case "en-US":
            case "en":
                return Language.EN.name();

            case "ja":
                return Language.JP.name();

            case "ko":
                return Language.KR.name();

            case "fr":
                return Language.FR.name();

            default:
                return Language.EN.name();
        }
    }

}
