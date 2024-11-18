// 登录
export function mobileLogin(params: any) {
    return $request.post({
        url: '/login/mobileLogin',
        params
    })
}

export function accountLogin(params: any) {
    return $request.post({
        url: '/login/accountLogin',
        params
    })
}

//注册
export function register(params: any) {
    return $request.post({
        url: '/login/register',
        params
    })
}

//向微信请求code的链接
export function getWxCodeUrl() {
    return $request.get({
        url: '/login/scanCodeUrl',
        params: {
            url: location.href
        }
    })
}

export function wxLogin(params: any) {
    return $request.post({ url: '/login/scanLogin', params })
}

//忘记密码
export function forgotPassword(params: Record<string, any>) {
    return $request.post({ url: '/user/forgotPwd', params })
}


export function getWeChatLoginUrl(params: any) {
    return $request.post({ url: '/login/getWeChatLoginUrl', params })
}



export function wechatLoginCallback(params: any) {
    return $request.get({ url: '/index/wechatLoginCallback', params })
}

