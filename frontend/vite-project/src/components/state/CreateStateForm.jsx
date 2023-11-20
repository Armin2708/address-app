import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {saveState} from "../../services/stateClient.js";
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
const CreateStateForm = ({ fetchStates }) => {
    const { countryId } = useParams();
    return (
        <>
            <Formik
                initialValues={{
                    stateName: '',
                    stateId: '',
                    countryId:''
                }}
                validationSchema={Yup.object({
                    stateName: Yup.string()
                        .max(30, 'Must be 15 characters or less')
                        .required('Required'),
                    stateId: Yup.number()
                        .required()
                        .min(1, 'must be more than 1')
                        .max(99, 'must be less than 99')
                })}
                onSubmit={(state, {setSubmitting}) => {
                    setSubmitting(true);
                    saveState(countryId,state)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "State saved",
                                `${state.name} was successfully saved with id ${state.id}`
                            )
                            fetchStates();
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
                                name="stateName"
                                type="text"
                                placeholder="California"
                            />

                            <MyTextInput
                                label="Id"
                                name="stateId"
                                type="number"
                                placeholder="75"
                            />





                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default CreateStateForm;