<script setup>
import avatar from '@/assets/default.png'

import { userInfoService } from '@/api/user.js';
import useUserInfoStore from '@/stores/userInfo.js'
import { useTokenStore } from '@/stores/token.js';
const userInfoStore = useUserInfoStore();
const tokenStore = useTokenStore();
//调用函数，获取用户详细信息
const getUserInfo = async () => {
    //调用接口
    let result = await userInfoService();
    //存储到pinia中
    userInfoStore.setInfo(result.data)
}
getUserInfo();

import { ElMessageBox, ElMessage } from 'element-plus'
//条目被点击后调用的函数
import { useRouter } from 'vue-router'
const router = useRouter();

//设置cookie
const setCookie = (token, exdays) => {
    var exdate = new Date(); //获取时间
    exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays); //保存的天数
    //字符串拼接cookie
    window.document.cookie = "token" + "=" + token + ";path=/;expires=" + exdate.toGMTString();
}

//清除cookie
const clearCookie = () => {
    setCookie("", -1); //修改值为空，天数为负1天就好了
}
const handleCommand = (command) => {
    //判断指令
    if (command === 'logout') {
        //退出登录确认框   
        ElMessageBox.confirm(
            '您确认要退出吗?',
            '温馨提示',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        )
            .then(() => {
                clearCookie();
                //退出登录
                //清空pinia中存储的token和个人信息
                tokenStore.removeToken();
                userInfoStore.removeInfo();
                //跳转到登录页面
                router.push('/login');
                ElMessage({
                    type: 'success',
                    message: '退出登录成功',
                })

            })
            .catch(() => {
                ElMessage({
                    type: 'info',
                    message: '用户取消退出登录',
                })
            })
    } else {
        //路由
        router.push('/user/' + command)

    }
}

</script>
<template>
    <!-- element-plus 中的容器 -->
    <el-container class="layout-container">

        <!-- 左侧菜单 -->
        <el-aside width="250px">

            <div class="el-aside__logo"></div>
            <!-- element-plus的菜单标签 -->
            <el-menu active-text-color="#ffd04b" background-color="#232323" text-color="#fff" router>
                <el-sub-menu>
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>个人中心</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
                    <el-menu-item index="/user/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>更换头像</span>
                    </el-menu-item>
                    <el-menu-item index="/user/resetPassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>重置密码</span>
                    </el-menu-item>
                </el-sub-menu>

                <el-menu-item index="/user/rank">
                    <el-icon>
                        <TrendCharts />
                    </el-icon>
                    <span>公益排名</span>
                </el-menu-item>

                <el-menu-item index="/user/goods">
                    <el-icon><GoodsFilled /></el-icon>
                    <span>我的物品</span>
                </el-menu-item>

                <el-menu-item index="/user/message">
                    <el-icon><Comment /></el-icon>
                    <span>我的聊天</span>
                </el-menu-item>

                <el-menu-item index="/user/backstage" v-if="userInfoStore.info.role == 1">
                    <el-icon><Management /></el-icon>
                    <span>后台管理</span>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>

                <div>
                    <router-link to="/">
                        <el-icon class="clickable"><Back /></el-icon>
                        <!-- <span class="clickable" style="margin-right: 490px;" >返回首页</span> -->
                        <el-text class="clickable" style="margin-right: 490px;" type="primary" size="large">返回首页</el-text>
                    </router-link>
                    <strong>帅气的{{ userInfoStore.info.nickname }}</strong>
                </div>

                
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                    内容展示区
                </div> -->
                <router-view></router-view>
            </el-main>
            <!-- 底部区域 -->
            <el-footer>共享益 ©2024 Created by 258333</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #232323;

        &__logo {
            height: 120px;
            background: url('@/assets/xiongchumo.jpg') no-repeat center / 120px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}

.clickable {
    cursor: pointer;
    transition: transform 0.2s;
}

.clickable:hover {
    transform: scale(1.5);
}
</style>
