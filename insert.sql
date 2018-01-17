Insert into Service_areas values (40201);
Insert into Service_areas values (30201);
Insert into Service_areas values (25301);
Insert into Service_areas values (10001);

Insert Into Address (street, city, zip_code, state) values ('125 Main rd.', 'Houston',40201,'TX');

Insert Into Address (street, city, zip_code, state) values ('34 Grand Ave.','New York',30201, 'NY');

Insert Into Address (street, city, zip_code, state) values ('1001 Walkers Ct.', 'Atlanta',25301,'GA');

Insert Into Address (street, city, zip_code, state) values ('N 50th St.','Miami', 10001,'FL');

Insert into Online_user(first_name,last_name,is_admin, password, email, address_id, status) values ('Jane', 'Broom', 'No', 'Bit2','JBroom@ymail.com',(Select Address_id from Address where state='TX') ,'Enabled');

Insert into Online_user(first_name,last_name,is_admin, password, email, address_id, status) values ('Dan', 'Bay', 'No', '2Gj','dbay@ymail.com',(Select Address_id from Address where state='NY') ,'Enabled');

Insert into Online_user(first_name,last_name,is_admin, password, email, address_id, status) values ('Joel', 'Moss', 'No', 'jmo34','Jmo@ymail.com',(Select Address_id from Address where state='GA') ,'Enabled');

Insert into Online_user(first_name,last_name,is_admin, password, email, address_id, status) values ('Geel', 'Dak', 'No', 'seeme4','JBroom@ymail.com',(Select Address_id from Address where state='FL') ,'Disabled');

Insert into Online_user(first_name,last_name,is_admin, password, email, address_id, status) values ('asd', 'asd', 'Yes', 'asd','asd',(Select Address_id from Address where state='FL') ,'Enabled');

INSERT into FOOD_ITEM (name, description, price, type, is_veg)values('Boons','Rice, peas, and Corn',8.99, 'Lunch', 'Yes');

INSERT into FOOD_ITEM (name, description, price, type, is_veg)values('Lunch','Kiwi, onions, and honey',7.99, 'Dinner', 'Yes');

INSERT into FOOD_ITEM (name, description, price, type, is_veg)values('Corn','corn',2.99, 'Side', 'Yes');

Insert into Availability (food_item_id,zip_code, time, begin_date, end_date) values ((select food_item_id from food_item where name='Boons'),40201, 'Morning', sysdate, '09-DEC-18');

Insert into Availability (food_item_id,zip_code, time, begin_date, end_date) values ((select food_item_id from food_item where name='Boons'),30201, 'Afternoon', sysdate, '09-DEC-18');

Insert into Availability (food_item_id,zip_code, time, begin_date, end_date) values ((select food_item_id from food_item where name='Boons'),25301,'Morning', sysdate, '11-MAY-18');

Insert into Availability (food_item_id,zip_code, time, begin_date, end_date) values ((select food_item_id from food_item where name='Corn'),10001, 'Evening', sysdate, '22-MAR-18');

INSERT into ORDERS (user_id,address_id,payment_type,order_date, price, delivery_date, delivery_time)values((Select user_id from online_user where First_name='Jane'),(Select Address_id from Address where state='TX'),'Card',sysdate, 20.00, last_day(sysdate), to_timestamp(last_day(sysdate)));

INSERT into ORDERS (user_id,address_id,payment_type,order_date, price, delivery_date, delivery_time)values((Select user_id from online_user where First_name='Dan'),(Select Address_id from Address where state='NY'),'Cash',sysdate, 10.00, last_day(sysdate), to_timestamp(last_day(sysdate)));

INSERT into ORDER_ITEMS (order_id, food_item_id, quantity)values((Select order_id from orders where price=20.00),(Select food_item_id from food_item where type='Lunch'),1);

INSERT into ORDER_ITEMS (order_id, food_item_id, quantity)values((Select order_id from orders where price=10.00),(Select food_item_id from food_item where type='Dinner'),2);