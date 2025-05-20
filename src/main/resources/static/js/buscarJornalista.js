document.addEventListener("DOMContentLoaded", function () {
  const searchInput = document.getElementById("inputBusca");
  const resultsTable = document.getElementById("resultadoBusca");
  let jornalistaSelecionado = null;

  if (searchInput) {
    searchInput.addEventListener("input", function () {
      const filtro = this.value.trim();

      if (filtro.length >= 3) {
        fetch(`/agenciaDigital/jornalista/consultar?filtro=${encodeURIComponent(filtro)}`)
          .then(response => response.json())
          .then(data => {
            resultsTable.innerHTML = ""; // Limpa

            data.forEach(j => {
              const row = document.createElement("tr");
              row.classList.add("clicavel");
              row.innerHTML = `
                <td>${j.nome}</td>
                <td>${j.cpf}</td>
                <td>${j.ativo ? "Ativo" : "Inativo"}</td>
              `;

              // Clique na linha para selecionar
              row.addEventListener("click", () => {
                // Verifica se a linha já está selecionada
                if (!row.classList.contains("selecionada")) {
                  // Remove seleção anterior
                  resultsTable.querySelectorAll("tr.selecionada").forEach(r => r.classList.remove("selecionada"));
                  // Adiciona a classe de seleção à linha clicada
                  row.classList.add("selecionada");
                  // Atualiza o objeto do jornalista selecionado
                  jornalistaSelecionado = j;
                } else {
                  // Se a linha já estiver selecionada, desmarque-a
                  row.classList.remove("selecionada");
                  jornalistaSelecionado = null;
                }
              });

              resultsTable.appendChild(row);
            });

            // Limpa seleção ao refazer busca
            jornalistaSelecionado = null;
          });
      } else {
        resultsTable.innerHTML = "";
        jornalistaSelecionado = null;
      }
    });
  }

  // Botão Escolher
  document.getElementById("btnEscolher").addEventListener("click", () => {
    if (!jornalistaSelecionado) {
      alert("Por favor, selecione um jornalista antes de escolher.");
      return;
    }

    selecionarJornalista(jornalistaSelecionado);

    // Fecha o modal
    const modal = bootstrap.Modal.getInstance(document.getElementById("modalBuscarJornalista"));
    modal.hide();
  });
  
  //Limpa dos dados quando fechado
  const limparModal = document.getElementById("modalBuscarJornalista");

  if (limparModal) {
    limparModal.addEventListener('hidden.bs.modal', function () {
      // Limpa campo de busca
      const inputBusca = document.getElementById("inputBusca");
      if (inputBusca) inputBusca.value = "";

      // Limpa resultados
      const resultsTable = document.getElementById("resultadoBusca");
      if (resultsTable) resultsTable.innerHTML = "";

      // Limpa objeto selecionado
      jornalistaSelecionado = null;
    });
  }
  
});

// Função para preencher os campos do formulário com o jornalista selecionado
function selecionarJornalista(jornalista) {
  document.getElementById("jornalistaNome").value = jornalista.nome || "teste";
  document.getElementById("jornalistaId").value = jornalista.id;
}
