-- tables

CREATE TABLE IF NOT EXISTS category (
    id BIGSERIAL PRIMARY KEY,
    "name" varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS sleep (
    id BIGSERIAL PRIMARY KEY,
    start_dt TIMESTAMP WITHOUT TIME ZONE,
    end_dt TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS category_hierarchy (
    parent_id BIGSERIAL REFERENCES category(id) ON DELETE CASCADE NOT NULL,
    child_id BIGSERIAL REFERENCES category(id) ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (parent_id, child_id)
);

CREATE TABLE IF NOT EXISTS category_hierarchy_recursive(
    parent_id BIGINT REFERENCES category(id) ON DELETE CASCADE NOT NULL,
    child_id BIGINT REFERENCES category(id) ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (parent_id, child_id)
);

CREATE TABLE IF NOT EXISTS my_time(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    start_dt TIMESTAMP WITH TIME ZONE,
    end_dt TIMESTAMP WITH TIME ZONE
);