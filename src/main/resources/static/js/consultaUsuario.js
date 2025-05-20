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
              row.innerHTML = `
                <td>${j.nome}</td>
                <td>${j.username}</td>
                <td>${j.email}</td>
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
                  usuarioSelecionado = j;
                } else {
                  // Se a linha já estiver selecionada, desmarque-a
                  row.classList.remove("selecionada");
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
  
  //Botão de excluir
  document.getElementById("btnExcluir").addEventListener("click", function() {
     const form = document.querySelector("form[action*='/usuario/cadastrar']");
     const idField = form.querySelector("input[name='id']");
     
     if (!idField || !idField.value) {
       alert("Selecione um usuário antes de excluir.");
       return;
     }

     if (!confirm("Tem certeza que deseja excluir este usuário?")) {
       return;
     }

     // Muda ação e método para exclusão
     form.action = "/agenciaDigital/usuario/excluir";
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

