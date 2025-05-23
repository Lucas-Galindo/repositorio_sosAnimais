document.addEventListener('DOMContentLoaded', async function() {
    // Variáveis globais
    let selectedBaiaType = '';
    let selectedBaiaId = null;
    let selectedBaiaName = '';

    // Array para armazenar os dados das baias
    const baiasData = [];

    try {
        const [medicaResponse, comumResponse] = await Promise.all([
            fetch(`http://localhost:8080/apis/baias/categoria/Medica`),
            fetch(`http://localhost:8080/apis/baias/categoria/Comum`)
        ]);

        if (!medicaResponse.ok || !comumResponse.ok) {
            throw new Error('Erro em uma das requisições');
        }

        const [medicaData, comumData] = await Promise.all([
            medicaResponse.json(),
            comumResponse.json()
        ]);

        baiasData.push(
            {categoria: 'Medica', dados: medicaData},
            {categoria: 'Comum', dados: comumData}
        );

        console.log('Dados carregados:', baiasData);
        
    } catch (error) {
        console.error('Erro ao carregar dados:', error);
        // Fallback com dados vazios em caso de erro
        baiasData.push(
            {categoria: 'Medica', dados: []},
            {categoria: 'Comum', dados: []}
        );
    }   

    // Inicialização
    init();

    function init() {
        setupEventListeners();
        setDefaultDateTime();
    }

    // Configurar todos os event listeners
    function setupEventListeners() {
        setupFormValidation();
        setupBaiaTypeSelector();
        setupFormActions();
    }

    // Configurar validação em tempo real
    function setupFormValidation() {
        const animalInput = document.getElementById('animal-identificacao');
        const dataInput = document.getElementById('data-transferencia');
        const matriculaInput = document.getElementById('matricula-funcionario');

        animalInput.addEventListener('blur', function() {
            validateAnimalId(this.value);
        });

        dataInput.addEventListener('change', function() {
            validateDataTransferencia(this.value);
        });

        matriculaInput.addEventListener('blur', function() {
            validateMatricula(this.value);
        });
    }

    // Configurar seletor de tipo de baia
    function setupBaiaTypeSelector() {
        const baiaTypeButtons = document.querySelectorAll('.baia-type-btn');

        baiaTypeButtons.forEach(button => {
            button.addEventListener('click', function() {
                // Remove classe active de todos os botões
                baiaTypeButtons.forEach(btn => btn.classList.remove('active'));
                
                // Adiciona classe active ao botão clicado
                this.classList.add('active');
                
                // Atualiza tipo selecionado
                selectedBaiaType = this.getAttribute('data-type');
                
                // Carrega as baias do tipo selecionado
                loadBaias(selectedBaiaType);
                
                // Remove erro se existir
                showError('tipo-baia-error', false);
            });
        });
    }

    // Configurar ações do formulário
    function setupFormActions() {
        const saveButton = document.getElementById('btn-save');
        const cancelButton = document.getElementById('btn-cancel');

        saveButton.addEventListener('click', handleSave);
        cancelButton.addEventListener('click', handleCancel);
    }

    // Definir data e hora padrão (agora)
    function setDefaultDateTime() {
        const now = new Date();
        now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
        document.getElementById('data-transferencia').value = now.toISOString().slice(0, 16);
    }

    // FUNÇÃO CORRIGIDA: Carregar baias do tipo selecionado
    function loadBaias(tipo) {
        const baiaList = document.getElementById('baia-list');
        
        // Encontrar os dados do tipo selecionado na nova estrutura
        const tipoCapitalizado = tipo.charAt(0).toUpperCase() + tipo.slice(1);
        const categoriaBaias = baiasData.find(item => 
            item.categoria.toLowerCase() === tipo.toLowerCase() || 
            item.categoria === tipoCapitalizado
        );
        
        const baias = categoriaBaias ? categoriaBaias.dados : [];

        // Limpar seleção anterior
        selectedBaiaId = null;
        selectedBaiaName = '';

        if (baias.length === 0) {
            baiaList.innerHTML = '<p class="help-text">Nenhuma baia disponível para este tipo.</p>';
            return;
        }

        // Mapear os dados da API para o formato esperado pelo front-end
        baiaList.innerHTML = baias.map(baia => {
            console.log(baia);
            const baiaId = baia.id;
            const baiaNome = baia.nome || '';
            const quantidade = baia.quantidadeAnimais || 0;
            
            return `
                <div class="baia-card" 
                     data-baia-id="${baiaId}" 
                     data-baia-name="${baiaNome}">
                    <div class="baia-name">${baiaNome}</div>
                    <div class="baia-info">
                        ${quantidade} animais
                    </div>
                </div>
            `;
        }).join('');

        // Adicionar event listeners aos cards das baias
        const baiaCards = baiaList.querySelectorAll('.baia-card:not(.disabled)');
        baiaCards.forEach(card => {
            card.addEventListener('click', function() {
                // Remove seleção de todos os cards
                baiaCards.forEach(c => c.classList.remove('selected'));
                
                // Seleciona o card clicado
                this.classList.add('selected');
                
                // Atualiza dados da baia selecionada
                selectedBaiaId = parseInt(this.getAttribute('data-baia-id'));
                selectedBaiaName = this.getAttribute('data-baia-name');
                
                // Remove erro se existir
                showError('baia-error', false);
            });
        });
    }

    // Funções de validação
    function validateAnimalId(value) {
        const isValid = value.trim().length > 0;
        showError('animal-error', !isValid);
        return isValid;
    }

    function validateDataTransferencia(value) {
        const isValid = value !== '';
        showError('data-error', !isValid);
        return isValid;
    }

    function validateMatricula(value) {
        const isValid = value.trim().length > 0 && !isNaN(value) && parseInt(value) > 0;
        showError('matricula-error', !isValid);
        return isValid;
    }

    function validateTipoBaia() {
        const isValid = selectedBaiaType !== '';
        showError('tipo-baia-error', !isValid);
        return isValid;
    }

    function validateBaiaEspecifica() {
        const isValid = selectedBaiaId !== null;
        showError('baia-error', !isValid);
        return isValid;
    }

    // Função para exibir/ocultar erros
    function showError(errorId, show) {
        const errorElement = document.getElementById(errorId);
        if (errorElement) {
            errorElement.style.display = show ? 'block' : 'none';
        }

        // Adicionar classe invalid no campo correspondente
        const fieldMap = {
            'animal-error': 'animal-identificacao',
            'data-error': 'data-transferencia',
            'matricula-error': 'matricula-funcionario'
        };

        const fieldId = fieldMap[errorId];
        if (fieldId) {
            const field = document.getElementById(fieldId);
            if (field) {
                if (show) {
                    field.classList.add('invalid');
                } else {
                    field.classList.remove('invalid');
                }
            }
        }
    }

    // Toast de notificação
    function showToast(type, title, message) {
        const toastContainer = document.getElementById('toast-container');

        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;

        const iconClass = type === 'success' ? 'fa-check-circle' : 'fa-exclamation-circle';

        toast.innerHTML = `
            <i class="fas ${iconClass} toast-icon"></i>
            <div class="toast-content">
                <div class="toast-title">${title}</div>
                <div class="toast-message">${message}</div>
            </div>
            <button class="toast-close">&times;</button>
        `;

        toastContainer.appendChild(toast);

        // Adicionar evento de fechamento
        toast.querySelector('.toast-close').addEventListener('click', function() {
            toast.remove();
        });

        // Exibir o toast com animação
        setTimeout(() => {
            toast.classList.add('show');
        }, 10);

        // Remover o toast automaticamente após 5 segundos
        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => {
                toast.remove();
            }, 300);
        }, 5000);
    }

    // FUNÇÃO COMPLETAMENTE CORRIGIDA: Buscar dados do animal
    async function buscarAnimal(animalId) {
        try {
            const response = await fetch(`http://localhost:8080/apis/animal/${animalId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error(`Animal com ID ${animalId} não encontrado`);
            }

            const animalData = await response.json();
            console.log('Animal encontrado:', animalData);
            return animalData;
            
        } catch (error) {
            console.error('Erro ao buscar animal:', error);
            throw error;
        }
    }

    // FUNÇÃO COMPLETAMENTE CORRIGIDA: Enviar dados para a API
    async function saveTransferencia(transferenciaData) {
        try {
            console.log('Iniciando processo de transferência...');

            // ETAPA 1: Buscar dados do animal para obter baia origem
            console.log('1. Buscando dados do animal...');
            const animalData = await buscarAnimal(transferenciaData.animalId);
            
            // Assumindo que o animal retorna o campo da baia atual
            // Ajuste o nome do campo conforme sua API retorna
            const baiaOrigem = animalData.baiaId || animalData.baia_cod || animalData.Baia_baia_cod;
            
            if (!baiaOrigem) {
                throw new Error('Não foi possível determinar a baia atual do animal');
            }

            // ETAPA 2: Criar transferência básica
            console.log('2. Salvando transferência básica...');
            const jsonTransferencia = {
                data: transferenciaData.data,
                matFunc: transferenciaData.matFunc
            };

            console.log('Enviando dados para API de Transferência:', jsonTransferencia);

            const responseTransf = await fetch('http://localhost:8080/apis/transferencias/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(jsonTransferencia)
            });

            if (!responseTransf.ok) {
                const errorData = await responseTransf.text();
                console.error('Erro na resposta da API:', errorData);
                throw new Error(`Erro HTTP: ${responseTransf.status} - ${errorData}`);
            }

            const transferenciaResponse = await responseTransf.json();
            console.log('Transferência básica criada:', transferenciaResponse);

            // ETAPA 3: Obter ID da transferência criada
            console.log('3. Obtendo ID da transferência...');
            const transferenciaCriada = await buscarUltimaTransferencia(transferenciaData.matFunc);
            
            if (!transferenciaCriada || !transferenciaCriada.id) {
                throw new Error('Não foi possível obter o ID da transferência criada');
            }

            // ETAPA 4: Criar registro associativo
            console.log('4. Salvando dados associativos...');
            const jsonTransferenciaToBaia = {
                transfId: transferenciaCriada.id,
                aniId: parseInt(transferenciaData.animalId),
                baiaOrigem: parseInt(baiaOrigem),
                baiaDestino: parseInt(transferenciaData.baiaDestino)
            };

            console.log('Salvando dados associativos:', jsonTransferenciaToBaia);

            const responseDados = await fetch('http://localhost:8080/apis/transferencias/salvarDadosTransferencia/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(jsonTransferenciaToBaia)
            });

            if (!responseDados.ok) {
                const errorData = await responseDados.text();
                console.error('Erro na resposta da API de dados associativos:', errorData);
                throw new Error(`Erro HTTP: ${responseDados.status} - ${errorData}`);
            }

            const responseDataAssoc = await responseDados.json();
            console.log('Dados associativos salvos:', responseDataAssoc);

            // Limpar formulário
            resetForm();

            // Mensagem de sucesso
            showToast('success', 'Transferência Realizada', 
                `Animal "${transferenciaData.animalId}" transferido de "${baiaOrigem}" para "${selectedBaiaName}" com sucesso!`);

        } catch (erro) {
            console.error('Erro ao realizar transferência:', erro);
            showToast('error', 'Erro na Transferência', 
                `Ocorreu um erro ao realizar a transferência: ${erro.message}`);
        }
    }

    // FUNÇÃO AUXILIAR: Buscar última transferência criada por um funcionário
    async function buscarUltimaTransferencia(matFunc) {
        try {
            // Buscar todas as transferências (você pode precisar ajustar este endpoint)
            const response = await fetch('http://localhost:8080/apis/transferencias/lista', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Erro ao buscar transferências');
            }

            const transferencias = await response.json();
            
            // Encontrar a transferência mais recente do funcionário
            const transferenciasDoFunc = transferencias.filter(t => t.matFunc === matFunc);
            
            if (transferenciasDoFunc.length === 0) {
                throw new Error('Nenhuma transferência encontrada para este funcionário');
            }

            // Ordenar por data/ID e pegar a mais recente
            transferenciasDoFunc.sort((a, b) => {
                if (a.id && b.id) return b.id - a.id;
                return new Date(b.data) - new Date(a.data);
            });

            return transferenciasDoFunc[0];
            
        } catch (error) {
            console.error('Erro ao buscar última transferência:', error);
            throw error;
        }
    }

    // Handler para salvar - CORRIGIDO
    async function handleSave() {
        const animalInput = document.getElementById('animal-identificacao');
        const dataInput = document.getElementById('data-transferencia');
        const matriculaInput = document.getElementById('matricula-funcionario');

        console.log('Valores capturados:', {
            animal: animalInput.value,
            data: dataInput.value,
            matricula: matriculaInput.value,
            tipoBaia: selectedBaiaType,
            baiaId: selectedBaiaId,
            baiaNome: selectedBaiaName
        });

        // Validar todos os campos
        const animalValid = validateAnimalId(animalInput.value);
        const dataValid = validateDataTransferencia(dataInput.value);
        const matriculaValid = validateMatricula(matriculaInput.value);
        const tipoValid = validateTipoBaia();
        const baiaValid = validateBaiaEspecifica();

        // Se todos os campos forem válidos, enviar o formulário
        if (animalValid && dataValid && matriculaValid && tipoValid && baiaValid) {
            // Preparar dados conforme estrutura backend
            const transferenciaData = {
                data: new Date(dataInput.value).toISOString(),
                matFunc: parseInt(matriculaInput.value),
                animalId: animalInput.value.trim(),
                baiaDestino: selectedBaiaId
            };

            console.log('Dados da transferência a serem enviados:', transferenciaData);
            await saveTransferencia(transferenciaData);
        } else {
            console.log('Validação falhou:', {
                animalValid,
                dataValid,
                matriculaValid,
                tipoValid,
                baiaValid
            });
            showToast('error', 'Erro de Validação', 'Por favor, corrija os erros no formulário antes de continuar.');
        }
    }

    // Handler para cancelar
    function handleCancel() {
        if (confirm('Tem certeza que deseja cancelar? Todas as informações não salvas serão perdidas.')) {
            resetForm();
        }
    }

    // Resetar formulário
    function resetForm() {
        const form = document.getElementById('transferenciaForm');
        form.reset();

        // Resetar seleções
        selectedBaiaType = '';
        selectedBaiaId = null;
        selectedBaiaName = '';

        // Remover classes active
        document.querySelectorAll('.baia-type-btn').forEach(btn => btn.classList.remove('active'));
        document.querySelectorAll('.baia-card').forEach(card => card.classList.remove('selected'));

        // Limpar lista de baias
        document.getElementById('baia-list').innerHTML = '';

        // Remover mensagens de erro
        document.querySelectorAll('.error-text').forEach(element => {
            element.style.display = 'none';
        });

        // Remover classes de erro
        document.querySelectorAll('.form-control').forEach(element => {
            element.classList.remove('invalid');
        });

        // Resetar data padrão
        setDefaultDateTime();
    }
});