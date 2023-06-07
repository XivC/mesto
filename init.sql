CREATE TABLE locations (
                           id SERIAL PRIMARY KEY,
                           latitude FLOAT NOT NULL,
                           longitude FLOAT NOT NULL
);

CREATE TABLE events (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        startTime TIMESTAMP NOT NULL,
                        endTime TIMESTAMP NOT NULL,
                        maxParticipants INT NOT NULL,
                        location_id INT NOT NULL,
                        needParticipatingApprove BOOLEAN NOT NULL,
                        tags TEXT,  -- эта я пока не придумал как хранить
                        FOREIGN KEY (location_id) REFERENCES locations(id)
);
