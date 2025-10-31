<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="max-w-7xl mx-auto">
    <div class="flex justify-between items-center mb-6">
        <h1 class="text-3xl font-bold text-gray-900">Minhas Solicitações</h1>
        <button onclick="document.getElementById('novaSolicitacaoModal').classList.remove('hidden')"
                class="bg-primary text-white px-4 py-2 rounded-md hover:bg-primary-dark">
            <i class="fas fa-plus mr-2"></i> Nova Solicitação
        </button>
    </div>

    <div class="bg-white shadow overflow-hidden sm:rounded-lg">
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Documento</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Data</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Prioridade</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Ações</th>
                </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
                <c:forEach var="solicitacao" items="${solicitacoes}">
                    <tr>
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">#${solicitacao.id}</td>
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
                            <a href="<c:url value='/solicitacao/${solicitacao.id}'/>"
                               class="text-primary hover:text-primary-dark mr-3">
                                <i class="fas fa-eye"></i> Detalhes
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Modal Nova Solicitação -->
<div id="novaSolicitacaoModal" class="hidden fixed z-10 inset-0 overflow-y-auto" aria-labelledby="modal-title" role="dialog" aria-modal="true">
    <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
        <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
        <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
            <form action="<c:url value='/api/solicitacoes'/>" method="POST" id="formNovaSolicitacao">
                <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                    <h3 class="text-lg leading-6 font-medium text-gray-900 mb-4">Nova Solicitação de Documento</h3>
                    
                    <div class="mb-4">
                        <label for="tipoDocumento" class="block text-sm font-medium text-gray-700">Tipo de Documento</label>
                        <select id="tipoDocumento" name="tipoDocumento" required
                                class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary sm:text-sm">
                            <c:forEach var="tipo" items="${tiposDocumento}">
                                <option value="${tipo.id}">${tipo.nome}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-4">
                        <label for="urgencia" class="block text-sm font-medium text-gray-700">Urgência</label>
                        <select id="urgencia" name="urgencia" required
                                class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary sm:text-sm">
                            <option value="BAIXA">Baixa</option>
                            <option value="MEDIA">Média</option>
                            <option value="ALTA">Alta</option>
                        </select>
                    </div>

                    <div class="mb-4">
                        <label for="observacoes" class="block text-sm font-medium text-gray-700">Observações</label>
                        <textarea id="observacoes" name="observacoes" rows="3"
                                class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary sm:text-sm"
                                placeholder="Adicione observações relevantes..."></textarea>
                    </div>
                </div>

                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                    <button type="submit"
                            class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-primary text-base font-medium text-white hover:bg-primary-dark focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary sm:ml-3 sm:w-auto sm:text-sm">
                        Solicitar
                    </button>
                    <button type="button"
                            onclick="document.getElementById('novaSolicitacaoModal').classList.add('hidden')"
                            class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm">
                        Cancelar
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    document.getElementById('formNovaSolicitacao').addEventListener('submit', function(e) {
        e.preventDefault();
        
        var usuarioId = '${sessionScope.usuario.id}';
        
        var formData = {
            idAluno: usuarioId,
            idDocumentoTipo: document.getElementById('tipoDocumento').value,
            urgencia: document.getElementById('urgencia').value,
            observacoes: document.getElementById('observacoes').value
        };

        fetch('<c:url value="/api/solicitacoes"/>', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(function(response) {
            if (response.ok) {
                window.location.reload();
            } else {
                throw new Error('Erro ao criar solicitação');
            }
        })
        .catch(function(error) {
            alert('Erro: ' + error.message);
        });
    });
</script>