export function getUserCenter(headers?: any) {
    return $request.get({ url: '/user/center', headers })
}

// 个人信息
export function getUserInfo() {
    return $request.get({ url: '/user/info' })
}

// 个人编辑
export function userEdit(params: any) {
    return $request.post({ url: '/user/edit', params })
}

// 绑定手机
export function userBindMobile(params: any, headers?: any) {
    console.info("!headers?.token", !headers?.token);
    console.info("headers", headers);
    console.info("headers.token",headers.token);
    return $request.post(
        { url: '/user/bindMobile', params, headers },
        { withToken: !headers?.token }
        //  { withToken: Boolean(headers?.token) }
    )
}

// 更改密码
export function userChangePwd(params: any) {
    return $request.post({ url: '/user/changePwd', params })
}


// 查询秘钥
export function getApiKey() {
    return $request.get({ url: '/user/getApiKey' })
}

