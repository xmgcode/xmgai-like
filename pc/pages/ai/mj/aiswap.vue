<template>
  <div class="container">
    <!-- 左侧标题区域 -->
    <div class="left-titles">
      <!-- 第一个标题 -->
      <el-row>
        <el-col :span="24" class="title">一、上传人脸原图片</el-col>
      </el-row>
      
      <!-- 上传组件 -->
      <el-row>
        <el-col :span="24" class="image-wrapper">
          <el-upload
            class="upload-demo custom-border"
            list-type="picture-card"
            :on-change="uploadOriginPic"
            :before-remove="beforeRemove"
            :limit="1"
          >
            <el-icon class="el-icon--upload"></el-icon>
            <div class="el-upload__text">点击或拖拽文件到此处上传</div>
            <div class="el-upload__text">支持jpg、png</div>
          </el-upload>
        </el-col>
      </el-row>
      
      <!-- 第二个标题 -->
      <el-row>
        <el-col :span="24" class="title">二、上传目标人脸图片</el-col>
      </el-row>
      
      <el-row>
        <el-col :span="24" class="image-wrapper">
          <el-upload
            class="upload-demo custom-border"
            list-type="picture-card"
            :on-change="uploadTargetPic"
            :limit="1"
          >
            <el-icon class="el-icon--upload"></el-icon>
            <div class="el-upload__text">点击或拖拽文件到此处上传</div>
            <div class="el-upload__text">支持jpg、png</div>
          </el-upload>
        </el-col>
      </el-row>
    </div>

    <!-- 分割线 -->
    <div class="divider"></div>

    <!-- 右侧标题区域 -->
    <div class="right-title">
      <!-- 第三个标题 -->
      <el-row>
        <el-col :span="24" class="threetitle">作品展示</el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div class="image-preview">
            <el-image
              style="width: 300px; height: 300px;"
              :src="resultpic"
              fit="cover"
            >
              <template #error>
                <div class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </template>
            </el-image>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div class="button-group">
            <el-button type="primary" :loading="loading" class="butcls" @click="handleSubmit">{{butMsg}}</el-button>
          </div>
        </el-col>
        <el-col :span="24">
          <div class="desc-group">
            <span>生成时间2分钟左右，再次点击生成会有惊喜哦，生成后请点击图片右键保存！</span>
          </div>
        </el-col>
        <el-col :span="24">
          <div class="desc-group">
            <span>也可到
              <el-button type="primary" style="height:20px;" @click="goRecord">创作记录</el-button>
              中查看！</span>
          </div>
        </el-col>
      </el-row>
    </div>


  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { ElUpload, ElImage, ElDialog, ElRow, ElCol, ElButton,ElIcon} from "element-plus";
import * as xmgai from "~~/api/ai";
import modelPicImage from "@/assets/images/yangli.png";
import resultImage from "@/assets/images/yangli.png";
import feedback from "~~/utils/feedback";
import { useUserStore } from '@/stores/user';
const router = useRouter()

//获取图片前缀
const config = useRuntimeConfig();
const filePrefix = config.public.filePrefix;

const loading = ref(false);
// 模板列表
const claasifyList = ref([]);
// 模板图片
const modelImage = ref();
// 结果图片
const resultpic = ref();
// base64图片
const base64Image = ref();
// 对话框
const dialogVisible = ref(false);
// 文件模板ID
const modelId = ref();
//请求参数
const paramsCreate = reactive({

});
//查询二维码参数
const paramsCode = reactive({
  taskId: "",
});

// 按钮消息
const butMsg = ref();

// 定义一个变量来保存定时器的 ID
const intervalId = ref(null); // 使用 ref 来存储定时器 ID

const originFile = ref<File | null>(null);
const targetFile = ref<File | null>(null);


const uploadOriginPic = (file) => {
  originFile.value = file.raw;
};

const uploadTargetPic = (file) => {
  targetFile.value = file.raw;
};

const beforeRemove = (file, fileList) => {
  if (file.raw === originFile.value) {
    originFile.value = null;
  } else if (file.raw === targetFile.value) {
    targetFile.value = null;
  }
  return true;
};

const handleSubmit = async () => {
// 构造 FormData 对象
  const formData = new FormData();
  formData.append('originFile', originFile.value);
  formData.append('targetFile', targetFile.value);

  const userStore = useUserStore()
  const token = userStore.token

  if(null == token){
    feedback.msgError("请先登录！");
    return;
  }

  if(null == originFile.value){
    feedback.msgError("请先上传原图片！");
    return;
  }

 if(null == targetFile.value){
    feedback.msgError("请先上传目标图片！");
    return;
  }

  //点击就开始加载，防止多次点击生成
  butMsg.value = "拼命生成中，请稍后！";
  loading.value = true;
  const ctdata = await xmgai.aiSwap(formData);
  console.log("ctdata",ctdata);
  if(ctdata.code === 0){
    feedback.msgError(ctdata.msg);
    butMsg.value = "立即换脸（消耗4点数）";
    loading.value = false;
    return;
  }

  if(ctdata.code === 1){
    butMsg.value = "拼命生成中，请稍后！";
    console.info("生成的图片ID为：" + ctdata.data)
    //获得图片ID
    paramsCode.taskId = ctdata.data;
    // paramsCode.imageId = "baf27a71-417c-484e-a4b9-e86e62fc5a10";
    intervalId.value = setInterval(getSwapUrl, 10000);
  }


};

