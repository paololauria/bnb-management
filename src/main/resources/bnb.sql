CREATE TABLE public._user (
    user_id SERIAL PRIMARY KEY,
    birthdate DATE,
    image VARCHAR(2000),
    email VARCHAR(255) UNIQUE,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
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
    location VARCHAR(255),
    max_guest INTEGER NOT NULL,
    price_per_night NUMERIC(10, 2) NOT NULL,
    description VARCHAR(1000),
    room_cover VARCHAR(1000)
);

CREATE TABLE public.bookings (
    booking_id SERIAL PRIMARY KEY,
    room_id INT REFERENCES rooms(room_id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES _user(user_id) ON DELETE CASCADE,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL,
    status VARCHAR(50)
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

CREATE TABLE public.amenities (
    amenity_id SERIAL PRIMARY KEY,
    room_id INT REFERENCES rooms(room_id) ON DELETE CASCADE,
    amenity_name VARCHAR(255) NOT NULL
    amenity_image VARCHAR(500) NOT NULL
);

CREATE TABLE public.reviews (
    review_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES _user(user_id) ON DELETE CASCADE,
    room_id INT REFERENCES rooms(room_id) ON DELETE CASCADE,
    rating INTEGER,
    comment TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



-- Insert for rooms
INSERT INTO public.rooms (room_name, max_guest, price_per_night, description, room_cover)
VALUES ('Camera Deluxe', '2', 150.00, 'On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/456706928.jpg?k=1e4aca73239b86b77e147b4249007245454ed0b6c306889fcbd4a8c383191d22&o=&hp=1');

INSERT INTO public.rooms (room_name, max_guest, price_per_night, description, room_cover)
VALUES ('Suite Presidenziale', '2', 300.00, 'But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/456706928.jpg?k=1e4aca73239b86b77e147b4249007245454ed0b6c306889fcbd4a8c383191d22&o=&hp=1');

INSERT INTO public.rooms (room_name, max_guest, price_per_night, description, room_cover)
VALUES ('Camera Standard', '2', 100.00, 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/456706928.jpg?k=1e4aca73239b86b77e147b4249007245454ed0b6c306889fcbd4a8c383191d22&o=&hp=1');

INSERT INTO public.rooms (room_name, max_guest, price_per_night, description, room_cover)
VALUES ('Camera Executive', '3', 200.00, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/456706928.jpg?k=1e4aca73239b86b77e147b4249007245454ed0b6c306889fcbd4a8c383191d22&o=&hp=1');
