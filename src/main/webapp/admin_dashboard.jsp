<%@ page
    import="java.util.List, java.util.Map, java.util.HashMap, classes.User, classes.DocumentType, classes.Solicitation"
    %>
    <!DOCTYPE html>
    <html lang="pt-BR">

    <head>
        <meta charset="utf-8" />
        <meta content="width=device-width, initial-scale=1.0" name="viewport" />
        <title>FAETERJ-RIO ADMIN - Solicitacoes</title>
        <link href="https://fonts.googleapis.com" rel="preconnect" />
        <link crossorigin="" href="https://fonts.gstatic.com" rel="preconnect" />
        <link href="https://fonts.googleapis.com/css2?family=Public+Sans:wght@400;500;600;700;800&display=swap"
            rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
        <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
        <script>
            tailwind.config = {
                darkMode: "class",
                theme: {
                    extend: {
                        colors: {
                            "primary": "#005aa3",
                            "primary-dark": "#004a8a",
                            "success": "#28a745",
                            "alert": "#ffc107",
                            "urgent": "#dc3545",
                            "neutral": "#6c757d",
                            "background-light": "#f8fafc",
                            "background-dark": "#0f172a",
                            "content-light": "#ffffff",
                            "content-dark": "#1e293b",
                            "text-light": "#334155",
                            "text-dark": "#e2e8f0",
                            "border-light": "#e2e8f0",
                            "border-dark": "#334155"
                        },
                        fontFamily: {
                            "display": ["Public Sans", "sans-serif"]
                        },
                        borderRadius: {
                            "DEFAULT": "0.5rem",
                            "lg": "0.75rem",
                            "xl": "1rem",
                            "full": "9999px"
                        },
                        boxShadow: {
                            'subtle': '0 1px 3px 0 rgb(0 0 0 / 0.1), 0 1px 2px -1px rgb(0 0 0 / 0.1)',
                            'card': '0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1)'
                        }
                    },
                },
            }
        </script>
        <style>
            .material-symbols-outlined {
                font-variation-settings:
                    'FILL' 1,
                    'wght' 400,
                    'GRAD' 0,
                    'opsz' 24
            }

            /* Smooth transitions for dark mode */
            * {
                transition: background-color 0.2s ease, border-color 0.2s ease;
            }
        </style>
    </head>

    <body class="font-display bg-background-light dark:bg-background-dark text-text-light dark:text-text-dark">
        <% // Retrieve data from request attributes List<Solicitation> solicitations = (List<Solicitation>)
                request.getAttribute("solicitations");
                List<User> allUsers = (List<User>) request.getAttribute("allUsers");
                        List<DocumentType> allDocumentTypes = (List<DocumentType>)
                                request.getAttribute("allDocumentTypes");

                                if (solicitations == null) {
                                solicitations = java.util.Collections.emptyList();
                                }

                                // Create lookup maps
                                Map<Integer, String> userNames = new HashMap<>();
                                        if (allUsers != null) {
                                        for (User user : allUsers) {
                                        userNames.put(user.getId(), user.getNome());
                                        }
                                        }

                                        Map<Integer, String> documentTypeNames = new HashMap<>();
                                                if (allDocumentTypes != null) {
                                                for (DocumentType dt : allDocumentTypes) {
                                                documentTypeNames.put(dt.getId(), dt.getNome());
                                                }
                                                }
                                                %>

                                                <div class="flex h-screen">
                                                    <!-- Sidebar -->
                                                    <aside
                                                        class="w-64 bg-content-light dark:bg-content-dark flex flex-col border-r border-border-light dark:border-border-dark shadow-sm">
                                                        <div
                                                            class="flex items-center justify-center h-16 border-b border-border-light dark:border-border-dark px-4">
                                                            <div class="flex items-center gap-3">
                                                                <div
                                                                    class="w-8 h-8 bg-primary rounded-lg flex items-center justify-center">
                                                                    <span
                                                                        class="material-symbols-outlined text-white text-sm">school</span>
                                                                </div>
                                                                <h1 class="text-lg font-bold text-primary">FAETERJ-RIO
                                                                </h1>
                                                            </div>
                                                        </div>
                                                        <nav class="flex-1 px-4 py-6 space-y-2">
                                                            <a class="flex items-center gap-3 px-4 py-3 rounded-lg bg-primary/10 text-primary font-semibold border border-primary/20"
                                                                href="#">
                                                                <span
                                                                    class="material-symbols-outlined text-lg">dashboard</span>
                                                                <span>Dashboard</span>
                                                            </a>
                                                        </nav>
                                                    </aside>

                                                    <!-- Main Content -->
                                                    <div class="flex-1 flex flex-col overflow-hidden">
                                                        <!-- Header -->
                                                        <header
                                                            class="h-16 bg-content-light dark:bg-content-dark border-b border-border-light dark:border-border-dark flex items-center justify-between px-6 shadow-sm">
                                                            <div>
                                                                <h1
                                                                    class="text-xl font-bold text-text-light dark:text-text-dark">
                                                                    Solicitacoes</h1>
                                                                <p class="text-sm text-neutral">Gerencie as solicitacoes
                                                                    de documentos</p>
                                                            </div>
                                                            <div class="flex items-center gap-4">
                                                                <span
                                                                    class="font-medium text-text-light dark:text-text-dark">Administrador</span>
                                                                <a class="bg-primary hover:bg-primary-dark text-white px-4 py-2 rounded-lg text-sm font-semibold flex items-center gap-2 transition-colors"
                                                                    href="logout">
                                                                    <span
                                                                        class="material-symbols-outlined text-lg">logout</span>
                                                                    Sair
                                                                </a>
                                                            </div>
                                                        </header>

                                                        <!-- Main Content Area -->
                                                        <main
                                                            class="flex-1 overflow-x-hidden overflow-y-auto bg-background-light dark:bg-background-dark p-6">
                                                            <div class="max-w-7xl mx-auto">
                                                                <!-- Solicitacoes Table -->
                                                                <section
                                                                    class="bg-content-light dark:bg-content-dark rounded-xl shadow-card border border-border-light dark:border-border-dark overflow-hidden">
                                                                    <div
                                                                        class="px-6 py-4 border-b border-border-light dark:border-border-dark">
                                                                        <h2
                                                                            class="text-lg font-bold text-text-light dark:text-text-dark">
                                                                            Lista de Solicitacoes</h2>
                                                                    </div>

                                                                    <div class="overflow-x-auto">
                                                                        <table class="w-full">
                                                                            <thead
                                                                                class="bg-background-light dark:bg-background-dark border-b border-border-light dark:border-border-dark">
                                                                                <tr>
                                                                                    <th
                                                                                        class="p-4 font-semibold text-neutral text-sm text-left">
                                                                                        ID</th>
                                                                                    <th
                                                                                        class="p-4 font-semibold text-neutral text-sm text-left">
                                                                                        Aluno</th>
                                                                                    <th
                                                                                        class="p-4 font-semibold text-neutral text-sm text-left">
                                                                                        Documento</th>
                                                                                    <th
                                                                                        class="p-4 font-semibold text-neutral text-sm text-left">
                                                                                        Data</th>
                                                                                    <th
                                                                                        class="p-4 font-semibold text-neutral text-sm text-left">
                                                                                        Urgencia</th>
                                                                                    <th
                                                                                        class="p-4 font-semibold text-neutral text-sm text-left">
                                                                                        Status</th>
                                                                                    <th
                                                                                        class="p-4 font-semibold text-neutral text-sm text-left">
                                                                                        Prioridade</th>
                                                                                    <th
                                                                                        class="p-4 font-semibold text-neutral text-sm text-center">
                                                                                        Acoes</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody
                                                                                class="divide-y divide-border-light dark:divide-border-dark">
                                                                                <% for (Solicitation solicitation :
                                                                                    solicitations) { String
                                                                                    statusColorClass="" ; String
                                                                                    statusIcon="" ; String
                                                                                    priorityColorClass="" ; String
                                                                                    urgencyColorClass="" ; // Status
                                                                                    styling switch
                                                                                    (solicitation.getStatus()) {
                                                                                    case "PENDENTE" :
                                                                                    statusColorClass="bg-alert/10 text-alert border-alert/20"
                                                                                    ; statusIcon="pending" ; break;
                                                                                    case "EM_PROCESSAMENTO" :
                                                                                    statusColorClass="bg-primary/10 text-primary border-primary/20"
                                                                                    ; statusIcon="autorenew" ; break;
                                                                                    case "CONCLUIDA" :
                                                                                    statusColorClass="bg-success/10 text-success border-success/20"
                                                                                    ; statusIcon="check_circle" ; break;
                                                                                    case "CANCELADA" :
                                                                                    statusColorClass="bg-urgent/10 text-urgent border-urgent/20"
                                                                                    ; statusIcon="cancel" ; break;
                                                                                    default:
                                                                                    statusColorClass="bg-neutral/10 text-neutral border-neutral/20"
                                                                                    ; statusIcon="help" ; } // Priority
                                                                                    styling if
                                                                                    ("Alta".equals(solicitation.getPrioridade()))
                                                                                    {
                                                                                    priorityColorClass="text-urgent font-semibold"
                                                                                    ; } else if
                                                                                    ("Média".equals(solicitation.getPrioridade()))
                                                                                    {
                                                                                    priorityColorClass="text-alert font-semibold"
                                                                                    ; } else if
                                                                                    ("Baixa".equals(solicitation.getPrioridade()))
                                                                                    {
                                                                                    priorityColorClass="text-success font-semibold"
                                                                                    ; } else {
                                                                                    priorityColorClass="text-neutral" ;
                                                                                    } // Urgency styling if
                                                                                    ("Alta".equals(solicitation.getUrgencia()))
                                                                                    {
                                                                                    urgencyColorClass="text-urgent font-semibold"
                                                                                    ; } %>
                                                                                    <tr
                                                                                        class="hover:bg-background-light dark:hover:bg-background-dark transition-colors duration-150">
                                                                                        <td
                                                                                            class="p-4 font-medium text-text-light dark:text-text-dark">
                                                                                            #<%= solicitation.getId() %>
                                                                                        </td>
                                                                                        <td class="p-4">
                                                                                            <div
                                                                                                class="flex items-center gap-3">
                                                                                                <div
                                                                                                    class="w-8 h-8 bg-primary/10 rounded-full flex items-center justify-center">
                                                                                                    <span
                                                                                                        class="material-symbols-outlined text-primary text-sm">person</span>
                                                                                                </div>
                                                                                                <div>
                                                                                                    <p
                                                                                                        class="font-medium text-text-light dark:text-text-dark">
                                                                                                        <%= userNames.get(solicitation.getIdAluno())
                                                                                                            %>
                                                                                                    </p>
                                                                                                </div>
                                                                                            </div>
                                                                                        </td>
                                                                                        <td
                                                                                            class="p-4 text-text-light dark:text-text-dark">
                                                                                            <%= documentTypeNames.get(solicitation.getIdDocumentoTipo())
                                                                                                %>
                                                                                        </td>
                                                                                        <td
                                                                                            class="p-4 text-text-light dark:text-text-dark">
                                                                                            <%= solicitation.getDataSolicitacao()
                                                                                                %>
                                                                                        </td>
                                                                                        <td
                                                                                            class="p-4 <%= urgencyColorClass %>">
                                                                                            <%= solicitation.getUrgencia()
                                                                                                %>
                                                                                        </td>
                                                                                        <td class="p-4">
                                                                                            <div
                                                                                                class="flex items-center gap-2">
                                                                                                <span
                                                                                                    class="material-symbols-outlined text-sm">
                                                                                                    <%= statusIcon %>
                                                                                                </span>
                                                                                                <span
                                                                                                    class="px-3 py-1 rounded-full text-xs font-semibold border <%= statusColorClass %>">
                                                                                                    <%= solicitation.getStatus().replace("_", " "
                                                                                                        ) %>
                                                                                                </span>
                                                                                            </div>
                                                                                        </td>
                                                                                        <td
                                                                                            class="p-4 <%= priorityColorClass %>">
                                                                                            <%= solicitation.getPrioridade()
                                                                                                %>
                                                                                        </td>
                                                                                        <td class="p-4 text-center">
                                                                                            <a class="inline-flex items-center gap-1 bg-primary hover:bg-primary-dark text-white px-3 py-2 rounded-lg text-sm font-semibold transition-colors"
                                                                                                href="solicitation_details?id=<%= solicitation.getId() %>">
                                                                                                <span
                                                                                                    class="material-symbols-outlined text-sm">visibility</span>
                                                                                                Visualizar
                                                                                            </a>
                                                                                        </td>
                                                                                    </tr>
                                                                                    <% } %>

                                                                                        <% if (solicitations.isEmpty())
                                                                                            { %>
                                                                                            <tr>
                                                                                                <td colspan="8"
                                                                                                    class="p-8 text-center">
                                                                                                    <div
                                                                                                        class="flex flex-col items-center justify-center text-neutral">
                                                                                                        <span
                                                                                                            class="material-symbols-outlined text-4xl mb-2">inbox</span>
                                                                                                        <p
                                                                                                            class="text-lg font-medium">
                                                                                                            Nenhuma
                                                                                                            solicitação
                                                                                                            encontrada
                                                                                                        </p>
                                                                                                        <p
                                                                                                            class="text-sm">
                                                                                                            Não há
                                                                                                            solicitacoes
                                                                                                            para exibir
                                                                                                            no momento.
                                                                                                        </p>
                                                                                                    </div>
                                                                                                </td>
                                                                                            </tr>
                                                                                            <% } %>
                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                                                </section>
                                                            </div>
                                                        </main>
                                                    </div>
                                                </div>
    </body>

    </html>