<script setup>
import { reactive, ref } from 'vue'
import { categoryListService } from '@/api/category.js'
import { goodAddService, goodListService, goodDeleteService } from '@/api/good.js';

let goods = ref([
    {
        id: '',
        categoryId: '',
        userId: '',
        name: '',
        color: '',
        size: '',
        shape: '',
        status: '',
        purchaseTime: '',
        content: '',
        coverImg: '',
        phone: '',
        email: '',
        address: '',
        createTime: '',
        updateTime: ''
    },
])

let categorys = reactive([
    {
        id: "",
        categoryName: "",
    },
])

//查询所有物品分类
const categoryList = async () => {
    let result = await categoryListService();
    Object.assign(categorys, result.data);
}
categoryList();

//物品状态分类
const status = reactive([
    "9-10成新",
    "7-8成新",
    "5-6成新",
    "3-4成新",
    "1-2成新",
])

let goodModel = ref({
    id: '',
    categoryId: '',
    userId: '',
    name: '',
    color: '',
    size: '',
    shape: '',
    status: '',
    purchaseTime: '',
    content: '',
    coverImg: '',
    phone: '',
    email: '',
    address: '',
    createTime: '',
    updateTime: ''

})
let categoryId = ref('');

let input = ref('');


//清空模型
const clearGoodModel = () => {
    goodModel.value = {
        id: '',
        categoryId: '',
        userId: '',
        name: '',
        color: '',
        size: '',
        shape: '',
        status: '',
        purchaseTime: '',
        content: '',
        coverImg: '',
        phone: '',
        email: '',
        address: '',
        createTime: '',
        updateTime: ''
    }
}

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//查询所有物品
const goodList = async () => {
    if (categoryId.value == 0)
        categoryId.value = ''
    if (input.value == '')
        input.value = null
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        userId: null,
        categoryId: categoryId.value,
        input: input.value
    }
    // console.log('aaa')
    let result = await goodListService(params);
    goods.value = result.data.items;
    total.value = result.data.total;
    input.value = ''
}
goodList();

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    goodList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    goodList();
}

//发布物品
const addGood = async () => {
    // console.log(goodModel.value)
    let result = await goodAddService(goodModel.value);

    ElMessage.success(result.message ? result.message : "发布成功");
    visibleDrawer.value = false
    clearGoodModel();
    goodList();
}

// import { QuillEditor } from '@vueup/vue-quill'
// import '@vueup/vue-quill/dist/vue-quill.snow.css'
// import { Plus } from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)

//上传成功的回调函数
const uploadSuccess = (result) => {
    goodModel.value.coverImg = result.data
    // console.log(articleModel.value.coverImg)
}

// import { provide } from 'vue';
// import { useRouter } from 'vue-router'
// const router = useRouter()
import { useGoodStore } from '@/stores/good.js';
const goodStore = useGoodStore();
//存储要展示商品的详细信息
const toDetail = (good) => {
    goodStore.setGood(good);
}

//导入token
import { useTokenStore } from '@/stores/token.js'
import { getMonthDays } from 'element-plus/es/components/calendar/src/date-table';
import { ElMessage } from 'element-plus';
const tokenStore = useTokenStore();


import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();

//删除物品
import { Delete } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'
const goodDelete = (id) => {
    ElMessageBox.confirm(
        '您确认要删除吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {

            let result = await goodDeleteService(id);
            goodList();
            ElMessage({
                type: 'success',
                message: "删除成功"
            })

        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用取消删除',
            })
        })
}
</script>



