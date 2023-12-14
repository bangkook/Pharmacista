import Sidebar from "./side-bar/Sidebar"
import './home.css'

const Home = () => {
    return (
        <div className="home">
            <div className="homein"> 
            <div className="left">
                <Sidebar></Sidebar>
            </div>
            <div className="right">
                Dashboard
            </div>
            </div>
        </div>
    )
}

export default Home