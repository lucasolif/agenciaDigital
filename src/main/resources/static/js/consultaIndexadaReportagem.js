document.addEventListener('DOMContentLoaded', function () {
  const inputBusca = document.getElementById('buscaIndexada');
  const tabelaCorpo = document.querySelector('table tbody');
  const btnAlterar = document.getElementById('btnAlterar');
  const btnExcluir = document.getElementById('btnExcluir');

  let timeout = null;
  let linhaSelecionada = null;

  function aplicarEventoCliqueLinha(linha, report = null) {
    linha.addEventListener("click", function () {
      const isSelecionada = linha === linhaSelecionada;

      // Remove seleção anterior
      document.querySelectorAll("tbody tr.table-active").forEach(r => r.classList.remove("table-active"));

      if (isSelecionada) {
        linhaSelecionada = null; // desfaz a seleção
      } else {
        linha.classList.add("table-active");
        linhaSelecionada = linha;
        linhaSelecionada.report = report;
      }
    });
  }

  document.querySelectorAll("tbody tr[data-reportagem-id]").forEach(linha => {
    aplicarEventoCliqueLinha(linha);
  });

  inputBusca.addEventListener('input', function () {
    clearTimeout(timeout);

    timeout = setTimeout(() => {
      const termo = inputBusca.value.trim();

      if (termo.length === 0) {
        tabelaCorpo.innerHTML = '<tr><td colspan="4" class="text-center">Nenhum resultado encontrado.</td></tr>';
        linhaSelecionada = null;
        return;
      }

      fetch(`/agenciaDigital/reportagem/consultaIndexada?buscaIndexada=${encodeURIComponent(termo)}`)
        .then(response => {
          if (!response.ok) {
            throw new Error('Erro na requisição');
          }
          return response.json();
        })
        .then(data => {
          tabelaCorpo.innerHTML = '';

          if (data.length === 0) {
            tabelaCorpo.innerHTML = '<tr><td colspan="4" class="text-center">Nenhum resultado encontrado.</td></tr>';
            return;
          }

          data.forEach(report => {
            const tr = document.createElement('tr');
            tr.classList.add("clicavel");
            tr.setAttribute('data-reportagem-id', report.id);

            tr.innerHTML = `
              <td>${report.titulo}</td>
              <td>${report.jornalista?.nome || ''}</td>
              <td>${report.status}</td>
              <td>${report.assuntosString}</td>
            `;

            aplicarEventoCliqueLinha(tr, report);
            tabelaCorpo.appendChild(tr);
          });

          linhaSelecionada = null;
        })
        .catch(err => {
          console.error(err);
          tabelaCorpo.innerHTML = '<tr><td colspan="4" class="text-center text-danger">Erro ao buscar dados.</td></tr>';
        });
    }, 300);
  });

  btnAlterar?.addEventListener("click", function () {
    if (!linhaSelecionada) {
      alert("Selecione uma reportagem para alterar.");
      return;
    }

    const id = linhaSelecionada.getAttribute("data-reportagem-id");
    window.location.href = `/agenciaDigital/reportagem/editar/${id}`;
  });

  // Botão Excluir
  document.getElementById("btnExcluir").addEventListener("click", () => {
      if (!reportagemSelecionadaId) {
          alert("Selecione uma reportagem clicando em uma linha da tabela.");
          return;
      }

      document.getElementById("reportagemIdParaExcluir").value = reportagemSelecionadaId;

      const modal = new bootstrap.Modal(document.getElementById("modalConfirmarExclusao"));
      modal.show();
  });
});
