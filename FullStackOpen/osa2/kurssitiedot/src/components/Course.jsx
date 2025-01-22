const Header = ({ header }) => {
  //console.log(header)
  return(
    <>
      <h1>{header}</h1>
    </>
  )
} 

const Content = ({ parts }) => {
  console.log(parts)
  
  return(
    <>
    {parts.map(part =>
      <Part key={part.id} part={part}/>
    )}

    </>
  )
}

const Part = ({ part }) => {
  //console.log(props)
  return(
    <>
      <p>
        {part.name} {part.exercises}
      </p>
    </>
  )
}

const Total = ({ parts }) => {
  console.log(parts[0].exercises)
  
/*   const tehtavat = parts.map (part => part.exercises)

  console.log("Lista tehtävien määrästä", tehtavat) */

  const summExercices = parts.reduce((sum, part) => sum + part.exercises, 0)

  //console.log(props)
  return(
    <>
      <strong>total of {summExercices} exercises</strong>
    </>
  )
}


const Course = ({ course }) => {  
  return(
    <>
    <Header header={course.name} />
    <Content parts={course.parts} />
      
    <Total parts={course.parts} />
    
    </>
  )
}
export default Course