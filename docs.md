Sistema de Comanda de Documentos
Autor: Nome do Autor/Equipe
Instituição: Instituição de Ensino
Curso: Nome do Curso
Data: 16 de outubro de 2025

Sumário
Introdução
Requisitos do Sistema
Modelagem e Arquitetura do Sistema
Implementação de Componentes Chave
Features e Plano de Apresentação
1. Introdução
Este documento detalha o projeto de desenvolvimento do Sistema de Comanda de
Documentos, uma plataforma web projetada para otimizar e gerenciar o processo de
solicitação de documentos acadêmicos em uma instituição de ensino. O sistema visa
substituir processos manuais, oferecendo uma interface digital para que alunos possam
solicitar documentos e para que a administração possa processar essas solicitações de forma
eficiente e organizada.
A principal inovação do projeto é a implementação do padrão de projeto Strategy para o
cálculo dinâmico da prioridade de cada solicitação. Essa abordagem permite flexibilidade na
definição dos algoritmos de priorização, garantindo que as solicitações mais urgentes sejam
atendidas com a devida celeridade.

1.1. Justificativa
O padrão Strategy será aplicado no cálculo de prioridades das solicitações, permitindo
diferentes algoritmos de cálculo baseados na urgência e tipo de documento. Isso garante que
a lógica de negócio para priorização possa evoluir de acordo com as necessidades da
instituição, sem a necessidade de refatorações complexas.

2. Requisitos do Sistema
2.1. Requisitos Funcionais
ID Módulo Descrição
RF01 Aluno Realizar login no sistema.
RF02 Aluno Visualizar documentos
disponíveis.
RF03 Aluno Solicitar documento
selecionando tipo e
urgência.
RF04 Aluno Acompanhar status das
solicitações.
RF05 Aluno Consultar histórico de
documentos.
RF06 Administração Processar solicitações por
ordem de prioridade.
RF07 Administração Atualizar status dos
documentos.
RF08 Administração Gerar relatório de
produtividade.
RF09 Administração Gerenciar tipos de
documentos.
RF10 Sistema Calcular prioridade usando
padrão Strategy.
RF11 Sistema Notificar sobre mudanças
de status.
RF12 Sistema Validar limites de
solicitações por aluno.
2.2. Requisitos Não Funcionais
Categoria Descrição
Desempenho O sistema deve fornecer notificações
sobre mudança de status em tempo real
(via WebSocket) para garantir a agilidade
na comunicação.
Usabilidade A interface deve ser intuitiva, com
dashboards visuais que utilizem cores para
indicar os diferentes status das
solicitações.
Segurança O acesso ao sistema deve ser restrito a
usuários autenticados, com separação de
perfis entre Aluno e Administração.
Portabilidade O sistema será desenvolvido como uma
aplicação web, com uma API REST
planejada para futuras integrações com
clientes mobile.
2.3. Regras de Negócio
ID Descrição
RN01 Um aluno não pode ter mais de um número
X de solicitações ativas simultaneamente.
RN02 A prioridade é calculada dinamicamente
com base na urgência e no tempo
decorrido.
RN03 A administração deve processar as
solicitações estritamente na ordem
decrescente de prioridade.
RN04 O status de uma solicitação deve seguir o
fluxo: SOLICITADO → VISUALIZADO →
PRODUCAO → CONCLUIDO.
3. Modelagem e Arquitetura do Sistema
3.1. Diagrama de Classes com Padrão Strategy
3.2. Estrutura do Projeto
● Páginas JSP (4)
login.jsp - Tela de autenticação
alunoDashboard.jsp - Dashboard do aluno
adminDashboard.jsp - Gestão administrativa
detalhesSolicitacao.jsp - Detalhes e histórico
● Servlets (3)
LoginServlet - Controle de autenticação
SolicitacaoServlet - CRUD de solicitações
AdminServlet - Gestão administrativa
● Web Service (1)
○ PrioridadeWS - Expõe o cálculo de prioridade.
● API (1)
○ SolicitacaoAPI - Endpoints REST para futuro uso mobile.
3.3. Diagrama de Casos de Uso
3.4 Casos de Uso Detalhados
UC01: Realizar login
Ator Principal: Aluno
Pré-condições: Usuário possui cadastro no sistema
Fluxo Principal:

