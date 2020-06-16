package dev.krun.xg;

import com.squareup.javapoet.JavaFile;

/**
 * 模板
 * @author krun
 * @since 2020/06/16
 */
public abstract class Template {

	public abstract JavaFile generate(String entityPackageName, String entityName);

}
