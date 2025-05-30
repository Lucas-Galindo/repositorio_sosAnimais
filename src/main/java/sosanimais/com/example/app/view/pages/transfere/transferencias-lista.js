/**
 * Sistema de Listagem de Transferências - JavaScript
 * Comunicação entre Front-end e Back-end
 */

// Configurações da API
const API_BASE_URL = 'http://localhost:8080/apis';

// Cache de dados
let cacheFuncionarios = new Map();
let cacheBaias = new Map();
let cacheAnimais = new Map();
let transferenciasData = [];

// Estado atual dos filtros
let filtrosAtivos = {
    dataInicial: null,
    dataFinal: null,
    matriculaFuncionario: null,
    nomeFuncionario: null,
    categoriaBaias: null
};

// Inicializar quando página carregar
document.addEventListener('DOMContentLoaded', function() {
    console.log('Inicializando sistema de listagem de transferências...');
    inicializarSistema();
});

/**
 * Inicializar sistema
 */
async function inicializarSistema() {
    try {
        // Carregar dados básicos
        await carregarDadosIniciais();
        
        // Configurar botões e eventos
        configurarEventListeners();
        
        console.log('Sistema inicializado com sucesso!');
        
    } catch (error) {
        console.error('Erro ao inicializar:', error);
        mostrarToast('Erro ao carregar sistema', 'erro');
    }
}

/**
 * Carregar dados iniciais das APIs
 */
async function carregarDadosIniciais() {
    console.log('Carregando dados iniciais...');
    
    // Carregar funcionários
    try {
        const response = await fetch(`${API_BASE_URL}/funcionario/lista`);
        if (response.ok) {
            const funcionarios = await response.json();
            funcionarios.forEach(func => {
                cacheFuncionarios.set(func.matricula, func);
            });
            console.log(`${funcionarios.length} funcionários carregados`);
        }
    } catch (error) {
        console.warn('Erro ao carregar funcionários:', error);
    }

    // Carregar baias
    try {
        const response = await fetch(`${API_BASE_URL}/baias/lista`);
        if (response.ok) {
            const baias = await response.json();
            baias.forEach(baia => {
                cacheBaias.set(baia.id, baia);
            });
            console.log(`${baias.length} baias carregadas`);
        }
    } catch (error) {
        console.warn('Erro ao carregar baias:', error);
    }

    // Carregar animais
    try {
        const response = await fetch(`${API_BASE_URL}/animal`);
        if (response.ok) {
            const animais = await response.json();
            animais.forEach(animal => {
                cacheAnimais.set(animal.id, animal);
            });
            console.log(`${animais.length} animais carregados`);
        }
    } catch (error) {
        console.warn('Erro ao carregar animais:', error);
    }
}

/**
 * Configurar todos os event listeners dos botões
 */
function configurarEventListeners() {
    // Botão Pesquisar
    const btnPesquisar = document.getElementById('btn-pesquisar');
    if (btnPesquisar) {
        btnPesquisar.addEventListener('click', function() {
            pesquisarTransferencias();
        });
    }

    // Botão Listar Todas
    const btnListarTodas = document.getElementById('btn-listar-todas');
    if (btnListarTodas) {
        btnListarTodas.addEventListener('click', function() {
            listarTodasTransferencias();
        });
    }

    // Botão Limpar Filtros
    const btnLimparFiltros = document.getElementById('btn-limpar-filtros');
    if (btnLimparFiltros) {
        btnLimparFiltros.addEventListener('click', function() {
            limparTodosFiltros();
        });
    }

    // Enter nos campos de input para pesquisar
    const campos = [
        'filtro-data-inicial',
        'filtro-data-final', 
        'filtro-matricula-funcionario',
        'filtro-nome-funcionario'
    ];

    campos.forEach(campoId => {
        const campo = document.getElementById(campoId);
        if (campo) {
            campo.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    pesquisarTransferencias();
                }
            });
        }
    });

    // Change no select
    const selectCategoria = document.getElementById('filtro-categoria-baias');
    if (selectCategoria) {
        selectCategoria.addEventListener('change', function() {
            pesquisarTransferencias();
        });
    }
}

/**
 * Pesquisar transferências com filtros
 */
