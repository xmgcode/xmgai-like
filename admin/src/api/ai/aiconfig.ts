import request from '@/utils/request'

// AI配置列表
export function aiconfigLists(params?: Record<string, any>) {
    return request.get({ url: '/aiconfig/list', params })
}

// AI配置详情
export function aiconfigDetail(params: Record<string, any>) {
    return request.get({ url: '/aiconfig/detail', params })
}

// AI配置新增
export function aiconfigAdd(params: Record<string, any>) {
    return request.post({ url: '/aiconfig/add', params })
}

// AI配置编辑
export function aiconfigEdit(params: Record<string, any>) {
    return request.post({ url: '/aiconfig/edit', params })
}

// AI配置删除
export function aiconfigDelete(id) {
    return request.post({ url: '/aiconfig/del', data: id })
}
