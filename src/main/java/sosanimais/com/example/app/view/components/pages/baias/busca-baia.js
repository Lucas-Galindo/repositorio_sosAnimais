      document.addEventListener('DOMContentLoaded', function() {
            // Search form handling
            const resultsContainer = document.getElementById('results-container');
            const resultsCount = document.getElementById('results-count');
            const resultsTable = document.getElementById('results-table');
            const resultsTbody = document.getElementById('results-tbody');
            const noResults = document.getElementById('no-results');

            // Filter elements
            const filterCodigo = document.getElementById('filter-codigo');
            const filterCategoria = document.getElementById('filter-categoria');
            const filterQuantidade = document.getElementById('filter-quantidade');
            const filterComum = document.getElementById('filter-comum');
            const filterMedica = document.getElementById('filter-medica');
            const btnSearch = document.getElementById('btn-search');
            const btnListAll = document.getElementById('btn-list-all');
            const btnClearFilters = document.getElementById('btn-clear-filters');

            // Toast notification function
            function showToast(type, title, message) {
                const toastContainer = document.getElementById('toast-container');

                const toast = document.createElement('div');
                toast.className = `toast toast-${type}`;

                const iconClass = type === 'success' ? 'fa-check-circle' : 
                               type === 'error' ? 'fa-exclamation-circle' : 'fa-info-circle';

                toast.innerHTML = `
                    <i class="fas ${iconClass} toast-icon"></i>
                    <div class="toast-content">
                        <div class="toast-title">${title}</div>
                        <div class="toast-message">${message}</div>
                    </div>
                    <button class="toast-close">&times;</button>
                `;

                toastContainer.appendChild(toast);

                // Add close event
                toast.querySelector('.toast-close').addEventListener('click', function() {
                    toast.remove();
                });

                // Show toast with animation
                setTimeout(() => {
                    toast.classList.add('show');
                }, 10);

                // Auto-remove toast after 5 seconds
                setTimeout(() => {
                    toast.classList.remove('show');
                    setTimeout(() => {
                        toast.remove();
                    }, 300);
                }, 5000);
            }

            // Clear filters function
            function clearFilters() {
                filterCodigo.value = '';
                filterCategoria.value = '';
                filterQuantidade.value = '';
                filterComum.checked = true;
                filterMedica.checked = true;
                
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
                
                if (filterCategoria.value.trim()) {
                    params.categoria = filterCategoria.value.trim();
                }
                
                if (filterQuantidade.value) {
                    params.quantidade = filterQuantidade.value;
                }
                
                // Tipo de baia
                const tipos = [];
                if (filterComum.checked) tipos.push('comum');
                if (filterMedica.checked) tipos.push('medica');
                if (tipos.length > 0 && tipos.length < 2) {
                    params.tipo = tipos[0];
                }
                
                return params;
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
                resultsContainer.classList.add('active');
                resultsTable.style.display = 'none';
                noResults.style.display = 'none';
                resultsCount.textContent = 'Carregando...';
                
                // Build API URL
                let apiUrl = 'http://localhost:8080/apis/baias';
                
                if (listAll) {
                    apiUrl = 'http://localhost:8080/apis/baias/lista';
                } else {
                    const params = buildSearchParams();
                    
                    // Determine endpoint based on provided filters
                    if (params.codigo && Object.keys(params).length === 1) {
                        apiUrl = `http://localhost:8080/apis/baias/${params.codigo}`;
                    } else if (params.categoria && Object.keys(params).length === 1) {
                        apiUrl = `http://localhost:8080/apis/baias/lista/categoria/${params.categoria}`;
                    } else {
                        // Multiple filters - you can implement a composite search endpoint
                        // For now, we'll use a generic search with query parameters
                        const queryParams = new URLSearchParams();
                        Object.entries(params).forEach(([key, value]) => {
                            queryParams.append(key, value);
                        });
                        apiUrl = `http://localhost:8080/apis/baias/search?${queryParams.toString()}`;
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
                        processSearchResults(data)
                            .catch(error => {
                                console.error('Erro ao processar resultados:', error);
                                showToast('error', 'Erro na Exibição', `Ocorreu um erro ao processar resultados: ${error.message}`);
                            });
                    })
                    .catch(error => {
                        console.error('Erro ao buscar baias:', error);
                        showToast('error', 'Erro na Pesquisa', `Ocorreu um erro ao buscar baias: ${error.message}`);
                        resultsContainer.classList.remove('active');
                    });
            }

            // Process search results and determine types
            async function processSearchResults(data) {
                // Convert to array if single object
                const results = Array.isArray(data) ? data : [data];
                
                console.log("Dados brutos recebidos:", results);
                
                // Process each baia to determine type
                for (const baia of results) {
                    let baiaType = 'Comum';
                    
                    try {
                        // Log para debug dos dados da baia
                        console.log("Processando baia:", baia);
                        
                        // Verificar se é uma baia médica através do nome ou categoria
                        const nome = baia.nome || '';
                        const categoria = baia.categoria || '';
                        
                        const nomeOuCategoria = (nome + ' ' + categoria).toLowerCase();
                        
                        console.log("Nome ou categoria para verificação:", nomeOuCategoria);
                        
                        if (nomeOuCategoria.includes('medica') || nomeOuCategoria.includes('médica')) {
                            baiaType = 'Médica';
                        } else {
                            // Fallback: verificar via API se necessário
                            const medicaResp = await fetch(`http://localhost:8080/apis/baias/lista/categoria/medica/${baia.id || baia.baia_cod}`)
                                .catch(() => ({ ok: false }));
                            
                            if (medicaResp.ok) {
                                baiaType = 'Médica';
                            }
                        }
                        
                        console.log(`Baia ${baia.id || baia.baia_cod} classificada como: ${baiaType}`);
                    } catch (error) {
                        console.error('Erro ao verificar tipo da baia:', error);
                    }
                    
                    baia.tipo = baiaType;
                }
                
                displayResults(results);
            }

            // Display results
            function displayResults(results) {
                // Update results count
                const count = results.length;
                resultsCount.textContent = `${count} ${count === 1 ? 'baia encontrada' : 'baias encontradas'}`;
                
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
                
                // Para cada baia nos resultados
                results.forEach(baia => {
                    // Criar uma nova linha na tabela
                    const row = document.createElement('tr');
                    
                    // Extrair dados da baia
                    const id = baia.id;
                   // const categoria = baia.categoria || baia.baia_categoria || '';
                    //const quantidade = baia.quantidade || baia.baia_qtde || 0;
                    //const tipo = baia.tipo || 'Comum';

                    const nome = baia.nome;
                    const categoria = baia.categoria ;
                    const quantidade = baia.quantidade || 0;
                    
                    // Create type badge
                    const typeBadge = `<span class="badge badge-${categoria.toLowerCase()}">${categoria}</span>`;
                    
                    // Populate row
                    row.innerHTML = `
                        <td>${id}</td>           <!-- ID -->
                        <td>${nome}</td>         <!-- Nome real da baia -->
                        <td>${categoria}</td>    <!-- Categoria -->
                        <td>${quantidade}</td>   <!-- Quantidade -->
                        <td>${typeBadge}</td>    <!-- Badge do Tipo -->
                        <td>                    <!-- Ações -->
                        <div class="action-buttons">...</div>
                    </td>
                        <td>
                            <div class="action-buttons">
                                <button class="btn-action" title="Visualizar" data-id="${id}">
                                    <i class="fas fa-eye"></i>
                                </button>
                                <button class="btn-action edit-btn" title="Alterar" data-id="${id}">
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

            // Add event listeners to action buttons
            function addActionButtonListeners() {
                // Visualizar
                document.querySelectorAll('.btn-action[title="Visualizar"]').forEach(button => {
                    button.addEventListener('click', function() {
                        const baiaId = this.getAttribute('data-id');
                        showToast('info', 'Visualizar Baia', `Visualizando baia ID: ${baiaId}`);
                        // Aqui você pode abrir um modal ou redirecionar para página de detalhes
                    });
                });

                // Alterar
                document.querySelectorAll('.edit-btn').forEach(button => {
                    button.addEventListener('click', function() {
                        const baiaId = this.getAttribute('data-id');
                        // Redirecionar para página de alteração
                        window.open(`pageAlterarBaia.html?id=${baiaId}`, '_blank');
                    });
                });

                // Excluir
                document.querySelectorAll('.delete-btn').forEach(button => {
                    button.addEventListener('click', async function() {
                        const baiaId = this.getAttribute('data-id');
                        
                        if (confirm('Tem certeza que deseja excluir esta baia? Esta ação não pode ser desfeita.')) {
                            try {
                                showToast('info', 'Processando', 'Excluindo baia...');
                                
                                const response = await fetch(`http://localhost:8080/apis/baias/${baiaId}`, {
                                    method: 'DELETE',
                                    headers: {'Content-Type': 'application/json'}
                                });
                                
                                if (!response.ok) {
                                    throw new Error(`Erro ao excluir baia: ${response.status}`);
                                }
                                
                                // Mostrar mensagem de sucesso
                                showToast('success', 'Sucesso', 'Baia excluída com sucesso!');
                                
                                // Remover a linha da tabela
                                const row = button.closest('tr');
                                row.remove();
                                
                                // Atualizar o contador de resultados
                                const count = resultsTbody.children.length;
                                resultsCount.textContent = `${count} ${count === 1 ? 'baia encontrada' : 'baias encontradas'}`;
                                
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
            
            // Handle Enter key press in filter inputs
            [filterCodigo, filterCategoria].forEach(input => {
                input.addEventListener('keyup', function(event) {
                    if (event.key === 'Enter') {
                        performSearch(false);
                    }
                });
            });
        });