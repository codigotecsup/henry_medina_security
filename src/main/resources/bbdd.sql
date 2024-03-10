-- Tabla Usuario
CREATE TABLE usuario (
                         id_usuario SERIAL PRIMARY KEY,
                         username VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         telefono VARCHAR(15) NOT NULL,
                         enabled BOOLEAN NOT NULL DEFAULT TRUE,
                         accountnonexpire BOOLEAN NOT NULL DEFAULT FALSE,
                         accountnonlocked BOOLEAN NOT NULL DEFAULT FALSE,
                         credentialsnonexpired BOOLEAN NOT NULL DEFAULT FALSE,
);

-- Tabla Rol
CREATE TABLE rol (
                     id_rol SERIAL PRIMARY KEY,
                     nombre_rol VARCHAR(255) NOT NULL UNIQUE
);

-- Tabla Usuario Rol
CREATE TABLE usuario_rol (
                             id_usuario_rol SERIAL PRIMARY KEY,
                             id_usuario INTEGER NOT NULL,
                             id_rol INTEGER NOT NULL,
                             FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
                             FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);