import React, { useState, useEffect } from 'react'
import axios from 'axios'

const CountryList = ({ countries, onCountryClick }) => {
  if (countries.length > 10) {
    return (
      <p>Too many matches, specify another filter</p>
    )
  }

  if (countries.length == 1) {
    return(
      <CountryDetail country={countries[0]} />
    )
  }

  const buttonStyle = {
    padding: 2
  }

  return (
    
    <ul>
      {countries.map(country => (
        <li key={country.cca3}>
          {country.name.common}
          <button style={buttonStyle} onClick={() => onCountryClick(country)}>show</button> </li> ))}
    </ul>
  )
}

const CountryDetail = ({ country }) => {
  const [weather, setWeather] = useState(null)
  const api_key = import.meta.env.VITE_WEATHER_API_KEY
  
  useEffect(() => {
    axios
      .get(`https://api.openweathermap.org/data/2.5/weather?q=${country.capital[0]}&appid=${api_key}&units=metric`)
      .then(response => {
        setWeather(response.data)
      }) }, [country, api_key])
      
      return (
      <div>
        <h2>{country.name.common}</h2>
        <p>capital {country.capital[0]}</p>
        <p>area {country.area}</p>
        
        
        
        <strong>languages:</strong>
      
        <ul>
          {Object.values(country.languages).map(lang => (
            <li key={lang}>{lang}</li>
          ))}
        </ul>
        
        <img src={country.flags.png} alt={`Flag of ${country.name.common}`} width="100" />  


        {weather && (
          <div>
            <h3>Weather in {country.capital[0]}</h3>
            <p>temperature {weather.main.temp} Celsius</p>
            <img src={`http://openweathermap.org/img/wn/${weather.weather[0].icon}.png`} alt="Weather icon" />
            <p>wind {weather.wind.speed} m/s</p>
            
            </div>
            )}
          </div>
          )
        }

const App = () => {
  const [countries, setCountries] = useState([])
  const [filter, setFilter] = useState('')
  const [selectedCountry, setSelectedCountry] = useState(null)

  useEffect(() => {
    axios.get('https://studies.cs.helsinki.fi/restcountries/api/all')
      .then(response => {
        setCountries(response.data)
      })
  }, [])

  const handleFilterChange = (event) => {
    setFilter(event.target.value)
    setSelectedCountry(null)
  }

  const filteredCountries = countries.filter(country =>
    country.name.common.toLowerCase().includes(filter.toLowerCase())
  )

  const handleCountryClick = (country) => {
    setSelectedCountry(country)
  }

  return (
    <div>
      find countries
      <input 
        value={filter} 
        onChange={handleFilterChange} 
        placeholder='' 
      />

      <CountryList 
        countries={filteredCountries} 
        onCountryClick={handleCountryClick} 
      />

      {selectedCountry && <CountryDetail country={selectedCountry} />}


    </div>
  )
}


export default App
