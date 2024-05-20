<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { mailGetCodeService } from '@/api/mail'

//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
//定义数据模型
const registerData = ref({
    username: '',
    password: '',
    rePassword: ''
})
//校验密码的函数
const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== registerData.value.password) {
        callback(new Error('请确保两次输入的密码一样'))
    } else {
        callback()
    }
}

//定义表单校验规则
const rules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5-16位非空字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5-16位非空字符', trigger: 'blur' }
    ],
    rePassword: [
        { validator: checkRePassword, trigger: 'blur' }
    ]
}

const form = ref(null);

const submitForm = (func) => {
    form.value.validate((valid) => {
        if (valid) {
            func();
            // 表单校验成功
        } else {
            ElMessage.error('格式错误');
            //失败
        }
    });
}

//调用后台接口完成注册
import { userRegisterService, userLoginService, userPwdUpdateByEmailService } from '@/api/user.js'
const register = async () => {
    //registerData是一个响应式对象
    let result = await userRegisterService(registerData.value);
    ElMessage.success(result.message ? result.message : '注册成功');
    isRegister.value = false;
}

//绑定数据，复用注册表单的数据模型  
//表单数据校验
//登录函数
import { useTokenStore } from '@/stores/token.js'
import { useRouter } from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore();


//设置cookie
const setCookie = (token, exdays) => {
    var exdate = new Date(); //获取时间
    exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays); //保存的天数
    //字符串拼接cookie
    window.document.cookie = "token" + "=" + token + ";path=/;expires=" + exdate.toGMTString();
}
//读取cookie
const getCookie = () => {
    // console.log(1)
    if (document.cookie.length > 0) {
        console.log(document.cookie)
        var arr = document.cookie;
        console.log(arr)

        var arr2 = arr.split('='); //再次切割
        //判断查找相对应的值
        // console.log(arr[i])
        if (arr2[0] == 'token') {
            console.log(arr2[1])
            //将token 与 redis 中保存的token 对比
            //如果相同 则再次保存token 并且直接登录
            tokenStore.setToken(arr2[1]);   //先将此token保存
            //跳转到首页 路由完成跳转
            getUserInfo();
            router.push('/')
            //如果不相同 则结束
        }

    }
}
//清除cookie
const clearCookie = () => {
    setCookie("", -1); //修改值为空，天数为负1天就好了
}

onMounted(() => {
    getCookie();
})
//记住我
let checked = ref(false)
const login = async () => {

    //调用接口，完成登录
    let result = await userLoginService(registerData.value);
    ElMessage.success(result.message ? result.message : '登录成功');
    //把得到的token存储到pinia中
    tokenStore.setToken(result.data)

    //判断复选框是否被勾选 勾选则调用配置cookie方法
    if (checked.value == true) {
        //传入账号名，密码，和保存天数3个参数
        setCookie(result.data, 1);
    } else {
        //清空Cookie
        clearCookie();
    }

    //跳转到首页 路由完成跳转
    getUserInfo();
    router.push('/')
}

import { userInfoService } from '@/api/user.js';
import useUserInfoStore from '@/stores/userInfo.js'
import { info } from 'sass';
const userInfoStore = useUserInfoStore();
//调用函数，获取用户详细信息
const getUserInfo = async () => {
    //调用接口
    let result = await userInfoService();
    //存储到pinia中
    userInfoStore.setInfo(result.data)
}
// getUserInfo();
//定义函数用来清空数据模型的数据    
const clearRegisterData = () => {
    registerData.value = {
        username: '',
        password: '',
        rePassword: ''
    }
}

//忘记密码
//创建数据模型
let infoModel = ref({
    username: '',
    email: '',
    verify: '',
    password: '',
    rePassword: ''
})
//清空数据模型
const clearInfoModel = () => {
    infoModel.value = {
        username: '',
        email: '',
        verify: '',
        password: '',
        rePassword: ''
    }
}
//控制弹窗开关
const dialogVisible = ref(false)
//关闭弹窗
const handleClose = () => {
    clearInfoModel();
    dialogVisible.value = false;
}
//获取验证码
const getCode = async () => {
    // if(infoModel.value.username === "") ElMessage.info(请输入用户名)、
    // console.log(infoModel.value)
    let result = await mailGetCodeService(infoModel.value.username, infoModel.value.email)
    ElMessage.success(result.data);
}
//修改密码
const changePassword = async () => {
    if (infoModel.value.password != infoModel.value.rePassword) {
        ElMessage.error('新密码和确认密码不一致')
        infoModel.value.password = ''
        infoModel.value.rePassword = ''
    } else {
        let result = await userPwdUpdateByEmailService(infoModel.value);
        dialogVisible.value = false;
        ElMessage.success(result.message ? result.message : '修改成功')
    }
}
</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>注册</h1>
                </el-form-item>

                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>

                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码"
                        v-model="registerData.password"></el-input>
                </el-form-item>

                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="确认密码"
                        v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="submitForm(register)">
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false; clearRegisterData()">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码"
                        v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox label="记住我" v-model="checked"></el-checkbox>
                        <el-link type="primary" :underline="false" @click="dialogVisible = true">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="submitForm(login)">登录</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true; clearRegisterData()">
                        注册 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" title="找回密码" width="350px" :before-close="handleClose" >
        <el-form :inline="true">
            <el-form-item>
                <el-input v-model=infoModel.username placeholder="用户名" clearable style="width: 240px"></el-input>
            </el-form-item>

            <el-form-item>
                <el-input v-model="infoModel.email" placeholder="邮箱" clearable style="width: 240px"></el-input>
            </el-form-item>

            <el-form-item>
                <el-input v-model="infoModel.verify" style="width: 240px" placeholder="验证码">
                    <template #append>
                        <el-button :type="primary" link @click="getCode()">发送验证码</el-button>
                    </template>
                </el-input>
            </el-form-item>

            <el-form-item>
                <el-input v-model="infoModel.password" placeholder="密码" clearable style="width: 240px"></el-input>
            </el-form-item>

            <el-form-item>
                <el-input v-model="infoModel.rePassword" placeholder="确认密码" clearable style="width: 240px"></el-input>
            </el-form-item>

            <el-form-item>
                <el-button @click="changePassword">修改</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background:
            // url('@/assets/biyao.png') no-repeat 60% center / 240px auto,
            url('@/assets/xiongchumo2.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>