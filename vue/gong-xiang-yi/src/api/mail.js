//导入request.js请求工具
import request from '@/utils/request.js'
export const mailGetCodeService = (username, email) => {
    //借助于UrlSearSchParams完成传递
    const params = new URLSearchParams();
    params.append("username", username);
    params.append("email", email)
    // console.log(params.username)

    return request.get('/mail/getCode', { params: params });
}
