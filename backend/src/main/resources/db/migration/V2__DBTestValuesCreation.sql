--Country
INSERT INTO countries(country_id, name) VALUES ('CT1','CountryTest1');
INSERT INTO countries(country_id, name) VALUES ('CT2','CountryTest2');

--States
INSERT INTO states(state_id, name,country_id) VALUES (10,'StateTest1','CT1');
INSERT INTO states(state_id, name,country_id) VALUES (20, 'StateTest2','CT2');

--Cities
INSERT INTO cities(city_id, name,state_id) VALUES (100,'CityTest1',10);
INSERT INTO cities(city_id, name,state_id) VALUES (200, 'CityTest2',20);

--Streets
INSERT INTO streets(street_id, name,city_id) VALUES (1000,'StreetTest1',100);
INSERT INTO streets(street_id, name,city_id) VALUES (2000, 'StreetTest2',200);

--Properties
INSERT INTO properties(property_id, name,street_id) VALUES (10000,'PropertyTest1',1000);
INSERT INTO properties(property_id, name,street_id) VALUES (20000, 'PropertyTest2',2000);