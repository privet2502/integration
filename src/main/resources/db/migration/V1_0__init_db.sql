create table if not exists account (
                         id bigserial not null,
                         account_number varchar(10) not null unique ,
                         currency varchar(255) check (currency in ('USD','KZT','RUB')),
                         full_name varchar(255),
                         primary key (id)
);
    
create table if not exists currency (
                          id bigserial not null,
                          amount float(53) not null,
                          symbol varchar(255),
                          updated_at timestamp(6),
                          primary key (id)
);
    
create table if not exists good_limit (
                            id bigserial not null,
                            active boolean not null,
                            amount bigint,
                            created_at timestamp(6),
                            expense_category varchar(255) check (expense_category in ('SERVICE','GOOD')),
                            limit_to_account bigint,
                            primary key (id),
                            foreign key (limit_to_account) references account(id)
);
    
create table if not exists service_limit (
                               id bigserial not null,
                               active boolean not null,
                               amount bigint,
                               created_at timestamp(6),
                               expense_category varchar(255) check (expense_category in ('SERVICE','GOOD')),
                               limit_to_account bigint,
                               primary key (id),
                               foreign key (limit_to_account) references account(id)
);
    
create table if not exists transactions (
                              id bigserial not null,
                              currency varchar(255) check (currency in ('USD','KZT','RUB')),
                              datetime timestamp(6),
                              expense_category varchar(255) check (expense_category in ('SERVICE','GOOD')),
                              limit_exceeded boolean not null,
                              remainder float(53),
                              sum float(53),
                              transaction_to_account bigint,
                              transaction_to_counterparty_account bigint,
                              transaction_to_limit bigint,
                              primary key (id),
                              foreign key (transaction_to_account) references account(id),
                              foreign key (transaction_to_counterparty_account) references account(id)

);

