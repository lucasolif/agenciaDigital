document.addEventListener("DOMContentLoaded", () => {
	let linhaSelecionada = null;

	document.querySelectorAll("tbody tr[data-reportagem-id]").forEach(linha => {
	    linha.addEventListener("click", function () {
	        if (linhaSelecionada) {
	            linhaSelecionada.classList.remove("table-active");
	        }

	        linhaSelecionada = this;
	        linhaSelecionada.classList.add("table-active");
	    });
	});

	document.getElementById("btnAlterar").addEventListener("click", function () {
	    if (!linhaSelecionada) {
	        alert("Selecione uma reportagem para alterar.");
	        return;
	    }

	    const id = linhaSelecionada.getAttribute("data-reportagem-id");
	    window.location.href = `/agenciaDigital/reportagem/editar/${id}`;
	});
});