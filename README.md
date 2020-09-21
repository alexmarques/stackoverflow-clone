# stackoverflow-clone

Para iniciar o projeto deve ser iniciando um banco de dados com o comando abaixo:

```dockerfile 
docker run --name stackoverflow-clone-db -e MYSQL_ROOT_PASSWORD=admin -p 3306:3306 -d mysql
```

Após iniciado o container mysql na raiz do projeto, executar o comando:

```editorconfig
mvn spring-boot:run
```