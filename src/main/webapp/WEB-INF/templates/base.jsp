<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - Sistema de Documentos</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: '#1a56db',
                        secondary: '#7e3af2'
                    }
                }
            }
        }
    </script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="bg-gray-100 min-h-screen">
    <nav class="bg-primary shadow-lg">
        <div class="max-w-7xl mx-auto px-4">
            <div class="flex justify-between h-16">
                <div class="flex">
                    <a href="<c:url value='/'/>" class="flex items-center text-white font-bold text-xl">
                        Sistema de Documentos
                    </a>
                </div>
                <c:if test="${not empty sessionScope.usuario}">
                    <div class="flex items-center">
                        <span class="text-white mr-4">${sessionScope.usuario.nome}</span>
                        <a href="<c:url value='/logout'/>" class="text-white hover:text-gray-200">
                            <i class="fas fa-sign-out-alt"></i> Sair
                        </a>
                    </div>
                </c:if>
            </div>
        </div>
    </nav>

    <main class="container mx-auto px-4 py-8">
        <jsp:include page="/WEB-INF/views/${param.content}.jsp" />
    </main>

    <script>
        // WebSocket connection setup
        const wsProtocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
        const wsUrl = \`\${wsProtocol}/\${window.location.host}\${window.location.pathname}websocket/notificacoes/\${window.USER_ID}\`;
        const ws = new WebSocket(wsUrl);

        ws.onmessage = function(event) {
            const notification = JSON.parse(event.data);
            showNotification(notification);
        };

        function showNotification(notification) {
            const toast = document.createElement('div');
            toast.className = 'fixed bottom-4 right-4 bg-white shadow-lg rounded-lg p-4 mb-4 border-l-4 border-primary';
            toast.innerHTML = `
                <div class="flex items-center">
                    <div class="flex-shrink-0">
                        <i class="fas fa-bell text-primary"></i>
                    </div>
                    <div class="ml-3">
                        <p class="text-sm font-medium text-gray-900">
                            Atualização de Status
                        </p>
                        <p class="text-sm text-gray-500">
                            Solicitação #\${notification.solicitacaoId}: \${notification.status}
                        </p>
                        <p class="text-xs text-gray-400 mt-1">
                            \${notification.observacao}
                        </p>
                    </div>
                </div>
            `;
            document.body.appendChild(toast);
            setTimeout(() => toast.remove(), 5000);
        }
    </script>
</body>
</html>