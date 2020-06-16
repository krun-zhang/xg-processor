# XG-processor

本项目用于快速为被 `@javax.persistence.Entity` 标记的类生成相应的 `Repository`、`Service` 与 `Controller`.

## Features

* 无代码侵入性，*maven* 中依赖此项目后，通过环境变量设置相关参数就可以开始生成代码;
* 使用 Groovy 与 javapoet 编写脚本以获得完整的模板定义能力.

## Requirements

* *Java 1.8*
* *Maven*

## Installation

```shell script
git clone <repo>
cd <repo>
maven install
```

## Usage

> **注意**: 由于 `GroovyClassLoader` 的问题，脚本中请使用 `ClassName.get(<packageName>, <className>)` 来获得类型描述符而不是通过 `import <package>.<className>`.

1. 在项目 *pom* 文件中添加此模块的依赖（本地依赖）;
2. 设置环境变量，可以通过 IDE 的 *Run configuration* 等相关功能进行设置.
3. 在模板存放目录下新建一个 *groovy* 文件，举个栗子：
    ```groovy
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
    ```
   它会生成：
   ```java
    package org.example.repository;
    
    import java.lang.Long;
    import org.example.entity.Test;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    
    @Repository
    interface TestRepository extends JpaRepository<Test, Long> {
    }
    ```
4. 在 `target/generated-sources/annotations` 中获得生成的文件.

### 环境变量

* `xg-enable`: 是否启用
* `xg-template`: 模板文件存放目录，默认为 `./src/main/resources/xg`

## Support

有使用问题或功能建议欢迎开 *Issue*.

## License

```text
MIT License

Copyright (c) 2020 krun

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
