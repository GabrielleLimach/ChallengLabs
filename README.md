# Welcome!

# This is Challeng Labs
Desafio de uma plataforma de agendamento de comunicação.

## 🚀 Começando

### 🔧 Instalação
Para instalar o projeto, basta clonar o repositório:
```
git@github.com:GabrielleLimach/ChallengLabs.git
```

## 🛠️ Executando o projeto

#### Após clonar o projeto, executar os seguintes comandos para criar as imagens e os containers do docker
```
mvn package
docker-compose up
```

## Testes

### Testes Unitários

```
Utilizado recursos do JUnit e Mockito para verificação dos endpoints e das respostas esperadas.
```

## 📄 Documentação

#### A documentação de api foi criada com o Swagger que pode ser acessada após a execução do projeto no link:

```
http://localhost:9999/swagger-ui.html
```

#### Para ter acesso a todos endpoints da API do projeto basta acessar o link:

```
https://galactic-shuttle-519548.postman.co/workspace/Integracao~81a3f365-3462-4f4d-8e34-1cc2529d1a15/documentation/2260423-812af351-0044-488d-8920-9d63ee6c5405
```

## 🛠️ Tecnologias Utilizadas

* Postgres - Bando de dados
* Flyway - utilizado para automatizar execução de scripts e gerar as tabelas do banco de dados.
* ModelMapper - Facilitar a conversão de objetos, para retorno da API.
* Docker - Criar conteiner com ambiente controlado para execução da aplicação
* Swagger - Geração de documentação dos endpoints com ui
* Maven - Gerenciador de dependências
* Jpa - Facilita o ORM ao possibilitar a escrita de querys, controlando as requisições ao banco de dados


## ✒️ Autor
* **Gabrielle Limach** - *Desenvolvimento do projeto* - [Gabrielle Limach](https://github.com/GabrielleLimach)
* 
## Licença
O código-fonte do site está licenciado sob a licença MIT, que você pode encontrar no arquivo LICENSE .
