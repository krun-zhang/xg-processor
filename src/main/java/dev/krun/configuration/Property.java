package dev.krun.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用以绑定环境变量与 {@link XGConfiguration} 参数项.
 * @author krun
 * @since 2020/06/16
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

	/**
	 * @return 环境变量名称
	 */
	String name();

	/**
	 * @return 环境变量的默认值，默认为空字符串.
	 */
	String defaultValue() default "";

}
