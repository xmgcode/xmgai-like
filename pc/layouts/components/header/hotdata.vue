<template>
  <ClientOnly>
    <MenuItem :menu-item="menuItem" :route-path="menuItem.path" />
  </ClientOnly>
</template>
<script lang="ts" setup>
import { ElDropdown, ElDropdownItem, ElDropdownMenu } from "element-plus";
import MenuItem from "../menu/menu-item.vue";
defineProps({
  menuItem: {
    type: Object,
    default: () => ({}),
  },
});
</script>

<style lang="scss" scoped></style>
