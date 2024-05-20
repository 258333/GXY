package com.example.pojo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Message {
    private String id;  //消息id
    private Integer sendId; //发送者id
    private Integer receiveId;  //接收者id
    private String message;     //消息
    private java.sql.Timestamp time;    //发送时间
}
