<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="utf-8" />
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <title>FAETERJ-RIO - Sistema de Solicitacao de Documentos</title>
    <script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
    <link href="https://fonts.googleapis.com" rel="preconnect" />
    <link crossorigin="" href="https://fonts.gstatic.com" rel="preconnect" />
    <link href="https://fonts.googleapis.com/css2?family=Public+Sans:wght@300;400;500;600;700&display=swap"
        rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
    <script id="tailwind-config">
        tailwind.config = {
            darkMode: "class",
            theme: {
                extend: {
                    colors: {
                        "primary": "#005aa3",
                        "primary-dark": "#004a8a",
                        "secondary": "#f8a31c",
                        "background-light": "#f8fafc",
                        "background-dark": "#0f172a",
                        "foreground-light": "#1e293b",
                        "foreground-dark": "#f1f5f9",
                        "neutral-light": "#64748b",
                        "neutral-dark": "#94a3b8",
                        "border-light": "#e2e8f0",
                        "border-dark": "#334155",
                        "error": "#dc2626",
                        "success": "#16a34a"
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
                        'soft': '0 4px 6px -1px rgb(0 0 0 / 0.05), 0 2px 4px -2px rgb(0 0 0 / 0.05)',
                        'card': '0 10px 15px -3px rgb(0 0 0 / 0.05), 0 4px 6px -4px rgb(0 0 0 / 0.05)'
                    },
                    animation: {
                        'fade-in': 'fadeIn 0.5s ease-in-out',
                        'slide-up': 'slideUp 0.5s ease-out',
                        'pulse-subtle': 'pulseSubtle 2s infinite'
                    },
                    keyframes: {
                        fadeIn: {
                            '0%': { opacity: '0' },
                            '100%': { opacity: '1' }
                        },
                        slideUp: {
                            '0%': { transform: 'translateY(10px)', opacity: '0' },
                            '100%': { transform: 'translateY(0)', opacity: '1' }
                        },
                        pulseSubtle: {
                            '0%, 100%': { opacity: '1' },
                            '50%': { opacity: '0.8' }
                        }
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

        body {
            font-family: 'Public Sans', sans-serif;
        }

        .gradient-bg {
            background: linear-gradient(135deg, #005aa3 0%, #003a6b 100%);
        }

        .input-focus:focus {
            box-shadow: 0 0 0 3px rgba(0, 90, 163, 0.1);
        }

        .btn-hover:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px -3px rgba(0, 90, 163, 0.3);
        }

        .btn-hover:active {
            transform: translateY(0);
        }

        .floating-label {
            transform: translateY(-50%);
            transition: all 0.2s ease-out;
        }

        input:focus+.floating-label,
        input:not(:placeholder-shown)+.floating-label {
            top: 0;
            font-size: 0.75rem;
            color: #005aa3;
        }

        .dark input:focus+.floating-label,
        .dark input:not(:placeholder-shown)+.floating-label {
            color: #60a5fa;
        }
    </style>
</head>

<body class="bg-background-light dark:bg-background-dark font-display">
    <div class="min-h-screen flex flex-col md:flex-row">
        <!-- Left Panel with Branding -->
        <div class="gradient-bg text-white md:w-1/2 flex flex-col justify-between p-8 md:p-12">
            <div class="flex items-center space-x-3">
                <div class="w-10 h-10 bg-white/20 rounded-lg flex items-center justify-center">
                    <span class="material-symbols-outlined text-white">school</span>
                </div>
                <span class="text-xl font-bold">FAETERJ-RIO</span>
            </div>

            <div class="max-w-md mx-auto md:mx-0 my-auto text-center md:text-left animate-fade-in">
                <h1 class="text-3xl md:text-4xl font-bold mb-4">Sistema de Solicitacao de Documentos</h1>
                <p class="text-lg opacity-90 mb-8">Acesse sua conta para gerenciar solicitacoes de documentos academicos
                    de forma rapida e segura.</p>

                <div class="space-y-4">
                    <div class="flex items-center space-x-3">
                        <span class="material-symbols-outlined text-secondary">check_circle</span>
                        <span>Solicitacao online de documentos</span>
                    </div>
                    <div class="flex items-center space-x-3">
                        <span class="material-symbols-outlined text-secondary">check_circle</span>
                        <span>Acompanhamento em tempo real</span>
                    </div>
                    <div class="flex items-center space-x-3">
                        <span class="material-symbols-outlined text-secondary">check_circle</span>
                        <span>Processo seguro e eficiente</span>
                    </div>
                </div>
            </div>

            <div class="text-center md:text-left text-sm opacity-80">
                <p>© 2023 FAETERJ-RIO. Todos os direitos reservados.</p>
            </div>
        </div>

        <!-- Right Panel with Login Form -->
        <div class="md:w-1/2 flex items-center justify-center p-8">
            <div class="w-full max-w-md space-y-8 animate-slide-up">
                <!-- Logo for mobile -->
                <div class="text-center md:hidden">
                    <div class="flex items-center justify-center space-x-3 mb-4">
                        <div class="w-12 h-12 bg-primary rounded-xl flex items-center justify-center">
                            <span class="material-symbols-outlined text-white">school</span>
                        </div>
                        <span
                            class="text-xl font-bold text-foreground-light dark:text-foreground-dark">FAETERJ-RIO</span>
                    </div>
                    <h2 class="text-xl font-bold text-foreground-light dark:text-foreground-dark">
                        Sistema de Solicitacao de Documentos
                    </h2>
                </div>

                <div
                    class="bg-white dark:bg-foreground-light border border-border-light dark:border-border-dark rounded-xl p-8 shadow-card">
                    <div class="text-center mb-8">
                        <h3 class="text-2xl font-bold text-foreground-light dark:text-foreground-dark">Acesse sua conta
                        </h3>
                        <p class="text-neutral-light dark:text-neutral-dark mt-2">
                            Entre com suas credenciais para continuar
                        </p>
                    </div>

                    <% String error=(String) request.getAttribute("error"); %>
                        <% if (error !=null) { %>
                            <div class="bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 text-red-700 dark:text-red-300 px-4 py-3 rounded-lg mb-6 flex items-start space-x-2"
                                role="alert">
                                <span class="material-symbols-outlined text-red-500 flex-shrink-0 mt-0.5">error</span>
                                <div>
                                    <p class="font-medium">Erro de autenticação</p>
                                    <p class="text-sm mt-1">
                                        <%= error %>
                                    </p>
                                </div>
                            </div>
                            <% } %>

                                <form action="login" class="space-y-6" method="POST">
                                    <div class="relative">
                                        <input autocomplete="email"
                                            class="w-full rounded-lg border border-border-light dark:border-border-dark bg-white dark:bg-background-dark py-3 pl-11 pr-4 text-foreground-light dark:text-foreground-dark placeholder-transparent focus:border-primary focus:ring-0 input-focus transition-colors"
                                            id="email" name="email" placeholder="Email" required="" type="email" />
                                        <span
                                            class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-neutral-light dark:text-neutral-dark">mail</span>
                                        
                                    </div>

                                    <div class="relative">
                                        <input autocomplete="current-password"
                                            class="w-full rounded-lg border border-border-light dark:border-border-dark bg-white dark:bg-background-dark py-3 pl-11 pr-4 text-foreground-light dark:text-foreground-dark placeholder-transparent focus:border-primary focus:ring-0 input-focus transition-colors"
                                            id="senha" name="senha" placeholder="Senha" required="" type="password" />
                                        <span
                                            class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-neutral-light dark:text-neutral-dark">lock</span>
                                    </div>

                                    <div class="flex items-center justify-between">
                                        <div class="flex items-center">
                                            <input id="remember-me" name="remember-me" type="checkbox"
                                                class="h-4 w-4 rounded border-border-light dark:border-border-dark text-primary focus:ring-primary" />
                                            <label for="remember-me"
                                                class="ml-2 block text-sm text-neutral-light dark:text-neutral-dark">
                                                Lembrar-me
                                            </label>
                                        </div>

                                        <div class="text-sm">
                                            <a href="#"
                                                class="font-medium text-primary hover:text-primary-dark transition-colors">
                                                Esqueceu sua senha?
                                            </a>
                                        </div>
                                    </div>

                                    <div>
                                        <button
                                            class="w-full rounded-lg bg-primary px-4 py-3 text-sm font-semibold text-white shadow-soft hover:bg-primary-dark btn-hover transition-all duration-300 flex items-center justify-center"
                                            type="submit">
                                            <span class="material-symbols-outlined mr-2 text-lg">login</span>
                                            ENTRAR
                                        </button>
                                    </div>
                                </form>

                                <div class="mt-8 text-center">
                                    <p class="text-sm text-neutral-light dark:text-neutral-dark">
                                        Problemas para acessar?
                                        <a href="#"
                                            class="font-medium text-primary hover:text-primary-dark transition-colors">
                                            Contate o suporte
                                        </a>
                                    </p>
                                </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Simple form enhancement
        document.addEventListener('DOMContentLoaded', function () {
            const form = document.querySelector('form');
            const submitButton = form.querySelector('button[type="submit"]');

            form.addEventListener('submit', function () {
                // Add loading state to button
                submitButton.disabled = true;
                submitButton.innerHTML = `
                    <svg class="animate-spin -ml-1 mr-2 h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                    </svg>
                    Processando...
                `;
            });

            // Toggle password visibility (optional enhancement)
            const passwordInput = document.getElementById('senha');
            const passwordToggle = document.createElement('span');
            passwordToggle.className = 'material-symbols-outlined absolute right-3 top-1/2 -translate-y-1/2 text-neutral-light dark:text-neutral-dark cursor-pointer';
            passwordToggle.textContent = 'visibility';
            passwordToggle.addEventListener('click', function () {
                if (passwordInput.type === 'password') {
                    passwordInput.type = 'text';
                    passwordToggle.textContent = 'visibility_off';
                } else {
                    passwordInput.type = 'password';
                    passwordToggle.textContent = 'visibility';
                }
            });

            passwordInput.parentNode.appendChild(passwordToggle);
        });
    </script>
</body>

</html>