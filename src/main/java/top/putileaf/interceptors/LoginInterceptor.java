package top.putileaf.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.putileaf.utils.JwtUtil;
import top.putileaf.utils.ThreadLocalUtil;

import java.util.Map;
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取令牌
        String token = request.getHeader("Authorization");
        //验证token
        try {
            //从redis获取token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            //能找到token
            String redisToken = operations.get(token);
            //找不到token
            if (redisToken==null){
                //token失效
                throw new RuntimeException();
            }
            //找到的token解析出来
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //已登录放行
            return true;
        } catch (Exception e) {
            //http响应码401
            response.setStatus(401);
            //未登录不给予放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的token数据
        ThreadLocalUtil.remove();
    }






}
