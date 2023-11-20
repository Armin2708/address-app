import MainPage from "./pages/MainPage.jsx";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import StatesPage from "./pages/StatesPage.jsx";
import CountriesPage from "./pages/CountriesPage.jsx";
import CitiesPage from "./pages/CitiesPage.jsx";
import StreetsPage from "./pages/StreetsPage.jsx";
import PropertiesPage from "./pages/PropertiesPage.jsx";

const App = () =>{
    return (
        <Router>
            <Routes>
                <Route path="/" exact element={<MainPage/>} />
                <Route path="/countries" exact element={<CountriesPage/>} />
                <Route path="/:countryId/states" element={<StatesPage />} />
                <Route path="/countries/:stateId/cities" element={<CitiesPage />} />
                <Route path="/countries/states/:cityId/streets" element={<StreetsPage />} />
                <Route path="/countries/states/cities/:streetId/properties" element={<PropertiesPage />} />
            </Routes>
        </Router>
    );
}

export default App;