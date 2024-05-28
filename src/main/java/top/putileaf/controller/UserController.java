package top.putileaf.controller;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.putileaf.pojo.Result;
import top.putileaf.pojo.User;
import top.putileaf.service.UserService;
import top.putileaf.utils.JwtUtil;
import top.putileaf.utils.Md5Util;
import top.putileaf.utils.ThreadLocalUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")

//数据校验
@Validated

public class UserController {

    @Autowired
    private UserService userService;
    @Autowired private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/register")
    //判断是否符合用户名规则
    public Result register(@Pattern(regexp="^\\S{5,16}$")String username,@Pattern(regexp="^\\S{5,16}$")String password){
            //查询用户
            User u = userService.findByUsername(username);
            if (u==null){//可以注册
                userService.register(username,password);
                return Result.success("注册成功");
            }else {//用户名已存在不能注册
                return Result.error("用户名被注册");
            }
    }

    //登录逻辑
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp="^\\S{5,16}$")String username,@Pattern(regexp="^\\S{5,16}$")String password){
        //根据用户名查询用户
        User loginUser = userService.findByUsername(username);
        //判断用户是否存在
        if (loginUser==null){
            return Result.error("用户不存在");
        }
        //判断密码是否正确
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            //给Map集合加id和username字段
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            claims.put("vip",loginUser.getVip());
            //生成JWT令牌
            String token = JwtUtil.genToken(claims);
            //把token存储在redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            //返回JWT令牌
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    //根据用户名查询用户
    @GetMapping("/userInfo")
    public Result<User> userinfo(){
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        System.out.println(map.keySet());
        Map<String,Object> map = ThreadLocalUtil.get();
        String username =(String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    //更新
    @PutMapping("/update")
    //Validated让传入的参数进行对象变量校验
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.successT("修改成功");
    }

    //修改头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.successT("修改头像成功");
    }


    //修改密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        //校验参数
        String oldPwd = params.get("old_pwd");//旧密码
        String newPwd = params.get("new_pwd");//新密码
        String rePwd = params.get("re_pwd");//确认新密码
        if ( (!StringUtils.hasLength(oldPwd)) || (!StringUtils.hasLength(newPwd)) || (!StringUtils.hasLength(rePwd)) ){
            //只要有一个值是空的，直接拒绝处理
            return Result.error("请填写密码");
        }
        //先查询原账号的密码
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        //将查询到的密码跟用户输入的旧密码比对
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            //如果密码不一致
            return Result.error("原密码不正确");
        }
        //判断新密码和确认新密码是否一致
        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不一致");
        }
        //全部判断结束，开始重置密码
        userService.updatePwd(newPwd);
        //删除redis中的旧token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }

    //判断用户是否会员


}