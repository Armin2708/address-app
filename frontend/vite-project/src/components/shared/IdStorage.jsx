export function setCountryId(newCountryId) {
    localStorage.setItem('countryId', newCountryId);
}

export function setStateId(newStateId) {
    localStorage.setItem('stateId', newStateId);
}

export function setCityId(newCityId) {
    localStorage.setItem('cityId', newCityId);
}

export function setStreetId(newStreetId) {
    localStorage.setItem('streetId', newStreetId);
}

export function getCountryId() {
    return localStorage.getItem('countryId');
}

export function getStateId() {
    return localStorage.getItem('stateId');
}

export function getCityId() {
    return localStorage.getItem('cityId');
}

export function getStreetId() {
    return localStorage.getItem('streetId');
}
