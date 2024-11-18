<template>
  <div class="container">
    <el-row >
      <el-col :span="24" class="oneTitle">
        <span >AI换脸</span>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8">
        <div class="imageCla">
          <el-image
            class="faceimage"
            src="http://aipic.xmgai.cn/faceswap/11.png"
            fit="cover"
            @click="handleFace(item)"
          >
          </el-image>
        </div>
        <div class="image-description">原图</div>
      </el-col>

      <el-col :span="8">
        <div class="imageCla">
          <el-image
            class="faceimage"
            src="http://aipic.xmgai.cn/faceswap/22.jpg"
            fit="cover"
            @click="handleFace(item)"
          >
          </el-image>
        </div>
        <div class="image-description">目标图</div>
      </el-col>

      <el-col :span="8">
        <div class="imageCla">
          <el-image
            class="faceimage"
            src="http://aipic.xmgai.cn/faceswap/33.jpg"
            fit="cover"
            @click="handleFace(item)"
          >
          </el-image>
        </div>
        <div class="image-description">成果</div>
      </el-col>
    </el-row>


    <el-row :gutter="20">
      <el-col :span="8">
        <div class="imageCla">
          <el-image
            class="faceimage"
            src="http://aipic.xmgai.cn/faceswap/1.jpg"
            fit="cover"
            @click="handleFace(item)"
          >
          </el-image>
        </div>
        <div class="image-description">原图</div>
      </el-col>

      <el-col :span="8">
        <div class="imageCla">
          <el-image
            class="faceimage"
            src="http://aipic.xmgai.cn/faceswap/2.jpg"
            fit="cover"
            @click="handleFace(item)"
          >
          </el-image>
        </div>
        <div class="image-description">目标图</div>
      </el-col>

      <el-col :span="8">
        <div class="imageCla">
          <el-image
            class="faceimage"
            src="http://aipic.xmgai.cn/faceswap/3.jpg"
            fit="cover"
            @click="handleFace(item)"
          >
          </el-image>
        </div>
        <div class="image-description">成果</div>
      </el-col>
    </el-row>






    <el-row >
      <el-col :span="24" class="oneTitle">
        <span >独一无二的艺术二维码</span>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="6" v-for="(item, index) in claasifyList" :key="index">
        <div class="imageCla">
          <el-image
            class="dialog-image"
            :src="item.previewImg"
            fit="cover"
            @click="handleTemplateSelect(item)"
          >
          </el-image>
        </div>
        <!-- <div class="image-description">{{ filePrefix + item.previewImg }}</div> -->
        <div class="image-description">{{ item.name }}</div>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";

import {
  ElContainer,
  ElAside,
  ElMenu,
  ElMenuItem,
  ElMain,
  ElRow,
  ElCol,
  ElCard,
  ElAvatar,
  ElSpace,
  ElButton,
  ElImage,
  ElDialog,
} from "element-plus";


import { useAccount, PopupTypeEnum } from "@/layouts/components/account/useAccount";
import { getDatacont, getListApif } from "~~/api/hot";
import { wechatLoginCallback } from "~~/api/account";
import { getModelList,createArtcode,getQrCode} from "~~/api/ai";
import { useUserStore } from "@/stores/user";
import feedback from "~~/utils/feedback";
const { setPopupType, toggleShowPopup } = useAccount();
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

//获取图片前缀
const config = useRuntimeConfig();
const filePrefix = config.public.filePrefix;

console.log("filePrefix",filePrefix);


// 模板列表
const claasifyList = ref([]);

const params = reactive({
  contentId: "",
});


const classifyList = async () => {
  const resdata = await getModelList();
  console.log("123123123",resdata);
  if(resdata.code === 1){
    claasifyList.value = resdata.data;
  }
  if(resdata.code === 0){
    feedback.msgError("获取模型失败！");
  }
 
};

const handleTemplateSelect = (item) => {
  router.push({
    path: '/artcode',
  })
};


const handleFace = (item) => {
  router.push({
    path: '/ai/mj/aiswap',
  })
};



onMounted(() => {
  classifyList();
});


</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column; /* 改变排列方向为垂直 */
}


.dialog-image {
  margin: 10px 5px; /* 上下各10像素的间距 */
  width: 200px; 
  height: 200px; 
  object-fit: cover; 
  cursor: pointer;
  
}


.faceimage {
  margin: 10px 5px; /* 上下各10像素的间距 */
  width: 300px; 
  height: 400px; 
  object-fit: cover; 
  cursor: pointer;
  
}

.imageCla{
  display: flex;
  flex-direction: column; /* 确保内容垂直排列 */
  justify-content: center; /* 内容垂直居中 */
  align-items: center;     /* 内容水平居中 */
}


.image-description {
  text-align: center;
  margin-top: 5px; /* 调整文字描述与图片之间的间距 */
  font-size: 14px;
  color: #333; /* 文字颜色 */
}

.oneTitle{
  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中，可选 */
  height: 50px;
  font-size: 20px;
  text-align: center;
  margin: 20px 20px;
  font-weight:bolder;
  background-image: linear-gradient(135deg, #eb7ace, #2f41e6, #bdd631, #eb7ace);
  background-size: 200% 100%;
  animation: GenerateButton_gradient 10s cubic-bezier(.62,.28,.23,.99) infinite;
  border: none;
  border-radius: 6px;
  color: #fff;
  cursor: pointer;
  overflow: hidden;
  user-select: none;
  transition: all .10s ease-in-out;
}


</style>