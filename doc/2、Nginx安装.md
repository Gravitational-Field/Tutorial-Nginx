# 2、Nginx安装



## 2.1、使用docker进行安装

[基于Docker安装Nginx及其常用配置](https://blog.lupf.cn/articles/2020/11/08/1604845476002.html)

```bash
# 下载
docker pull nginx

# 运行 一个nginx叫nginx81的容器
docker run --name nginx81 -p 81:80 -d nginx

# 将容器中的配置文件复制到本地文件夹中
mkdir -p /data/nginx-data/conf/conf.d
mkdir -p /data/nginx-data/html
mkdir -p /data/nginx-data/logs

docker run --name nginx81 -p 81:80 -d nginx
docker ps  # 查看到nginx81容器的id，比如说是b37

# 复制配置文件
docker cp b37:/etc/nginx/nginx.conf /data/nginx-data/conf/nginx.conf
docker cp b37:/etc/nginx/conf.d /data/nginx-data/conf
docker cp b37:/usr/share/nginx/html /data/nginx-data

# 停止容器，并删除当前nginx81容器
docker stop b37
docker rm b37

# 重新启动一个容器，并将数据卷配置进去（前边复制来的模板）
run -p 81:80 --name nginx81 --restart=always -v /data/nginx-data/conf/nginx.conf:/etc/nginx/nginx.conf -v /data/nginx-data/conf/conf.d:/etc/nginx/conf.d -v /data/nginx-data/html:/usr/share/nginx/html -v /data/nginx-data/logs:/var/log/nginx -d nginx


# 在修改了default.conf后，需要重新启动nginx81容器，或者重新加载
docker exec -t 容器id nginx -t   # 重启
docker exec -t 容器id nginx -s reload   # 重新加载   推荐
```

更多详细内容参考链接。

