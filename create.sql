CREATE TABLE Availability (
  food_item_id Number,
  zip_code Number,
  time varchar2(20),
  begin_date Date,
  end_date Date,
  constraint availZip primary key (food_item_id,zip_code)
); 


CREATE TABLE Food_item (
  food_item_id Number Primary Key,
  name varchar2(10),
  description varchar2(60),
  price Number,
  type varchar2(8),
  is_veg varchar(3),
  image blob
);

CREATE TABLE Address (
  address_id Number Primary Key,
  street varchar2(50),
  city varchar2(15),
  zip_code Number not null,
  state varchar2(2)
);

CREATE TABLE Orders(
  order_id Number Primary Key,
  user_id Number,
  address_id Number,
  payment_type varchar2(20),
  order_date Date default sysdate not null,
  price Number,
  delivery_date Date,
  delivery_time timestamp
);



CREATE TABLE Online_user(
  user_id Number Primary Key,
  first_name varchar2(10),
  last_name varchar2(16),
  is_admin varchar2(3),
  password varchar2(50) not null,
  email varchar(35) not null,
  address_id Number,
  status varchar2(10) not null
);

CREATE TABLE Service_areas (
    zip_code Number not null,
    CONSTRAINT zip_code_pk PRIMARY KEY (zip_code)
);

CREATE TABLE Order_Items (
   order_id Number,
   food_item_id Number,
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

CREATE SEQUENCE user_seq START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE order_seq START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE address_seq START WITH 1000 INCREMENT BY 1;
CREATE SEQUENCE food_item_seq START WITH 1000 INCREMENT BY 1;