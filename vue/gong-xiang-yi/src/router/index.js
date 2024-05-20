import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import FirstPageVue from '@/views/FirstPage.vue'
import LoginVue from '@/views/Login.vue'
import DetailPageVue from '@/views/DetailPage.vue'
import UserPageVue from '@/views/UserPage.vue'
import UserInfoVue from '@/component/user/UserInfo.vue'
import UserAvatarVue from '@/component/user/UserAvatar.vue'
import UserResetPasswordVue from '@/component/user/UserResetPassword.vue'
import UserGoodsVue from '@/component/user/UserGoods.vue'
import UserRankVue from '@/component/user/UserRank.vue'
import UserBackstageVue from '@/component/user/UserBackstage.vue'
import UserMessageVue from '@/component/user/UserMessage.vue'
import AVue from '@/component/a.vue'

const routes = [
    { path: '/login', component: LoginVue },
    { path: '/', component: FirstPageVue },
    { path: '/detail', component: DetailPageVue, name: "Detail" },
    {
        path: '/user',
        component: UserPageVue,
        redirect: '/user/info',
        children: [
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/avatar', component: UserAvatarVue },
            { path: '/user/resetPassword', component: UserResetPasswordVue },
            { path: '/user/goods', component: UserGoodsVue },
            { path: '/user/rank', component: UserRankVue },
            { path: '/user/backstage', component: UserBackstageVue },
            { path: '/user/message', component: UserMessageVue, name: "UserMessage" }
        ]
    },
    { path: '/a', component: AVue }
]


//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

//导出路由
export default router 