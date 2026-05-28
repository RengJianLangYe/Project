<template>
  <div class="addEdit-block">
    <el-form
      class="detail-form-content"
      ref="ruleForm"
      :model="ruleForm"
      :rules="rules"
      label-width="80px"
      :style="{backgroundColor:addEditForm.addEditBoxColor}"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'" label="笔记名称" prop="bijimingcheng">
            <el-input v-model="ruleForm.bijimingcheng" 
                placeholder="笔记名称" clearable :readonly="ro.bijimingcheng"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="笔记名称" prop="bijimingcheng">
              <el-input v-model="ruleForm.bijimingcheng" 
                placeholder="笔记名称" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'" label="笔记类型" prop="bijileixing">
            <el-input v-model="ruleForm.bijileixing" 
                placeholder="笔记类型" clearable :readonly="ro.bijileixing"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="笔记类型" prop="bijileixing">
              <el-input v-model="ruleForm.bijileixing" 
                placeholder="笔记类型" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'" label="笔记标签" prop="bijibiaoqian">
            <el-input v-model="ruleForm.bijibiaoqian" 
                placeholder="笔记标签" clearable :readonly="ro.bijibiaoqian"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="笔记标签" prop="bijibiaoqian">
              <el-input v-model="ruleForm.bijibiaoqian" 
                placeholder="笔记标签" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="24">  
          <el-form-item class="upload" v-if="type!='info' && !ro.bijitupian" label="笔记图片" prop="bijitupian">
            <file-upload
              tip="点击上传笔记图片"
              action="file/upload"
              :limit="3"
              :multiple="true"
              :fileUrls="ruleForm.bijitupian?ruleForm.bijitupian:''"
              @change="bijitupianUploadChange"
            ></file-upload>
          </el-form-item>
          <div v-else>
            <el-form-item v-if="ruleForm.bijitupian" label="笔记图片" prop="bijitupian">
              <img style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in ruleForm.bijitupian.split(',')" :src="$base.url+item" width="100" height="100">
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="date" v-if="type!='info'" label="笔记日期" prop="fenxiangriqi">
            <el-date-picker
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              v-model="ruleForm.fenxiangriqi" 
              type="date"
              :readonly="ro.fenxiangriqi"
              placeholder="笔记日期">
            </el-date-picker> 
          </el-form-item>
          <div v-else>
            <el-form-item class="input" v-if="ruleForm.fenxiangriqi" label="笔记日期" prop="fenxiangriqi">
              <el-input v-model="ruleForm.fenxiangriqi" 
                placeholder="笔记日期" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'" label="用户名" prop="yonghuming">
            <el-input v-model="ruleForm.yonghuming" 
                placeholder="用户名" clearable :readonly="ro.yonghuming"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="用户名" prop="yonghuming">
              <el-input v-model="ruleForm.yonghuming" 
                placeholder="用户名" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'" label="邮箱" prop="youxiang">
            <el-input v-model="ruleForm.youxiang" 
                placeholder="邮箱" clearable :readonly="ro.youxiang"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="邮箱" prop="youxiang">
              <el-input v-model="ruleForm.youxiang" 
                placeholder="邮箱" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
      </el-row>

      <!-- 编辑模式 -->
<el-row v-if="type!='info'">
  <el-col :span="24">
    <div class="note-content-box">
      <div class="note-content-header">
        <span class="section-title" style="font-size: 25px; font-weight: bold;">编辑笔记</span>
        <div class="header-buttons">
          <el-button type="primary" size="small" style="font-size: 18px; " @click="onSubmit">保存</el-button>
          <el-button size="small" style="font-size: 20px; " @click="back()">返回</el-button>
        </div>
      </div>
      <div class="note-content-editor">
        <div id="vditor-container" style="min-height: 500px;"></div>
        <textarea name="bijineirong" id="bijineirong" style="display:none;"></textarea>
      </div>
      <div class="note-content-footer">
        <div class="footer-left">
          <div class="char-count-box">
            <span class="char-count">字符数：{{ charCount }}</span>
          </div>
        </div>
        <div class="footer-right">
          <div class="ai-buttons">
            <el-button 
              type="success" 
              plain 
              size="medium" 
              @click="handleAIContinue" 
              id="btn-ai-continue"
              style="font-size: 18px; padding: 10px 20px; height: auto;">
              ✨ AI 续写
            </el-button>
            <el-button 
              type="primary" 
              plain 
              size="medium" 
              @click="handleAISummary" 
              id="btn-ai-summary"
              style="font-size: 18px; padding: 10px 20px; height: auto;">
              📄 智能摘要
            </el-button>
          </div>
        </div>
      </div>
    </div>
  </el-col>
