<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户Id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户Id"></el-input>
    </el-form-item>
    <el-form-item label="微博授权" prop="weiboAu">
      <el-input v-model="dataForm.weiboAu" placeholder="微博授权"></el-input>
    </el-form-item>
    <el-form-item label="微信授权" prop="wechatAu">
      <el-input v-model="dataForm.wechatAu" placeholder="微信授权"></el-input>
    </el-form-item>
    <el-form-item label="QQ授权" prop="qqAu">
      <el-input v-model="dataForm.qqAu" placeholder="QQ授权"></el-input>
    </el-form-item>
    <el-form-item label="斑马授权" prop="banmaAu">
      <el-input v-model="dataForm.banmaAu" placeholder="斑马授权"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="更新时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          auId: 0,
          userId: '',
          weiboAu: '',
          wechatAu: '',
          qqAu: '',
          banmaAu: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '用户Id不能为空', trigger: 'blur' }
          ],
          weiboAu: [
            { required: true, message: '微博授权不能为空', trigger: 'blur' }
          ],
          wechatAu: [
            { required: true, message: '微信授权不能为空', trigger: 'blur' }
          ],
          qqAu: [
            { required: true, message: 'QQ授权不能为空', trigger: 'blur' }
          ],
          banmaAu: [
            { required: true, message: '斑马授权不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '更新时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.auId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.auId) {
            this.$http({
              url: this.$http.adornUrl(`/generator/tappauthorize/info/${this.dataForm.auId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.tAppAuthorize.userId
                this.dataForm.weiboAu = data.tAppAuthorize.weiboAu
                this.dataForm.wechatAu = data.tAppAuthorize.wechatAu
                this.dataForm.qqAu = data.tAppAuthorize.qqAu
                this.dataForm.banmaAu = data.tAppAuthorize.banmaAu
                this.dataForm.createTime = data.tAppAuthorize.createTime
                this.dataForm.updateTime = data.tAppAuthorize.updateTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/generator/tappauthorize/${!this.dataForm.auId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'auId': this.dataForm.auId || undefined,
                'userId': this.dataForm.userId,
                'weiboAu': this.dataForm.weiboAu,
                'wechatAu': this.dataForm.wechatAu,
                'qqAu': this.dataForm.qqAu,
                'banmaAu': this.dataForm.banmaAu,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
