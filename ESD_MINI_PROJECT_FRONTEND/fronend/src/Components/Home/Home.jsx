import React, { useEffect } from 'react'
import './Home.css'
import CardList from '../CardList/CardList'
import Filter from '../Filter/Filter'

const Home = () => {
 

  return (
    <>
      
        <div className='home-main'>
          <div className='home-left-panel'>
            <Filter />
          </div>

          <div className='home-right-panel'>
            <CardList />
          </div>
        </div>
      
    </>
  )
}

export default Home
