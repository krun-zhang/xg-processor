package dev.krun.xg;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 模板渲染器
 * @author krun
 * @since 2020/06/16
 */
public class TemplateRender {

	/**
	 * 模板文件存放目录
	 */
	private final String templatePath;

	private List<Template> templates;

	public TemplateRender(String path) {
		templatePath = path;
		templates = new ArrayList<>();
	}

	public static TemplateRender withEnv(Map<String, String> env) {
		return new TemplateRender(env.getOrDefault("xg-template", "./src/main/resources/xg"));
	}

	protected List<Template> loadTemplates() {
		File path = new File(templatePath);
		if (!path.exists() || !path.isDirectory()) {
			throw new UnsupportedOperationException("请提供存放模板文件的有效路径!");
		}
		List<File> files = Arrays.asList(Optional.ofNullable(path.listFiles((dir, name) -> name.endsWith("groovy"))).orElse(new File[0]));
		GroovyClassLoader groovyClassLoader = new GroovyClassLoader(this.getClass().getClassLoader());
		files.forEach(file -> {
			try {
				System.out.println("Found template: " + file.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				templates.add((Template) groovyClassLoader.parseClass(file).newInstance());
			} catch (IOException | IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}
		});
		return templates;
	}

	public void renderAllTemplates(String entityClassName) {
		templates.forEach(template -> renderTemplate(template, entityClassName));
	}

	protected String renderTemplate(Template template, String entityClassName) {
		System.out.println(String.format("Using template \"%s\" for entity %s.", template.getClass().getSimpleName(), entityClassName));
		int endIndex = entityClassName.lastIndexOf('.');
		return template.generate(entityClassName.substring(0, endIndex), entityClassName.substring(endIndex + 1));
	}
}
