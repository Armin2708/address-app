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