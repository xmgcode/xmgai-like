/**
 * 
 * @param headers 模型列表
 * @returns 
 */
export function getModelList(headers?: any) {
    return $request.get({ url: '/aiart/getOwnModel', headers })
}



//艺术二维码生成
export function createArtcode(params: any) {
    return $request.post({ url: '/aiart/createArtcode', params })
}


/**查询二维码 */
export function getQrCode(params) {
    return $request.get({ url: '/aiart/getQrCode',params })
}



/**获取可用点数 */
export function getPionts() {
    return $request.get({ url: '/aiart/getPionts' })
}



/**获取充值记录 */
export function getChargeRecords() {
    return $request.get({ url: '/aiart/getChargeRecords' })
}


/**获取获取创作记录 */
export function getArtRecords(params) {
    return $request.get({ url: '/aiart/getArtRecords',params  })
}

/**获取点数消费记录 */
export function getConsumred() {
    return $request.get({ url: '/aiart/getConsumred' })
}


//换脸
export function aiSwap(params: any) {
    return $request.post({ url: '/mj/faceswap', params })
}


/**查询AI任务 */
export function getSwapUrl(params) {
    return $request.get({ url: '/mj/queryTask',params })
}



//dalle3绘画
export function dalle3(params: any) {
    return $request.post({ url: '/mj/dall', params })
}


//AI作品列表
export function aiList(params: any) {
    return $request.post({ url: '/aiart/aiList', params })
}


//mj绘画文生图
export function imagine(params: any) {
    return $request.post({ url: '/mj/imagine', params })
}








