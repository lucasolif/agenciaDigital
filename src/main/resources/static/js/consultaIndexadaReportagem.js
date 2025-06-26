document.addEventListener('DOMContentLoaded', function () {
  const inputBusca = document.getElementById('buscaIndexada');
  const tabelaCorpo = document.querySelector('table tbody');

  let timeout = null;

  inputBusca.addEventListener('input', function () {
    clearTimeout(timeout);
    
    // Espera 300ms após parar de digitar para evitar muitas requisições
    timeout = setTimeout(() => {
      const termo = inputBusca.value.trim();

      if (termo.length === 0) {
		tabelaCorpo.innerHTML = '<tr><td colspan="4" class="text-center">Nenhum resultado encontrado.</td></tr>';
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
          tabelaCorpo.innerHTML = ''; // limpa tabela

          if (data.length === 0) {
            tabelaCorpo.innerHTML = '<tr><td colspan="4" class="text-center">Nenhum resultado encontrado.</td></tr>';
            return;
          }

          // Preenche tabela com resultados
          data.forEach(report => {
            const tr = document.createElement('tr');
            tr.setAttribute('data-reportagem-id', report.id);

            tr.innerHTML = `
              <td>${report.titulo}</td>
              <td>${report.jornalista?.nome || ''}</td>
              <td>${report.status}</td>
              <td>${report.assuntosString}</td>
            `;
            tabelaCorpo.appendChild(tr);
          });
        })
        .catch(err => {
          console.error(err);
          tabelaCorpo.innerHTML = '<tr><td colspan="4" class="text-center text-danger">Erro ao buscar dados.</td></tr>';
        });
    }, 300);
  });
});