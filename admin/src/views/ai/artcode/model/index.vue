<template>
  <div class="index-lists">
    <el-card class="!border-none" shadow="never">
      <el-form
        ref="formRef"
        class="mb-[-16px]"
        :model="queryParams"
        :inline="true"
      >
        <el-form-item label="模板序号" prop="modelNum">
          <el-input class="w-[280px]" v-model="queryParams.modelNum" />
        </el-form-item>
        <el-form-item label="模板名称" prop="name">
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
          v-perms="['artcode:add']"
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
        <el-table-column label="模板序号" prop="modelNum" min-width="100" />
        <el-table-column label="模板名称" prop="name" min-width="100" />
        <el-table-column label="预览地址" prop="previewImg" min-width="100">
          <template #default="{ row }">
            <image-contain
              :width="40"
              :height="40"
              :src="row.previewImg"
              :preview-src-list="[row.previewImg]"
              preview-teleported
              hide-on-click-modal
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button
              v-perms="['artcode:edit']"
              type="primary"
              link
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
            <el-button
              v-perms="['artcode:del']"
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
<script lang="ts" setup name="artcode">
import { artcodeDelete, artcodeLists } from "@/api/ai/artcodemodel";
import { usePaging } from "@/hooks/usePaging";
import feedback from "@/utils/feedback";
import EditPopup from "./edit.vue";
const editRef = shallowRef<InstanceType<typeof EditPopup>>();
const showEdit = ref(false);
const queryParams = reactive({
  modelNum: "",
  name: "",
});

const { pager, getLists, resetPage, resetParams } = usePaging({
  fetchFun: artcodeLists,
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
  await artcodeDelete(id);
  feedback.msgSuccess("删除成功");
  getLists();
};

getLists();
</script>
