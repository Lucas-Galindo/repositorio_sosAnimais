document.addEventListener('DOMContentLoaded', function() {
        // Formatação de CPF
        const cpfInput = document.getElementById('person-cpf');
        cpfInput.addEventListener('input', function(e) {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length <= 11) {
                value = value.replace(/(\d{3})(\d)/, '$1.$2');
                value = value.replace(/(\d{3})(\d)/, '$1.$2');
                value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
                e.target.value = value;
            }
        });

        // Formatação de telefone
        const phoneInput = document.getElementById('person-phone');
        phoneInput.addEventListener('input', function(e) {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length <= 11) {
                if (value.length > 2) {
                    value = '(' + value.substring(0, 2) + ') ' + value.substring(2);
                }
                if (value.length > 10) {
                    value = value.substring(0, 10) + '-' + value.substring(10);
                }
                e.target.value = value;
            }
        });

        // Controle dos checkboxes de tipo de pessoa
        const checkboxes = document.querySelectorAll('input[name="person-type"]');
        const employeeSection = document.getElementById('employee-section');
        const funcionarioCheckbox = document.getElementById('funcionario-checkbox');
        const adotanteCheckbox = document.getElementById('adotante-checkbox');
        const doadorCheckbox = document.getElementById('doador-checkbox');

        // Permitir múltiplos checkboxes selecionados ao mesmo tempo
        checkboxes.forEach(checkbox => {
            checkbox.addEventListener('change', function() {
                // Mostrar/esconder seção de funcionário baseado no checkbox
                if (this.id === 'funcionario-checkbox') {
                    if (this.checked) {
                        employeeSection.classList.add('active');
                    } else {
                        employeeSection.classList.remove('active');
                    }
                }
                
                // Verificar se pelo menos um checkbox está marcado
                const anyChecked = Array.from(checkboxes).some(cb => cb.checked);
                document.getElementById('person-type-error').style.display = anyChecked ? 'none' : 'block';
            });
        });

        // Funções de validação
        function validateName(name) {
            return name.trim().length > 0;
        }

        function validateEmail(email) {
            const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return re.test(String(email).toLowerCase());
        }

        function validateCPF(cpf) {
            // Simplificado para este exemplo, apenas verifica se o CPF tem a formatação correta
            const re = /^\d{3}\.\d{3}\.\d{3}-\d{2}$|^\d{11}$/;
            return re.test(String(cpf));
        }

        function validatePhone(phone) {
            // Simplificado para este exemplo, apenas verifica se o telefone tem a formatação correta
            const re = /^\(\d{2}\) \d{5}-\d{4}$|^\(\d{2}\) \d{4}-\d{4}$|^\d{10,11}$/;
            return re.test(String(phone));
        }

        function validateEmployeeFields() {
            const loginValid = document.getElementById('employee-login').value.trim().length > 0;
            const passwordValid = document.getElementById('employee-password').value.trim().length > 0;
            
            showError('employee-login', 'login-error', !loginValid);
            showError('employee-password', 'password-error', !passwordValid);
            
            return loginValid && passwordValid;
        }

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

        // Validação em tempo real
        document.getElementById('person-name').addEventListener('blur', function() {
            showError('person-name', 'name-error', !validateName(this.value));
        });

        document.getElementById('person-email').addEventListener('blur', function() {
            showError('person-email', 'email-error', !validateEmail(this.value));
        });

        document.getElementById('person-cpf').addEventListener('blur', function() {
            showError('person-cpf', 'cpf-error', !validateCPF(this.value));
        });

        document.getElementById('person-phone').addEventListener('blur', function() {
            showError('person-phone', 'phone-error', !validatePhone(this.value));
        });

        document.getElementById('employee-login').addEventListener('blur', function() {
            if (funcionarioCheckbox.checked) {
                showError('employee-login', 'login-error', this.value.trim().length === 0);
            }
        });

        document.getElementById('employee-password').addEventListener('blur', function() {
            if (funcionarioCheckbox.checked) {
                showError('employee-password', 'password-error', this.value.trim().length === 0);
            }
        });

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

        // Não precisamos gerar matrícula, pois será gerada pelo banco de dados
        // A matrícula de funcionário é informada manualmente pelo usuário

        // Salvar o formulário
        const saveButton = document.getElementById('btn-save');
        saveButton.addEventListener('click', function() {
            const nameInput = document.getElementById('person-name');
            const emailInput = document.getElementById('person-email');
            const cpfInput = document.getElementById('person-cpf');
            const phoneInput = document.getElementById('person-phone');

            // Verificar se algum tipo de pessoa foi selecionado
            const anyChecked = Array.from(checkboxes).some(cb => cb.checked);
            if (!anyChecked) {
                document.getElementById('person-type-error').style.display = 'block';
                return;
            }

            // Validar todos os campos
            const nameValid = validateName(nameInput.value);
            const emailValid = validateEmail(emailInput.value);
            const cpfValid = validateCPF(cpfInput.value);
            const phoneValid = validatePhone(phoneInput.value);

            showError('person-name', 'name-error', !nameValid);
            showError('person-email', 'email-error', !emailValid);
            showError('person-cpf', 'cpf-error', !cpfValid);
            showError('person-phone', 'phone-error', !phoneValid);

            // Validar campos específicos de funcionário se o checkbox estiver marcado
            let employeeValid = true;
            if (funcionarioCheckbox.checked) {
                employeeValid = validateEmployeeFields();
            }

            // Se todos os campos forem válidos, enviar o formulário
            if (nameValid && emailValid && cpfValid && phoneValid && employeeValid) {
                // Preparar os dados base da pessoa
                const pessoaData = {
                    name: nameInput.value,
                    email: emailInput.value,
                    cpf: cpfInput.value.replace(/\D/g, ''), // Remove formatação
                    phone: phoneInput.value.replace(/\D/g, '') // Remove formatação
                };

                // Salvar a pessoa primeiro e depois salvar os diferentes papéis
                savePerson(pessoaData);
            }
        });

        ///////////////////////  FUNÇÃO DE PESQUISAR A PESSOA  /////////////////////////////

        function pesquisarCPF(pessoaData, local)
        {

            if(local == "funcionario"){
                fetch('http://localhost:8080/apis/pessoa/busca/' + pessoaData.Pess_CPF)
                .then(response => {
                    if (!response.ok) {
                    throw new Error('Pessoa não encontrada');
                    }
                    return response.json(); // transforma a resposta em objeto JS
                })
                .then(pessoa => {
                    // 2. Agora temos a pessoa, com ID
                    const pessoaId = pessoa.id;

                    // 3. Criar Funcionario com o ID da Pessoa
                    return fetch('http://localhost:8080/apis/funcionario', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        Func_Login: document.getElementById('employee-login').value,
                        Func_Senha: document.getElementById('employee-password').value,
                        Usu_ID: pessoaId // Aqui vai o ID real
                    })
                    });
                })
                .then(response => {
                    if (!response.ok) {
                    throw new Error('Erro ao salvar funcionário');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Funcionário salvo com sucesso:', data);
                })
                .catch(error => {
                    console.error('Erro:', error.message);
                });

            } 
            else if(local == "adotante"){

                
                fetch('http://localhost:8080/apis/pessoa/busca/' + pessoaData.Pess_CPF)
                .then(response => {
                    if (!response.ok) {
                    throw new Error('Pessoa não encontrada');
                    }
                    return response.json(); // transforma a resposta em objeto JS
                })
                .then(pessoa => {
                    // 2. Agora temos a pessoa, com ID
                    const pessoaId = pessoa.id;

                    // 3. Criar Funcionario com o ID da Pessoa
                    return fetch('http://localhost:8080/apis/adotante', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        Usu_ID: pessoaId // Aqui vai o ID real
                    })
                    });
                })
                .then(response => {
                    if (!response.ok) {
                    throw new Error('Erro ao salvar funcionário');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Funcionário salvo com sucesso:', data);
                })
                .catch(error => {
                    console.error('Erro:', error.message);
                });
            }
            else if(local == "doador"){
                if(local == "funcionario"){
                fetch('http://localhost:8080/apis/pessoa/busca/' + pessoaData.Pess_CPF)
                .then(response => {
                    if (!response.ok) {
                    throw new Error('Pessoa não encontrada');
                    }
                    return response.json(); // transforma a resposta em objeto JS
                })
                .then(pessoa => {
                    // 2. Agora temos a pessoa, com ID
                    const pessoaId = pessoa.id;

                    // 3. Criar doador com o ID da Pessoa
                    return fetch('http://localhost:8080/apis/doador', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        Usu_ID: pessoaId // Aqui vai o ID real
                    })
                    });
                })
                .then(response => {
                    if (!response.ok) {
                    throw new Error('Erro ao salvar funcionário');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Funcionário salvo com sucesso:', data);
                })
                .catch(error => {
                    console.error('Erro:', error.message);
                });

            }
            
        
        }
    }
        ///////////////////////////////////////////////////////////////////////////////////


        // Função para enviar os dados para a API
        function savePerson(pessoaData) {
            // Primeiro, salvar a pessoa
            fetch('http://localhost:8080/apis/pessoa', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    Pess_Nome: pessoaData.name,
                    Pess_CPF: pessoaData.cpf,
                    Pess_Telefone: pessoaData.phone,
                    Pess_Email: pessoaData.email
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erro HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then(pessoaResponseData => {
                console.log('Pessoa cadastrada com sucesso:', pessoaResponseData);
                
                // Array para armazenar todas as promessas de salvamento dos tipos de pessoa
                const savePromises = [];
                // Se for funcionário, salvar os dados de funcionário
                /*if (funcionarioCheckbox.checked) {
                    var pessoaAux = pesquisarCPF(pessoaData.Pess_CPF);
                    const funcionarioPromise = fetch('http://localhost:8080/apis/funcionario', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            Func_Login: document.getElementById('employee-login').value,
                            Func_Senha: document.getElementById('employee-password').value,
                            Usu_ID: pessoaAux
                        })
                    });
                    savePromises.push(funcionarioPromise);
                }*/
                
                // Se for adotante, salvar os dados de adotante
                /*if (adotanteCheckbox.checked) {
                    const adotantePromise = fetch('http://localhost:8080/apis/adotante', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            Usu_ID: pessoaResponseData.Pess_ID
                        })
                    });
                    savePromises.push(adotantePromise);
                }
                
                // Se for doador, salvar os dados de doador
                if (doadorCheckbox.checked) {
                    const doadorPromise = fetch('http://localhost:8080/apis/doador', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            Usu_Id: pessoaResponseData.Pess_ID
                        })
                    });
                    savePromises.push(doadorPromise);
                }*/
                if (funcionarioCheckbox.checked){
                    pesquisarCPF(pessoaData, "funcionario");
                    savePromises.push(funcionarioPromise);
                }   
                if (adotanteCheckbox.checked){
                    pesquisarCPF(pessoaData, "adotante");
                    savePromises.push(adotantePromise);
                }
                if (doadorCheckbox.checked){
                    pesquisarCPF(pessoaData, "doador");
                    savePromises.push(doadorPromise);
                }
                
                // Executar todas as promessas de salvamento
                return Promise.all(savePromises);
            })
            .then(results => {
                console.log('Todos os registros foram salvos com sucesso:', results);
                
                // Limpar o formulário
                document.getElementById('personForm').reset();
                
                // Esconder a seção de funcionário
                employeeSection.classList.remove('active');
                
                // Construir a mensagem de sucesso
                let tiposPessoa = [];
                if (funcionarioCheckbox.checked) tiposPessoa.push('Funcionário');
                if (adotanteCheckbox.checked) tiposPessoa.push('Adotante');
                if (doadorCheckbox.checked) tiposPessoa.push('Doador');
                
                const tiposMsg = tiposPessoa.join(', ');
                
                // Mostrar mensagem de sucesso
                showToast('success', 'Cadastro Realizado', `${pessoaData.name} cadastrado(a) com sucesso como ${tiposMsg}!`);
            })
            .catch(error => {
                console.error('Erro ao cadastrar:', error);
                
                // Mostrar mensagem de erro
                showToast('error', 'Erro no Cadastro', 'Ocorreu um erro ao cadastrar. Por favor, tente novamente.');
            });
        }

        // Cancelar o formulário
        const cancelButton = document.getElementById('btn-cancel');
        cancelButton.addEventListener('click', function() {
            if (confirm('Tem certeza que deseja cancelar? Todas as informações não salvas serão perdidas.')) {
                const form = document.getElementById('personForm');
                form.reset();

                // Esconder a seção de funcionário
                employeeSection.classList.remove('active');

                // Remover mensagens de erro
                document.querySelectorAll('.error-text').forEach(element => {
                    element.style.display = 'none';
                });

                // Remover classes de erro
                document.querySelectorAll('.form-control').forEach(element => {
                    element.classList.remove('invalid');
                });

                // Redirecionar ou fazer outra ação
                // window.location.href = 'listagem-pessoas.html';
            }
        });
    });