import { useSelector, useDispatch } from 'react-redux'
import { updateAnecdote } from '../reducers/anecdoteReducer'
import { showNotification } from '../reducers/notificationReducer'

const AnecdoteList = () => {
  const anecdotes = useSelector(({ anecdotes, filter }) =>
    anecdotes.filter(anecdote =>
      anecdote.content.toLowerCase().includes(filter.toLowerCase())
    )
  )

  const dispatch = useDispatch()

  const vote = async (id) => {
    dispatch(updateAnecdote(id))
    const votedAnecdote = anecdotes.find(a => a.id === id)
    dispatch(showNotification(`You voted '${votedAnecdote.content}'`, 5))
  }

  const sortedAnecdotes = [...anecdotes].sort((a, b) => b.votes - a.votes)

  return (
    <div>
      {sortedAnecdotes.map(anecdote => (
        <div key={anecdote.id}>
          <div>{anecdote.content}</div>
          <div>
            has {anecdote.votes}
            <button onClick={() => vote(anecdote.id)}>vote</button>
          </div>
        </div>
      ))}
    </div>
  )
}


export default AnecdoteList