async function pesquisarTransferencias() {
    // Capturar valores dos filtros
    const dataInicial = document.getElementById('filtro-data-inicial')?.value;
    const dataFinal = document.getElementById('filtro-data-final')?.value;
    const matriculaFunc = document.getElementById('filtro-matricula-funcionario')?.value;
    const nomeFunc = document.getElementById('filtro-nome-funcionario')?.value;
    const categoriaBaias = document.getElementById('filtro-categoria-baias')?.value;

    // Salvar estado dos filtros
    filtrosAtivos = {
        dataInicial: dataInicial || null,
        dataFinal: dataFinal || null,
        matriculaFuncionario: matriculaFunc || null,
        nomeFuncionario: nomeFunc || null,
        categoriaBaias: categoriaBaias || null
    };

    console.log('Aplicando filtros:', filtrosAtivos);

    try {
        // Carregar todas as transferências se não estão carregadas
        if (transferenciasData.length === 0) {
            await carregarTodasTransferencias();
        }

        // Aplicar filtros
        let dadosFiltrados = [...transferenciasData];

        // Filtro por data inicial
        if (dataInicial) {
            const dataMin = new Date(dataInicial);
            dadosFiltrados = dadosFiltrados.filter(t => {
                const dataTransf = new Date(t.data);
                return dataTransf >= dataMin;
            });
        }

        // Filtro por data final
        if (dataFinal) {
            const dataMax = new Date(dataFinal);
            dataMax.setHours(23, 59, 59, 999); // Final do dia
            dadosFiltrados = dadosFiltrados.filter(t => {
                const dataTransf = new Date(t.data);
                return dataTransf <= dataMax;
            });
        }

        // Filtro por matrícula do funcionário
        if (matriculaFunc) {
            dadosFiltrados = dadosFiltrados.filter(t => 
                t.matFunc.toString().includes(matriculaFunc)
            );
        }

        // Filtro por nome do funcionário
        if (nomeFunc) {
            dadosFiltrados = dadosFiltrados.filter(t => {
                const funcionario = cacheFuncionarios.get(t.matFunc);
                if (funcionario) {
                    const nome = funcionario.pessoa?.pessoa?.nome || funcionario.pessoa?.nome || '';
                    return nome.toLowerCase().includes(nomeFunc.toLowerCase());
                }
                return false;
            });
        }

        // Exibir resultados
        exibirResultados(dadosFiltrados);
        
        mostrarToast(`Pesquisa realizada. ${dadosFiltrados.length} resultado(s) encontrado(s).`, 'info');

    } catch (error) {
        console.error('Erro ao pesquisar:', error);
        mostrarToast('Erro ao realizar pesquisa', 'erro');
    }
}

/**
 * Listar todas as transferências
 */
async function listarTodasTransferencias() {
    try {
        await carregarTodasTransferencias();
        exibirResultados(transferenciasData);
        mostrarToast(`${transferenciasData.length} transferências carregadas.`, 'sucesso');
    } catch (error) {
        console.error('Erro ao listar todas:', error);
        mostrarToast('Erro ao carregar transferências', 'erro');
    }
}

/**
 * Carregar todas as transferências da API
 */
async function carregarTodasTransferencias() {
    try {
        console.log('Buscando todas as transferências...');
        const response = await fetch(`${API_BASE_URL}/transferencias/lista`);
        
        if (response.ok) {
            const transferencias = await response.json();
            transferenciasData = transferencias;
            console.log(`${transferencias.length} transferências carregadas`);
        } else {
            throw new Error(`Erro HTTP: ${response.status}`);
        }
        
    } catch (error) {
        console.error('Erro ao carregar transferências:', error);
        throw error;
    }
}

/**
 * Exibir resultados na tabela
 */
function exibirResultados(transferencias) {
    const containerResultados = document.getElementById('container-resultados');
    const contadorResultados = document.getElementById('contador-resultados');
    const tbodyResultados = document.getElementById('tbody-resultados');
    const semResultados = document.getElementById('sem-resultados');

    // Mostrar container de resultados
    if (containerResultados) {
        containerResultados.classList.add('ativo');
    }

    // Atualizar contador
    if (contadorResultados) {
        contadorResultados.textContent = `${transferencias.length} transferências encontradas`;
    }

    // Verificar se há resultados
    if (!transferencias || transferencias.length === 0) {
        if (tbodyResultados) tbodyResultados.innerHTML = '';
        if (semResultados) semResultados.style.display = 'block';
        return;
    }

    // Esconder mensagem de sem resultados
    if (semResultados) semResultados.style.display = 'none';

    // Ordenar por data mais recente
    const transferenciasOrdenadas = [...transferencias].sort((a, b) => 
        new Date(b.data) - new Date(a.data)
    );

    // Construir HTML da tabela
    let htmlTabela = '';
    
    transferenciasOrdenadas.forEach(transferencia => {
        const funcionario = cacheFuncionarios.get(transferencia.matFunc) || { 
            matricula: transferencia.matFunc, 
            pessoa: { pessoa: { nome: 'Funcionário não encontrado' } }
        };
        
        const dataFormatada = formatarData(transferencia.data);
        const nomeFuncionario = funcionario.pessoa?.pessoa?.nome || funcionario.pessoa?.nome || 'Nome não disponível';

        htmlTabela += `
            <tr class="linha-transferencia" data-id="${transferencia.id}" onclick="toggleDetalhesTransferencia(${transferencia.id})">
                <td>${transferencia.id}</td>
                <td>${dataFormatada}</td>
                <td>${nomeFuncionario}</td>
                <td>${funcionario.matricula}</td>
                <td>
                    <i class="fas fa-chevron-right icone-expandir" id="icone-${transferencia.id}"></i>
                </td>
            </tr>
            <tr class="detalhes-transferencia" id="detalhes-${transferencia.id}">
                <td colspan="5">
                    <div class="detalhes-conteudo">
                        <div class="loading-details">
                            <i class="fas fa-spinner fa-spin"></i>
                            <span>Carregando detalhes...</span>
                        </div>
                    </div>
                </td>
            </tr>
        `;
    });

    if (tbodyResultados) {
        tbodyResultados.innerHTML = htmlTabela;
    }
}

