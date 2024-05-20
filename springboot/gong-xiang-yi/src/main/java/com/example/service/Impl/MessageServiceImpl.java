package com.example.service.Impl;

import com.example.mapper.MessageMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.Message;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;


    //发送消息，将发送的消息添加到数据库
    @Override
    public void sendMessage(Message message) {
        String uuid = UUID.randomUUID().toString();
        message.setId(uuid);
        messageMapper.sendMessage(message);
    }

    //查询所有与自己有过会话的用户你 昵称 头像和 id
    @Override
    public List<Map<String, Object>> findAllUsers(Integer id) {
//        List<Integer> ids= messageMapper.findAllSendId(id);
//        List<Integer> ids2= messageMapper.findAllReceiveId(id);
//        ids.addAll(ids2);
//
//        //通过转为集合的方式给ids去重
//        Set<Integer> idSet = new LinkedHashSet<>(ids);  // 使用LinkedHashSet可以保留元素的添加顺序
//        List<Integer> uniqueIds = new ArrayList<>(idSet);

//        List<Integer> ids = new ArrayList<Integer>();
//        ids.addAll(messageMapper.findAllUserId(id));
        List<Integer> ids = messageMapper.findAllUserId(id);
//        System.out.println(ids);
        List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();


        for(int i=0;i<ids.size();i++){
//        for (int i = ids.size() - 1; i >= 0; i--) {
            Map<String, Object> user = userMapper.getPicNick(ids.get(i));
            users.add(user);
        }

//        List<Map<String, Object>> user = userMapper.getUserInfoById(uniqueIds);
        return users;
    }

    @Override
    public List<Message> findAllMessage(Integer myId, Integer otherId) {
        return messageMapper.findAllMessage(myId, otherId);
    }

    //删除与某个人的全部消息
    @Override
    public void deleteAllMessage(Integer myId, Integer otherId) {
        messageMapper.deleteAllMessage(myId, otherId);
    }
}
