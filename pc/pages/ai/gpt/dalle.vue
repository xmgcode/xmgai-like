<template>
  <div>
    <div class="page-dall">
      <el-row>
        <el-col :span="6">
          <div class="inner">
            <div class="sd-box">
              <h2>DALL-E 创作中心</h2>
              <div>
                <el-form label-position="left">
                  <div style="padding-top: 10px">
                    <el-form-item :label-style="{ color: 'white' }" label="图片尺寸">
                      <template #default>
                        <div>
                          <el-select v-model="selectedValue" @change="updateSize" style="width:176px">
                            <el-option label="1024*1024" value="1024*1024"/>
                            <el-option label="1972*1024" value="1972*1024"/>
                            <el-option label="1024*1972" value="1024*1972"/>
                          </el-select>
                        </div>
                      </template>
                    </el-form-item>
                  </div>

                  <div style="padding-top: 10px">
                    <div class="param-line">
                        <el-input
                            v-model="dalleParams.prompt"
                            :autosize="{ minRows: 4, maxRows: 6 }"
                            type="textarea"
                            ref="promptRef"
                            placeholder="请在此输入绘画提示词，系统会自动翻译中文提示词，高手请直接输入英文提示词"
                        />
                      </div>
                  </div>
                </el-form>
              </div>
              <div class="submit-btn">
                <el-button color="#ffffff" :loading="loading" :dark="false" round @click="generate">
                  立即生成
                </el-button>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="18">
          <div class="inner">
            <div class="right-box">
              <h2>创作记录</h2>
              <div>
                <el-form label-position="left">
                   <div class="container">
                    <WaterFall :columns="columns" :gap="10" :images="images" :fetchMoreImages="fetchMoreImages" :isEnd="isEnd">
                      <template #item="{ image }">
                        <div class="card-box">
                          <el-image :src="image.url" @click="previewImg(image)" alt="waterfall image" fit="cover" style="width: 100%; height: 100%;cursor:pointer;" loading="lazy"></el-image>
                          

                          <!-- <div class="remove">
                            <el-tooltip content="删除" placement="top" effect="light">
                              <el-button type="danger" :icon="Delete" circle/>
                            </el-tooltip>
                            <el-tooltip content="分享" placement="top" effect="light">
                              <el-button type="warning"
                                        circle>
                                <i class="iconfont icon-cancel-share"></i>
                              </el-button>
                            </el-tooltip>
                            <el-tooltip content="取消分享" placement="top" effect="light">
                              <el-button type="success" circle>
                                <i class="iconfont icon-share-bold"></i>
                              </el-button>
                            </el-tooltip>

                            <el-tooltip content="复制提示词" placement="top" effect="light">
                              <el-button type="info" circle class="copy-prompt">
                                <i class="iconfont icon-file"></i>
                              </el-button>
                            </el-tooltip>
                        </div> -->

                        
                        </div>
                      </template>
                    </WaterFall>
                  </div>
                </el-form>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-image-viewer @close="() => { previewURL = '' }" v-if="previewURL !== ''"  :url-list="[previewURL]"/>
  </div>
</template>

<script lang="ts" setup>
import { ElUpload, ElImage, ElDialog, ElRow, ElCol, ElButton, ElIcon, ElTag, ElInput, ElSelect, ElTooltip, ElForm, ElFormItem, ElOption ,ElImageViewer} from "element-plus";
import {Delete, InfoFilled, Picture} from "@element-plus/icons-vue";
import feedback from "~~/utils/feedback";
import { useUserStore } from '@/stores/user';
import WaterFall from '@/components/waterfall/index.vue';
import * as xmgai from "~~/api/ai";

// 获取图片前缀
const config = useRuntimeConfig();
const filePrefix = config.public.filePrefix;

const router = useRouter();

const selectedValue = ref('1024*1024');

const previewURL = ref("")

const loading = ref(false);


// 请求参数
const dalleParams = reactive({
  size:"1024*1024",
  prompt: ""
});

// 创建绘图任务
const promptRef = ref(null);


const updateSize = () => {
  dalleParams.size = selectedValue.value;
};


