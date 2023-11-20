import SidebarWithHeader from "../components/shared/SideBar.jsx";
import {useEffect, useState} from "react";
import {getStreets} from "../services/streetClient.js";
import {Wrap, WrapItem, Spinner, Text, Button} from '@chakra-ui/react'
import {errorNotification} from "../services/notification.js";

import {Link, useParams} from "react-router-dom";
import CreateStreetDrawerForm from "../components/street/CreateStreetDrawerForm.jsx";
import StreetCardWithImage from "../components/street/StreetCard.jsx";


const StreetsPage = () =>{

    const { cityId } = useParams();
    const [streets, setStreets] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");

    const fetchStreets = () =>{
        setLoading(true);
        getStreets(cityId).then(res =>{
            setStreets(res.data)
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
            fetchStreets()
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
                <Link to="/countries/states/cities">
                    <Button colorScheme='green'>Return</Button>
                </Link>
                <CreateStreetDrawerForm
                    fetchStreets={fetchStreets}
                ></CreateStreetDrawerForm>
                <Text>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }


    if(streets.length<=0){
        return (
            <SidebarWithHeader>
                <Link to="/countries/states/cities">
                    <Button colorScheme='green'>Return</Button>
                </Link>
                <CreateStreetDrawerForm
                    fetchStreets={fetchStreets}
                    ></CreateStreetDrawerForm>
                <Text>No Streets Available</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <Link to="/countries/states/cities">
                <Button colorScheme='green'>Return</Button>
            </Link>
            <CreateStreetDrawerForm
                fetchStreets={fetchStreets}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {streets.map((street,index) => (
                    <WrapItem key={index}>
                        <StreetCardWithImage
                            streetId={street.street_id}
                            streetName={street.name}
                            fetchStreets={fetchStreets}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>

    )
}

export default StreetsPage;