<template>
  <div class="Login-page">
    <!-- 基于element-ui组件的用户登录界面 -->
    <div class="user-login">
      <div class="user-login-bg" :style="{'background-image':`url(${backgroundImage})`}"></div>
      <div class="content-wrapper">
        <h2 class="slogan">
          欢迎使用 <br/> 权限管理系统
        </h2>
        <div class="form-container">
          <h4 class="form-title">登录</h4>
          <el-form ref="form" :model="user" label-width="0">
            <div class="form-items">
              <el-row class="form-item">
                <el-col>
                  <el-form-item prop="username" :rules="[ { required: true, message: '会员名/邮箱/手机号不能为空'}]">
                    <el-input
                      prefix-icon="el-icon-user"
                      placeholder="会员名/邮箱/手机号"
                      v-model="user.username"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row class="form-item mt-10">
                <el-col>
                  <el-form-item prop="password" :rules="[ { required: true, message: '密码不能为空'}]">
                    <el-input
                      prefix-icon="el-icon-lock"
                      type="password"
                      placeholder="密码"
                      v-model="user.password"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row class="form-item mt-10">
                <el-col>
                  <el-form-item prop="code" :rules="[ { required: true, message: '验证码不能为空'}]">
                    <el-input
                      prefix-icon="el-icon-view"
                      placeholder="验证码"
                      v-model="user.code">
                      <template slot="suffix">
                        <img class="code-img" src="http://129.211.159.145:9999/code?randomStr=1234" alt="">
                      </template>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row class=form-item>
                <el-col>
                  <el-form-item>
                    <el-checkbox class="checkbox">记住账号</el-checkbox>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row class="form-item">
                <el-button type="primary" class="submit-btn" size="small" @click="submitBtn">
                  登 录
                </el-button>
              </el-row>
            </div>
            <el-row class="tips">
              <a href="/" class="link">
                立即注册
              </a>
              <span class="line">|</span>
              <a href="/" class="link">
                忘记密码
              </a>
            </el-row>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
const backgroundImage =
  'https://img.alicdn.com/tfs/TB1zsNhXTtYBeNjy1XdXXXXyVXa-2252-1500.png'
import sha256 from 'crypto-js/sha256'
import Base64 from 'crypto-js/enc-base64'

export default {
  name: 'UserLogin',

  data() {
    return {
      backgroundImage: backgroundImage,
      user: {
        username: '',
        password: '',
        code: ''
      },
      img: ''
    }
  },

  created() {
  },
  methods: {
    submitBtn() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.user.password = Base64.stringify(sha256(this.user.password))
          this.$store.dispatch('user/login', this.user).then(() => {
            this.$router.push({ path: '/' })
            this.$message({
              message: '登录成功',
              type: 'success'
            })
          }).catch(() => {
          })
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
  @import '~normalize.css/normalize.css';

  .user-login {
    .user-login-bg {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background-size: cover;
    }

    .el-checkbox__label {
      color: #999;
      font-size: 13px;
    }

    .content-wrapper {
      position: absolute;
      top: -100px;
      left: 0;
      right: 0;
      bottom: 0;
      max-width: 1080px;
      margin: 0 auto;
      display: flex;
      justify-content: space-around;
      align-items: center;

      .slogan {
        text-align: center;
        color: #fff;
        font-size: 36px;
        letter-spacing: 2px;
        line-height: 48px;
      }
    }

    .form-container {
      display: flex;
      justify-content: center;
      flex-direction: column;
      padding: 30px 40px;
      background-color: #fff;
      border-radius: 6px;
      box-shadow: 1px 1px 2px #eee;
    }

    .el-form-item {
      margin-bottom: 15px;
    }

    .form-Item {
      position: relative;
      flex-direction: column;
    }

    .el-input {
      width: 240px;

      input {
        border: none;
        margin: 0;
        padding-left: 10px;
        font-size: 13px;
      }
    }

    .form-title {
      margin: 0 0 20px;
      text-align: center;
      color: #3080fe;
      letter-spacing: 12px;
    }

    .input-icon {
      color: #999;
    }

    .checkbox {
      margin-left: 5px;
    }

    .submit-btn {
      margin-bottom: 25px;
      width: 100%;
      background: #3080fe;
      border-radius: 28px;
    }

    .tips {
    }

    .link {
      color: #999;
      text-decoration: none;
      font-size: 13px;
    }

    .line {
      color: #dcd6d6;
      margin: 0 8px;
    }
  }

  @media screen and (max-width: 720px) {
    .user-login {
      .content-wrapper {
        margin: 20px auto;
        top: 40px;
        max-width: 300px;
        display: block;

        .slogan {
          color: #666;
          font-size: 22px;
          line-height: 30px;
        }
      }
    }
  }
 /deep/ .el-input__suffix {
    right: 1px!important;
  }
  .code-img {
    height: 38px;
    margin-top: 1px;
    border-bottom-right-radius: 4px;
    border-top-right-radius: 4px;
  }

</style>
