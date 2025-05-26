
/**
 * 
    FLUXO Usuario: 
    1- Inserir animal ou por codigo ou por nome
    2- Inserir data de ocorrencia
    3- Inserir codigo de funcionario (no caso o correto é carregar a sessão, mas ainda ta desativada aqui) 25/05/25 
    4- Escolher baia para mudança
    5- mover animal

    FLUXO Sistema:
    1- Recebe as informacoes ao apertar salvar
    2- Valida se os campos de inserção estao corretos
    2.1- Se animal existe
    2.2- Se animal esta dispinivel
    3- Realiza a transferencia para a baia de destino
    3.1- atualiza a quantidade dentro da baia, a baia destino aumenta a sua quantidade
    3.2- muda o local que esta dentro de animal
    3.3- atualiza a baia origem do animal, diminui a sua quantidade
    4- carrega os dados na associativa, colocando o id da transferencia, animal e data.

 */ 

document.addEventListener('DOMContentLoaded', async function() {
    
    // Variáveis globais
    let selectedBaiaType = '';
    let selectedBaiaId = null;
    let selectedBaiaName = '';

    // carregando as baias
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
        const trimmedValue = value.trim();
        var status = [];
        // Verificar se o campo não está vazio
        if (trimmedValue.length === 0) {
            showError('animal-error', true);
            return false;
        }

        // Verificar se é um número (ID)
        if (!isNaN(trimmedValue) && Number.isInteger(parseFloat(trimmedValue))) {
            const id = parseInt(trimmedValue);
            const isValidId = id > 0;
            showError('animal-error', !isValidId);
            status.push('valido');
            statsus.push('numero')
            return status;
        }
        
        // Verificar se é uma string válida (Nome)
        if (isNaN(trimmedValue)) {
            // Validar nome: apenas letras, espaços, números e alguns caracteres especiais
            const nomeRegex = /^[a-zA-ZÀ-ÿ0-9\s\-\_\.]+$/;
            const isValidNome = nomeRegex.test(trimmedValue) && trimmedValue.length >= 2;
            showError('animal-error', !isValidNome);
            status.push('valido');
            statsus.push('nome')
            return status;
        }

        // Se chegou até aqui, é inválido
        showError('animal-error', true);
        return false
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

    // FUNÇÃO CORRIGIDA: Buscar dados do animal
    async function buscarAnimal(animal,condicao) {
        try {
            var response;
            var animalData;
            if(condicao == "id"){
                response = await fetch(`http://localhost:8080/apis/animal/id/${animal}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                if (!response.ok) {
                    throw new Error(`Animal com ID ${animal} não encontrado`);
                }
                animalData = await response.json()
            }
            else{
                response = await fetch(`http://localhost:8080/apis/animal/nome/${animal}`, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }
                );

                if (!response.ok) {
                    throw new Error(`Animal com ID ${animal} não encontrado`);
                }
                animalData = await response.json();
            }
            console.log('Animal encontrado: ', animalData);
            return animalData;
                                                 
        } catch (error) {
            console.error('Erro ao buscar animal:', error);
            throw error;
        }
    }
    //Aqui ele deve retornar o objeto inteiro - id, data e func
    async function salvarRegistroTransferencia(data){
        let response = await fetch('http://localhost:8080/apis/transferencias/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        return response;
    }

    async function salvarDadosMovimentados(data){
        let response = await fetch(`http://localhost:8080/apis/transferencias/salvarDadosTransf/`,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        return response;
    }

    // FUNÇÃO COMPLETAMENTE CORRIGIDA: Enviar dados para a API
    async function saveTransferencia(transferenciaData) {
        try {
            console.log('Iniciando processo de transferência...');

            // ETAPA 1: Buscar dados do animal para obter baia origem
            console.log('1. Buscando dados do animal...');
            console.log("ID animal antes de busca:", transferenciaData.animalId);
            const animalData = await buscarAnimal(transferenciaData.animalId,"id");
            
            if(animalData){
                const baiaOrigem = animalData.idBaia;
                if (!baiaOrigem) {
                    throw new Error('Não foi possível determinar a baia atual do animal');
                }
                console.log('Baia atual do '+animalData.id+'encontrada:', baiaOrigem);



                console.log('2. Salvando transferência básica...');
                const jsonTransferencia = {
                    data: transferenciaData.data,
                    matFunc: transferenciaData.matFunc
                };
                console.log('Enviando dados para API de Transferência:', jsonTransferencia);

                const responseTransf = await salvarRegistroTransferencia(jsonTransferencia);

                if (!responseTransf.ok) {
                    const errorData = await responseTransf.text();
                    console.error('Erro na resposta da API:', errorData);
                    throw new Error(`Erro HTTP: ${responseTransf.status} - ${errorData}`);
                }

                const transferenciaResponse = await responseTransf.json();
                console.log('Dados de transferencia:', transferenciaResponse);

                // ETAPA 3: Buscar a transferência recém-criada para obter o ID
                console.log('3. Salvar registro do que foi movimentado');
            
                if (!transferenciaResponse || !transferenciaResponse.id) {
                    throw new Error('ID da transferência não foi retornado pela API');
                }
                const jsonTransferenciaToBaia = {
                    transfId: transferenciaResponse.id,
                    aniId: parseInt(transferenciaData.animalId),
                    baiaOrigem: parseInt(baiaOrigem),
                    baiaDestino: parseInt(transferenciaData.baiaDestino)
                };
               
                console.log('Dados a serem salvos:', jsonTransferenciaToBaia);
                const respostaSaveDados = await salvarDadosMovimentados(jsonTransferenciaToBaia);
                if (!respostaSaveDados.ok) {
                    const errorData = await respostaSaveDados.text();
                    console.error('Erro na resposta da API de dados associativos:', errorData);
                    throw new Error(`Erro HTTP: ${respostaSaveDados.status} - ${errorData}`);
                }

                const responseDataAssoc = await respostaSaveDados.json();
                console.log('Dados associativos salvos:', responseDataAssoc);
                // Limpar formulário
                resetForm();

                // MENSAGEM DE SUCESSO CORRIGIDA
                showToast('success', 'Transferência Realizada', 
                    `Animal "${transferenciaData.animalId}" transferido da baia "${baiaOrigem}" para "${selectedBaiaName}" com sucesso!`);


            }
            else
                showToast('error', 'Erro ao achar animal',`Ocorreu um erro ao buscar animal " ${transferenciaData.animalId}".Digite novamente..."`);            

        } catch (erro) {
            console.error('Erro ao realizar transferência:', erro);
            showToast('error', 'Erro na Transferência', 
                `Ocorreu um erro ao realizar a transferência: ${erro.message}`);
        }
    }

    async function retornaAnimal(valor){   
        const valorLimpo = valor.trim();
        
        if (valorLimpo.length === 0) {
            exibirErro('animal-error', 'Campo obrigatório');
            return false;
        }

        // Verificar se é um ID (número)
        if (VALIDATION_PATTERNS.id.test(valorLimpo)) {
            const id = parseInt(valorLimpo);
            const valido = id > 0;
            
            if (!valido) {
                exibirErro('animal-error', 'ID deve ser um número positivo');
            } else {
                ocultarErro('animal-error');
            }
            return valido;
        }
        
        // Verificar se é um nome válido
        if (VALIDATION_PATTERNS.nome.test(valorLimpo) && valorLimpo.length >= 1) {
            ocultarErro('animal-error');
            return true;
        }
        
        exibirErro('animal-error', 'Digite um ID válido ou nome do animal');
        return false;
    };
    // Handler para salvar - FINAL
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
        var status = [];
        status = validateAnimalId(animalInput.value);
        const condicaoAnimal = status[1];
        const animalValid = status[0];
        const dataValid = validateDataTransferencia(dataInput.value);
        const matriculaValid = validateMatricula(matriculaInput.value);
        const tipoValid = validateTipoBaia();
        const baiaValid = validateBaiaEspecifica();

        // Se todos os campos forem válidos, enviar o formulário
        if (animalValid && dataValid && matriculaValid && tipoValid && baiaValid) {

            var refAnimal = await retornaAnimal(animalInput.value);
            console.log('Valores refAnimal: '+ refAnimal.id);

            // Preparar dados conforme estrutura backend
            const transferenciaData = {
                data: new Date(dataInput.value).toISOString(),
                matFunc: parseInt(matriculaInput.value),
                animalId: refAnimal.id,
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