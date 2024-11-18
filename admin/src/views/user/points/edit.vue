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
        <el-form-item label="总点数" prop="totalPoints">
          <el-input v-model="formData.totalPoints" placeholder="请输入总点数" />
        </el-form-item>
        <el-form-item label="剩余点数" prop="remainPoints">
          <el-input
            v-model="formData.remainPoints"
            placeholder="请输入剩余点数"
          />
        </el-form-item>
      </el-form>
    </popup>
  </div>
</template>
<script lang="ts" setup>
import type { FormInstance } from "element-plus";
import { pointsEdit, pointsAdd, pointsDetail } from "@/api/user/points";
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
  return mode.value == "edit" ? "编辑用户点数" : "新增用户点数";
});

const formData = reactive({
  id: "",
  totalPoints: "",
  remainPoints: "",
});

const formRules = {
  id: [
    {
      required: true,
      message: "请输入主键",
      trigger: ["blur"],
    },
  ],
  totalPoints: [
    {
      required: true,
      message: "请输入总点数",
      trigger: ["blur"],
    },
  ],
  remainPoints: [
    {
      required: true,
      message: "请输入剩余点数",
      trigger: ["blur"],
    },
  ],
};

const handleSubmit = async () => {
  await formRef.value?.validate();
  const data: any = { ...formData };
  mode.value == "edit" ? await pointsEdit(data) : await pointsAdd(data);
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
  const data = await pointsDetail({
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
