import { render, screen } from '@testing-library/react'
import '@testing-library/jest-dom'
import userEvent from '@testing-library/user-event'
import Blog from './Blog'
import BlogForm from './BlogForm'

//5.13----------------------------------------------------------------------
test('renders title', () => {
    const blog = {
        id: '1',
        title: 'Blog Title',
        author: 'Author',
        url: 'http://url.fi',
        likes: 1,
        user: {
            username: 'kayttajanimi',
            name: 'testikayttaja'
        }
    }
    render(
        <Blog blog={blog} updateBlog={() => {
        }} deleteBlog={() => { }} user={{ username: 'kayttajanimi' }}
        />)

    const titleElement = screen.getByText((content, element) => content.includes('Title'))

    expect(titleElement).toBeInTheDocument()
})

//5.14----------------------------------------------------------------------
test('clicking button shows blog details', async () => {
    const blog = {
        id: '1',
        title: 'Blog1',
        author: 'Anssi Author',
        url: 'http://urlitesti.fi',
        likes: 2,
        user: {
            username: 'user',
            name: 'namn'
        }
    }

    const mockHandler = vi.fn()

    render(
        <Blog blog={blog} updateBlog={() => { }} deleteBlog={() => { }} user={{ username: 'user' }}
        />)

    const user = userEvent.setup()
    const button = screen.getByText('view')
    await user.click(button)

    const urlElement = screen.getByText('http://urlitesti.fi')
    const likesElement = screen.getByText('likes 2')
    const userElement = screen.getByText('namn')

    expect(urlElement).toBeInTheDocument()
    expect(likesElement).toBeInTheDocument()
    expect(userElement).toBeInTheDocument()
})

//5.15----------------------------------------------------------------------
test('liking twice calls event handler twice', async () => {
    const blog = {
        id: '1',
        title: 'Blog1',
        author: 'Anssi Author',
        url: 'http://urlitesti.fi',
        likes: 2,
        user: {
            username: 'user',
            name: 'namn'
        }
    }

    const mockHandler = vi.fn()

    render(
        <Blog blog={blog} updateBlog={mockHandler} deleteBlog={() => { }} user={{ username: 'user' }} />
    )

    const user = userEvent.setup()
    const viewButton = screen.getByText('view')
    await user.click(viewButton)

    const likeButton = screen.getByText('like')
    await user.click(likeButton)
    await user.click(likeButton)

    expect(mockHandler.mock.calls).toHaveLength(2)
})