<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="min-h-screen flex items-center justify-center bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full space-y-8 text-center">
        <div>
            <h1 class="mt-6 text-center text-4xl font-extrabold text-gray-900">
                403
            </h1>
            <h2 class="mt-2 text-center text-2xl font-bold text-gray-900">
                Acesso Negado
            </h2>
            <p class="mt-2 text-center text-sm text-gray-600">
                Você não tem permissão para acessar esta página.
            </p>
        </div>
        <div>
            <a href="<c:url value='/'/>"
               class="w-full flex items-center justify-center px-4 py-2 border border-transparent text-base font-medium rounded-md text-white bg-primary hover:bg-primary-dark">
                Voltar para a página inicial
            </a>
        </div>
    </div>
</div>