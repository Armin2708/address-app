import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {updateProperty} from "../../services/propertyClient.js";
import {errorNotification, successNotification} from "../../services/notification.js";


const MyTextInput = ({label, ...props}) => {

    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

// And now we can use these
const UpdatePropertyForm = ({ fetchProperties, propertyName, propertyId }) => {
    return (
        <>
            <Formik
                initialValues={{
                    name: propertyName
            }}

                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),

                })}
                onSubmit={(updatedProperty, {setSubmitting}) => {
                    setSubmitting(true);
                    console.log(updatedProperty.name)
                    updateProperty(propertyId, updatedProperty)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "Property updated",
                                `${updatedProperty.name} was successfully updated with id ${updatedProperty.propertyId}`
                            )
                            fetchProperties();
                        }).catch(err => {
                        console.log(err);
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}
            >
                {({isValid, isSubmitting, dirty}) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder={propertyName}
                            />

                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default UpdatePropertyForm;