<template>
  <div class="login">
    <div class="text-4xl">欢迎登录</div>
    
    <ElForm
      ref="formRef"
      class="mt-[35px]"
      size="large"
      :model="formData"
      :rules="formRules"
    >

      <template v-if="isAccountLogin && includeLoginWay(LoginWayEnum.ACCOUNT)">
         <!--微信登录-->
        <ElFormItem>
          <div id="login_container" v-show="loginContainerVisible" style="text-align: center"></div>
        </ElFormItem>
      </template>

    <div id="otherLogin" v-show="isOtherLoginVisible">    
      <template v-if="isAccountLogin && includeLoginWay(LoginWayEnum.ACCOUNT)">
        <ElFormItem prop="account">
          <ElInput v-model="formData.account" placeholder="请输入账号/手机号" />
        </ElFormItem>
        <ElFormItem prop="password">
          <ElInput
            v-model="formData.password"
            type="password"
            show-password
            placeholder="请输入密码"
          />
        </ElFormItem>
      </template>
      <template v-if="isMobileLogin && includeLoginWay(LoginWayEnum.MOBILE)">
        <ElFormItem prop="account">
          <ElInput v-model="formData.account" placeholder="请输入手机号" />
        </ElFormItem>
        <ElFormItem prop="code">
          <ElInput v-model="formData.code" placeholder="请输入验证码">
            <template #suffix>
              <div
                class="
                  flex
                  justify-center
                  leading-5
                  w-[90px]
                  pl-2.5
                  border-l border-br
                "
              >
                <VerificationCode
                  ref="verificationCodeRef"
                  @click-get="sendSms"
                />
              </div>
            </template>
          </ElInput>
        </ElFormItem>
      </template>
      <div class="flex">
        <div class="flex-1">
          <ElButton
            v-if="isAccountLogin && includeLoginWay(LoginWayEnum.MOBILE)"
            type="primary"
            link
            @click="changeLoginWay"
          >
            手机验证码登录
          </ElButton>
          <ElButton
            v-if="isMobileLogin && includeLoginWay(LoginWayEnum.ACCOUNT)"
            type="primary"
            link
            @click="changeLoginWay"
          >
            账号密码登录
          </ElButton>
        </div>

        <ElButton
          v-if="isAccountLogin"
          link
          @click="setPopupType(PopupTypeEnum.FORGOT_PWD)"
        >
          忘记密码？
        </ElButton>
      </div>
      <ElFormItem class="mt-[30px]">
        <ElButton
          class="w-full"
          type="primary"
          :loading="isLock"
          @click="loginLock"
        >
          登录
        </ElButton>
      </ElFormItem>
      <div class="mt-[40px]" v-if="isOpenOtherAuth">
        <ElDivider>
          <span class="text-tx-secondary font-normal"> 第三方登录 </span>
        </ElDivider>
        <div class="flex justify-center">
          <ElButton link @click="getWxCodeLock" v-if="inWxAuth">
            <img
              class="w-[48px] h-[48px]"
              src="@/assets/images/icon/icon_wx.png"
            />
          </ElButton>
        </div>
      </div>


    </div>

      <div
        class="
          mb-[-15px]
          mx-[-40px]
          mt-[30px]
          bg-primary-light-9
          rounded-b-md
          px-[15px]
          flex
          leading-10
        "
      >
        <div class="flex-1">
          <ElCheckbox v-if="isOpenAgreement" v-model="isAgreement">
            <span class="text-tx-secondary text-sm">
              已阅读并同意
              <a
                :href="`/policy/${PolicyAgreementEnum.SERVICE}`"
                target="_blank"
                class="text-tx-primary"
              >
                《服务协议》
              </a>
              和
              <a
                :href="`/policy/${PolicyAgreementEnum.PRIVACY}`"
                target="_blank"
                class="text-tx-primary"
              >
                《隐私政策》
              </a>
            </span>
          </ElCheckbox>
        </div>
        <div>
          <ElButton
            link
            type="primary"
            @click="setPopupType(PopupTypeEnum.REGISTER)"
          >
            <span class="text-sm">注册账号</span>
          </ElButton>
        </div>
      </div>
    </ElForm>
  </div>
