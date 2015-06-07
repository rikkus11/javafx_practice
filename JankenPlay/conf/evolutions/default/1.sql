# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table member (
  id                        bigint not null,
  name                      varchar(255),
  mail                      varchar(255),
  tel                       varchar(255),
  constraint pk_member primary key (id))
;

create table message (
  id                        bigint not null,
  name                      varchar(255),
  message                   varchar(255),
  member_id                 bigint,
  post_date                 timestamp not null,
  constraint pk_message primary key (id))
;

create table tickets (
  id                        bigint not null,
  title                     varchar(64) not null,
  start_date                timestamp,
  end_date                  timestamp,
  content                   text,
  created_datetime          timestamp not null,
  updated_datetime          timestamp not null,
  constraint pk_tickets primary key (id))
;

create table user_id (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint pk_user_id primary key (id))
;

create sequence member_seq;

create sequence message_seq;

create sequence tickets_seq;

create sequence user_id_seq;

alter table message add constraint fk_message_member_1 foreign key (member_id) references member (id);
create index ix_message_member_1 on message (member_id);



# --- !Downs

drop table if exists member cascade;

drop table if exists message cascade;

drop table if exists tickets cascade;

drop table if exists user_id cascade;

drop sequence if exists member_seq;

drop sequence if exists message_seq;

drop sequence if exists tickets_seq;

drop sequence if exists user_id_seq;

