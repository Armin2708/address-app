import SidebarWithHeader from "../components/shared/SideBar.jsx";
import {Box, Button, Input} from '@chakra-ui/react';
import {Link} from "react-router-dom";


const MainPage = () =>{



    return (
        <SidebarWithHeader>
            <Input placeholder='Basic usage' />
            <Box display='flex' justifyContent='center' width='100%' py={12} mb={2}>
                <Link to="/countries">
                    <Button colorScheme='green'>Countries</Button>
                </Link>
            </Box>
            <img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExNmhlazhuZ2ZnYmhleW10eTB2NDgxeWtyZXduejUwbjN3Z293bmJzaSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/W307DdkjIsRHVWvoFE/giphy.gif" alt="GIF Image" />
        </SidebarWithHeader>

    )
}

export default MainPage;