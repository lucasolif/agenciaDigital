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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodb.MongoException;

import br.edu.utfpr.td.tsi.agencia.digital.exception.DadosDuplicadosException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.DadoVinculadoException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Jornalista;
import br.edu.utfpr.td.tsi.agencia.digital.services.JornalistaServices;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/jornalista")
public class JornalistaController {
	
	@Autowired
	private JornalistaServices jornalistaService;

    @GetMapping(value = "/cadastrar")
    public String exibirPaginaCadastro(Model model) {
        Jornalista jornalista = new Jornalista();
        jornalista.setAtivo(true); 

        model.addAttribute("jornalista", jornalista);
        
        return "formCadastroJornalista";
    }
    
	@PostMapping(value = "/cadastrar")
	public String salvar (@Valid Jornalista jornalista, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) { 		
		try {		
			
			// Retorna mensagem caso os campos obrigatório não tenha sido preenchido
			if (bindingResult.hasErrors()) {
				model.addAttribute("jornalista", jornalista);
	            return "formCadastroJornalista";
			}
			      	
			jornalistaService.salvar(jornalista);
			
	        redirectAttrs.addFlashAttribute("mensagem", "Jornalista cadastrado/alterado com sucesso!");
	        redirectAttrs.addFlashAttribute("tipoMensagem", "success");			

		}catch (DadosDuplicadosException e) {
		    redirectAttrs.addFlashAttribute("mensagem", e.getMessage());
		    redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
		}catch (MongoException e) {
		    redirectAttrs.addFlashAttribute("mensagem", e.getMessage());
		    redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
        }  catch(Exception e) {
            redirectAttrs.addFlashAttribute("mensagem", "Erro inesperado: " + e.getMessage());
            redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
		}

		return "redirect:/jornalista/cadastrar";

	}
	
	@PostMapping("/excluir")
	public String excluir(@RequestParam String id, RedirectAttributes redirectAttributes) {
		try {
	        jornalistaService.excluir(id);
	        redirectAttributes.addFlashAttribute("mensagem", "Jornalista excluído(a) com sucesso!");
	        redirectAttributes.addFlashAttribute("tipoMensagem", "success");
	    } catch (DadoVinculadoException e) {
	        redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
	        redirectAttributes.addFlashAttribute("tipoMensagem", "warning");
	    }  catch (Exception e) {
	        redirectAttributes.addFlashAttribute("mensagem", "Erro ao tentar excluir o(a) jornalista.");
	        redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
	    }
	    return "redirect:/jornalista/cadastrar";
	}
    
    @GetMapping("/consultar")
    @ResponseBody
    public List<Jornalista> buscarJornalistas(@RequestParam(name = "filtro") String filtro) {
        List<Jornalista> listaPessoas = null;
      

        if (filtro != null && !filtro.isEmpty()) {
        	listaPessoas = jornalistaService.buscarNome(filtro);          
        }

        return listaPessoas;
    }
}
