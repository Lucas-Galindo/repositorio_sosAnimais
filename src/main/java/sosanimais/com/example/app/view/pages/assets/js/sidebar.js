class SideBar extends HTMLElement{
    connectedCallback(){
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
    
    <div class="menu-item">
        <button class="menu-button">
            <div class="menu-button-content">
                <i class="fas fa-paw menu-icon"></i>
                Animais
            </div>
            <i class="fas fa-chevron-down dropdown-icon"></i>
        </button>
        <div class="dropdown-menu">
            <a href="../animal/pageListarAnimal.html" class="dropdown-item">
                <i class="fas fa-list"></i>
                Listar todos
            </a>
            <a href="../animal/pageCadastroAnimal.html" class="dropdown-item">
                <i class="fas fa-plus-circle"></i>
                Cadastrar novo
            </a>
            <a href="../animal/pageBuscaAnimal.html" class="dropdown-item">
                <i class="fas fa-search"></i>
                Buscar animal
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
            <a href="../pessoa/pageListarPessoa.html" class="dropdown-item">
                <i class="fas fa-list"></i>
                Listar todas
            </a>
            <a href="../pessoa/pageCadastroPessoa.html" class="dropdown-item">
                <i class="fas fa-plus-circle"></i>
                Cadastrar nova
            </a>
            <a href="../pessoa/pageBuscaPessoa.html" class="dropdown-item">
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
            <a href="../transfere/pageTransfere.html" class="dropdown-item">
                <i class="fas fa-plus-circle"></i>
                Cadastrar nova
            </a>
            <a href="../transfere/pageListarTransfere.html" class="dropdown-item">
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
</aside>`;
    }
}

customElements.define('main-sidebar',SideBar);