Usuário acessa a tela de login
Sistema exibe formulário de email e senha
Usuário preenche credenciais e submete
Sistema valida credenciais no banco de dados
Sistema cria sessão de usuário
Sistema redireciona para dashboard do aluno
Fluxos Alternativos:
● Credenciais inválidas: Sistema exibe mensagem de erro
● Usuário inativo: Sistema bloqueia acesso
UC02: Visualizar documentos disponíveis
Ator Principal: Aluno
Pré-condições: Usuário está autenticado como aluno
Fluxo Principal:
Aluno acessa dashboard
Sistema consulta documentos ativos no banco
Sistema exibe lista de documentos disponíveis
Aluno visualiza descrição e prazos de cada documento
Fluxos Alternativos:
● Nenhum documento disponível: Sistema exibe mensagem informativa
UC03: Solicitar documento
Ator Principal: Aluno
Pré-condições: Aluno está autenticado e tem menos de 3 solicitações ativas
Fluxo Principal:
Aluno seleciona tipo de documento
Aluno seleciona nível de urgência (baixa, média, alta)
Aluno confirma solicitação
Sistema valida limite de solicitações ativas
Sistema cria nova solicitação no banco
Sistema calcula prioridade automaticamente
Sistema registra status inicial "SOLICITADO"
Sistema exibe confirmação para o aluno
Fluxos Alternativos:
● Limite excedido: Sistema impede nova solicitação
UC04: Acompanhar status
Ator Principal: Aluno
Pré-condições: Aluno tem pelo menos uma solicitação
Fluxo Principal:

Aluno acessa seção "Minhas Solicitações"
Sistema consulta solicitações do aluno no banco
Sistema exibe lista com status atual de cada uma
Aluno clica em solicitação específica
Sistema exibe histórico completo de status
Aluno visualiza tempo decorrido e próximos passos
Fluxos Alternativos: Nenhum
UC05: Consultar histórico
Ator Principal: Aluno
Pré-condições: Aluno tem solicitações anteriores
Fluxo Principal:
Aluno acessa seção "Histórico"
Sistema consulta todas as solicitações do aluno
Sistema aplica filtro para mostrar apenas finalizadas
Sistema exibe dados completos (documento, data, status final)
Aluno pode filtrar por período ou tipo de documento
Fluxos Alternativos:
● Nenhum histórico: Sistema exibe mensagem "Nenhum documento solicitado"
UC06: Realizar login administrativo
Ator Principal: Administração
Pré-condições: Usuário possui cadastro como admin
Fluxo Principal:
Admin acessa tela de login
Sistema exibe formulário de email e senha
Admin preenche credenciais e submete
Sistema valida tipo de usuário como ADMIN
Sistema cria sessão administrativa
Sistema redireciona para dashboard administrativo
Fluxos Alternativos:
● Credenciais de aluno: Sistema redireciona para dashboard do aluno
UC07: Visualizar solicitações pendentes
Ator Principal: Administração
Pré-condições: Admin está autenticado
Fluxo Principal:
Admin acessa dashboard
Sistema consulta solicitações com status não finalizados
Sistema ordena por prioridade (maior primeiro)
Sistema exibe lista com dados principais
Admin visualiza contadores por urgência
Fluxos Alternativos: Nenhum
UC08: Filtrar solicitações
Ator Principal: Administração
Pré-condições: Admin está autenticado e visualizando solicitações
Fluxo Principal:

Admin aplica filtro por urgência (alta, média, baixa)
Admin aplica filtro por status específico
Admin altera ordenação (data, prioridade, urgência)
Sistema atualiza lista conforme filtros aplicados
Admin visualiza resultados filtrados
Fluxos Alternativos:
● Nenhum resultado: Sistema exibe "Nenhuma solicitação encontrada"
UC09: Atualizar status
Ator Principal: Administração
Pré-condições: Admin está autenticado e visualizando uma solicitação
Fluxo Principal:
Admin seleciona solicitação específica
Sistema exibe detalhes completos e histórico
Admin seleciona novo status no fluxo pré-definido
Admin pode adicionar observação opcional
Sistema atualiza status no banco de dados
Sistema registra no histórico com data/hora e responsável
Sistema dispara notificação para o aluno
Fluxos Alternativos:
● Status inválido: Sistema impede transição incorreta no fluxo
UC10: Gerar relatório
Ator Principal: Administração
Pré-condições: Admin está autenticado
Fluxo Principal:
Admin acessa seção "Relatórios"
Sistema exibe formulário com opções de período
Admin seleciona data inicial e final
Sistema consulta métricas no banco
Sistema calcula: total de solicitações, tempo médio, taxa de conclusão
Sistema exibe relatório em formato de tabela/gráfico
Admin pode exportar para PDF
Fluxos Alternativos:
● Período sem dados: Sistema exibe "Nenhum dado no período"
UC11: Calcular prioridade
Ator Principal: Sistema
Pré-condições: Existe uma solicitação com urgência definida
Fluxo Principal:
Sistema identifica urgência da solicitação
Sistema consulta pesos na tabela config_prioridades
Sistema calcula horas de espera desde a solicitação
Sistema aplica fórmula: prioridade = peso_base + (horas_espera × multiplicador)
Sistema atualiza campo prioridade na solicitação
Fluxos Alternativos: Nenhum
UC12: Notificar mudanças
Ator Principal: Sistema
Pré-condições: Ocorreu mudança de status em uma solicitação
Fluxo Principal:
Sistema detecta alteração de status
Sistema identifica aluno relacionado à solicitação
Sistema registra notificação no banco
Sistema atualiza interface do aluno em tempo real
Sistema marca notificação como não lida
Fluxos Alternativos:
● Aluno offline: Notificação fica pendente até próximo acesso
3.5. Fluxo com Strategy
4. Implementação de Componentes Chave
4.1. Implementação do Padrão Strategy
Interface Strategy
public interface CalculadoraPrioridade {
int calcularPrioridade(Solicitacao solicitacao);
}
Implementações Concretas
public class CalculadoraUrgenciaAlta implements CalculadoraPrioridade {
public int calcularPrioridade(Solicitacao solicitacao) {
// Alta urgência: base + tempo de espera acelerado
long horasEspera = ChronoUnit.HOURS.between(
solicitacao.getDataSolicitacao(), LocalDateTime.now());
return 100 + (int)horasEspera * 10;
}
}
public class CalculadoraUrgenciaMedia implements CalculadoraPrioridade {
public int calcularPrioridade(Solicitacao solicitacao) {
// Média urgência: base média + tempo normal
long horasEspera = ChronoUnit.HOURS.between(
solicitacao.getDataSolicitacao(), LocalDateTime.now());
return 50 + (int)horasEspera * 5;
}
}

