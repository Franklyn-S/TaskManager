CREATE DATABASE task_manager;
USE task_manager;

CREATE table tar_tarefas
(
    tar_id INT PRIMARY KEY AUTO_INCREMENT,
    tar_titulo VARCHAR(50) NOT NULL,
    tar_descricao VARCHAR(100) DEFAULT NULL,
    tar_data_expiracao DATE NOT NULL,
    tar_concluida BIT DEFAULT FALSE
);


CREATE TABLE usr_usuarios
(
	usr_id INT PRIMARY KEY AUTO_INCREMENT,
    usr_email VARCHAR(100) NOT NULL,
    usr_senha VARCHAR(100) NOT NULL
);

INSERT INTO tar_tarefas(tar_titulo, tar_descricao, tar_data_expiracao)
VALUES('Aprender java spring', 'Ver os vídeos da semana do java spring da treina web', NOW());

SELECT * FROM tar_tarefas;
