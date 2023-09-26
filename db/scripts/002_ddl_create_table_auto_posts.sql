create table auto_posts
(
    id           SERIAL PRIMARY KEY,
    description  varchar not null,
    created      timestamp,
    auto_user_id int references auto_users(id)
);