const getSwapUrl = async () => {
    const gcdata = await xmgai.getSwapUrl(paramsCode);
    //二维码生成中
    if(gcdata.code === 0){
      butMsg.value = "拼命生成中，请稍后！";
    }
    //二维码生成完成
    if(gcdata.code === 1){
        if('100%' == gcdata.data.progress){
            resultpic.value =  gcdata.data.imageUrl;
            console.info("二维码访问链接为：" + resultpic.value );
            clearInterval(intervalId.value); // 组件卸载时清除定时器
            console.info("定时器已清除！");
            loading.value = false;
            butMsg.value = "立即换脸（消耗4点数）！";
        }
      
    }
}




const handleTemplateSelect = (item) => {
  modelImage.value = item.previewImg;
  modelId.value = item.modelNum;
  dialogVisible.value = false; // 关闭对话框
};


const goRecord = () => {
    router.push({
      path: '/artcode/history',
    })
};

// onMounted(() => {
//   intervalId.value = setInterval(getQrCodeUrl, 10000);
// });

onUnmounted(() => {
  if (intervalId.value) {
    clearInterval(intervalId.value); // 组件卸载时清除定时器
  }
});

onMounted(() => {
  butMsg.value = "立即换脸（消耗4点数）";
  modelImage.value = modelPicImage;
  resultpic.value = resultImage;
});
</script>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  height: 100%;
  background-color: transparent;
}

.title{
  font-size: 20px;
  font-weight: bold;
  color: #f014a6;
}

.left-titles {
  flex: 1;
}

.right-title {
  flex: 1;
}

.divider {
  width: 1px;
  background-color: #ccc;
  margin: 0 10px;
}

.el-image {
  margin: 0 auto;
  display: block;
}

.image-wrapper {
  display: flex; /* 使用 Flexbox 布局 */
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
  margin: 0 auto;
}

@keyframes GenerateButton_gradient {
  0% { background-position: 0% 0%; }
  100% { background-position: 100% 0%; }
}

.button-group {
  margin: 20px auto;
  width: 300px;
  text-align: center;
}

.desc-group{
  margin: 5px auto;
  width: 350px;
  font-size: 15px;
  font-weight: bold;
  color: rgb(243, 93, 123);
  text-align: center;
}

.butcls {
  font-size:20px;
  font-weight:bold;
  width:300px;
  height:40px;
  background-image: linear-gradient(135deg, #eb7ace, #2f41e6, #bdd631, #eb7ace);
  background-size: 200% 100%;
  animation: GenerateButton_gradient 10s cubic-bezier(.62,.28,.23,.99) infinite;
  border: none;
  border-radius: 6px;
  color: #fff;
  cursor: pointer;
  overflow: hidden;
  user-select: none;
  transition: all .3s ease-in-out;
}

.butcls:disabled,
.butcls.is-loading {
  opacity: 0.5;
  cursor: not-allowed;
}

.title-wrapper {
  justify-content: center;
  margin: 0 auto;
  margin-top: 20px;
  display: flex;
  font-size:20px;
  font-weight:bold;
  width:150px;
  height:40px;
  background-image: linear-gradient(135deg, #e966b2, #8a52ec, #dbfa86, #e966b2);
  background-size: 200% 100%;
  animation: GenerateButton_gradient 10s cubic-bezier(.62,.28,.23,.99) infinite;
  border: none;
  border-radius: 6px;
  color: #fff;
  cursor: pointer;
  overflow: hidden;
  user-select: none;
  transition: all .3s ease-in-out;
}

.image-preview {
  text-align: center;
}


.dialog-image {
  margin: 10px 5px; /* 上下各10像素的间距 */
  width: 200px; 
  height: 200px; 
  object-fit: cover; 
  cursor: pointer;
}


.el-dialog-div {
  height: 700px;
  overflow-y: auto;
}

.image-description {
  text-align: center;
  margin-top: 5px; /* 调整文字描述与图片之间的间距 */
  font-size: 14px;
  color: #333; /* 文字颜色 */
}

.threetitle{
  font-size:30px;
  text-align: center;
  color: #f014a6;
  font-weight: bold;
  margin-top: 20px;
}

.custom-border {
  border: 2px solid #409EFF; /* 设置边框颜色为 Element UI 主题色，可以根据需要更改 */
  border-radius: 4px; /* 设置边框圆角 */
  padding: 10px; /* 可选：增加内边距，使得边框不会紧贴内容 */
}

</style>
