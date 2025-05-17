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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodb.MongoException;

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
    	
    	model.addAttribute("assunto", new Assunto()); 	     
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
			
			//Zerar id no salvamento de um novo cadastro
	        if (assunto.getId() != null && assunto.getId().isEmpty()) {
	        	assunto.setId(null);
	        }
			
			boolean novoCadastro = (assunto.getId() == null || assunto.getId().isEmpty());
			assuntoServices.salvar(assunto);

			if(novoCadastro) {
		        redirectAttrs.addFlashAttribute("mensagem", "Assunto cadastrado com sucesso!");
		        redirectAttrs.addFlashAttribute("tipoMensagem", "success");		
			}else {
		        redirectAttrs.addFlashAttribute("mensagem", "Assunto alterado com sucesso!");
		        redirectAttrs.addFlashAttribute("tipoMensagem", "success");		
			}

		}catch (MongoException e) {
		    redirectAttrs.addFlashAttribute("mensagem", "Erro ao acessar o banco de dados.");
		    redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
        }  catch(Exception e) {
            redirectAttrs.addFlashAttribute("mensagem", "Erro inesperado: " + e.getMessage());
            redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
		}

		return "redirect:/assunto/cadastrar";
	}
	
	@GetMapping("/consultar")
	public String consultar(@RequestParam(name = "filtro", required = false) String filtro, Model model) {
	    List<Assunto> listaAssunto = null;

	    if (filtro != null && !filtro.isEmpty()) {
	    	listaAssunto = assuntoServices.buscarNome(filtro);	    
	    }

	    model.addAttribute("assuntos", listaAssunto);    
	    return "formConsultaCargos"; 
	}
		
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
        	assuntoServices.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "O assunto foi excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao tentar excluir o assunto.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
        }
        return "redirect:/assunto/consultar";
    }
}
