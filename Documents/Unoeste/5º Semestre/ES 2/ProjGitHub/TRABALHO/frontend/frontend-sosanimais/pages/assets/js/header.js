class Header extends HTMLElement{
    connectedCallback(){
        // Verificar autenticação
        const token = localStorage.getItem('token');
        if (!token) {
            window.location.href = '/frontend/frontend-sosanimais/pages/pageTelaLogin.html';
            return;
        }

        this.innerHTML = `
        <style>
        /* Estilos adicionais para o ícone de home */
        .home-icon {
            color: white;
            text-decoration: none;
            margin-right: 20px;
            cursor: pointer;
            transition: color 0.3s ease, transform 0.2s ease;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .home-icon:hover {
            color: #f0f0f0;
            transform: scale(1.1);
        }

        .home-icon:active {
            transform: scale(0.95);
        }

        /* Ajuste para manter o espaçamento consistente */
        .header-icons {
            display: flex;
            align-items: center;
        }

        .notification-icon {
            margin-left: 20px;
            margin-right: 20px;
            cursor: pointer;
            transition: color 0.3s ease, transform 0.2s ease;
        }

        .notification-icon:hover {
            color: #f0f0f0;
            transform: scale(1.1);
        }

        .profile-icon {
            background-color: #e8e8e8;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: var(--color-brown-dark);
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .profile-icon:hover {
            background-color: #d0d0d0;
            transform: scale(1.05);
        }

        /* Responsividade para telas menores */
        @media (max-width: 768px) {
            .home-icon {
                margin-right: 15px;
            }
            
            .notification-icon {
                margin-left: 15px;
                margin-right: 15px;
            }
        }

        @media (max-width: 480px) {
            .home-icon {
                margin-right: 10px;
            }
            
            .notification-icon {
                margin-left: 10px;
                margin-right: 10px;
            }
            
            .home-icon i,
            .notification-icon {
                font-size: 1.1rem;
            }
        }

        /* Estilos para o menu dropdown do perfil */
        .profile-dropdown {
            position: absolute;
            top: 100%;
            right: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            display: none;
            z-index: 1000;
        }

        .profile-dropdown.active {
            display: block;
        }

        .profile-dropdown-item {
            padding: 12px 20px;
            display: flex;
            align-items: center;
            color: var(--color-brown-dark);
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .profile-dropdown-item:hover {
            background-color: #f5f5f5;
        }

        .profile-dropdown-item i {
            margin-right: 10px;
            width: 20px;
            text-align: center;
        }

        /* Estilos para o menu de notificações */
        .notifications-dropdown {
            position: absolute;
            top: 100%;
            right: 100px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            display: none;
            z-index: 1000;
            width: 300px;
            max-height: 400px;
            overflow-y: auto;
        }

        .notifications-dropdown.active {
            display: block;
        }

        .notification-item {
            padding: 12px 20px;
            border-bottom: 1px solid #eee;
            display: flex;
            align-items: center;
        }

        .notification-item:last-child {
            border-bottom: none;
        }

        .notification-item i {
            margin-right: 10px;
            color: var(--color-orange-burnt);
        }

        .notification-content {
            flex: 1;
        }

        .notification-title {
            font-weight: bold;
            margin-bottom: 4px;
        }

        .notification-time {
            font-size: 0.8em;
            color: #666;
        }
        </style>
        <header>
            <button class="sidebar-toggle" id="sidebarToggle">
                <i class="fas fa-bars"></i>
            </button>
            <div class="logo-container">
                <img src="../assets/img/paw-logo.png" alt="Pata de Animal" class="paw-logo">
                <div class="title">S.O.S. Amigos de Animais</div>
            </div>
            <div class="header-icons">
                <a href="../home.html" class="home-icon" title="Página Inicial">
                    <i class="fas fa-home fa-lg"></i>
                </a>
                <div class="notification-container">
                    <i class="fas fa-bell notification-icon fa-lg" title="Notificações"></i>
                    <div class="notifications-dropdown">
                        <div class="notification-item">
                            <i class="fas fa-info-circle"></i>
                            <div class="notification-content">
                                <div class="notification-title">Nenhuma notificação</div>
                                <div class="notification-time">Você está em dia!</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="profile-container">
                    <div class="profile-icon" title="Perfil do Usuário">
                        <i class="fas fa-user fa-lg"></i>
                    </div>
                    <div class="profile-dropdown">
                        <a href="#" class="profile-dropdown-item">
                            <i class="fas fa-user-cog"></i>
                            Meu Perfil
                        </a>
                        <a href="#" class="profile-dropdown-item">
                            <i class="fas fa-cog"></i>
                            Configurações
                        </a>
                        <a href="#" class="profile-dropdown-item" id="logoutButton">
                            <i class="fas fa-sign-out-alt"></i>
                            Sair
                        </a>
                    </div>
                </div>
            </div>
        </header>`;

        // Adicionar eventos
        const sidebarToggle = this.querySelector('#sidebarToggle');
        const profileIcon = this.querySelector('.profile-icon');
        const profileDropdown = this.querySelector('.profile-dropdown');
        const notificationIcon = this.querySelector('.notification-icon');
        const notificationsDropdown = this.querySelector('.notifications-dropdown');
        const logoutButton = this.querySelector('#logoutButton');

        // Toggle da sidebar
        sidebarToggle.addEventListener('click', () => {
            const sidebar = document.querySelector('.sidebar');
            if (sidebar) {
                sidebar.classList.toggle('active');
            }
        });

        // Toggle do menu de perfil
        profileIcon.addEventListener('click', (e) => {
            e.stopPropagation();
            profileDropdown.classList.toggle('active');
            notificationsDropdown.classList.remove('active');
        });

        // Toggle do menu de notificações
        notificationIcon.addEventListener('click', (e) => {
            e.stopPropagation();
            notificationsDropdown.classList.toggle('active');
            profileDropdown.classList.remove('active');
        });

        // Fechar dropdowns ao clicar fora
        document.addEventListener('click', (e) => {
            if (!e.target.closest('.profile-container')) {
                profileDropdown.classList.remove('active');
            }
            if (!e.target.closest('.notification-container')) {
                notificationsDropdown.classList.remove('active');
            }
        });

        // Logout
        logoutButton.addEventListener('click', (e) => {
            e.preventDefault();
            localStorage.removeItem('token');
            window.location.href = '/frontend/frontend-sosanimais/pages/pageTelaLogin.html';
        });
    }
}

customElements.define('main-header',Header);