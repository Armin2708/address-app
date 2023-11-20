import {
    Heading,
    Avatar,
    Box,
    Center,
    Image,
    Flex,
    Text,
    Stack,
    Tag,
    useColorModeValue, Button,
} from '@chakra-ui/react';
import DeleteStateDrawerPopUp from "./DeleteStateDrawerPopUp";
import UpdateStateDrawerForm from "./UpdateStateDrawerForm.jsx";
import {Link} from "react-router-dom";
import {setCountryId, setStateId} from "../shared/IdStorage.jsx";

export default function StateCardWithImage({stateId,stateName,fetchStates}) {
    const nameLowerCase=stateName.toLowerCase();
    return (
        <Center py={6}>
            <Box
                maxW={'300px'}
                w={'full'}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'md'}
                overflow={'hidden'}>
                <Image
                    h={'120px'}
                    w={'full'}
                    src={'https://i.kym-cdn.com/entries/icons/facebook/000/040/095/sparkleschampagne.jpg'
                    }
                    objectFit={'cover'}
                />
                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={
                            `https://i.ytimg.com/vi/8Q26YeiYMHM/maxresdefault.jpg`
                        }
                        alt={`${nameLowerCase} flag`}
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={6}>
                    <Stack spacing={2} align={'center'} mb={5}>
                        <Tag borderRadius={"full"}>{stateId}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {stateName}
                        </Heading>

                    </Stack>

                    <Stack direction={'row'} justify={'center'} spacing={6} p={4}>
                        <Stack>
                            <UpdateStateDrawerForm
                                stateId={stateId}
                                stateName={stateName}
                                fetchStates={fetchStates}
                            />
                        </Stack>
                        <Stack>
                            <DeleteStateDrawerPopUp
                                stateId={stateId}
                                stateName={stateName}
                                fetchStates={fetchStates}
                            />
                        </Stack>
                    </Stack>
                    <Box display='flex' justifyContent='center' width='100%' py={12} mb={2}>
                        <Link to={`/countries/${stateId}/cities`}>
                            <Button colorScheme='yellow'onClick={() => setStateId(stateId)}>Cities</Button>
                        </Link>
                    </Box>
                </Box>
            </Box>
        </Center>
    );
}