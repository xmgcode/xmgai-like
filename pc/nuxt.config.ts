// https://v3.nuxtjs.org/api/configuration/nuxt.config

import { getEnvConfig } from './nuxt/env'
const envConfig = getEnvConfig()
export default defineNuxtConfig({
    css: [
            '@/assets/styles/index.scss',
            '@/assets/styles/iconfont/iconfont.css',
            'vue-waterfall-plugin-next/dist/style.css',
            'v3-waterfall/dist/style.css',
            '@/assets/styles/custom-scroll.css',
            '@/assets/styles/images-wall.css'
        ],
    modules: ['@pinia/nuxt', '@nuxtjs/tailwindcss'],
    app: {
        baseURL: envConfig.baseUrl
    },
    runtimeConfig: {
        public: {
            ...envConfig,
            filePrefix: process.env.NUXT_FILE_PREFIX // 直接引用 process.env 中的值
        }
    },
    ssr: !!envConfig.ssr,

    // 添加这一段来引入外部脚本
    head: {
        script: [
            {
                src: 'http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js',
                async: true,
                defer: true,
            },
        ],
    },
     // 注册插件
    plugins: ['~/plugins/v3-waterfall.ts','~/plugins/vue-waterfall-plugin.client.ts'],
})