const generate = async () => {
  loading.value = true;
  if (dalleParams.prompt === '') {
    promptRef.value.focus();
    loading.value = false;
    return feedback.msgError("请输入绘画提示词！");
    
  }

  const ctdata = await xmgai.dalle3(dalleParams);
  console.info("ctdata",ctdata);
  if (ctdata.code === 0) {
    feedback.msgError(ctdata.msg);
    loading.value = false;
    return [];
  }

  if (ctdata.code === 1) {
    // 获取新生成的图片地址
    const newImage = {
      url: filePrefix +  ctdata.data,
      width: 300 + Math.random() * 300,
      height: 400 + Math.random() * 300,
    };

    // 将新图片插入到 images 数组的开头
     // 将新图片插入到 images 数组的开头
    images.value = [newImage, ...images.value];

    // 将 WaterFall 组件的滚动条滚动到顶部
    nextTick(() => {
      const waterfallContainer = document.querySelector('.waterfall-container');
      if (waterfallContainer) {
        waterfallContainer.scrollTop = 0;
      }
    });

    feedback.msgSuccess(ctdata.msg);
    loading.value = false;
  }

};

const images = ref([]);
const pageNo = ref(1);
const pageSize = ref(10);
const isEnd = ref(false);

// 请求参数
const paramsCreate = reactive({
  aiType: "dalle3",
  pageNo: pageNo.value,
  pageSize: pageSize.value,
});

const fetchImages = async () => {
  const ctdata = await xmgai.aiList(paramsCreate);
  if (ctdata.code === 0) {
    feedback.msgError(ctdata.msg);
    return [];
  }

  if (ctdata.code === 1) {
    const data = ctdata.data.lists;
    if (data.length === 0) {
      isEnd.value = true;
      return [];
    }
    paramsCreate.pageNo++;
    return data.map(item => ({
      ...item, // 保留所有原始字段
      url: filePrefix + item.localUrls,
      width: 300 + Math.random() * 300,
      height: 400 + Math.random() * 300,
    }));
  }
};

const fetchMoreImages = async () => {
  if (isEnd.value) {
    return; // 如果已经没有更多数据了，直接返回
  }
  const newImages = await fetchImages();
  images.value = [...newImages];
};


// 列数设置
const columns = ref(4); // 你可以在这里修改列数
//放大预览
const previewImg = (item) => {
  console.info("item",item.url);
  previewURL.value = item.url
}



onMounted(async () => {
  const initialImages = await fetchImages();
  images.value = initialImages;
});

</script>

<style scoped>
.page-dall {
  background-color: #0c1c9181;
  border-radius: 10px; /* 所有角的圆角大小相同 */
  border: 1px solid #3399FF;
}

.page-dall .inner {
  display: flex;
}

.page-dall .inner .sd-box {
  margin: 10px;
  background-color: #222542b4;
  width: 100%;
  padding: 10px;
  border-radius: 10px;
  color: #ffffff;
  font-size: 14px;
}

.page-dall .inner .sd-box h2 {
  font-weight: bold;
  font-size: 20px;
  text-align: center;
  color: #ffffff;
}

.page-dall .inner .right-box {
  margin: 10px;
  background-color: #222542b4;
  width: 100%;
  padding: 10px;
  border-radius: 10px;
  color: #ffffff;
  font-size: 14px;
}

.page-dall .inner .right-box h2 {
  font-weight: bold;
  font-size: 20px;
  text-align: center;
  color: #ffffff;
}

.submit-btn {
  padding: 10px 15px 0 15px;
  text-align: center;
}

::v-deep(.el-form-item__label) {
  color: white !important;
}

.container {
  height: 600px;
  border: 2px solid #000;
  margin-top: 10px;
  margin-left: auto;
  margin-right: auto; /* 添加居中处理 */
}

.card-box {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 4px;
  overflow: hidden;
}

.card-box img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}



.card-box .remove {
  display: none;
  position: absolute;
  right: 10px;
  top: 10px;
}

.card-box:hover .remove {
  display: block;
}
</style>