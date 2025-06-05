# Projeto TESTE: ambev_order

Este projeto representa o **consumidor de pedidos** de uma arquitetura baseada em microsserviços. Ele consome mensagens via RabbitMQ e envia os dados processados para outro serviço via FeignClient.

---

## 🚀 Tecnologias utilizadas

- **Java 17**
- **Spring Boot 3**
- **Mensageria com RabbitMQ**
- **FeignClient**
- **Flyway para versionamento do Banco de dados**
- **Banco de dados: PostgreSQL, H2**
- **Maven**
- **Lombok**
- **JUnit 5 para testes**
- **Docker & Docker Compose**

---

## 📦 Como rodar o projeto

### 1. Clonar o repositório

```bash 
  git clone https://github.com/RomarioCabo/ambev_order.git
```

### 2. Subir os containers com Docker

Execute o comando abaixo na raiz do projeto para subir os serviços necessários:
```bash 
  docker compose up -d
```
### 3. Executar o projeto
Com os containers rodando, execute o projeto ambev_order na sua IDE favorita (como IntelliJ, Eclipse ou VS Code).

## 📡 Serviços externos necessários
O projeto depende de dois outros repositórios para funcionar corretamente. Você precisa cloná-los e executá-los também.

### 🔁 Serviço produtor de mensagens
Repositório: ambev_produto_externo_a
```bash 
  git clone https://github.com/RomarioCabo/ambev_produto_externo_a.git
```
- **Com os containers do ambev_order já rodando, execute este projeto na sua IDE.**

### Swagger UI:
``` 
http://localhost:8090/swagger-ui/index.html 
```
Há apenas um endpoint POST:

- **Você pode preencher o body manualmente**
- **Ou deixar o body vazio — o serviço irá gerar os dados automaticamente**

### 📬 Serviço receptor da ordem processada
Repositório: ambev_produto_externo_b
```bash 
  git clone https://github.com/RomarioCabo/ambev_produto_externo_b.git
```
- **Execute o projeto normalmente na sua IDE.**
- **Esse serviço recebe os dados processados através do FeignClient usado no ambev_order.**

### 🧩 Visão geral da arquitetura

[ ambev_produto_externo_a ] --(RabbitMQ)--> [ ambev_order ] --(FeignClient)--> [ ambev_produto_externo_b ]

### 👨‍💻 Autor
Desenvolvido por [Romário Cabo](https://github.com/RomarioCabo)

