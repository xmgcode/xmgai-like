<template>
  <div class="index-lists">
    <el-card class="!border-none" shadow="never">
      <el-form
        ref="formRef"
        class="mb-[-16px]"
        :model="queryParams"
        :inline="true"
      >
        <el-form-item label="充值金额" prop="money">
          <el-input class="w-[280px]" v-model="queryParams.money" />
        </el-form-item>
        <el-form-item label="支付单号（第三方支付单号）" prop="payNo">
          <el-input class="w-[280px]" v-model="queryParams.payNo" />
        </el-form-item>
        <el-form-item label="支付状态（0未支付，1已支付）" prop="payStatus">
          <el-select
            v-model="queryParams.payStatus"
            class="w-[280px]"
            clearable
          >
            <el-option label="全部" value="" />
            <el-option
              v-for="(item, index) in dictData.whether"
              :key="index"
              :label="item.name"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间" prop="createTime">
          <daterange-picker
            v-model:startTime="queryParams.createTimeStart"
            v-model:endTime="queryParams.createTimeEnd"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="resetPage">查询</el-button>
          <el-button @click="resetParams">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="!border-none mt-4" shadow="never">
      <div>
        <el-button v-perms="['order:add']" type="primary" @click="handleAdd()">
          <template #icon>
            <icon name="el-icon-Plus" />
          </template>
          新增
        </el-button>
      </div>
      <el-table
        class="mt-4"
        size="large"
        v-loading="pager.loading"
        :data="pager.lists"
      >
        <el-table-column label="充值金额" prop="money" min-width="100" />
        <el-table-column
          label="支付单号（第三方支付单号）"
          prop="payNo"
          min-width="100"
        />
        <el-table-column
          label="支付状态（0未支付，1已支付）"
          prop="payStatus"
          min-width="100"
        >
          <template #default="{ row }">
            <dict-value :options="dictData.whether" :value="row.payStatus" />
          </template>
        </el-table-column>
        <el-table-column label="创建者" prop="creator" min-width="100" />
        <el-table-column label="创建时间" prop="createTime" min-width="100" />
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button
              v-perms="['order:edit']"
              type="primary"
              link
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
            <el-button
              v-perms="['order:del']"
              type="danger"
              link
              @click="handleDelete(row.id)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="flex justify-end mt-4">
        <pagination v-model="pager" @change="getLists" />
      </div>
    </el-card>
    <edit-popup
      v-if="showEdit"
      ref="editRef"
      :dict-data="dictData"
      @success="getLists"
      @close="showEdit = false"
    />
  </div>
</template>
<script lang="ts" setup name="order">
import { orderDelete, orderLists } from "@/api/user/order";
import { useDictData } from "@/hooks/useDictOptions";
import { usePaging } from "@/hooks/usePaging";
import feedback from "@/utils/feedback";
import EditPopup from "./edit.vue";
const editRef = shallowRef<InstanceType<typeof EditPopup>>();
const showEdit = ref(false);
const queryParams = reactive({
  money: "",
  payNo: "",
  payStatus: "",
  createTimeStart: "",
  createTimeEnd: "",
});

const { pager, getLists, resetPage, resetParams } = usePaging({
  fetchFun: orderLists,
  params: queryParams,
});

const { dictData } = useDictData<{
  whether: any[];
}>(["whether"]);

const handleAdd = async () => {
  showEdit.value = true;
  await nextTick();
  editRef.value?.open("add");
};

const handleEdit = async (data: any) => {
  showEdit.value = true;
  await nextTick();
  editRef.value?.open("edit");
  editRef.value?.getDetail(data);
};

const handleDelete = async (id) => {
  await feedback.confirm("确定要删除？");
  await orderDelete(id);
  feedback.msgSuccess("删除成功");
  getLists();
};

getLists();
</script>
