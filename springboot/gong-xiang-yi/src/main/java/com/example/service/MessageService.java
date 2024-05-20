package com.example.service;

import com.example.pojo.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {
    //发送消息，将发送的消息添加到数据库
    void sendMessage(Message message);

    //查询所有与自己有过会话的用户的昵称 头像 和 id
    List<Map<String, Object>> findAllUsers(Integer id);

    //根据自己的id和另一个人的id查询所有的消息
    List<Message> findAllMessage(Integer myId, Integer otherId);

    //删除与某个人的全部消息
    void deleteAllMessage(Integer myId, Integer otherId);
}
