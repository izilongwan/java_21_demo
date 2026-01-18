package com.java_21_demo.web.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.springframework.util.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import jakarta.servlet.ServletOutputStream;

public class QRCodeUtil {
    /**
     * 生成二维码
     *
     * @param url    二维码对应的URL
     * @param width  二维码图片宽度
     * @param height 二维码图片高度
     * @return
     */
    public static String createQRCode(String url, int width, int height) {
        // 除了尺寸，传入内容不能为空
        if (!StringUtils.hasLength(url)) {
            throw new RuntimeException("Url 不能为空");
        }

        // 二维码参数
        HashMap<EncodeHintType, Comparable<?>> hints = new HashMap<>();
        // 指定字符编码为“utf-8”
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        // L M Q H四个纠错等级从低到高，指定二维码的纠错等级为M
        // 纠错级别越高，可以修正的错误就越多，需要的纠错码的数量也变多，相应的二维吗可储存的数据就会减少
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置图片的边距
        hints.put(EncodeHintType.MARGIN, 1);

        try (ServletOutputStream stream = null;
                ByteArrayOutputStream os = new ByteArrayOutputStream();) {
            // zxing生成二维码核心类
            // 把输入文本按照指定规则转成二维吗
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            // 生成二维码图片流
            // 输出流
            ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "png", os);
            /**
             * 原生转码前面没有 data:image/png;base64 这些字段，返回给前端是无法被解析，所以加上前缀
             */
            return new String(
                    "data:image/png;base64," + Base64.getEncoder().encodeToString(os.toByteArray()));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成二维码出错");
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(QRCodeUtil.createQRCode("https://google.com", 200, 200));
    }
}
