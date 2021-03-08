INSERT INTO tusers ( email, password, creation_date)
SELECT 'testando@email.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG',
current_timestamp() WHERE NOT EXISTS (SELECT 1 FROM tusers us WHERE us.email = "testando@email.com");