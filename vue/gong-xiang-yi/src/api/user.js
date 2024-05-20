//导入request.js请求工具
import request from '@/utils/request.js'

//提供调用注册接口的函数
export const userRegisterService = (registerData) => {
    //借助于UrlSearSchParams完成传递
    const params = new URLSearchParams();
    for (let key in registerData) {
        params.append(key, registerData[key]);

    }
    return request.post('/user/register', params);
}

//提供调用登录接口的函数
export const userLoginService = (loginData) => {
    const params = new URLSearchParams();
    for (let key in loginData) {
        params.append(key, loginData[key])
    }
    return request.post('/user/login', params)
}

//根据id查询用户昵称
export const userNicknameByIdService = (id) => {
    return request.get('/user/' + id);
}

//获取用户详细信息
export const userInfoService = () => {
    return request.get('/user/userInfo')
}

//修改个人信息
export const userInfoUpdateService = (userInfoData) => {
    return request.put('/user/update', userInfoData)
}

//修改头像
export const userAvatarUpdateService = (avatarUrl) => {
    const params = new URLSearchParams();
    params.append('avatarUrl', avatarUrl);
    return request.patch('/user/updateAvatar', params);
}

//修改密码
export const userPwdUpdateService = (pwd) => {
    return request.patch('/user/updatePwd', pwd)
}

//获取积分排名前10名的用户信息
export const userGetTop10Service = () => {
    return request.get('/user/top');
}

//获取我的积分排名
export const userGetRankService = (points) => {
    return request.get('/user/rank/'+ points)
}

//查询用户总数
export const userCountService=()=>{
    return request.get('/user/count');
}

//根据id查询用户的头像昵称
export const userPicNickService=(id)=>{
    return request.get("/user/picnick/" + id)
}

//验证登录时的token cookie中 与 redis 中是否一致
export const tokenCheckService=()=>{
    return request.get("/user/token")
}

//根据邮箱修改密码
export const userPwdUpdateByEmailService=(info)=>{
    return request.put('/user/updatePwdByEmail',info)
}