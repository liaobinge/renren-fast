<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="手机号码" prop="mobile">
      <el-input v-model="dataForm.mobile" placeholder="手机号码"></el-input>
    </el-form-item>
    <el-form-item label="发送时间" prop="sendTime">
      <el-input v-model="dataForm.sendTime" placeholder="发送时间"></el-input>
    </el-form-item>
    <el-form-item label="发送内容" prop="sendContent">
      <el-input v-model="dataForm.sendContent" placeholder="发送内容"></el-input>
    </el-form-item>
    <el-form-item label="验证码" prop="smsCode">
      <el-input v-model="dataForm.smsCode" placeholder="验证码"></el-input>
    </el-form-item>
    <el-form-item label="用途" prop="smsUse">
      <el-input v-model="dataForm.smsUse" placeholder="用途"></el-input>
    </el-form-item>
    <el-form-item label="用户Id" prop="smsUserId">
      <el-input v-model="dataForm.smsUserId" placeholder="用户Id"></el-input>
    </el-form-item>
    <el-form-item label="查询次数" prop="queryCount">
      <el-input v-model="dataForm.queryCount" placeholder="查询次数"></el-input>
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
          smsId: 0,
          mobile: '',
          sendTime: '',
          sendContent: '',
          smsCode: '',
          smsUse: '',
          smsUserId: '',
          queryCount: ''
        },
        dataRule: {
          mobile: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' }
          ],
          sendTime: [
            { required: true, message: '发送时间不能为空', trigger: 'blur' }
          ],
          sendContent: [
            { required: true, message: '发送内容不能为空', trigger: 'blur' }
          ],
          smsCode: [
            { required: true, message: '验证码不能为空', trigger: 'blur' }
          ],
          smsUse: [
            { required: true, message: '用途不能为空', trigger: 'blur' }
          ],
          smsUserId: [
            { required: true, message: '用户Id不能为空', trigger: 'blur' }
          ],
          queryCount: [
            { required: true, message: '查询次数不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.smsId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.smsId) {
            this.$http({
              url: this.$http.adornUrl(`/generator/tappsms/info/${this.dataForm.smsId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.mobile = data.tAppSms.mobile
                this.dataForm.sendTime = data.tAppSms.sendTime
                this.dataForm.sendContent = data.tAppSms.sendContent
                this.dataForm.smsCode = data.tAppSms.smsCode
                this.dataForm.smsUse = data.tAppSms.smsUse
                this.dataForm.smsUserId = data.tAppSms.smsUserId
                this.dataForm.queryCount = data.tAppSms.queryCount
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
              url: this.$http.adornUrl(`/generator/tappsms/${!this.dataForm.smsId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'smsId': this.dataForm.smsId || undefined,
                'mobile': this.dataForm.mobile,
                'sendTime': this.dataForm.sendTime,
                'sendContent': this.dataForm.sendContent,
                'smsCode': this.dataForm.smsCode,
                'smsUse': this.dataForm.smsUse,
                'smsUserId': this.dataForm.smsUserId,
                'queryCount': this.dataForm.queryCount
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
