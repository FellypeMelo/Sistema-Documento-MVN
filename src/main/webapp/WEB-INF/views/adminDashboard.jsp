<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="max-w-7xl mx-auto">
    <div class="flex justify-between items-center mb-6">
        <h1 class="text-3xl font-bold text-gray-900">Painel Administrativo</h1>
        <div class="flex space-x-4">
            <select id="filtroStatus" onchange="filtrarSolicitacoes()"
                    class="rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring focus:ring-primary focus:ring-opacity-50">
                <option value="">Todos os Status</option>
                <option value="PENDENTE">Pendentes</option>
                <option value="EM_ANDAMENTO">Em Andamento</option>
                <option value="CONCLUIDO">Concluídos</option>
                <option value="CANCELADO">Cancelados</option>
            </select>
            <select id="filtroPrioridade" onchange="filtrarSolicitacoes()"
                    class="rounded-md border-gray-300 shadow-sm focus:border-primary focus:ring focus:ring-primary focus:ring-opacity-50">
                <option value="">Todas as Prioridades</option>
                <option value="ALTA">Alta</option>
                <option value="MEDIA">Média</option>
                <option value="BAIXA">Baixa</option>
            </select>
        </div>
    </div>

    <div class="bg-white shadow overflow-hidden sm:rounded-lg">
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aluno</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Documento</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Data</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Prioridade</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Ações</th>
                </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
                <c:forEach var="solicitacao" items="${solicitacoes}">
                    <tr data-status="${solicitacao.status}" data-prioridade="${solicitacao.prioridade}">
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">#${solicitacao.id}</td>
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${solicitacao.usuario.nome}</td>
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${solicitacao.tipoDocumento.nome}</td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                                ${solicitacao.status eq 'PENDENTE' ? 'bg-yellow-100 text-yellow-800' :
                                  solicitacao.status eq 'EM_ANDAMENTO' ? 'bg-blue-100 text-blue-800' :
                                  solicitacao.status eq 'CONCLUIDO' ? 'bg-green-100 text-green-800' :
                                  'bg-red-100 text-red-800'}">
                                ${solicitacao.status}
                            </span>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                            <fmt:formatDate value="${solicitacao.dataSolicitacao}" pattern="dd/MM/yyyy HH:mm"/>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                                ${solicitacao.prioridade eq 'ALTA' ? 'bg-red-100 text-red-800' :
                                  solicitacao.prioridade eq 'MEDIA' ? 'bg-yellow-100 text-yellow-800' :
                                  'bg-green-100 text-green-800'}">
                                ${solicitacao.prioridade}
                            </span>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                                        <button onclick='abrirModalStatus("${solicitacao.id}")'
                                    class="text-primary hover:text-primary-dark mr-3">
                                <i class="fas fa-edit"></i> Atualizar Status
                            </button>
                            <a href="<c:url value='/solicitacao/${solicitacao.id}'/>"
                               class="text-primary hover:text-primary-dark">
                                <i class="fas fa-eye"></i> Detalhes
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Modal Atualizar Status -->
<div id="atualizarStatusModal" class="hidden fixed z-10 inset-0 overflow-y-auto">
    <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
        <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>
        <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
            <form id="formAtualizarStatus">
                <input type="hidden" id="solicitacaoId" name="solicitacaoId">
                <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                    <h3 class="text-lg leading-6 font-medium text-gray-900 mb-4">Atualizar Status da Solicitação</h3>
                    
                    <div class="mb-4">
                        <label for="novoStatus" class="block text-sm font-medium text-gray-700">Novo Status</label>
                        <select id="novoStatus" name="status" required
                                class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary sm:text-sm">
                            <option value="PENDENTE">Pendente</option>
                            <option value="EM_ANDAMENTO">Em Andamento</option>
                            <option value="CONCLUIDO">Concluído</option>
                            <option value="CANCELADO">Cancelado</option>
                        </select>
                    </div>

                    <div class="mb-4">
                        <label for="observacao" class="block text-sm font-medium text-gray-700">Observação</label>
                        <textarea id="observacao" name="observacao" rows="3"
                                class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary sm:text-sm"
                                placeholder="Adicione uma observação sobre a mudança de status..."></textarea>
                    </div>
                </div>

                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                    <button type="submit"
                            class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-primary text-base font-medium text-white hover:bg-primary-dark focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary sm:ml-3 sm:w-auto sm:text-sm">
                        Atualizar
                    </button>
                    <button type="button"
                            onclick="document.getElementById('atualizarStatusModal').classList.add('hidden')"
                            class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm">
                        Cancelar
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
function filtrarSolicitacoes() {
    const statusFiltro = document.getElementById('filtroStatus').value;
    const prioridadeFiltro = document.getElementById('filtroPrioridade').value;
    const rows = document.querySelectorAll('tbody tr');

    rows.forEach(row => {
        const status = row.getAttribute('data-status');
        const prioridade = row.getAttribute('data-prioridade');
        const mostrarStatus = !statusFiltro || status === statusFiltro;
        const mostrarPrioridade = !prioridadeFiltro || prioridade === prioridadeFiltro;
        
        row.style.display = mostrarStatus && mostrarPrioridade ? '' : 'none';
    });
}

function abrirModalStatus(solicitacaoId) {
    document.getElementById('solicitacaoId').value = solicitacaoId;
    document.getElementById('atualizarStatusModal').classList.remove('hidden');
}

document.getElementById('formAtualizarStatus').addEventListener('submit', function(e) {
    e.preventDefault();
    
    var solicitacaoId = document.getElementById('solicitacaoId').value;
    var usuarioId = '${sessionScope.usuario.id}';
    var formData = {
        status: document.getElementById('novoStatus').value,
        observacao: document.getElementById('observacao').value,
        idAdmin: usuarioId
    };

    fetch('<c:url value="/api/solicitacoes/"/>' + solicitacaoId + '/status', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(function(response) {
        if (response.ok) {
            window.location.reload();
        } else {
            throw new Error('Erro ao atualizar status');
        }
    })
    .catch(function(error) {
        alert('Erro: ' + error.message);
    });
});
</script>