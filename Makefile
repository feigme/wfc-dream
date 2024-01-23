# Makefile 介绍 https://seisman.github.io/how-to-write-makefile/index.html

# 定义检查，检查 docker 是否安装
DOCKER_CHECK = @if ! command -v docker &> /dev/null; then \
    echo "Error: Docker is not installed. Please install Docker and try again."; \
    exit 1; \
fi

# 定义检查，检查 docker-compose 是否安装
DOCKER_COMPOSE_CHECK = @if ! command -v docker-compose &> /dev/null; then \
   echo "Error: docker-compose is not installed. Please install docker-compose and try again."; \
   exit 1; \
fi

# 自动获取命令的介绍，注意需要使用#> 作为前缀
.PHONY: help
help: Makefile
	@echo "Choose a command to run:"
	@sed -n 's/^#>//p' $< | column -t -s ':' |  sed -e 's/^/ /'

#> make package-in-docker: 在本地docker环境中，编译打包工程
.PHONY: package-in-docker
package-in-docker:
	@echo "创建一个跟本地maven仓库共享的docker volume"
	$(DOCKER_CHECK)
	docker volume create --name=maven-repo-volume --driver local --opt type=none --opt device=${HOME}/.m2/repository --opt o=bind
	@echo "在docker中编译项目"
	docker run -it --rm --name maven-build-docker \
    	-v maven-repo-volume:/root/.m2/repository \
    	-v ${PWD}:/usr/src/workspace \
    	-w /usr/src/workspace \
    	maven:3.6.3 \
    	mvn clean package '-Dmaven.test.skip=true'

#> make run-in-docker: 运行应用的docker
.PHONY: run-in-docker
run-in-docker:
	@echo "启动应用相关的所有镜像"
	$(DOCKER_COMPOSE_CHECK)
	docker compose --env-file docker.env up -d

#> make build-docker name=xxx: 构建某个镜像
.PHONY: build-docker
build-docker:
	@if [ -z "$(name)" ]; then \
		echo "ERROR: name is not provided."; \
		exit 1; \
	fi
	@echo "构建 ${name} 服务的镜像"
	$(DOCKER_COMPOSE_CHECK)
	docker compose --env-file docker.env build ${name}

#> make build-without-test: 在当前环境，执行mvn package打包
.PHONY: build-without-test
build-without-test:
	mvn clean package -Dmaven.test.skip -Dcheckstyle.skip -Dpmd.skip
  
#> make test: 在当前环境，运行测试用例
.PHONY: test
test:
	mvn test

#> make checkstyle: 在当前环境，运行checkstyle
.PHONY: checkstyle
checkstyle:
	mvn checkstyle:check

#> make pmd: 在当前环境，运行pmd
.PHONY: pmd
pmd:
	mvn pmd:pmd

#> make install: 在当前环境，执行mvn install，不执行测试
.PHONY: install
install:
	mvn clean install -Dpmd.skip=true -Dcheckstyle.skip=true -Dmaven.test.skip=true
