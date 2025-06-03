document.addEventListener('DOMContentLoaded', function() {
  // Elementos do DOM
  const animalSelect    = document.getElementById('ac-animal-select');
  const acDateInput     = document.getElementById('ac-date');
  const funcInput       = document.getElementById('ac-func');
  const registrarBtn    = document.getElementById('botaoRegistrar');   // botão de registrar
  const alterarBtn      = document.getElementById('botaoAlterar');     // botão de alterar

  // 1) Carrega animais disponíveis (sem acolhimento)
  async function loadAvailableAnimals() {
    if (!animalSelect) {
      console.error('Select de animais não encontrado (id ac-animal-select).');
      return;
    }
    try {
      const resp = await fetch("http://localhost:8080/apis/animal");
      if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
      const data = await resp.json();
      // Considera não acolhidos tanto null quanto zero
      const available = Array.isArray(data)
        ? data.filter(a => a.idAcolhimento == null || a.idAcolhimento === 0)
        : [];

      animalSelect.innerHTML = '<option value="">Selecione...</option>';
      available.forEach(a => {
        const nome = a.informacao?.nome || 'Sem nome';
        const opt = document.createElement('option');
        opt.value = a.id;
        opt.text = `${a.id} – ${nome}`;
        animalSelect.appendChild(opt);
      });

      if (available.length === 0) {
        animalSelect.innerHTML = '<option value="">Nenhum animal disponível</option>';
        animalSelect.disabled = true;
      }
    } catch (err) {
      console.error("Erro ao carregar animais disponíveis:", err);
      animalSelect.innerHTML = '<option value="">Erro ao carregar</option>';
      animalSelect.disabled = true;
    }
  }

  // 2) Função registrar acolhimento
  async function registrarAcolhimento() {
    const dataRaw    = acDateInput.value.trim();
    const funcRaw    = funcInput.value.trim();
    const animalRaw  = animalSelect.value;

    if (!dataRaw || !funcRaw || !animalRaw) {
      alert("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    const idFunc    = parseInt(funcRaw, 10);
    const idAnimal  = parseInt(animalRaw, 10);
    if (isNaN(idFunc) || isNaN(idAnimal)) {
      alert("IDs devem ser números válidos.");
      return;
    }

    const payload = { data: dataRaw, idFunc, idAnimal };

    try {
      const response = await fetch("http://localhost:8080/apis/acolhimento", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      if (!response.ok) {
        let errorText = await response.text();
        let errorMsg  = errorText;
        try { errorMsg = JSON.parse(errorText).mensagem; } catch {}
        if (errorMsg.includes("Animal com id")) {
          if (confirm(`${errorMsg}\nDeseja cadastrar um novo animal agora?`)) {
            window.location.href = "/pages/animal/pageCadastroAnimal.html";
          }
          return;
        }
        alert("Erro ao registrar acolhimento: " + errorMsg);
        return;
      }

      const result = await response.json();
      alert(`Acolhimento registrado com sucesso! ID: ${result.id}`);
      loadAvailableAnimals(); // atualiza lista após registro
    } catch (error) {
      alert("Erro ao registrar acolhimento: " + error.message);
      console.error("Erro na função registrarAcolhimento:", error);
    }
  }

  // 3) Função alterar acolhimento
  async function alterarAcolhimento() {
    const idAcolhimentoRaw = document.getElementById("ac-id").value.trim();
    const dataRaw          = acDateInput.value.trim();
    const funcRaw          = funcInput.value.trim();
    const animalRaw        = animalSelect.value;

    if (!idAcolhimentoRaw || !dataRaw || !funcRaw || !animalRaw) {
      alert("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    const idAcolhimento = parseInt(idAcolhimentoRaw, 10);
    const idFunc        = parseInt(funcRaw, 10);
    const idAnimal      = parseInt(animalRaw, 10);
    if (isNaN(idAcolhimento) || isNaN(idFunc) || isNaN(idAnimal)) {
      alert("IDs devem ser números válidos.");
      return;
    }

    const payload = { data: dataRaw, idFunc, idAnimal };

    try {
      const response = await fetch(`http://localhost:8080/apis/acolhimento/${idAcolhimento}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      if (!response.ok) {
        const errorText = await response.text();
        alert("Erro ao alterar acolhimento: " + errorText);
        return;
      }

      const result = await response.json();
      alert(`Acolhimento alterado com sucesso! ID: ${result.id || idAcolhimento}`);
      loadAvailableAnimals(); // atualiza select se desejado
    } catch (error) {
      alert("Erro ao alterar acolhimento: " + error.message);
      console.error("Erro na função alterarAcolhimento:", error);
    }
  }

  // Liga os botões se existirem
  if (registrarBtn) registrarBtn.addEventListener('click', registrarAcolhimento);
  if (alterarBtn)   alterarBtn.addEventListener('click', alterarAcolhimento);

  // Expor para onclick inline
  window.registrarAcolhimento = registrarAcolhimento;
  window.alterarAcolhimento  = alterarAcolhimento;

  // Inicializa carregando select
  loadAvailableAnimals();
});
