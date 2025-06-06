package br.edu.utfpr.td.tsi.agencia.digital.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.utfpr.td.tsi.agencia.digital.exception.DadoVinculadoException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.ErroBancoException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Assunto;
import br.edu.utfpr.td.tsi.agencia.digital.services.AssuntoServices;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/assunto")
public class AssuntoController {
	
	@Autowired
	private AssuntoServices assuntoServices;
	
    @GetMapping(value = "/cadastrar")
    public String exibirPaginaCadastro(Model model) {
    	Assunto assunto = new Assunto();
    	assunto.setStatus(true);
    	
    	model.addAttribute("assunto", assunto); 	     
        return "formCadastroAssunto";
    }
    
	@PostMapping(value = "/cadastrar")
	public String salvar (@Valid Assunto assunto,BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) { 		
		try {	
			
			// Retorna mensagem caso os campos obrigatório não tenha sido preenchido
			if (bindingResult.hasErrors()) {
				model.addAttribute("assunto", assunto);
	            return "formCadastroAssunto";
			}

			assuntoServices.salvar(assunto);

			if(assunto.getId() == null || assunto.getId().isEmpty()) {
		        redirectAttrs.addFlashAttribute("mensagem", "Assunto cadastrado com sucesso!");
		        redirectAttrs.addFlashAttribute("tipoMensagem", "success");		
			}else {
		        redirectAttrs.addFlashAttribute("mensagem", "Assunto alterado com sucesso!");
		        redirectAttrs.addFlashAttribute("tipoMensagem", "success");		
			}
		}catch (ErroBancoException erro) {
		    redirectAttrs.addFlashAttribute("mensagem", erro.getMessage());
		    redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
        }catch(Exception erro) {
            redirectAttrs.addFlashAttribute("mensagem", erro.getMessage());
            redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
		}

		return "redirect:/assunto/cadastrar";
	}
	
	@GetMapping("/consultar")
	@ResponseBody
	public List<Assunto> consultar(@RequestParam(name = "filtro", required = false) String filtro, Model model) {
		
	    if (filtro == null || filtro.isBlank()) {
            return Collections.emptyList();   
        }
	    
	    List<Assunto> listaAssunto = assuntoServices.buscarNome(filtro);	       
	    
	    return listaAssunto;	   
	}
		
    @PostMapping("/excluir")
    public String excluir(@RequestParam String id, RedirectAttributes redirectAttributes) {
        try {
        	assuntoServices.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "O assunto foi excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (DadoVinculadoException e) {
	        redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
	        redirectAttributes.addFlashAttribute("tipoMensagem", "warning");
	    }  catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao tentar excluir o assunto.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
        }
        return "redirect:/assunto/cadastrar";
    }
}
