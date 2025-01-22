const { test, after, beforeEach } = require('node:test')
const mongoose = require('mongoose')
const supertest = require('supertest')
const app = require('../app')
const User = require('../models/user')
const assert = require('node:assert')

const api = supertest(app)

beforeEach(async () => {
    await User.deleteMany({})
})


test('a valid user can be added', async () => {
    const newUser = {
        username: 'johndoe',
        name: 'John Doe',
        password: 'password123'
    }

    await api
        .post('/api/users')
        .send(newUser)
        .expect(201)
        .expect('Content-Type', /application\/json/)

    const users = await api.get('/api/users')
    assert.strictEqual(users.body.length, 1)
    assert.strictEqual(users.body[0].username, 'johndoe')
})

test('user without username is not added', async () => {
    const newUser = {
        name: 'John Doe',
        password: 'password123'
    }

    await api
        .post('/api/users')
        .send(newUser)
        .expect(400)

    const users = await api.get('/api/users')
    assert.strictEqual(users.body.length, 0)
})

test('user without password is not added', async () => {
    const newUser = {
        username: 'johndoe',
        name: 'John Doe'
    }

    await api
        .post('/api/users')
        .send(newUser)
        .expect(400)

    const users = await api.get('/api/users')
    assert.strictEqual(users.body.length, 0)
})

test('username and password must have at least 3 characters', async () => {
    const newUser = {
        username: 'jo',
        name: 'John Doe',
        password: 'pw'
    }

    await api
        .post('/api/users')
        .send(newUser)
        .expect(400)

    const users = await api.get('/api/users')
    assert.strictEqual(users.body.length, 0)
})

test('username unique', async () => {
    const user1 = {
        username: 'johndoe',
        name: 'John Doe',
        password: 'password123'
    }

    const user2 = {
        username: 'johndoe',
        name: 'Jane Doe',
        password: 'password456'
    }

    await api
        .post('/api/users')
        .send(user1)
        .expect(201)

    await api
        .post('/api/users')
        .send(user2)
        .expect(400)

    const users = await api.get('/api/users')
    assert.strictEqual(users.body.length, 1)
})

after(async () => {
    await mongoose.connection.close()
})
