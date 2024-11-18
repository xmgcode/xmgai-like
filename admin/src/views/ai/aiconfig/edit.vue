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
        <el-form-item label="类型" prop="type">
          <el-input v-model="formData.type" placeholder="请输入类型" />
        </el-form-item>
        <el-form-item label="键" prop="name">
          <el-input v-model="formData.name" placeholder="请输入键" />
        </el-form-item>
        <el-form-item label="值" prop="value">
          <el-input
            v-model="formData.value"
            placeholder="请输入值"
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 6 }"
          />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="formData.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
    </popup>
  </div>
</template>
<script lang="ts" setup>
import type { FormInstance } from "element-plus";
import { aiconfigEdit, aiconfigAdd, aiconfigDetail } from "@/api/ai/aiconfig";
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
  return mode.value == "edit" ? "编辑AI配置" : "新增AI配置";
});

const formData = reactive({
  id: "",
  type: "",
  name: "",
  value: "",
  remark: "",
});

const formRules = {
  id: [
    {
      required: true,
      message: "请输入主键",
      trigger: ["blur"],
    },
  ],
  type: [
    {
      required: true,
      message: "请输入类型",
      trigger: ["blur"],
    },
  ],
  name: [
    {
      required: true,
      message: "请输入键",
      trigger: ["blur"],
    },
  ],
  value: [
    {
      required: true,
      message: "请输入值",
      trigger: ["blur"],
    },
  ],
};

const handleSubmit = async () => {
  await formRef.value?.validate();
  const data: any = { ...formData };
  mode.value == "edit" ? await aiconfigEdit(data) : await aiconfigAdd(data);
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
  const data = await aiconfigDetail({
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
