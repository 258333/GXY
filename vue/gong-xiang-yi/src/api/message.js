//导入request.js请求工具
import request from '@/utils/request.js'

//发送消息
export const messageSendService = (messageData) => {
    return request.post('/message/send', messageData);
}

//查询所有与自己有过会话的用户的昵称和头像
export const messageUsersService = () => {
    return request.get('/message/users')
}

//查询所有的消息 根据自己的id和另一个人的id
export const messageAllService = (otherId) => {
    return request.get('/message/' + otherId)
}

//删除与某个人的全部会话
export const messageDeleteService = (otherId) => {
    return request.delete('/message/' + otherId);
}