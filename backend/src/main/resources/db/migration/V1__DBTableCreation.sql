CREATE TABLE countries (
                        country_id TEXT PRIMARY KEY,
                        name TEXT not null
);

CREATE TABLE states(
                       state_id BIGSERIAL,
                       name TEXT NOT NULL,
                       country_id TEXT,
                       primary key (state_id),
                       foreign key (country_id) references countries(country_id) ON DELETE CASCADE
);
Create Table cities(
                         city_id BIGSERIAL,
                         name TEXT NOT NULL,
                         state_id BIGSERIAL,
                         primary key (city_id),
                         foreign key (state_id) references states(state_id) ON DELETE CASCADE
);
Create Table streets(
                         street_id BIGSERIAL,
                         name TEXT NOT NULL,
                         city_id BIGSERIAL,
                         primary key (street_id),
                         foreign key (city_id) references cities(city_id) ON DELETE CASCADE
);
Create Table properties(
                         property_id BIGSERIAL,
                         property_number TEXT NOT NULL,
                         street_id BIGSERIAL,
                         primary key (property_id),
                         foreign key (street_id) references streets(street_id) ON DELETE CASCADE
);
