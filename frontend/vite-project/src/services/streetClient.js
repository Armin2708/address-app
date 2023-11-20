import axios from 'axios';

export const getStreet = async (streetId) => {
    try{
        return await axios.get(`http://localhost:8080/api/v1/countries/states/cities/streets/${streetId}`)
    } catch (error){
        throw error;
    }
}

export const getStreets = async (cityId) => {
    try {
        return  await axios.get(`http://localhost:8080/api/v1/countries/states/${cityId}/streets`);
    } catch (error) {
        throw error;
    }
}


export const saveStreet = async (cityId,street) => {
    try {
        return await axios.post(
            `http://localhost:8080/api/v1/countries/states/${cityId}/streets`,
            street
        )
    } catch (e) {
        throw e;
    }
}

export const updateStreet = async (streetId, updateRequest) => {
    try {
        return await axios.put(
            `http://localhost:8080/api/v1/countries/states/cities/streets/${streetId}`,
            updateRequest
        )
    } catch (e) {
        throw e;
    }
}

export const deleteStreet = async (streetId) => {
    try {
        return await axios.delete(
            `http://localhost:8080/api/v1/countries/states/cities/streets/${streetId}`
        )
    } catch (e) {
        throw e;
    }
}
