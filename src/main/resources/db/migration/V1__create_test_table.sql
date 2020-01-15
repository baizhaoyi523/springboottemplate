create table test (
    id bigint identity(1, 1),
    text varchar(100),
    ntext nvarchar(max),
    constraint pk primary key (id),
    index text_index (text)
)
