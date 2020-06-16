# XG-processor

本项目用于快速为被 `@javax.persistence.Entity` 标记的类生成相应的 `Repository`、`Service` 与 `Controller`.

## Features

* 无代码侵入性，*maven* 中依赖此项目后，通过环境变量设置相关参数就可以开始生成代码;

### Todo

* [ ] 若待生成的 *class* 已存在则会被略过;
* [ ] 具有一系列参数用以控制生成结果;
* [ ] 可以通过 *SPI* 加载自定义生成策略.

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

1. 在项目 *pom* 文件中添加此模块的依赖（本地依赖）;
2. 设置环境变量，可以通过 IDE 的 *Run configuration* 等相关功能进行设置.

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

## Changelog