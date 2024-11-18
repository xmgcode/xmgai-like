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
        <el-form-item label="持续时间" prop="duration">
          <el-input class="w-[280px]" v-model="queryParams.duration" />
        </el-form-item>
        <el-form-item label="消耗点数" prop="cost">
          <el-input class="w-[280px]" v-model="queryParams.cost" />
        </el-form-item>
        <el-form-item label="模型编号" prop="mode">
          <el-input class="w-[280px]" v-model="queryParams.mode" />
        </el-form-item>
        <el-form-item label="回调地址" prop="callbackOriginUrl">
          <el-input class="w-[280px]" v-model="queryParams.callbackOriginUrl" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" class="w-[280px]" clearable>
            <el-option label="请选择字典生成" value="" />
          </el-select>
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
          v-perms="['artqrcode:add']"
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
        <el-table-column label="图片ID" prop="imgUuid" min-width="100" />
        <el-table-column label="用户ID" prop="userId" min-width="100" />
        <el-table-column label="预览地址" prop="urls" min-width="100" />
        <el-table-column label="本地地址" prop="localUrls" min-width="100" />
        <el-table-column label="持续时间" prop="duration" min-width="100" />
        <el-table-column label="消耗点数" prop="cost" min-width="100" />
        <el-table-column label="模型编号" prop="mode" min-width="100" />
        <el-table-column
          label="回调地址"
          prop="callbackOriginUrl"
          min-width="100"
        />
        <el-table-column label="状态" prop="status" min-width="100" />
        <el-table-column label="创建时间" prop="createTime" min-width="100" />
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button
              v-perms="['artqrcode:edit']"
              type="primary"
              link
              @click="handleEdit(row)"
            >
              编辑
            </el-button>
            <el-button
              v-perms="['artqrcode:del']"
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
<script lang="ts" setup name="artqrcode">
import { artqrcodeDelete, artqrcodeLists } from "@/api/ai/artcode";
import { usePaging } from "@/hooks/usePaging";
import feedback from "@/utils/feedback";
import EditPopup from "./edit.vue";
const editRef = shallowRef<InstanceType<typeof EditPopup>>();
const showEdit = ref(false);
const queryParams = reactive({
  userId: "",
  urls: "",
  duration: "",
  cost: "",
  mode: "",
  callbackOriginUrl: "",
  status: "",
});

const { pager, getLists, resetPage, resetParams } = usePaging({
  fetchFun: artqrcodeLists,
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
  await artqrcodeDelete(id);
  feedback.msgSuccess("删除成功");
  getLists();
};

getLists();
</script>
