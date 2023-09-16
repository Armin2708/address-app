import MainPage from "./MainPage.jsx";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import StatesPage from "./StatesPage.jsx";

const App = () =>{
    return (
        <Router>
            <Routes>
                <Route path="/" exact element={<MainPage/>} />
                <Route path="/countries" exact element={<MainPage/>} />
                <Route path="/countries/states" element={<StatesPage/>} />
            </Routes>
        </Router>
    );
}

export default App;