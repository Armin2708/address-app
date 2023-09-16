import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {useEffect, useState} from "react";
import {getStates} from "./services/client.js";
import { Wrap, WrapItem, Spinner, Text } from '@chakra-ui/react'
import CreateCountryDrawerForm from "./components/country/CreateCountryDrawerForm.jsx";
import {errorNotification} from "./services/notification.js";
import CardWithImage from "./components/country/CountryCard.jsx";

const StatesPage = () =>{

    const [states, setStates] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");

    const fetchStates = () =>{
        setLoading(true);
        getStates().then(res =>{
            setStates(res.data)
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
            fetchStates()
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
                <CreateCountryDrawerForm
                    fetchStates={fetchStates}
                ></CreateCountryDrawerForm>
                <Text>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }


    if(states.length<=0){
        return (
            <SidebarWithHeader>
                <CreateCountryDrawerForm
                    fetchStates={fetchStates}
                    ></CreateCountryDrawerForm>
                <Text>No States Available</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <CreateCountryDrawerForm
                fetchStates={fetchStates}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {states.map((state,index) => (
                    <WrapItem key={index}>
                        <CardWithImage
                            {...state}
                            fetchStates={fetchStates}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>

    )
}

export default StatesPage;