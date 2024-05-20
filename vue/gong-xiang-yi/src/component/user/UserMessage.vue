<script setup>
import { ref, nextTick } from 'vue'
import avatar from '@/assets/default.png'
import useUserInfoStore from '@/stores/userInfo';
import { useGoodStore } from '@/stores/good.js';
import { messageSendService, messageUsersService, messageAllService, messageDeleteService } from '@/api/message';
import { ElMessage, ElMessageBox } from 'element-plus';
import { userPicNickService } from '@/api/user';
import { onBeforeMount, onMounted } from 'vue'

const goodStore = useGoodStore();
const userInfoStore = useUserInfoStore();
const userInfo = ref({ ...userInfoStore.info })

let users = ref([{
    id: Number,
    userPic: '',
    nickname: '',
},])
//查询所有与自己有过会话的用户的昵称和头像以及id
const messageUsers = async () => {
    let result = await messageUsersService();
    users.value = result.data;

    // console.log(users.value)
}

// 挂载前
// onBeforeMount(async () => {
// })


//正在与自己进行会话的用户的信息
let user = ref({
    id: Number,
    userPic: '',
    nickname: ''
})

//所有的聊天信息
let messages = ref([])

let messageModel = ref({
    id: '',
    sendId: Number,
    receiveId: Number,
    message: '',
    time: ''
})
//消息框滚动条
const scrollContainer = ref(null)
//将滚动条滚动到最底部
const scrollToBottom = () => {
    if (scrollContainer.value) {
        nextTick(() => {
            scrollContainer.value.scrollTop = scrollContainer.value.scrollHeight;
        });
    }
}
//查询所有的消息 根据自己的id和另一个人的id
const messageAll = async (other) => {
    let result = await messageAllService(other.id);
    messages.value = result.data;
    user.value.userPic = other.userPic
    user.value.id = other.id;
    user.value.nickname = other.nickname
    //将滚动条滚动到最底部
    scrollToBottom();
}

//在第一次到达此页面时确定聊天页面显示的联系人   初始化
const initialize = async () => {
    //由物品详情页面跳转过来时的物品的发布者的id
    //判断是不是物品详情页面跳转过来
    console.log(goodStore.flag)
    if (goodStore.flag) {
        // console.log("asdf")
        user.value.id = goodStore.good.userId
        console.log(goodStore.good.userId);
        //根据id查询此用户的头像 昵称 
        let result = await userPicNickService(goodStore.good.userId)
        console.log(result.data)
        users.value.unshift({
            id: result.data.id,
            // id: goodStore.good.userId,
            userPic: result.data.userPic,
            nickname: result.data.nickname
        });
        user.value.userPic = result.data.userPic
        user.value.nickname = result.data.nickname
        goodStore.removeFlag();
    }
    else {
        // console.log(112)
        //如果不是的话 将查到的第一个对话的用户初始化
        if (users.value.length >= 1) {
            // user.value.id = users.value[0].id
            // user.value.nickname = user.value[0].nickname;
            // user.value.userPic = users.value[0].userPic
            user.value = {
                id: users.value[0].id,
                nickname: users.value[0].nickname,
                userPic: users.value[0].userPic
            }
            //查询与此用户的聊天记录
            // console.log(users.value)
            let result = await messageAllService(user.value.id);
            messages.value = result.data;
        }
        //没有聊天的用户则不做处理
    }
}
// 挂载完毕
onMounted(async () => {
    await messageUsers();
    await initialize();
})

const closeAndDelete = async (otherId) => {
    ElMessageBox.confirm(
        '删除后将清空该聊天的消息记录?',
        '温馨提示',
        {
            confirmButtonText: '删除',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            await messageDeleteService(otherId);
            ElMessage.success("删除成功");
            await messageUsers();
            await initialize();

        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '取消删除',
            })
        })
}

//发送消息
const messageSend = async () => {
    messageModel.value.sendId = userInfo.value.id
    messageModel.value.receiveId = user.value.id
    let result = await messageSendService(messageModel.value);
    messages.value.push({
        sendId: messageModel.value.sendId,
        receiveId: messageModel.value.receiveId,
        message: messageModel.value.message,
    });
    // messages.value.push(messageModel.value)//这样赋值的话 在messageModel的值改变后messages中对应的值也会改变
    // console.log(messages.value)
    ElMessage.success("发送成功")
    messageModel.value.message = ""
    scrollToBottom();
}


