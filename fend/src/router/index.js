import { createRouter, createWebHistory } from 'vue-router';

// 懒加载页面组件
const Login = () => import('@/views/Login.vue');
const Dashboard = () => import('@/views/Dashboard.vue');
const Posts = () => import('@/views/Posts.vue');
const Users = () => import('@/views/Users.vue');
const Categories = () => import('@/views/Categories.vue');
const Comments = () => import('@/views/Comments.vue');
const Settings = () => import('@/views/Settings.vue');
const NotFound = () => import('@/views/NotFound.vue');

// 路由规则
const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { requiresAuth: false }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard,
    meta: { requiresAuth: true }
  },
  {
    path: '/posts',
    name: 'Posts',
    component: Posts,
    meta: { requiresAuth: true }
  },
  {
    path: '/users',
    name: 'Users',
    component: Users,
    meta: { requiresAuth: true }
  },
  {
    path: '/categories',
    name: 'Categories',
    component: Categories,
    meta: { requiresAuth: true }
  },
  {
    path: '/comments',
    name: 'Comments',
    component: Comments,
    meta: { requiresAuth: true }
  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/',
    redirect: '/dashboard' // 默认跳控制台
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFound,
    meta: { requiresAuth: false }
  }
];

// 创建路由实例
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  linkActiveClass: 'router-link-active' // 匹配导航激活类
});

// 登录守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('gamebbs_admin_token');
  if (to.meta.requiresAuth && !token) {
    next('/login'); // 未登录跳登录页
  } else {
    next();
  }
});

export default router;