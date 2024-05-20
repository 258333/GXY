<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();
const userInfo = ref({ ...userInfoStore.info })
// userInfo.value = userInfoStore.info;

const pwd = ref({
    old_pwd: '',
    new_pwd: '',
    re_pwd: ''
})

const rules = {
    old_pwd: [
        { required: true, message: '请输入原密码', trigger: 'blur' },

    ],
    new_pwd: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        {
            pattern: /^\S{5,10}$/,
            message: '密码必须是5-10位的非空字符串',
            trigger: 'blur'
        }
    ],
    re_pwd: [
        { required: true, message: '请输入确认密码', trigger: 'blur' },
        {
            pattern: /^\S{5,10}$/,
            message: '密码必须是5-10位的非空字符串',
            trigger: 'blur'
        }
    ]
}

import { userInfoUpdateService, userPwdUpdateService } from '@/api/user.js'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
const router = useRouter();
//修改个人密码
const updatePassword = async () => {
    if (pwd.value.new_pwd != pwd.value.re_pwd) {
        ElMessage.error('新密码和确认密码不一致')
        pwd.value.new_pwd = ''
        pwd.value.re_pwd = ''
    } else {
        //调用接口
        // console.log(pwd.value)
        let result = await userPwdUpdateService(pwd.value);
        ElMessage.success(result.message ? result.message : '修改成功')
        router.push('/login')
    }
}
</script>


<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>修改密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="pwd" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="原密码" prop="old_pwd">
                        <el-input v-model="pwd.old_pwd" type="password"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="new_pwd">
                        <el-input v-model="pwd.new_pwd" type="password"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="re_pwd">
                        <el-input v-model="pwd.re_pwd" type="password"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updatePassword">确认修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>