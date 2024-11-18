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
        <el-form-item label="充值金额" prop="money">
          <el-input v-model="formData.money" placeholder="请输入充值金额" />
        </el-form-item>
        <el-form-item label="获取点数" prop="obtainPoints">
          <el-input
            v-model="formData.obtainPoints"
            placeholder="请输入获取点数"
          />
        </el-form-item>
      </el-form>
    </popup>
  </div>
</template>
<script lang="ts" setup>
import type { FormInstance } from "element-plus";
import {
  chargrecordEdit,
  chargrecordAdd,
  chargrecordDetail,
} from "@/api/user/chargrecord";
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
  return mode.value == "edit" ? "编辑充值记录" : "新增充值记录";
});

const formData = reactive({
  id: "",
  money: "",
  obtainPoints: "",
});

const formRules = {
  id: [
    {
      required: true,
      message: "请输入主键",
      trigger: ["blur"],
    },
  ],
  money: [
    {
      required: true,
      message: "请输入充值金额",
      trigger: ["blur"],
    },
  ],
  obtainPoints: [
    {
      required: true,
      message: "请输入获取点数",
      trigger: ["blur"],
    },
  ],
};

const handleSubmit = async () => {
  await formRef.value?.validate();
  const data: any = { ...formData };
  mode.value == "edit"
    ? await chargrecordEdit(data)
    : await chargrecordAdd(data);
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
  const data = await chargrecordDetail({
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
