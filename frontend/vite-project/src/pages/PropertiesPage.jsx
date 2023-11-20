import SidebarWithHeader from "../components/shared/SideBar.jsx";
import {useEffect, useState} from "react";
import {getProperties, getProperty} from "../services/propertyClient.js";
import {Wrap, WrapItem, Spinner, Text, Button} from '@chakra-ui/react'
import {errorNotification} from "../services/notification.js";

import {Link, useParams} from "react-router-dom";
import CreatePropertyDrawerForm from "../components/property/CreatePropertyDrawerForm.jsx";
import PropertyCardWithImage from "../components/property/PropertyCard.jsx";
import {getStreet} from "../services/streetClient.js";

const PropertiesPage = () =>{

    const { streetId } = useParams();
    const [properties, setProperties] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");
    const fetchCityId = async () => {
        try {
            const response = await getStreet(streetId);
            const streetData = response.data; // Assuming the cityId is in the response data
            const cityId = streetData.city_id; // Access the city_id property
        } catch (error) {
            console.error("Error:", error);
        }
    }
    const cityId = fetchCityId();

    const fetchProperties = () =>{
        setLoading(true);
        getProperties(streetId).then(res =>{
            setProperties(res.data)
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
            fetchProperties()
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
                <Link to={`/countries/states/${cityId}/streets`}>
                    <Button colorScheme='green'>Return</Button>
                </Link>
                <CreatePropertyDrawerForm
                    fetchProperties={fetchProperties}
                ></CreatePropertyDrawerForm>
                <Text>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }


    if(properties.length<=0){
        return (
            <SidebarWithHeader>
                <Link to={`/countries/states/${cityId}/streets`}>
                    <Button colorScheme='green'>Return</Button>
                </Link>
                <CreatePropertyDrawerForm
                    fetchProperties={fetchProperties}
                    ></CreatePropertyDrawerForm>
                <Text>No Properties Available</Text>
            </SidebarWithHeader>
        )
    }
    return (
        <SidebarWithHeader>
            <Link to={`/countries/states/${cityId}/streets`}>
                <Button colorScheme='green'>Return</Button>
            </Link>
            <CreatePropertyDrawerForm
                fetchProperties={fetchProperties}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {properties.map((property,index) => (
                    <WrapItem key={index}>
                        <PropertyCardWithImage
                            propertyId={property.property_id}
                            propertyName={property.name}
                            fetchProperties={fetchProperties}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>

    )
}

export default PropertiesPage;