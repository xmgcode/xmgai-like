<template>
  <div class="index-lists">
    <el-card class="!border-none" shadow="never">
      <el-form
        ref="formRef"
        class="mb-[-16px]"
        :model="queryParams"
        :inline="true"
      >
        <el-form-item label="用户ID" prop="userId">
          <el-input class="w-[280px]" v-model="queryParams.userId" />
        </el-form-item>
        <el-form-item label="使用场景" prop="scene">
          <el-input class="w-[280px]" v-model="queryParams.scene" />
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
        <el-button
          v-perms="['userecord:add']"
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
        <el-table-column label="用户ID" prop="userId" min-width="100" />
        <el-table-column label="使用场景" prop="scene" min-width="100" />
        <el-table-column label="消耗点数" prop="consume" min-width="100" />
        <el-table-column label="创建者" prop="creator" min-width="100" />
        <el-table-column label="创建时间" prop="createTime" min-width="100" />
        <el-table-column label="更新者" prop="updater" min-width="100" />
        <el-table-column label="更新时间" prop="updateTime" min-width="100" />
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button
              v-perms="['userecord:edit']"
              type="primary"
              link
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
            <el-button
              v-perms="['userecord:del']"
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
<script lang="ts" setup name="userecord">
import { userecordDelete, userecordLists } from "@/api/user/userecord";
import { usePaging } from "@/hooks/usePaging";
import feedback from "@/utils/feedback";
import EditPopup from "./edit.vue";
const editRef = shallowRef<InstanceType<typeof EditPopup>>();
const showEdit = ref(false);
const queryParams = reactive({
  userId: "",
  scene: "",
  createTimeStart: "",
  createTimeEnd: "",
});

const { pager, getLists, resetPage, resetParams } = usePaging({
  fetchFun: userecordLists,
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
  await userecordDelete(id);
  feedback.msgSuccess("删除成功");
  getLists();
};

getLists();
</script>
