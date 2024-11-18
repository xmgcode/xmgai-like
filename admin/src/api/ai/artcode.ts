import request from '@/utils/request'

// 二维码生成列表
export function artqrcodeLists(params?: Record<string, any>) {
    return request.get({ url: '/artqrcode/list', params })
}

// 二维码生成详情
export function artqrcodeDetail(params: Record<string, any>) {
    return request.get({ url: '/artqrcode/detail', params })
}

// 二维码生成新增
export function artqrcodeAdd(params: Record<string, any>) {
    return request.post({ url: '/artqrcode/add', params })
}

// 二维码生成编辑
export function artqrcodeEdit(params: Record<string, any>) {
    return request.post({ url: '/artqrcode/edit', params })
}

// 二维码生成删除
export function artqrcodeDelete(id) {
    return request.post({ url: '/artqrcode/del', data: id })
}
