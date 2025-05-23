class Header extends HTMLElement{
    connectedCallback(){
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
        </style>
        <header>
            <button class="sidebar-toggle" id="sidebarToggle">
                <i class="fas fa-bars"></i>
            </button>
            <div class="logo-container">
                <img src="/api/placeholder/40/40" alt="Pata de Animal" class="paw-logo">
                <div class="title">S.O.S. Amigos de Animais</div>
            </div>
            <div class="header-icons">
                <a href="home.html" class="home-icon" title="Página Inicial">
                    <i class="fas fa-home fa-lg"></i>
                </a>
                <i class="fas fa-bell notification-icon fa-lg" title="Notificações"></i>
                <div class="profile-icon" title="Perfil do Usuário">
                    <i class="fas fa-user fa-lg"></i>
                </div>
            </div>
        </header> `;
    }
}

customElements.define('main-header',Header);