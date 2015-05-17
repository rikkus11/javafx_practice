# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table message (
  id                        bigint not null,
  name                      varchar(255),
  mail                      varchar(255),
  message                   varchar(255),
  post_date                 timestamp not null,
  constraint pk_message primary key (id))
;

create table user_id (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint pk_user_id primary key (id))
;

create sequence message_seq;

create sequence user_id_seq;




# --- !Downs

drop table if exists message cascade;

drop table if exists user_id cascade;

drop sequence if exists message_seq;

drop sequence if exists user_id_seq;

