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
    useColorModeValue,
    WrapItem,
    Wrap,
    ButtonGroup,
    Button,
} from '@chakra-ui/react';
import DeleteDrawerForm from "./DeleteCountryDrawerPopUp.jsx";
import UpdateCountryDrawerForm from "./UpdateCountryDrawerForm.jsx";
import { Link } from 'react-router-dom';

export default function CardWithImage({ id, name, fetchCountries }) {
    const nameLowerCase = name.toLowerCase();

    return (
        <Center py={6}>
            <Box
                maxW={'300px'}
                w={'full'}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'md'}
                overflow={'hidden'}
            >
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                        'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit={'cover'}
                />
                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={`https://www.countryflags.com/wp-content/uploads/${nameLowerCase}-flag-png-large.png`}
                        alt={`${nameLowerCase} flag`}
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={6}>
                    <Stack spacing={2} align={'center'} mb={5}>
                        <Tag borderRadius={"full"}>{id}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {name}
                        </Heading>
                    </Stack>

                    <Stack direction={'row'} justify={'center'} spacing={6} p={4}>
                        <Stack>
                            <UpdateCountryDrawerForm id={id} name={name} fetchCountries={fetchCountries} />
                        </Stack>
                        <Stack>
                            <DeleteDrawerForm id={id} name={name} />
                        </Stack>
                    </Stack>

                    {/* "States" button below the "Update" and "Delete" buttons */}
                    <Box display='flex' justifyContent='center' width='100%' py={12} mb={2}>
                        <Link to="/countries/states">
                            <Button colorScheme='yellow'>States</Button>
                        </Link>
                    </Box>
                </Box>
            </Box>
        </Center>
    );
}
