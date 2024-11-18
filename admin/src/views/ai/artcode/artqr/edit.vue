<template>
  <div class="edit-popup">
    <popup
      ref="popupRef"
      :title="popupTitle"
      :async="true"
      width="550px"
      :clickModalClose="true"
      @confirm="handleSubmit"
      @close="handleClose"
    >
      <el-form
        ref="formRef"
        :model="formData"
        label-width="84px"
        :rules="formRules"
      >
        <el-form-item label="主键" prop="id">
          <el-input v-model="formData.id" placeholder="请输入主键" />
        </el-form-item>
        <el-form-item label="图片ID" prop="imgUuid">
          <el-input
            v-model="formData.imgUuid"
            placeholder="请输入图片ID"
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 6 }"
          />
        </el-form-item>
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="formData.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="预览地址" prop="urls">
          <el-input
            v-model="formData.urls"
            placeholder="请输入预览地址"
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 6 }"
          />
        </el-form-item>
        <el-form-item label="本地地址" prop="localUrls">
          <el-input v-model="formData.localUrls" placeholder="请输入本地地址" />
        </el-form-item>
        <el-form-item label="持续时间" prop="duration">
          <el-input v-model="formData.duration" placeholder="请输入持续时间" />
        </el-form-item>
        <el-form-item label="消耗点数" prop="cost">
          <el-input v-model="formData.cost" placeholder="请输入消耗点数" />
        </el-form-item>
        <el-form-item label="模型编号" prop="mode">
          <el-input v-model="formData.mode" placeholder="请输入模型编号" />
        </el-form-item>
        <el-form-item label="回调地址" prop="callbackOriginUrl">
          <el-input
            v-model="formData.callbackOriginUrl"
            placeholder="请输入回调地址"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status" placeholder="请选择状态">
            <el-radio label="0">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </popup>
  </div>
</template>
<script lang="ts" setup>
import type { FormInstance } from "element-plus";
import { artqrcodeEdit, artqrcodeAdd, artqrcodeDetail } from "@/api/ai/artcode";
import Popup from "@/components/popup/index.vue";
import feedback from "@/utils/feedback";
import type { PropType } from "vue";
defineProps({
  dictData: {
    type: Object as PropType<Record<string, any[]>>,
    default: () => ({}),
  },
});
const emit = defineEmits(["success", "close"]);
const formRef = shallowRef<FormInstance>();
const popupRef = shallowRef<InstanceType<typeof Popup>>();
const mode = ref("add");
const popupTitle = computed(() => {
  return mode.value == "edit" ? "编辑二维码生成" : "新增二维码生成";
});

const formData = reactive({
  id: "",
  imgUuid: "",
  userId: "",
  urls: "",
  localUrls: "",
  duration: "",
  cost: "",
  mode: "",
  callbackOriginUrl: "",
  status: "",
});

const formRules = {
  id: [
    {
      required: true,
      message: "请输入主键",
      trigger: ["blur"],
    },
  ],
  imgUuid: [
    {
      required: true,
      message: "请输入图片ID",
      trigger: ["blur"],
    },
  ],
  userId: [
    {
      required: true,
      message: "请输入用户ID",
      trigger: ["blur"],
    },
  ],
};

const handleSubmit = async () => {
  await formRef.value?.validate();
  const data: any = { ...formData };
  mode.value == "edit" ? await artqrcodeEdit(data) : await artqrcodeAdd(data);
  popupRef.value?.close();
  feedback.msgSuccess("操作成功");
  emit("success");
};

const open = (type = "add") => {
  mode.value = type;
  popupRef.value?.open();
};

const setFormData = async (data: Record<string, any>) => {
  for (const key in formData) {
    if (data[key] != null && data[key] != undefined) {
      //@ts-ignore
      formData[key] = data[key];
    }
  }
};

const getDetail = async (row: Record<string, any>) => {
  const data = await artqrcodeDetail({
    id: row.id,
  });
  setFormData(data);
};

const handleClose = () => {
  emit("close");
};

defineExpose({
  open,
  setFormData,
  getDetail,
});
</script>
