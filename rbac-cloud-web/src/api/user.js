import request from '@/utils/request'

export function login(params) {
  return request({
    url: '/auth/oauth/token',
    method: 'get',
    headers: {
      Authorization: 'Basic cGlnOnBpZw=='
    },
    params
  })
}

export function getInfo(token) {
  return request({
    url: '/code?randomStr=1234',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/mock/user/logout',
    method: 'post'
  })
}
