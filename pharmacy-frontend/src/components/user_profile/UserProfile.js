import './UserProfile.css'

const AccountSettings = () => {
    return (
      <div className='accountsettings'>
        <h1 className='mainhead1'>Personal Information</h1>
        <hr ></hr>
        <div className='form'>
          <div className='form-group'>
            <label htmlFor='name'>Username <span>*</span></label>
            <input type='text' name='name' id='name' />
          </div>
  
          <div className='form-group'>
            <label htmlFor='phone'>Phone/Mobile <span>*</span></label>
            <input type='text' name='phone' id='phone'
  
            />
          </div>
  
          <div className='form-group'>
            <label htmlFor='saddr'>Street Address <span>*</span></label>
            <input type='text' name='saddr' id='saddr' />
          </div>

          <div className='form-group'>
            <label htmlFor='city'>City <span>*</span></label>
            <input type='text' name='city' id='city' />
          </div>

          <div className='form-group'>
            <label htmlFor='country'>Country <span>*</span></label>
            <input type='text' name='country' id='country' />
          </div>

          <div className='form-group'>
            <label htmlFor='zipcode'>Zip Code <span>*</span></label>
            <input type='text' name='zipcode' id='zipcode' />
          </div>
  
        
        </div>
  
        <button className='mainbutton1'
          
          >Save Changes</button>
      </div>
    )
}
  
const ChangePassword = () => {
    return (
        <div className='accountsettings'>
            <h1 className='mainhead1'>Change Password</h1>
            <hr></hr>
            <div className='form'>
                <div className='form-group'>
                    <label htmlFor='curpass'>Current Password <span>*</span></label>
                    <input type="password"
                    />
                </div>

                <div className='form-group'>
                    <label htmlFor='newpass'>New Password <span>*</span></label>
                    <input type="password"
                    />
                </div>
                <div className='form-group'>
                    <label htmlFor='cnewpass'>Confirm New Password <span>*</span></label>
                    <input type="password"
                    />
                </div>


            </div>

            <button className='mainbutton1'

            >Save Changes</button>
        </div>
    )
}

  
const UserProfile = () => {
  return (
    <div className='userprofile'>

         <div className='userprofilein'>
            <div className='left'>
              <AccountSettings/>
              <ChangePassword/>
            </div>
            <div className='right'>
              Profile Picture
            </div>
         </div>
        
        </div>
  )
}

export default UserProfile