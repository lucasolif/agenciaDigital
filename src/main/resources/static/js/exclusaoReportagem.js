document.addEventListener("DOMContentLoaded", () => {
    let reportagemSelecionadaId = null;

    // Marcar a linha clicada e salvar o ID
    document.querySelectorAll("tbody tr[data-reportagem-id]").forEach(row => {
        row.addEventListener("click", () => {
            // Remover seleção anterior
            document.querySelectorAll("tbody tr").forEach(r => r.classList.remove("table-active"));
            row.classList.add("table-active");

            // Salvar ID
            reportagemSelecionadaId = row.getAttribute("data-reportagem-id");
        });
    });

    // Quando clicar no botão de excluir
    document.getElementById("btnExcluir").addEventListener("click", () => {
        if (!reportagemSelecionadaId) {
            alert("Selecione uma reportagem clicando em uma linha da tabela.");
            return;
        }
        
        // Passar ID para o hidden do form
        document.getElementById("reportagemIdParaExcluir").value = reportagemSelecionadaId;

        // Abrir o modal
        const modal = new bootstrap.Modal(document.getElementById("modalConfirmarExclusao"));
        modal.show();
    });
});