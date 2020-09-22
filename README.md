# stackoverflow-clone

Para iniciar o projeto deve ser iniciando um banco de dados com o comando abaixo:

```dockerfile 
docker run --name stackclone-db -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=stackoverflow-clone-db -p 3306:3306 -d mysql
```

Após iniciado o container mysql, na raiz do projeto java, executar o comando:

```editorconfig
mvn spring-boot:run
```