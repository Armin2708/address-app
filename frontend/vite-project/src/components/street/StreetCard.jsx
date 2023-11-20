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
import DeleteStreetDrawerPopUp from "./DeleteStreetDrawerPopUp.jsx";
import UpdateStreetDrawerForm from "./UpdateStreetDrawerForm.jsx";
import {Link} from "react-router-dom";
import {setCityId, setStreetId} from "../shared/IdStorage.jsx";

export default function StreetCardWithImage({streetId,streetName,fetchStreets}) {
    const nameLowerCase=streetName.toLowerCase();
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
                        <Tag borderRadius={"full"}>{streetId}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {streetName}
                        </Heading>

                    </Stack>

                    <Stack direction={'row'} justify={'center'} spacing={6} p={4}>
                        <Stack>
                            <UpdateStreetDrawerForm
                                streetId={streetId}
                                streetName={streetName}
                                fetchStreets={fetchStreets}
                            />
                        </Stack>
                        <Stack>
                            <DeleteStreetDrawerPopUp
                                streetId={streetId}
                                streetName={streetName}
                                fetchStreets={fetchStreets}
                            />
                        </Stack>
                    </Stack>
                    <Box display='flex' justifyContent='center' width='100%' py={12} mb={2}>
                    <Link to={`/countries/states/cities/${streetId}/properties`}>
                        <Button colorScheme='yellow' onClick={()=> setStreetId(streetId)}>Properties</Button>
                    </Link>
                    </Box>
                </Box>
            </Box>
        </Center>
    );
}