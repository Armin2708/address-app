import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {saveProperty} from "../../services/propertyClient.js";
import {successNotification, errorNotification} from "../../services/notification.js";
import {useParams} from "react-router-dom";

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
const CreatePropertyForm = ({ fetchProperties }) => {
    const { streetId } = useParams();
    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    property_id: '',
                    streetId:''
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    property_id: Yup.number()
                        .min(1, 'Must be superior to 0')
                        .max(99999, 'Must be less 999')
                        .required(),
                })}
                onSubmit={(property, {setSubmitting}) => {
                    setSubmitting(true);
                    saveProperty(streetId,property)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "Property saved",
                                `${property.name} was successfully saved with id ${property.id}`
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
                {({isValid, isSubmitting}) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder="California"
                            />

                            <MyTextInput
                                label="Id"
                                name="property_id"
                                type="number"
                                placeholder="90"
                            />





                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default CreatePropertyForm;