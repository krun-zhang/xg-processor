package dev.krun.xg;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.persistence.Entity;
import java.io.IOException;
import java.util.Set;

/**
 * XG-Processor.
 *
 * @author krun
 * @since 2020/06/16
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes(XGProcessor.ENTITY_CLASS_NAME)
public class XGProcessor extends AbstractProcessor {

	public static final String ENTITY_CLASS_NAME = "javax.persistence.Entity";

	private final boolean enable = Boolean.parseBoolean(System.getenv().getOrDefault("xg-enable", "false"));

	private final TemplateRender templateRender = TemplateRender.withEnv(System.getenv());

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (!enable) {
			return false;
		}
		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Entity.class);
		if (elements.isEmpty()) {
			return false;
		}
		try {
			/* 由于 processor 会被默认加载，导致单测中即便没有执行此类也会触发 loadTemplates 中模板路径的检查 */
			templateRender.loadTemplates();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Element element : elements) {
			templateRender.renderAllTemplates(element.asType().toString(), javaFile -> {
				String className = javaFile.packageName + "." + javaFile.typeSpec.name;
				try {
					if (processingEnv.getElementUtils().getTypeElement(className) == null) {
						javaFile.writeTo(processingEnv.getFiler());
					} else {
						System.out.println(className + " 已存在.");
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		return false;
	}
}
