import { useDispatch, useSelector } from 'react-redux'
import { useEffect } from 'react'
import { getUserProfile } from './Store/AuthReducer'
import Auth from './Components/Auth/Auth'
import Navbar from './Components/Navbar/Navbar/Navbar'
import Home from './Components/Home/Home'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router'

function App () {
  const dispatch = useDispatch()
  const { auth } = useSelector(store => store)
  useEffect(() => {
    dispatch(getUserProfile(localStorage.getItem('jwt')))
  }, [auth.jwt])
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route
            path='/'
            element={
              auth.user === null ? (
                <Navigate to='/login' />
              ) : (
                <Navigate to='/hostel' />
              )
            }
          />
          <Route path='login' element={<Auth />} />
          <Route
            path='hostel'
            element={auth.user === null ? <Navigate to='/login' /> : <Home />}
          />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
