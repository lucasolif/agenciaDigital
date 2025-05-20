document.addEventListener("DOMContentLoaded", function () {
  const searchInput = document.getElementById("inputBusca");
  const resultsTable = document.getElementById("resultadoBusca");
  let jornalistaSelecionado = null;

  if (searchInput) {
    searchInput.addEventListener("input", function () {
      const filtro = this.value.trim();

      if (filtro.length >= 1) {
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
  
  document.getElementById("btnExcluir").addEventListener("click", function() {
    const form = document.querySelector("form[action*='/jornalista/cadastrar']");
    const idField = form.querySelector("input[name='id']");
    
    if (!idField || !idField.value) {
      alert("Selecione um jornalista antes de excluir.");
      return;
    }

    if (!confirm("Tem certeza que deseja excluir este jornalista?")) {
      return;
    }

    // Muda ação e método para exclusão
    form.action = "/agenciaDigital/jornalista/excluir";
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
  document.querySelector("[name='id']").value = jornalista.id || "";
  document.querySelector("[name='nome']").value = jornalista.nome || "";
  document.querySelector("[name='cpf']").value = jornalista.cpf || "";
  document.querySelector("[name='dataNascimento']").value = jornalista.dataNascimento || "";
  document.querySelector("[name='rg']").value = jornalista.rg || "";
  document.querySelector("[name='sexo']").value = jornalista.sexo || "";
  document.querySelector("[name='celular']").value = jornalista.celular || "";
  document.querySelector("[name='telefone']").value = jornalista.telefone || "";
  document.querySelector("[name='email']").value = jornalista.email || "";

  if (jornalista.endereco) {
    document.querySelector("[name='endereco\\.logradouro']").value = jornalista.endereco.logradouro || "";
    document.querySelector("[name='endereco\\.numero']").value = jornalista.endereco.numero || "";
    document.querySelector("[name='endereco\\.bairro']").value = jornalista.endereco.bairro || "";
    document.querySelector("[name='endereco\\.cidade']").value = jornalista.endereco.cidade || "";
    document.querySelector("[name='endereco\\.estado']").value = jornalista.endereco.estado || "";
    document.querySelector("[name='endereco\\.cep']").value = jornalista.endereco.cep || "";
    document.querySelector("[name='endereco\\.complemento']").value = jornalista.endereco.complemento || "";
  }

  document.querySelector("[name='ativo']").checked = jornalista.ativo || false;
}
