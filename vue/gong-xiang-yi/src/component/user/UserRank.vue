<script setup>
import { ref } from 'vue'
import avatar from '@/assets/default.png'
import { Edit, Delete } from '@element-plus/icons-vue'
import useUserInfoStore from '@/stores/userInfo.js'
import { userGetTop10Service, userGetRankService } from '@/api/user.js';
const userInfoStore = useUserInfoStore();
const userInfo = ref({ ...userInfoStore.info })

//排名的用户信息
const users = ref([
    {
        id: 1,
        userPic: "https://big-event-258333.oss-cn-beijing.aliyuncs.com/c65a5fb0-8373-4bc9-8305-e27e6a77f163.jpg",
        nickname: "258333",
        points: "45"
    },
])
//我的排名
let userRank = ref('')

//获取积分排名前10名的用户信息
const userGetTop10 = async () => {
    let result = await userGetTop10Service();
    users.value = result.data;
}
userGetTop10();

//获取我的排名和我的积分
const userGetRank = async () => {
    let result = await userGetRankService(userInfo.value.points);
    userRank.value = result.data;
}
userGetRank();
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <div>
                    <span>我的积分:</span>
                    <span style="margin-right: 100px;">{{ userInfo.points }}</span>

                    <span>我的排名:</span>
                    <span>{{ userRank }}</span>
                </div>
            </div>
        </template>

        <el-table :data="users" style="width: 100%">
            <el-table-column label="排名" width="100" type="index"> </el-table-column>
            <el-table-column label="用户头像">
                <template #default="scope">
                    <!-- <img :src="scope.row.userPic" alt="用户头像" style="width: 50px; height: 50px;"> -->
                    <el-avatar :size="50" :src="scope.row.userPic?scope.row.userPic :avatar" />
                </template>
            </el-table-column>
            <el-table-column label="用户昵称" prop="nickname"></el-table-column>
            <el-table-column label="用户积分" prop="points" width=""></el-table-column>
            <el-table-column label="操作" width="">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="console.log(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>



    </el-card>
</template>