import React, { useState } from 'react'
import PropTypes from 'prop-types'

const Blog = ({ blog, updateBlog, deleteBlog, user }) => {
  const [visible, setVisible] = useState(false)

  const blogStyle = {
    paddingTop: 10,
    paddingLeft: 2,
    border: 'solid',
    borderWidth: 1,
    marginBottom: 5
  }

  const toggleVisibility = () => {
    setVisible(!visible)
  }

  const handleLike = () => {
    const updatedBlog = {
      ...blog,
      likes: blog.likes + 1,
      user: blog.user
    }
    updateBlog(blog.id, updatedBlog)
  }

  const handleDelete = () => {
    if (window.confirm(`Remove blog ${blog.title} by ${blog.author}`)) {
      deleteBlog(blog.id)
    }
  }

  return (
    <div style={blogStyle}>
      <div>
        {blog.title} {blog.author} <button onClick={toggleVisibility}>{visible ? 'hide' : 'view'}</button>
      </div>
      {visible && (
        <div>
          <p>{blog.url}</p>
          <p>likes {blog.likes} <button onClick={handleLike}>like</button></p>
          {blog.user && <p>{blog.user.name}</p>}
          {user.username === blog.user.username && (
            <button onClick={handleDelete}>delete</button>
          )}
        </div>
      )}
    </div>
  )
}



export default Blog
