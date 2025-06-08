
/**
 * 
    FLUXO Usuario: 
    1- Inserir animal ou por codigo ou por nome
    2- Inserir data de ocorrencia
    3- Inserir codigo de funcionario (no caso o correto é carregar a sessão, mas ainda ta desativada aqui) 25/05/25 
    4- Escolher baia para mudança
    5- mover animal

    FLUXO Sistema:
    1- Recebe as informacoes ao apertar salvar - feito
    2- Valida se os campos de inserção estao corretos - feito
    2.1- Se animal existe - feito
    2.2- Se animal esta dispinivel - feito
    3- Realiza a transferencia para a baia de destino 
    3.1- atualiza a quantidade dentro da baia, a baia destino aumenta a sua quantidade
    3.2- muda o local que esta dentro de animal
    3.3- atualiza a baia origem do animal, diminui a sua quantidade
    4- carrega os dados na associativa, colocando o id da transferencia, animal e data.

 */ 

     // Variáveis globais
    let selectedBaiaType = '';
    let selectedBaiaId = null;
    let selectedBaiaName = '';
    let baiasData = [];

    const VALIDATION_PATTERNS = {
        nomeAnimal: /^[a-zA-ZÀ-ÿ0-9\s\-\_\.]+$/,
        matricula: /^\d+$/,
        id: /^\d+$/ // ← ADICIONADO
    };



    const urlPadrao = 'http://localhost:8080/apis/';

    //==================== MODULO DE BAIAS =========================

    async function carregarBaias(){
        // carregando as baias
        baiasData = [];

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
    }

    
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


    function botoesBaia() {
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


    //==========================================================================================


    
    //==================== MODULO DE MANUTENÇÃO ESPECIFICOS =========================

    // Função utilitária para fazer requisições GET
    async function fetchAnimalData(url, erroMensagem) {
        const response = await fetch(url, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });

        if (!response.ok) {
            throw new Error(erroMensagem);
        }

        return await response;
    }

    async function buscarAnimal(animal, condicao) {
        try {
            
            var url;
            //var animalData;

            if (condicao === "id") {
                //const url = `${baseUrl}/id/${animal}`;
                url = `http://localhost:8080/apis/animal/id/${animal}`;
                const response = await fetchAnimalData(url, `Animal não encontrado`);
                const animalData = await response.json();
                console.log('Animal encontrado: ', animalData);
                return await animalData;
                 
            } else {
                url = `http://localhost:8080/apis/animal/nome/${animal}`;
                const response = await fetchAnimalData(url, `Animal não encontrado`);
                const animalData = await response.json();
                console.log('Animal encontrado: ', animalData);
                return await animalData;
            }
 

        } catch (error) {
            console.error('Erro ao buscar animal:', error);
            throw error;
        
        }
    }

    async function retornaAnimal(valor) {
        const valorLimpo = valor.trim();

        if (valorLimpo.length === 0) {
            showError('animal-error', 'Campo obrigatório');
            return null;
        }

        try {
            let animalData;
        
            if (VALIDATION_PATTERNS.id.test(valorLimpo)) {
                animalData = await buscarAnimal(valorLimpo, "id"); 
            } else {
                animalData = await buscarAnimal(valorLimpo, "nome"); 
            }

            showError('animal-error', false);
            console.log("Retorno animal: ", animalData);
            return await animalData;
           
        } catch (erro) {
            showError('animal-error', erro.message);
            return null;
        }
    }

    
    async function configurarAnimal(valor){
        try{
            const animal = await buscarAnimal(valor.aniId,"id");
         
            console.log('Retorno animal - configurar: ',animal);
            if (!animal) { // ← VERIFICAÇÃO ADEQUADA
                showToast('error', 'Animal não encontrado', `Animal com ID "${valor.aniId}" não foi localizado.`);
                return null;
            }
            const response = await fetch(`http://localhost:8080/apis/animal/${animal.id}`,{
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({               
                    "idBaia": valor.baiaDestino,
                    "idAcolhimento": animal.idAcolhimento,
                    "informacao": {
                        "raca": animal.informacao.raca,
                        "nome": animal.informacao.nome,
                        "idade": animal.informacao.idade,
                        "status": animal.informacao.status,
                        "statusVida": animal.informacao.statusVida
                    }

                })
                
            });

            if(!response.ok){
                showToast('error', 'Animal não disponível', `Animal com ID não foi localizado.`);
                return; 
            }

            return await response.json();

        }catch (erro){
            showError('animal-error', erro.message);
            return null;
        }
        
    }

    //Provavelmente tem que colocar um request body tambem la no controller 
    async function atualizarBaia(id,condicao){

        try{

            let url;
            if(condicao === "Decremento"){
                url = `http://localhost:8080/apis/baia/atualizar-ocupacao/menos/${id}`;
            }
            else if(condicao === "Incremento")
                url = `http://localhost:8080/apis/baia/atualizar-ocupacao/mais/${id}`;

            const response = await fetch(url,{
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
            });
            
            if(!response.ok){
                const errorText = await response.text();
                showToast('error','Erro ao atualizar a baia',`Erro: ${errorText}`);
                return null;
            }
        }catch(erro){
            showToast('error','Problemas ao atuaizar baia')
            return;
        }
        
        
    }


    //Aqui ele carrega o json 
    async function configurarBaia(valor){
        const responseOrigem = await fetch(`http://localhost:8080/apis/baia/${valor.baiaOrigem}`,{
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });
        if(!responseOrigem.ok){
            showToast('error', 'Erro ao decrementar');
            return;
        }
       
        const retornoBaiaOrigem = await atualizarBaia(valor.baiaOrigem,"Decremento");
        if(!retornoBaiaOrigem.ok){
            console.log("Decremento nao feito");
            return;
        }
        console.log("Decremento realizado");
        

        //Destino
        const responseDestino = fetch(`http://localhost:8080/apis/baia/${valor.baiaDestino}`,{
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });

        if(!responseDestino.ok){
           showToast('error', 'Erro ao encontrar baia');
           return null;
        }
        const retornoBaiaDestino = await atualizarBaia(valor.baiaDestino,"Incremento");
        if(!retornoBaiaDestino){
            //Isso aqui é para caso der errado ele incrementa a baia origem la
            showToast('error', 'Erro ao incrementar baia destino');
            await atualizar(valor.baiaOrigem,"Incremento");
            return null;
        }
        console.log("Incremento realizado");

        
        return {
            origem: retornoBaiaOrigem,
            destino: retornoBaiaDestino,
            sucesso: true
        }


    }

    //====================================================================





    //==================== MODULO DE VALIDACAO E CAMPOS DE FORMA GERAL =========================

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
        const animalValid = validateAnimalId(animalInput.value);
        const dataValid = validateDataTransferencia(dataInput.value);
        const matriculaValid = validateMatricula(matriculaInput.value);
        const tipoValid = validateTipoBaia();
        const baiaValid = validateBaiaEspecifica();
        

        // Se todos os campos forem válidos, enviar o formulário
        if (animalValid && dataValid && matriculaValid && tipoValid && baiaValid) {

            const refAnimal = await retornaAnimal(animalInput.value);

            if (!refAnimal || !refAnimal.id) {
                showToast('error', 'Erro', 'Animal não encontrado ou dados inválidos');
                return;
            }
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
        setDataHoraLocal();
    }
    
    function validateAnimalId(value) {
        const trimmedValue = value.trim();

         if (trimmedValue.length === 0) {
            return false; // Boolean
        }
        

        if (!isNaN(trimmedValue) && Number.isInteger(parseFloat(trimmedValue))) {
            const id = parseInt(trimmedValue);
            const isValidId = id > 0;
            showError('animal-error', !isValidId);
            return isValidId;
        }

        const isValidNome = VALIDATION_PATTERNS.nomeAnimal.test(trimmedValue) && trimmedValue.length >= 2;
        showError('animal-error', !isValidNome);
        return isValidNome;
    }



    function validateDataTransferencia(value) {
        const isValid = value !== '';
        showError('data-error', !isValid);
        return isValid;
    }

    function validateMatricula(value) {
        const isValid = VALIDATION_PATTERNS.matricula.test(value.trim());
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

    function capturaDadosCampo(){
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

    // Configurar ações do formulário
    function setAcoesUsuario() {
        const saveButton = document.getElementById('btn-save');
        const cancelButton = document.getElementById('btn-cancel');

        saveButton.addEventListener('click', handleSave);
        cancelButton.addEventListener('click', handleCancel);
    }

    //==============================================================

    //==================== MODULO DE ERROS =========================

    
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

    function ocultarErro(errorId) {
        showError(errorId, false);
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


    //===============================================================================



    //==================== MODULO DE OPERACAO - TRANSFERENCIA =========================
    //Aqui ele deve retornar o objeto inteiro - id, data e func
    async function salvarRegistroTransferencia(data) {
        const response = await fetch(`http://localhost:8080/apis/transferencias/`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        return response;
    }


    async function salvarDadosMovimentados(data) {
        const response = await fetch(`http://localhost:8080/apis/transferencias/salvarDadosTransf/`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        return response;
    }

    async function saveTransferencia(transferenciaData) {
        try {
            console.log('Iniciando processo de transferência...');
            const animalData = await buscarAnimal(transferenciaData.animalId, "id");

            

            if(animalData.informacao.status!='D'){
                showToast('error', 'Animal não disponível', `Animal com ID "${transferenciaData.animalId}" não foi localizado.`);
                return;
            }

            if(animalData.informacao.statusVida!='V'){
                showToast('error', 'Animal não disponível', `Animal com ID "${transferenciaData.animalId}" não foi localizado.`);
                return;
            }

            if (!animalData  || !animalData.id) {
                showToast('error', 'Animal não encontrado', `Animal com ID "${transferenciaData.animalId}" não foi localizado.`);
                return;
            }

            const baiaOrigem = animalData.idBaia;
            if (!baiaOrigem) {
                throw new Error('Baia de origem do animal não encontrada.');
            }

            console.log(`Baia atual do animal ${animalData.id}: ${baiaOrigem}`);

            const jsonTransferencia = {
                data: transferenciaData.data,
                matFunc: transferenciaData.matFunc
            };

            console.log('Dados Transferencia: ',jsonTransferencia);

            const responseTransf = await salvarRegistroTransferencia(jsonTransferencia);
            if (!responseTransf.ok) {
                const errorData = await responseTransf.text();
                throw new Error(`Erro ao salvar transferência: ${errorData}`);
            }

            const transferenciaResponse = await responseTransf.json();
            if (!transferenciaResponse?.id) {
                throw new Error('ID da transferência não retornado pela API');
            }

            const jsonTransferenciaToBaia = {
                transfId: transferenciaResponse.id,
                aniId: parseInt(transferenciaData.animalId, 10),
                baiaOrigem: parseInt(baiaOrigem, 10),
                baiaDestino: parseInt(transferenciaData.baiaDestino, 10)
            };

            console.log('Dados Registro em associativa: ',jsonTransferenciaToBaia);
            const respostaSaveDados = await salvarDadosMovimentados(jsonTransferenciaToBaia);
            if (!respostaSaveDados.ok) {
                const errorData = await respostaSaveDados.text();
                throw new Error(`Erro ao salvar dados associativos: ${errorData}`);
            }

            const responseDataAssoc = await respostaSaveDados.json();
            console.log('Dados associativos salvos:', responseDataAssoc);

            const responstaConfigurarAnimal = await configurarAnimal(jsonTransferenciaToBaia);
            console.log('Animal alterado: ', responstaConfigurarAnimal);
            const respostaAtualizarBaia = await configurarBaia(jsonTransferenciaToBaia);
            console.log('Baia alterada: ',respostaAtualizarBaia);

            resetForm();

            // Corrigido: você precisa definir ou passar "selectedBaiaName"
            const baiaDestinoNome = transferenciaData.baiaDestinoNome || 'baia destino';

            showToast('success', 'Transferência Realizada',
                `Animal "${animalData.nome}" transferido da baia "${baiaOrigem}" para "${baiaDestinoNome}" com sucesso!`
            );

        } catch (erro) {
            console.error('Erro ao realizar transferência:', erro);
            showToast('error', 'Erro na Transferência',
                `Ocorreu um erro ao realizar a transferência: ${erro.message}`);
        }
    }



    //======================================================================

    function setupCampos(){
        //Aqui ele ta chamando as validações tambem
        capturaDadosCampo();
    }

    // REALIZANDO SET EM HORA E DATA NO DIA ATUAL
    function setDataHoraLocal() {
        const now = new Date();
        now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
        document.getElementById('data-transferencia').value = now.toISOString().slice(0, 16);
    }



    async function init(){
        await carregarBaias();
        await botoesBaia();
        setDataHoraLocal();
        setupCampos();
        setAcoesUsuario();        
    }




document.addEventListener('DOMContentLoaded', async function(){
    init();
});


