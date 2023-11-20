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
} from '@chakra-ui/react';
import DeletePropertyDrawerPopUp from "./DeletePropertyDrawerPopUp.jsx";
import UpdatePropertyDrawerForm from "./UpdatePropertyDrawerForm.jsx";

export default function PropertyCardWithImage({propertyId,propertyName,fetchProperties}) {
    const nameLowerCase=propertyName.toLowerCase();
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
                        <Tag borderRadius={"full"}>{propertyId}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {propertyName}
                        </Heading>

                    </Stack>

                    <Stack direction={'row'} justify={'center'} spacing={6} p={4}>
                        <Stack>
                            <UpdatePropertyDrawerForm
                                propertyId={propertyId}
                                propertyName={propertyName}
                                fetchProperties={fetchProperties}
                            />
                        </Stack>
                        <Stack>
                            <DeletePropertyDrawerPopUp
                                propertyId={propertyId}
                                propertyName={propertyName}
                                fetchProperties={fetchProperties}
                            />
                        </Stack>
                    </Stack>
                </Box>
            </Box>
        </Center>
    );
}