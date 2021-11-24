create table member
(
    id         bigint       not null auto_increment,
    account_id varchar(255) not null,
    birth      date         not null,
    email      varchar(255) not null,
    gender     varchar(255) not null,
    name       varchar(255) not null,
    password   varchar(255) not null,
    parent_id  bigint,
    sitter_id  bigint,
    primary key (id)
) engine = InnoDB;

create table member_roles
(
    member_id bigint not null,
    roles     varchar(255)
) engine = InnoDB;


create table parent
(
    id                  bigint   not null auto_increment,
    request_information longtext not null,
    primary key (id)
) engine = InnoDB;


create table parent_children
(
    parent_id bigint       not null,
    age       integer      not null,
    birth     date         not null,
    gender    varchar(255) not null,
    name      varchar(255) not null,
    note      varchar(255) not null
) engine = InnoDB;


create table sitter
(
    id      bigint   not null auto_increment,
    bio     longtext not null,
    max_age integer,
    min_age integer,
    primary key (id)
) engine = InnoDB;


alter table member
    add constraint UK_i54h1gvvnejys85e9d9qo9f2u unique (account_id);


alter table member
    add constraint FK8b7eccg9xh0xncgrlufa55obc
        foreign key (parent_id)
            references parent (id);


alter table member
    add constraint FKa7otxbcpvhc8shu83dp5pomav
        foreign key (sitter_id)
            references sitter (id);


alter table member_roles
    add constraint FKet63dfllh4o5qa9qwm7f5kx9x
        foreign key (member_id)
            references member (id);


alter table parent_children
    add constraint FKdnxvj4hlnv40nix37bpjsvecn
        foreign key (parent_id)
            references parent (id);
