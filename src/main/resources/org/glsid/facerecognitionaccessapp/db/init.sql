-- Appartement Database schema

drop table if exists user_permissions;
drop table if exists attempts_pictures;
drop table if exists doors;
drop table if exists permissions;
drop table if exists users;
drop table if exists pictures;
drop table if exists attempts;


CREATE TABLE IF NOT EXISTS doors (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS permissions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    start_date DATE DEFAULT CURRENT_DATE, -- Date when the permission becomes active
    end_date DATE, -- Date when the permission expires, null if it never expires
    start_time TIME DEFAULT '00:00', -- Time when the permission becomes active
    end_time TIME DEFAULT '23:59', -- Time when the permission expires
    allowed_days VARCHAR(30) DEFAULT 'mon,tue,wed,thu,fri,sat,sun', -- Days when the permission is valid
    door_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (door_id) REFERENCES doors(id)
);

CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    bio TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- attempts_log: log of the attempts to access the doors
CREATE TABLE IF NOT EXISTS attempts (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER DEFAULT NULL, -- user_id is null if the user is not registered
    door_id INTEGER NOT NULL,
    status VARCHAR(20) DEFAULT 'unknown' CHECK(status IN ('unknown', 'success', 'failure')),
    confidence FLOAT DEFAULT 0.0,
    reason TEXT, -- ex: 'face not recognized', 'expired permission', 'not allowed day', etc.
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS pictures (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL,
    picture_path VARCHAR(255) NOT NULL,
    type TEXT CHECK(type IN ('profile', 'attempt', 'room')),
    user_id INTEGER, -- might be null if it is inserted from an unknown user attempting to access a door
    room_id INTEGER,
    attempt_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES doors(id) ON DELETE CASCADE,
    FOREIGN KEY (attempt_id) REFERENCES attempts(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS user_permissions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    permission_id INTEGER NOT NULL,
    status VARCHAR(20) DEFAULT 'pending' CHECK(status IN ('pending', 'active', 'inactive')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- -- access_requests: requests from guests to access the doors
-- CREATE TABLE IF NOT EXISTS access_requests (
--     id INTEGER PRIMARY KEY AUTOINCREMENT,
--     first_name VARCHAR(50) NOT NULL,
--     last_name VARCHAR(50) NOT NULL,
--     bio TEXT,
--     message TEXT,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );


-------------------------------------------------
--                  TRIGGERS                   --
-------------------------------------------------
CREATE TRIGGER IF NOT EXISTS update_doors_updated_at
    AFTER UPDATE ON doors
    FOR EACH ROW
BEGIN
    UPDATE doors SET updated_at = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;

CREATE TRIGGER IF NOT EXISTS update_permissions_updated_at
    AFTER UPDATE ON permissions
    FOR EACH ROW
BEGIN
    UPDATE permissions SET updated_at = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;

CREATE TRIGGER IF NOT EXISTS update_users_updated_at
    AFTER UPDATE ON users
    FOR EACH ROW
BEGIN
    UPDATE users SET updated_at = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;

CREATE TRIGGER IF NOT EXISTS update_pictures_updated_at
    AFTER UPDATE ON pictures
    FOR EACH ROW
BEGIN
    UPDATE pictures SET updated_at = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;

CREATE TRIGGER IF NOT EXISTS update_attempts_updated_at
    AFTER UPDATE ON attempts
    FOR EACH ROW
BEGIN
    UPDATE attempts SET updated_at = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;

CREATE TRIGGER IF NOT EXISTS update_user_permissions_updated_at
    AFTER UPDATE ON user_permissions
    FOR EACH ROW
BEGIN
    UPDATE user_permissions SET updated_at = CURRENT_TIMESTAMP WHERE id = OLD.id;
END;
