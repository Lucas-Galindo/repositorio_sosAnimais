// Funções de validação
function validateName(name) {
    return name.trim().length >= 3;
}

function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(String(email).toLowerCase());
}
function validateCPF(cpf) {
    const value = cpf.replace(/\D/g, '');
    if (value.length !== 11) return false;

    // Verificação se todos os dígitos são iguais (CPF inválido, mas com formato válido)
    if (/^(\d)\1+$/.test(value)) return false;

    // Validação do primeiro dígito verificador
    let sum = 0;
    for (let i = 0; i < 9; i++) {
        sum += parseInt(value.charAt(i)) * (10 - i);
    }
    let remainder = sum % 11;
    let dv1 = remainder < 2 ? 0 : 11 - remainder;

    // Validação do segundo dígito verificador
    sum = 0;
    for (let i = 0; i < 10; i++) {
        sum += parseInt(value.charAt(i)) * (11 - i);
    }
    remainder = sum % 11;
    let dv2 = remainder < 2 ? 0 : 11 - remainder;
    return (parseInt(value.charAt(9)) === dv1 && parseInt(value.charAt(10)) === dv2);
}
function validatePhone(phone) {
    const value = phone.replace(/\D/g, '');
    return value.length >= 10 && value.length <= 11;
}


const cpfInput = document.getElementById('person-cpf');
if (cpfInput) {
    cpfInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, ''); // Remove caracteres não numéricos

        if (value.length > 11) {
            value = value.substring(0, 11);
        }

        if (value.length > 9) {
            value = value.replace(/(\d{3})(\d{3})(\d{3})(\d{1,2})/, '$1.$2.$3-$4');
        } else if (value.length > 6) {
            value = value.replace(/(\d{3})(\d{3})(\d{0,3})/, '$1.$2.$3');
        } else if (value.length > 3) {
            value = value.replace(/(\d{3})(\d{0,3})/, '$1.$2');
        }

        e.target.value = value;
    });
}

        // Formatação de telefone
const phoneInput = document.getElementById('person-phone');
if (phoneInput) {
    phoneInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, ''); // Remove caracteres não numéricos

        if (value.length > 11) {
            value = vasubstring(0, 11);
        }

        if (value.length > 10) {
            value = value.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
        } else if (value.length > 6) {
            value = value.replace(/(\d{2})(\d{4})(\d{0,4})/, '($1) $2-$3');
        } else if (value.length > 2) {
            value = value.replace(/(\d{2})(\d{0,5})/, '($1) $2');
        }

        e.target.value = value;
    });
}
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
    });