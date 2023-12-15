insert into account(account_number, currency, full_name)
values ('4400459683', 'USD', 'Yelnur Doszhanuly'),
       ('4400303345', 'USD', 'SHOP') ON CONFLICT (account_number) DO NOTHING;


create sequence limit_seq start with 1 increment by 50;