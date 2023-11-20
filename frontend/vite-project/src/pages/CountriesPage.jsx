import SidebarWithHeader from "../components/shared/SideBar.jsx";
import {useEffect, useState} from "react";
import {getCountries} from "../services/countryClient.js";
import {Wrap, WrapItem, Spinner, Text, Button} from '@chakra-ui/react'
import CreateCountryDrawerForm from "../components/country/CreateCountryDrawerForm.jsx";
import {errorNotification} from "../services/notification.js";
import {Link} from "react-router-dom";
import CountryCardWithImage from "../components/country/CountryCard.jsx";


const MainPage = () =>{

    const [countries, setCountries] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");

    const fetchCountries = () =>{
        setLoading(true);
        getCountries().then(res =>{
            setCountries(res.data)
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
            fetchCountries()
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
                <Link to="/">
                    <Button colorScheme='green'>Return</Button>
                </Link>
                <CreateCountryDrawerForm
                    fetchCountries={fetchCountries}
                ></CreateCountryDrawerForm>
                <Text>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }


    if(countries.length<=0){
        return (
            <SidebarWithHeader>
                <Link to="/">
                    <Button colorScheme='green'>Return</Button>
                </Link>
                <CreateCountryDrawerForm
                    countryId={countries.id}
                    countryName={countries.name}
                    fetchCountries={fetchCountries}
                    ></CreateCountryDrawerForm>
                <Text>No Countries Available</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <Link to="/">
                <Button colorScheme='green'>Return</Button>
            </Link>
            <CreateCountryDrawerForm

                fetchCountries={fetchCountries}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {countries.map((country,index) => (
                    <WrapItem key={index}>
                        <CountryCardWithImage
                            countryId={country.id}
                            countryName={country.name}
                            fetchCountries={fetchCountries}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>

    )
}

export default MainPage;