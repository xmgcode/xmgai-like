<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <div class="package">
          <div class="title-wrapper">
            <span class="title">套餐一</span>
          </div>
          <div class="details">
            <div class="detail-price">¥9.9/<span style="font-size:15px;">99点数</span></div>
            <div class="detail-give">合计：99点数</div>
            <div class="detail-total"></div>
            <button class="purchase-button" @click="purchase('comboOne')">购买</button>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="package">
          <div class="title-wrapper">
            <span class="title">套餐二</span>
          </div>
          <div class="details">
            <div class="detail-price">¥29.9/<span style="font-size:15px;">299点数</span></div>
            <div class="detail-give">赠送：11点数</div>
            <div class="detail-total">合计：310点数</div>
            <button class="purchase-button" @click="purchase('comboTwo')">购买</button>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="package">
          <div class="title-wrapper">
            <span class="title">套餐三</span>
          </div>
          <div class="details">
            <div class="detail-price">¥99.9/<span style="font-size:15px;">999点数</span></div>
            <div class="detail-give">赠送：51点数</div>
            <div class="detail-total">合计：1050点数</div>
            <button class="purchase-button" @click="purchase('comboThree')">购买</button>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 添加一个图片展示区域 -->
    <div v-if="showImage" class="image-popup">
       <h3 class="image-title">微信扫码支付</h3>
      <img :src="imageSrc" alt=""/>
      <el-row>
        <el-col :span="12">
          <el-button class="close-button" @click="hideImage">关闭</el-button>
        </el-col>
        <el-col  :span= "12">
            <button class="close-button" @click="alreadyPay">已支付</button>
        </el-col>
      </el-row>
      
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, reactive, shallowRef } from "vue";
import { ElRow, ElCol, ElButton } from "element-plus";
import {weChatPay} from "~~/api/shop";
import { useRoute } from "vue-router";
import RichEditor from "../../../layouts/components/wangedit/wangedit.vue";
import feedback from "~~/utils/feedback";

const router = useRouter();

const loading = ref(true); // 加载中 

const showImage = ref(false); // 控制图片显示的布尔值
const imageSrc = ref("");//返回的支付二维码

const comboPara = reactive({
  comboType: "",
});


const purchase = async (params) => {
  comboPara.comboType = params;
  try {
    const resdata = await weChatPay(comboPara); 
    showImage.value = true; // 购买后显示图片
    imageSrc.value = resdata;
  } finally {
    loading.value = false; // 加载完成或失败后，重置loading为false
  }
};

const hideImage = () => {
  showImage.value = false; // 点击关闭按钮隐藏图片
};


//已支付点击跳转
const alreadyPay = () => {
    router.push({
      path: '/user/points',
    })
};



onMounted(async () => {

});
</script>

<style scoped>
.package {
  display: flex;
  flex-direction: column;
  height: 400px; /* 修改高度为300px */
  border: 2px solid #007bff; /* 科技蓝色边框，颜色值可按需调整 */
  border-radius: 10px; /* 设置四角圆弧，数值越大圆弧越明显 */
}

.title-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

.title {
  font-size: 30px;
  font-weight: bold;
}

.details {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.detail-price {
  text-align: center;
  flex: 1;
  display: flex;
  font-size: 25px;
  justify-content: center;
  align-items: center;
  font-weight: bold;
}

.detail-give {
  text-align: center;
  flex: 1;
  display: flex;
  font-size: 20px;
  justify-content: center;
  align-items: center;
}

.detail-total{
  text-align: center;
  flex: 1;
  display: flex;
  font-size: 20px;
  justify-content: center;
  align-items: center;
}


.el-col {
  border-radius: 4px;
}


.el-row {
  margin-top: 15px;
}

.purchase-button {
  width: 50%; /* 或具体像素值，确保按钮宽度适配 */
  margin: 10px auto 10px auto;
  padding: 10px 0; /* 垂直内边距，可根据需要调整 */
  border-radius: 50px; /* 椭圆形状，调整数值可改变圆润程度 */
  background-color: #007bff; /* 科技蓝色背景 */
  color: white; /* 白色字体 */
  border: none; /* 无边框 */
  cursor: pointer; /* 鼠标悬停时显示手型 */
  font-size: 16px; /* 字体大小 */
  margin-top: auto; /* 使按钮自动定位到底部 */
  display: block; /* 作为块级元素处理，确保宽度生效 */
  text-align: center; /* 文字居中 */
}


.image-popup {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 999;
  background-color: rgba(0, 0, 0, 0.5);
  padding: 20px;
  border-radius: 10px;
  text-align: center;
}

/* 确保图片居中显示 */
.image-popup img {
  display: block; 
  margin: 0 auto;
  max-width: 80%;
  height: auto;
}

/* 提升关闭按钮的科技感设计 */
.close-button {
  width: 100px;
  height: 35px;
  margin-top: 20px; /* 增加与图片的间距 */
  padding: 8px 16px; /* 调整按钮尺寸以适应新设计 */
  border: none;
  background-color: #3498db; /* 更改背景色以匹配科技主题 */
  color: white;
  cursor: pointer;
  border-radius: 50px; /* 圆形按钮设计 */
  font-size: 16px;
  font-weight: bold;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
  transition: all 0.3s ease; /* 平滑过渡效果 */
}

.close-button:hover {
  background-color: #2980b9; /* 悬停时颜色变深，增强交互感 */
  transform: translateY(-2px); /* 微小上移动画 */
}

.image-title {
  text-align: center;
  color: white;
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px; /* 根据需要调整底部外边距 */
}


</style>
