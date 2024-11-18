<template>
  <div class="editor" v-loading.fullscreen.lock="fullLoading">
    <component
      :is="toolBarComponent"
      style="border-bottom: 1px solid #ccc"
      :editor="editorRef"
      :defaultConfig="toolbarConfig"
      mode="default"
    />
    <component
      :is="editorComponent"
      style="height: 500px; overflow-y: hidden"
      v-model:html="valueHtml"
      :defaultConfig="editorConfig"
      mode="default"
      @onCreated="handleCreated"
      @onChange="returnHtml"
    />
  </div>
</template>

<script setup>
import "@wangeditor/editor/dist/css/style.css"; // 引入 css
import { markRaw, ref, shallowRef, onBeforeUnmount } from "vue";

const emit = defineEmits(['update:html']) // 用于支持 v-model

const baseUrl = "http://www.baidu.com"

// 上传显示loading
const fullLoading = ref(false)

// 工具栏
const toolBarComponent = ref(null)
// 编辑组件
const editorComponent = ref(null)
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef(null);
// 内容 HTML 
const valueHtml = ref('');
watch(valueHtml, (newVal) => {
  emit('update:html', newVal);
});

// 工具栏默认配置
const toolbarConfig = {};
// 输入框配置
const editorConfig = {
  placeholder: "请输入内容...",
  readOnly: false,
  MENU_CONF: {
    // 上传图片
    uploadImage: {
      fieldName: "file",
      // 上传地址
      server: `${baseUrl}file/upload`,
      // 超时时间，默认为 10 秒
      timeout: 30 * 1000, // 5s
      // 将 meta 拼接到 url 参数中，默认 false
      metaWithUrl: false,
      // 自定义上传参数。
      meta: {
        type: 3
      },
      // 跨域是否传递 cookie ，默认为 false
      withCredentials: true,
      // 自定义增加 http  header

      // 限制图片大小
      maxFileSize: 10 * 1024 * 1024, // 10M
      // 如果文件大小小于5kb，请插入base64格式
      base64LimitSize: 5 * 1024,
      // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
      allowedFileTypes: ["image/*"],
      // 最多可上传几个文件，默认为 100
      maxNumberOfFiles: 1,
      // 上传之前触发
      onBeforeUpload (file) {
        // console.log("onBeforeUpload", file);
        // 显示loading
        fullLoading.value = true
        return file;
      },
      // 上传进度的回调函数 progress 是 0-100 的数字
      onProgress (progress) {
        // console.log("onProgress", progress);
      },
      // 单个文件上传成功之后
      onSuccess (file, res) {
        // console.log("onSuccess", file, res);
      },
      // 单个文件上传失败
      onFailed (file, res) {
        // console.log("onFailed", file, res);
      },
      // 上传错误，或者触发 timeout 超时
      onError (file, err, res) {
        // console.error("onError", file, err, res);
      },
      // 自定义插入图片 res 服务器返回的数据 insertFn 方法
      customInsert (res, insertFn) {
        // 隐藏loading
        fullLoading.value = false
        insertFn(res.url)
      }
    },
    // 上传视频
    uploadVideo: {
      fieldName: "file",
      // 上传地址
      server: `${baseUrl}file/upload`,
      // 超时时间，默认为 30 秒
      timeout: 3000 * 1000, // 15 秒
      // 将 meta 拼接到 url 参数中，默认 false
      metaWithUrl: false,
      // 自定义上传参数。
      meta: {
        type: 3
      },
      // 跨域是否传递 cookie ，默认为 false
      withCredentials: true,
      // 自定义增加 http  header

      // 选择文件时的类型限制，默认为 ['video/*'] 。如不想限制，则设置为 []
      allowedFileTypes: ["video/*"],
      // 最多可上传几个文件，默认为 5
      maxNumberOfFiles: 1,
      // 单个文件的最大体积限制，默认为 10M
      maxFileSize: 1200 * 1024 * 1024, // 1200M
      // 上传之前触发
      onBeforeUpload (file) {
        // console.log("onBeforeUpload", file);
        // 显示loading
        fullLoading.value = true
        return file;
      },
      // 上传进度的回调函数
      onProgress (progress) {
        // console.log("progress", progress);
      },
      // 单个文件上传成功之后
      onSuccess (file, res) {
        // console.log(`${file.name} 上传成功`, res);
      },
      // 单个文件上传失败
      onFailed (file, res) {
        // console.log(`${file.name} 上传失败`, res);
      },
      // 上传错误，或者触发 timeout 超时
      onError (file, err, res) {
        // console.log(`${file.name} 上传出错`, err, res);
      },
      // 自定义插入视频 res 服务器返回的数据 insertFn 方法
      customInsert (res, insertFn) {
        // 隐藏loading
        fullLoading.value = false
        insertFn(res.url)
      }
    }
  }
};

// 动态组件赋值
if (process.client) {
  const { Editor, Toolbar } = await import("@wangeditor/editor-for-vue");
  editorComponent.value = markRaw(Editor)
  toolBarComponent.value = markRaw(Toolbar)
}

// 记录 editor 实例，重要！
const handleCreated = (editor) => {
  editorRef.value = editor;
};

// 返回内容
const returnHtml = () => {
  emit('update:html', valueHtml.value)
}

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor == null) return;
  editor.destroy();
});

</script>

<style scoped lang="scss">
.editor {
  border: 1px solid #ccc;
}
:deep(.w-e-text-placeholder) {
  top: 10px;
}
</style>
