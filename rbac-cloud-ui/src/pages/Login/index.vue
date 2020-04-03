<template>
  <div class="Login-page">
    <!-- 基于element-ui组件的用户登录界面 -->
    <UserLogin />
  </div>
</template>
<script>
import UserLogin from './components/UserLogin';
export default {
  name: 'Login',
  components: {
    UserLogin,
  },
};
</script>
