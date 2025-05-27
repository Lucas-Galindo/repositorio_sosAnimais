async function registrarAcolhimento() {
  // Pega os valores do formulário
  const data = document.getElementById("ac-date").value.trim();
  const idFuncRaw = document.getElementById("ac-func").value.trim();
  const idAnimalRaw = document.getElementById("ac-id-animal").value.trim();

  // Validação simples
  if (!data || !idFuncRaw || !idAnimalRaw) {
    alert("Por favor, preencha todos os campos obrigatórios.");
    return;
  }

  const idFunc = parseInt(idFuncRaw);
  const idAnimal = parseInt(idAnimalRaw);

  if (isNaN(idFunc) || isNaN(idAnimal)) {
    alert("IDs devem ser números válidos.");
    return;
  }

  const payload = {
    data: data,
    idFunc: idFunc,
    idAnimal: idAnimal
  };

  try {
    const response = await fetch("http://localhost:8080/apis/acolhimento", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (!response.ok) {
      const errorText = await response.text();

      // Tenta interpretar como JSON para pegar a mensagem
      let errorObj;
      try {
        errorObj = JSON.parse(errorText);
      } catch {
        errorObj = null;
      }

      // Verifica se o erro é de animal não encontrado
      if (errorObj && errorObj.mensagem && errorObj.mensagem.includes("Animal com id")) {
        const wantToRegister = confirm(
          `${errorObj.mensagem} Deseja cadastrar um novo animal agora?`
        );
        if (wantToRegister) {
          // Redireciona para página de cadastro (altere para a sua URL real)
          window.location.href = "/pages/animal/pageCadastroAnimal.html";
        }
        return;
      }

      alert("Erro ao registrar acolhimento: " + errorText);
      return;
    }

    const result = await response.json();
    alert(`Acolhimento registrado com sucesso! ID: ${result.id}`);
    // Opcional: limpar formulário, atualizar lista, etc.
  } catch (error) {
    alert("Erro ao registrar acolhimento: " + error.message);
    console.error("Erro na função registrarAcolhimento:", error);
  }
}


async function alterarAcolhimento() {
  // Pega os valores do formulário
  const idAcolhimentoRaw = document.getElementById("ac-id").value.trim();
  const data = document.getElementById("ac-date").value.trim();
  const idFuncRaw = document.getElementById("ac-func").value.trim();
  const idAnimalRaw = document.getElementById("ac-id-animal").value.trim();

  // Validação simples
  if (!idAcolhimentoRaw || !data || !idFuncRaw || !idAnimalRaw) {
    alert("Por favor, preencha todos os campos obrigatórios.");
    return;
  }

  const idAcolhimento = parseInt(idAcolhimentoRaw);
  const idFunc = parseInt(idFuncRaw);
  const idAnimal = parseInt(idAnimalRaw);

  if (isNaN(idAcolhimento) || isNaN(idFunc) || isNaN(idAnimal)) {
    alert("IDs devem ser números válidos.");
    return;
  }

  // Monta o payload JSON conforme esperado
  const payload = {
    data: data,
    idFunc: idFunc,
    idAnimal: idAnimal
  };

  try {
    const response = await fetch(`http://localhost:8080/apis/acolhimento/${idAcolhimento}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
      redirect: "follow"
    });

    if (!response.ok) {
      const errorText = await response.text();
      alert("Erro ao alterar acolhimento: " + errorText);
      return;
    }

    const result = await response.json();
    alert(`Acolhimento alterado com sucesso! ID: ${result.id || idAcolhimento}`);
    // Opcional: limpar formulário, atualizar lista, etc.
  } catch (error) {
    alert("Erro ao alterar acolhimento: " + error.message);
    console.error("Erro na função alterarAcolhimento:", error);
  }
}