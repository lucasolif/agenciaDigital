package br.edu.utfpr.td.tsi.agencia.digital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.utfpr.td.tsi.agencia.digital.dto.ReportagemDto;
import br.edu.utfpr.td.tsi.agencia.digital.exception.CotaReportagemException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.StatusReportagemException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Assunto;
import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;
import br.edu.utfpr.td.tsi.agencia.digital.services.AssuntoServices;
import br.edu.utfpr.td.tsi.agencia.digital.services.ReportagemServices;


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
    public String salvarReportagem(Reportagem reportagem, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
		
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
    		redirectAttrs.addFlashAttribute("mensagem", "Erro ao tentar cadastrar/excluir reportagem."+e.getMessage());
    		redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
	    }

        return "redirect:/reportagem/cadastrar"; 
    }
    
    @GetMapping("/consultar")
    public String consultarReportagem(@RequestParam(required = false) String jornalistaId, @RequestParam(required = false) String status, @RequestParam(required = false) String assuntoId, Model model) {
        
    	List<Assunto> listaAssuntos = assuntoService.listarTodos();
        List<ReportagemDto> listaReportagemDTO = null;

        //Adicionado essa validação para que ao abrir a tela não seja listados todos
        if (jornalistaId != null || status != null || assuntoId != null) {
            listaReportagemDTO = reportagemService.consultarReportagens(jornalistaId, assuntoId, status);
        }

        model.addAttribute("reportagensDto", listaReportagemDTO);
        model.addAttribute("assuntos", listaAssuntos);
        
        model.addAttribute("jornalistaId", jornalistaId); 
        model.addAttribute("assuntoId", assuntoId);
        model.addAttribute("status", status);

        return "formConsultaReportagem";
    }
    
    @GetMapping("/consultaIndexada")
    @ResponseBody
    public List<ReportagemDto> consultaIndexada(@RequestParam(required = false) String buscaIndexada) {      	
    	return reportagemService.listarTodosBuscaIndexada(buscaIndexada);
    }
    
    @PostMapping("/excluir")
    public String excluir(@RequestParam String id, RedirectAttributes redirectAttributes) {
        try {      	
            reportagemService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Reportagem excluída com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        }catch(StatusReportagemException e) {
        	redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
        	redirectAttributes.addFlashAttribute("tipoMensagem", "warning");	
    	} catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir a reportagem.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
        }
        return "redirect:/reportagem/consultar";
    }
    
    @GetMapping("/editar/{id}")
    public String editarReportagem(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {

        try {
            Reportagem reportagem = reportagemService.consultarReportagemId(id);

            model.addAttribute("reportagem", reportagem);
            model.addAttribute("assuntos", assuntoService.listarTodos());
            
            return "formCadastroReportagem";

        } catch (StatusReportagemException e) {
            redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "warning");
            return "redirect:/reportagem/consultar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao carregar a reportagem para edição.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
            return "redirect:/reportagem/consultar";
        }
    }

}
