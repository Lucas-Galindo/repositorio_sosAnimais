document.addEventListener('DOMContentLoaded', function() {
       
 
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
                //console.log("Entrou no if de campos validos");
                const pessoaData = {
                    name: nameInput.value,
                    email: emailInput.value,
                    cpf: cpfInput.value.replace(/\D/g, ''), // Remove formatação
                    phone: phoneInput.value.replace(/\D/g, '') // Remove formatação
                };
                //console.log(pessoaData);

                // Salvar a pessoa primeiro e depois salvar os diferentes papéis
                savePerson(pessoaData);
            }
        });


        // Funções para salvar os diferentes tipos de pessoa
        function salvarFuncionario(pessoaId) {
            return fetch('http://localhost:8080/apis/funcionario', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: pessoaId,
                    login: document.getElementById('employee-login').value,
                    senha: document.getElementById('employee-password').value,
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao salvar funcionário');
                }
                return response.json();
            })
            .then(data => {
                console.log('Funcionário salvo com sucesso:', data);
                return data;
            });
        }

        function salvarAdotante(pessoaId) {
            return fetch('http://localhost:8080/apis/adotante', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: pessoaId
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao salvar adotante');
                }
                return response.json();
            })
            .then(data => {
                console.log('Adotante salvo com sucesso:', data);
                return data;
            });
        }

        function salvarDoador(pessoaId) {
            return fetch('http://localhost:8080/apis/doador', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                   id: pessoaId
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao salvar doador');
                }
                return response.json();
            })
            .then(data => {
                console.log('Doador salvo com sucesso:', data);
                return data;
            });
        }



        // Função para enviar os dados para a API
        async function savePerson(pessoaData) {
            try {
                // Cadastrar pessoa mantendo a estrutura requerida
                const response = await fetch('http://localhost:8080/apis/pessoa', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        "pessoa": {
                            nome: pessoaData.name,
                            cpf: pessoaData.cpf,
                            telefone: pessoaData.phone,
                            email: pessoaData.email
                        }
                    })
                });
                
                if (!response.ok) {
                    throw new Error(`Erro HTTP: ${response.status}`);
                }
                
                const pessoaResponseData = await response.json();
                console.log('Resposta da API:', pessoaResponseData);
                
                // Tentar acessar o ID em diferentes possíveis locais na estrutura
                let pessoaId;  
                //console.log("Não foi possível encontrar o ID diretamente, tentando buscar por CPF...");
                const cpf = pessoaData.cpf;
                const pessoaPorCPF = await pesquisarCPF(cpf);
                pessoaId = pessoaPorCPF.id;
                
                
                if (!pessoaId) {
                    throw new Error("Não foi possível obter o ID da pessoa");
                }
                console.log('ID da pessoa:', pessoaId);
                
                // Executar em paralelo todas as operações necessárias
                const promises = [];
                
                if (funcionarioCheckbox.checked) {
                    promises.push(salvarFuncionario(pessoaId));
                }
                
                if (adotanteCheckbox.checked) {
                    promises.push(salvarAdotante(pessoaId));
                }
                
                if (doadorCheckbox.checked) {
                    promises.push(salvarDoador(pessoaId));
                }
                // Aguardar todas as operações
                const resultados = promises.length > 0 ? await Promise.all(promises) : [];
                // Concluir com sucesso
                console.log('Cadastros realizados com sucesso:', resultados);
                // Limpar formulário
                document.getElementById('personForm').reset();
                employeeSection.classList.remove('active');
                
                // Mensagem de sucesso
                let tiposPessoa = [];
                if (funcionarioCheckbox.checked) tiposPessoa.push('Funcionário');
                if (adotanteCheckbox.checked) tiposPessoa.push('Adotante');
                if (doadorCheckbox.checked) tiposPessoa.push('Doador');
                
                const tiposMsg = tiposPessoa.join(', ');
                showToast('success', 'Cadastro Realizado', `${pessoaData.name} cadastrado(a) com sucesso como ${tiposMsg}!`);
                
            } catch (erro) {
                console.error('Erro ao cadastrar:', erro);
                showToast('error', 'Erro no Cadastro', `Ocorreu um erro ao cadastrar: ${erro.message}`);
            }
        }

        ///////////////////////  FUNÇÃO DE PESQUISAR A PESSOA  /////////////////////////////

        function pesquisarCPF(cpf) {
            return fetch(`http://localhost:8080/apis/pessoa/busca-cpf/${cpf}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Erro ao buscar pessoa por CPF: ${response.status}`);
                }
                return response.json();
            });
        }
        ///////////////////////////////////////////////////////////////////////////////////

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