<template>
  <div class="index-lists">
    <el-card class="!border-none" shadow="never">
      <el-form
        ref="formRef"
        class="mb-[-16px]"
        :model="queryParams"
        :inline="true"
      >
        <el-form-item label="类型" prop="type">
          <el-input class="w-[280px]" v-model="queryParams.type" />
        </el-form-item>
        <el-form-item label="键" prop="name">
          <el-input class="w-[280px]" v-model="queryParams.name" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="resetPage">查询</el-button>
          <el-button @click="resetParams">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="!border-none mt-4" shadow="never">
      <div>
        <el-button
          v-perms="['aiconfig:add']"
          type="primary"
          @click="handleAdd()"
        >
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
        <el-table-column label="类型" prop="type" min-width="100" />
        <el-table-column label="键" prop="name" min-width="100" />
        <el-table-column label="值" prop="value" min-width="100" />
        <el-table-column label="备注" prop="remark" min-width="100" />
        <el-table-column label="创建时间" prop="createTime" min-width="100" />
        <el-table-column label="更新时间" prop="updateTime" min-width="100" />
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button
              v-perms="['aiconfig:edit']"
              type="primary"
              link
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
            <el-button
              v-perms="['aiconfig:del']"
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
      @success="getLists"
      @close="showEdit = false"
    />
  </div>
</template>
<script lang="ts" setup name="aiconfig">
import { aiconfigDelete, aiconfigLists } from "@/api/ai/aiconfig";
import { usePaging } from "@/hooks/usePaging";
import feedback from "@/utils/feedback";
import EditPopup from "./edit.vue";
const editRef = shallowRef<InstanceType<typeof EditPopup>>();
const showEdit = ref(false);
const queryParams = reactive({
  type: "",
  name: "",
  value: "",
});

const { pager, getLists, resetPage, resetParams } = usePaging({
  fetchFun: aiconfigLists,
  params: queryParams,
});

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
  await aiconfigDelete(id);
  feedback.msgSuccess("删除成功");
  getLists();
};

getLists();
</script>
