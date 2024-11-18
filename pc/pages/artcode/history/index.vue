<template>
  <div class="container">
    <el-row >
      <el-col :span="24" class="oneTitle">
        <span >创作记录</span>
      </el-col>
    </el-row>
    <el-row :gutter="20">
          <el-col :span="6" v-for="(item, index) in claasifyList" :key="index">
            <div class="imageCla">
              <el-image
                :initial-index="initialIndex"
                class="dialog-image"
                :src="item.localUrls"
                :preview-src-list="srcList"
                :lazy="true"
                fit="cover"
                @click="openPreview(index)"
              >
              </el-image>
            </div>
            <div class="image-description"> {{ formatDate(item.createTime) }}</div>
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
import * as xmgai from "~~/api/ai";
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


// 模板列表
const claasifyList = ref([]);

const initialIndex = ref(0); // 添加这个引用



// 预览列表
const srcList = ref([]);

const params = reactive({
  contentId: "",
});


const classifyList = async () => {
  const data = await xmgai.getArtRecords();
  console.log("data",data);
  if(data.code === 1){
    claasifyList.value = data.data;
    for (let i = 0; i < data.data.length; i++) {
      srcList.value.push(data.data[i].localUrls);
    }
    console.info("srcList",srcList)
  } else{
    feedback.msgError("查询失败，请联系管理员！");
  }
  
 
};

const handleTemplateSelect = (item) => {
  router.push({
    path: '/artcode',
  })
};


// 格式化日期的方法
const formatDate = (value: string) => {
  const date = new Date(value);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  const seconds = date.getSeconds().toString().padStart(2, '0');
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};


const openPreview = (index: number) => {
  initialIndex.value = index; // 当点击图片时，设置初始索引为当前图片的索引
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