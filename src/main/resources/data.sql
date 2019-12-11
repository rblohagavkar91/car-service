/**
 * CREATE Script for init of DB
 */
------------------

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (1, now(), false, 'ABC123',
4, false, 5, 'DISEL', 'Suzuki');

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (2, now(), false, 'XYZ123',
6, false, 8, 'GAS', 'Honda');

insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (3, now(), false, 'PQR123',
8, false, 6, 'PETROL', 'Ford');

