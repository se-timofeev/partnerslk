
insert into users (id,name,password,status)
values ('91ae04bc-6a19-47cf-b066-4cf2692d8213',
        'test_user',
        '$2a$10$5aMeDsiVTo14JW15KBeeweOMLSrOLREpavcVGP4jY9cKxxxLtosT6','ACTIVE')

insert into roles (name)
values ('ADMIN')

insert into roles (name)
values ('USER')

insert into user_roles (user_id, role_id)
values ('91ae04bc-6a19-47cf-b066-4cf2692d8213',1)
