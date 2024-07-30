# Microsserviços para Gestão de Clientes e Cartões de Crédito

Este projeto consiste em uma arquitetura de microsserviços utilizando Spring Boot, Eureka Server, API Gateway, RabbitMQ, banco de dados H2 e OpenFeign. A estrutura é composta por três microsserviços principais: `ms-clientes`, `ms-cartões` e `ms-avaliar-credito`.

## Estrutura do Projeto

1. **Eureka Server**
    - Serviço de registro para os microsserviços.
    - Fornece descoberta de serviços para balanceamento de carga e failover.

2. **API Gateway**
    - Ponto de entrada único para todos os microsserviços.
    - Gerencia e roteia as solicitações para os serviços apropriados.

3. **ms-clientes**
    - Realiza o cadastro de clientes.
    - Utiliza um banco de dados H2 para armazenamento de dados.

4. **ms-cartões**
    - Realiza o cadastro de cartões.
    - Retorna os cartões disponíveis com base na renda do cliente.

5. **ms-avaliar-credito**
    - Verifica os cartões disponíveis com base no CPF do cliente.
    - Realiza solicitações de cartões.

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para construção dos microsserviços.
- **Eureka Server**: Serviço de registro e descoberta de serviços.
- **API Gateway**: Ponto de entrada único para gerenciamento e roteamento de solicitações.
- **RabbitMQ**: Mensageria para comunicação assíncrona entre microsserviços.
- **H2 Database**: Banco de dados em memória para armazenamento dos dados.
- **OpenFeign**: Cliente HTTP para comunicação entre os microsserviços.

## Como Executar o Projeto

### Pré-requisitos

- Java 11 ou superior
- Maven
- RabbitMQ (instalado e em execução)

### Passos para Execução

1. **Clone o repositório**
    ```bash
    git clone https://github.com/seu-usuario/seu-repositorio.git
    cd seu-repositorio
    ```

2. **Inicie o Eureka Server**
    ```bash
    cd eureka-server
    mvn spring-boot:run
    ```

3. **Inicie o API Gateway**
    ```bash
    cd api-gateway
    mvn spring-boot:run
    ```

4. **Inicie o microsserviço ms-clientes**
    ```bash
    cd ms-clientes
    mvn spring-boot:run
    ```

5. **Inicie o microsserviço ms-cartões**
    ```bash
    cd ms-cartoes
    mvn spring-boot:run
    ```

6. **Inicie o microsserviço ms-avaliar-credito**
    ```bash
    cd ms-avaliar-credito
    mvn spring-boot:run
    ```

## Contribuição

Se você deseja contribuir com este projeto, sinta-se à vontade para fazer um fork do repositório e enviar pull requests. Todas as contribuições são bem-vindas!

