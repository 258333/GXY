package com.example.controller;

import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import com.example.utils.Md5Util;
import com.example.utils.ThreadLocalUtil;
import com.mysql.cj.util.StringUtils;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result regist(@Pattern(regexp = "^\\S{5,16}$", message = "用户名格式错误") String username,
                         @Pattern(regexp = "^\\S{5,16}$", message = "密码格式错误") String password) {
        //查询用户
        User u = userService.findByUsername(username);
        if (u == null) {
            //没有占用
            //注册
            userService.register(username, password);
            return Result.success();
        } else {
            //占用
            return Result.error("用户名被占用");
        }
    }


    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$", message = "用户名格式错误") String username,
                                @Pattern(regexp = "^\\S{5,16}$", message = "密码格式错误") String password) {
        //根据用户名查询用户
        User loginUser = userService.findByUsername(username);
        //判断该用户是否存在
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        //判断密码是否正确 loginUser对象中的密码是密文
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("userInfo")
    public Result<User> getUserInfo(@RequestHeader("Authorization") String token) {
        //根据用户名查询用户
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    @PutMapping("update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        //校验参数
        String odlPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (StringUtils.isNullOrEmpty(odlPwd) || StringUtils.isNullOrEmpty(newPwd) || StringUtils.isNullOrEmpty(rePwd)) {
            return Result.error("缺少必要的参数");
        }
        //原密码是否正确
        //调用userService根据用户名拿到原密码，再和old_pwd比对
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
//        if (!loginUser.getPassword().equals(Md5Util.getMD5String(odlPwd))) {
//            return Result.error("原密码错误");
//        }
        if (!Md5Util.checkPassword(odlPwd, loginUser.getPassword())) {
            return Result.error("原密码错误");
        }

        //newpwd与repwd是否一致
        if (!newPwd.equals(rePwd)) {
            return Result.error("两次输入的密码不一致");
        }

        //调用servi完成密码更新
        userService.updatePwd(newPwd);
        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();

    }

    //根据id查询用户昵称
    @GetMapping("{id}")
    public Result getNicknameById(@PathVariable Integer id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        String nickname = user.getNickname();
        return Result.success(nickname);
    }

    //查询积分最高的前10名
    @GetMapping("top")
    public Result getTop10() {
        List<User> users = userService.getTop10();
        return Result.success(users);
    }

    //获取我的积分排名
    @GetMapping("rank/{points}")
    public Result getRank(@PathVariable Integer points) {
        Integer rank = userService.getRank(points);
        return Result.success(rank);
    }

    //获取用户总数
    @GetMapping("count")
    public Result getCount() {
        Integer count = userService.getCount();
        return Result.success(count);
    }

    //根据id查询用户的昵称 头像
    @GetMapping("picnick/{id}")
    public Result getPicNick(@PathVariable Integer id) {
        Map<String, Object> user = userService.getPicNick(id);
        return Result.success(user);
    }

    @PutMapping("updatePwdByEmail")
    public Result updatePwdByEmail(@RequestBody Map<String, String> params) {
        //校验参数
        String password = params.get("password");
        String rePassword = params.get("rePassword");
        String email = params.get("email");
        String username = params.get("username");
        String verify = params.get("verify");

        if (StringUtils.isNullOrEmpty(password) || StringUtils.isNullOrEmpty(rePassword)
                || StringUtils.isNullOrEmpty(email) || StringUtils.isNullOrEmpty(username)
                || StringUtils.isNullOrEmpty(verify)) {
            return Result.error("缺少必要的参数");
        }
        if (!password.equals(rePassword)) {
            return Result.error("两次输入的密码不一致");
        }

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String verifyCode = operations.get("verify");
        if (verifyCode == null) {
            return Result.error("请获取验证码");
        }
        if (!verifyCode.equals(verify)) {
            return Result.error("验证码错误");
        }

        //更新密码
        userService.updatePwdByEmail(password, username);
        return Result.success();
    }
}
