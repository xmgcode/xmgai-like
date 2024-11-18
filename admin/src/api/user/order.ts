import request from '@/utils/request'

// 点数订单列表
export function orderLists(params?: Record<string, any>) {
    return request.get({ url: '/pointorder/list', params })
}

// 点数订单详情
export function orderDetail(params: Record<string, any>) {
    return request.get({ url: '/pointorder/detail', params })
}

// 点数订单新增
export function orderAdd(params: Record<string, any>) {
    return request.post({ url: '/pointorder/add', params })
}

// 点数订单编辑
export function orderEdit(params: Record<string, any>) {
    return request.post({ url: '/pointorder/edit', params })
}

// 点数订单删除
export function orderDelete(id) {
    return request.post({ url: '/pointorder/del', data: id })
}
