create table image
(
  id   integer primary key not null,
  name TEXT                not null,
  md5  TEXT
);

create table image_tag
(
  image_id integer not null,
  tag      TEXT    not null
);

create index image_tag_image_id_index on image_tag (image_id);
