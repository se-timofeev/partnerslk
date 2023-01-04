create table items
(
    description      nvarchar(255),
    name             nvarchar(255),
    country          nvarchar(255),
    description_html nvarchar(max),
    is_group         bit,
    is_out_of_stock  bit,
    level            int,
    parent_id        varchar(255) collate SQL_Latin1_General_CP1_CI_AS,
    vendor_code      varchar(255) collate SQL_Latin1_General_CP1_CI_AS,
    id               varchar(50) not null
        primary key,
    updated          datetime2
)
go

create table partners
(
    id       varchar(50) not null
        primary key,
    name     nvarchar(255),
    discount int         not null,
    account  nvarchar(100)
)
go

create table prices
(
    id      varchar(50) not null
        primary key
        constraint prices_items_id_fk
            references items,
    retail  float       not null,
    sale    float       not null,
    updated datetime2
)
go

create table roles
(
    id   bigint identity
        primary key,
    name varchar(255) collate SQL_Latin1_General_CP1_CI_AS
)
go

create table users
(
    id         varchar(50)  not null
        constraint PK__users__id
            primary key,
    full_name  nvarchar(255),
    first_name nvarchar(200),
    last_name  nvarchar(200),
    mobile     varchar(12),
    email      varchar(255),
    name       varchar(15)  not null
        unique,
    password   varchar(255) not null,
    status     varchar(20)  not null,
    created    datetime2,
    updated    datetime2
)
go

create table user_partners
(
    user_id    varchar(50)
        constraint users_id_fk
            references users,
    partner_id varchar(50)
        constraint partner_id_fk
            references partners
)
go

create table user_roles
(
    user_id varchar(50) not null
        constraint FKhfh9dx7w3ubf1co1vdev94g3f
            references users,
    role_id bigint      not null
        constraint FKh8ciramu9cc9q3qcqiv4ue8a6
            references roles
)
go

create table dbo.contractors
(
    id             varchar(50)   not null
        primary key,
    name           nvarchar(100) not null,
    description    nvarchar(255) not null,
    inn            varchar(12)   not null,
    kpp            varchar(9)    not null,
    legal_address  nvarchar(255) not null,
    actual_address nvarchar(255) not null,
    partner_id     varchar(50)   not null
        constraint contractors_partners_id_fk
            references dbo.partners,
    updated        datetime2     not null

)


