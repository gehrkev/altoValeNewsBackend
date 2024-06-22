create table media_file
(
    id        serial primary key,
    file_name varchar(255),
    file_type varchar(100),
    data      bytea
);

alter table publicacao drop column imagem;
alter table publicacao drop column video;

alter table publicacao add column imagem_id integer references media_file(id);
alter table publicacao add column video_id integer references media_file(id);