import Sidebar from "./side-bar/Sidebar"
import ListOfMediciens from "./listOfMediciens"
import './home.css'

const Home = ({userId = 1, isAdmin = false}) => {
    console.log(userId)
    return (
        <div className="home">
            <div className="homein"> 
            <div className="left">
                <Sidebar isAdmin={isAdmin}></Sidebar>
            </div>
            <div className="right">
                <ListOfMediciens userId={userId}/>
            </div>
            </div>
        </div>
    )
}

export default Home