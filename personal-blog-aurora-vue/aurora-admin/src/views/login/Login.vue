<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-title">管理员登录</div>
      <el-form status-icon :model="loginForm" :rules="rules" ref="ruleForm" class="login-form">
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            prefix-icon="el-icon-user-solid"
            placeholder="用户名"
            @keyup.enter.native="login" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            prefix-icon="iconfont el-icon-mymima"
            show-password
            placeholder="密码"
            @keyup.enter.native="login" />
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="login">登录</el-button>
    </div>
  </div>
</template>

<script>
import { generaMenu } from '@/assets/js/menu'
import { _post } from '@/api/api'

export default {
  data: function() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { pattern: /^[a-zA-Z0-9\u4e00-\u9fa5]+$/, message: '用户名格式不正确', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 6, max: 16, message: '密码长度必须在6到16位之间', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          const that = this
          const requestData = {
            username: that.loginForm.username,
            password: that.loginForm.password
          }
          _post('/admin/user/login', requestData, (data, message) => {
            that.$store.commit('login', data)
            localStorage.setItem('token', data.token)
            generaMenu()
            that.$message.success(message)
            that.$router.push({ path: '/' })
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  background: url(../../assets/img/login-background-img.jpeg) center center / cover no-repeat;
}

.login-card {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  background: #fff;
  padding: 170px 60px 180px;
  width: 350px;
}

.login-title {
  color: #303133;
  font-weight: bold;
  font-size: 1rem;
}

.login-form {
  margin-top: 1.2rem;
}

.login-card button {
  margin-top: 1rem;
  width: 100%;
}
</style>
