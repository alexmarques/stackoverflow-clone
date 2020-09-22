# stackoverflow-clone

Para iniciar o projeto deve ser iniciando um banco de dados com o comando abaixo:

```dockerfile 
docker run --name stackclone-db -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=stackoverflow-clone-db -p 3306:3306 -d mysql
```

Após iniciado o container mysql, na raiz do projeto java, executar o comando:

```editorconfig
mvn spring-boot:run
```

# Problema Conhecidos

Existe um problema entre o swagger e api pageable do spring, o swagger não exibe os campos de paginação quando vamos realizar a consulta, ao tentar executar uma exceção é lançada.