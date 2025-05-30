document.addEventListener('DOMContentLoaded', function() {
    // DOM elements
    const form = document.getElementById('personForm');
    const personIdInput = document.getElementById('person-id');
    const nameInput = document.getElementById('person-name');
    const cpfInput = document.getElementById('person-cpf');
    const emailInput = document.getElementById('person-email');
    const phoneInput = document.getElementById('person-phone');
    
    const funcionarioCheckbox = document.getElementById('funcionario-checkbox');
    const adotanteCheckbox = document.getElementById('adotante-checkbox');
    const doadorCheckbox = document.getElementById('doador-checkbox');
    
    const funcionarioFields = document.getElementById('funcionario-fields');
    const adotanteFields = document.getElementById('adotante-fields');
    const doadorFields = document.getElementById('doador-fields');
    
    const funcMatriculaInput = document.getElementById('func-matricula');
    const funcLoginInput = document.getElementById('func-login');
    const funcSenhaInput = document.getElementById('func-senha');
    const funcSenhaConfirmInput = document.getElementById('func-senha-confirm');
    const adotanteMatriculaInput = document.getElementById('adotante-matricula');
    const doadorMatriculaInput = document.getElementById('doador-matricula');
    
    const cancelButton = document.getElementById('btn-cancel');
    const saveButton = document.getElementById('btn-save');
    const loadingOverlay = document.getElementById('loading-overlay');

    // Verificar se os elementos existem
    if (!adotanteMatriculaInput) {
        console.error('Elemento adotante-matricula não encontrado!');
    }
    if (!doadorMatriculaInput) {
        console.error('Elemento doador-matricula não encontrado!');
    }

    // Variáveis para controlar estado atual dos tipos
    let currentState = {
        isFuncionario: false,
        isAdotante: false,
        isDoador: false,
        funcionarioData: null,
        adotanteData: null,
        doadorData: null
    };

    // Format functions
    function formatCPF(cpf) {
        cpf = cpf.replace(/\D/g, '');
        if (cpf.length === 11) {
            return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
        }
        return cpf;
    }

    function formatPhone(phone) {
        phone = phone.replace(/\D/g, '');
        if (phone.length === 11) {
            return phone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
        } else if (phone.length === 10) {
            return phone.replace(/(\d{2})(\d{4})(\d{4})/, '($1) $2-$3');
        }
        return phone;
    }

    // Validation functions
    function validateCPF(cpf) {
        cpf = cpf.replace(/\D/g, '');
        return cpf.length === 11;
    }

    function validateEmail(email) {
        const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return re.test(email);
    }

    function validatePhone(phone) {
        phone = phone.replace(/\D/g, '');
        return phone.length >= 10 && phone.length <= 11;
    }

    // Format input function
    function formatInput(input, formatFn) {
        const start = input.selectionStart;
        const end = input.selectionEnd;
        const oldValue = input.value;
        const newValue = formatFn(oldValue);
        
        if (oldValue !== newValue) {
            input.value = newValue;
            const newPos = start + (newValue.length - oldValue.length);
            input.setSelectionRange(newPos, newPos);
        }
    }

    // Show/hide fields
    function toggleTypeFields() {
        funcionarioFields.style.display = funcionarioCheckbox.checked ? 'block' : 'none';
        adotanteFields.style.display = adotanteCheckbox.checked ? 'block' : 'none';
        doadorFields.style.display = doadorCheckbox.checked ? 'block' : 'none';
        
        const anyChecked = funcionarioCheckbox.checked || adotanteCheckbox.checked || doadorCheckbox.checked;
        document.getElementById('person-type-error').style.display = anyChecked ? 'none' : 'block';
    }

    // Show error function
    function showError(fieldId, errorId, show) {
        const field = document.getElementById(fieldId);
        const errorElement = document.getElementById(errorId);
        
        if (show) {
            field.classList.add('invalid');
            errorElement.style.display = 'block';
        } else {
            field.classList.remove('invalid');
            errorElement.style.display = 'none';
        }
    }

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

        toast.querySelector('.toast-close').addEventListener('click', function() {
            toast.remove();
        });

        setTimeout(() => {
            toast.classList.add('show');
        }, 10);

        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => {
                toast.remove();
            }, 300);
        }, 5000);
    }

    // Loading functions
    function showLoading() {
        loadingOverlay.classList.add('active');
    }

    function hideLoading() {
        loadingOverlay.classList.remove('active');
    }

    // CORREÇÃO PRINCIPAL: Função para verificar tipos de pessoa
    async function checkPersonTypes() {
        try {
            const requestOptions = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                mode: 'cors'
            };
            
            // Reset current state
            currentState = {
                isFuncionario: false,
                isAdotante: false,
                isDoador: false,
                funcionarioData: null,
                adotanteData: null,
                doadorData: null
            };

            // Check funcionario
            try {
                const funcionarioResponse = await fetch(`http://localhost:8080/apis/funcionario/busca-pessoa/${personId}`, requestOptions);
                if (funcionarioResponse.ok) {
                    const funcionarioData = await funcionarioResponse.json();
                    console.log('Dados do funcionário:', funcionarioData);
                    
                    currentState.isFuncionario = true;
                    currentState.funcionarioData = funcionarioData;
                    
                    funcionarioCheckbox.checked = true;
                    funcionarioFields.style.display = 'block';
                    
                    if (funcMatriculaInput) funcMatriculaInput.value = funcionarioData.matricula || '';
                    if (funcLoginInput) funcLoginInput.value = funcionarioData.login || '';
                    // Não carregar senha por segurança
                }
            } catch (err) {
                console.log('Não é funcionário ou erro:', err);
            }
            
            // Check adotante - CORREÇÃO AQUI
            try {
                const adotanteResponse = await fetch(`http://localhost:8080/apis/adotante/busca-pessoa/${personId}`, requestOptions);
                if (adotanteResponse.ok) {
                    const adotanteData = await adotanteResponse.json();
                    console.log('Dados do adotante:', adotanteData);
                    
                    currentState.isAdotante = true;
                    currentState.adotanteData = adotanteData;
                    
                    adotanteCheckbox.checked = true;
                    adotanteFields.style.display = 'block';
                    
                    // CORREÇÃO: usar .value em vez de atribuir diretamente à variável
                    if (adotanteMatriculaInput) {
                        adotanteMatriculaInput.value = adotanteData.matricula || '';
                    }
                }
            } catch (err) {
                console.log('Não é adotante ou erro:', err);
            }
            
            // Check doador
            try {
                const doadorResponse = await fetch(`http://localhost:8080/apis/doador/busca-pessoa/${personId}`, requestOptions);
                if (doadorResponse.ok) {
                    const doadorData = await doadorResponse.json();
                    console.log('Dados do doador:', doadorData);
                    
                    currentState.isDoador = true;
                    currentState.doadorData = doadorData;
                    
                    doadorCheckbox.checked = true;
                    doadorFields.style.display = 'block';
                    
                    if (doadorMatriculaInput) {
                        doadorMatriculaInput.value = doadorData.matricula || '';
                    }
                }
            } catch (err) {
                console.log('Não é doador ou erro:', err);
            }

            console.log('Relacionado ao currentState: ', currentState);
            
        } catch (error) {
            console.error('Erro ao verificar tipos:', error);
            showToast('error', 'Erro', 'Falha ao verificar os tipos da pessoa');
        }
    }

    // CORREÇÃO: Função para gerenciar funcionário
    async function manageFuncionario(checkado) {
        if (checkado) {
            // Verificar se os campos obrigatórios existem e têm valores
            if (!funcLoginInput || !funcLoginInput.value.trim()) {
                throw new Error('Login do funcionário é obrigatório');
            }

            // Criar ou atualizar funcionário
            const funcionarioData = {
                id: parseInt(personIdInput.value),
                pessoa: null,
                matricula: currentState.funcionarioData ? currentState.funcionarioData.matricula : 0,
                login: funcLoginInput.value.trim(),
                senha: funcSenhaInput && funcSenhaInput.value ? funcSenhaInput.value : ""
            };

            if (currentState.isFuncionario) {
                // Atualizar funcionário existente
                funcionarioData.matricula = currentState.funcionarioData.matricula;
                
                const response = await fetch(`http://localhost:8080/apis/funcionario`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    mode: 'cors',
                    body: JSON.stringify(funcionarioData)
                });
                
                if (!response.ok) {
                    throw new Error(`Erro ao atualizar funcionário: ${response.status}`);
                }
            } else {
                // Criar novo funcionário
                const response = await fetch(`http://localhost:8080/apis/funcionario`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    mode: 'cors',
                    body: JSON.stringify(funcionarioData)
                });
                
                if (!response.ok) {
                    throw new Error(`Erro ao criar funcionário: ${response.status}`);
                }
            }
        } else if (currentState.isFuncionario) {
            // Remover funcionário
            const response = await fetch(`http://localhost:8080/apis/funcionario/exclusao-pessoa/${personId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                mode: 'cors'
            });
            
            if (!response.ok) {
                throw new Error(`Erro ao remover funcionário: ${response.status}`);
            }
        }
    }

    // CORREÇÃO: Função para gerenciar adotante
    async function manageAdotante(shouldBeAdotante) {
        try {
            if (shouldBeAdotante) {
                if (currentState.isAdotante) {
                    // Já é adotante, não precisa fazer nada
                    console.log('Pessoa já é adotante, mantendo registro');
                    return;
                } else {
                    // Criar novo adotante
                    const adotanteData = {
                        id: parseInt(personIdInput.value)
                    };

                    const response = await fetch(`http://localhost:8080/apis/adotante`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json'
                        },
                        mode: 'cors',
                        body: JSON.stringify(adotanteData)
                    });
                    
                    if (!response.ok) {
                        const errorText = await response.text();
                        throw new Error(`Erro ao criar adotante: ${response.status} - ${errorText}`);
                    }
                    
                    console.log('Adotante criado com sucesso');
                }
            } else if (currentState.isAdotante) {
                // Remover adotante
                const response = await fetch(`http://localhost:8080/apis/adotante/exclusao-pessoa/${personId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    mode: 'cors'
                });
                
                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error(`Erro ao remover adotante: ${response.status} - ${errorText}`);
                }
                
                console.log('Adotante removido com sucesso');
            }
        } catch (error) {
            console.error('Erro em manageAdotante:', error);
            throw error;
        }
    }

    // CORREÇÃO: Função para gerenciar doador
    async function manageDoador(shouldBeDoador) {
        try {
            if (shouldBeDoador) {
                if (currentState.isDoador) {
                    // Já é doador, não precisa fazer nada
                    console.log('Pessoa já é doador, mantendo registro');
                    return;
                } else {
                    // Criar novo doador
                    const doadorData = {
                        id: parseInt(personIdInput.value)
                    };

                    const response = await fetch(`http://localhost:8080/apis/doador`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json'
                        },
                        mode: 'cors',
                        body: JSON.stringify(doadorData)
                    });
                    
                    if (!response.ok) {
                        const errorText = await response.text();
                        throw new Error(`Erro ao criar doador: ${response.status} - ${errorText}`);
                    }
                    
                    console.log('Doador criado com sucesso');
                }
            } else if (currentState.isDoador) {
                // Remover doador
                const response = await fetch(`http://localhost:8080/apis/doador/exclusao-pessoa/${personId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                    },
                    mode: 'cors'
                });
                
                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error(`Erro ao remover doador: ${response.status} - ${errorText}`);
                }
                
                console.log('Doador removido com sucesso');
            }
        } catch (error) {
            console.error('Erro em manageDoador:', error);
            throw error;
        }
    }

    // Event listeners
    funcionarioCheckbox.addEventListener('change', toggleTypeFields);
    adotanteCheckbox.addEventListener('change', toggleTypeFields);
    doadorCheckbox.addEventListener('change', toggleTypeFields);

    // Input validation event listeners
    nameInput.addEventListener('input', function() {
        if (this.value.length > 30) {
            this.value = this.value.slice(0, 30);
        }
        showError('person-name', 'name-error', this.value.length < 5 || this.value.length > 30);
    });

    cpfInput.addEventListener('input', function() {
        formatInput(this, formatCPF);
        const cpfValue = this.value.replace(/\D/g, '');
        showError('person-cpf', 'cpf-error', cpfValue.length > 0 && cpfValue.length !== 11);
    });

    emailInput.addEventListener('input', function() {
        showError('person-email', 'email-error', this.value.trim() !== '' && !validateEmail(this.value));
    });

    phoneInput.addEventListener('input', function() {
        formatInput(this, formatPhone);
        const phoneValue = this.value.replace(/\D/g, '');
        showError('person-phone', 'phone-error', 
            phoneValue.length > 0 && (phoneValue.length < 10 || phoneValue.length > 11));
    });

    if (funcSenhaInput) {
        funcSenhaInput.addEventListener('input', function() {
            if (funcSenhaConfirmInput && funcSenhaConfirmInput.value) {
                showError('func-senha-confirm', 'func-senha-confirm-error', 
                    this.value !== funcSenhaConfirmInput.value);
            }
        });
    }
    
    if (funcSenhaConfirmInput) {
        funcSenhaConfirmInput.addEventListener('input', function() {
            if (this.value) {
                showError('func-senha-confirm', 'func-senha-confirm-error', 
                    this.value !== funcSenhaInput.value);
            } else {
                showError('func-senha-confirm', 'func-senha-confirm-error', false);
            }
        });
    }

    // Get person ID from URL
    const urlParams = new URLSearchParams(window.location.search);
    const personId = urlParams.get('id');
    console.log('Id '+personId);

    if (!personId) {
        showToast('error', 'Erro', 'ID da pessoa não fornecido!');
        setTimeout(() => {
            window.location.href = 'pageBuscaPessoa.html';
        }, 2000);
        return;
    }

    // Load person data
    async function loadPersonData() {
        try {
            showLoading();
            
            const requestOptions = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                mode: 'cors'
            };
            
            // Fetch basic person data
            const personResponse = await fetch(`http://localhost:8080/apis/pessoa/busca-id/${personId}`, requestOptions);
            
            if (!personResponse.ok) {
                throw new Error(`Erro ao buscar dados da pessoa: ${personResponse.status}`);
            }
            
            const personData = await personResponse.json();
            console.log('Dados da pessoa carregados:', personData);
            
            // Fill in basic form fields
            personIdInput.value = personId;
            nameInput.value = personData.pessoa.nome || '';
            cpfInput.value = formatCPF(personData.pessoa.cpf || '');
            emailInput.value = personData.pessoa.email || '';
            phoneInput.value = formatPhone(personData.pessoa.telefone || '');
            
            // Check person types
            await checkPersonTypes();
            
        } catch (error) {
            console.error('Erro ao carregar dados:', error);
            showToast('error', 'Erro', `Falha ao carregar dados: ${error.message}`);
        } finally {
            hideLoading();
        }
    }

    // Form submit handler
    form.addEventListener('submit', async function(event) {
        event.preventDefault();
        
        // Validação
        let isValid = true;
        let errorMessages = [];
        
        const name = nameInput.value.trim();
        if (name.length < 5 || name.length > 30) {
            showError('person-name', 'name-error', true);
            isValid = false;
            errorMessages.push('Nome deve ter entre 5 e 30 caracteres');
        } else {
            showError('person-name', 'name-error', false);
        }
        
        const cpf = cpfInput.value;
        const cpfDigits = cpf.replace(/\D/g, '');
        if (!validateCPF(cpf) && cpfDigits.length !== 11) {
            showError('person-cpf', 'cpf-error', true);
            isValid = false;
            errorMessages.push('CPF inválido');
        } else {
            showError('person-cpf', 'cpf-error', false);
        }
        
        const email = emailInput.value.trim();
        if (!validateEmail(email)) {
            showError('person-email', 'email-error', true);
            isValid = false;
            errorMessages.push('Email inválido');
        } else {
            showError('person-email', 'email-error', false);
        }
        
        const phone = phoneInput.value;
        if (!validatePhone(phone)) {
            showError('person-phone', 'phone-error', true);
            isValid = false;
            errorMessages.push('Telefone inválido');
        } else {
            showError('person-phone', 'phone-error', false);
        }
        
        const anyChecked = funcionarioCheckbox.checked || adotanteCheckbox.checked || doadorCheckbox.checked;
        if (!anyChecked) {
            document.getElementById('person-type-error').style.display = 'block';
            isValid = false;
            errorMessages.push('Selecione pelo menos um tipo de pessoa');
        } else {
            document.getElementById('person-type-error').style.display = 'none';
        }
        
        if (funcionarioCheckbox.checked && funcSenhaInput && funcSenhaInput.value) {
            if (funcSenhaInput.value !== funcSenhaConfirmInput.value) {
                showError('func-senha-confirm', 'func-senha-confirm-error', true);
                isValid = false;
                errorMessages.push('As senhas não coincidem');
            } else {
                showError('func-senha-confirm', 'func-senha-confirm-error', false);
            }
        }
        
        if (!isValid) {
            showToast('error', 'Erro de Validação', errorMessages.join(', '));
            return;
        }
        
        try {
            showLoading();
            let tel = phone.replace(/\D/g,'')
            
            // 1. Atualizar dados básicos da pessoa
            const updatePersonResponse = await fetch(`http://localhost:8080/apis/pessoa`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                mode: 'cors',
                body: JSON.stringify({
                    "id": parseInt(personIdInput.value),
                    "pessoa": {
                        "nome": `${name}`,
                        "cpf": `${cpfDigits}`,
                        "email": `${email}`,
                        "telefone": `${tel}`
                    }
                })
            });
            
            if (!updatePersonResponse.ok) {
                throw new Error(`Erro ao atualizar dados básicos de pessoa: ${updatePersonResponse.status}`);
            }
            
            // 2. Gerenciar tipos em sequência para evitar conflitos
            console.log('Gerenciando funcionário...');
            await manageFuncionario(funcionarioCheckbox.checked);
            
            console.log('Gerenciando adotante...');
            await manageAdotante(adotanteCheckbox.checked);
            
            console.log('Gerenciando doador...');
            await manageDoador(doadorCheckbox.checked);
            
            showToast('success', 'Sucesso', 'Dados atualizados com sucesso!');
            
            setTimeout(() => {
                window.location.href = 'pageBuscaPessoa.html';
            }, 2000);
            
        } catch (error) {
            console.error('Erro ao salvar:', error);
            showToast('error', 'Erro', `Falha ao atualizar dados: ${error.message}`);
        } finally {
            hideLoading();
        }
    });
    
    // Cancel button handler
    cancelButton.addEventListener('click', function() {
        if (confirm('Tem certeza que deseja cancelar? Todas as alterações serão perdidas.')) {
            window.location.href = 'pageBuscaPessoa.html';
        }
    });

    // Load person data when page loads
    loadPersonData();
});