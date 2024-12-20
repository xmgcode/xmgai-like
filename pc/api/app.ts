//发送短信
export function smsSend(params: any) {
    return $request.post({ url: '/index/sendSms', params })
}

// 获取配置
export function getConfig() {
    return $request.get({ url: '/pc/getConfig' })
}

// 获取协议
export function getPolicy(params: any) {
    return $request.get({ url: '/index/policy', params })
}

// 上传图片
export function uploadImage(params: any) {
    return $request.uploadFile({ url: '/upload/image' }, params)
}

//发送短信前校验手机号是否存在
export function validatePhone(params: any) {
    return $request.post({ url: '/login/validatePhone', params })
}
