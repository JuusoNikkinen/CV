const { test, after, beforeEach } = require('node:test')
const mongoose = require('mongoose')
const supertest = require('supertest')
const app = require('../app')
const assert = require('node:assert')

const api = supertest(app)
const Blog = require('../models/blog')

const initialBlogs = [
    {
        title: 'Blogi 1',
        author: 'A',
        url: 'http://esimerkki.fi',
        likes: 2
    },
    {
        title: 'Blogi 2',
        author: 'B',
        url: 'http://exampe.com',
        likes: 8
    },
    {
        title: 'Blogi 3',
        author: 'C',
        url: 'http://blogi.fi',
        likes: 1
    }
]

beforeEach(async () => {
    await Blog.deleteMany({})
    let blogObject = new Blog(initialBlogs[0])
    await blogObject.save()

    blogObject = new Blog(initialBlogs[1])
    await blogObject.save()

    blogObject = new Blog(initialBlogs[2])
    await blogObject.save()
})

//teht 4.8----------------------------------------------------
test('there are three blogs', async () => {
    const response = await api.get('/api/blogs')
    assert.strictEqual(response.body.length, initialBlogs.length)
})

test('blogs are returned as json', async () => {
    await api
        .get('/api/blogs')
        .expect(200)
        .expect('Content-Type', /application\/json/)
})

//teht 4.9----------------------------------------------------
test('identifier is named id and not _id', async () => {
    const response = await api.get('/api/blogs')
    const blogs = response.body
    blogs.forEach((blog) => {
        assert.ok(blog.id)
        assert.strictEqual(blog._id, undefined)
    })
})

//teht 4.10----------------------------------------------------
test('blog can be added', async () => {
    const newBlog = {
        title: 'Uusi blogi',
        author: 'D',
        url: 'http://uusi.fi',
        likes: 999
    }

    await api
        .post('/api/blogs')
        .send(newBlog)
        .expect(201)
        .expect('Content-Type', /application\/json/)

    const response = await api.get('/api/blogs')
    const titles = response.body.map(blog => blog.title)
    const authors = response.body.map(blog => blog.author)
    const urls = response.body.map(blog => blog.url)
    const likes = response.body.map(blog => blog.likes)

    assert.strictEqual(response.body.length, initialBlogs.length + 1)
    assert.ok(titles.includes('Uusi blogi'))
    assert.ok(authors.includes('D'))
    assert.ok(urls.includes('http://uusi.fi'))
    assert.ok(likes.includes(999))
})

//teht 4.11----------------------------------------------------
test('no likes = 0', async () => {
    const newBlog = {
        title: 'Blog without likes',
        author: 'E',
        url: 'http://nolikes.fi'
    }


    await api
        .post('/api/blogs')
        .send(newBlog)
        .expect(201)
        .expect('Content-Type', /application\/json/)


    const response = await api.get('/api/blogs')
    const addedBlog = response.body.find(blog => blog.title === 'Blog without likes')

    assert.ok(addedBlog)
    assert.strictEqual(addedBlog.likes, 0)
})

//teht 4.12----------------------------------------------------
test('blog cant be added without url', async () => {
    const newBlog = {
        title: 'No URL',
        author: 'No URL',
        likes: 5
    }

    await api
        .post('/api/blogs')
        .send(newBlog)
        .expect(400)

    const response = await api.get('/api/blogs')
    assert.strictEqual(response.body.length, initialBlogs.length)
})

test('blog cant be added without title', async () => {
    const newBlog = {
        author: 'No Title',
        url: 'http://notitle.fi',
        likes: 5
    }

    await api
        .post('/api/blogs')
        .send(newBlog)
        .expect(400)

    const response = await api.get('/api/blogs')
    assert.strictEqual(response.body.length, initialBlogs.length)
})

//teht 4.13----------------------------------------------------
test('blog can be deleted', async () => {
    const responseAtStart = await api.get('/api/blogs')
    const blogToDelete = responseAtStart.body[0]

    await api
        .delete(`/api/blogs/${blogToDelete.id}`)
        .expect(204)

    const responseAtEnd = await api.get('/api/blogs')
    assert.strictEqual(responseAtEnd.body.length, initialBlogs.length - 1)

    const titles = responseAtEnd.body.map(r => r.title)
    assert.ok(!titles.includes(blogToDelete.title))
})

after(async () => {
    await mongoose.connection.close()
})


//teht 4.14----------------------------------------------------
test('blog can be updated', async () => {
    const responseAtStart = await api.get('/api/blogs')
    const blogToUpdate = responseAtStart.body[0]

    const updatedBlog = {
        likes: blogToUpdate.likes + 1
    }

    await api
        .put(`/api/blogs/${blogToUpdate.id}`)
        .send(updatedBlog)
        .expect(200)
        .expect('Content-Type', /application\/json/)

    const responseAtEnd = await api.get('/api/blogs')
    const updated = responseAtEnd.body.find(blog => blog.id === blogToUpdate.id)

    assert.ok(updated)
    assert.strictEqual(updated.likes, blogToUpdate.likes + 1)
})






after(async () => {
    await mongoose.connection.close()
})