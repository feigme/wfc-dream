# Makefile 介绍 https://seisman.github.io/how-to-write-makefile/index.html

# 使用#> 规则自动获取命令介绍
.PHONY: help
help: Makefile
	@echo "Choose a command run:"
	@sed -n 's/^#>//p' $< | column -t -s ':' |  sed -e 's/^/ /'
	
#> make package: java打包
.PHONY: build
build:
	mvn clean package -Dmaven.test.skip=true
  
#> make run: 直接运行jar
.PHONY: run
run:
	java -jar start-test/target/start-test.jar
  
#> make test: 运行测试用例
.PHONY: test
test:
	mvn test

#> make install: 本地打包，默认不跑测试
.PHONY: install
install:
	mvn clean install -Dpmd.skip=true -Dcheckstyle.skip=true -Dmaven.test.skip=true

