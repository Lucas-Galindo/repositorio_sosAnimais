document.addEventListener('DOMContentLoaded', function() {
    // Variáveis globais
    let selectedBaiaType = '';
    let selectedBaiaId = null;
    let selectedBaiaName = '';

    // Dados simulados das baias (em produção, viriam de uma API)
    /*const baiasData = {
        comum: [
            { id: 1, nome: 'Baia_Comum_01', ocupada: false, categoria: 'Comum' },
            { id: 2, nome: 'Baia_Comum_02', ocupada: true, categoria: 'Comum' },
            { id: 3, nome: 'Baia_Comum_03', ocupada: false, categoria: 'Comum' },
            { id: 4, nome: 'Baia_Comum_04', ocupada: false, categoria: 'Comum' },
            { id: 5, nome: 'Baia_Comum_05', ocupada: true, categoria: 'Comum' }
        ],
        medica: [
            { id: 6, nome: 'Baia_Medica_01', ocupada: false, categoria: 'Médica' },
            { id: 7, nome: 'Baia_Medica_02', ocupada: false, categoria: 'Médica' },
            { id: 8, nome: 'Baia_Medica_03', ocupada: true, categoria: 'Médica' },
            { id: 9, nome: 'Baia_Medica_04', ocupada: false, categoria: 'Médica' }
        ]
    };*/

    const baiasData = [];

    const medica = fetch(`http://localhost:8080/apis/baias/categoria/Medica`,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            return response.json();
        })
    });

    baiasData.push(medica);

    const comum = fetch(`http://localhost:8080/apis/baias/categoria/Comum`,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            return response.json();
        })
    });
    baiasData.push(comum);

    // Inicialização
    init();

    function init() {
        setupEventListeners();
        setDefaultDateTime();
    }

    // Configurar todos os event listeners
    function setupEventListeners() {
        setupMenuDropdowns();
        setupSidebarToggle();
        setupFormValidation();
        setupBaiaTypeSelector();
        setupFormActions();
    }

    // JavaScript para controlar os dropdowns do menu
    function setupMenuDropdowns() {
        const menuButtons = document.querySelectorAll('.menu-button');

        menuButtons.forEach(button => {
            button.addEventListener('click', function() {
                const dropdownMenu = this.nextElementSibling;
                const dropdownIcon = this.querySelector('.dropdown-icon');

                if (dropdownMenu && dropdownIcon) {
                    dropdownMenu.classList.toggle('active');
                    dropdownIcon.classList.toggle('active');

                    // Fecha outros dropdowns abertos
                    document.querySelectorAll('.dropdown-menu.active').forEach(menu => {
                        if (menu !== dropdownMenu) {
                            menu.classList.remove('active');
                            menu.previousElementSibling.querySelector('.dropdown-icon').classList.remove('active');
                        }
                    });
                }
            });
        });
    }

    // Toggle para sidebar em dispositivos móveis
    function setupSidebarToggle() {
        const sidebarToggle = document.getElementById('sidebarToggle');
        const sidebar = document.getElementById('sidebar');

        if (sidebarToggle && sidebar) {
            sidebarToggle.addEventListener('click', function() {
                sidebar.classList.toggle('active');
            });

            // Fechar a sidebar quando clicar fora dela
            document.addEventListener('click', function(event) {
                const isClickInsideSidebar = sidebar.contains(event.target);
                const isClickOnToggle = sidebarToggle.contains(event.target);

                if (!isClickInsideSidebar && !isClickOnToggle && sidebar.classList.contains('active') && window.innerWidth <= 768) {
                    sidebar.classList.remove('active');
                }
            });
        }
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

    // Carregar baias do tipo selecionado
    function loadBaias(tipo) {
        const baiaList = document.getElementById('baia-list');
        const baias = baiasData[tipo] || [];

        // Limpar seleção anterior
        selectedBaiaId = null;
        selectedBaiaName = '';

        if (baias.length === 0) {
            baiaList.innerHTML = '<p class="help-text">Nenhuma baia disponível para este tipo.</p>';
            return;
        }

        baiaList.innerHTML = baias.map(baia => `
            <div class="baia-card ${baia.ocupada ? 'disabled' : ''}" data-baia-id="${baia.id}" data-baia-name="${baia.nome}">
                <div class="baia-name">${baia.nome}</div>
                <div class="baia-info">
                    ${baia.ocupada ? 'Ocupada' : 'Disponível'}
                </div>
            </div>
        `).join('');

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

    // Função para enviar dados para a API
    async function saveTransferencia(transferenciaData) {
        try {
            console.log('Enviando dados para API:', transferenciaData);

            const response = await fetch('http://localhost:8080/apis/transferencias', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(transferenciaData)
            });

            if (!response.ok) {
                const errorData = await response.text();
                console.error('Erro na resposta da API:', errorData);
                throw new Error(`Erro HTTP: ${response.status} - ${errorData}`);
            }

            const responseData = await response.json();
            console.log('Transferência realizada com sucesso:', responseData);

            // Limpar formulário
            resetForm();

            // Mensagem de sucesso
            showToast('success', 'Transferência Realizada', 
                `Animal "${transferenciaData.animalId}" transferido para "${selectedBaiaName}" com sucesso!`);

        } catch (erro) {
            console.error('Erro ao realizar transferência:', erro);
            showToast('error', 'Erro na Transferência', 
                `Ocorreu um erro ao realizar a transferência: ${erro.message}`);
        }
    }

    // Handler para salvar
    function handleSave() {
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
                // id será gerado automaticamente no backend
                data: new Date(dataInput.value).toISOString(),
                matFunc: parseInt(matriculaInput.value),
                // Dados adicionais para contexto (não necessariamente enviados ao backend)
                animalId: animalInput.value.trim(),
                baiaDestino: selectedBaiaId,
                baiaNome: selectedBaiaName,
                tipoBaia: selectedBaiaType
            };

            console.log('Dados da transferência a serem enviados:', transferenciaData);
            saveTransferencia(transferenciaData);
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

    // Carregar baias iniciais (opcional - pode ser chamado na inicialização se necessário)
    // loadBaias('comum');
});