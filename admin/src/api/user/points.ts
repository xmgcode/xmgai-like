import request from '@/utils/request'

// 用户点数列表
export function pointsLists(params?: Record<string, any>) {
    return request.get({ url: '/points/list', params })
}

// 用户点数详情
export function pointsDetail(params: Record<string, any>) {
    return request.get({ url: '/points/detail', params })
}

// 用户点数新增
export function pointsAdd(params: Record<string, any>) {
    return request.post({ url: '/points/add', params })
}

// 用户点数编辑
export function pointsEdit(params: Record<string, any>) {
    return request.post({ url: '/points/edit', params })
}

// 用户点数删除
export function pointsDelete(id) {
    return request.post({ url: '/points/del', data: id })
}
