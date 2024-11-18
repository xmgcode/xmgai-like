import request from '@/utils/request'

// 艺术二维码模板列表
export function artcodeLists(params?: Record<string, any>) {
    return request.get({ url: '/artcode/list', params })
}

// 艺术二维码模板详情
export function artcodeDetail(params: Record<string, any>) {
    return request.get({ url: '/artcode/detail', params })
}

// 艺术二维码模板新增
export function artcodeAdd(params: Record<string, any>) {
    return request.post({ url: '/artcode/add', params })
}

// 艺术二维码模板编辑
export function artcodeEdit(params: Record<string, any>) {
    return request.post({ url: '/artcode/edit', params })
}

// 艺术二维码模板删除
export function artcodeDelete(id) {
    return request.post({ url: '/artcode/del', data: id })
}
