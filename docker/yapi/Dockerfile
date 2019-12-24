FROM node:11.14-alpine as builder
RUN apk add --no-cache git python make openssl tar gcc
ADD yapi.tar.gz /home/
RUN mkdir /api && mv /home/yapi-1.5.11 /api/vendors
RUN cd /api/vendors && \
    npm install --production --registry https://registry.npm.taobao.org

FROM node:11.14-alpine
ENV TZ="Asia/Shanghai"
COPY --from=builder /api/vendors /api/vendors
WORKDIR /api/vendors
EXPOSE 3000
ENTRYPOINT ["node","server/app.js"]