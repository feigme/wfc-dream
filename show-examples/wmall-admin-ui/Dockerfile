## 打包镜像
FROM node:18-alpine as build-stage

## 创建目录
RUN mkdir -p /alidata/workspace
## 设置为工作目录
WORKDIR /alidata/workspace

## 复制文件到工作目录
COPY .npmrc package.json ./
## 安装依赖
RUN yarn install

## 复制当前目录到到镜像工作目录
COPY . .

## 环境并打包
ARG NODE_ENV=""
RUN env ${NODE_ENV} yarn build:prod

## nginx运行镜像
FROM nginx:alpine

ENV TZ=Asia/Shanghai

## 复制nginx配置
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage /alidata/workspace/dist-prod /usr/share/nginx/html

EXPOSE 80
