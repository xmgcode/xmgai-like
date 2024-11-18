//首页数据
export function getIndex() {
    return $request.get({ url: '/pc/index' })
}




/**微信支付 */
export function weChatPay(params) {
    return $request.get({ url: '/pay/wxPayCode',params })
}