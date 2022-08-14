CREATE USER 'store'@'%' IDENTIFIED BY 'store';

GRANT ALL PRIVILEGES ON * . * TO 'store'@'%';

CREATE SCHEMA IF NOT EXISTS store_producer CHARACTER SET = 'utf8mb4' COLLATE = 'utf8mb4_unicode_ci';

CREATE SCHEMA IF NOT EXISTS store_processor CHARACTER SET = 'utf8mb4' COLLATE = 'utf8mb4_unicode_ci';

CREATE SCHEMA IF NOT EXISTS store_consumer CHARACTER SET = 'utf8mb4' COLLATE = 'utf8mb4_unicode_ci';
