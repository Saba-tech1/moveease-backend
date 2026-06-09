-- ============================================================
-- MoveEase Database Setup Script
-- Run this in MySQL Workbench or MySQL CLI before starting app
-- ============================================================

-- 1. Create the database
CREATE DATABASE IF NOT EXISTS moveease_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE moveease_db;

-- 2. Create a dedicated user (safer than using root)
CREATE USER IF NOT EXISTS 'moveease_user'@'localhost' IDENTIFIED BY 'MoveEase@2024';
GRANT ALL PRIVILEGES ON moveease_db.* TO 'moveease_user'@'localhost';
FLUSH PRIVILEGES;

-- ============================================================
-- NOTE: Spring Boot (JPA/Hibernate) will auto-create the tables
-- below when you run the app with spring.jpa.hibernate.ddl-auto=update
-- These are shown here for reference / manual setup.
-- ============================================================

-- 3. Bookings table
CREATE TABLE IF NOT EXISTS bookings (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name        VARCHAR(255)  NOT NULL,
    email            VARCHAR(255)  NOT NULL,
    phone            VARCHAR(20)   NOT NULL,
    pickup_address   VARCHAR(500)  NOT NULL,
    delivery_address VARCHAR(500)  NOT NULL,
    moving_date      DATE          NOT NULL,
    service_type     VARCHAR(50)   NOT NULL,   -- COURIER | HOUSE_SHIFTING | RENTAL_TRANSPORT
    branch           VARCHAR(100),             -- Chennai | Coimbatore | Salem | Trichy | Bangalore
    additional_notes VARCHAR(1000),
    status           VARCHAR(30)   NOT NULL DEFAULT 'PENDING', -- PENDING | CONFIRMED | IN_PROGRESS | COMPLETED | CANCELLED
    created_at       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 4. Quote requests table
CREATE TABLE IF NOT EXISTS quote_requests (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    phone         VARCHAR(20)  NOT NULL,
    from_location VARCHAR(255) NOT NULL,
    to_location   VARCHAR(255) NOT NULL,
    service_type  VARCHAR(50),
    property_size VARCHAR(50),
    message       TEXT,
    status        VARCHAR(20)  NOT NULL DEFAULT 'NEW',  -- NEW | RESPONDED | CONVERTED | CLOSED
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 5. Contact messages table
CREATE TABLE IF NOT EXISTS contact_messages (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255)  NOT NULL,
    email      VARCHAR(255)  NOT NULL,
    subject    VARCHAR(255)  NOT NULL,
    message    VARCHAR(2000) NOT NULL,
    type       VARCHAR(50),          -- BUSINESS_ENQUIRY | CAREERS | GENERAL
    `read`     TINYINT(1)    NOT NULL DEFAULT 0,
    created_at DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ============================================================
-- Useful queries for admin / testing
-- ============================================================

-- View all bookings
-- SELECT * FROM bookings ORDER BY created_at DESC;

-- View pending bookings
-- SELECT * FROM bookings WHERE status = 'PENDING';

-- View new quote requests
-- SELECT * FROM quote_requests WHERE status = 'NEW';

-- View unread messages
-- SELECT * FROM contact_messages WHERE `read` = 0;