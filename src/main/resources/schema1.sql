create table groups
(
    id       varchar(50) collate SQL_Latin1_General_CP1_CI_AS not null primary key,
    name     nvarchar(50)                                     not null,
    group_id varchar(50) collate SQL_Latin1_General_CP1_CI_AS,
    level    int                                               not null
)
go

create table items
(
    description      nvarchar(255),
    name             nvarchar(50),
    country          nvarchar(50),
    description_html nvarchar(max),
    is_out_of_stock  bit,
    level            int,
    group_id         varchar(50) collate SQL_Latin1_General_CP1_CI_AS
        constraint items_groups_fk
            references groups,
    vendor_code      varchar(50) collate SQL_Latin1_General_CP1_CI_AS,
    id               varchar(50) not null
        primary key,
    updated          datetime2,
    is_novelty       bit
)
go

create table prices
(
    id      varchar(50)
        primary key,
    retail  float       not null,
    sale    float       not null,
    updated datetime2,
    item_id varchar(50) not null unique
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
    full_name  nvarchar(50),
    first_name nvarchar(50),
    last_name  nvarchar(50),
    mobile     varchar(12),
    email      varchar(25),
    name       varchar(15)  not null
        unique,
    password   varchar(20) not null,
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
    name           nvarchar(50) not null,
    description    nvarchar(255) not null,
    inn            varchar(12)   not null,
    kpp            varchar(9)    not null,
    legal_address  nvarchar(70) not null,
    actual_address nvarchar(70) not null,
    partner_id     varchar(50)   not null
        constraint contractors_partners_id_fk
            references dbo.partners,
    updated        datetime2     not null

)

create table vt_order_statuses
(
    id           bigint not null
        primary key,
    order_status varchar(10),
    updated      datetime2,
    order_id     varchar(50)
        constraint FKqermtixnbf4936ayg8jix2ob5
            references orders,
    user_id       varchar(50)
)
go

create table orders_vt
(
    id       bigint not null
        primary key,
    amount   bigint,
    discount int,
    n_row    bigint,
    price    float,
    sale     float,
    total    float,
    item_id  varchar(50) not null
        constraint FKe2vvenpsuht663ti5jauhrx1n
            references items,
    order_id varchar(50) not null
        constraint FKq93kspsb17sd43yprlki3cs1s
            references orders
)
go
create table orders
(
    id                   varchar(50) not null
        primary key,
    status               varchar(10),
    num                  bigint,
    order_date           datetime2,
    sum_of_discount      float,
    sum_with_discount    float,
    sum_without_discount float,
    contractor_id        varchar(50) not null
        constraint FK3xvcmuk6a7ktrx3awo4ye55fs
            references contractors,
    partner_id           varchar(50) not null
        constraint FK3xvcmuk6a7ktrx3awo4ye99fs
            references partners
)
go

create table images
(
    id      varchar(36) not null
        primary key,
    base64  NTEXT       not null,
    item_id varchar(50) not null
        constraint images_fk_items references items
)
go

create table notifications
(
    id          int identity not null
        primary key,
    user_id     varchar(50)
        constraint notifications_on_users references users,
    order_id    varchar(50)
        constraint notifications_on_orders references orders,
    is_read     bit          not null,
    create_time datetime     not null,
    status      varchar(10) not null,
)
go

create table mails_for_notifications
(
    id      int identity not null
        primary key,
    user_id varchar(50)
        constraint mails_by_notifications_on_users references users,
    email   varchar(25) not null,
)
go

