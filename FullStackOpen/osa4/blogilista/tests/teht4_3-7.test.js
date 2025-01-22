const { test, describe } = require('node:test')
const assert = require('node:assert')
const listHelper = require('../utils/list_helper')

//teht 4.3----------------------------------------------------
test('dummy returns one', () => {
    const blogs = []

    const result = listHelper.dummy(blogs)
    assert.strictEqual(result, 1)
})


//teht 4.4----------------------------------------------------
describe('total likes', () => {
    const listWithOneBlog = [
        {
            _id: '5a422aa71b54a676234d17f8',
            title: 'Go To Statement Considered Harmful',
            author: 'Edsger W. Dijkstra',
            url: 'http://www.u.arizona.edu/~rubinson/copyright_violations/Go_To_Considered_Harmful.html',
            likes: 5,
            __v: 0
        }
    ]

    const listWithZeroBlogs = []

    const listWithMultipleBlogs = [
        {
            _id: '1',
            title: 'AAA',
            author: 'Edsger',
            url: 'http://www.u.arizona.edu/~rubinson/copyright_violations/Go_To_Considered_Harmful.html',
            likes: 1,
            __v: 0
        },
        {
            _id: '2',
            title: 'BBB',
            author: 'W.',
            url: 'http://www.u.arizona.edu/~rubinson/copyright_violations/Go_To_Considered_Harmful.html',
            likes: 5,
            __v: 0
        },
        {
            _id: '3',
            title: 'CCC',
            author: 'Dijkstra',
            url: 'http://www.u.arizona.edu/~rubinson/copyright_violations/Go_To_Considered_Harmful.html',
            likes: 23,
            __v: 0
        }
    ]

    test('when list has only one blog equals the likes of that', () => {
        const result = listHelper.totalLikes(listWithOneBlog)
        assert.strictEqual(result, 5)
    })

    test('test with multiple blogs equals the likes of all blogs combined', () => {
        const result = listHelper.totalLikes(listWithMultipleBlogs)
        assert.strictEqual(result, 29)
    })
    test('empty list has 0 likes', () => {
        const result = listHelper.totalLikes(listWithZeroBlogs)
        assert.strictEqual(result, 0)
    })
})


//teht 4.5----------------------------------------------------
describe('favorite blog', () => {
    const listWithOneBlog = [
        {
            _id: '5a422aa71b54a676234d17f8',
            title: 'Go To Statement Considered Harmful',
            author: 'Edsger W. Dijkstra',
            url: 'http://www.u.arizona.edu/~rubinson/copyright_violations/Go_To_Considered_Harmful.html',
            likes: 5,
            __v: 0
        }
    ]

    const listWithMultipleBlogs = [
        {
            _id: '1',
            title: 'AAA',
            author: 'Edsger',
            url: 'http://example.com',
            likes: 1,
            __v: 0
        },
        {
            _id: '2',
            title: 'BBB',
            author: 'W.',
            url: 'http://example.com',
            likes: 5,
            __v: 0
        },
        {
            _id: '3',
            title: 'CCC',
            author: 'Dijkstra',
            url: 'http://example.com',
            likes: 23,
            __v: 0
        }
    ]

    test('when list has one blog return that blog', () => {
        const result = listHelper.favoriteBlog(listWithOneBlog)
        assert.deepStrictEqual(result, {
            title: 'Go To Statement Considered Harmful',
            author: 'Edsger W. Dijkstra',
            likes: 5
        })
    })

    test('when list has multiple blogs return one with most likes', () => {
        const result = listHelper.favoriteBlog(listWithMultipleBlogs)
        assert.deepStrictEqual(result, {
            title: 'CCC',
            author: 'Dijkstra',
            likes: 23
        })
    })

    test('empty list returns null', () => {
        const result = listHelper.favoriteBlog([])
        assert.strictEqual(result, null)
    })
})



//teht 4.6----------------------------------------------------
describe('most blogs', () => {
    const listWithOneBlog = [
        {
            _id: '5a422aa71b54a676234d17f8',
            title: 'Go To Statement Considered Harmful',
            author: 'Edsger W. Dijkstra',
            url: 'http://www.u.arizona.edu/~rubinson/copyright_violations/Go_To_Considered_Harmful.html',
            likes: 5,
            __v: 0
        }
    ]

    const listWithMultipleBlogs = [
        {
            _id: '1',
            title: 'AAA',
            author: 'Edsger',
            url: 'http://example.com',
            likes: 1,
            __v: 0
        },
        {
            _id: '2',
            title: 'BBB',
            author: 'W.',
            url: 'http://example.com',
            likes: 5,
            __v: 0
        },
        {
            _id: '3',
            title: 'CCC',
            author: 'Dijkstra',
            url: 'http://example.com',
            likes: 23,
            __v: 0
        },
        {
            _id: '4',
            title: 'DDD',
            author: 'Dijkstra',
            url: 'http://example.com',
            likes: 7,
            __v: 0
        }
    ]

    test('when list has one blog return the author with one blog', () => {
        const result = listHelper.mostBlogs(listWithOneBlog)
        assert.deepStrictEqual(result, {
            author: 'Edsger W. Dijkstra',
            blogs: 1
        })
    })

    test('when list has multiple blogs return the author with most blogs', () => {
        const result = listHelper.mostBlogs(listWithMultipleBlogs)
        assert.deepStrictEqual(result, {
            author: 'Dijkstra',
            blogs: 2
        })
    })

    test('empty list returns null', () => {
        const result = listHelper.mostBlogs([])
        assert.strictEqual(result, null)
    })
})

//teht 4.7----------------------------------------------------

describe('most likes', () => {
    const listWithOneBlog = [
        {
            _id: '5a422aa71b54a676234d17f8',
            title: 'Go To Statement Considered Harmful',
            author: 'Edsger W. Dijkstra',
            url: 'http://www.u.arizona.edu/~rubinson/copyright_violations/Go_To_Considered_Harmful.html',
            likes: 5,
            __v: 0
        }
    ]

    const listWithMultipleBlogs = [
        {
            _id: '1',
            title: 'AAA',
            author: 'Edsger',
            url: 'http://example.com',
            likes: 10,
            __v: 0
        },
        {
            _id: '2',
            title: 'BBB',
            author: 'W.',
            url: 'http://example.com',
            likes: 5,
            __v: 0
        },
        {
            _id: '3',
            title: 'CCC',
            author: 'Dijkstra',
            url: 'http://example.com',
            likes: 20,
            __v: 0
        },
        {
            _id: '4',
            title: 'DDD',
            author: 'Dijkstra',
            url: 'http://example.com',
            likes: 15,
            __v: 0
        }
    ]

    test('when list has one blog return the author with their likes', () => {
        const result = listHelper.mostLikes(listWithOneBlog)
        assert.deepStrictEqual(result, {
            author: 'Edsger W. Dijkstra',
            likes: 5
        })
    })

    test('when list has multiple blogs return the author with most likes', () => {
        const result = listHelper.mostLikes(listWithMultipleBlogs)
        assert.deepStrictEqual(result, {
            author: 'Dijkstra',
            likes: 35
        })
    })

    test('empty list returns null', () => {
        const result = listHelper.mostLikes([])
        assert.strictEqual(result, null)
    })
})
