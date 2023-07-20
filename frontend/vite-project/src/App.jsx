import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {useEffect, useState} from "react";
import {getCountries} from "./services/client.js";
import { Wrap, WrapItem, Spinner, Text } from '@chakra-ui/react'
import CardWithImage from "./components/Card.jsx";
import DrawerForm from "./components/DrawerForm.jsx";
import {errorNotification} from "./services/notification.js";

const App = () =>{

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
                <DrawerForm
                    fetchCountries={fetchCountries}
                ></DrawerForm>
                <Text>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }


    if(countries.length<=0){
        return (
            <SidebarWithHeader>
                <DrawerForm
                    fetchCountries={fetchCountries}
                    ></DrawerForm>
                <Text>No Countries Available</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <DrawerForm
                fetchCountries={fetchCountries}
                />
        </SidebarWithHeader>

    )
}

export default App;