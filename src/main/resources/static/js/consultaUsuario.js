document.addEventListener("DOMContentLoaded", function () {
  const searchInput = document.getElementById("inputBusca");
  const resultsTable = document.getElementById("resultadoBusca");
  let usuarioSelecionado = null;

  if (searchInput) {
    searchInput.addEventListener("input", function () {
      const filtro = this.value.trim();

      if (filtro.length >= 1) {
        fetch(`/agenciaDigital/usuario/consultar?filtro=${encodeURIComponent(filtro)}`)
          .then(response => response.json())
          .then(data => {
            resultsTable.innerHTML = ""; // Limpa

            data.forEach(j => {
              const row = document.createElement("tr");
			  row.classList.add("clicavel");
			  row.classList.add("no-select"); // evita seleção de texto
              row.innerHTML = `
                <td>${j.nome}</td>
                <td>${j.username}</td>
                <td>${j.email}</td>
				<td>${j.status ? "Ativo" : "Inativo"}</td>
              `;

              // Clique na linha para selecionar
			  row.addEventListener("click", () => {
			    const isSelected = row.classList.contains("table-active");

			    // Remove seleção de todas as linhas
			    resultsTable.querySelectorAll("tr.table-active").forEach(r => r.classList.remove("table-active"));

			    if (!isSelected) {
			      row.classList.add("table-active");
			      usuarioSelecionado = j;
			    } else {
			      usuarioSelecionado = null;
			    }
			  });

              resultsTable.appendChild(row);
            });

            // Limpa seleção ao refazer busca
            usuarioSelecionado = null;
          });
      } else {
        resultsTable.innerHTML = "";
        usuarioSelecionado = null;
      }
    });
  }

  // Botão Escolher
  document.getElementById("btnEscolher").addEventListener("click", () => {
    if (!usuarioSelecionado) {
      alert("Por favor, selecione um usuário antes de escolher.");
      return;
    }

    selecionarUsuario(usuarioSelecionado);

    // Fecha o modal
    const modal = bootstrap.Modal.getInstance(document.getElementById("modalBuscarUsuario"));
    modal.hide();
  });
  
  //Limpa dos dados quando fechado
  const limparModal = document.getElementById("modalBuscarUsuario");

  if (limparModal) {
    limparModal.addEventListener('hidden.bs.modal', function () {
      // Limpa campo de busca
      const inputBusca = document.getElementById("inputBusca");
      if (inputBusca) inputBusca.value = "";

      // Limpa resultados
      const resultsTable = document.getElementById("resultadoBusca");
      if (resultsTable) resultsTable.innerHTML = "";

      // Limpa objeto selecionado
      usuarioSelecionado = null;
    });
  }
  
});

// Função para preencher os campos do formulário com o jornalista selecionado
function selecionarUsuario(usuario) {
  document.querySelector("[name='id']").value = usuario.id || "";
  document.querySelector("[name='nome']").value = usuario.nome || "";
  document.querySelector("[name='celular']").value = usuario.celular || "";
  document.querySelector("[name='email']").value = usuario.email || "";
  document.querySelector("[name='username']").value = usuario.username || "";
  
  document.querySelector("[name='status']").checked = usuario.status || false;
 }