/**
 * Toggle detalhes da transferência (função global para onclick)
 */
function toggleDetalhesTransferencia(transferenciaId) {
    const linhaTransferencia = document.querySelector(`[data-id="${transferenciaId}"]`);
    const detalhes = document.getElementById(`detalhes-${transferenciaId}`);
    const icone = document.getElementById(`icone-${transferenciaId}`);
    
    if (!detalhes || !icone) return;
    
    if (detalhes.classList.contains('ativo')) {
        // Colapsar
        detalhes.classList.remove('ativo');
        linhaTransferencia.classList.remove('expandida');
        icone.classList.remove('expandido');
    } else {
        // Expandir
        detalhes.classList.add('ativo');
        linhaTransferencia.classList.add('expandida');
        icone.classList.add('expandido');
        
        // Carregar detalhes se ainda não carregados
        const conteudo = detalhes.querySelector('.detalhes-conteudo');
        if (conteudo && conteudo.querySelector('.loading-details')) {
            carregarDetalhesTransferencia(transferenciaId);
        }
    }
}

/**
 * Carregar detalhes específicos da transferência
 */
async function carregarDetalhesTransferencia(transferenciaId) {
    const detalhes = document.getElementById(`detalhes-${transferenciaId}`);
    if (!detalhes) return;
    
    const conteudoDetalhes = detalhes.querySelector('.detalhes-conteudo');
    if (!conteudoDetalhes) return;
    
    try {
        console.log(`Carregando detalhes para transferência ${transferenciaId}`);
        
        // Buscar detalhes da transferência
        const dadosDetalhes = await buscarDetalhesTransferencia(transferenciaId);
        
        let htmlDetalhes = `
            <div class="detalhes-titulo">
                <i class="fas fa-list-ul"></i>
                Movimentações desta Transferência
            </div>
        `;
        
        if (dadosDetalhes && dadosDetalhes.length > 0) {
            htmlDetalhes += `
                <table class="tabela-detalhes">
                    <thead>
                        <tr>
                            <th>ID Movimentação</th>
                            <th>Baia Origem</th>
                            <th>Baia Destino</th>
                            <th>Animal</th>
                        </tr>
                    </thead>
                    <tbody>
            `;
            
            dadosDetalhes.forEach(detalhe => {
                const baiaOrigem = cacheBaias.get(detalhe.baiaOrigem) || { 
                    id: detalhe.baiaOrigem, 
                    nome: 'Não encontrada',
                    categoria: 'N/A'
                };
                const baiaDestino = cacheBaias.get(detalhe.baiaDestino) || { 
                    id: detalhe.baiaDestino, 
                    nome: 'Não encontrada',
                    categoria: 'N/A'
                };
                const animal = cacheAnimais.get(detalhe.animalId) || { 
                    id: detalhe.animalId, 
                    informacao: { nome: 'Animal não encontrado', raca: 'N/A' }
                };
                
                htmlDetalhes += `
                    <tr>
                        <td>${detalhe.id}</td>
                        <td>
                            <strong>${baiaOrigem.nome}</strong><br>
                            <small>ID: ${baiaOrigem.id}</small><br>
                            <span class="badge badge-${baiaOrigem.categoria.toLowerCase()}">${baiaOrigem.categoria}</span>
                        </td>
                        <td>
                            <strong>${baiaDestino.nome}</strong><br>
                            <small>ID: ${baiaDestino.id}</small><br>
                            <span class="badge badge-${baiaDestino.categoria.toLowerCase()}">${baiaDestino.categoria}</span>
                        </td>
                        <td>
                            <strong>${animal.informacao?.nome || 'Nome não disponível'}</strong><br>
                            <small>ID: ${animal.id}</small><br>
                            <small>Raça: ${animal.informacao?.raca || 'N/A'}</small>
                        </td>
                    </tr>
                `;
            });
            
            htmlDetalhes += `
                    </tbody>
                </table>
            `;
        } else {
            htmlDetalhes += `
                <p style="text-align: center; color: #6c757d; padding: 1rem;">
                    <i class="fas fa-info-circle"></i>
                    Nenhuma movimentação encontrada para esta transferência.
                </p>
            `;
        }
        
        conteudoDetalhes.innerHTML = htmlDetalhes;
        
    } catch (error) {
        console.error('Erro ao carregar detalhes:', error);
        conteudoDetalhes.innerHTML = `
            <div style="text-align: center; color: #dc3545; padding: 1rem;">
                <i class="fas fa-exclamation-triangle"></i>
                <p>Erro ao carregar detalhes da transferência.</p>
                <button class="btn-pesquisar" onclick="carregarDetalhesTransferencia(${transferenciaId})">
                    <i class="fas fa-redo"></i>
                    Tentar Novamente
                </button>
            </div>
        `;
    }
}

