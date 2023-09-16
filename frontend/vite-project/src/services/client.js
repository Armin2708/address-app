import axios from 'axios';

export const getCountries= async () => {
    try {
        return await axios.get(`http://localhost:8080/api/v1/countries`)
    }catch (e){
        throw e;
    }
}

export const saveCountry = async (country) => {
    try {
        return await axios.post(
            `http://localhost:8080/api/v1/countries`,
            country
        )
    } catch (e) {
        throw e;
    }
}

    export const updateCountry = async (id, updateRequest) => {
        try {
            return await axios.put(
                `http://localhost:8080/api/v1/countries/${id}`,
                updateRequest
            )
        } catch (e) {
            throw e;
        }
    }

    export const deleteCountry = async (id) => {
        try {
            const countryId = id;
            return await axios.delete(
                `http://localhost:8080/api/v1/countries/${countryId}`
            )
        } catch (e) {
            throw e;
        }
}

export const getStates= async () => {
    try {
        return await axios.get(`http://localhost:8080/api/v1/countries/states`)
    }catch (e){
        throw e;
    }
}

export const saveState = async (state) => {
    try {
        return await axios.post(
            `http://localhost:8080/api/v1/countries/states`,
            state
        )
    } catch (e) {
        throw e;
    }
}

export const updateState = async (id, updateRequest) => {
    try {
        return await axios.put(
            `http://localhost:8080/api/v1/countries/states/${id}`,
            updateRequest
        )
    } catch (e) {
        throw e;
    }
}

export const deleteState = async (id) => {
    try {
        const stateId = id;
        return await axios.delete(
            `http://localhost:8080/api/v1/countries/states/${stateId}`
        )
    } catch (e) {
        throw e;
    }
}
