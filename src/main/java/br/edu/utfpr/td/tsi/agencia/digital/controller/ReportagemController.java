package br.edu.utfpr.td.tsi.agencia.digital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;
import br.edu.utfpr.td.tsi.agencia.digital.services.AssuntoServices;
import br.edu.utfpr.td.tsi.agencia.digital.services.ReportagemServices;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/reportagem")
public class ReportagemController {

    @Autowired
    private ReportagemServices reportagemService;
    @Autowired
    private AssuntoServices assuntoService;

    @GetMapping("/cadastrar")
    public String exibirPaginaCadastro(Model model) {
    	
        model.addAttribute("reportagem", new Reportagem());
        model.addAttribute("assuntos", assuntoService.listarTodos());
        return "formCadastroReportagem";
        
    }

    @PostMapping("/cadastrar")
    public String salvarReportagem(@Valid Reportagem reportagem, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
		
    	// Retorna mensagem caso os campos obrigatório não tenha sido preenchido
		if (bindingResult.hasErrors()) {
			model.addAttribute("reportagem", reportagem);
			model.addAttribute("assuntos", assuntoService.listarTodos());
			
            return "formCadastroReportagem";
		}
		
		//Zerar id no salvamento de um novo cadastro
        if (reportagem.getId() != null && reportagem.getId().isEmpty()) {
        	reportagem.setId(null);
        }
    	
        boolean novaReportagem = (reportagem.getId() == null || reportagem.getId().isEmpty());
        
        if(novaReportagem) {
	        redirectAttrs.addFlashAttribute("mensagem", "Reportagem cadastrada com sucesso!");
	        redirectAttrs.addFlashAttribute("tipoMensagem", "success");	
        }else {
	        redirectAttrs.addFlashAttribute("mensagem", "Reportagem alterada com sucesso!");
	        redirectAttrs.addFlashAttribute("tipoMensagem", "success");	
        }
        
        reportagemService.salvar(reportagem);

        return "redirect:/reportagem/cadastrar"; 
    }
}
