package com.example.controller;

import com.example.mapper.MessageMapper;
import com.example.pojo.Message;
import com.example.pojo.Result;
import com.example.service.MessageService;
import com.example.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
@Validated
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageMapper messageMapper;

    //发送消息 将发送的消息添加到数据库
    @PostMapping("send")
    public Result sendMessage(@RequestBody @Validated Message message) {
        messageService.sendMessage(message);
        return Result.success();
    }

    //查询所有与自己有过会话的用户的昵称头像和id
    @GetMapping("users")
    public Result findAllUsers() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Map<String, Object>> users = messageService.findAllUsers(id);
        return Result.success(users);
    }

    //根据自己的id和另一个人的id查询所有的消息
    @GetMapping("{otherId}")
    public Result findAllMessage(@PathVariable Integer otherId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer myId = (Integer) map.get("id");
        List<Message> messages = messageService.findAllMessage(myId, otherId);
        return Result.success(messages);
    }

//    //    测试发现 函数返回List 值时 接收之后 会变成逆序 还会缺失第一个值   ////我神奇的发现 我自己测试错了  所以前面的问题是不存在的
//    @GetMapping("as")
//    public Result findAllMessageAs() {
////        List<Integer> ids = new ArrayList<Integer>();
////        ids.addAll(messageMapper.findAllUserId(3));
////        List<Integer> ids  = messageMapper.findAllUserId(3);
////        ids.addAll(messageMapper.findAllReceiveId(3));
//        List<Integer> ids = messageMapper.findAllUserId(3);
//        System.out.println(ids);
//        System.out.println(ids.get(0));
//        System.out.println(ids.get(1));
//        System.out.println(ids.get(2));
////        System.out.println(ids.get(3));
//        System.out.println(messageMapper.findAllUserId(3));
//        return Result.success(ids);
////        return Result.success(messageMapper.findAllUserId(3));
//    }

    //删除与某个人的全部会话
    @DeleteMapping("{otherId}")
    public Result deleteAllMessage(@PathVariable Integer otherId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer myId = (Integer) map.get("id");
        messageService.deleteAllMessage(myId, otherId);
        return Result.success();
    }

}
