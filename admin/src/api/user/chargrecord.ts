import request from '@/utils/request'

// 充值记录列表
export function chargrecordLists(params?: Record<string, any>) {
    return request.get({ url: '/chargrecord/list', params })
}

// 充值记录详情
export function chargrecordDetail(params: Record<string, any>) {
    return request.get({ url: '/chargrecord/detail', params })
}

// 充值记录新增
export function chargrecordAdd(params: Record<string, any>) {
    return request.post({ url: '/chargrecord/add', params })
}

// 充值记录编辑
export function chargrecordEdit(params: Record<string, any>) {
    return request.post({ url: '/chargrecord/edit', params })
}

// 充值记录删除
export function chargrecordDelete(id) {
    return request.post({ url: '/chargrecord/del', data: id })
}
