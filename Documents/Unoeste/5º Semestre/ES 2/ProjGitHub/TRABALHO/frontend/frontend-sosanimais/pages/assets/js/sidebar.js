class SideBar extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: 'open' });
    }

    connectedCallback() {
        // Verificar autenticação
        const token = localStorage.getItem('token');
        if (!token) {
            window.location.href = '/frontend/frontend-sosanimais/pages/pageTelaLogin.html';
            return;
        }

        // Carregar o CSS
        const linkElem = document.createElement('link');
        linkElem.setAttribute('rel', 'stylesheet');
        linkElem.setAttribute('href', '/frontend/frontend-sosanimais/pages/assets/css/sidebar.css');
        this.shadowRoot.appendChild(linkElem);

        // Carregar o Font Awesome
        const fontAwesome = document.createElement('link');
        fontAwesome.setAttribute('rel', 'stylesheet');
        fontAwesome.setAttribute('href', 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css');
        this.shadowRoot.appendChild(fontAwesome);

        // Adicionar o HTML da sidebar
        this.shadowRoot.innerHTML += `
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
            
            <div class="menu-item">
                <button class="menu-button">
                    <div class="menu-button-content">
                        <i class="fas fa-paw menu-icon"></i>
                        Animais
                    </div>
                    <i class="fas fa-chevron-down dropdown-icon"></i>
                </button>
                <div class="dropdown-menu">
                    <a href="/frontend/frontend-sosanimais/pages/animal/pageListarAnimal.html" class="dropdown-item">
                        <i class="fas fa-list"></i>
                        Listar todos
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/animal/pageCadastroAnimal.html" class="dropdown-item">
                        <i class="fas fa-plus-circle"></i>
                        Cadastrar novo
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/animal/pageBuscaAnimal.html" class="dropdown-item">
                        <i class="fas fa-search"></i>
                        Buscar animal
                    </a>
                </div>
            </div>

            <div class="menu-item">
                <button class="menu-button">
                    <div class="menu-button-content">
                        <i class="fas fa-home menu-icon"></i>
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
                        <i class="fas fa-shopping-cart menu-icon"></i>
                        Compras
                    </div>
                    <i class="fas fa-chevron-down dropdown-icon"></i>
                </button>
                <div class="dropdown-menu">
                    <a href="/frontend/frontend-sosanimais/pages/compra/pageCadastroCompra.html" class="dropdown-item">
                        <i class="fas fa-plus-circle"></i>
                        Cadastrar Compra
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/compra/pageBuscaCompra.html" class="dropdown-item">
                        <i class="fas fa-search"></i>
                        Buscar Compra
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/compra/pageListarCompra.html" class="dropdown-item">
                        <i class="fas fa-list"></i>
                        Listar Compras
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
                    <a href="/frontend/frontend-sosanimais/pages/pessoa/pageListarPessoa.html" class="dropdown-item">
                        <i class="fas fa-list"></i>
                        Listar todas
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/pessoa/pageCadastroPessoa.html" class="dropdown-item">
                        <i class="fas fa-plus-circle"></i>
                        Cadastrar nova
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/pessoa/pageBuscaPessoa.html" class="dropdown-item">
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
                    <a href="/frontend/frontend-sosanimais/pages/transfere/pageTransfere.html" class="dropdown-item">
                        <i class="fas fa-plus-circle"></i>
                        Cadastrar nova
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/transfere/pageListarTransfere.html" class="dropdown-item">
                        <i class="fas fa-list"></i>
                        Listar todos
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
                        <i class="fas fa-box menu-icon"></i>
                        Insumos
                    </div>
                    <i class="fas fa-chevron-down dropdown-icon"></i>
                </button>
                <div class="dropdown-menu">
                    <a href="/frontend/frontend-sosanimais/pages/Categoria/pageRelatorioCategoria.html" class="dropdown-item">
                        <i class="fas fa-list"></i>
                        Listar todos
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/Categoria/pageCadastroCategoria.html" class="dropdown-item">
                        <i class="fas fa-plus-circle"></i>
                        Cadastrar novo
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/Categoria/pageExcluirCategoria.html" class="dropdown-item">
                        <i class="fas fa-trash"></i>
                        Excluir insumo
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/Categoria/pageAlterarCategoria.html" class="dropdown-item">
                        <i class="fas fa-edit"></i>
                        Alterar insumo
                    </a>
                </div>
            </div>

            <div class="menu-item">
                <button class="menu-button">
                    <div class="menu-button-content">
                        <i class="fas fa-tags menu-icon"></i>
                        Categoria
                    </div>
                    <i class="fas fa-chevron-down dropdown-icon"></i>
                </button>
                <div class="dropdown-menu">
                    <a href="/frontend/frontend-sosanimais/pages/Categoria/pageRelatorioCategoria.html" class="dropdown-item">
                        <i class="fas fa-list"></i>
                        Listar todos
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/Categoria/pageCadastroCategoria.html" class="dropdown-item">
                        <i class="fas fa-plus-circle"></i>
                        Cadastrar novo
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/Categoria/pageExcluirCategoria.html" class="dropdown-item">
                        <i class="fas fa-trash"></i>
                        Excluir Categoria
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/Categoria/pageAlterarCategoria.html" class="dropdown-item">
                        <i class="fas fa-edit"></i>
                        Alterar categoria
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
                    <a href="/frontend/frontend-sosanimais/pages/baias/pageCadastroBaia.html" class="dropdown-item">
                        <i class="fas fa-plus-circle"></i>
                        Cadastrar nova
                    </a>
                    <a href="/frontend/frontend-sosanimais/pages/baias/pageBuscaBaia.html" class="dropdown-item">
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
                    <a href="#" class="dropdown-item" id="logoutButton">
                        <i class="fas fa-sign-out-alt"></i>
                        Sair
                    </a>
                </div>
            </div>
        </aside>`;

        // Adicionar eventos após o HTML estar pronto
        this.addEventListeners();
    }

    addEventListeners() {
        // Eventos dos menus dropdown
        const menuButtons = this.shadowRoot.querySelectorAll('.menu-button');
        menuButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                e.preventDefault();
                const dropdownMenu = button.nextElementSibling;
                const dropdownIcon = button.querySelector('.dropdown-icon');

                // Fechar outros menus
                menuButtons.forEach(otherButton => {
                    if (otherButton !== button) {
                        const otherMenu = otherButton.nextElementSibling;
                        const otherIcon = otherButton.querySelector('.dropdown-icon');
                        if (otherMenu) otherMenu.classList.remove('active');
                        if (otherIcon) otherIcon.classList.remove('active');
                    }
                });

                // Toggle do menu atual
                if (dropdownMenu) dropdownMenu.classList.toggle('active');
                if (dropdownIcon) dropdownIcon.classList.toggle('active');
            });
        });

        // Evento de logout
        const logoutButton = this.shadowRoot.querySelector('#logoutButton');
        if (logoutButton) {
            logoutButton.addEventListener('click', (e) => {
                e.preventDefault();
                localStorage.removeItem('token');
                window.location.href = '/frontend/frontend-sosanimais/pages/pageTelaLogin.html';
            });
        }
    }
}

customElements.define('main-sidebar', SideBar);