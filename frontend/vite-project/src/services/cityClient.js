import axios from 'axios';

export const getCities = async (stateId) => {
    try {
        return  await axios.get(`http://localhost:8080/api/v1/countries/${stateId}/cities`);
    } catch (error) {
        throw error;
    }
}


export const saveCity = async (stateId,city) => {
    try {
        return await axios.post(
            `http://localhost:8080/api/v1/countries/${stateId}/cities`,
            city
        )
    } catch (e) {
        throw e;
    }
}

export const updateCity = async (cityId, updateRequest) => {
    try {
        console.log(updateRequest)
        return await axios.put(
            `http://localhost:8080/api/v1/countries/states/cities/${cityId}`,
            updateRequest
        )
    } catch (e) {
        throw e;
    }
}

export const deleteCity = async (cityId) => {
    try {
        return await axios.delete(
            `http://localhost:8080/api/v1/countries/states/cities/${cityId}`
        )
    } catch (e) {
        throw e;
    }
}
