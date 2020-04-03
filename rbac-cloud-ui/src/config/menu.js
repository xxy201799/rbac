// 菜单配置
// headerMenuConfig：头部导航配置
// asideMenuConfig：侧边导航配置

const headerMenuConfig = [];

const asideMenuConfig = [
  { name: 'form', path: '/form', id: 'Menu_lri2a' },
  { name: 'login', path: '/login', id: 'Menu_19esy' },
  {
    path: '/dashboard',
    name: 'Dashboard',
    icon: 'el-icon-menu',
    children: [
      { path: '/analysis', name: '分析页', id: 'Menu_iebfv' },
      { path: '/monitor', name: '监控页', id: 'Menu_11hxn' },
      { path: '/workplace', name: '工作台', id: 'Menu_buet3' },
    ],
    id: 'Menu_oapfc',
  },
  {
    path: '/table',
    name: '表格页',
    icon: 'el-icon-date',
    children: [
      { path: '/basic', name: '基础表格', id: 'Menu_gccsh' },
      { path: '/fixed', name: '固定表格', id: 'Menu_9yx9l' },
    ],
    id: 'Menu_08ogf',
  },
  {
    path: '/form',
    name: '表单页',
    icon: 'el-icon-edit-outline',
    children: [
      { path: '/basic', name: '典型表单', id: 'Menu_dk7l4' },
      { path: '/signup', name: '注册表单', id: 'Menu_l6f2p' },
    ],
    id: 'Menu_avd6f',
  },
  {
    path: '/charts',
    name: '图表页',
    icon: 'el-icon-picture-outline',
    children: [
      { path: '/line', name: '折线图', id: 'Menu_m3ejk' },
      { path: '/histogram', name: '柱状图', id: 'Menu_mx0fq' },
      { path: '/bar', name: '条形图', id: 'Menu_yffy0' },
    ],
    id: 'Menu_86y5p',
  },
  {
    path: '/profile',
    name: '详情页',
    icon: 'el-icon-tickets',
    children: [
      { path: '/success', name: '基础详情页', id: 'Menu_cikfx' },
      { path: '/fail', name: '失败', id: 'Menu_r3eul' },
    ],
    id: 'Menu_njr7m',
  },
  {
    path: '/result',
    name: '结果页',
    icon: 'el-icon-circle-check-outline',
    children: [
      { path: '/success', name: '成功', id: 'Menu_pitpj' },
      { path: '/fail', name: '失败', id: 'Menu_llkjn' },
    ],
    id: 'Menu_tai6e',
  },
];

export { headerMenuConfig, asideMenuConfig };
