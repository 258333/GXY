<script setup>
import { ref, inject } from 'vue';
import { useGoodStore } from '@/stores/good.js';
import { useRouter } from 'vue-router'
const router = useRouter()
const goodStore = useGoodStore();
const good = ref({ ...goodStore.good })
// console.log(good.value)


import { categoryByIdService } from '@/api/category.js';
let categoryName = ref('')
//根据物品类型id查询物品类型
const categoryById = async (id) => {
    // console.log(good.value.categoryId)
    let result = await categoryByIdService(id);
    categoryName.value = result.data;
}
//查询物品类型
categoryById(good.value.categoryId);

import { userNicknameByIdService } from '@/api/user.js';
import UserMessage from '@/component/user/UserMessage.vue';
let nickname = ref('')
//根据用户id查询用户昵称
const userNicknameById = async (id) => {
    let result = await userNicknameByIdService(id);
    nickname.value = result.data;
}
//查询用户昵称
userNicknameById(good.value.userId)



const touch = async () => {

    goodStore.setFlag();
    router.push({ name: "UserMessage" })

}

</script>


<template>
    <el-card class="page-container">
        <template #header>
            <el-row>
                <router-link to="/">
                    <el-icon class="clickable">
                        <Back />
                    </el-icon>
                </router-link>
                <div class="header" style="text-align: center; margin: 0 auto;">
                    <span>物品详情</span>
                </div>
            </el-row>

        </template>

        <el-row :span="24">

            <el-col :span="24">
                <div class="grid-content ep-bg-purple-dark">
                    <img :src="good.coverImg" class="custom-img" />
                </div>
            </el-col>

            <el-col :span="24">
                <table class="inform-table" width="100%" border="0" cellspacing="1" cellpadding="4" bgcolor="#ccccff"
                    align="center">
                    <tr>
                        <th>
                        <td width="80px" style="text-align: left;">物品发布者</td>
                        <td>
                            <div class="align-left">{{ nickname }}</div>
                        </td>
                        </th>
                        <th>
                        <td width="80px" style="text-align: left;">物品名称</td>
                        <td>
                            <div class="align-left">{{ good.name }}</div>
                        </td>
                        </th>
                    </tr>
                    <tr>
                        <th>
                        <td width="80px" style="text-align: left;">物品类型</td>
                        <td>
                            <div class="align-left">{{ categoryName }}</div>
                        </td>
                        </th>
                        <th>
                        <td width="80px" style="text-align: left;">物品颜色</td>
                        <td>
                            <div class="align-left">{{ good.color }}</div>
                        </td>
                        </th>
                    </tr>
                    <tr>
                        <th>
                        <td width="80px" style="text-align: left;">物品大小</td>
                        <td>{{ good.size }}</td>
                        </th>
                        <th>
                        <td width="80px" style="text-align: left;">物品形状</td>
                        <td>{{ good.shape }}</td>
                        </th>
                    </tr>
                    <tr>
                        <th>
                        <td width="80px" style="text-align: left;">物品状态</td>
                        <td>{{ good.status }}</td>
                        </th>
                        <th>
                        <td width="80px" style="text-align: left;">详细信息</td>
                        <td>{{ good.content }}</td>
                        </th>
                    </tr>
                    <tr>
                        <th>
                        <td width="80px" style="text-align: left;">买入时间</td>
                        <td>{{ good.purchaseTime ? good.purchaseTime.replace('T', ' ').split('.')[0] : "" }}</td>
                        </th>
                        <th>
                        <td width="80px" style="text-align: left;">上传时间</td>
                        <td>{{ good.updateTime.replace('T', ' ').split('.')[0] }}</td>
                        </th>
                    </tr>
                    <tr>
                        <th>
                        <td width="80px" style="text-align: left;">电话</td>
                        <td>{{ good.phone }}</td>
                        </th>
                        <th>
                        <td width="80px" style="text-align: left;">邮箱</td>
                        <td>{{ good.email }}</td>
                        </th>
                    </tr>
                </table>

            </el-col>
        </el-row>


        <el-row :span="24">


            <el-form :inline="true">
                <el-form-item label="发表评论">
                    <el-input v-model="input" style="width: 240px" placeholder="Please input" />
                </el-form-item>

                <el-form-item>
                    <el-button type="info" @click="touch">和发布者联系</el-button>
                </el-form-item>
            </el-form>
        </el-row>
    </el-card>
</template>

<style scoped>
.page-container {
    max-width: 1600px;
    /* 设置页面内容的最大宽度 */
    margin: 0 auto;
    /* 左右居中 */

    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-goods: center;
        justify-content: space-between;
    }
}

.el-row {
    margin-bottom: 20px;
}

.el-row:last-child {
    margin-bottom: 0;
}

.el-col {
    border-radius: 4px;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}

.clickable {
    cursor: pointer;
    transition: transform 0.2s;
}

.clickable:hover {
    transform: scale(1.5);
}
</style>