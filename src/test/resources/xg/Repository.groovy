package xg

import com.squareup.javapoet.*
import dev.krun.xg.Template

class Repository extends Template {
    public static final ClassName repositorySupperInterface = ClassName.get("org.springframework.data.jpa.repository", "JpaRepository")
    private final repositoryAnnotation = ClassName.get("org.springframework.stereotype", "Repository")

    @Override
    JavaFile generate(String entityPackageName, String entityName) {
        def entityClass = ClassName.get(entityPackageName, entityName)
        def classSpec = TypeSpec.interfaceBuilder(entityName + "Repository")
                .addAnnotation(repositoryAnnotation)
                .addSuperinterface(ParameterizedTypeName.get(repositorySupperInterface, entityClass, TypeName.get(Long.class)))
                .build()
        return JavaFile.builder("org.example.repository", classSpec)
                .build()
    }
}
