CREATE TABLE IF NOT EXISTS RegisteredUser (
    user_id VARCHAR (30) PRIMARY KEY ,
    password VARCHAR (30) NOT NULL ,
    email VARCHAR (50) UNIQUE NOT NULL ,
    first_name VARCHAR (20) NOT NULL ,
    last_name VARCHAR (20) NOT NULL ,
    home_address VARCHAR (50) NOT NULL ,
    fiscal_code VARCHAR (16) NOT NULL ,

    CONSTRAINT useridlength CHECK ( char_length ( user_id ) >=
    4) ,
    CONSTRAINT passlength CHECK ( char_length ( password ) >=
    8) ,
    CONSTRAINT emailpattern CHECK (email::text ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'::text),
    CONSTRAINT fnamelength CHECK ( char_length ( first_name )
    >= 3) ,
    CONSTRAINT lnamelength CHECK ( char_length ( user_id ) >=
    3),
    CONSTRAINT fclength CHECK (char_length(fiscal_code) = 16),
    CONSTRAINT fcformat CHECK (fiscal_code ~* '^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$'::text)
);

CREATE TABLE IF NOT EXISTS Song (
    song_id VARCHAR (18) PRIMARY KEY ,
    title VARCHAR (50) NOT NULL ,
    author VARCHAR (50) NOT NULL ,
    year_released NUMERIC NOT NULL ,

    CONSTRAINT yearrange CHECK ( year_released <= ( CAST (
    date_part ('year', CURRENT_DATE ) AS NUMERIC )))
);

CREATE TABLE IF NOT EXISTS Playlist (
    playlist_name VARCHAR (50) NOT NULL ,
    song_id VARCHAR (18) REFERENCES Song
    ON DELETE CASCADE
    ON UPDATE CASCADE ,
    user_id VARCHAR (30) REFERENCES RegisteredUser
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    PRIMARY KEY(playlist_name, song_id, user_id)
);

CREATE TABLE IF NOT EXISTS Emotion (
    emotion_name VARCHAR (10) NOT NULL
    CONSTRAINT emotionenum CHECK ( emotion_name
    IN ('joy ','power ',
    'amazement ',
    'solemnity ',
    'tenderness ',
    'nostalgia ',
    'calmness ',
    'tension ',
    'sadness ')),

    user_id VARCHAR (30) NOT NULL REFERENCES registeredUser
    ON DELETE CASCADE
    ON UPDATE CASCADE ,

    song_id varchar (18) NOT NULL REFERENCES Song
    ON DELETE CASCADE
    ON UPDATE CASCADE ,

    score numeric NOT NULL
    CONSTRAINT scorerange CHECK ( score BETWEEN 1 AND 5) ,

    notes VARCHAR (255) ,

    PRIMARY KEY ( emotion_name , user_id , song_id )
);