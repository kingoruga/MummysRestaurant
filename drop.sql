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

Drop sequence user_seq;
Drop sequence order_seq;
Drop sequence address_seq;
Drop sequence food_item_seq;