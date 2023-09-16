import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {saveState} from "../../services/client.js";
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
const CreateStateForm = ({ fetchStates }) => {
    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    id: ''
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    id: Yup.number()
                        .max(3, 'Must be 3 numbers or less')
                        .required('Required')
                })}
                onSubmit={(state, {setSubmitting}) => {
                    setSubmitting(true);
                    saveState(state)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "State saved",
                                `${state.name} was successfully saved`
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
                                name="name"
                                type="text"
                                placeholder="California"
                            />

                            <MyTextInput
                                label="Id"
                                name="id"
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

export default CreateStateForm;