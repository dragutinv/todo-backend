CREATE TABLE public.todo_item
(
    id        serial primary key,
    text      character varying(250) COLLATE pg_catalog."default" NOT NULL,
    createdat timestamp                                           NOT NULL,
    active    boolean                                             NOT NULL,
    CONSTRAINT todo_item_pkey PRIMARY KEY (id)
);

CREATE TABLE public.category
(
    id            serial primary key,
    category_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE public.todo_item_category
(
    todo_item_id integer NOT NULL,
    category_id  integer NOT NULL,
    CONSTRAINT todo_item_category_pkey PRIMARY KEY (todo_item_id, category_id)
);





