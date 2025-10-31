# Sistema de Comanda de Documentos

Sistema web para gerenciamento de solicitações de documentos acadêmicos.

## Requisitos do Sistema

- Java 11 ou superior
- Maven 3.6 ou superior
- MySQL 8.0
- Servidor de Aplicação Jakarta EE 9+ (ex: TomEE 9.0, GlassFish 6.0)

## Configuração do Ambiente

1. Clone o repositório:
```bash
git clone https://github.com/FellypeMelo/Sistema-Documento-MVN.git
cd Sistema-Documento-MVN
```

2. Configure o banco de dados:
- Crie um banco de dados MySQL
- Execute o script SQL localizado em `sql/schema.sql`
- Atualize as credenciais de conexão em `src/main/resources/database.properties`

3. Compile o projeto:
```bash
mvn clean install
```

4. Deploy a aplicação:
- Copie o arquivo WAR gerado (`target/sistema-documento.war`) para o diretório de deploy do seu servidor
- Ou use o plugin do Maven para deploy direto:
```bash
mvn tomee:run
```

5. Acesse a aplicação:
```
http://localhost:8080/sistema-documento
```

## Estrutura do Projeto

```
sistema-documento/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/instituicao/sistemacomanda/
│   │   │       ├── controller/    # Servlets e API REST
│   │   │       ├── dao/          # Camada de acesso a dados
│   │   │       ├── facade/       # Fachada do sistema
│   │   │       ├── model/        # Classes de domínio
│   │   │       └── strategy/     # Padrão Strategy para cálculo de prioridade
│   │   ├── resources/
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       ├── css/
│   │       ├── js/
│   │       └── *.jsp
│   └── test/
└── pom.xml
```

## Padrões de Projeto Utilizados

- **Facade**: `SistemaDocumentosFacade` centraliza e simplifica as operações do sistema
- **Strategy**: Usado para cálculo dinâmico de prioridade das solicitações
- **DAO**: Abstrai o acesso ao banco de dados

## Desenvolvimento

Para adicionar novas funcionalidades:

1. Crie uma branch para sua feature
2. Desenvolva e teste sua implementação
3. Crie um Pull Request para a branch main

## Testes

Execute os testes unitários:
```bash
mvn test
```

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.