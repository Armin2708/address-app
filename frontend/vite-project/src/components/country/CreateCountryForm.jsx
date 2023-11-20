import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {saveCountry} from "../../services/countryClient.js";
import {successNotification, errorNotification} from "../../services/notification.js";

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
const CreateCountryForm = ({ fetchCountries }) => {
    return (
        <>
            <Formik
                initialValues={{
                    countryName: '',
                    countryId: ''
                }}
                validationSchema={Yup.object({
                    countryName: Yup.string()
                        .max(30, 'Must be 15 characters or less')
                        .required('Required'),
                    countryId: Yup.string()
                        .max(3, 'Must be 3 characters')
                        .required('Required')
                })}
                onSubmit={(country, {setSubmitting}) => {
                    setSubmitting(true);
                    saveCountry(country)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "Country saved",
                                `${country.name} was successfully saved`
                            )
                            fetchCountries();
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
                                name="countryName"
                                type="text"
                                placeholder="France"
                            />

                            <MyTextInput
                                label="Id"
                                name="countryId"
                                type="text"
                                placeholder="FRA"
                            />





                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default CreateCountryForm;