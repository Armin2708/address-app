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
import DeleteCityDrawerPopUp from "./DeleteCityDrawerPopUp.jsx";
import UpdateCityDrawerForm from "./UpdateCityDrawerForm.jsx";
import {Link} from "react-router-dom";
import {setCityId} from "../shared/IdStorage.jsx";

export default function CityCardWithImage({cityId,cityName,fetchCities}) {
    const nameLowerCase=cityName.toLowerCase();
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
                        <Tag borderRadius={"full"}>{cityId}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {cityName}
                        </Heading>

                    </Stack>

                    <Stack direction={'row'} justify={'center'} spacing={6} p={4}>
                        <Stack>
                            <UpdateCityDrawerForm
                                cityId={cityId}
                                cityName={cityName}
                                fetchCities={fetchCities}
                            />
                        </Stack>
                        <Stack>
                            <DeleteCityDrawerPopUp
                                cityId={cityId}
                                cityName={cityName}
                                fetchCities={fetchCities}
                            />
                        </Stack>
                    </Stack>
                    <Box display='flex' justifyContent='center' width='100%' py={12} mb={2}>
                    <Link to={`/countries/states/${cityId}/streets`}>
                        <Button colorScheme='yellow' onClick={()=> setCityId(cityId)}>Streets</Button>
                    </Link>
                    </Box>
                </Box>
            </Box>
        </Center>
    );
}