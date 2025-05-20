let formParaExcluir = null;

document.addEventListener("DOMContentLoaded", function () {
  const btnExcluir = document.getElementById("btnExcluir");
  const btnConfirmar = document.getElementById("btnConfirmarExclusao");

  if (btnExcluir) {
    btnExcluir.addEventListener("click", function (event) {
      const botao = event.target;

      const formSelector = botao.getAttribute("data-form-selector");
      const deleteUrl = botao.getAttribute("data-delete-url");

      const form = document.querySelector(formSelector);
      const idField = form?.querySelector("input[name='id']");

      if (!form || !idField || !idField.value) {
        alert("Selecione um item antes de excluir.");
        return;
      }

      formParaExcluir = {
        form,
        deleteUrl
      };

      const modal = new bootstrap.Modal(document.getElementById('confirmarExclusaoModal'));
      modal.show();
    });
  }

  if (btnConfirmar) {
    btnConfirmar.addEventListener("click", function () {
      if (formParaExcluir && formParaExcluir.form) {
        const form = formParaExcluir.form;
        const url = formParaExcluir.deleteUrl;

        form.action = url;
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
      }
    });
  }
});