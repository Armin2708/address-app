import axios from 'axios';

export const getProperty = async (propertyId) => {
    try{
        return await axios.get(`http://localhost:8080/api/v1/countries/states/cities/streets/properties/${propertyId}`)
    } catch (error){
        throw error;
    }
}
export const getProperties = async (streetId) => {
    try {
        return  await axios.get(`http://localhost:8080/api/v1/countries/states/cities/${streetId}/properties`);
    } catch (error) {
        throw error;
    }
}


export const saveProperty = async (streetId,property) => {
    try {
        return await axios.post(
            `http://localhost:8080/api/v1/countries/states/cities/${streetId}/properties`,
            property
        )
    } catch (e) {
        throw e;
    }
}

export const updateProperty = async (propertyId, updateRequest) => {
    try {
        return await axios.put(
            `http://localhost:8080/api/v1/countries/states/cities/streets/properties/${propertyId}`,
            updateRequest
        )
    } catch (e) {
        throw e;
    }
}

export const deleteProperty = async (propertyId) => {
    try {
        return await axios.delete(
            `http://localhost:8080/api/v1/countries/states/cities/streets/properties/${propertyId}`
        )
    } catch (e) {
        throw e;
    }
}
