drop table if exists comments;

create table comments
(
    id        integer primary key autoincrement,
    author    text not null,
    body      text not null,
    post_slug text not null
);

create index idx_comments_post_id on comments (post_slug);
