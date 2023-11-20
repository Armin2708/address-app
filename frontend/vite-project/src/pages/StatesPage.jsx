import SidebarWithHeader from "../components/shared/SideBar.jsx";
import {useEffect, useState} from "react";
import {getStates} from "../services/stateClient.js";
import {Wrap, WrapItem, Spinner, Text, Button} from '@chakra-ui/react'
import {errorNotification} from "../services/notification.js";

import {Link, useParams} from "react-router-dom";
import CreateStateDrawerForm from "../components/state/CreateStateDrawerForm.jsx";
import StateCardWithImage from "../components/state/StateCard.jsx";

const StatesPage = () =>{

    const { countryId } = useParams();
    const [states, setStates] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");

    const fetchStates = () =>{
        setLoading(true);
        getStates(countryId).then(res =>{
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
                <Link to="/countries">
                    <Button colorScheme='green'>Return</Button>
                </Link>
                <CreateStateDrawerForm
                    fetchStates={fetchStates}
                ></CreateStateDrawerForm>
                <Text>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }


    if(states.length<=0){
        return (
            <SidebarWithHeader>
                <Link to="/countries">
                    <Button colorScheme='green'>Return</Button>
                </Link>
                <CreateStateDrawerForm
                    fetchStates={fetchStates}
                    ></CreateStateDrawerForm>
                <Text>No States Available</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <Link to="/countries">
                <Button colorScheme='green'>Return</Button>
            </Link>
            <CreateStateDrawerForm
                fetchStates={fetchStates}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {states.map((state,index) => (
                    <WrapItem key={index}>
                        <StateCardWithImage
                            stateId={state.state_id}
                            stateName={state.name}
                            fetchStates={fetchStates}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>

    )
}

export default StatesPage;