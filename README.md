# Alto Vale News Backend

Este é o backend da aplicação Alto Vale News, desenvolvido com Spring Boot e Spring Data JPA.
Ele fornece endpoints REST para permitir comunicação do [frontend](https://github.com/AndreHLudwig/altoValeNewsPhp_front) com o banco de dados Postgresql do projeto.

## Configuração

Certifique-se de ter o Java JDK, Maven, Apache Tomcat e banco de dados configurado instalados no seu computador.
Defina as propriedades de seu banco Postgres (```spring.datasource```) em `altoValeNewsBackend/src/main/resources/application.properties`

### Passos para executar a aplicação:

1. **Clone o repositório:**

   ```bash
   git clone hhttps://github.com/gehrkev/altoValeNewsBackend.git
   cd altoValeNewsBackend
   ```

2. **Build do projeto:**

Para construir o projeto, abra um terminal no diretório raiz do projeto e execute o seguinte comando:

  ```bash
  mvn clean install
  ```

3. **Executar a Aplicação:**

Para gerar a aplicação, utilize o seguinte comando no terminal:

  ```bash
  mvn package
  ```

Faça o deploy do pacote ```altoValeNewsBackend-0.0.1-SNAPSHOT.war``` localizado no diretório `altoValeNewsBackend/target` para seu servidor Tomcat

3.1. ***Alternativamente, executar pelo Spring:***

Após constuir o projeto com ```mvn clean install```, no diretório raiz do projeto, execute o seguinte comando no terminal:
   
   ```bash
   mvn spring-boot:run
   ```

4. Acesso à API:

Após iniciar a aplicação, você pode acessar a API através do seguinte endereço:

```http://localhost:8080```

## Documentação da API

A documentação da API está disponível em [Swagger UI](http://localhost:8080/swagger-ui.html), onde você pode explorar e testar os endpoints da aplicação. A aplicação deve estar executando para acessar o Swagger UI.
