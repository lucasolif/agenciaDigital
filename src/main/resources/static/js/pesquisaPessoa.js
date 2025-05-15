document.addEventListener("DOMContentLoaded", function () {
    const inputFiltro = document.getElementById("filtroPessoa");
    const tabela = document.getElementById("tabelaPessoas").getElementsByTagName("tbody")[0];

    inputFiltro.addEventListener("keyup", function () {
        const filtro = inputFiltro.value.trim();

        // Só faz a busca se o filtro tiver 3 ou mais caracteres
        if (filtro.length < 1) { 
            tabela.innerHTML = ""; // Limpar tabela se o filtro for muito curto
            return;
        }

        // Enviar requisição AJAX para buscar as pessoas
        fetch(`/thent/pessoa/consultar?filtro=${encodeURIComponent(filtro)}`)
            .then(response => response.json())
            .then(data => {
                tabela.innerHTML = ""; // Limpar tabela anterior
                console.log('Dados recebidos: ', data); // Verificar o que está sendo retornado
                // Verificar se há pessoas para exibir
                if (data && Array.isArray(data) && data.length > 0) {
                    data.forEach(pessoa => {
                        const row = tabela.insertRow();

                        row.innerHTML = `
                        	<td>${pessoa.codigo}</td>
                            <td>${pessoa.nome}</td>
                            <td>${pessoa.cpf}</td>
                            <td>
                                <button type="button" class="btn btn-sm btn-primary"
                                	onclick="selecionarPessoa('${pessoa.id}', '${pessoa.nome}', '${pessoa.codigo}')"
                                    data-bs-dismiss="modal">Selecionar</button>
                            </td>
                        `;
                    });
                } else {
                    // Se não houver resultados, mostrar mensagem na tabela
                    const row = tabela.insertRow();
                    const cell = row.insertCell(0);
                    cell.colSpan = 4;
                    cell.className = 'text-center';
                    cell.innerText = 'Nenhuma pessoa encontrada.';
                }
            })
            .catch(error => {
                console.error('Erro ao buscar pessoas:', error);
                // Mensagem de erro na tabela
                const row = tabela.insertRow();
                const cell = row.insertCell(0);
                cell.colSpan = 4;
                cell.className = 'text-center';
                cell.innerText = 'Erro ao carregar dados.';
            });
    });
});

// Função para selecionar pessoa no modal
function selecionarPessoa(id, nome, codigo) {
    document.getElementById("ofertanteId").value = id;
    document.getElementById("ofertanteNome").value = nome;
    document.getElementById("ofertanteCodigo").value = codigo;  // Preencher o campo de código
}