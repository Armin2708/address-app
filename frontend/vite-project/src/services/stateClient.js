import axios from 'axios';

export const getStates = async (countryId) => {
    try {
        return  await axios.get(`http://localhost:8080/api/v1/${countryId}/states`);
    } catch (error) {
        throw error;
    }
}


export const saveState = async (countryId,state) => {
    try {
        console.log(state.stateId)
        return await axios.post(
            `http://localhost:8080/api/v1/${countryId}/states`,
            state
        )
    } catch (e) {
        throw e;
    }
}

export const updateState = async (stateId, updateRequest) => {
    try {
        return await axios.put(
            `http://localhost:8080/api/v1/countries/states/${stateId}`,
            updateRequest
        )
    } catch (e) {
        throw e;
    }
}

export const deleteState = async (stateId) => {
    try {
        return await axios.delete(
            `http://localhost:8080/api/v1/countries/states/${stateId}`
        )
    } catch (e) {
        throw e;
    }
}
