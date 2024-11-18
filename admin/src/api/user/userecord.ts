import request from '@/utils/request'

// 使用记录列表
export function userecordLists(params?: Record<string, any>) {
    return request.get({ url: '/userecord/list', params })
}

// 使用记录详情
export function userecordDetail(params: Record<string, any>) {
    return request.get({ url: '/userecord/detail', params })
}

// 使用记录新增
export function userecordAdd(params: Record<string, any>) {
    return request.post({ url: '/userecord/add', params })
}

// 使用记录编辑
export function userecordEdit(params: Record<string, any>) {
    return request.post({ url: '/userecord/edit', params })
}

// 使用记录删除
export function userecordDelete(id) {
    return request.post({ url: '/userecord/del', data: id })
}
