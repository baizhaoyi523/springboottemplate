create table contact
(
  short_link  text primary key not null unique,
  name        text             not null,
  appellation text             not null,
  note        text
);

create table contact_feedback
(
  contact_short_link text,
  feedback           text
);

create index contact_feedback_contact_short_link_index on contact_feedback (contact_short_link);
