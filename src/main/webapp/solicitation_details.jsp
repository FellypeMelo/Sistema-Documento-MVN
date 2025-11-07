<%@page import="classes.User" %>
    <%@page import="classes.StatusHistory" %>
        <%@page import="classes.Solicitation" %>
            <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
                <%@ page import="java.util.List" %>
                    <%@ page import="java.text.SimpleDateFormat" %>
                        <%@ page import="java.util.Date" %>
                            <% // 1. Retrieve Data
                                Solicitation solicitation = (Solicitation) request.getAttribute("solicitation");
                                List<StatusHistory> history = (List<StatusHistory>) request.getAttribute("history");
                                    User loggedInUser = (User) session.getAttribute("user");

                                    boolean isAdmin = loggedInUser != null && "ADMIN".equals(loggedInUser.getTipo());

                                    // Assuming the servlet has set additional attributes for related entities
                                    String studentName = (String) request.getAttribute("studentName");
                                    String studentMatricula = (String) request.getAttribute("studentMatricula");
                                    String documentTypeName = (String) request.getAttribute("documentTypeName");
                                    String calculatedPriority = (String) request.getAttribute("calculatedPriority");

                                    // Fallback if attributes are missing
                                    if (solicitation != null) {
                                    if (studentName == null) studentName = "N/A";
                                    if (studentMatricula == null) studentMatricula = "N/A";
                                    if (documentTypeName == null) documentTypeName = "N/A";
                                    if (calculatedPriority == null) calculatedPriority =
                                    String.valueOf(solicitation.getPrioridade());
                                    }

                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    %>
                                    <!DOCTYPE html>
                                    <html lang="pt-BR">

                                    <head>
                                        <meta charset="utf-8" />
                                        <meta content="width=device-width, initial-scale=1.0" name="viewport" />
                                        <title>Detalhes da Solicitação #<%= solicitation !=null ? solicitation.getId()
                                                : "N/A" %> - FAETERJ-RIO</title>
                                        <link href="https://fonts.googleapis.com" rel="preconnect" />
                                        <link crossorigin="" href="https://fonts.gstatic.com" rel="preconnect" />
                                        <link
                                            href="https://fonts.googleapis.com/css2?family=Public+Sans:wght@300;400;500;600;700;800&display=swap"
                                            rel="stylesheet" />
                                        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined"
                                            rel="stylesheet" />
                                        <script src="https://cdn.tailwindcss.com?plugins=forms"></script>
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

                                            .timeline-item:not(:last-child):after {
                                                content: '';
                                                position: absolute;
                                                left: 1.25rem;
                                                top: 2.5rem;
                                                bottom: -1.5rem;
                                                width: 2px;
                                                background-color: #e2e8f0;
                                            }

                                            .dark .timeline-item:not(:last-child):after {
                                                background-color: #334155;
                                            }

                                            .btn-primary {
                                                background-color: #005aa3;
                                                color: white;
                                                border-radius: 0.5rem;
                                                padding: 0.5rem 1rem;
                                                font-weight: 600;
                                                font-size: 0.875rem;
                                                display: inline-flex;
                                                align-items: center;
                                                gap: 0.5rem;
                                                transition: all 0.2s ease;
                                            }

                                            .btn-primary:hover {
                                                background-color: #004a8a;
                                                transform: translateY(-1px);
                                                box-shadow: 0 4px 6px -1px rgba(0, 90, 163, 0.2);
                                            }
                                        </style>
                                    </head>

                                    <body
                                        class="bg-background-light dark:bg-background-dark font-display text-foreground-light dark:text-foreground-dark">
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
                                                    <h1 class="text-xl font-bold">FAETERJ-RIO</h1>
                                                </div>
                                                <div class="flex items-center gap-4">
                                                    <% if (loggedInUser !=null) { %>
                                                        <div class="flex items-center gap-3">
                                                            <div
                                                                class="w-8 h-8 bg-primary/10 rounded-full flex items-center justify-center">
                                                                <span
                                                                    class="material-symbols-outlined text-primary text-sm">person</span>
                                                            </div>
                                                            <div class="hidden sm:block text-right">
                                                                <p class="text-sm font-medium">
                                                                    <%= loggedInUser.getNome() %>
                                                                </p>
                                                                <p
                                                                    class="text-xs text-neutral-light dark:text-neutral-dark">
                                                                    <%= loggedInUser.getTipo() %>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <a class="flex items-center gap-2 text-sm font-medium text-urgent hover:text-urgent/80 px-3 py-2 rounded-lg hover:bg-urgent/5 transition-colors"
                                                            href="logout">
                                                            <span
                                                                class="material-symbols-outlined text-lg">logout</span>
                                                            <span class="hidden sm:inline">Sair</span>
                                                        </a>
                                                        <% } %>
                                                </div>
                                            </header>

                                            <main class="flex-1 px-4 py-8 sm:px-6 lg:px-8">
                                                <div class="mx-auto max-w-5xl">
                                                    <% if (solicitation==null) { %>
                                                        <div
                                                            class="text-center p-10 bg-content-light dark:bg-content-dark rounded-xl border border-border-light dark:border-border-dark shadow-card animate-fade-in">
                                                            <span
                                                                class="material-symbols-outlined text-urgent text-5xl mb-4">error</span>
                                                            <h2 class="text-2xl font-bold text-urgent mb-2">Solicitação
                                                                não encontrada</h2>
                                                            <p class="text-neutral-light dark:text-neutral-dark mb-6">
                                                                Verifique o ID da solicitação e tente novamente.</p>
                                                            <a href="<%= isAdmin ? " /admin_dashboard"
                                                                : "/student_dashboard" %>" class="btn-primary">
                                                                <span
                                                                    class="material-symbols-outlined text-lg">arrow_back</span>
                                                                Voltar ao Dashboard
                                                            </a>
                                                        </div>
                                                        <% } else { %>
                                                            <!-- Breadcrumb and Header -->
                                                            <nav class="mb-8 flex items-center gap-2 text-sm">
                                                                <a class="flex items-center gap-1 text-neutral-light dark:text-neutral-dark hover:text-primary transition-colors"
                                                                    href="<%= isAdmin ? " /admin_dashboard"
                                                                    : "/student_dashboard" %>">
                                                                    <span
                                                                        class="material-symbols-outlined text-sm">dashboard</span>
                                                                    Dashboard
                                                                </a>
                                                                <span
                                                                    class="material-symbols-outlined text-sm text-neutral-light dark:text-neutral-dark">chevron_right</span>
                                                                <span
                                                                    class="font-semibold text-foreground-light dark:text-foreground-dark">Detalhes
                                                                    #SOL-<%= solicitation.getId() %></span>
                                                            </nav>

                                                            <div class="space-y-8 animate-slide-up">
                                                                <!-- Page Header -->
                                                                <div
                                                                    class="flex flex-col md:flex-row md:items-center justify-between gap-4">
                                                                    <div>
                                                                        <h1 class="text-2xl font-bold">Solicitação #<%=
                                                                                solicitation.getId() %>
                                                                        </h1>
                                                                        <p
                                                                            class="text-neutral-light dark:text-neutral-dark mt-1">
                                                                            Detalhes completos da solicitação de
                                                                            documento</p>
                                                                    </div>
                                                                    <div class="flex items-center gap-3">
                                                                        <span
                                                                            class="text-sm text-neutral-light dark:text-neutral-dark">Status
                                                                            atual:</span>
                                                                        <% String statusClass="" ; String statusIcon=""
                                                                            ; switch (solicitation.getStatus()) {
                                                                            case "SOLICITADO" :
                                                                            statusClass="bg-alert/10 text-alert border-alert/20"
                                                                            ; statusIcon="pending" ; break;
                                                                            case "VISUALIZADO" :
                                                                            statusClass="bg-primary/10 text-primary border-primary/20"
                                                                            ; statusIcon="visibility" ; break;
                                                                            case "EM_PRODUCAO" :
                                                                            statusClass="bg-primary/10 text-primary border-primary/20"
                                                                            ; statusIcon="build" ; break;
                                                                            case "CONCLUIDO" :
                                                                            statusClass="bg-success/10 text-success border-success/20"
                                                                            ; statusIcon="check_circle" ; break;
                                                                            default:
                                                                            statusClass="bg-neutral-light/10 text-neutral-light border-neutral-light/20"
                                                                            ; statusIcon="help" ; } %>
                                                                            <span
                                                                                class="status-badge <%= statusClass %>">
                                                                                <span
                                                                                    class="material-symbols-outlined text-sm">
                                                                                    <%= statusIcon %>
                                                                                </span>
                                                                                <% String statusDisplay="" ; switch
                                                                                    (solicitation.getStatus()) {
                                                                                    case "SOLICITADO" :
                                                                                    statusDisplay="Solicitado" ; break;
                                                                                    case "VISUALIZADO" :
                                                                                    statusDisplay="Visualizado" ; break;
                                                                                    case "EM_PRODUCAO" :
                                                                                    statusDisplay="Em Produção" ; break;
                                                                                    case "CONCLUIDO" :
                                                                                    statusDisplay="Concluído" ; break;
                                                                                    default:
                                                                                    statusDisplay=solicitation.getStatus();
                                                                                    } %>
                                                                                    <%= statusDisplay %>
                                                                            </span>
                                                                    </div>
                                                                </div>

                                                                <!-- Main Content Grid -->
                                                                <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
                                                                    <!-- Left Column - Details and Actions -->
                                                                    <div class="lg:col-span-2 space-y-8">
                                                                        <!-- Details Card -->
                                                                        <div
                                                                            class="bg-content-light dark:bg-content-dark rounded-xl border border-border-light dark:border-border-dark shadow-card overflow-hidden">
                                                                            <div
                                                                                class="border-b border-border-light dark:border-border-dark px-6 py-4 bg-background-light dark:bg-background-dark">
                                                                                <h2
                                                                                    class="text-lg font-bold flex items-center gap-2">
                                                                                    <span
                                                                                        class="material-symbols-outlined text-primary">info</span>
                                                                                    Detalhes da Solicitação
                                                                                </h2>
                                                                            </div>
                                                                            <div class="p-6">
                                                                                <div
                                                                                    class="grid grid-cols-1 md:grid-cols-2 gap-6">
                                                                                    <div class="space-y-1">
                                                                                        <p
                                                                                            class="text-sm text-neutral-light dark:text-neutral-dark">
                                                                                            Solicitante</p>
                                                                                        <div
                                                                                            class="flex items-center gap-3">
                                                                                            <div
                                                                                                class="w-10 h-10 bg-primary/10 rounded-full flex items-center justify-center">
                                                                                                <span
                                                                                                    class="material-symbols-outlined text-primary text-sm">person</span>
                                                                                            </div>
                                                                                            <div>
                                                                                                <p
                                                                                                    class="font-semibold">
                                                                                                    <%= studentName %>
                                                                                                </p>
                                                                                                <p
                                                                                                    class="text-sm text-neutral-light dark:text-neutral-dark">
                                                                                                    Matrícula: <%=
                                                                                                        studentMatricula
                                                                                                        %>
                                                                                                </p>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="space-y-1">
                                                                                        <p
                                                                                            class="text-sm text-neutral-light dark:text-neutral-dark">
                                                                                            Documento Solicitado</p>
                                                                                        <div
                                                                                            class="flex items-center gap-3">
                                                                                            <div
                                                                                                class="w-10 h-10 bg-secondary/10 rounded-full flex items-center justify-center">
                                                                                                <span
                                                                                                    class="material-symbols-outlined text-secondary text-sm">description</span>
                                                                                            </div>
                                                                                            <p class="font-semibold">
                                                                                                <%= documentTypeName %>
                                                                                            </p>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="space-y-1">
                                                                                        <p
                                                                                            class="text-sm text-neutral-light dark:text-neutral-dark">
                                                                                            Data da Solicitação</p>
                                                                                        <div
                                                                                            class="flex items-center gap-3">
                                                                                            <div
                                                                                                class="w-10 h-10 bg-success/10 rounded-full flex items-center justify-center">
                                                                                                <span
                                                                                                    class="material-symbols-outlined text-success text-sm">calendar_today</span>
                                                                                            </div>
                                                                                            <p class="font-semibold">
                                                                                                <%= solicitation.getDataSolicitacao()
                                                                                                    %>
                                                                                            </p>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="space-y-1">
                                                                                        <p
                                                                                            class="text-sm text-neutral-light dark:text-neutral-dark">
                                                                                            Urgência</p>
                                                                                        <% String urgencyClass="" ;
                                                                                            String urgencyIcon="" ;
                                                                                            switch
                                                                                            (solicitation.getUrgencia())
                                                                                            { case "BAIXA" :
                                                                                            urgencyClass="bg-success/10 text-success border-success/20"
                                                                                            ;
                                                                                            urgencyIcon="arrow_downward"
                                                                                            ; break; case "MEDIA" :
                                                                                            urgencyClass="bg-alert/10 text-alert border-alert/20"
                                                                                            ; urgencyIcon="remove" ;
                                                                                            break; case "ALTA" :
                                                                                            urgencyClass="bg-urgent/10 text-urgent border-urgent/20"
                                                                                            ; urgencyIcon="arrow_upward"
                                                                                            ; break; default:
                                                                                            urgencyClass="bg-neutral-light/10 text-neutral-light border-neutral-light/20"
                                                                                            ; urgencyIcon="help" ; }
                                                                                            String urgencyDisplay="" ;
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
                                                                                            <span
                                                                                                class="status-badge <%= urgencyClass %>">
                                                                                                <span
                                                                                                    class="material-symbols-outlined text-sm">
                                                                                                    <%= urgencyIcon %>
                                                                                                </span>
                                                                                                <%= urgencyDisplay %>
                                                                                            </span>
                                                                                    </div>
                                                                                    <div class="space-y-1">
                                                                                        <p
                                                                                            class="text-sm text-neutral-light dark:text-neutral-dark">
                                                                                            Prioridade Calculada</p>
                                                                                        <span
                                                                                            class="status-badge bg-alert/10 text-alert border-alert/20">
                                                                                            <span
                                                                                                class="material-symbols-outlined text-sm">flag</span>
                                                                                            <%= calculatedPriority %>
                                                                                        </span>
                                                                                    </div>
                                                                                    <% if (solicitation.getObservacoes()
                                                                                        !=null &&
                                                                                        !solicitation.getObservacoes().isEmpty())
                                                                                        { %>
                                                                                        <div
                                                                                            class="md:col-span-2 space-y-1">
                                                                                            <p
                                                                                                class="text-sm text-neutral-light dark:text-neutral-dark">
                                                                                                Observações</p>
                                                                                            <div
                                                                                                class="bg-background-light dark:bg-background-dark p-4 rounded-lg border border-border-light dark:border-border-dark">
                                                                                                <p
                                                                                                    class="text-foreground-light dark:text-foreground-dark">
                                                                                                    <%= solicitation.getObservacoes()
                                                                                                        %>
                                                                                                </p>
                                                                                            </div>
                                                                                        </div>
                                                                                        <% } %>
                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Status History -->
                                                                        <div
                                                                            class="bg-content-light dark:bg-content-dark rounded-xl border border-border-light dark:border-border-dark shadow-card overflow-hidden">
                                                                            <div
                                                                                class="border-b border-border-light dark:border-border-dark px-6 py-4 bg-background-light dark:bg-background-dark">
                                                                                <h2
                                                                                    class="text-lg font-bold flex items-center gap-2">
                                                                                    <span
                                                                                        class="material-symbols-outlined text-primary">history</span>
                                                                                    Histórico de Status
                                                                                </h2>
                                                                            </div>
                                                                            <div class="p-6">
                                                                                <% if (history !=null &&
                                                                                    !history.isEmpty()) { %>
                                                                                    <div class="space-y-6">
                                                                                        <% for (StatusHistory item :
                                                                                            history) { String
                                                                                            itemStatusClass="" ; String
                                                                                            itemStatusIcon="" ; switch
                                                                                            (item.getStatus()) {
                                                                                            case "SOLICITADO" :
                                                                                            itemStatusClass="bg-alert/10 text-alert border-alert/20"
                                                                                            ; itemStatusIcon="pending" ;
                                                                                            break; case "VISUALIZADO" :
                                                                                            itemStatusClass="bg-primary/10 text-primary border-primary/20"
                                                                                            ;
                                                                                            itemStatusIcon="visibility"
                                                                                            ; break; case "EM_PRODUCAO"
                                                                                            :
                                                                                            itemStatusClass="bg-primary/10 text-primary border-primary/20"
                                                                                            ; itemStatusIcon="build" ;
                                                                                            break; case "CONCLUIDO" :
                                                                                            itemStatusClass="bg-success/10 text-success border-success/20"
                                                                                            ;
                                                                                            itemStatusIcon="check_circle"
                                                                                            ; break; default:
                                                                                            itemStatusClass="bg-neutral-light/10 text-neutral-light border-neutral-light/20"
                                                                                            ; itemStatusIcon="help" ; }
                                                                                            String itemStatusDisplay=""
                                                                                            ; switch (item.getStatus())
                                                                                            { case "SOLICITADO" :
                                                                                            itemStatusDisplay="Solicitado"
                                                                                            ; break; case "VISUALIZADO"
                                                                                            :
                                                                                            itemStatusDisplay="Visualizado"
                                                                                            ; break; case "EM_PRODUCAO"
                                                                                            :
                                                                                            itemStatusDisplay="Em Produção"
                                                                                            ; break; case "CONCLUIDO" :
                                                                                            itemStatusDisplay="Concluído"
                                                                                            ; break; default:
                                                                                            itemStatusDisplay=item.getStatus();
                                                                                            } %>
                                                                                            <div
                                                                                                class="timeline-item relative pl-12">
                                                                                                <div
                                                                                                    class="absolute left-0 top-0 w-10 h-10 rounded-full bg-content-light dark:bg-content-dark border border-border-light dark:border-border-dark flex items-center justify-center z-10">
                                                                                                    <span
                                                                                                        class="material-symbols-outlined text-sm <%= itemStatusClass.replace(" bg-", "text-").split(" ")[0] %>">
                                                                                                        <%= itemStatusIcon
                                                                                                            %>
                                                                                                    </span>
                                                                                                </div>
                                                                                                <div
                                                                                                    class="flex flex-col sm:flex-row sm:items-start sm:justify-between gap-2">
                                                                                                    <div>
                                                                                                        <span
                                                                                                            class="status-badge <%= itemStatusClass %> mb-2">
                                                                                                            <%= itemStatusDisplay
                                                                                                                %>
                                                                                                        </span>
                                                                                                        <p
                                                                                                            class="text-sm text-neutral-light dark:text-neutral-dark">
                                                                                                            <%= item.getResponsavel()
                                                                                                                !=null ?
                                                                                                                item.getResponsavel()
                                                                                                                : "Sistema"
                                                                                                                %>
                                                                                                        </p>
                                                                                                    </div>
                                                                                                    <p
                                                                                                        class="text-sm text-neutral-light dark:text-neutral-dark font-medium">
                                                                                                        <%= item.getDataHora()
                                                                                                            %>
                                                                                                    </p>
                                                                                                </div>
                                                                                            </div>
                                                                                            <% } %>
                                                                                    </div>
                                                                                    <% } else { %>
                                                                                        <div class="text-center py-8">
                                                                                            <span
                                                                                                class="material-symbols-outlined text-neutral-light dark:text-neutral-dark text-4xl mb-3">history</span>
                                                                                            <p
                                                                                                class="text-neutral-light dark:text-neutral-dark">
                                                                                                Nenhum histórico de
                                                                                                status encontrado.</p>
                                                                                        </div>
                                                                                        <% } %>
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <!-- Right Column - Admin Actions -->
                                                                    <div class="space-y-8">
                                                                        <% if (isAdmin) { %>
                                                                            <!-- Status Update Form -->
                                                                            <div
                                                                                class="bg-content-light dark:bg-content-dark rounded-xl border border-border-light dark:border-border-dark shadow-card overflow-hidden">
                                                                                <div
                                                                                    class="border-b border-border-light dark:border-border-dark px-6 py-4 bg-background-light dark:bg-background-dark">
                                                                                    <h2
                                                                                        class="text-lg font-bold flex items-center gap-2">
                                                                                        <span
                                                                                            class="material-symbols-outlined text-primary">update</span>
                                                                                        Atualizar Status
                                                                                    </h2>
                                                                                </div>
                                                                                <form class="p-6 space-y-6"
                                                                                    method="POST"
                                                                                    action="solicitation_details">
                                                                                    <input type="hidden"
                                                                                        name="solicitationId"
                                                                                        value="<%= solicitation.getId() %>">
                                                                                    <div>
                                                                                        <label
                                                                                            class="block text-sm font-medium mb-2"
                                                                                            for="newStatus">Novo
                                                                                            Status</label>
                                                                                        <select
                                                                                            class="w-full rounded-lg border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark py-3 px-4 text-foreground-light dark:text-foreground-dark focus:border-primary focus:ring-primary transition-colors"
                                                                                            id="newStatus"
                                                                                            name="newStatus">
                                                                                            <option value="SOLICITADO">
                                                                                                Solicitado</option>
                                                                                            <option value="VISUALIZADO">
                                                                                                Visualizado</option>
                                                                                            <option value="EM_PRODUCAO">
                                                                                                Em Produção</option>
                                                                                            <option value="CONCLUIDO">
                                                                                                Concluído</option>
                                                                                        </select>
                                                                                    </div>
                                                                                    <div>
                                                                                        <label
                                                                                            class="block text-sm font-medium mb-2"
                                                                                            for="observations">Observações</label>
                                                                                        <textarea
                                                                                            class="w-full rounded-lg border-border-light dark:border-border-dark bg-background-light dark:bg-background-dark py-3 px-4 text-foreground-light dark:text-foreground-dark focus:border-primary focus:ring-primary transition-colors"
                                                                                            id="observations"
                                                                                            name="observations"
                                                                                            placeholder="Adicione observações sobre a atualização de status"
                                                                                            rows="4"></textarea>
                                                                                    </div>
                                                                                    <button
                                                                                        class="w-full btn-primary justify-center"
                                                                                        type="submit">
                                                                                        <span
                                                                                            class="material-symbols-outlined text-lg">update</span>
                                                                                        Atualizar Status
                                                                                    </button>
                                                                                </form>
                                                                            </div>
                                                                            <% } %>

                                                                                <!-- Quick Actions -->
                                                                                <div
                                                                                    class="bg-content-light dark:bg-content-dark rounded-xl border border-border-light dark:border-border-dark shadow-card overflow-hidden">
                                                                                    <div
                                                                                        class="border-b border-border-light dark:border-border-dark px-6 py-4 bg-background-light dark:bg-background-dark">
                                                                                        <h2
                                                                                            class="text-lg font-bold flex items-center gap-2">
                                                                                            <span
                                                                                                class="material-symbols-outlined text-primary">bolt</span>
                                                                                            Ações Rápidas
                                                                                        </h2>
                                                                                    </div>
                                                                                    <div class="p-6 space-y-4">
                                                                                        <a href="#"
                                                                                            class="flex items-center gap-3 p-3 rounded-lg border border-border-light dark:border-border-dark hover:bg-background-light dark:hover:bg-background-dark transition-colors">
                                                                                            <span
                                                                                                class="material-symbols-outlined text-primary">download</span>
                                                                                            <span
                                                                                                class="font-medium">Baixar
                                                                                                Documento</span>
                                                                                        </a>
                                                                                        <a href="#"
                                                                                            class="flex items-center gap-3 p-3 rounded-lg border border-border-light dark:border-border-dark hover:bg-background-light dark:hover:bg-background-dark transition-colors">
                                                                                            <span
                                                                                                class="material-symbols-outlined text-primary">print</span>
                                                                                            <span
                                                                                                class="font-medium">Imprimir
                                                                                                Detalhes</span>
                                                                                        </a>
                                                                                        <% if (isAdmin) { %>
                                                                                            <a href="#"
                                                                                                class="flex items-center gap-3 p-3 rounded-lg border border-border-light dark:border-border-dark hover:bg-background-light dark:hover:bg-background-dark transition-colors">
                                                                                                <span
                                                                                                    class="material-symbols-outlined text-primary">share</span>
                                                                                                <span
                                                                                                    class="font-medium">Compartilhar</span>
                                                                                            </a>
                                                                                            <% } %>
                                                                                    </div>
                                                                                </div>

                                                                                <!-- Information Card -->
                                                                                <div
                                                                                    class="bg-blue-50 dark:bg-blue-900/20 rounded-xl border border-blue-200 dark:border-blue-800 p-6">
                                                                                    <div class="flex items-start gap-3">
                                                                                        <span
                                                                                            class="material-symbols-outlined text-blue-500 text-xl mt-0.5">info</span>
                                                                                        <div>
                                                                                            <h3
                                                                                                class="font-bold text-blue-800 dark:text-blue-300 mb-2">
                                                                                                Informações</h3>
                                                                                            <p
                                                                                                class="text-blue-700 dark:text-blue-400 text-sm">
                                                                                                O status da solicitação
                                                                                                será atualizado conforme
                                                                                                o andamento do
                                                                                                processamento.
                                                                                                <% if (!isAdmin) { %>
                                                                                                    Você receberá uma
                                                                                                    notificação quando
                                                                                                    houver alterações.
                                                                                                    <% } %>
                                                                                            </p>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <% } %>
                                                </div>
                                            </main>
                                        </div>

                                        <script>
                                            // Add some interactivity
                                            document.addEventListener('DOMContentLoaded', function () {
                                                // Update form submission with loading state
                                                const updateForm = document.querySelector('form');
                                                if (updateForm) {
                                                    updateForm.addEventListener('submit', function () {
                                                        const submitButton = this.querySelector('button[type="submit"]');
                                                        if (submitButton) {
                                                            submitButton.disabled = true;
                                                            submitButton.innerHTML = `
                        <svg class="animate-spin -ml-1 mr-2 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                        </svg>
                        Processando...
                    `;
                                                        }
                                                    });
                                                }

                                                // Set current status as selected in the form
                                                const currentStatus = '<%= solicitation != null ? solicitation.getStatus() : "" %>';
                                                const statusSelect = document.getElementById('newStatus');
                                                if (statusSelect && currentStatus) {
                                                    statusSelect.value = currentStatus;
                                                }
                                            });
                                        </script>
                                    </body>

                                    </html>