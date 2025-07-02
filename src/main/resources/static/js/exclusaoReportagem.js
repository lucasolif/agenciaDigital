document.addEventListener("DOMContentLoaded", () => {
    let reportagemSelecionadaId = null;

    // Marcar a linha clicada e salvar/remover o ID
    document.querySelectorAll("tbody tr[data-reportagem-id]").forEach(row => {
        row.addEventListener("click", () => {
            const isSelecionada = row.classList.contains("table-active");

            // Remover seleção anterior
            document.querySelectorAll("tbody tr").forEach(r => r.classList.remove("table-active"));

            if (!isSelecionada) {
                row.classList.add("table-active");
                reportagemSelecionadaId = row.getAttribute("data-reportagem-id");
            } else {
                reportagemSelecionadaId = null;
            }
        });
    });

    // Botão Excluir
    document.getElementById("btnExcluir").addEventListener("click", () => {

        document.getElementById("reportagemIdParaExcluir").value = reportagemSelecionadaId;

        const modal = new bootstrap.Modal(document.getElementById("modalConfirmarExclusao"));
        modal.show();
    });
});
