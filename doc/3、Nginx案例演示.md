# 3、Nginx案例演示

思路：用springboot构建一个服务，存储userId到session中：

情况1：没使用反向代理时，两个服务分别是localhost:8080/hello、localhost:8081/hello、各自获取session，userId不再变化。

参见代码：**[Code\nginx]**

```java
@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello(HttpServletRequest request) {
        //return "helloworld";
        HttpSession session= request.getSession();
        int port2=request.getLocalPort();
        if(session.getAttribute("userid")==null){
            String userid= String.valueOf(new Random().nextInt(100)) ;
            session.setAttribute("userid", userid);
            //response.getWriter().append("Hello, "+userid+",this is "+port2+ " port");
            return "Hello, "+userid+",this is "+port2+ " port";
        }else{
            String userid=(String)session.getAttribute("userid");
            //response.getWriter().append("Welcome back, "+userid+", this is "+port2+" port") ;
            return "Welcome back, "+userid+", this is "+port2+" port";
        }
    }
}
```



情况2：使用反向代理时，两个服务分别是localhost:8080/hello、localhost:8081/hello，将两个服务在nginx中进行配置，访问nginx的81接口进入，分别访问两台服务器，每次访问session均变化，因为没有用redis做数据一致性。

**nginx配置**：nginx.conf    （我保存的位置在/data/nginx-data/nginx.conf）

```json
http{
    ...
    
	upstream myserver{
        # ip_hash;
        server 172.18.4.209:8080 weight=1;
        server 172.18.4.209:8081 weight=1;
    }

    server{
        listen 81;

        location / {   # 匹配任何查询，因为所有请求都已 / 开头。但是正则表达式规则和长的块规则将被优先和查询匹配。
        #反向代理路径
        proxy_pass http://myserver;
        #反向代理的超时时间
        proxy_connect_timeout 10;
    }
}
```

**重启nginx容器：**

```bash
# 重启nginx的docker容器
docker exec -t docker容器id nginx -s reload
```











