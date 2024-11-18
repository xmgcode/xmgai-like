/**
 * @description 获取热榜分类
 * @return { Promise }
 */
export function getDatacont() {
    return $request.get({ url: '/hot/datacont' })
}


export function getListApif(params) {
    return $request.get({ url: '/hot/listApif',params })
}


/**获取api实体 */
export function getDataApiDetail(params) {
    return $request.get({ url: '/hot/getDataApiDetail',params })
}


/**根据apifID获取所有套餐 */
export function listByApifId(params) {
    return $request.get({ url: '/hot/listByApifId',params })
}


/**增加API订单 */

export function addOrder(params: any) {
    return $request.post({ url: '/hot/addOrder', params })
}



/**获取用户已支付定制的的API接口 */
export function payUserApif() {
    return $request.get({ url: '/hot/payUserApif' })
}


/**获取用户未支付定制的的API接口 */
export function noPayUserApif() {
    return $request.get({ url: '/hot/noPayUserApif' })
}


