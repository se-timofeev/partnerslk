
insert into users (id,name,password,status)
values ('91ae04bc-6a19-47cf-b066-4cf2692d8213',
        'test_user',
        '$2a$10$lMOafQevsnvtAbSZKi6dyuCORSuD9fic/uLBPYRps6El2bgqOBF1u','ACTIVE')

insert into roles (name)
values ('ADMIN')

insert into roles (name)
values ('USER')

insert into user_roles (user_id, role_id)
values ('91ae04bc-6a19-47cf-b066-4cf2692d8213',1)
