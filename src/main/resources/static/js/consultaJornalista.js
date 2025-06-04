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
			      const isSelected = row.classList.contains("table-active");

			      // Remove qualquer seleção anterior
			      resultsTable.querySelectorAll("tr.table-active").forEach(r => r.classList.remove("table-active"));

			      if (!isSelected) {
			        row.classList.add("table-active");
			        jornalistaSelecionado = j;
			      } else {
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
