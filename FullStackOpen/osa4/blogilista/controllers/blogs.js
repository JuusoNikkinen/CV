const blogsRouter = require('express').Router()
const Blog = require('../models/blog')
const mongoose = require('mongoose')


blogsRouter.get('/', async (request, response) => {
  const blogs = await Blog.find({}).populate('user', { username: 1, name: 1, id: 1 })
  response.json(blogs)
})


blogsRouter.get('/:id', async (request, response) => {
  if (!mongoose.Types.ObjectId.isValid(request.params.id)) {
    return response.status(400).json({ error: 'malformatted id' })
  }
  const blog = await Blog.findById(request.params.id).populate('user', { username: 1, name: 1, id: 1 })
  if (blog) {
    response.json(blog)
  } else {
    response.status(404).end()
  }
})


blogsRouter.post('/', async (request, response) => {
  const body = request.body

  if (!body.title || !body.url) {
    return response.status(400).json({ error: 'title or url missing' })
  }

  const user = request.user

  const blog = new Blog({
    title: body.title,
    author: body.author,
    url: body.url,
    likes: body.likes || 0,
    user: user._id
  })

  const savedBlog = await blog.save()
  user.blogs = user.blogs.concat(savedBlog._id)
  await user.save()

  response.status(201).json(savedBlog)
})


blogsRouter.delete('/:id', async (request, response) => {
  if (!mongoose.Types.ObjectId.isValid(request.params.id)) {
    return response.status(400).json({ error: 'malformatted id' })
  }

  const blog = await Blog.findById(request.params.id)
  if (!blog) {
    return response.status(404).json({ error: 'blog not found' })
  }

  const user = request.user
  if (blog.user.toString() !== user._id.toString()) {
    return response.status(401).json({ error: 'cant delete someone elses blog' })
  }

  await Blog.findByIdAndDelete(request.params.id)
  response.status(204).end()
})


blogsRouter.put('/:id', async (request, response) => {
  const id = request.params.id
  const { likes } = request.body

  if (!mongoose.Types.ObjectId.isValid(id)) {
    return response.status(400).json({ error: 'bad id' })
  }

  const updatedBlog = await Blog.findByIdAndUpdate(id, { likes }, { new: true })

  if (!updatedBlog) {
    return response.status(404).json({ error: 'blog not found' })
  }

  response.json(updatedBlog)
})




module.exports = blogsRouter
