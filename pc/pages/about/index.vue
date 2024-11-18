<template>
  <div >
    <el-row :gutter="20">
      <span class="desc">副业项目：搭建自己的AI网站，增加一份额外收入，请微信扫码咨询！</span>
    </el-row>


     <el-row :gutter="20">
          <el-image style="width:300px;height:300px;" src="http://aipic.xmgai.cn/model/1.png" ></el-image>
        
    </el-row>
    
    <el-row :gutter="20">
        <a class="clashref" target="_blank" href="https://xmgtoagi.feishu.cn/wiki/FNcgwRywfitcO8kVzclchkRanJb">关于小码哥AI平台</a>
    </el-row>

    <el-row :gutter="20">
        <a class="clashref" target="_blank" href="https://xmgtoagi.feishu.cn/wiki/WNYJwxb7BibsP8kMVe0c1XIEnke">AI副业项目拆解</a>
    </el-row>

    <el-row :gutter="20">
        <a class="clashref" target="_blank" href="https://xmgtoagi.feishu.cn/wiki/S9BwwRcXciCz8BkA4uoc67UBnBf">AI学习基地</a>
    </el-row>
    

  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, reactive, shallowRef } from "vue";
import { ElRow, ElCol, ElButton,ElImage } from "element-plus";
import {weChatPay} from "~~/api/shop";
import { useRoute } from "vue-router";
import RichEditor from "../../../layouts/components/wangedit/wangedit.vue";
import feedback from "~~/utils/feedback";

const router = useRouter();

const loading = ref(true); // 加载中 





onMounted(async () => {

});
</script>

<style scoped>

.imgsty{
  width:300px;
  height:300px;
}

.el-row{
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px;
}

.desc{
  margin: 20px 0px;
  font-size:20px;
  font-weight:bold;
  background-image: linear-gradient(135deg, #f54bc2, #e26923, #bdd631, #57f164);
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


.clashref {
  position: relative;
  display: inline-block;
  padding: 10px 20px;
  margin: 20px 0px;
  font-size: 20px;
  font-weight: bold;
  color: blue;
  background-size: 200% 100%;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  overflow: hidden;
  user-select: none;
  text-decoration: underline;
  transition: text-decoration 0.3s ease; /* 添加过渡效果 */
}

.clashref:hover {
  text-decoration: none;
}

</style>