4.2. Banco de Dados MySQL
-- Tabela de Usuários
CREATE TABLE usuarios (
id INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
senha VARCHAR(255) NOT NULL,
tipo ENUM('ALUNO', 'ADMIN') NOT NULL,
ativo BOOLEAN DEFAULT TRUE,
data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
matricula VARCHAR(20),
curso VARCHAR(50)
);
-- Tabela de Tipos de Documento
CREATE TABLE documentos_tipo (
id INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100) NOT NULL,
descricao TEXT,
prazo_base INT NOT NULL, -- em horas
ativo BOOLEAN DEFAULT TRUE
);

-- Tabela de Solicitações
CREATE TABLE solicitacoes (
id INT PRIMARY KEY AUTO_INCREMENT,
id_aluno INT NOT NULL,
id_documento_tipo INT NOT NULL,
data_solicitacao DATETIME DEFAULT CURRENT_TIMESTAMP,
urgencia ENUM('BAIXA', 'MEDIA', 'ALTA') NOT NULL,
status ENUM('SOLICITADO', 'VISUALIZADO', 'EM_PRODUCAO', 'CONCLUIDO') DEFAULT
'SOLICITADO',
prioridade INT,
observacoes TEXT,
data_conclusao DATETIME,
FOREIGN KEY (id_aluno) REFERENCES usuarios(id),
FOREIGN KEY (id_documento_tipo) REFERENCES documentos_tipo(id)
);
-- Tabela de Histórico de Status
CREATE TABLE status_historico (
id INT PRIMARY KEY AUTO_INCREMENT,
id_solicitacao INT NOT NULL,
status VARCHAR(50) NOT NULL,
data_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
responsavel VARCHAR(100),
observacao TEXT,

FOREIGN KEY (id_solicitacao) REFERENCES solicitacoes(id) ON DELETE CASCADE
);
-- Tabela de Configuração de Prioridades (para o padrão Strategy)
CREATE TABLE config_prioridades (
id INT PRIMARY KEY AUTO_INCREMENT,
tipo_urgencia ENUM('BAIXA', 'MEDIA', 'ALTA') NOT NULL,
peso_base INT NOT NULL,
multiplicador_tempo INT NOT NULL
);
-- Inserir dados iniciais
INSERT INTO usuarios (nome, email, senha, tipo, matricula, curso) VALUES
('João Silva', 'joao.silva@faeterj-rio.edu.br', 'senha123', 'ALUNO', '2023001001', 'Engenharia de
Software'),
('Maria Santos', 'maria.santos@faeterj-rio.edu.br', 'senha123', 'ALUNO', '2023001002', 'Ciência
da Computação'),
('Admin User', 'admin@faeterj-rio.edu.br', 'admin123', 'ADMIN', NULL, NULL);
INSERT INTO documentos_tipo (nome, descricao, prazo_base) VALUES
('Histórico Escolar', 'Documento com todas as disciplinas cursadas e notas', 72),
('Atestado de Matrícula', 'Comprovação de vínculo com a instituição', 24),
('Programa de Disciplina', 'Ementa e programa de uma disciplina específica', 48);

INSERT INTO config_prioridades (tipo_urgencia, peso_base, multiplicador_tempo) VALUES
('ALTA', 100, 10),
('MEDIA', 50, 5),
('BAIXA', 25, 2);

5. Features e Plano de Apresentação
5.1. Features Inovadoras
Sistema de Prioridade Dinâmica com padrão Strategy.
Dashboard Visual com status colorido.
Notificações em Tempo Real via WebSocket simples.
Relatório de Performance da administração.
5.2. Para Apresentação de 5 Minutos
● Demo: Aluno solicita documento → Admin processa com prioridade.
● Mostrar cálculo dinâmico de prioridade.
● Exibir mudança de status em tempo real.
5.3. Para Vídeo de 20 Minutos
● Explicação detalhada do padrão Strategy.
● Código dos Servlets e JSPs.
● Demonstração completa do fluxo.
● Explicação do banco de dados.