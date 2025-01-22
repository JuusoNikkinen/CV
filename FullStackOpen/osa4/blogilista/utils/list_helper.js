const _ = require('lodash')

const dummy = (blogs) => {
    return 1
}

//teht 4.4----------------------------------------------------
const totalLikes = (blogs) => {
    return blogs.reduce((sum, blog) => sum + blog.likes, 0)
}

//teht 4.5----------------------------------------------------
const favoriteBlog = (blogs) => {
    if (blogs.length === 0) {
        return null
    }

    const favorite = blogs.reduce((prev, current) => {
        return (prev.likes > current.likes
            ? prev
            : current
        )
    })

    return {
        title: favorite.title,
        author: favorite.author,
        likes: favorite.likes
    }
}


//teht 4.6----------------------------------------------------
const mostBlogs = (blogs) => {
    if (blogs.length === 0) {
        return null
    }


    const authorCounts = _.countBy(blogs, 'author')
    const author = _.maxBy(Object.keys(authorCounts), author => authorCounts[author])

    return {
        author: author,
        blogs: authorCounts[author]
    }
}

//teht 4.7----------------------------------------------------
const mostLikes = (blogs) => {
    if (blogs.length === 0) {
        return null
    }

    const authors = _.groupBy(blogs, 'author')
    const authorLikes = _.map(authors, (blogs, author) => ({
        author: author,
        likes: _.sumBy(blogs, 'likes')
    }))

    return _.maxBy(authorLikes, 'likes')
}



module.exports = {
    dummy,
    totalLikes,
    favoriteBlog,
    mostBlogs,
    mostLikes
}