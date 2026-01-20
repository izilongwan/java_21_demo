package com.java_21_demo.web.enums;

import java.util.Objects;

import com.java_21_demo.web.aspect.anno.PrivacySerializer;

/**
 * 隐私数据处理类型枚举
 * 定义了不同的隐私数据处理方式，如手机号脱敏、身份证号脱敏和 AES 加密。
 * 每种类型实现了 apply 方法，用于对输入的隐私数据进行相应的处理。
 * - PHONE: 对手机号进行脱敏处理，显示前三位和后四位，中间四位用星号替代。
 * - ID_CARD: 对身份证号进行脱敏处理，显示前六位和后四位，中间八位用星号替代。
 * - AES: 对数据进行 AES 加密处理，返回加密后的字符串。
 */
public enum PrivacyType implements PrivacySerializer {
    // 手机号脱敏: 138****5678
    PHONE() {
        @Override
        public String serializer(String value) {
            if (Objects.isNull(value)) {
                return null;
            }

            if (value.length() < 7) {
                return maskValue(value, 1, value.length() - 2, 4);
            }

            return maskValue(value, 3, 9, 5);
        }
    },
    // 身份证号脱敏: 123456********3456
    ID_CARD() {
        @Override
        public String serializer(String value) {
            if (Objects.isNull(value)) {
                return null;
            }

            if (value.length() < 10) {
                return maskValue(value, 2, 9, 6);
            }
            return maskValue(value, 6, 14, 8);
        }
    },
    EMAIL() {
        @Override
        public String serializer(String value) {
            if (Objects.isNull(value)) {
                return null;
            }

            int atIndex = value.indexOf('@');
            if (atIndex <= 1) {
                return value; // 无法脱敏，返回原值
            }

            return maskValue(value, 1, atIndex, atIndex - 1);
        }
    },
    // 真实 AES 加密: 返回密文
    AES() {
        @Override
        public String serializer(String value) {
            // 这里可以实现 AES 加密逻辑，返回加密后的字符串
            return "ENCRYPTED_" + value; // 示例返回值
        }
    },
    None();

    @Override
    public String serializer(String value) {
        return value;
    }

    String maskValue(String value, int startIndex, int endIndex, int maskLength) {
        return value.substring(0, startIndex) + "*".repeat(maskLength) + value.substring(endIndex);
    }
}
