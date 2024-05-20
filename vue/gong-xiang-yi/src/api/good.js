//导入request.js请求工具
import request from '@/utils/request.js'

//发布物品
export const goodAddService = (goodData) => {
    return request.post('/good', goodData);
}

//查询所有物品
export const goodListService = (params) => {
    return request.get('/good', { params: params });
}

//根据分查询所有物品
export const goodCategoryListService = () => {
    return request.get('/good/category')
}

//根据用户id查询所有物品
export const goodIdListService = (id) => {
    return request.get('/good/' + id);
}

//更新物品信息
export const goodUpdateService = (goodDate) => {
    return request.put('/good', goodDate);
}

//删除物品
export const goodDeleteService = (id) =>{
    return request.delete('/good/'+id);
}