</script>

<template>
    <el-card class="page-container">
        <el-container class="bordered-section">

            <el-aside width="200px" class="bordered-section">
                <el-menu class="el-menu-demo" mode=" vertical" background-color="#545c64" active-text-color="#ffd04b">

                    <el-scrollbar height="780px">
                        <div v-for="(user, index ) in users" :key="index">
                            <el-menu-item @click="messageAll(user)" style="height: 75px;">
                                <el-avatar :size="60" :src="user.userPic ? user.userPic : avatar"
                                    style="margin-right: 15px;" />
                                <el-text style="color: black;width: 65px;" truncated>
                                    {{ user.nickname }}
                                </el-text>
                                <el-icon class="clickable" @click="closeAndDelete(user.id)">
                                    <Close />
                                </el-icon>
                            </el-menu-item>
                        </div>
                    </el-scrollbar>

                </el-menu>
            </el-aside>

            <el-container>

                <el-header class="bordered-section"
                    style="height: 40px; display: flex; justify-content: center; align-items: center;background-color: gray;">
                    <strong>{{ user.nickname }}</strong>
                </el-header>


                <el-main class="bordered-section">
                    <el-scrollbar height="550px" :ref="scrollContainer" v-slot="{ $el }">

                        <div v-for="(message, index) in messages" :key="index">
                            <div class="send" v-if="message.sendId === userInfo.id"
                                style="display: flex; align-items: center;justify-content: flex-end;  margin-bottom: 10px;">
                                <el-card style="max-width: 480px; max-width: fit-content; max-height: fit-content;">
                                    <p style=" margin: 0;padding: 0;font-size: 1rem;">{{ message.message }}</p>
                                </el-card>
                                <el-avatar :size="50" :src="userInfo.userPic ? userInfo.userPic : avatar"
                                    style="margin-right: 15px;" />
                            </div>
                            <div class="receive" v-else style="display: flex; align-items: center;margin-bottom: 10px;">
                                <el-avatar :size="50" :src="user.userPic ? user.userPic : avatar"
                                    style="margin-right: 15px;" />
                                <el-card style="max-width: 480px; max-width: fit-content; max-height: fit-content;">
                                    <p style=" margin: 0;padding: 0;font-size: 1rem;">{{ message.message }}</p>
                                </el-card>
                            </div>
                        </div>

                    </el-scrollbar>
                </el-main>

                <!-- <div style="position: relative; height: 72px; display: flex; justify-content: flex-end;">
                    <el-input v-model="messageModel.message" type="textarea" :autosize="{ minRows: 3, maxRows: 3 }"
                        placeholder="" resize="none" autofocus maxlength="500" style="background-color: white;"></el-input>
                    <el-card style="width: 80px; margin-left: 10px; position: absolute; z-index: 1;"
                    body-style="background-color:transparent;">
                        <el-button type="info" @click="messageSend" style="bottom: 10px;">发送</el-button>
                    </el-card>
                </div> -->

                <!-- <div style="position: relative; height: 72px; display: flex; justify-content: flex-end;">
                    <el-input v-model="messageModel.message" style="width: 240px" placeholder="验证码">
                        <template #append>
                            <el-button type="info" @click="messageSend" style="bottom: 10px;">发送</el-button>
                        </template>
</el-input>
</div> -->

                <div style="position: relative; height: 100px; display: flex; justify-content: flex-end;">
                    <el-input v-model="messageModel.message">
                        <template #append>
                            <el-button type="info" @click="messageSend" style="bottom: 10px;">发送</el-button>
                        </template>
                    </el-input>
                </div>


            </el-container>
        </el-container>
    </el-card>
    <el-input>

    </el-input>
</template>

<style scoped>
.page-container {
    max-width: 1200px;
    /* 设置页面内容的最大宽度 */
    margin: 0 auto;
    /* 左右居中 */

    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

.bordered-section {
    border: 1px solid #ccc;
    /* 添加各部分的边框 */
    margin: 0px;
    /* 可选添加一些外边距更美观 */
}

.clickable {
    cursor: pointer;
    transition: transform 0.2s;
}

.clickable:hover {
    transform: scale(1.3);
}

.editor:focus {
    outline: none;
}

.editor {
    border: 1px solid #ccc;
    height: 98px;
    width: 100%;
    margin-left: 0px;
}
</style>