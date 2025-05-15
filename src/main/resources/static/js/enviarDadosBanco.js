// Função para preparar envio do formulário
function prepararEnvio() {
    const tabela = document.getElementById("tabelaMovimentos").getElementsByTagName('tbody')[0];
    const movimentos = [];

    for (let i = 0; i < tabela.rows.length; i++) {
        const linha = tabela.rows[i];
        movimentos.push({
            ofertante: linha.getAttribute('data-ofertante-id'),
            valor: parseFloat(linha.cells[1].innerText),
            tipoOferta: linha.getAttribute('data-tipo-oferta-id'),
            formaPagto: linha.getAttribute('data-forma-pagto-id'),
            contaCaixa: linha.getAttribute('data-conta-caixa-id'),
            igreja: linha.getAttribute('data-igreja-id'),
            dataOferta: linha.cells[6].innerText,
            tipoMovimento: "ENTRADA",
            complemento: " Entrada - " + linha.cells[2].innerText + " | " + linha.cells[5].innerText,
            usuario: document.getElementById('nomeUsuarioLogado').value
        });
    }

    document.getElementById("movimentosJson").value = JSON.stringify(movimentos);
    document.getElementById("movimentoForm").submit();
}