</template>
<script lang="ts" setup>
import {
  ElForm,
  ElFormItem,
  ElInput,
  ElButton,
  ElDivider,
  ElCheckbox,
  FormInstance,
  FormRules,
} from "element-plus";
import { useAccount, PopupTypeEnum } from "./useAccount";
import { getWxCodeUrl, mobileLogin, accountLogin,getWeChatLoginUrl } from "@/api/account";
import { useAppStore } from "@/stores/app";
import { useUserStore } from "@/stores/user";
import { smsSend, validatePhone } from "~~/api/app";
import { PolicyAgreementEnum, SMSEnum } from "~~/enums/appEnums";
import feedback from "~~/utils/feedback";
const appStore = useAppStore();
const userStore = useUserStore();
const { setPopupType, toggleShowPopup } = useAccount();
enum LoginWayEnum {
  ACCOUNT = 1,
  MOBILE = 2,
}
const isAgreement = ref(false);
const formRef = shallowRef<FormInstance>();

const isOtherLoginVisible = ref(true); // 控制显示状态的响应式属性



const loginContainerVisible = ref(true); // 控制显示状态的响应式属性

const wechatLoginKey = ref({ loginKey: '' });

const formRules: FormRules = {
  account: [
    {
      required: true,
      validator(rule: any, value: any, callback: any) {
        if (value === "") {
          callback(
            new Error(
              formData.scene == LoginWayEnum.ACCOUNT
                ? "请输入账号/手机号"
                : "请输入手机号"
            )
          );
          return;
        }
        callback();
      },
      trigger: ["change", "blur"],
    },
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
      trigger: ["change", "blur"],
    },
  ],
  code: [
    {
      required: true,
      message: "请输入验证码",
      trigger: ["change", "blur"],
    },
  ],
};
const formData = reactive({
  code: "",
  account: "",
  password: "",
  scene: 0,
});
const isAccountLogin = computed(() => formData.scene == LoginWayEnum.ACCOUNT);
const isMobileLogin = computed(() => formData.scene == LoginWayEnum.MOBILE);
const includeLoginWay = (way: LoginWayEnum) =>
  appStore.getLoginConfig.loginWay?.includes(way);

const inWxAuth = computed(() => {
  return appStore.getLoginConfig.autoLoginAuth.includes(2);
});

const isOpenAgreement = computed(
  () => appStore.getLoginConfig.openAgreement == 1
);
const isOpenOtherAuth = computed(
  () => appStore.getLoginConfig.openOtherAuth == 1
);
const isForceBindMobile = computed(
  () => appStore.getLoginConfig.forceBindMobile == 1
);
const changeLoginWay = () => {
  if (formData.scene == LoginWayEnum.ACCOUNT) {
    formData.scene = LoginWayEnum.MOBILE;
  } else {
    formData.scene = LoginWayEnum.ACCOUNT;
  }
};
const verificationCodeRef = shallowRef();
const sendSms = async () => {
  await formRef.value?.validateField(["account"]);
  //以下是先校验手机号是否存在，存在才开始
  // await validatePhone({
  //   scene: SMSEnum.LOGIN,
  //   mobile: formData.account,
  // });
  await smsSend({
    scene: SMSEnum.LOGIN,
    mobile: formData.account,
  });

  verificationCodeRef.value?.start();
};

const handleLogin = async () => {
  await formRef.value?.validate();
  const params: any = {};
  if (isAccountLogin.value) {
    params.username = formData.account;
    params.password = formData.password;
  }
  if (isMobileLogin.value) {
    params.mobile = formData.account;
    params.code = formData.code;
  }
  let data;
  switch (formData.scene) {
    case LoginWayEnum.ACCOUNT:
      data = await accountLogin(params);
      break;
    case LoginWayEnum.MOBILE:
      data = await mobileLogin(params);

      break;
  }
  if (!data) return;
  if (isForceBindMobile.value && !data.isBindMobile) {
    userStore.temToken = data.token;
    setPopupType(PopupTypeEnum.BIND_MOBILE);
    return;
  }
  userStore.login(data.token);
  await userStore.getUser();
  toggleShowPopup(false);
};
const { lockFn: handleLoginLock, isLock } = useLockFn(handleLogin);
const agreementConfirm = async () => {
  if (isAgreement.value) {
    return;
  }
  await feedback.confirm("确认已阅读并同意《服务协议》和《隐私政策》");
  isAgreement.value = true;
};
const loginLock = async () => {
  await agreementConfirm();
  await handleLoginLock();
};

