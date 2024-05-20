//定义store
import { defineStore } from 'pinia'
import { ref } from 'vue'

/**
 * 第一个参数：名字，唯一性 
 * 第二个参数：函数，函数的内部可以定义状态的所有内容
 * 
 * 返回值：函数
 */
export const useGoodStore = defineStore('good', () => {
    //定义状态的内容

    //1.响应式变量
    const good = ref({
        
    })

    const flag = ref(false)

    //2.定义一个函数，修改good的值
    const setGood = (newGood) => {
        good.value = newGood
    }

    const setFlag = () => {
        flag.value = true
    }

    //3.函数，移除good的值
    const removeGood = () => {
        goodId.value = ''
    }

    const removeFlag = ()=>{
        flag.value = false;
    }
    return { good, setGood, removeGood,flag,setFlag,removeFlag }
}, {
    persist: false       //取消持久化存储
});