</el-row>

      <!-- 查看模式 -->
      <el-row v-else>
        <el-col :span="24">
          <div class="note-content-box">
            <div class="note-content-header">
              <span class="section-title">笔记内容</span>
            </div>
            <div class="note-content-view">
              <span v-html="ruleForm.bijineirong"></span>
            </div>
            <div class="note-content-footer">
              <div class="footer-right" style="width: 100%; justify-content: flex-end;">
                <div class="submit-buttons">
                  <el-button size="small" @click="back()">返回</el-button>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>

    </el-form>
  </div>
</template>
<script>
// 引入 Vditor
import Vditor from 'vditor';
import 'vditor/dist/index.css';
// 数字，邮件，手机，url，身份证校验
import { isNumber,isIntNumer,isEmail,isPhone, isMobile,isURL,checkIdCard } from "@/utils/validate";
export default {
  data() {
    let self = this
    var validateIdCard = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!checkIdCard(value)) {
        callback(new Error("请输入正确的身份证号码"));
      } else {
        callback();
      }
    };
    var validateUrl = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isURL(value)) {
        callback(new Error("请输入正确的URL地址"));
      } else {
        callback();
      }
    };
    var validateMobile = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isMobile(value)) {
        callback(new Error("请输入正确的手机号码"));
      } else {
        callback();
      }
    };
    var validatePhone = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isPhone(value)) {
        callback(new Error("请输入正确的电话号码"));
      } else {
        callback();
      }
    };
    var validateEmail = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isEmail(value)) {
        callback(new Error("请输入正确的邮箱地址"));
      } else {
        callback();
      }
    };
    var validateNumber = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isNumber(value)) {
        callback(new Error("请输入数字"));
      } else {
        callback();
      }
    };
    var validateIntNumber = (rule, value, callback) => {
      if(!value){
        callback();
      } else if (!isIntNumer(value)) {
        callback(new Error("请输入整数"));
      } else {
        callback();
      }
    };
    return {
    vditorInstance: null,
    isVditorReady: false,

     // 【新增】分布式锁相关变量
     editTimer: null,       // 心跳定时器
    currentLockToken: '',  // 存储当前的锁 Token

	  addEditForm: {"btnSaveFontColor":"#fff","selectFontSize":"14px","btnCancelBorderColor":"rgba(0, 0, 0, 1)","inputBorderRadius":"0px","inputFontSize":"14px","textareaBgColor":"#fff","btnSaveFontSize":"14px","textareaBorderRadius":"4px","uploadBgColor":"#fff","textareaBorderStyle":"solid","btnCancelWidth":"88px","textareaHeight":"120px","dateBgColor":"#fff","btnSaveBorderRadius":"25px","uploadLableFontSize":"14px","textareaBorderWidth":"1px","inputLableColor":"#606266","addEditBoxColor":"rgba(255, 255, 255, 1)","dateIconFontSize":"16px","btnSaveBgColor":"rgba(0, 123, 255, 1)","uploadIconFontColor":"rgba(0, 123, 255, 1)","textareaBorderColor":"rgba(65, 81, 100, 1)","btnCancelBgColor":"rgba(255, 255, 255, 1)","selectLableColor":"#606266","btnSaveBorderStyle":"solid","dateBorderWidth":"1px","dateLableFontSize":"14px","dateBorderRadius":"0px","btnCancelBorderStyle":"solid","selectLableFontSize":"14px","selectBorderStyle":"none none solid none","selectIconFontColor":"rgba(0, 123, 255, 1)","btnCancelHeight":"44px","inputHeight":"36px","btnCancelFontColor":"#606266","dateBorderColor":"rgba(65, 81, 100, 1)","dateIconFontColor":"rgba(0, 123, 255, 1)","uploadBorderStyle":"solid","dateBorderStyle":"none none solid none","dateLableColor":"#606266","dateFontSize":"14px","inputBorderWidth":"1px","uploadIconFontSize":"28px","selectHeight":"36px","inputFontColor":"rgba(96, 98, 102, 1)","uploadHeight":"120px","textareaLableColor":"#606266","textareaLableFontSize":"14px","btnCancelFontSize":"14px","inputBorderStyle":"none none solid none","btnCancelBorderRadius":"30px","inputBgColor":"rgba(255, 255, 255, 1)","inputLableFontSize":"14px","uploadLableColor":"#606266","uploadBorderRadius":"4px","btnSaveHeight":"44px","selectBgColor":"#fff","btnSaveWidth":"88px","selectIconFontSize":"20px","dateHeight":"36px","selectBorderColor":"rgba(65, 81, 100, 1)","inputBorderColor":"rgba(65, 81, 100, 1)","uploadBorderColor":"rgba(30, 144, 255, 1)","textareaFontColor":"#606266","selectBorderWidth":"1px","dateFontColor":"#606266","btnCancelBorderWidth":"1px","uploadBorderWidth":"1px","textareaFontSize":"14px","selectBorderRadius":"0px","selectFontColor":"#606266","btnSaveBorderColor":"rgba(255, 69, 0, 1)","btnSaveBorderWidth":"0px"},
      id: '',
      type: '',
      ro:{
	bijimingcheng : false,
	bijileixing : false,
	bijibiaoqian : false,
	bijitupian : false,
	bijineirong : false,
	fenxiangriqi : false,
	yonghuming : false,
	youxiang : false,
	sfsh : false,
	shhf : false,
	thumbsupnum : false,
	crazilynum : false,
      },
      ruleForm: {
        bijimingcheng: '',
        bijileixing: '',
        bijibiaoqian: '',
        bijitupian: '',
        bijineirong: '',
        fenxiangriqi: '',
        yonghuming: '',
        youxiang: '',
        shhf: '',
      },
      rules: {
          bijimingcheng: [
                { required: true, message: '笔记名称不能为空', trigger: 'blur' },
          ],
          bijileixing: [
          ],
          bijibiaoqian: [
          ],
          bijitupian: [
          ],
          bijineirong: [
                { required: true, message: '笔记内容不能为空', trigger: 'blur' },
          ],
          fenxiangriqi: [
          ],
          yonghuming: [
          ],
          youxiang: [
                { validator: validateEmail, trigger: 'blur' },
          ],
          sfsh: [
          ],
          shhf: [
          ],
          thumbsupnum: [
                { validator: validateIntNumber, trigger: 'blur' },
          ],
          crazilynum: [
                { validator: validateIntNumber, trigger: 'blur' },
          ],
      }
    };
  },
  props: ["parent"],
  computed: {
    charCount() {
    return this.ruleForm.bijineirong ? this.ruleForm.bijineirong.length : 0;
  }
  },
  created() {
	this.addEditStyleChange()
	this.addEditUploadStyleChange()
  },

  mounted() {
    this.initVditor();
},
beforeDestroy() {
    if (this.vditorInstance) {
        this.vditorInstance.destroy();
    }
},
  methods: {

        // 初始化 Vditor 编辑器
        initVditor() {
      this.$nextTick(() => {
        if (!document.getElementById('vditor-container')) return;
        
        const existingContent = this.ruleForm.bijineirong;
        
        this.vditorInstance = new Vditor('vditor-container', {
          height: 500,
          mode: 'ir',
          placeholder: '使用 Markdown 语法书写笔记...',
          toolbar: ['headings', 'bold', 'italic', 'strike', 'link', '|', 'list', 'ordered-list', 'check', 'quote', 'code', 'inline-code', '|', 'undo', 'redo', '|', 'preview', 'fullscreen'],
          
          input: (value) => {
            this.ruleForm.bijineirong = value;
          },
          
          after: () => {
            console.log('Vditor 初始化完成');
            this.isVditorReady = true;
            
            if (existingContent) {
              this.vditorInstance.setValue(existingContent);
            }
          },
          cache: { enable: false }
        });
      });
    },
  
      // AI 续写
        handleAIContinue() {
      const currentContent = this.vditorInstance ? this.vditorInstance.getValue() : '';
      if (!currentContent.trim()) {
        this.$message.error('请先输入一些内容');
        return;
      }

      this.$message.info('AI 正在思考中...');

      const requestData = {
        content: currentContent
      };

      this.$http({
        url: '/api/chat/continue',
        method: 'post',
        headers: {
          'Content-Type': 'application/json'
        },
        data: requestData
      }).then(({ data }) => {
        if (data && data.code === 200) {
          this.$alert(data.data, 'AI 续写结果', {
            confirmButtonText: '插入笔记',
            showCancelButton: true,
            cancelButtonText: '取消',
            type: 'info'
          }).then(() => {
            this.vditorInstance.setValue(currentContent + '\n\n' + data.data);
            this.$message.success('已插入');
          }).catch(() => {});
        } else {
          this.$message.error(data.msg || '生成失败');
        }
      }).catch((error) => {
        console.error(error);
        this.$message.error('网络请求失败，请检查后端日志');
      });
    },

    // 智能摘要
    handleAISummary() {
      const currentContent = this.vditorInstance ? this.vditorInstance.getValue() : '';
      if (!currentContent.trim()) {
        this.$message.error('请先输入内容');
        return;
      }

      this.$message.info('AI 正在生成摘要...');

      const requestData = {
        content: currentContent
      };

      this.$http({
        url: '/api/chat/summary',
        method: 'post',
        data: requestData
      }).then(({ data }) => {
        if (data && data.code === 200) {
          this.$alert(data.data, '智能摘要', {
            confirmButtonText: '确定',
            type: 'info'
          });
        } else {
          this.$message.error(data.msg || '生成失败');
        }
      }).catch(() => {
        this.$message.error('网络请求失败');
      });
    },

    // 下载
    download(file){
      window.open(`${file}`)
    },
    // 初始化
    init(id,type) {
      if (id) {
        this.id = id;
        this.type = type;
      }
      if(this.type=='info'||this.type=='else'){
        this.info(id);
      }else if(this.type=='logistics'){
        this.logistics=false;
        this.info(id);
      }else if(this.type=='cross'){
        var obj = this.$storage.getObj('crossObj');
        for (var o in obj){
          if(o=='bijimingcheng'){
            this.ruleForm.bijimingcheng = obj[o];
	    this.ro.bijimingcheng = true;
            continue;
          }
          if(o=='bijileixing'){
            this.ruleForm.bijileixing = obj[o];
	    this.ro.bijileixing = true;
            continue;
          }
          if(o=='bijibiaoqian'){
            this.ruleForm.bijibiaoqian = obj[o];
	    this.ro.bijibiaoqian = true;
            continue;
          }
          if(o=='bijitupian'){
            this.ruleForm.bijitupian = obj[o];
	    this.ro.bijitupian = true;
            continue;
          }
          if(o=='bijineirong'){
            this.ruleForm.bijineirong = obj[o];
	    this.ro.bijineirong = true;
            continue;
          }
          if(o=='fenxiangriqi'){
            this.ruleForm.fenxiangriqi = obj[o];
	    this.ro.fenxiangriqi = true;
            continue;
          }
          if(o=='yonghuming'){
            this.ruleForm.yonghuming = obj[o];
	    this.ro.yonghuming = true;
            continue;
          }
          if(o=='youxiang'){
            this.ruleForm.youxiang = obj[o];
	    this.ro.youxiang = true;
            continue;
          }
          if(o=='thumbsupnum'){
            this.ruleForm.thumbsupnum = obj[o];
	    this.ro.thumbsupnum = true;
            continue;
          }
          if(o=='crazilynum'){
            this.ruleForm.crazilynum = obj[o];
	    this.ro.crazilynum = true;
            continue;
          }
        }
      }
      // 获取用户信息
      this.$http({
        url: `${this.$storage.get('sessionTable')}/session`,
        method: "get"
      }).then(({ data }) => {
        if (data && data.code === 0) {
          var json = data.data;
		if(json.yonghuming!=''&&json.yonghuming){
                this.ruleForm.yonghuming = json.yonghuming
	    		this.ro.yonghuming = true;
		}
		if(json.youxiang!=''&&json.youxiang){
                this.ruleForm.youxiang = json.youxiang
	    		this.ro.youxiang = true;
		}
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    // 多级联动参数
    info(id) {
      this.$http({
        url: `bijifenxiang/info/${id}`,
        method: "get"
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.ruleForm = data.data;
          
          // 解决图片路径问题
          let reg = new RegExp('../../../upload', 'g');
          // 处理笔记内容中的图片
          this.ruleForm.bijineirong = this.ruleForm.bijineirong ? 
            this.ruleForm.bijineirong.replace(reg, '../../../springbootf3x3h/upload') : '';
          
          // 处理笔记图片字段
          if (this.ruleForm.bijitupian) {
            console.log('原始图片路径:', this.ruleForm.bijitupian);
            this.ruleForm.bijitupian = this.ruleForm.bijitupian.replace(reg, '../../../springbootf3x3h/upload');
            console.log('替换后路径:', this.ruleForm.bijitupian);
          }
          
          // Vditor 相关代码
          this.$nextTick(() => {
            if (this.vditorInstance && this.ruleForm.bijineirong) {
              if (this.isVditorReady) {
                this.vditorInstance.setValue(this.ruleForm.bijineirong);
              } else {
                const checkReady = setInterval(() => {
                  if (this.vditorInstance && this.isVditorReady) {
                    clearInterval(checkReady);
                    this.vditorInstance.setValue(this.ruleForm.bijineirong);
                  }
                }, 100);
              }
            }
          });
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    // 提交
  onSubmit() {
    
    // 从 Vditor 同步内容
    if (this.vditorInstance) {
        this.ruleForm.bijineirong = this.vditorInstance.getValue();
    }
    
    // 处理图片路径
    if (this.ruleForm.bijitupian != null) {
        this.ruleForm.bijitupian = this.ruleForm.bijitupian.replace(new RegExp(this.$base.url, "g"), "");
    }
    
    // 创建提交数据（不包含创建人信息）
    const submitData = { ...this.ruleForm };
    delete submitData.yonghuming;
    delete submitData.userid;
    delete submitData.addtime;
    
    var objcross = this.$storage.getObj('crossObj');

    // 更新跨表属性
    var crossuserid;
    var crossrefid;
    var crossoptnum;
    if (this.type == 'cross') {
        var statusColumnName = this.$storage.get('statusColumnName');
        var statusColumnValue = this.$storage.get('statusColumnValue');
        if (statusColumnName != '') {
            var obj = this.$storage.getObj('crossObj');
            if (!statusColumnName.startsWith("[")) {
                for (var o in obj) {
                    if (o == statusColumnName) {
                        obj[o] = statusColumnValue;
                    }
                }
                var table = this.$storage.get('crossTable');
                this.$http({
                    url: `${table}/update`,
                    method: "post",
                    data: obj
                }).then(({ data }) => {});
            } else {
                crossuserid = this.$storage.get('userid');
                crossrefid = obj['id'];
                crossoptnum = this.$storage.get('statusColumnName');
                crossoptnum = crossoptnum.replace(/\[/, "").replace(/\]/, "");
            }
        }
    }
    
    this.$refs["ruleForm"].validate(valid => {
        if (valid) {
            if (crossrefid && crossuserid) {
                submitData.crossuserid = crossuserid;
                submitData.crossrefid = crossrefid;
                let params = {
                    page: 1,
                    limit: 10,
                    crossuserid: submitData.crossuserid,
                    crossrefid: submitData.crossrefid,
                }
                this.$http({
                    url: "bijifenxiang/page",
                    method: "get",
                    params: params
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        if (data.data.total >= crossoptnum) {
                            this.$message.error(this.$storage.get('tips'));
                            return false;
                        } else {
                            this.$http({
                                url: `bijifenxiang/${!submitData.id ? "save" : "update"}`,
                                method: "post",
                                data: submitData
                            }).then(({ data }) => {
                                if (data && data.code === 0) {
                                    this.$message({
                                        message: "操作成功",
                                        type: "success",
                                        duration: 1800,
                                        onClose: () => {
                                            this.parent.showFlag = true;
                                            this.parent.addOrUpdateFlag = false;
                                            this.parent.bijifenxiangCrossAddOrUpdateFlag = false;
                                            this.parent.search();
                                            this.parent.contentStyleChange();
                                        }
                                    });
                                } else {
                                    this.$message.error(data.msg);
                                }
                            });
                        }
                    }
                });
            } else {
                this.$http({
                    url: `bijifenxiang/${!submitData.id ? "save" : "update"}`,
                    method: "post",
                    data: submitData
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        this.$message({
                            message: "操作成功",
                            type: "success",
                            duration: 1500,
                            onClose: () => {
                                this.parent.showFlag = true;
                                this.parent.addOrUpdateFlag = false;
                                this.parent.bijifenxiangCrossAddOrUpdateFlag = false;
                                this.parent.search();
                                this.parent.contentStyleChange();
                            }
                        });
                    } else {
                        this.$message.error(data.msg);
                    }
                });
            }
        }
    });
},
    // 返回
back() {
    this.parent.showFlag = true;
    this.parent.addOrUpdateFlag = false;
    this.parent.bijifenxiangCrossAddOrUpdateFlag = false;
    this.parent.contentStyleChange();
},
    bijitupianUploadChange(fileUrls) {
	this.ruleForm.bijitupian = fileUrls;
	this.addEditUploadStyleChange()
    },
	addEditStyleChange() {
	  this.$nextTick(()=>{
	    // input
	    document.querySelectorAll('.addEdit-block .input .el-input__inner').forEach(el=>{
	      el.style.height = this.addEditForm.inputHeight
	      el.style.color = this.addEditForm.inputFontColor
	      el.style.fontSize = this.addEditForm.inputFontSize
	      el.style.borderWidth = this.addEditForm.inputBorderWidth
	      el.style.borderStyle = this.addEditForm.inputBorderStyle
	      el.style.borderColor = this.addEditForm.inputBorderColor
	      el.style.borderRadius = this.addEditForm.inputBorderRadius
	      el.style.backgroundColor = this.addEditForm.inputBgColor
	    })
	    document.querySelectorAll('.addEdit-block .input .el-form-item__label').forEach(el=>{
	      el.style.lineHeight = this.addEditForm.inputHeight
	      el.style.color = this.addEditForm.inputLableColor
	      el.style.fontSize = this.addEditForm.inputLableFontSize
	    })
	    // select
	    document.querySelectorAll('.addEdit-block .select .el-input__inner').forEach(el=>{
	      el.style.height = this.addEditForm.selectHeight
	      el.style.color = this.addEditForm.selectFontColor
	      el.style.fontSize = this.addEditForm.selectFontSize
	      el.style.borderWidth = this.addEditForm.selectBorderWidth
	      el.style.borderStyle = this.addEditForm.selectBorderStyle
	      el.style.borderColor = this.addEditForm.selectBorderColor
	      el.style.borderRadius = this.addEditForm.selectBorderRadius
	      el.style.backgroundColor = this.addEditForm.selectBgColor
	    })
	    document.querySelectorAll('.addEdit-block .select .el-form-item__label').forEach(el=>{
	      el.style.lineHeight = this.addEditForm.selectHeight
	      el.style.color = this.addEditForm.selectLableColor
	      el.style.fontSize = this.addEditForm.selectLableFontSize
	    })
	    document.querySelectorAll('.addEdit-block .select .el-select__caret').forEach(el=>{
	      el.style.color = this.addEditForm.selectIconFontColor
	      el.style.fontSize = this.addEditForm.selectIconFontSize
	    })
	    // date
	    document.querySelectorAll('.addEdit-block .date .el-input__inner').forEach(el=>{
	      el.style.height = this.addEditForm.dateHeight
	      el.style.color = this.addEditForm.dateFontColor
	      el.style.fontSize = this.addEditForm.dateFontSize
	      el.style.borderWidth = this.addEditForm.dateBorderWidth
	      el.style.borderStyle = this.addEditForm.dateBorderStyle
	      el.style.borderColor = this.addEditForm.dateBorderColor
	      el.style.borderRadius = this.addEditForm.dateBorderRadius
	      el.style.backgroundColor = this.addEditForm.dateBgColor
	    })
	    document.querySelectorAll('.addEdit-block .date .el-form-item__label').forEach(el=>{
	      el.style.lineHeight = this.addEditForm.dateHeight
	      el.style.color = this.addEditForm.dateLableColor
	      el.style.fontSize = this.addEditForm.dateLableFontSize
	    })
	    document.querySelectorAll('.addEdit-block .date .el-input__icon').forEach(el=>{
	      el.style.color = this.addEditForm.dateIconFontColor
	      el.style.fontSize = this.addEditForm.dateIconFontSize
	      el.style.lineHeight = this.addEditForm.dateHeight
	    })
	    // upload
	    let iconLineHeight = parseInt(this.addEditForm.uploadHeight) - parseInt(this.addEditForm.uploadBorderWidth) * 2 + 'px'
	    document.querySelectorAll('.addEdit-block .upload .el-upload--picture-card').forEach(el=>{
	      el.style.width = this.addEditForm.uploadHeight
	      el.style.height = this.addEditForm.uploadHeight
	      el.style.borderWidth = this.addEditForm.uploadBorderWidth
	      el.style.borderStyle = this.addEditForm.uploadBorderStyle
	      el.style.borderColor = this.addEditForm.uploadBorderColor
	      el.style.borderRadius = this.addEditForm.uploadBorderRadius
	      el.style.backgroundColor = this.addEditForm.uploadBgColor
	    })
	    document.querySelectorAll('.addEdit-block .upload .el-form-item__label').forEach(el=>{
	      el.style.lineHeight = this.addEditForm.uploadHeight
	      el.style.color = this.addEditForm.uploadLableColor
	      el.style.fontSize = this.addEditForm.uploadLableFontSize
	    })
	    document.querySelectorAll('.addEdit-block .upload .el-icon-plus').forEach(el=>{
	      el.style.color = this.addEditForm.uploadIconFontColor
	      el.style.fontSize = this.addEditForm.uploadIconFontSize
	      el.style.lineHeight = iconLineHeight
	      el.style.display = 'block'
	    })
	    // 多文本输入框
	    document.querySelectorAll('.addEdit-block .textarea .el-textarea__inner').forEach(el=>{
	      el.style.height = this.addEditForm.textareaHeight
	      el.style.color = this.addEditForm.textareaFontColor
	      el.style.fontSize = this.addEditForm.textareaFontSize
	      el.style.borderWidth = this.addEditForm.textareaBorderWidth
	      el.style.borderStyle = this.addEditForm.textareaBorderStyle
	      el.style.borderColor = this.addEditForm.textareaBorderColor
	      el.style.borderRadius = this.addEditForm.textareaBorderRadius
	      el.style.backgroundColor = this.addEditForm.textareaBgColor
	    })
	    document.querySelectorAll('.addEdit-block .textarea .el-form-item__label').forEach(el=>{
	      // el.style.lineHeight = this.addEditForm.textareaHeight
	      el.style.color = this.addEditForm.textareaLableColor
	      el.style.fontSize = this.addEditForm.textareaLableFontSize
	    })
	    // 保存
	    document.querySelectorAll('.addEdit-block .btn .btn-success').forEach(el=>{
	      el.style.width = this.addEditForm.btnSaveWidth
	      el.style.height = this.addEditForm.btnSaveHeight
	      el.style.color = this.addEditForm.btnSaveFontColor
	      el.style.fontSize = this.addEditForm.btnSaveFontSize
	      el.style.borderWidth = this.addEditForm.btnSaveBorderWidth
	      el.style.borderStyle = this.addEditForm.btnSaveBorderStyle
	      el.style.borderColor = this.addEditForm.btnSaveBorderColor
	      el.style.borderRadius = this.addEditForm.btnSaveBorderRadius
	      el.style.backgroundColor = this.addEditForm.btnSaveBgColor
	    })
	    // 返回
	    document.querySelectorAll('.addEdit-block .btn .btn-close').forEach(el=>{
	      el.style.width = this.addEditForm.btnCancelWidth
	      el.style.height = this.addEditForm.btnCancelHeight
	      el.style.color = this.addEditForm.btnCancelFontColor
	      el.style.fontSize = this.addEditForm.btnCancelFontSize
	      el.style.borderWidth = this.addEditForm.btnCancelBorderWidth
	      el.style.borderStyle = this.addEditForm.btnCancelBorderStyle
	      el.style.borderColor = this.addEditForm.btnCancelBorderColor
	      el.style.borderRadius = this.addEditForm.btnCancelBorderRadius
	      el.style.backgroundColor = this.addEditForm.btnCancelBgColor
	    })
	  })
	},
	addEditUploadStyleChange() {
		this.$nextTick(()=>{
		  document.querySelectorAll('.addEdit-block .upload .el-upload-list--picture-card .el-upload-list__item').forEach(el=>{
			el.style.width = this.addEditForm.uploadHeight
			el.style.height = this.addEditForm.uploadHeight
			el.style.borderWidth = this.addEditForm.uploadBorderWidth
			el.style.borderStyle = this.addEditForm.uploadBorderStyle
			el.style.borderColor = this.addEditForm.uploadBorderColor
			el.style.borderRadius = this.addEditForm.uploadBorderRadius
			el.style.backgroundColor = this.addEditForm.uploadBgColor
		  })
	  })
	},
  }
};
</script>
<style lang="scss">
.editor{
  height: 500px;
  
  & ::v-deep .ql-container {
    height: 310px;
  }
}
.amap-wrapper {
  width: 100%;
  height: 500px;
}
.search-box {
  position: absolute;
}
.addEdit-block {
  margin: -10px;
}
.detail-form-content {
  padding: 40px;
  background-color: transparent;
}
.btn .el-button {
  padding: 0;
}

/* 笔记内容盒子样式 */
.note-content-box {
  max-width: 1500px;
  margin: 0 auto;
  width: 100%;
  background-color: #fff;
  overflow: visible;
  padding: 0 8px;
  
  .note-content-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;
    padding-bottom: 12px;
    border-bottom: 1px solid #e8e8e8;
    
    .section-title {
      font-size: 14px;
      font-weight: 500;
      color: #333;
      position: relative;
      padding-left: 12px;
      
      &::before {
        content: '';
        position: absolute;
        left: 0;
        top: 50%;
        transform: translateY(-50%);
        width: 3px;
        height: 14px;
        background-color: #0050b3;
        border-radius: 2px;
      }
    }
    
    .header-buttons {
      display: flex;
      gap: 10px;
      align-items: center;
      
      .el-button--primary {
        background-color: #0050b3;
        border-color: #0050b3;
        
        &:hover {
          background-color: #0050b3;
          border-color: #0050b3;
        }
      }
    }
  }
  
  .note-content-editor {
    margin-bottom: 16px;
    
    #vditor-container {
      text-align: left;
      
      .vditor-reset {
        text-align: left !important;
        padding: 40px !important;
      }
      
      .vditor-ir {
        text-align: left !important;
      }
    }
  }
  
  .note-content-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 16px;
    border-top: 1px solid #f0f0f0;
    
    .footer-left {
      .char-count-box {
        padding: 5px;
        background-color: #fafafa;
        
        .char-count {
          font-size: 18px;
          color: #666;
        }
      }
    }
    
    .footer-right {
      display: flex;
      gap: 20px;
      align-items: center;
      
      .ai-buttons {
        display: flex;
        font-size: 18px;
        gap: 10px;
      }
    }
  }
}
</style>