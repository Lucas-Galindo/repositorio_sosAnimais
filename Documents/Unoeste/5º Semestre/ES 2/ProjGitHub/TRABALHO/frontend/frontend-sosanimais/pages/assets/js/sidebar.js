class SideBar extends HTMLElement {
    connectedCallback() {
        // Verificar autenticação
        const token = localStorage.getItem('token');
        if (!token) {
            window.location.href = '/frontend/frontend-sosanimais/pages/pageTelaLogin.html';
            return;
        }

        this.innerHTML = `
            <aside class="sidebar" id="sidebar">
                <div class="menu-item">
                    <button class="menu-button">
                        <div class="menu-button-content">
                            <i class="fas fa-home menu-icon"></i>
                            Dashboard
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-chart-line"></i>
                            Estatísticas
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-calendar-alt"></i>
                            Calendário
                        </a>
                    </div>
                </div>
                
                <div class="menu-item"> <button class="menu-button">
                        <div class="menu-button-content">
                            <i class="fas fa-paw menu-icon"></i>
                            Animais
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-list"></i>
                            Listar todos
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-plus-circle"></i>
                            Cadastrar novo
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-search"></i>
                            Buscar animal
                        </a>
                    </div>
                </div>

                <div class="menu-item">
                    <button class="menu-button">
                        <div class="menu-button-content">
                            <span class="menu-icon"> <i class="fas fa-user-friends"></i><i class="fas fa-paw" style="margin-left: 4px;"></i> </span>
                            Adoção
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="/frontend/frontend-sosanimais/pages/adocao/pageCadastroAdocao.html" class="dropdown-item">
                            <i class="fas fa-plus-circle"></i>
                            Cadastrar Adoção
                        </a>
                        <a href="/frontend/frontend-sosanimais/pages/adocao/pageBuscaAdocao.html" class="dropdown-item">
                            <i class="fas fa-search"></i>
                            Buscar Adoção
                        </a>
                        <a href="/frontend/frontend-sosanimais/pages/adocao/pageListarAdocao.html" class="dropdown-item">
                            <i class="fas fa-list"></i>
                            Listar Adoções
                        </a>
                    </div>
                </div>
                <div class="menu-item">
                    <button class="menu-button">
                        <div class="menu-button-content">
                            <i class="fas fa-user-friends menu-icon"></i>
                            Pessoas
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="../../pages/pessoa/pageListarPessoa.html" class="dropdown-item">
                            <i class="fas fa-list"></i>
                            Listar todas
                        </a>
                        <a href="../../pages/pessoa/pageCadastroPessoa.html" class="dropdown-item">
                            <i class="fas fa-plus-circle"></i>
                            Cadastrar nova
                        </a>
                        <a href="../../pages/pessoa/pageBuscaPessoa.html" class="dropdown-item">
                            <i class="fas fa-search"></i>
                            Buscar pessoa
                        </a>
                    </div>
                </div>
                
                <div class="menu-item">
                    <button class="menu-button">
                        <div class="menu-button-content">
                            <i class="fas fa-warehouse menu-icon"></i>
                            Armazenamento
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-list"></i>
                            Listar todos
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-plus-circle"></i>
                            Cadastrar novo
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-search"></i>
                            Buscar armazenamento
                        </a>
                    </div>
                </div>
                
                <div class="menu-item">
                    <button class="menu-button">
                        <div class="menu-button-content">
                            <i class="fas fa-exchange-alt menu-icon"></i>
                            Transferência
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="../pages/transfere/pageTransfere.html" class="dropdown-item">
                            <i class="fas fa-plus-circle"></i>
                            Cadastrar nova
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-search"></i>
                            Buscar transferência
                        </a>
                    </div>
                </div>
                
                <div class="menu-item">
                    <button class="menu-button">
                        <div class="menu-button-content">
                            <i class="fas fa-cube menu-icon"></i>
                            Produtos
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-list"></i>
                            Listar todos
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-plus-circle"></i>
                            Cadastrar novo
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-search"></i>
                            Buscar produto
                        </a>
                    </div>
                </div>
                
                <div class="menu-item">
                    <button class="menu-button">
                        <div class="menu-button-content">
                            <i class="fas fa-door-open menu-icon"></i>
                            Baias
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="../baias/pageCadastroBaia.html" class="dropdown-item">
                            <i class="fas fa-plus-circle"></i>
                            Cadastrar nova
                        </a>
                        <a href="../baias/pageBuscaBaia.html" class="dropdown-item">
                            <i class="fas fa-search"></i>
                            Buscar baia
                        </a>
                    </div>
                </div>
                
                <div class="menu-item">
                    <button class="menu-button">
                        <div class="menu-button-content">
                            <i class="fas fa-heart menu-icon"></i>
                            Doações
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-hand-holding-heart"></i>
                            Receber doação
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-history"></i>
                            Histórico
                        </a>
                    </div>
                </div>
                
                <div class="menu-item">
                    <button class="menu-button">
                        <div class="menu-button-content">
                            <i class="fas fa-file-alt menu-icon"></i>
                            Relatórios
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-paw"></i>
                            Animais
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-home"></i>
                            Adoções
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-heart"></i>
                            Doações
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-user-friends"></i>
                            Voluntários
                        </a>
                    </div>
                </div>
                
                <div class="menu-item">
                    <button class="menu-button">
                        <div class="menu-button-content">
                            <i class="fas fa-cog menu-icon"></i>
                            Configurações
                        </div>
                        <i class="fas fa-chevron-down dropdown-icon"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-user-cog"></i>
                            Perfil
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-users-cog"></i>
                            Usuários
                        </a>
                        <a href="#" class="dropdown-item">
                            <i class="fas fa-sign-out-alt"></i>
                            Sair
                        </a>
                    </div>
                </div>
            </aside>
        `;

        // Adicionar eventos de toggle para os menus após criar o componente
        setTimeout(() => {
            const menuButtons = this.querySelectorAll('.menu-button');
            menuButtons.forEach(button => {
                button.addEventListener('click', () => {
                    const dropdownMenu = button.nextElementSibling;
                    const dropdownIcon = button.querySelector('.dropdown-icon');
                    
                    // Toggle do menu atual
                    dropdownMenu.classList.toggle('active');
                    dropdownIcon.classList.toggle('active');

                    // Fecha outros menus
                    menuButtons.forEach(otherButton => {
                        if (otherButton !== button) {
                            const otherMenu = otherButton.nextElementSibling;
                            const otherIcon = otherButton.querySelector('.dropdown-icon');
                            otherMenu.classList.remove('active');
                            otherIcon.classList.remove('active');
                        }
                    });
                });
            });

            // Adicionar evento de toggle para a sidebar em telas pequenas
            const sidebarToggle = document.querySelector('.sidebar-toggle');
            if (sidebarToggle) {
                sidebarToggle.addEventListener('click', () => {
                    const sidebar = this.querySelector('.sidebar');
                    sidebar.classList.toggle('active');
                });
            }

            // Adicionar evento de logout
            const logoutButton = this.querySelector('a[href="#"]:last-child');
            if (logoutButton) {
                logoutButton.addEventListener('click', (e) => {
                    e.preventDefault();
                    localStorage.removeItem('token');
                    window.location.href = '../auth/pageTelaLogin.html';
                });
            }
        }, 0);
    }
}

customElements.define('main-sidebar', SideBar);