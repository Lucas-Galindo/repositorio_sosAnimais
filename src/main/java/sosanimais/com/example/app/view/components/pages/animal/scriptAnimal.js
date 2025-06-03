async function editarAnimal() {
  // Captura os valores do formulário
  const idAnimalRaw = document.getElementById("animal-id").value.trim();
  const idBaiaRaw = document.getElementById("baia-id-fk").value.trim(); // corrigido idBaia
  const nome = document.getElementById("animal-name").value.trim();
  const raca = document.getElementById("animal-breed").value.trim();
  const descricao = document.getElementById("animal-description").value.trim();
  const idadeRaw = document.getElementById("animal-age").value.trim();
  const status = document.getElementById("animal-type").value;
  const statusVida = document.getElementById("animal-size").value;

  // Validação básica de campos obrigatórios
  if (!idAnimalRaw || !nome || !status || !statusVida || !idadeRaw) {
    alert("Por favor, preencha todos os campos obrigatórios corretamente.");
    return;
  }

  // Convertendo para número e validando
  const idAnimal = parseInt(idAnimalRaw);
  const idBaia = parseInt(idBaiaRaw);
  const idade = parseInt(idadeRaw);

  if (isNaN(idAnimal) || isNaN(idade)) {
    alert("ID do animal, ID da baia e idade devem ser números válidos.");
    return;
  }

  // Monta o payload conforme esperado pela API
  const payload = {
    idBaia: idBaia,
    idAcolhimento: null, // ajustar se precisar
    informacao: {
      nome: nome,
      raca: raca || "",
      descricao: descricao || "",
      idade: idade,
      status: status,
      statusVida: statusVida,
    }
  };

  try {
    const response = await fetch(`http://localhost:8080/apis/animal/${idAnimal}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    if (!response.ok) {
      const errorText = await response.text();
      alert("Erro ao editar animal: " + errorText);
      return;
    }

    const result = await response.json();
    alert("Animal editado com sucesso! ID: " + (result.id || idAnimal));
    // Opcional: limpar formulário, atualizar lista, etc.
  } catch (error) {
    alert("Erro ao editar animal: " + error.message);
    console.error("Erro na função editarAnimal:", error);
  }
}

async function cadastrarAnimal() {
    const idB = parseInt(document.getElementById("baia-id-fk").value);
    const nome = document.getElementById("animal-name").value.trim();
    const raca = document.getElementById("animal-breed").value.trim();
    const descricao = document.getElementById("animal-description").value.trim();
    const idade = parseInt(document.getElementById("animal-age").value);
    const status = document.getElementById("animal-type").value;
    const statusVida = document.getElementById("animal-size").value;
  
    // Validação simples (pode expandir conforme necessidade)
    if ( !status || !statusVida || isNaN(idade)) {
      alert("Por favor, preencha todos os campos obrigatórios corretamente.");
      return;
    }

    const payload = {
      idBaia: idB,           // ou outro valor fixo/dinâmico conforme seu contexto
      idAcolhimento: null, // pode adaptar se precisar
      informacao: {
        nome: nome,
        raca: raca || "",       // aceita vazio para SRD
        descricao: descricao || "",
        idade: idade,
        status: status,
        statusVida: statusVida,
      },
    };
  
    try {
      const response = await fetch("http://localhost:8080/apis/animal", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
  
      if (!response.ok) {
        const errorText = await response.text();
        alert("Erro ao cadastrar animal: " + errorText);
        return;
      }
  
      const result = await response.json();
      alert("Animal cadastrado com sucesso!");
      // Limpar formulário, atualizar lista ou outras ações
    } catch (error) {
      alert("Erro ao cadastrar animal: " + error.message);
      console.error("Erro no fetch cadastrarAnimal:", error);
    }
  }

async function findAnimal(event) {
  if (event) event.preventDefault();

  // Pega os elementos do DOM que vamos usar
  const input = document.getElementById("animalIdInput");
  const table = document.getElementById("results-table");
  const resultsContainer = document.getElementById("results-container");
  const resultsCount = document.getElementById("results-count");
  const noResults = document.getElementById("no-results");

  // Mostra o container de resultados e esconde a mensagem de "nenhum resultado"
  resultsContainer.style.display = 'block';
  noResults.style.display = "none";

  // Pega o texto digitado pelo usuário (chave)
  const chave = input.value.trim();

  // Limpa a tabela
  let tbody = table.tBodies[0] || table.createTBody();
  tbody.innerHTML = "";

  if (!chave) {
    resultsCount.innerText = "0 animais encontrados";
    noResults.querySelector("h3").innerText = "Por favor, digite um termo válido.";
    noResults.style.display = "block";
    return;
  }

  // Função que valida se string é número inteiro positivo
  function isNumeric(value) {
    return /^\d+$/.test(value);
  }

  // Descobre o tipo da busca (id ou nome)
  const tipoBusca = document.querySelector('input[name="search-type"]:checked')?.value;

  // Validação extra: se for busca por ID, o termo deve ser numérico
  if (tipoBusca === "idAnimail" && !isNumeric(chave)) {
    resultsCount.innerText = "0 animais encontrados";
    noResults.querySelector("h3").innerText = "Por favor, digite um ID numérico válido.";
    noResults.style.display = "block";
    return;
  }

  resultsCount.innerText = "Carregando...";

  try {
    // Monta a URL conforme o tipo de busca
    let url;
    if (tipoBusca === "idAnimail") {
      url = `http://localhost:8080/apis/animal/id/${encodeURIComponent(chave)}`;
    } else if (tipoBusca === "name") {
      url = `http://localhost:8080/apis/animal/nome/${encodeURIComponent(chave)}`;
    } else {
      throw new Error("Tipo de busca inválido.");
    }

    // Faz a requisição
    const response = await fetch(url);

    if (!response.ok) {
      // Se não deu certo, mostra mensagem e para
      resultsCount.innerText = "0 animais encontrados";
      const msg = await response.text();
      noResults.querySelector("h3").innerText = msg || "Nenhum resultado encontrado";
      noResults.style.display = "block";
      return;
    }

    // Se a busca foi por ID, o retorno é um objeto só
    if (tipoBusca === "idAnimail") {
      const animal = await response.json();
      const info = animal.informacao || {};

      // Preenche a tabela com os dados
      const row = tbody.insertRow();
      row.insertCell().innerText = animal.id ?? "-";
      row.insertCell().innerText = info.nome ?? "-";
      row.insertCell().innerText = info.raca ?? "-";
      row.insertCell().innerText = info.status ?? "-";
      row.insertCell().innerText = info.idade != null ? `${info.idade} ano(s)` : "-";

      resultsCount.innerText = "1 animal encontrado";

    } else {
      // Se a busca foi por nome, o retorno é uma lista de objetos
      const lista = await response.json();

      if (!Array.isArray(lista) || lista.length === 0) {
        resultsCount.innerText = "0 animais encontrados";
        noResults.querySelector("h3").innerText = "Nenhum resultado encontrado";
        noResults.style.display = "block";
        return;
      }

      // Para cada animal da lista, cria uma linha na tabela
      lista.forEach(animal => {
        const info = animal.informacao || {};

        const row = tbody.insertRow();
        row.insertCell().innerText = animal.id ?? "-";
        row.insertCell().innerText = info.nome ?? "-";
        row.insertCell().innerText = info.raca ?? "-";
        row.insertCell().innerText = info.status ?? "-";
        row.insertCell().innerText = info.idade != null ? `${info.idade} ano(s)` : "-";
      });

      resultsCount.innerText = `${lista.length} animal${lista.length > 1 ? "s" : ""} encontrado${lista.length > 1 ? "s" : ""}`;
    }

  } catch (error) {
    // Caso algum erro inesperado aconteça
    console.error("Erro ao buscar animal:", error);
    resultsCount.innerText = "0 animais encontrados";
    noResults.querySelector("h3").innerText = "Erro ao buscar animal.";
    noResults.style.display = "block";
  }
}
  
  async function getAllAnimais() {
    const resultado = document.getElementById("resultText");
    resultado.innerText = "Carregando lista de animais...";
  
    try {
      const response = await fetch("http://localhost:8080/apis/animal");
  
      if (!response.ok) {
        resultado.innerText = "Erro ao buscar lista de animais.";
        return;
      }
  
      const data = await response.json();
  
      if (!Array.isArray(data) || data.length === 0) {
        resultado.innerText = "Nenhum animal encontrado.";
        return;
      }
  
      let texto = "Lista de Animais:\n\n";
      data.forEach(animal => {
        texto += `ID: ${animal.id || "N/A"}  |  Nome: ${animal.informacao?.nome || animal.nome || "Sem nome"}\n`;
      });
  
      resultado.innerText = texto;
    } catch (error) {
      resultado.innerText = "Erro ao buscar lista de animais.";
      console.error("Erro ao buscar lista de animais:", error);
    }
  }
  
  document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("searchBtn").addEventListener("click", findAnimal);
    //document.getElementById("showAllBtn").addEventListener("click", getAllAnimais);
    document.getElementById("animalIdInput").addEventListener("keydown", (e) => {
      if (e.key === "Enter") findAnimal();
    });
  });

