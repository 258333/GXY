//导入request.js请求工具
import request from '@/utils/request.js'

//查询所有分类
export const categoryListService=()=>{
    return request.get('/category');
}

//根据id查询分类
export const categoryByIdService=(id)=>{
    return request.get('/category/'+id);
}