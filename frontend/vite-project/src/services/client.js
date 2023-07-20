import axios from 'axios';

export const getCountries= async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/countries`)
    }catch (e){
        throw e;
    }
}

export const saveCountries = async (countries) => {
    try{
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/countries`,
            countries
        )
    }catch (e){
        throw e;
    }
}