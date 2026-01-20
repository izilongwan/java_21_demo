package com.java_21_demo.web.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.java_21_demo.web.aspect.anno.PrivacySerializer;
import com.java_21_demo.web.aspect.anno.PrivacyEncrypt;
import com.java_21_demo.web.enums.PrivacyType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PrivacyEncryptSerializer extends JsonSerializer<String> implements ContextualSerializer {
    private PrivacyEncrypt privacyEncrypt;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)) {
            gen.writeNull();
            return;
        }

        if (Objects.isNull(serializers)) {
            gen.writeString(value);
            return;
        }

        String encryptedValue = value;

        // 如果没有指定类型，尝试使用自定义的 Privacy 实现
        Class<? extends PrivacySerializer> serializerClass = privacyEncrypt.serializer();
        if (serializerClass != PrivacySerializer.class) {
            try {
                PrivacySerializer privacyInstance = serializerClass.getDeclaredConstructor().newInstance();
                encryptedValue = privacyInstance.serializer(value);
            } catch (Exception e) {
                throw new IOException("Failed to instantiate Privacy serializer: " + serializerClass.getName(),
                    e);
            }
        } else {
            encryptedValue = privacyEncrypt.value().serializer(value);
        }

        gen.writeString(encryptedValue);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
        throws JsonMappingException {
        return Optional.ofNullable(property.getAnnotation(PrivacyEncrypt.class))
            .map(PrivacyEncryptSerializer::new)
            .map(JsonSerializer.class::cast)
            .orElse(prov.findValueSerializer(property.getType(), property));
    }

}
