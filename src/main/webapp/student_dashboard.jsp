<%@ page import="classes.User" %>
<%@ page import="classes.Solicitation" %>
<%@ page import="classes.DocumentType" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
                    <!DOCTYPE html>
                    <html lang="pt-BR">

                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Portal do Aluno - FAETERJ-RIO</title>
                        <link href="https://fonts.googleapis.com" rel="preconnect" />
                        <link crossorigin="" href="https://fonts.gstatic.com" rel="preconnect" />
                        <link
                            href="https://fonts.googleapis.com/css2?family=Public+Sans:wght@300;400;500;600;700;800&display=swap"
                            rel="stylesheet" />
                        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined"
                            rel="stylesheet" />
                        <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
                        <script>
                            tailwind.config = {
                                darkMode: "class",
                                theme: {
                                    extend: {
                                        colors: {
                                            primary: "#005aa3",
                                            "primary-dark": "#004a8a",
                                            "secondary": "#f8a31c",
                                            "background-light": "#f8fafc",
                                            "background-dark": "#0f172a",
                                            "content-light": "#ffffff",
                                            "content-dark": "#1e293b",
                                            "foreground-light": "#1e293b",
                                            "foreground-dark": "#f1f5f9",
                                            "neutral-light": "#64748b",
                                            "neutral-dark": "#94a3b8",
                                            "border-light": "#e2e8f0",
                                            "border-dark": "#334155",
                                            success: "#16a34a",
                                            alert: "#d97706",
                                            urgent: "#dc2626",
                                        },
                                        fontFamily: {
                                            display: ["Public Sans", "sans-serif"],
                                        },
                                        borderRadius: {
                                            DEFAULT: "0.5rem",
                                            lg: "0.75rem",
                                            xl: "1rem",
                                            full: "9999px",
                                        },
                                        boxShadow: {
                                            'soft': '0 1px 3px 0 rgb(0 0 0 / 0.05), 0 1px 2px -1px rgb(0 0 0 / 0.05)',
                                            'card': '0 4px 6px -1px rgb(0 0 0 / 0.05), 0 2px 4px -2px rgb(0 0 0 / 0.05)',
                                            'hover': '0 10px 15px -3px rgb(0 0 0 / 0.05), 0 4px 6px -4px rgb(0 0 0 / 0.05)'
                                        },
                                        animation: {
                                            'fade-in': 'fadeIn 0.5s ease-in-out',
                                            'slide-up': 'slideUp 0.5s ease-out',
                                        },
                                        keyframes: {
                                            fadeIn: {
                                                '0%': { opacity: '0' },
                                                '100%': { opacity: '1' }
                                            },
                                            slideUp: {
                                                '0%': { transform: 'translateY(10px)', opacity: '0' },
                                                '100%': { transform: 'translateY(0)', opacity: '1' }
                                            }
                                        }
                                    },
                                },
                            };
                        </script>
                        <style>
                            .material-symbols-outlined {
                                font-variation-settings:
                                    'FILL' 1,
                                    'wght' 400,
                                    'GRAD' 0,
                                    'opsz' 24
                            }

                            .status-badge {
                                display: inline-flex;
                                align-items: center;
                                gap: 0.375rem;
                                border-radius: 9999px;
                                padding-left: 0.75rem;
                                padding-right: 0.75rem;
                                padding-top: 0.375rem;
                                padding-bottom: 0.375rem;
                                font-size: 0.75rem;
                                font-weight: 600;
                                border: 1px solid transparent;
                            }

                            .btn-primary {
                                background-color: #005aa3;
                                color: white;
                                border-radius: 0.5rem;
                                padding: 0.75rem 1.5rem;
                                font-weight: 600;
                                font-size: 0.875rem;
                                display: inline-flex;
                                align-items: center;
                                gap: 0.5rem;
                                transition: all 0.2s ease;
                                border: none;
                                cursor: pointer;
                            }

                            .btn-primary:hover {
                                background-color: #004a8a;
                                transform: translateY(-1px);
                                box-shadow: 0 4px 6px -1px rgba(0, 90, 163, 0.2);
                            }

                            .form-input {
                                width: 100%;
                                border-radius: 0.5rem;
                                border: 1px solid #e2e8f0;
                                background-color: #ffffff;
                                padding: 0.75rem 1rem;
                                font-size: 0.875rem;
                                transition: all 0.2s ease;
                            }

                            .form-input:focus {
                                outline: none;
                                border-color: #005aa3;
                                box-shadow: 0 0 0 3px rgba(0, 90, 163, 0.1);
                            }

                            .dark .form-input {
                                background-color: #0f172a;
                                border-color: #334155;
                                color: #f1f5f9;
                            }

                            .dark .form-input:focus {
                                border-color: #005aa3;
                                box-shadow: 0 0 0 3px rgba(0, 90, 163, 0.2);
                            }
                        </style>
                    </head>

                    <body
                        class="bg-background-light dark:bg-background-dark font-display text-foreground-light dark:text-foreground-dark">
                        <%
                                // Retrieve data from request attributes
                                User user = (User) request.getSession().getAttribute("user");
                                
                                List<DocumentType> documentTypes = (List<DocumentType>)
                                        request.getAttribute("documentTypes");
                                List<Solicitation> solicitations = (List<Solicitation>)
                                        request.getAttribute("solicitations");
                                        if (documentTypes == null) {
                                        documentTypes = java.util.Collections.emptyList();
                                        }
                                        if (solicitations == null) {
                                        solicitations = java.util.Collections.emptyList();
                                        }

                                        // Create a lookup map for DocumentType names
                                        Map<Integer, String> documentTypeNames = new HashMap<>();
                                                for (DocumentType type : documentTypes) {
                                                documentTypeNames.put(type.getId(), type.getNome());
                                                }

                                                // Count solicitations by status for the stats
                                                int pendingCount = 0;
                                                int inProgressCount = 0;
                                                int completedCount = 0;
                                                for (Solicitation solicitation : solicitations) {
                                                switch (solicitation.getStatus()) {
                                                case "SOLICITADO":
                                                case "PENDENTE":
                                                pendingCount++;
                                                break;
                                                case "VISUALIZADO":
                                                case "EM_PRODUCAO":
                                                case "EM_PROCESSAMENTO":
                                                inProgressCount++;
                                                break;
                                                case "CONCLUIDO":
                                                case "CONCLUIDA":
                                                completedCount++;
                                                break;
                                                }
                                                }
                                                %>

                                                <div class="min-h-screen flex flex-col">
                                                    <!-- Header -->
                                                    <header
                                                        class="sticky top-0 z-20 flex h-16 items-center justify-between border-b border-border-light dark:border-border-dark bg-content-light dark:bg-content-dark px-4 shadow-sm backdrop-blur-sm sm:px-6 lg:px-8">
                                                        <div class="flex items-center gap-3">
                                                            <div
                                                                class="w-10 h-10 bg-primary rounded-lg flex items-center justify-center">
                                                                <span
                                                                    class="material-symbols-outlined text-white text-lg">school</span>
                                                            </div>
                                                            <div>
                                                                <h1 class="text-xl font-bold">FAETERJ-RIO</h1>
                                                                <p
                                                                    class="text-xs text-neutral-light dark:text-neutral-dark">
                                                                    Portal do Aluno</p>
                                                            </div>
                                                        </div>
                                                        <div class="flex items-center gap-4">
                                                            <a class="flex items-center gap-2 text-sm font-medium text-urgent hover:text-urgent/80 px-3 py-2 rounded-lg hover:bg-urgent/5 transition-colors"
                                                                href="logout">
                                                                <span
                                                                    class="material-symbols-outlined text-lg">logout</span>
                                                                <span class="hidden sm:inline">Sair</span>
                                                            </a>
                                                        </div>
                                                    </header>

                                                    <main class="flex-1 px-4 py-8 sm:px-6 lg:px-8">
                                                        <div class="max-w-6xl mx-auto">
                                                            <!-- Page Header -->
                                                            <div class="mb-8 animate-fade-in">
                                                                <h1 class="text-2xl font-bold mb-2">Solicitacoes de
                                                                    Documentos</h1>
                                                                <p class="text-neutral-light dark:text-neutral-dark">
                                                                    Gerencie suas solicitacoes de documentos academicos
                                                                </p>
                                                            </div>

                                                            <!-- Stats Cards -->
                                                            <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
                                                                <div
                                                                    class="bg-content-light dark:bg-content-dark rounded-xl p-6 shadow-card border border-border-light dark:border-border-dark">
                                                                    <div class="flex items-center justify-between">
                                                                        <div>
                                                                            <p
                                                                                class="text-sm font-medium text-neutral-light dark:text-neutral-dark mb-1">
                                                                                Pendentes</p>
                                                                            <p class="text-2xl font-bold text-alert">
                                                                                <%= pendingCount %>
                                                                            </p>
                                                                        </div>
                                                                        <div
                                                                            class="w-12 h-12 bg-yellow-100 dark:bg-yellow-900/20 rounded-lg flex items-center justify-center">
                                                                            <span
                                                                                class="material-symbols-outlined text-alert text-xl">pending</span>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div
                                                                    class="bg-content-light dark:bg-content-dark rounded-xl p-6 shadow-card border border-border-light dark:border-border-dark">
                                                                    <div class="flex items-center justify-between">
                                                                        <div>
                                                                            <p
                                                                                class="text-sm font-medium text-neutral-light dark:text-neutral-dark mb-1">
                                                                                Em Andamento</p>
                                                                            <p class="text-2xl font-bold text-primary">
                                                                                <%= inProgressCount %>
                                                                            </p>
                                                                        </div>
                                                                        <div
                                                                            class="w-12 h-12 bg-blue-100 dark:bg-blue-900/20 rounded-lg flex items-center justify-center">
                                                                            <span
                                                                                class="material-symbols-outlined text-primary text-xl">autorenew</span>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div
                                                                    class="bg-content-light dark:bg-content-dark rounded-xl p-6 shadow-card border border-border-light dark:border-border-dark">
                                                                    <div class="flex items-center justify-between">
                                                                        <div>
                                                                            <p
                                                                                class="text-sm font-medium text-neutral-light dark:text-neutral-dark mb-1">
                                                                                Concluidas</p>
                                                                            <p class="text-2xl font-bold text-success">
                                                                                <%= completedCount %>
                                                                            </p>
                                                                        </div>
                                                                        <div
                                                                            class="w-12 h-12 bg-green-100 dark:bg-green-900/20 rounded-lg flex items-center justify-center">
                                                                            <span
                                                                                class="material-symbols-outlined text-success text-xl">check_circle</span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
                                                                <!-- New Solicitation Form -->
                                                                <div class="lg:col-span-1">
                                                                    <div
                                                                        class="bg-content-light dark:bg-content-dark rounded-xl border border-border-light dark:border-border-dark shadow-card overflow-hidden sticky top-24">
                                                                        <div
                                                                            class="border-b border-border-light dark:border-border-dark px-6 py-4 bg-background-light dark:bg-background-dark">
                                                                            <h2
                                                                                class="text-lg font-bold flex items-center gap-2">
                                                                                <span
                                                                                    class="material-symbols-outlined text-primary">add_circle</span>
                                                                                Nova Solicitacao
                                                                            </h2>
                                                                        </div>
                                                                        <form action="student_dashboard" method="POST"
                                                                            class="p-6 space-y-6">
                                                                            <div>
                                                                                <label
                                                                                    class="block text-sm font-medium mb-2"
                                                                                    for="documentType">Tipo de
                                                                                    Documento</label>
                                                                                <select id="documentType"
                                                                                    name="documentTypeId" required
                                                                                    class="form-input">
                                                                                    <% for (DocumentType type :
                                                                                        documentTypes) { %>
                                                                                        <option
                                                                                            value="<%= type.getId() %>">
                                                                                            <%= type.getNome() %>
                                                                                        </option>
                                                                                        <% } %>
                                                                                </select>
                                                                            </div>

                                                                            <div>
                                                                                <label
                                                                                    class="block text-sm font-medium mb-2"
                                                                                    for="urgency">Urgencia</label>
                                                                                <select id="urgency" name="urgency"
                                                                                    required class="form-input">
                                                                                    <option value="BAIXA">Baixa</option>
                                                                                    <option value="MEDIA">Media</option>
                                                                                    <option value="ALTA">Alta</option>
                                                                                </select>
                                                                            </div>

                                                                            <div>
                                                                                <label
                                                                                    class="block text-sm font-medium mb-2"
                                                                                    for="description">Descricao</label>
                                                                                <textarea id="description"
                                                                                    name="descricao" rows="4" required
                                                                                    class="form-input"
                                                                                    placeholder="Descreva o motivo da sua solicitacao"></textarea>
                                                                            </div>

                                                                            <button type="submit"
                                                                                class="w-full btn-primary justify-center">
                                                                                <span
                                                                                    class="material-symbols-outlined text-lg">send</span>
                                                                                Enviar Solicitacao
                                                                            </button>
                                                                        </form>
                                                                    </div>
                                                                </div>

                                                                <!-- Solicitation List -->
                                                                <div class="lg:col-span-2">
                                                                    <div
                                                                        class="bg-content-light dark:bg-content-dark rounded-xl border border-border-light dark:border-border-dark shadow-card overflow-hidden">
                                                                        <div
                                                                            class="border-b border-border-light dark:border-border-dark px-6 py-4 bg-background-light dark:bg-background-dark">
                                                                            <h2
                                                                                class="text-lg font-bold flex items-center gap-2">
                                                                                <span
                                                                                    class="material-symbols-outlined text-primary">list_alt</span>
                                                                                Minhas Solicitacoes
                                                                            </h2>
                                                                        </div>

                                                                        <% if (solicitations.isEmpty()) { %>
                                                                            <div class="p-8 text-center">
                                                                                <span
                                                                                    class="material-symbols-outlined text-neutral-light dark:text-neutral-dark text-4xl mb-3">inbox</span>
                                                                                <h3
                                                                                    class="text-lg font-medium text-foreground-light dark:text-foreground-dark mb-2">
                                                                                    Nenhuma solicitacao encontrada</h3>
                                                                                <p
                                                                                    class="text-neutral-light dark:text-neutral-dark mb-6">
                                                                                    Você ainda nao possui solicitacoes
                                                                                    de documentos.</p>
                                                                                <p
                                                                                    class="text-sm text-neutral-light dark:text-neutral-dark">
                                                                                    Use o formulário ao lado para criar
                                                                                    sua primeira solicitacao.</p>
                                                                            </div>
                                                                            <% } else { %>
                                                                                <div class="overflow-x-auto">
                                                                                    <table class="w-full">
                                                                                        <thead
                                                                                            class="bg-background-light dark:bg-background-dark border-b border-border-light dark:border-border-dark">
                                                                                            <tr>
                                                                                                <th
                                                                                                    class="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-neutral-light dark:text-neutral-dark">
                                                                                                    ID</th>
                                                                                                <th
                                                                                                    class="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-neutral-light dark:text-neutral-dark">
                                                                                                    Documento</th>
                                                                                                <th
                                                                                                    class="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-neutral-light dark:text-neutral-dark">
                                                                                                    Urgencia</th>
                                                                                                <th
                                                                                                    class="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-neutral-light dark:text-neutral-dark">
                                                                                                    Status</th>
                                                                                                <th
                                                                                                    class="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-neutral-light dark:text-neutral-dark">
                                                                                                    Data</th>
                                                                                                <th
                                                                                                    class="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-neutral-light dark:text-neutral-dark">
                                                                                                    Acoes</th>
                                                                                            </tr>
                                                                                        </thead>
                                                                                        <tbody
                                                                                            class="divide-y divide-border-light dark:divide-border-dark">
                                                                                            <% for (Solicitation
                                                                                                solicitation :
                                                                                                solicitations) { String
                                                                                                statusClass="" ; String
                                                                                                statusIcon="" ; switch
                                                                                                (solicitation.getStatus())
                                                                                                { case "SOLICITADO" :
                                                                                                case "PENDENTE" :
                                                                                                statusClass="bg-alert/10 text-alert border-alert/20"
                                                                                                ; statusIcon="pending" ;
                                                                                                break;
                                                                                                case "VISUALIZADO" :
                                                                                                case "EM_PRODUCAO" :
                                                                                                case "EM_PROCESSAMENTO"
                                                                                                :
                                                                                                statusClass="bg-primary/10 text-primary border-primary/20"
                                                                                                ; statusIcon="autorenew"
                                                                                                ; break;
                                                                                                case "CONCLUIDO" :
                                                                                                case "CONCLUIDA" :
                                                                                                statusClass="bg-success/10 text-success border-success/20"
                                                                                                ;
                                                                                                statusIcon="check_circle"
                                                                                                ; break; default:
                                                                                                statusClass="bg-neutral-light/10 text-neutral-light border-neutral-light/20"
                                                                                                ; statusIcon="help" ; }
                                                                                                String urgencyClass="" ;
                                                                                                switch
                                                                                                (solicitation.getUrgencia())
                                                                                                { case "BAIXA" :
                                                                                                urgencyClass="bg-success/10 text-success border-success/20"
                                                                                                ; break; case "MEDIA" :
                                                                                                urgencyClass="bg-alert/10 text-alert border-alert/20"
                                                                                                ; break; case "ALTA" :
                                                                                                urgencyClass="bg-urgent/10 text-urgent border-urgent/20"
                                                                                                ; break; default:
                                                                                                urgencyClass="bg-neutral-light/10 text-neutral-light border-neutral-light/20"
                                                                                                ; } String
                                                                                                urgencyDisplay="" ;
                                                                                                switch
                                                                                                (solicitation.getUrgencia())
                                                                                                { case "BAIXA" :
                                                                                                urgencyDisplay="Baixa" ;
                                                                                                break; case "MEDIA" :
                                                                                                urgencyDisplay="Média" ;
                                                                                                break; case "ALTA" :
                                                                                                urgencyDisplay="Alta" ;
                                                                                                break; default:
                                                                                                urgencyDisplay=solicitation.getUrgencia();
                                                                                                } %>
                                                                                                <tr
                                                                                                    class="hover:bg-background-light dark:hover:bg-background-dark transition-colors duration-150">
                                                                                                    <td
                                                                                                        class="whitespace-nowrap px-6 py-4 text-sm font-medium text-foreground-light dark:text-foreground-dark">
                                                                                                        #<%= solicitation.getId()
                                                                                                            %>
                                                                                                    </td>
                                                                                                    <td
                                                                                                        class="whitespace-nowrap px-6 py-4 text-sm text-foreground-light dark:text-foreground-dark">
                                                                                                        <%= documentTypeNames.getOrDefault(solicitation.getIdDocumentoTipo(), "N/A"
                                                                                                            ) %>
                                                                                                    </td>
                                                                                                    <td
                                                                                                        class="whitespace-nowrap px-6 py-4 text-sm">
                                                                                                        <span
                                                                                                            class="status-badge <%= urgencyClass %>">
                                                                                                            <%= urgencyDisplay
                                                                                                                %>
                                                                                                        </span>
                                                                                                    </td>
                                                                                                    <td
                                                                                                        class="whitespace-nowrap px-6 py-4 text-sm">
                                                                                                        <span
                                                                                                            class="status-badge <%= statusClass %>">
                                                                                                            <span
                                                                                                                class="material-symbols-outlined text-sm">
                                                                                                                <%= statusIcon
                                                                                                                    %>
                                                                                                            </span>
                                                                                                            <%= solicitation.getStatus().replace("_", " "
                                                                                                                ) %>
                                                                                                        </span>
                                                                                                    </td>
                                                                                                    <td
                                                                                                        class="whitespace-nowrap px-6 py-4 text-sm text-neutral-light dark:text-neutral-dark">
                                                                                                        <%= solicitation.getDataSolicitacao()
                                                                                                            %>
                                                                                                    </td>
                                                                                                    <td
                                                                                                        class="whitespace-nowrap px-6 py-4 text-sm">
                                                                                                        <a class="inline-flex items-center gap-1 bg-primary hover:bg-primary-dark text-white px-3 py-2 rounded-lg text-sm font-semibold transition-colors"
                                                                                                            href="solicitation_details?id=<%= solicitation.getId() %>">
                                                                                                            <span
                                                                                                                class="material-symbols-outlined text-sm">visibility</span>
                                                                                                            Ver
                                                                                                        </a>
                                                                                                    </td>
                                                                                                </tr>
                                                                                                <% } %>
                                                                                        </tbody>
                                                                                    </table>
                                                                                </div>
                                                                                <% } %>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </main>
                                                </div>

                                                <script>
                                                    // Add form submission loading state
                                                    document.addEventListener('DOMContentLoaded', function () {
                                                        const form = document.querySelector('form');
                                                        if (form) {
                                                            form.addEventListener('submit', function () {
                                                                const submitButton = this.querySelector('button[type="submit"]');
                                                                if (submitButton) {
                                                                    submitButton.disabled = true;
                                                                    submitButton.innerHTML = `
                            <svg class="animate-spin -ml-1 mr-2 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                            </svg>
                            Enviando...
                        `;
                                                                }
                                                            });
                                                        }
                                                    });
                                                </script>
                    </body>

                    </html>