package com.example.mapper;

import com.example.pojo.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {

    //发送消息，将发送的消息添加到数据库
    @Insert("INSERT INTO message (id, send_id, receive_id, message, time) VALUES (#{id}, #{sendId}, #{receiveId}, #{message}, now())")
    void sendMessage(Message message);

    //根据自己的id查询所有给自己发送过消息的用户id
    @Select("SELECT DISTINCT send_id FROM message WHERE receive_id = #{id}")
    List<Integer> findAllSendId(Integer id);

    //根据自己的id查询所有自己接受到消息的用户id
    @Select("SELECT DISTINCT receive_id FROM message WHERE send_id = #{id}")
    List<Integer> findAllReceiveId(Integer id);


    //根据自己的id查询所有与自己有过会话的用户id 并根据时间排序
    @Select("""
              SELECT id
              FROM(
              SELECT DISTINCT id ,MAX(time) as time
              FROM (
              SELECT DISTINCT receive_id AS id, MAX(time) as time
              FROM message
              WHERE send_id = #{id}
              GROUP BY receive_id
              UNION
              SELECT DISTINCT send_id AS id, MAX(time) as time
              FROM message
              WHERE receive_id = #{id}
              GROUP BY send_id
            ) merged
            GROUP BY id
            ORDER BY time DESC
            ) merge
            ORDER BY time DESC
            """)
    List<Integer> findAllUserId(Integer id);

    //根据自己的id和另一个人的id查询所有的消息 并根据时间先后排序
    @Select("SELECT * FROM message WHERE (send_id = #{myId} AND receive_id = #{otherId}) OR (send_id = #{otherId} AND receive_id = #{myId}) ORDER BY time ASC")
    List<Message> findAllMessage(Integer myId, Integer otherId);

    //删除与某个人的全部消息
    @Delete("DELETE FROM message WHERE (send_id = #{myId} AND receive_id = #{otherId}) OR (send_id = #{otherId} AND receive_id = #{myId})")
    void deleteAllMessage(Integer myId, Integer otherId);
}
