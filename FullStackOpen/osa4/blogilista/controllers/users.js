const bcrypt = require('bcryptjs')
const mongoose = require('mongoose')
const usersRouter = require('express').Router()
const User = require('../models/user')

usersRouter.get('/', async (request, response) => {
  const users = await User.find({}).populate('blogs', { title: 1, author: 1, url: 1, id: 1 })
  response.json(users)
})



usersRouter.get('/:id', async (request, response) => {
  if (!mongoose.Types.ObjectId.isValid(request.params.id)) {
    return response.status(400).json({ error: 'malformatted id' })
  }
  const user = await User.findById(request.params.id).populate('blogs', { title: 1, author: 1, url: 1, id: 1 })
  if (user) {
    response.json(user)
  } else {
    response.status(404).end()
  }
})



usersRouter.post('/', async (request, response) => {
  const { username, name, password } = request.body

  if (!username || !password) {
    return response.status(400).json({ error: 'username and password are required' })
  }

  if (username.length < 3 || password.length < 3) {
    return response.status(400).json({ error: 'username and password must be at least 3 characters long' })
  }

  const existingUser = await User.findOne({ username })
  if (existingUser) {
    return response.status(400).json({ error: 'username must be unique' })
  }

  const saltRounds = 10
  const passwordHash = await bcrypt.hash(password, saltRounds)

  const user = new User({
    username,
    name,
    passwordHash
  })

  try {
    const savedUser = await user.save()
    response.status(201).json(savedUser)
  } catch (error) {
    console.error('Error in POST /api/users:', error)
    response.status(500).json({ error: 'server error' })
  }
})

module.exports = usersRouter
