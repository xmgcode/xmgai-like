export const NAVBAR = [
    {
        name: '首页',
        path: '/'
    },
    {
        name: 'AI换脸',
        path: '/ai/mj/aiswap'
    },
    {
        name: 'DALL-E3',
        path: '/ai/gpt/dalle'
    },
    {
        name: '艺术二维码',
        path: '/',
        children: [
            {
                name: '开始创作',
                path: 'artcode'
            },
            
            {
                name: '创作记录',
                path: 'artcode/history'
            },
            
        ]

    },
    {
        name: '充值中心',
        path: '/member'
    },
    {
        name: '关于赚钱',
        path: '/about'
    },
    // {
    //     name: '资讯',
    //     path: '/hotdata',
    //     component: 'hotdata'
    // },
    // {
    //     name: '资讯中心',
    //     path: '/information',
    //     component: 'information'
    // },
    // {
    //     name: '移动端',
    //     path: '/mobile',
    //     component: 'mobile'
    // },

    // {
    //     name: '管理后台',
    //     path: '/admin',
    //     component: 'admin'
    // }
]

export const SIDEBAR = [
    {
        module: 'personal',
        hidden: true,
        children: [
            {
                name: '个人中心',
                path: '/user',
                children: [
                    {
                        name: '我的点数',
                        path: 'points'
                    },
                    {
                        name: '消耗记录',
                        path: 'consumred'
                    },
                    // {
                    //     name: '我的接口',
                    //     path: 'apif'
                    // },
                    // {
                    //     name: '待支付',
                    //     path: 'apifnopay'
                    // },
                    {
                        name: '个人信息',
                        path: 'info'
                    },
                    // {
                    //     name: '我的收藏',
                    //     path: 'collection'
                    // }
                ]
            },

            {
                name: '账户设置',
                path: '/account',
                children: [
                    {
                        name: '账户安全',
                        path: 'security'
                    }
                ]
            }
        ]
    }
]
