<template>
  <div class="waterfall-container" ref="containerRef" @scroll="handleScroll">
    <div class="waterfall-list">
      <div
        class="waterfall-item"
        v-for="(item, index) in resultList"
        :key="index"
        :style="{
          width: `${item.width}px`,
          height: `${item.height}px`,
          transform: `translate3d(${item.x}px, ${item.y}px, 0)`,
        }"
      >
        <slot name="item" v-bind="item"></slot>
      </div>
      <div v-if="isEnd" class="no-more-data">暂无更多数据</div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, onUnmounted, watch } from "vue";
import { throttle, debounce } from "@/utils/waterfall/utils.js";

const props = defineProps({
  gap: {
    type: Number,
    default: 10,
  },
  columns: {
    type: Number,
    default: 3,
  },
  bottom: {
    type: Number,
    default: 0,
  },
  images: {
    type: Array,
    default: () => [],
  },
  fetchMoreImages: {
    type: Function,
    required: true,
  },
  isEnd: {
    type: Boolean,
    default: false,
  },
});

const containerRef = ref(null);
const cardWidth = ref(0);
const columnHeight = ref(new Array(props.columns).fill(0));
const resultList = ref([]);
const loading = ref(false);

const minColumn = computed(() => {
  let minIndex = -1,
    minHeight = Infinity;

  columnHeight.value.forEach((item, index) => {
    if (item < minHeight) {
      minHeight = item;
      minIndex = index;
    }
  });

  return {
    minIndex,
    minHeight,
  };
});

const handleScroll = throttle(() => {
  const { scrollTop, clientHeight, scrollHeight } = containerRef.value;
  const bottom = scrollHeight - clientHeight - scrollTop;
  if (bottom <= props.bottom && !props.isEnd) {
    !loading.value && props.fetchMoreImages();
  }
});

const getList = (list) => {
  return list.map((x, index) => {
    const cardHeight = Math.floor((x.height * cardWidth.value) / x.width);
    const { minIndex, minHeight } = minColumn.value;
    const isInit = index < props.columns && resultList.value.length < props.columns;
    if (isInit) {
      columnHeight.value[index] = cardHeight + props.gap;
    } else {
      columnHeight.value[minIndex] += cardHeight + props.gap;
    }

    return {
      width: cardWidth.value,
      height: cardHeight,
      x: isInit
        ? index % props.columns !== 0
          ? index * (cardWidth.value + props.gap)
          : 0
        : minIndex % props.columns !== 0
        ? minIndex * (cardWidth.value + props.gap)
        : 0,
      y: isInit ? 0 : minHeight,
      image: x,
    };
  });
};

const resizeObserver = new ResizeObserver(() => {
  handleResize();
});

const handleResize = debounce(() => {
  const containerWidth = containerRef.value.clientWidth;
  cardWidth.value =
    (containerWidth - props.gap * (props.columns - 1)) / props.columns;
  columnHeight.value = new Array(props.columns).fill(0);
  resultList.value = getList(resultList.value);
});

const init = () => {
  if (containerRef.value) {
    const containerWidth = containerRef.value.clientWidth;
    cardWidth.value =
      (containerWidth - props.gap * (props.columns - 1)) / props.columns;
    resultList.value = getList(props.images);
    resizeObserver.observe(containerRef.value);
  }
};

watch(() => props.images, (newImages) => {
  const newList = getList(newImages);
  resultList.value = [...resultList.value, ...newList];
});

onMounted(() => {
  init();
});

onUnmounted(() => {
  containerRef.value && resizeObserver.unobserve(containerRef.value);
});
</script>

<style lang="scss">
.waterfall {
  &-container {
    width: 100%;
    height: 100%;
    overflow-y: scroll;
    overflow-x: hidden;
  }

  &-list {
    width: 100%;
    position: relative;
  }
  &-item {
    position: absolute;
    left: 0;
    top: 0;
    box-sizing: border-box;
    transition: all 0.3s;
  }
  .no-more-data {
    text-align: center;
    padding: 20px;
    color: #999;
    font-size: 14px;
  }
}
</style>