<template>
  <NuxtLink class="flex" to="/">
    <img src="../../../static/image/pc_logo.png" class="h-[26px]" />
  </NuxtLink>
</template>
<script lang="ts" setup>
import { useAppStore } from "~~/stores/app";
const appStore = useAppStore();
</script>

<style lang="scss" scoped></style>
