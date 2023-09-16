import React from "react";
import {
    AlertDialog,
    AlertDialogBody,
    AlertDialogContent,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogOverlay,
    Button,
    useDisclosure,
} from "@chakra-ui/react";
import { deleteCountry } from "../../services/client.js";
import { errorNotification, successNotification } from "../../services/notification.js";

function DeleteCountryPopUp({ id, name }) {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const cancelRef = React.useRef();

    const handleDelete = async () => {
        try {
            // Call the deleteCountry function with the given id
            await deleteCountry(id);
            // Display success notification
            successNotification(`${name} deleted successfully.`);
            // Close the popup after successful deletion
            onClose();
        } catch (error) {
            // Display error notification if deletion fails
            errorNotification("Failed to delete the country.");
        }
    };

    return (
        <>
            <Button
                bg={'red'}
                color={'white'}
                rounded={'full'}
                _hover={{
                    transform: 'translateY(-2px)',
                    boxShadow: 'lg'
                }}
                onClick={onOpen}>
                Delete {name}
            </Button>

            <AlertDialog isOpen={isOpen} leastDestructiveRef={cancelRef} onClose={onClose}>
                <AlertDialogOverlay>
                    <AlertDialogContent>
                        <AlertDialogHeader fontSize="lg" fontWeight="bold">
                            Delete {name}
                        </AlertDialogHeader>

                        <AlertDialogBody>
                            Are you sure? You can't undo this action afterwards.
                        </AlertDialogBody>

                        <AlertDialogFooter>
                            <Button ref={cancelRef} onClick={onClose}>
                                Cancel
                            </Button>
                            <Button colorScheme="red" onClick={handleDelete} ml={3}>
                                Delete
                            </Button>
                        </AlertDialogFooter>
                    </AlertDialogContent>
                </AlertDialogOverlay>
            </AlertDialog>
        </>
    );
}

export default DeleteCountryPopUp;
