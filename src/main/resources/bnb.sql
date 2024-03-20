CREATE TABLE public._user (
    birthdate date,
    user_id bigint NOT NULL,
    email character varying(255) UNIQUE,
    firstname character varying(255),
    lastname character varying(255),
    password character varying(255),
    role character varying(255),
    CONSTRAINT _user_pkey PRIMARY KEY (user_id),
    CONSTRAINT _user_role_check CHECK (role IN ('USER', 'ADMIN', 'MANAGER'))
);

ALTER TABLE public._user OWNER TO "postgresMaster";

CREATE SEQUENCE public._user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public._user_seq OWNER TO "postgresMaster";

CREATE TABLE public.token (
    expired boolean NOT NULL,
    id integer NOT NULL,
    revoked boolean NOT NULL,
    user_id bigint,
    token character varying(255),
    token_type character varying(255),
    CONSTRAINT token_pkey PRIMARY KEY (id),
    CONSTRAINT token_token_type_check CHECK (token_type = 'BEARER')
);

ALTER TABLE public.token OWNER TO "postgresMaster";

CREATE SEQUENCE public.token_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.token_seq OWNER TO "postgresMaster";

CREATE TABLE public.rooms (
    room_id SERIAL PRIMARY KEY,
    room_name VARCHAR(50) NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    price_per_night NUMERIC(10, 2) NOT NULL
);

CREATE TABLE public.bookings (
    booking_id SERIAL PRIMARY KEY,
    room_id INT REFERENCES rooms(room_id),
    guest_name VARCHAR(100) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE public.room_images (
    image_id SERIAL PRIMARY KEY,
    room_id INT REFERENCES rooms(room_id),
    image_url VARCHAR(255) NOT NULL
);

CREATE TABLE public.room_availability (
    availability_id SERIAL PRIMARY KEY,
    room_id INT REFERENCES rooms(room_id),
    availability_date DATE NOT NULL,
    is_available BOOLEAN NOT NULL
);

