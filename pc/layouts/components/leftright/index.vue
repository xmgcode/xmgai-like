<template>
  <ElContainer>
    <ElAside
      width="200px"
      style="background-color: #f0f2f4; position: fixed; height: 100vh"
    >
      <ElMenu
        default-active="1"
        class="sidebar-menu"
        @select="handleSelect"
        :unique-opened="true"
      >
        <ElMenuItem index="1" route="/">
          <template #title>
            <i class="icon-fw"></i>
            <span>首页</span>
          </template>
        </ElMenuItem>
        <!-- 其他菜单项 -->
        <ElMenuItem index="2" route="/page2">
          <template #title>
            <i class="icon-fw"></i>
            <span>页面2</span>
          </template>
        </ElMenuItem>
      </ElMenu>
    </ElAside>
    <el-main>
      <!-- 页面主要内容 -->
    </el-main>
  </ElContainer>
</template>

<script lang="ts" setup>
import { defineComponent } from "vue";
import { ElContainer, ElAside, ElMenu, ElMenuItem } from "element-plus";
</script>

<style scoped>
.sidebar-menu {
  overflow-y: auto;
  overflow-x: hidden;
  transition: all 0.3s;
  box-sizing: border-box;
  height: 100%;
}

.icon-fw {
  text-align: center;
  width: 1.25em;
  font-size: 1.333rem;
  line-height: 1.333rem;
  vertical-align: -0.0667em;
}
</style>