import axios from 'axios'
import { _get } from '@/api/api'
import { useArticleStore } from '@/stores/article'
import markdownToHtml from '@/utils/markdown'

export default {
  getTopAndFeaturedArticles: (articleStore: ReturnType<typeof useArticleStore>) => {
    return _get('/api/article/top-and-featured', {}, (data: any) => {
      data.topArticle.articleContent = markdownToHtml(data.topArticle.articleContent)
        .replace(/<\/?[^>]*>/g, '')
        .replace(/[|]*\n/, '')
        .replace(/&npsp;/gi, '')
      data.featuredArticles.forEach((item: any) => {
        item.articleContent = markdownToHtml(item.articleContent)
          .replace(/<\/?[^>]*>/g, '')
          .replace(/[|]*\n/, '')
          .replace(/&npsp;/gi, '')
      })
      articleStore.topArticle = data.topArticle
      articleStore.featuredArticles = data.featuredArticles
    })
  },
  getArticles: (params: any) => {
    return axios.get('/api/article/list', { params: params })
  },
  getArticlesByCategoryId: (params: any) => {
    return axios.get('/api/article/categoryId', { params: params })
  },
  getArticleById: (articleId: any) => {
    return axios.get('/api/article/' + articleId)
  },
  getAllCategories: () => {
    return axios.get('/api/categories/all')
  },
  getAllTags: () => {
    return axios.get('/api/tags/all')
  },
  getTopTenTags: () => {
    return axios.get('/api/tags/topTen')
  },
  getArticlesByTagId: (params: any) => {
    return axios.get('/api/article/tagId', { params: params })
  },
  getAllArchives: (params: any) => {
    return axios.get('/api/archives/all', { params: params })
  },
  login: (params: any) => {
    return axios.post('/api/users/login', params)
  },
  saveComment: (params: any) => {
    return axios.post('/api/comments/save', params)
  },
  getComments: (params: any) => {
    return axios.get('/api/comments', { params: params })
  },
  getTopSixComments: () => {
    return axios.get('/api/comments/topSix')
  },
  getAbout: () => {
    return axios.get('/api/about')
  },
  getFriendLink: () => {
    return axios.get('/api/links')
  },
  submitUserInfo: (params: any) => {
    return axios.put('/api/users/info', params)
  },
  getUserInfoById: (id: any) => {
    return axios.get('/api/users/info/' + id)
  },
  updateUserSubscribe: (params: any) => {
    return axios.put('/api/users/subscribe', params)
  },
  sendValidationCode: (username: any) => {
    return axios.get('/api/users/code', {
      params: {
        username: username
      }
    })
  },
  bindingEmail: (params: any) => {
    return axios.put('/api/users/email', params)
  },
  register: (params: any) => {
    return axios.post('/api/admin/users/register', params)
  },
  searchArticles: (params: any) => {
    return axios.get('/api/article/search', {
      params: params
    })
  },
  getAlbums: () => {
    return axios.get('/api/photos/albums')
  },
  getPhotosBuAlbumId: (albumId: any, params: any) => {
    return axios.get('/api/albums/' + albumId + '/photos', {
      params: params
    })
  },
  getWebsiteConfig: () => {
    return axios.get('/api')
  },
  qqLogin: (params: any) => {
    return axios.post('/api/users/oauth/qq', params)
  },
  report: () => {
    axios.post('/api/admin/report')
  },
  getTalks: (params: any) => {
    return axios.get('/api/talks', {
      params: params
    })
  },
  getTalkById: (id: any) => {
    return axios.get('/api/talks/' + id)
  },
  logout: () => {
    return axios.post('/api/users/logout')
  },
  getRepliesByCommentId: (commentId: any) => {
    return axios.get(`/api/comments/${commentId}/replies`)
  },
  updatePassword: (params: any) => {
    return axios.put('/api/users/password', params)
  },
  accessArticle: (params: any) => {
    return axios.post('/api/article/access', params)
  }
}
