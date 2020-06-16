package dev.krun.xg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class TemplateRenderTest {

	@DisplayName("测试模板文件加载")
	@Test
	public void testLoadTemplates() {
		List<Template> templates = new TemplateRender("./src/test/resources/xg").loadTemplates();
		assertThat(templates).size().isEqualTo(1);
	}

	@DisplayName("测试模板渲染")
	@Test
	public void testRenderTemplate() {
		TemplateRender templateRender = new TemplateRender("./src/test/resources/xg");
		List<Template> templates = templateRender.loadTemplates();
		String template = templateRender.renderTemplate(templates.get(0), "org.example.entity.Test").toString();
		System.out.println(template);
	}

}