<template>

    <el-card class="page-container">

        <template #header>

            <div class="header">

                <router-link to="/user">
                    <!-- <span class="clickable">我的共享益</span> -->
                    <el-button type="primary" class="clickable">我的共享益</el-button>
                    <el-icon class="clickable" >
                        <User />
                    </el-icon>
                </router-link>

                <span style="text-align: center; margin: 0 auto;">物品展示</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true">发布物品</el-button>
                </div>
            </div>
        </template>

        <!-- 搜索表单 -->
        <el-form inline class="demo-form-inline">
            <el-form-item>
                <el-select placeholder="请选择" v-model="categoryId">
                    <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="goodList">分类搜索</el-button>
            </el-form-item>
            <el-form-item>
                <el-input v-model="input" style="width: 240px" placeholder="Please input" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="goodList">关键字搜索</el-button>
            </el-form-item>


        </el-form>


        <!-- 展示物品 -->
        <el-card class="show-contsainer">
            <div class="box-bd table-container"> <!-- Apply max-width style -->
                <div class="custom-table">
                    <div v-for="(good, index) in goods" :key="index" class="cell">
                        <!-- Assuming 'goods' is the array of items -->
                        <router-link to="/detail" class="cell-link" @click="toDetail(good)">
                            <img :src="good.coverImg" class="custom-img clickable" />

                            <div class="text-cell">
                                <div>{{ good.name }}</div>
                            </div>
                        </router-link>
                        <el-button :icon="Delete" plain type="danger" v-if="userInfoStore.info.role == 1"
                            @click="goodDelete(good.id)"></el-button>
                    </div>
                </div>
            </div>

            <!-- 分页条 -->
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 15, 20]"
                layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
                @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

        </el-card>


        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" title="发布物品" direction="rtl" size="45%">
            <!-- 添加物品表单 -->
            <el-form :model="goodModel" class="demo-form-inline">
                <el-form :inline="true">
                    <el-form-item label="物品名称">
                        <el-input v-model="goodModel.name" placeholder="请输入物品名称" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="物品类型">
                        <el-select placeholder="请选择" v-model="goodModel.categoryId" clearable>
                            <template v-for="(c, index) in categorys">
                                <template v-if="index != 0">
                                    <el-option :label="c.categoryName" :value="c.id"></el-option>
                                </template>
                            </template>
                        </el-select>
                    </el-form-item>
                </el-form>

                <el-form :inline="true">
                    <el-form-item label="物品颜色">
                        <el-input v-model="goodModel.color" placeholder="选填" clearable></el-input>
                    </el-form-item>

                    <el-form-item label="物品大小">
                        <el-input v-model="goodModel.size" placeholder="选填" clearable></el-input>
                    </el-form-item>
                </el-form>

                <el-form :inline="true">
                    <el-form-item label="物品形状">
                        <el-input v-model="goodModel.shape" placeholder="选填" clearable></el-input>
                    </el-form-item>

                    <el-form-item label="物品状态">
                        <el-select placeholder="请选择" v-model="goodModel.status" clearable>
                            <el-option v-for="(s, index) in status" :key="index" :label="s" :value="s">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>

                <el-form-item label="购买时间">
                    <el-date-picker v-model="goodModel.purchaseTime" type="date" placeholder="Pick a date" clearable
                        @click="console.log(goodModel.purchaseTime)" />
                </el-form-item>

                <el-form-item label="物品图片">
                    <!-- 
                        auto-upload:设置是否自动上传
                        action:设置服务器接口路径
                        name:设置上传的文件字段名
                        :headers:设置上传的请求头
                        :on-success:上传成功的回调函数
                     -->
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false" action="/api/upload"
                        name="file" :headers="{ 'Authorization': tokenStore.token }" :on-success="uploadSuccess">
                        <img v-if="goodModel.coverImg" :src="goodModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>

                </el-form-item>

                <!-- <el-form-item label="物品详细信息">
                    <div class="editor">
                        <quill-editor id="myForm" theme="snow" v-model:content="goodModel.content" contentType="html"
                            clearable>
                        </quill-editor>
                    </div>
                </el-form-item> -->
                <el-form-item label="物品详细信息">
                    <el-input v-model="goodModel.content" style="width: 400px" :rows="2" type="textarea"
                        placeholder="选填" clearable />
                </el-form-item>

                <el-form :inline="true" label-position="right" label-width="auto">
                    <el-form-item label="手机号">
                        <el-input v-model="goodModel.phone" placeholder="选填" clearable></el-input>
                    </el-form-item>

                    <el-form-item label="邮箱">
                        <el-input v-model="goodModel.email" placeholder="选填" clearable></el-input>
                    </el-form-item>

                    <el-form-item label="地址信息">
                        <el-input v-model="goodModel.address" placeholder="选填" clearable></el-input>
                    </el-form-item>
                </el-form>
                <el-form-item>
                    <el-button type="primary" @click="addGood">发布</el-button>
                    <el-button type="primary" @click="clearGoodModel">清空</el-button>
                </el-form-item>

            </el-form>
        </el-drawer>
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

.table-container {
    width: 1500px;
}


.custom-table {
    width: 100%;
    border-collapse: collapse;
    /* 合并单元格边框 */
    margin-top: 10px;
    /* 添加表格之间的间距 */
}


.custom-img {
    max-width: 100%;
    /* max-height: 100%; */
}

.clickable {
    cursor: pointer;
    transition: transform 0.2s;
}

.clickable:hover {
    transform: scale(1.01);
}

.custom-table {
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    gap: 10px;
    border: 1px solid #000;
    /* Add border to the table */
}

.cell {
    /* max-width: 100%; */
    border: 1px solid #000;
    /* Add border to each cell */
    padding: 10px;
    /* Add padding to create space between content and cell border */
}

.cell-link {
    display: block;
    text-align: center;
    text-decoration: none;
}


.demo-form-inline .el-input {
    --el-input-width: 220px;
}

.demo-form-inline .el-select {
    --el-select-width: 220px;
}

/* 抽屉样式 */
.avatar-uploader {
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }

    .el-upload {
        border: 1px dashed var(--el-border-color);
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
        border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        text-align: center;
    }
}

.editor {
    width: 100%;
    height: 100px;
    margin-bottom: 50px;
}
</style>