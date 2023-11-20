import SidebarWithHeader from "../components/shared/SideBar.jsx";
import {useEffect,useState} from "react";
import {getCities} from "../services/cityClient.js";
import {Wrap, WrapItem, Spinner, Text, Button} from '@chakra-ui/react'
import {errorNotification} from "../services/notification.js";

import {Link, useParams} from "react-router-dom";
import CreateCityDrawerForm from "../components/city/CreateCityDrawerForm.jsx";
import CityCardWithImage from "../components/city/CityCard.jsx";
import {getCountryId} from "../components/shared/IdStorage.jsx";

//const StoredCountryId = getCountryId();
//console.log(StoredCountryId);
const CitiesPage = () =>{

    const { stateId } = useParams();
    const [cities, setCities] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");

    const fetchCities = () =>{
        setLoading(true);
        getCities(stateId).then(res =>{
            setCities(res.data)
        }).catch(err =>{
            setError(err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally(() => {
            setLoading(false)
        })
    }

    useEffect(() => {
            fetchCities()
        },[])



    if(loading){
        return(
        <SidebarWithHeader>
            <Spinner
                thickness='4px'
                speed='0.65s'
                emptyColor='gray.200'
                color='blue.500'
                size='xl'
            />
        </SidebarWithHeader>
        )
    }

    if(err){
        return (
            <SidebarWithHeader>
                <Link to={`/cities/states`}>
                    <Button colorScheme='green'>Return</Button>
                </Link>
                <CreateCityDrawerForm
                    fetchCities={fetchCities}
                ></CreateCityDrawerForm>
                <Text>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }


    if(cities.length<=0){
        return (
            <SidebarWithHeader>
                <Link to={`/cities/states`}>
                    <Button colorScheme='green'>Return</Button>
                </Link>
                <CreateCityDrawerForm
                    fetchCities={fetchCities}
                    ></CreateCityDrawerForm>
                <Text>No Cities Available</Text>
            </SidebarWithHeader>
        )
    }

    return (

        <SidebarWithHeader>
            <Link to={`/cities/states`}>
                <Button colorScheme='green'>Return</Button>
            </Link>
            <CreateCityDrawerForm
                fetchCities={fetchCities}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {cities.map((city,index) => (
                    <WrapItem key={index}>
                        <CityCardWithImage
                            cityId={city.city_id}
                            cityName={city.name}
                            fetchCities={fetchCities}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>

    )
}

export default CitiesPage;