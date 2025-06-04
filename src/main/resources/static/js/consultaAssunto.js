document.addEventListener("DOMContentLoaded", function () {
  const searchInput = document.getElementById("inputBusca");
  const resultsTable = document.getElementById("resultadoBusca");
  let assuntoSelecionado = null;

  if (searchInput) {
    searchInput.addEventListener("input", function () {
      const filtro = this.value.trim();

      if (filtro.length >= 1) {
        fetch(`/agenciaDigital/assunto/consultar?filtro=${encodeURIComponent(filtro)}`)
          .then(response => response.json())
          .then(data => {
            resultsTable.innerHTML = ""; 

			data.forEach(j => {
			  const row = document.createElement("tr");
			  row.classList.add("clicavel");
			  row.classList.add("no-select"); // evita seleção de texto
			  row.innerHTML = `
			    <td>${j.nome}</td>
			    <td>${j.status ? "Ativo" : "Inativo"}</td>
			  `;

			  row.addEventListener("click", () => {
			    const isSelected = row.classList.contains("table-active");

			    // Remove seleção de todas as linhas
			    resultsTable.querySelectorAll("tr.table-active").forEach(r => r.classList.remove("table-active"));

			    if (!isSelected) {
			      row.classList.add("table-active");
			      assuntoSelecionado = j;
			    } else {
			      assuntoSelecionado = null;
			    }
			  });

			  resultsTable.appendChild(row);
			});

            // Limpa seleção ao refazer busca
            assuntoSelecionado = null;
          });
      } else {
        resultsTable.innerHTML = "";
        assuntoSelecionado = null;
      }
    });
  }

  // Botão Escolher
  document.getElementById("btnEscolher").addEventListener("click", () => {
    if (!assuntoSelecionado) {
      alert("Por favor, selecione um assunto antes de escolher.");
      return;
    }
    selecionarAssunto(assuntoSelecionado);
    // Fecha o modal
    const modal = bootstrap.Modal.getInstance(document.getElementById("modalBusca"));
    modal.hide();
  });
  
  //Limpa dos dados quando fechado
  const limparModal = document.getElementById("modalBusca");

  if (limparModal) {
	limparModal.addEventListener('hidden.bs.modal', function () {
	// Limpa campo de busca
	const inputBusca = document.getElementById("inputBusca");
	if (inputBusca) inputBusca.value = "";

      // Limpa resultados
      const resultsTable = document.getElementById("resultadoBusca");
      if (resultsTable) resultsTable.innerHTML = "";

      // Limpa objeto selecionado
      assuntoSelecionado = null;
    });
  }
  
});

// Função para preencher os campos do formulário com o jornalista selecionado
function selecionarAssunto(assunto) {
	document.querySelector("[name='id']").value = assunto.id || "";
  	document.querySelector("[name='nome']").value = assunto.nome || "";
	document.querySelector("[name='status']").checked = assunto.status || false;
 }

