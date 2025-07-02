// Configuração da API
const API_BASE_URL = 'http://localhost:8080/apis';

// Elementos do DOM
const form = document.getElementById('serviceForm');
const serviceNameInput = document.getElementById('service-name');
const serviceDescriptionInput = document.getElementById('service-description');
const btnSave = document.getElementById('btn-save');
const btnCancel = document.getElementById('btn-cancel');

// Validações
const validators = {
    serviceName: {
        element: serviceNameInput,
        errorElement: document.getElementById('name-error'),
        validate: function(value) {
            if (!value || value.trim().length < 2) {
                return 'O nome do serviço deve ter pelo menos 2 caracteres.';
            }
            if (value.length > 100) {
                return 'O nome do serviço não pode ter mais de 100 caracteres.';
            }
            return null;
        }
    },
    serviceDescription: {
        element: serviceDescriptionInput,
        errorElement: document.getElementById('description-error'),
        validate: function(value) {
            if (value && value.length > 500) {
                return 'A descrição não pode ter mais de 500 caracteres.';
            }
            return null;
        }
    }
};

// Funções de validação
function validateField(fieldName) {
    const validator = validators[fieldName];
    if (!validator) return true;

    const value = validator.element.value.trim();
    const error = validator.validate(value);

    if (error) {
        validator.element.classList.add('invalid');
        validator.errorElement.textContent = error;
        validator.errorElement.style.display = 'block';
        return false;
    } else {
        validator.element.classList.remove('invalid');
        validator.errorElement.style.display = 'none';
        return true;
    }
}

function validateForm() {
    let isValid = true;
    
    Object.keys(validators).forEach(fieldName => {
        if (!validateField(fieldName)) {
            isValid = false;
        }
    });

    return isValid;
}

// Event listeners para validação em tempo real
serviceNameInput.addEventListener('blur', () => validateField('serviceName'));
serviceNameInput.addEventListener('input', () => {
    if (serviceNameInput.classList.contains('invalid')) {
        validateField('serviceName');
    }
});

serviceDescriptionInput.addEventListener('blur', () => validateField('serviceDescription'));
serviceDescriptionInput.addEventListener('input', () => {
    if (serviceDescriptionInput.classList.contains('invalid')) {
        validateField('serviceDescription');
    }
});

// Funções de notificação (Toast)
function showToast(type, title, message) {
    const toastContainer = document.getElementById('toast-container');
    const toastId = 'toast-' + Date.now();
    
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.id = toastId;
    
    toast.innerHTML = `
        <div class="toast-icon">
            <i class="fas ${type === 'success' ? 'fa-check-circle' : 'fa-exclamation-circle'}"></i>
        </div>
        <div class="toast-content">
            <div class="toast-title">${title}</div>
            <div class="toast-message">${message}</div>
        </div>
        <button class="toast-close" onclick="closeToast('${toastId}')">
            <i class="fas fa-times"></i>
        </button>
    `;
    
    toastContainer.appendChild(toast);
    
    // Mostrar o toast
    setTimeout(() => {
        toast.classList.add('show');
    }, 100);
    
    // Auto remover após 5 segundos
    setTimeout(() => {
        closeToast(toastId);
    }, 5000);
}

function closeToast(toastId) {
    const toast = document.getElementById(toastId);
    if (toast) {
        toast.classList.remove('show');
        setTimeout(() => {
            toast.remove();
        }, 300);
    }
}

// Funções de loading
function setLoading(isLoading) {
    if (isLoading) {
        btnSave.classList.add('loading');
        btnSave.disabled = true;
        btnSave.innerHTML = '<i class="fas fa-spinner"></i> Salvando...';
    } else {
        btnSave.classList.remove('loading');
        btnSave.disabled = false;
        btnSave.innerHTML = '<i class="fas fa-save"></i> Salvar Serviço';
    }
}

// Função para salvar serviço
async function saveService() {
    if (!validateForm()) {
        showToast('error', 'Erro de Validação', 'Por favor, corrija os erros no formulário.');
        return;
    }

    const serviceData = {
        nome: serviceNameInput.value.trim(),
        descricao: serviceDescriptionInput.value.trim() || null
    };

    setLoading(true);

    try {
        const response = await fetch(`${API_BASE_URL}/servico`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(serviceData)
        });

        if (response.ok) {
            const result = await response.json();
            showToast('success', 'Sucesso!', 'Serviço cadastrado com sucesso.');
            
            // Limpar formulário
            form.reset();
            
            // Remover classes de erro
            Object.values(validators).forEach(validator => {
                validator.element.classList.remove('invalid');
                validator.errorElement.style.display = 'none';
            });

            // Opcional: redirecionar após alguns segundos
            setTimeout(() => {
                // window.location.href = 'lista-servicos.html';
            }, 2000);

        } else {
            const errorData = await response.json();
            const errorMessage = errorData.mensagem || 'Erro ao cadastrar serviço.';
            showToast('error', 'Erro', errorMessage);
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        showToast('error', 'Erro de Conexão', 'Não foi possível conectar ao servidor. Tente novamente.');
    } finally {
        setLoading(false);
    }
}

// Event listeners
btnSave.addEventListener('click', saveService);

btnCancel.addEventListener('click', () => {
    if (form.checkValidity() && (serviceNameInput.value.trim() || serviceDescriptionInput.value.trim())) {
        if (confirm('Você tem alterações não salvas. Deseja realmente cancelar?')) {
            form.reset();
            // window.location.href = 'lista-servicos.html';
        }
    } else {
        form.reset();
        // window.location.href = 'lista-servicos.html';
    }
});

// Validação ao submeter formulário
form.addEventListener('submit', (e) => {
    e.preventDefault();
    saveService();
});

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    // Focar no primeiro campo
    serviceNameInput.focus();
    
    console.log('Página de cadastro de serviços carregada');
});