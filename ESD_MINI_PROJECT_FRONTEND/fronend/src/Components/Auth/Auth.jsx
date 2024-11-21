import React, { useState } from 'react'
import LoginForm from './Signin/SigninForm'
import SignupForm from './Signup/SignupForm'
import './Auth.css'

const Auth = () => {
  const [isRegister, setIsRegister] = useState(false)

  const togglePanel = () => {
    setIsRegister(!isRegister)
  }

  return (
    <>
    <div className='flex justify-center h-screen items-center overflow-hidden '>

      <div className='box  lg:max-w-4xl'>
        <div className={`cover ${isRegister ? 'rotate-active' : ''}`}>
          <div class='front'>
            <img
              class=''
              src='https://d37c7ubwjknfep.cloudfront.net/wp-content/uploads/2017/12/IIIT-Bangalore-Campus.jpg'
              alt=''
            />
            <div class='text'>
              <span class='text-1'>
                INTERNATIONAL INSTITUTE OF INFORMATION TECHNOLOGY
              </span>
              <span class='text-2 text-xs'>BANGLORE</span>
            </div>
          </div>
          <div class='back'>
            <img
              src='https://d37c7ubwjknfep.cloudfront.net/wp-content/uploads/2017/12/IIIT-Bangalore-Campus.jpg'
              alt=''
            />
            <div class='text'>
              <span class='text-1'>
                INTERNATIONAL INSTITUTE OF INFORMATION TECHNOLOGY
              </span>
              <span class='text-2 text-xs'>BANGLORE</span>
            </div>
          </div>
        </div>
        <div className='forms h-full'>
          <div className='form-content h-full '>
            <div className='login-form '>
              <LoginForm togglePanel={togglePanel} />
            </div>
            <div className='signup-form'>
              <SignupForm togglePanel={togglePanel} />
            </div>
          </div>
        </div>
      </div>
    </div>
    </>
  )
}

export default Auth
