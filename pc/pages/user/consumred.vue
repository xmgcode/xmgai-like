<template>
  <div class="px-[30px] py-5 user-info">
    <div class="border-b border-br pb-5">
      <div class="user-info">
        <div class="section">
          <div class="recorse-list">
            <el-row>
              <el-col :span="24" class="recorse-title">点数消耗记录</el-col>
            </el-row>
            <el-table :data="myUserRecords" border  height="250" style="width: 100%">
              <el-table-column prop="scene" label="使用场景" width="180" />
              <el-table-column prop="consume" label="消耗点数" width="180"/>
              <el-table-column prop="createTime" label="消耗时间" >
                 <template #default="{ row }">
                  <!-- 使用 formatter 函数格式化日期 -->
                  {{ formatDate(row.createTime) }}
                </template>
              </el-table-column>
            </el-table>
          </div>
          <!-- 使用记录的内容 -->
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import *  as xmgai from "@/api/ai";
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
  ElDialog,
  ElDivider,
  ElTable,
} from "element-plus";
import {
  useAccount,
  PopupTypeEnum,
} from "@/layouts/components/account/useAccount";
import feedback from "~~/utils/feedback";
import { useUserStore } from "~~/stores/user";
import { getApiKey } from "~~/api/user";
import ElTableColumn from "element-plus/es/components/table/src/tableColumn";
const router = useRouter();

const route = useRoute();

const loading = ref(true); // 列表的加载中

const claasifyList = ref([]); // 分类明细列表的数据

const params = reactive({
  contentId: "",
});

const myRemainPoints = ref();
const myUserRecords = ref([]);

const toPageParams = reactive({
  apifId: "",
});





const myPoints = async () => {
  try {
    const data = await xmgai.getPionts(); // 修正函数调用，添加括号
    if (data.code === 1) {
      myRemainPoints.value = data.data; // 更新积分值
    }
  } catch (error) {
    console.error('获取积分失败:', error);
  }
};


const recordList = async () => {
  try {
    const data = await xmgai.getConsumred(); // 修正函数调用，添加括号
    console.log("data",data);
    if (data.code === 1) {
      myUserRecords.value = data.data; // 更新积分值
    }
  } catch (error) {
    console.error('获取记录失败:', error);
  }
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





onMounted(() => {
  // myPoints();
  recordList();
});

// 用户资料
enum UserFieldEnum {
  NONE = "",
  AVATAR = "avatar",
  USERNAME = "username",
  NICKNAME = "nickname",
  SEX = "sex",
}

const { data: userInfo, refresh } = await useAsyncData(() => getUserInfo(), {
  initialCache: false,
});

definePageMeta({
  module: "personal",
  auth: true,
});
</script>
<style lang="scss" scoped>

.dialog-content {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  height: 100%; /* 确保父容器有高度 */
  width: 100%;
  margin: 10px 0 10px 0;
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.user-info {
  padding: 30px;
}

.section {
  margin-bottom: 20px;
}

.recorse-list {
  margin-top: 20px;
  margin-bottom: 10px;
}

.recorse-title{
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
  text-align: center;
}

.view-key-button {
  margin-top: 20px; /* 按钮顶部边距 */
  text-align: center; /* 居中文本 */
  width: 200px;
  height: 30px;

}

.points-title{
  font-size:25px;
  height: 50px;
  align-items: center;
  justify-content: center;
  display: flex;
  font-weight:bold;
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
</style>
