Alter table Order_Items drop constraint Order_items_fk;
Alter table Order_Items drop constraint Food_item_fk;
Alter table Availability drop constraint zip_code_fk;
Alter table Orders drop constraint order_address_id_fk;
Alter table Online_User drop constraint user_address_id_fk;
Alter table Availability drop constraint food_item_id_fk;

Drop Table Availability;
Drop Table Food_item;
Drop Table Address;
Drop Table Orders;
Drop Table Online_user;
Drop Table Service_areas;
Drop Table Order_items;

CREATE TABLE Availability (
  food_item_id Number,
  zip_code Number,
  time varchar2(20),
  begin_date Date,
  end_date Date,
  constraint availZip primary key (food_item_id,zip_code)
); 


CREATE TABLE Food_item (
  food_item_id Number primary key,
  name varchar2(10),
  description varchar2(60),
  price Number,
  type varchar2(8),
  is_veg varchar(3),
  image blob
);

CREATE TABLE Address (
  address_id raw(16) default sys_guid() Primary Key,
  street varchar2(50),
  city varchar2(15),
  zip_code Number not null,
  state varchar2(2)
);

CREATE TABLE Orders(
  order_id raw(16) default sys_guid() Primary Key,
  user_id raw(16),
  address_id raw(16),
  payment_type varchar2(20),
  order_date Date default sysdate not null,
  price Number,
  delivery_date Date,
  delivery_time timestamp
);



CREATE TABLE Online_user(
  user_id raw(16) default sys_guid() Primary Key,
  first_name varchar2(10),
  last_name varchar2(16),
  is_admin varchar2(3),
  password varchar2(50) not null,
  email varchar(35) not null,
  address_id raw(16),
  status varchar2(10) not null
);

CREATE TABLE Service_areas (
    zip_code Number not null,
    CONSTRAINT zip_code_pk PRIMARY KEY (zip_code)
);

CREATE TABLE Order_Items (
   order_id raw(16),
   food_item_id number,
   quantity Number(4),
   CONSTRAINT order_items_pk PRIMARY KEY (order_id, food_item_id)
);

Alter Table Order_Items ADD  CONSTRAINT Order_items_fk FOREIGN KEY (order_id) REFERENCES Orders (order_id) on delete cascade;
Alter Table Order_Items ADD  CONSTRAINT Food_item_fk FOREIGN KEY (food_item_id) REFERENCES Food_item (food_item_id) on delete cascade;
Alter Table Availability ADD  CONSTRAINT zip_code_fk FOREIGN KEY (zip_code) REFERENCES Service_areas (zip_code) on delete cascade;
Alter Table Availability ADD  CONSTRAINT food_item_id_fk FOREIGN KEY (food_item_id) REFERENCES food_item (food_item_id) on delete cascade;
Alter Table Orders ADD  CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES Online_User (user_id) on delete cascade;
Alter Table Orders ADD  CONSTRAINT order_address_id_fk FOREIGN KEY (address_id) REFERENCES Address (address_id) on delete cascade;
Alter Table Online_User ADD  CONSTRAINT user_address_id_fk FOREIGN KEY (address_id) REFERENCES Address (address_id) on delete cascade;

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

INSERT into FOOD_ITEM (food_item_id,name, description, price, type, is_veg)values(1,'Boons','Rice, peas, and Corn',8.99, 'Lunch', 'Yes');

INSERT into FOOD_ITEM (food_item_id,name, description, price, type, is_veg)values(2,'Lunch','Kiwi, onions, and honey',7.99, 'Dinner', 'Yes');

INSERT into FOOD_ITEM (food_item_id,name, description, price, type, is_veg)values(3,'Corn','corn',2.99, 'Side', 'Yes');

Insert into Availability (food_item_id,zip_code, time, begin_date, end_date) values ((select food_item_id from food_item where name='Boons'),40201, 'Morning', sysdate, '09-DEC-18');

Insert into Availability (food_item_id,zip_code, time, begin_date, end_date) values ((select food_item_id from food_item where name='Boons'),30201, 'Afternoon', sysdate, '09-DEC-18');

Insert into Availability (food_item_id,zip_code, time, begin_date, end_date) values ((select food_item_id from food_item where name='Boons'),25301,'Morning', sysdate, '11-MAY-18');

Insert into Availability (food_item_id,zip_code, time, begin_date, end_date) values ((select food_item_id from food_item where name='Corn'),10001, 'Evening', sysdate, '22-MAR-18');

INSERT into ORDERS (user_id,address_id,payment_type,order_date, price, delivery_date, delivery_time)values((Select user_id from online_user where First_name='Jane'),(Select Address_id from Address where state='TX'),'Card',sysdate, 20.00, last_day(sysdate), to_timestamp(last_day(sysdate)));

INSERT into ORDERS (user_id,address_id,payment_type,order_date, price, delivery_date, delivery_time)values((Select user_id from online_user where First_name='Dan'),(Select Address_id from Address where state='NY'),'Cash',sysdate, 10.00, last_day(sysdate), to_timestamp(last_day(sysdate)));

INSERT into ORDER_ITEMS (order_id, food_item_id, quantity)values((Select order_id from orders where price=20.00),(Select food_item_id from food_item where type='Lunch'),1);

INSERT into ORDER_ITEMS (order_id, food_item_id, quantity)values((Select order_id from orders where price=10.00),(Select food_item_id from food_item where type='Dinner'),2);