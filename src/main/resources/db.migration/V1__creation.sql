CREATE TABLE countries(
    country_id BIGSERIAL PRIMARY KEY,
    name TEXT not null);

CREATE TABLE states(
    state_id BIGSERIAL PRIMARY KEY,
    name TEXT not null,
    countries_id BIGSERIAL,
    FOREIGN KEY (countries_id) REFERENCES countries(country_id));

CREATE TABLE cities(
    city_id BIGSERIAL PRIMARY KEY,
    name TEXT not null,
    states_id BIGSERIAL,
    FOREIGN KEY (states_id) REFERENCES states(state_id));

CREATE TABLE streets(
    street_id BIGSERIAL PRIMARY KEY,
    name TEXT not null,
    city_id BIGSERIAL,
    FOREIGN KEY (city_id) REFERENCES cities(city_id));

CREATE TABLE streetNumbers(
    streetNumber_id BIGSERIAL PRIMARY KEY,
    name TEXT not null,
    streets_id BIGSERIAL,
    FOREIGN KEY (streets_id) REFERENCES streets(street_id));