const getWxCode = async () => {
  await agreementConfirm();
  const { url } = await getWxCodeUrl();
  window.location.href = url;
};

//yungouOS微信登录
const getWxQrCode = async () => {
  await agreementConfirm();
  isOtherLoginVisible.value = !isOtherLoginVisible.value;
  getWeChatLoginUrlMethod();

};
const { lockFn: getWxCodeLock } = useLockFn(getWxQrCode);
watch(
  () => appStore.getLoginConfig,
  (value) => {
    const { loginWay } = value;
    if (loginWay && loginWay.length) {
      formData.scene = loginWay.at(0);
    }
  },
  {
    immediate: true,
  }
);


// const getWeChatLoginUrlMethod = async () => {
//   const params = reactive({
//     operateType: 'login'
//   })
//   const data = await getWeChatLoginUrl(params);
//   let obj = new WxLogin({
//     // 需要显示内嵌二维码的容器id
//     id: 'login_container',
//     self_redirect:true,
//     // 应用ID
//     appid: data.appId,
//     // 网页默认即可
//     scope: data.scope,
//     // 授权成功后回调的url
//     redirect_uri: encodeURIComponent(data.redirect_uri),
//     // 可设置为简单的随机数加session用来校验
//     state: data.state,
//     // 二维码的样式，提供"black"、"white"可选。
//     style: 'black',
//     // 自定义样式链接
//     href: 'https://oos.moguit.cn/cdn/wechatLogin.css'
//   })

// };




  
// 调用 API 获取参数并初始化 WxLogin  
const getWeChatLoginUrlMethod = async () => {  
  const params = {  
    operateType: 'login'  
  };  
  const data = await getWeChatLoginUrl(params);  
  await initWxLogin(data); // 初始化 WxLogin 实例  
};  


// 初始化 WxLogin 实例  
const initWxLogin = async (data: any) => {
    await loadWxLogin(); // 确保 wxLogin.js 加载完成  
     new WxLogin({  
      id: 'login_container',  
      self_redirect: false,  
      appid: data.appId,  
      scope: data.scope,  
      redirect_uri: encodeURIComponent(data.redirect_uri),  
      state: data.state,
      stylelite:1,  
      style: 'black',  
      href: 'data:text/css;base64,LmltcG93ZXJCb3ggLnFyY29kZSB7d2lkdGg6IDIwMHB4O30KLmltcG93ZXJCb3ggLnRpdGxlIHtkaXNwbGF5OiBub25lO30KLmltcG93ZXJCb3ggLmluZm8ge3dpZHRoOiAyMDBweDt9Ci5zdGF0dXNfaWNvbiB7ZGlzcGxheTogbm9uZX0KLmltcG93ZXJCb3ggLnN0YXR1cyB7dGV4dC1hbGlnbjogY2VudGVyO30gCiN3eF9kZWZhdWx0X3RpcCB7ZGlzcGxheTogbm9uZTt9',  
    }); 
};  


// 加载 wxLogin.js 脚本并初始化 WxLogin 实例  
const loadWxLogin = async () => {  
  return new Promise((resolve, reject) => {  
    const script = document.createElement('script');  
    script.src = 'http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js';  
    script.async = true;  
    script.onload = () => {  
      if (typeof WxLogin !== 'undefined') {  
        resolve();  
      } else {  
        reject(new Error('WxLogin is not defined'));  
      }  
    };  
    script.onerror = (error) => reject(error);  
    document.body.appendChild(script);  
  });  
};  
  



onMounted(() => {

});




</script>

<style lang="scss" scoped></style>
