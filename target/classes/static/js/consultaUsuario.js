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
});

// Função para preencher os campos do formulário com o jornalista selecionado
function selecionarUsuario(usuario) {
  document.querySelector("[name='id']").value = usuario.id || "";
  document.querySelector("[name='nome']").value = usuario.nome || "";
  document.querySelector("[name='celular']").value = usuario.celular || "";
  document.querySelector("[name='email']").value = usuario.email || "";
  document.querySelector("[name='username']").value = usuario.username || "";
 }

