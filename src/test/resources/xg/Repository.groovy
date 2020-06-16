package xg

import com.squareup.javapoet.*
import dev.krun.xg.Template

class Repository extends Template {
    public static final ClassName repositorySupperInterface = ClassName.get("org.springframework.data.jpa.repository", "JpaRepository")
    private final repositoryAnnotation = ClassName.get("org.springframework.stereotype", "Repository")

    @Override
    String generate(String entityClassName, String entityName) {
        def entityClass = Class.forName(entityClassName)
        def classSpec = TypeSpec.interfaceBuilder(entityName + "Repository")
                .addAnnotation(repositoryAnnotation)
                .addSuperinterface(ParameterizedTypeName.get(repositorySupperInterface, TypeName.get(entityClass), TypeName.get(Long.class)))
                .build()
        return JavaFile.builder("org.example.repository", classSpec)
                .build()
                .toString()
    }
}
