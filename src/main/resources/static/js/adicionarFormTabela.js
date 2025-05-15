function formatarData(dataString) {
    const data = new Date(dataString);
    const dia = String(data.getDate()).padStart(2, '0');
    const mes = String(data.getMonth() + 1).padStart(2, '0');
    const ano = data.getFullYear();
    return `${dia}/${mes}/${ano}`;
}

function adicionarMovimento() {
    const ofertanteNome = document.getElementById("ofertanteNome").value;
    const ofertanteId = document.getElementById("ofertanteId").value;
    const valor = parseFloat(document.getElementById("valor").value);
    const tipoOferta = document.getElementById("tipoOferta");
    const tipoOfertaId = tipoOferta.value;
    const formaPagto = document.getElementById("formaPagto");
    const formaPagtoId = formaPagto.value;
    const contaCaixa = document.getElementById("contaCaixa");
    const contaCaixaId = contaCaixa.value;
    const igreja = document.getElementById("igreja");
    const igrejaId = igreja.value;
    const dataOferta = document.getElementById("dataOferta").value;

    if (!ofertanteNome || !ofertanteId || isNaN(valor) || !tipoOfertaId || !formaPagtoId || !contaCaixaId || !igrejaId || !dataOferta) {
        alert("Preencha todos os campos obrigatórios antes de adicionar.");
        return;
    }

    const dataOfertaFormatada = formatarData(dataOferta);

    const tabela = document.getElementById("tabelaMovimentos").getElementsByTagName('tbody')[0];
    const novaLinha = tabela.insertRow();

    // Armazena os IDs nos atributos da linha
    novaLinha.setAttribute('data-ofertante-id', ofertanteId);
    novaLinha.setAttribute('data-tipo-oferta-id', tipoOfertaId);
    novaLinha.setAttribute('data-forma-pagto-id', formaPagtoId);
    novaLinha.setAttribute('data-conta-caixa-id', contaCaixaId);
    novaLinha.setAttribute('data-igreja-id', igrejaId);

    novaLinha.innerHTML = `
        <td>${ofertanteNome}</td>
        <td>${valor.toFixed(2)}</td>
        <td>${tipoOferta.options[tipoOferta.selectedIndex].text}</td>
        <td>${formaPagto.options[formaPagto.selectedIndex].text}</td>
        <td>${contaCaixa.options[contaCaixa.selectedIndex].text}</td>
        <td>${igreja.options[igreja.selectedIndex].text}</td>
        <td>${dataOfertaFormatada}</td>
        <td><button type="button" class="btn btn-danger btn-sm" onclick="this.closest('tr').remove()">Remover</button></td>`;

    // Limpar campos
    document.getElementById("ofertanteNome").value = '';
    document.getElementById("ofertanteId").value = '';
    document.getElementById("valor").value = '';
    document.getElementById("tipoOferta").selectedIndex = 0;
    document.getElementById("formaPagto").selectedIndex = 0;
    document.getElementById("contaCaixa").selectedIndex = 0;
    document.getElementById("igreja").selectedIndex = 0;
    document.getElementById("dataOferta").value = '';
}