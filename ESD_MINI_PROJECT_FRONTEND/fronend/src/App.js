
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { getUserProfile } from "./Store/AuthReducer";
import Auth from './Components/Auth/Auth'
import Navbar from "./Components/Navbar/Navbar/Navbar";
import Home from "./Components/Home/Home";
function App() {
  
  const dispatch=useDispatch()
  const {auth}=useSelector(store=>store)
  useEffect(()=>{
    dispatch(getUserProfile(localStorage.getItem("jwt")))
    
  },[auth.jwt])
  return (
    <>
    {auth.user?<div>
            <Navbar  />
            <Home/>
      </div>:<Auth/> }
    </>
  );
}

export default App;
