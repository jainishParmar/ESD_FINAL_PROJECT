import Home from './Components/Home/Home'
import Navbar from './Components/Navbar/Navbar'
import Auth from './Components/Auth/Auth'
import { useDispatch, useSelector } from 'react-redux'
import { useEffect } from 'react'
import { getUserProfile } from './store/authReducer'
import { CircularProgress } from '@mui/material'


function App () {
  const dispatch = useDispatch()
  const { auth } = useSelector(store => store)

  useEffect(() => {
    console.log("app js load")
    dispatch(getUserProfile(localStorage.getItem('jwt')))
  }, [auth.loggedIn])

  useEffect(()=>{

  },[])

  return (
    <>
    
      {auth.loading ? (
        <div><CircularProgress /></div>
      ) : auth.user ? (
        <div>
          <Navbar user={auth.user}/>
          <Home />
        </div>
      ) : (
        <Auth />
      )}
    </>
  )
}

export default App
