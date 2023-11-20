import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {saveCity} from "../../services/cityClient.js";
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
const CreateCityForm = ({ fetchCities }) => {
    const {stateId } = useParams();
    return (
        <>
            <Formik
                initialValues={{
                    cityName: '',
                    cityId: '',
                    stateId:''
                }}
                validationSchema={Yup.object({
                    cityName: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    cityId: Yup.number()
                        .required('Required')
                        .min(1, 'must be more than 1')
                        .max(999, 'must be less than 999')
                })}
                onSubmit={(city, {setSubmitting}) => {
                    setSubmitting(true);
                    saveCity(stateId,city)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "City saved",
                                `${city.cityName} was successfully saved with id ${city.cityId}`
                            )
                            fetchCities();
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
                                name="cityName"
                                type="text"
                                placeholder="Los Angeles"
                            />

                            <MyTextInput
                                label="Id"
                                name="cityId"
                                type="number"
                                placeholder="123"
                            />





                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default CreateCityForm;