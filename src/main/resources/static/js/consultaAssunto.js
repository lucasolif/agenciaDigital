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
            resultsTable.innerHTML = ""; // Limpa

            data.forEach(j => {
              const row = document.createElement("tr");
              row.classList.add("clicavel");
              row.innerHTML = `
			  	<td>${j.nome}</td>
				<td>${j.status ? "Ativo" : "Inativo"}</td>
			  `;

              // Clique na linha para selecionar
              row.addEventListener("click", () => {
                // Verifica se a linha já está selecionada
                if (!row.classList.contains("selecionada")) {
                  // Remove seleção anterior
                  resultsTable.querySelectorAll("tr.selecionada").forEach(r => r.classList.remove("selecionada"));
                  // Adiciona a classe de seleção à linha clicada
                  row.classList.add("selecionada");
                  // Atualiza o objeto do usuário selecionado
                  assuntoSelecionado = j;
                } else {
                  // Se a linha já estiver selecionada, desmarque-a
                  row.classList.remove("selecionada");
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
  
  //Botão de excluir
  document.getElementById("btnExcluir").addEventListener("click", function() {
     const form = document.querySelector("form[action*='/assunto/cadastrar']");
     const idField = form.querySelector("input[name='id']");
     
     if (!idField || !idField.value) {
       alert("Selecione um assunto antes de excluir.");
       return;
     }

     if (!confirm("Tem certeza que deseja excluir este assunto?")) {
       return;
     }

     // Muda ação e método para exclusão
     form.action = "/agenciaDigital/assunto/excluir";
     form.method = "post";

     let methodInput = form.querySelector("input[name='_method']");
     if (!methodInput) {
       methodInput = document.createElement("input");
       methodInput.type = "hidden";
       methodInput.name = "_method";
       form.appendChild(methodInput);
     }
     methodInput.value = "delete";

     form.submit();
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

