// Configuração da API
        const API_BASE_URL = 'http://localhost:8080/apis';

        document.addEventListener('DOMContentLoaded', function() {
            // Search form handling
            const resultsContainer = document.getElementById('results-container');
            const resultsCount = document.getElementById('results-count');
            const resultsTable = document.getElementById('results-table');
            const resultsTbody = document.getElementById('results-tbody');
            const noResults = document.getElementById('no-results');

            // Filter elements
            const filterCodigo = document.getElementById('filter-codigo');
            const filterNome = document.getElementById('filter-nome');
            const filterDescricao = document.getElementById('filter-descricao');
            const btnSearch = document.getElementById('btn-search');
            const btnListAll = document.getElementById('btn-list-all');
            const btnClearFilters = document.getElementById('btn-clear-filters');
            const btnAddNew = document.getElementById('btn-add-new');

            // Toast notification function
            function showToast(type, title, message) {
                const toastContainer = document.getElementById('toast-container');
                const toastId = 'toast-' + Date.now();

                const toast = document.createElement('div');
                toast.className = `toast toast-${type}`;
                toast.id = toastId;

                const iconClass = type === 'success' ? 'fa-check-circle' : 
                               type === 'error' ? 'fa-exclamation-circle' : 'fa-info-circle';

                toast.innerHTML = `
                    <i class="fas ${iconClass} toast-icon"></i>
                    <div class="toast-content">
                        <div class="toast-title">${title}</div>
                        <div class="toast-message">${message}</div>
                    </div>
                    <button class="toast-close" onclick="closeToast('${toastId}')">&times;</button>
                `;

                toastContainer.appendChild(toast);

                // Show toast with animation
                setTimeout(() => {
                    toast.classList.add('show');
                }, 10);

                // Auto-remove toast after 5 seconds
                setTimeout(() => {
                    closeToast(toastId);
                }, 5000);
            }

            // Close toast function
            window.closeToast = function(toastId) {
                const toast = document.getElementById(toastId);
                if (toast) {
                    toast.classList.remove('show');
                    setTimeout(() => {
                        toast.remove();
                    }, 300);
                }
            };

            // Clear filters function
            function clearFilters() {
                filterCodigo.value = '';
                filterNome.value = '';
                filterDescricao.value = '';
                
                // Hide results
                resultsContainer.classList.remove('active');
                
                showToast('info', 'Filtros Limpos', 'Todos os filtros foram removidos.');
            }

            // Build search parameters
            function buildSearchParams() {
                const params = {};
                
                if (filterCodigo.value.trim()) {
                    params.codigo = filterCodigo.value.trim();
                }
                
                if (filterNome.value.trim()) {
                    params.nome = filterNome.value.trim();
                }
                
                if (filterDescricao.value.trim()) {
                    params.descricao = filterDescricao.value.trim();
                }
                
                return params;
            }

            // Set loading state
            function setLoading(isLoading) {
                const buttons = [btnSearch, btnListAll];
                
                buttons.forEach(btn => {
                    if (isLoading) {
                        btn.classList.add('loading');
                        btn.disabled = true;
                        if (btn === btnSearch) {
                            btn.innerHTML = '<i class="fas fa-spinner"></i> Pesquisando...';
                        } else if (btn === btnListAll) {
                            btn.innerHTML = '<i class="fas fa-spinner"></i> Carregando...';
                        }
                    } else {
                        btn.classList.remove('loading');
                        btn.disabled = false;
                        if (btn === btnSearch) {
                            btn.innerHTML = '<i class="fas fa-search"></i> Pesquisar';
                        } else if (btn === btnListAll) {
                            btn.innerHTML = '<i class="fas fa-list"></i> Listar Todos';
                        }
                    }
                });
            }

            // Perform search function
            function performSearch(listAll = false) {
                if (!listAll) {
                    const params = buildSearchParams();
                    
                    // Check if at least one filter is provided
                    if (Object.keys(params).length === 0) {
                        showToast('error', 'Erro na Pesquisa', 'Por favor, preencha pelo menos um filtro para pesquisar.');
                        return;
                    }
                }
                
                // Clear previous results
                resultsTbody.innerHTML = '';
                
                // Show loading state
                setLoading(true);
                resultsContainer.classList.add('active');
                resultsTable.style.display = 'none';
                noResults.style.display = 'none';
                resultsCount.textContent = 'Carregando...';
                
                // Build API URL
                let apiUrl = `${API_BASE_URL}/servico`;
                
                if (listAll) {
                    apiUrl = `${API_BASE_URL}/servico/lista`;
                } else {
                    const params = buildSearchParams();
                    
                    // Determine endpoint based on provided filters
                    if (params.codigo && Object.keys(params).length === 1) {
                        apiUrl = `${API_BASE_URL}/servico/${params.codigo}`;
                    } else if (params.nome && Object.keys(params).length === 1) {
                        // Para busca por nome, vamos usar a lista e filtrar no frontend
                        apiUrl = `${API_BASE_URL}/servico/lista`;
                    } else {
                        // Multiple filters - usar lista e filtrar no frontend
                        apiUrl = `${API_BASE_URL}/servico/lista`;
                    }
                }
                
                console.log("Buscando em:", apiUrl);
                console.log("Parâmetros de busca:", buildSearchParams());
                
                // Make API call
                fetch(apiUrl)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`Erro HTTP: ${response.status}`);
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log("Dados recebidos da API:", data);
                        processSearchResults(data, listAll);
                    })
                    .catch(error => {
                        console.error('Erro ao buscar serviços:', error);
                        showToast('error', 'Erro na Pesquisa', `Ocorreu um erro ao buscar serviços: ${error.message}`);
                        resultsContainer.classList.remove('active');
                    })
                    .finally(() => {
                        setLoading(false);
                    });
            }

            // Process search results
            function processSearchResults(data, listAll) {
                // Convert to array if single object
                let results = Array.isArray(data) ? data : [data];
                
                console.log("Dados recebidos:", results);
                
                // Apply frontend filters if not listing all
                if (!listAll) {
                    const params = buildSearchParams();
                    
                    if (params.nome) {
                        results = results.filter(servico => 
                            servico.nome && servico.nome.toLowerCase().includes(params.nome.toLowerCase())
                        );
                    }
                    
                    if (params.descricao) {
                        results = results.filter(servico => 
                            servico.descricao && servico.descricao.toLowerCase().includes(params.descricao.toLowerCase())
                        );
                    }
                    
                    if (params.codigo) {
                        results = results.filter(servico => 
                            servico.id && servico.id.toString().includes(params.codigo)
                        );
                    }
                }
                
                displayResults(results);
            }

            // Display results
            function displayResults(results) {
                // Update results count
                const count = results.length;
                resultsCount.textContent = `${count} ${count === 1 ? 'serviço encontrado' : 'serviços encontrados'}`;
                
                // Check if there are results
                if (count === 0) {
                    resultsTable.style.display = 'none';
                    noResults.style.display = 'block';
                    return;
                }
                
                // Show results table
                resultsTable.style.display = 'table';
                noResults.style.display = 'none';
                
                // Limpar a tabela antes de adicionar os novos resultados
                resultsTbody.innerHTML = '';
                
                // Para cada serviço nos resultados
                results.forEach(servico => {
                    // Criar uma nova linha na tabela
                    const row = document.createElement('tr');
                    
                    // Extrair dados do serviço
                    const id = servico.id || servico.serv_cod;
                    const nome = servico.nome || servico.serv_nome || 'N/A';
                    const descricao = servico.descricao || servico.serv_desc || '';
                    
                    // Truncar descrição se muito longa
                    const maxDescLength = 100;
                    let descricaoDisplay = descricao;
                    let expandButton = '';
                    
                    if (descricao.length > maxDescLength) {
                        descricaoDisplay = descricao.substring(0, maxDescLength) + '...';
                        expandButton = `<button class="expand-btn" onclick="toggleDescription(this, '${escapeHtml(descricao)}')">Ver mais</button>`;
                    }
                    
                    // Populate row
                    row.innerHTML = `
                        <td>${id}</td>
                        <td>${escapeHtml(nome)}</td>
                        <td class="description-cell">
                            <span class="description-text">${escapeHtml(descricaoDisplay)}</span>
                            ${expandButton}
                        </td>
                        <td>
                            <div class="action-buttons">
                                <button class="btn-action" title="Visualizar" data-id="${id}">
                                    <i class="fas fa-eye"></i>
                                </button>
                                <button class="btn-action edit-btn" title="Editar" data-id="${id}">
                                    <i class="fas fa-edit"></i>
                                </button>
                                <button class="btn-action delete-btn" title="Excluir" data-id="${id}">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </div>
                        </td>
                    `;
                    
                    // Adicionar a linha à tabela
                    resultsTbody.appendChild(row);
                });
                
                // Adicionar event listeners aos botões de ação
                addActionButtonListeners();
            }

            // Escape HTML to prevent XSS
            function escapeHtml(text) {
                const map = {
                    '&': '&amp;',
                    '<': '&lt;',
                    '>': '&gt;',
                    '"': '&quot;',
                    "'": '&#039;'
                };
                return text.replace(/[&<>"']/g, function(m) { return map[m]; });
            }

            // Toggle description function
            window.toggleDescription = function(button, fullText) {
                const descriptionCell = button.closest('.description-cell');
                const descriptionText = descriptionCell.querySelector('.description-text');
                
                if (button.textContent === 'Ver mais') {
                    descriptionText.textContent = fullText;
                    button.textContent = 'Ver menos';
                    descriptionCell.classList.add('expanded');
                } else {
                    const truncated = fullText.substring(0, 100) + '...';
                    descriptionText.textContent = truncated;
                    button.textContent = 'Ver mais';
                    descriptionCell.classList.remove('expanded');
                }
            };

            // Add event listeners to action buttons
            function addActionButtonListeners() {
                // Visualizar
                document.querySelectorAll('.btn-action[title="Visualizar"]').forEach(button => {
                    button.addEventListener('click', function() {
                        const servicoId = this.getAttribute('data-id');
                        showToast('info', 'Visualizar Serviço', `Visualizando serviço ID: ${servicoId}`);
                        // Aqui você pode abrir um modal ou redirecionar para página de detalhes
                    });
                });

                // Editar
                document.querySelectorAll('.edit-btn').forEach(button => {
                    button.addEventListener('click', function() {
                        const servicoId = this.getAttribute('data-id');
                        // Redirecionar para página de edição
                        window.open(`alterar-servico.html?id=${servicoId}`, '_blank');
                    });
                });

                // Excluir
                document.querySelectorAll('.delete-btn').forEach(button => {
                    button.addEventListener('click', async function() {
                        const servicoId = this.getAttribute('data-id');
                        
                        if (confirm('Tem certeza que deseja excluir este serviço? Esta ação não pode ser desfeita.')) {
                            try {
                                showToast('info', 'Processando', 'Excluindo serviço...');
                                
                                const response = await fetch(`${API_BASE_URL}/servico/${servicoId}`, {
                                    method: 'DELETE',
                                    headers: {'Content-Type': 'application/json'}
                                });
                                
                                if (!response.ok) {
                                    throw new Error(`Erro ao excluir serviço: ${response.status}`);
                                }
                                
                                // Mostrar mensagem de sucesso
                                showToast('success', 'Sucesso', 'Serviço excluído com sucesso!');
                                
                                // Remover a linha da tabela
                                const row = button.closest('tr');
                                row.remove();
                                
                                // Atualizar o contador de resultados
                                const count = resultsTbody.children.length;
                                resultsCount.textContent = `${count} ${count === 1 ? 'serviço encontrado' : 'serviços encontrados'}`;
                                
                                // Se não há mais resultados, mostrar mensagem
                                if (count === 0) {
                                    resultsTable.style.display = 'none';
                                    noResults.style.display = 'block';
                                }
                                
                            } catch (error) {
                                console.error('Erro durante o processo de exclusão:', error);
                                showToast('error', 'Erro na Exclusão', `Ocorreu um erro: ${error.message}`);
                            }
                        }
                    });
                });
            }

            // Event listeners
            btnSearch.addEventListener('click', () => performSearch(false));
            btnListAll.addEventListener('click', () => performSearch(true));
            btnClearFilters.addEventListener('click', clearFilters);
            btnAddNew.addEventListener('click', () => {
                window.open('cadastro-servico.html', '_blank');
            });
            
            // Handle Enter key press in filter inputs
            [filterCodigo, filterNome, filterDescricao].forEach(input => {
                input.addEventListener('keyup', function(event) {
                    if (event.key === 'Enter') {
                        performSearch(false);
                    }
                });
            });

            // JavaScript para controlar os dropdowns
            const menuButtons = document.querySelectorAll('.menu-button');

            menuButtons.forEach(button => {
                button.addEventListener('click', function() {
                    // Encontra o dropdown-menu associado a este botão
                    const dropdownMenu = this.nextElementSibling;
                    const dropdownIcon = this.querySelector('.dropdown-icon');

                    // Toggle da classe active
                    dropdownMenu.classList.toggle('active');
                    dropdownIcon.classList.toggle('active');

                    // Opcional: Fecha outros dropdowns abertos
                    document.querySelectorAll('.dropdown-menu.active').forEach(menu => {
                        if (menu !== dropdownMenu) {
                            menu.classList.remove('active');
                            menu.previousElementSibling.querySelector('.dropdown-icon').classList.remove('active');
                        }
                    });
                });
            });

            // Toggle para exibir/ocultar a sidebar em dispositivos móveis
            const sidebarToggle = document.getElementById('sidebarToggle');
            const sidebar = document.getElementById('sidebar');

            if (sidebarToggle && sidebar) {
                sidebarToggle.addEventListener('click', function() {
                    sidebar.classList.toggle('active');
                });

                // Fechar a sidebar quando clicar fora dela em dispositivos móveis
                document.addEventListener('click', function(event) {
                    const isClickInsideSidebar = sidebar.contains(event.target);
                    const isClickOnToggle = sidebarToggle.contains(event.target);

                    if (!isClickInsideSidebar && !isClickOnToggle && sidebar.classList.contains('active') && window.innerWidth <= 768) {
                        sidebar.classList.remove('active');
                    }
                });
            }

            // Auto-load all services on page load
            setTimeout(() => {
                performSearch(true);
            }, 500);
        });