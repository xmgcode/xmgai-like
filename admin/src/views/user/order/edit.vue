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
        <el-form-item label="充值金额" prop="money">
          <el-input v-model="formData.money" placeholder="请输入充值金额" />
        </el-form-item>
        <el-form-item label="支付单号（第三方支付单号）" prop="payNo">
          <el-input
            v-model="formData.payNo"
            placeholder="请输入支付单号（第三方支付单号）"
          />
        </el-form-item>
        <el-form-item label="支付状态（0未支付，1已支付）" prop="payStatus">
          <el-select
            class="flex-1"
            v-model="formData.payStatus"
            placeholder="请选择支付状态（0未支付，1已支付）"
          >
            <el-option
              v-for="(item, index) in dictData.whether"
              :key="index"
              :label="item.name"
              :value="item.value"
              clearable
              :disabled="!item.status"
            />
          </el-select>
        </el-form-item>
      </el-form>
    </popup>
  </div>
</template>
<script lang="ts" setup>
import type { FormInstance } from "element-plus";
import { orderEdit, orderAdd, orderDetail } from "@/api/user/order";
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
  return mode.value == "edit" ? "编辑点数订单" : "新增点数订单";
});

const formData = reactive({
  id: "",
  money: "",
  payNo: "",
  payStatus: "",
});

const formRules = {
  money: [
    {
      required: true,
      message: "请输入充值金额",
      trigger: ["blur"],
    },
  ],
  payNo: [
    {
      required: true,
      message: "请输入支付单号（第三方支付单号）",
      trigger: ["blur"],
    },
  ],
  payStatus: [
    {
      required: true,
      message: "请选择支付状态（0未支付，1已支付）",
      trigger: ["blur"],
    },
  ],
};

const handleSubmit = async () => {
  await formRef.value?.validate();
  const data: any = { ...formData };
  mode.value == "edit" ? await orderEdit(data) : await orderAdd(data);
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
  const data = await orderDetail({
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
