<script setup>
import { ref } from 'vue'
import { categoryListService } from '@/api/category.js'
import { userCountService } from '@/api/user.js';

let userNum = ref(0)            //用户总数
// let goodNum = ref(0)            //物品总数
let categorys = ref([
    {
        categoryNum: '10'
    }
])    //所有分类
//查询所有物品分类
const categoryList = async () => {
    let result = await categoryListService();
    categorys.value = result.data;
}
categoryList();

//查询用户总数
const userCount =async()=>{
    let result =await userCountService();
    userNum.value = result.data;
}
userCount();


</script>



<template>
    <el-card class="page-container">

        <el-container>
            <el-main>
                <el-card style="max-width: 480px">
                    <template #header>
                        <div class="card-header">
                            <el-icon>
                                <UserFilled />
                            </el-icon>
                            <el-icon>
                                <User />
                            </el-icon>
                            <span style="margin-right: 20px;">用户总数</span>
                            <span>{{ userNum }}</span>
                        </div>
                    </template>
                    <p v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</p>
                    <template #footer>Footer content</template>
                </el-card>
            </el-main>

            <el-main>
                <el-card style="max-width: 480px">
                    <template #header>
                        <div class="card-header">
                            <el-icon>
                                <Memo />
                            </el-icon>
                            <span style="margin-right: 20px;">发布数量</span>
                            <span>{{ categorys[0].categoryNum }}</span>
                        </div>
                    </template>

                    <template v-for="(c, index) in categorys" :key="c.id" class="text item">
                        <div v-if="index != 0">
                            {{ c.categoryName }}
                            <div class="demo-progress">
                                <el-progress :striped="true" :striped-flow="true"  :percentage="c.categoryNum / categorys[0].categoryNum * 100">
                                    <el-button text>{{ c.categoryNum }}</el-button>
                                </el-progress>
                            </div>
                        </div>
                    </template>
                </el-card>
            </el-main>

        </el-container>
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
        align-items: center;
        justify-content: space-between;
    }
}

.demo-progress .el-progress--line {
    margin-bottom: 15px;
    max-width: 600px;
}
</style>