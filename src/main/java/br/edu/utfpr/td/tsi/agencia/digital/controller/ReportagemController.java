package br.edu.utfpr.td.tsi.agencia.digital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.utfpr.td.tsi.agencia.digital.dto.ReportagemDTOConsulta;
import br.edu.utfpr.td.tsi.agencia.digital.exception.CotaReportagemException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Assunto;
import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;
import br.edu.utfpr.td.tsi.agencia.digital.services.AssuntoServices;
import br.edu.utfpr.td.tsi.agencia.digital.services.JornalistaServices;
import br.edu.utfpr.td.tsi.agencia.digital.services.ReportagemServices;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/reportagem")
public class ReportagemController {

    @Autowired
    private ReportagemServices reportagemService;
    @Autowired
    private AssuntoServices assuntoService;
    @Autowired
    private JornalistaServices jornalistaService;

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

    	try {		
    		reportagemService.salvar(reportagem);
    		
            if(reportagem.getId() == null || reportagem.getId().isEmpty()) {
    	        redirectAttrs.addFlashAttribute("mensagem", "Reportagem cadastrada com sucesso!");
    	        redirectAttrs.addFlashAttribute("tipoMensagem", "success");	
            }else {
    	        redirectAttrs.addFlashAttribute("mensagem", "Reportagem alterada com sucesso!");
    	        redirectAttrs.addFlashAttribute("tipoMensagem", "success");	
            }      
    		
    	}catch(CotaReportagemException e) {
	        redirectAttrs.addFlashAttribute("mensagem", e.getMessage());
	        redirectAttrs.addFlashAttribute("tipoMensagem", "warning");	
    	}catch (Exception e) {
    		redirectAttrs.addFlashAttribute("mensagem", "Erro ao tentar cadastrar/excluir reportagem.");
    		redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
	    }

        return "redirect:/reportagem/cadastrar"; 
    }
    
    @GetMapping("/consultar")
    public String consultarReportagem(@RequestParam(required = false) String jornalistaId, @RequestParam(required = false) String status, @RequestParam(required = false) String assuntoId, Model model) {
    	
        List<ReportagemDTOConsulta> listaReportagemDTO = null;

        if (jornalistaId != null || status != null || assuntoId != null) {
        	//listaReportagemDTO = reportagemService.filtraReportagem(jornalistaId, status, assuntoId);
        }

        List<Assunto> listaAssuntos = assuntoService.listarTodos();
      
        model.addAttribute("jornalista", jornalistaService.listarTodos());
        model.addAttribute("reportagensDto", listaReportagemDTO);
        model.addAttribute("assuntos", listaAssuntos);

        return "formConsultaReportagem";
    }
}
