# Sistema de Gerenciamento de Solicitações de Documentos - Documentação Técnica

## Visão Geral da Documentação
Esta documentação técnica está organizada em múltiplos arquivos para facilitar a manutenção e consulta:

1. [README.md](../README.md)
   - Visão geral do projeto
   - Instruções de instalação e configuração
   - Tecnologias utilizadas
   - Estrutura do projeto

2. [API.md](API.md)
   - Documentação completa da API REST
   - Endpoints disponíveis
   - Formatos de requisição e resposta
   - Exemplos de uso

3. [DATABASE.md](DATABASE.md)
   - Esquema completo do banco de dados
   - Relacionamentos entre tabelas
   - Índices e constraints
   - Queries de exemplo

## Documentação do Código

### Padrões de Projeto Utilizados

1. **Strategy Pattern** (`br.com.instituicao.sistemacomanda.strategy`)
   - Interface `CalculadoraPrioridade`
   - Implementações: Alta, Média e Baixa prioridade
   - Factory para criação das estratégias

2. **Facade Pattern** (`br.com.instituicao.sistemacomanda.facade`)
   - `SistemaDocumentosFacade`
   - Encapsula lógica de negócios
   - Simplifica interação com o sistema

### Componentes Principais

1. **Modelos** (`br.com.instituicao.sistemacomanda.model`)
   - Entidades do domínio
   - Mapeamento objeto-relacional
   - Validações e regras de negócio

2. **Controladores** (`br.com.instituicao.sistemacomanda.controller`)
   - Servlets para processamento de requisições
   - Endpoints REST
   - Gerenciamento de sessão

3. **WebSocket** (`br.com.instituicao.sistemacomanda.websocket`)
   - Notificações em tempo real
   - Gerenciamento de conexões
   - Broadcasting de mensagens

4. **Filtros** (`br.com.instituicao.sistemacomanda.filter`)
   - Autenticação e autorização
   - Headers de segurança
   - Prevenção de SQL Injection

### JavaDoc

O código fonte está documentado usando JavaDoc, seguindo estas convenções:

1. **Classes**
   ```java
   /**
    * Descrição da classe
    *
    * @author Sistema de Documentos Team
    * @version 1.0
    * @since 2025-10-31
    */
   ```

2. **Métodos**
   ```java
   /**
    * Descrição do método
    *
    * @param parametro descrição do parâmetro
    * @return descrição do retorno
    * @throws Exception descrição da exceção
    */
   ```

3. **Campos**
   ```java
   /** Descrição do campo */
   private TipoVariavel campo;
   ```

### Convenções de Código

1. **Nomenclatura**
   - Classes: PascalCase
   - Métodos: camelCase
   - Variáveis: camelCase
   - Constantes: SNAKE_CASE_MAIÚSCULO

2. **Estrutura de Pacotes**
   ```
   br.com.instituicao.sistemacomanda/
   ├── api/
   ├── config/
   ├── controller/
   ├── dao/
   ├── facade/
   ├── filter/
   ├── model/
   ├── strategy/
   ├── util/
   └── websocket/
   ```

3. **Tratamento de Erros**
   - Uso de exceções personalizadas
   - Logs adequados
   - Mensagens amigáveis ao usuário

### Segurança

1. **Autenticação**
   - Filtro de autenticação baseado em sessão
   - Proteção contra CSRF
   - Validação de entrada

2. **Headers de Segurança**
   - Content Security Policy (CSP)
   - X-Frame-Options
   - X-XSS-Protection

3. **Prevenção de SQL Injection**
   - Uso de PreparedStatement
   - Validação de entrada
   - Escape de caracteres especiais

### WebSocket

1. **Endpoint**
   ```
   /websocket/notificacoes/{userId}
   ```

2. **Formato das Mensagens**
   ```json
   {
     "tipo": "STATUS_ATUALIZADO",
     "idSolicitacao": 123,
     "status": "EM_ANDAMENTO",
     "mensagem": "Documento em processamento",
     "timestamp": "2025-10-31T10:00:00"
   }
   ```

3. **Gerenciamento de Conexões**
   - Thread-safe usando ConcurrentHashMap
   - Limpeza automática de conexões inativas
   - Broadcast eficiente

### Testes

1. **Testes Unitários**
   - JUnit 5
   - Mockito para mocks
   - Cobertura de código

2. **Testes de Integração**
   - Teste de DAOs
   - Teste de Facade
   - Teste de WebSocket

3. **Testes End-to-End**
   - Selenium WebDriver
   - Teste de fluxos completos
   - Validação de UI

## Manutenção e Suporte

### Logs
- Localização: `/var/log/sistema-documentos/`
- Rotação: diária com retenção de 30 dias
- Níveis: ERROR, WARN, INFO, DEBUG

### Monitoramento
- Métricas de performance
- Uso de recursos
- Taxa de sucesso/erro

### Backup
- Banco de dados: backup diário
- Arquivos: backup semanal
- Retenção: 90 dias

## Contribuição
1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/nova-feature`)
3. Faça commit das alterações (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Crie um Pull Request