/**
 * Buscar detalhes da transferência na API
 */
async function buscarDetalhesTransferencia(transferenciaId) {
    try {
        // TODO: Implementar endpoint real para buscar dados de transferir_to_baia
        // const response = await fetch(`${API_BASE_URL}/transferencias/${transferenciaId}/detalhes`);
        // if (response.ok) {
        //     return await response.json();
        // }
        
        // Por enquanto, usando dados mock para demonstração
        return await getDadosMockDetalhes(transferenciaId);
        
    } catch (error) {
        console.error('Erro ao buscar detalhes:', error);
        throw error;
    }
}

/**
 * Dados mock para demonstração (remover quando implementar endpoint real)
 */
async function getDadosMockDetalhes(transferenciaId) {
    // Simular delay
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    // Dados de exemplo baseados no ID
    const mockData = [
        {
            id: `${transferenciaId}-1`,
            baiaOrigem: 1,
            baiaDestino: 2,
            animalId: 1
        }
    ];
    
    // Adicionar mais dados para alguns IDs
    if (transferenciaId % 3 === 0) {
        mockData.push({
            id: `${transferenciaId}-2`,
            baiaOrigem: 3,
            baiaDestino: 4,
            animalId: 2
        });
    }
    
    return mockData;
}

/**
 * Limpar todos os filtros
 */
function limparTodosFiltros() {
    // Limpar campos
    const campos = [
        'filtro-data-inicial',
        'filtro-data-final',
        'filtro-matricula-funcionario',
        'filtro-nome-funcionario',
        'filtro-categoria-baias'
    ];

    campos.forEach(campoId => {
        const campo = document.getElementById(campoId);
        if (campo) {
            campo.value = '';
        }
    });

    // Resetar estado dos filtros
    filtrosAtivos = {
        dataInicial: null,
        dataFinal: null,
        matriculaFuncionario: null,
        nomeFuncionario: null,
        categoriaBaias: null
    };

    // Esconder resultados
    const containerResultados = document.getElementById('container-resultados');
    if (containerResultados) {
        containerResultados.classList.remove('ativo');
    }

    mostrarToast('Filtros limpos', 'info');
}

/**
 * Formatar data
 */
function formatarData(dateString) {
    try {
        const date = new Date(dateString);
        return date.toLocaleString('pt-BR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    } catch (error) {
        return 'Data inválida';
    }
}

/**
 * Sistema de Toast/Notificações
 */
function mostrarToast(mensagem, tipo = 'sucesso', duracao = 4000) {
    let container = document.getElementById('container-toast');
    
    if (!container) {
        container = document.createElement('div');
        container.id = 'container-toast';
        container.className = 'container-toast';
        document.body.appendChild(container);
    }
    
    const toastId = 'toast-' + Date.now();
    const icones = {
        sucesso: 'fa-check-circle',
        erro: 'fa-exclamation-circle',
        info: 'fa-info-circle'
    };
    
    const toastHtml = `
        <div class="toast toast-${tipo}" id="${toastId}">
            <div class="icone-toast">
                <i class="fas ${icones[tipo] || icones.info}"></i>
            </div>
            <div class="conteudo-toast">
                <div class="mensagem-toast">${mensagem}</div>
            </div>
            <button class="fechar-toast" onclick="fecharToast('${toastId}')">
                <i class="fas fa-times"></i>
            </button>
        </div>
    `;
    
    container.insertAdjacentHTML('beforeend', toastHtml);
    
    const toast = document.getElementById(toastId);
    if (toast) {
        setTimeout(() => toast.classList.add('mostrar'), 100);
        setTimeout(() => fecharToast(toastId), duracao);
    }
}

/**
 * Fechar toast
 */
function fecharToast(toastId) {
    const toast = document.getElementById(toastId);
    if (toast) {
        toast.classList.remove('mostrar');
        setTimeout(() => toast.remove(), 300);
    }
}