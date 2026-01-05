/**
 * 获取用户信息（示例：从localStorage读取）
 * @returns {Object} 用户信息对象
 */
export const getUserInfo = () => {
  try {
    const userInfo = localStorage.getItem('userInfo');
    return userInfo ? JSON.parse(userInfo) : {};
  } catch (error) {
    console.error('获取用户信息失败：', error);
    return {};
  }
};

/**
 * 检查用户是否拥有指定权限
 * @param {string} permission - 要检查的权限标识
 * @returns {boolean} 是否拥有该权限
 */
export const checkPermission = (permission) => {
  const userInfo = getUserInfo();
  // 示例：假设用户信息中permissions是数组
  return userInfo.permissions?.includes(permission) || false;
};

/**
 * 路由守卫：检查路由权限（可选，扩展功能）
 * @param {Object} to - 目标路由
 * @returns {boolean|string} 是否允许跳转/重定向路径
 */
export const routeGuard = (to) => {
  const userInfo = getUserInfo();
  // 未登录且目标路由需要登录，则重定向到登录页
  if (to.meta.requiresAuth && !userInfo.token) {
    return '/login';
  }
  // 已登录但无权限，则重定向到无权限页（可自行扩展）
  if (to.meta.permission && !checkPermission(to.meta.permission)) {
    return '/403';
  }
  return true;
};