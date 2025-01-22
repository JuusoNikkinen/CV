import { useState, useEffect } from 'react'
import Blog from './components/Blog'
import BlogForm from './components/BlogForm'
import Notification from './components/Notification'
import blogService from './services/blogs'
import loginService from './services/login'
import PropTypes from 'prop-types'

const App = () => {
  const [blogs, setBlogs] = useState([])
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [user, setUser] = useState(null)
  const [notification, setNotification] = useState(null)
  const [notificationType, setNotificationType] = useState('')
  const [blogformVisible, setBlogformVisible] = useState(false)

  useEffect(() => {
    blogService.getAll().then(blogs => {
      const blogsWithUser = blogs.map(blog => ({
        ...blog,
        user: blog.user ? blog.user : { name: 'Anonymous', username: '' }
      }))

      blogsWithUser.sort((a, b) => b.likes - a.likes)
      setBlogs(blogsWithUser)
    })
  }, [])

  useEffect(() => {
    const loggedUserJSON = window.localStorage.getItem('loggedBlogUser')
    if (loggedUserJSON) {
      const user = JSON.parse(loggedUserJSON)
      setUser(user)
      blogService.setToken(user.token)
    }
  }, [])

  const handleLogin = async (event) => {
    event.preventDefault()
    console.log('logging in with', username, password)

    try {
      const user = await loginService.login({
        username, password,
      })

      window.localStorage.setItem('loggedBlogUser', JSON.stringify(user))

      setUser(user)
      setUsername('')
      setPassword('')

      window.location.reload()

      setNotification(`logged in as ${user.name}`)
      setNotificationType('success')
      setTimeout(() => {
        setNotification(null)
      }, 5000)

    } catch (exception) {
      setNotification('wrong username or password')
      setNotificationType('error')
      setTimeout(() => {
        setNotification(null)
      }, 5000)
    }
  }

  const handleLogout = () => {
    window.localStorage.removeItem('loggedBlogUser')
    setUser(null)
    window.location.reload()
  }

  const addBlog = async (blogObject) => {
    try {
      const newBlog = { ...blogObject, user: { id: user.id, username: user.username, name: user.name } }
      const createdBlog = await blogService.create(newBlog)
      createdBlog.user = user

      setBlogs(blogs.concat(createdBlog).sort((a, b) => b.likes - a.likes))

      setNotification(`a new blog ${blogObject.title} by ${blogObject.author} added`)
      setNotificationType('success')
      setTimeout(() => {
        setNotification(null)
      }, 5000)
      setBlogformVisible(false)

    } catch (exception) {
      console.error('Error creating blog')

      setNotification('Error creating blog')
      setNotificationType('error')
      setTimeout(() => {
        setNotification(null)
      }, 5000)
    }
  }

  const updateBlog = async (id, updatedBlog) => {
    try {
      const blogToUpdate = blogs.find(blog => blog.id === id)
      const returnedBlog = await blogService.update(id, {
        ...updatedBlog,
        user: blogToUpdate.user
      })

      setBlogs(blogs.map(blog => blog.id !== id ? blog : returnedBlog).sort((a, b) => b.likes - a.likes))
    } catch (exception) {
      console.error('Error updating blog')

      setNotification('Error updating blog')
      setNotificationType('error')
      setTimeout(() => {
        setNotification(null)
      }, 5000)
    }
  }

  const deleteBlog = async (id) => {
    try {
      await blogService.remove(id)
      setBlogs(blogs.filter(blog => blog.id !== id))

      setNotification('Blog removed successfully')
      setNotificationType('success')
      setTimeout(() => {
        setNotification(null)
      }, 5000)
    } catch (exception) {
      console.error('Error removing blog')

      setNotification('Error removing blog')
      setNotificationType('error')
      setTimeout(() => {
        setNotification(null)
      }, 5000)
    }
  }

  const addBlogForm = () => {
    const hideWhenVisible = { display: blogformVisible ? 'none' : '' }
    const showWhenVisible = { display: blogformVisible ? '' : 'none' }

    return (
      <div>
        <div style={hideWhenVisible}>
          <button onClick={() => setBlogformVisible(true)}>new blog</button>
        </div>
        <div style={showWhenVisible}>
          <BlogForm createBlog={addBlog} />
          <button onClick={() => setBlogformVisible(false)}>cancel</button>
        </div>
      </div>
    )
  }

  if (user === null) {
    return (
      <div>
        <h2>log in to application</h2>
        <Notification message={notification} type={notificationType} />
        <form onSubmit={handleLogin}>
          <div>
            username
            <input
              type="text"
              value={username}
              name="Username"
              onChange={({ target }) => setUsername(target.value)}
            />
          </div>
          <div>
            password
            <input
              type="password"
              value={password}
              name="Password"
              onChange={({ target }) => setPassword(target.value)}
            />
          </div>
          <button type="submit">login</button>
        </form>
      </div>
    )
  }

  return (
    <div>
      <h2>blogs</h2>
      <Notification message={notification} type={notificationType} />
      {user && (
        <div>
          {user.name} logged in
          <button onClick={handleLogout}>logout</button>
        </div>
      )}
      {addBlogForm()}
      {blogs.map(blog =>
        <Blog key={blog.id} blog={blog} updateBlog={updateBlog} deleteBlog={deleteBlog} user={user} />
      )}
    </div>
  )
}

export default App
