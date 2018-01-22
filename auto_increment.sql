create or replace trigger user_add
before insert on online_user
for each row
begin
    select main_seq.NextVal into :new.user_id
    from dual;
end;
/
create or replace trigger order_add
before insert on orders
for each row
begin
    select main_seq.NextVal into :new.order_id
    from dual;
end;
/
create or replace trigger address_add
before insert on address
for each row
begin
    select main_seq.NextVal into :new.address_id
    from dual;
end;
/
create or replace trigger food_item_add
before insert on food_item
for each row
begin
    select main_seq.NextVal into :new.food_item_id
    from dual;
end;
/
