<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="max-w-7xl mx-auto">
    <div class="bg-white shadow overflow-hidden sm:rounded-lg">
        <div class="px-4 py-5 sm:px-6">
            <div class="flex justify-between items-center">
                <div>
                    <h3 class="text-lg leading-6 font-medium text-gray-900">
                        Detalhes da Solicitação #${solicitacao.id}
                    </h3>
                    <p class="mt-1 max-w-2xl text-sm text-gray-500">
                        Informações detalhadas sobre a solicitação de documento
                    </p>
                </div>
                <c:if test="${sessionScope.usuario.admin}">
                    <button onclick='abrirModalStatus("${solicitacao.id}")'
                            class="inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-primary hover:bg-primary-dark focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary">
                        <i class="fas fa-edit mr-2"></i>
                        Atualizar Status
                    </button>
                </c:if>
            </div>
        </div>
        <div class="border-t border-gray-200">
            <dl>
                <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Tipo de Documento</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">${solicitacao.tipoDocumento.nome}</dd>
                </div>
                <div class="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Status Atual</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        <span class="px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full
                            ${solicitacao.status eq 'PENDENTE' ? 'bg-yellow-100 text-yellow-800' :
                              solicitacao.status eq 'EM_ANDAMENTO' ? 'bg-blue-100 text-blue-800' :
                              solicitacao.status eq 'CONCLUIDO' ? 'bg-green-100 text-green-800' :
                              'bg-red-100 text-red-800'}">
                            ${solicitacao.status}
                        </span>
                    </dd>
                </div>
                <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Solicitante</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">${solicitacao.usuario.nome}</dd>
                </div>
                <div class="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Data da Solicitação</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        <fmt:formatDate value="${solicitacao.dataSolicitacao}" pattern="dd/MM/yyyy HH:mm"/>
                    </dd>
                </div>
                <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Prioridade</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
                        <span class="px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full
                            ${solicitacao.prioridade eq 'ALTA' ? 'bg-red-100 text-red-800' :
                              solicitacao.prioridade eq 'MEDIA' ? 'bg-yellow-100 text-yellow-800' :
                              'bg-green-100 text-green-800'}">
                            ${solicitacao.prioridade}
                        </span>
                    </dd>
                </div>
                <div class="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                    <dt class="text-sm font-medium text-gray-500">Observações</dt>
                    <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">${solicitacao.observacoes}</dd>
                </div>
            </dl>
        </div>
    </div>

    <!-- Histórico de Status -->
    <div class="mt-8">
        <h4 class="text-lg font-medium text-gray-900 mb-4">Histórico de Status</h4>
        <div class="flow-root">
            <ul role="list" class="-mb-8">
                <c:forEach var="historico" items="${solicitacao.historicos}" varStatus="loop">
                    <li>
                        <div class="relative pb-8">
                            <c:if test="${!loop.last}">
                                <span class="absolute top-4 left-4 -ml-px h-full w-0.5 bg-gray-200" aria-hidden="true"></span>
                            </c:if>
                            <div class="relative flex space-x-3">
                                <div>
                                    <span class="h-8 w-8 rounded-full flex items-center justify-center ring-8 ring-white
                                        ${historico.status eq 'PENDENTE' ? 'bg-yellow-500' :
                                          historico.status eq 'EM_ANDAMENTO' ? 'bg-blue-500' :
                                          historico.status eq 'CONCLUIDO' ? 'bg-green-500' :
                                          'bg-red-500'}">
                                        <c:choose>
                                            <c:when test="${historico.status eq 'PENDENTE'}">
                                                <i class="fas fa-clock text-white"></i>
                                            </c:when>
                                            <c:when test="${historico.status eq 'EM_ANDAMENTO'}">
                                                <i class="fas fa-spinner text-white"></i>
                                            </c:when>
                                            <c:when test="${historico.status eq 'CONCLUIDO'}">
                                                <i class="fas fa-check text-white"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fas fa-times text-white"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                                <div class="min-w-0 flex-1 pt-1.5 flex justify-between space-x-4">
                                    <div>
                                        <p class="text-sm text-gray-500">
                                            Status alterado para <span class="font-medium text-gray-900">${historico.status}</span>
                                            <c:if test="${not empty historico.administrador}">
                                                por <span class="font-medium text-gray-900">${historico.administrador.nome}</span>
                                            </c:if>
                                        </p>
                                        <p class="text-sm text-gray-500">${historico.observacao}</p>
                                    </div>
                                    <div class="text-right text-sm whitespace-nowrap text-gray-500">
                                        <fmt:formatDate value="${historico.dataMudanca}" pattern="dd/MM/yyyy HH:mm"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<c:if test="${sessionScope.usuario.admin}">
    <!-- Modal Atualizar Status -->
    <div id="atualizarStatusModal" class="hidden fixed z-10 inset-0 overflow-y-auto">
        <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>
            <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                <form id="formAtualizarStatus">
                    <input type="hidden" id="solicitacaoId" name="solicitacaoId" value="${solicitacao.id}">
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
</c:if>