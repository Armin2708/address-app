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
import DeleteCountryDrawerPopUp from "./DeleteCountryDrawerPopUp.jsx";
import UpdateCountryDrawerForm from "./UpdateCountryDrawerForm.jsx";
import {Link} from "react-router-dom";
import {setCountryId} from "../shared/IdStorage.jsx";

export default function CountryCardWithImage({countryId,countryName,fetchCountries}) {
    const nameLowerCase=countryName.toLowerCase();
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
                    src={'https://i.natgeofe.com/n/2a832501-483e-422f-985c-0e93757b7d84/6_3x2.jpg'
                    }
                    objectFit={'cover'}
                />
                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={
                            `https://www.countryflags.com/wp-content/uploads/${nameLowerCase}-flag-png-large.png`
                        }
                        alt={`${nameLowerCase} flag`}
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={6}>
                    <Stack spacing={2} align={'center'} mb={5}>
                        <Tag borderRadius={"full"}>{countryId}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {countryName}
                        </Heading>

                    </Stack>

                    <Stack direction={'row'} justify={'center'} spacing={6} p={4}>
                        <Stack>
                            <UpdateCountryDrawerForm
                                countryId={countryId}
                                countryName={countryName}
                                fetchCountries={fetchCountries}
                            />
                        </Stack>
                        <Stack>
                            <DeleteCountryDrawerPopUp
                                countryId={countryId}
                                countryName={countryName}
                                fetchCountries={fetchCountries}
                            />
                        </Stack>
                    </Stack>
                    <Box display='flex' justifyContent='center' width='100%' py={12} mb={2}>
                        <Link to={`/${countryId}/states`}>
                            <Button colorScheme='yellow' onClick={() => setCountryId(countryId)}>States </Button>
                        </Link>
                    </Box>
                </Box>
            </Box>
        </Center>
    );
}