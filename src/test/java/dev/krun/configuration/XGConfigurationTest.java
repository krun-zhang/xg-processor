package dev.krun.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static dev.krun.configuration.XGConfiguration.XG_ENABLE;
import static org.assertj.core.api.Assertions.assertThat;

public class XGConfigurationTest {

	@Test
	public void testWith() {
		Map<String, String> env = new HashMap<>();
		env.put(XG_ENABLE, "true");
		XGConfiguration xgConfiguration = XGConfiguration.with(env);
		assertThat(xgConfiguration.isEnable()).isTrue();
	}

	@Test
	@DisplayName("测试 boolean 类型字段的值解析")
	public void testParseValueWithBooleanField() {
		assertThat(XGConfiguration.parseValue(boolean.class, "false")).isEqualTo(false);
		assertThat(XGConfiguration.parseValue(boolean.class, "true")).isEqualTo(true);
		assertThat(XGConfiguration.parseValue(boolean.class, "False")).isEqualTo(false);
		assertThat(XGConfiguration.parseValue(boolean.class, "True")).isEqualTo(true);
		assertThat(XGConfiguration.parseValue(boolean.class, "FaLse")).isEqualTo(false);
		assertThat(XGConfiguration.parseValue(boolean.class, "TrUe")).isEqualTo(true);
		assertThat(XGConfiguration.parseValue(boolean.class, "")).isEqualTo(false);
		assertThat(XGConfiguration.parseValue(boolean.class, null)).isEqualTo(false);
	}

}