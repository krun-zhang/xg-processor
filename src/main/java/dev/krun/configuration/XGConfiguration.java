package dev.krun.configuration;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author krun
 * @since 2020/06/16
 */
public class XGConfiguration {

	public static final String XG_ENABLE = "xg-enable";

	/**
	 * 是否启用 xg-processor
	 */
	@Property(name = XG_ENABLE, defaultValue = "false")
	private boolean enable;

	public boolean isEnable() {
		return enable;
	}

	/**
	 * 从环境变量中取值并生成配置实例.
	 * @param env 环境变量
	 * @return 配置实例.
	 */
	public static XGConfiguration with(Map<String, String> env) {
		XGConfiguration configuration = new XGConfiguration();
		for (Field declaredField : XGConfiguration.class.getDeclaredFields()) {
			declaredField.setAccessible(true);
			Property property = declaredField.getAnnotation(Property.class);
			try {
				declaredField.set(configuration, parseValue(declaredField.getType(), env.getOrDefault(property.name(), property.defaultValue())));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return configuration;
	}

	/**
	 * 利用表驱动的思路，抽取各种类型的转换办法.
	 */
	private static final Map<Class<?>, Function<String, Object>> fieldValueTransformer = new HashMap<>();

	static {
		fieldValueTransformer.put(boolean.class, value -> Boolean.parseBoolean(getOrDefault(value, Boolean.FALSE::toString)));
	}

	/**
	 * 按指定类型解析文本值
	 * @param fieldType 预期类型
	 * @param value 待解析的文本值
	 * @return 解析结果，若类型
	 */
	@Nullable
	protected static Object parseValue(Class<?> fieldType, String value) {
		return fieldValueTransformer.get(fieldType).apply(value);
	}

	/**
	 * 当 <code>value</code> 为 <code>null</code> 或空字符串时，取默认值.
	 * @param value 值
	 * @param defaultValueSupplier 默认值提供器
	 * @return 非空值
	 */
	@NotNull
	private static String getOrDefault(String value, Supplier<String> defaultValueSupplier) {
		return value == null || value.isEmpty() ? defaultValueSupplier.get() : value;
	}
}
