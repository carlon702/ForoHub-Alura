# Challenge Foro Hub Alura

## Foro Hub es un Web API creada en Java Spring Boot, parte de la formacion Java Backend Alura ONE.

### Base de datos

Este API se conecta a una base de datos MySQL que cuenta con las siguientes tablas:

**Tabla users** : Contiene los usuarios que crean tópicos y respuestas a esos tópicos.

**Tabla topics** : Contiene los topicos creados por los usuarios.

**Tabla responses** : Contiene las respuestas de cada topico creadas por los usuarios, a su vez pueden marcarse como solucion y cerrar topicos.

**Tabla courses** : Contiene los cursos que se imparten en el foro, funciona como categorias para los topicos.

**Tabla profiles** : Contiene los diferentes tipos de perfiles de los usuarios (ADMIN || USER).

###Diagrama de la base de datos:
![image](https://github.com/user-attachments/assets/5a568457-58ed-47ec-8dfa-8a40c1ac1b54)

La aplicacion se corre en el **puerto 8080** de **localhost** y cuenta con documentacion creada mediante swagger donde pueden verse todos los endpoints disponibles:

**(http://localhost:8080/swagger-ui.html)**

![image](https://github.com/user-attachments/assets/5ebd6c40-c6b2-4830-ac5c-4a935ede9a3b)

Los puertos estan protegidos y requieren de un token JWT que se obtiene mediante login.


###Tecnologias:
JDK 17
Maven
Spring Web
Spring Boot
Spring Data JPA
Hibernate
MySQL
Lombok
Flyway Migration
Spring Security
Auth0-JWT
Spring doc-Swagger
