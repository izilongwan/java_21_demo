package com.java_21_demo.app;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

// @SpringBootTest
class AppApplicationTests {

	@Test
	void contextLoads() {
		String str = "Java 21 Demo Application Test";
		String str2 = """
				%-10s
				a
				b
				%s
				""".formatted("start", str);
		System.out.println(str2);

		switch (str) {
			case "Demo" -> System.out.println("Contains Demo");
			default -> System.out.println("No Match");
		}
	}

	@Test
	public void name() {
		Map.of("key1", "value1", "key2", "value2")
				.forEach((key, value) -> System.out.println(key + ": " + value));
	}

	@Test
	public void replace() {
		String str = "tHis iS a Demo TeSt";

		// 如何将驼峰命名法转换为下划线命名法，第一个字母不需要下划线且为大写
		// String result = str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
		// System.out.println(result);

		Pattern pattern = Pattern.compile(" ?([a-zA-Z]+)");

		String replaceAll = pattern.matcher(str).replaceAll(matchResult -> {
			String firstKey = matchResult.group(1);
			int start = matchResult.start();
			if (start == 0) {
				String s = firstKey.toLowerCase(Locale.ROOT);
				return s.replaceFirst(new String(new char[] { s.charAt(0) }), (s.charAt(0) + "").toUpperCase());
			}
			return "_%s".formatted(firstKey.toLowerCase(Locale.ROOT));
		});
		System.out.println(replaceAll);
	}

	@Test
	public void replace2() {
		String s = "thisIsADemoTest";
		Pattern pattern = Pattern.compile("([A-Z]|^[a-z])");
		String result = pattern.matcher(s).replaceAll(matchResult -> {
			String key = matchResult.group(1);
			if (matchResult.start() == 0) {
				return key.toUpperCase(Locale.ROOT);
			}
			return " %s".formatted(key.toLowerCase(Locale.ROOT));
		});
		System.out.println(result);
	}

}
