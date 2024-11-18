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
        <el-form-item label="使用场景" prop="scene">
          <el-input v-model="formData.scene" placeholder="请输入使用场景" />
        </el-form-item>
        <el-form-item label="消耗点数" prop="consume">
          <el-input v-model="formData.consume" placeholder="请输入消耗点数" />
        </el-form-item>
      </el-form>
    </popup>
  </div>
</template>
<script lang="ts" setup>
import type { FormInstance } from "element-plus";
import {
  userecordEdit,
  userecordAdd,
  userecordDetail,
} from "@/api/user/userecord";
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
  return mode.value == "edit" ? "编辑使用记录" : "新增使用记录";
});

const formData = reactive({
  id: "",
  scene: "",
  consume: "",
});

const formRules = {
  scene: [
    {
      required: true,
      message: "请输入使用场景",
      trigger: ["blur"],
    },
  ],
  consume: [
    {
      required: true,
      message: "请输入消耗点数",
      trigger: ["blur"],
    },
  ],
};

const handleSubmit = async () => {
  await formRef.value?.validate();
  const data: any = { ...formData };
  mode.value == "edit" ? await userecordEdit(data) : await userecordAdd(data);
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
  const data = await userecordDetail({
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
