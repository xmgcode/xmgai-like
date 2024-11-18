// 用于模拟接口请求
export const getRemoteData = (data = '获取数据', time = 2000) => {
    return new Promise((resolve) => {
        setTimeout(() => {
            console.log(`模拟获取接口数据`, data)
            resolve(data)
        }, time)
    })
}

// 获取数组随机项
export const getRandomElement = (arr) => {
    var randomIndex = Math.floor(Math.random() * arr.length);
    return arr[randomIndex];
}

// 指定范围随机数
export const getRandomNumber = (min, max) => {
    return Math.floor(Math.random() * (max - min + 1) + min);
}

// 节流
export const throttle = (fn, time) => {
    let timer = null
    return (...args) => {
        if (!timer) {
            timer = setTimeout(() => {
                timer = null
                fn.apply(this, args)
            }, time)
        }
    }
}
// 防抖
export const debounce = (fn, time) => {
    let timer = null
    return (...args) => {
        clearTimeout(timer)
        timer = setTimeout(() => {
            fn.apply(this, args)
        }, time)
    }
}
