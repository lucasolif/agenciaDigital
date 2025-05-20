package br.edu.utfpr.td.tsi.agencia.digital.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

import br.edu.utfpr.td.tsi.agencia.digital.dto.UsuarioDTO;
import br.edu.utfpr.td.tsi.agencia.digital.dto.UsuarioDTOConsulta;
import br.edu.utfpr.td.tsi.agencia.digital.exception.DadosDuplicadosException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.UsuarioAdministradorException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Usuario;
import br.edu.utfpr.td.tsi.agencia.digital.services.UsuarioServices;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioServices usuarioService; 
    
    @GetMapping(value = "/cadastrar")
    public String exibirPaginaCadastro(Model model) {
    	UsuarioDTO usuarioDTO = new UsuarioDTO();
    	usuarioDTO.setStatus(true);
        
    	model.addAttribute("usuarioDTO", usuarioDTO); 
        
        return "formCadastroUsuario";
    }

    @PostMapping(value = "/cadastrar")
    public String salvar(@Valid UsuarioDTO usuarioDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
        
        if (bindingResult.hasErrors()) {
            // Retorna mensagem de campos vazios
            model.addAttribute("usuarioDTO", usuarioDTO);
            return "formCadastroUsuario";
        }
    	
    	try {    
            usuarioService.salvar(usuarioDTO);

            if(usuarioDTO.getId() == null || usuarioDTO.getId().isEmpty()) {
    	        redirectAttrs.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
    	        redirectAttrs.addFlashAttribute("tipoMensagem", "success");	
            }else {
    	        redirectAttrs.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");
    	        redirectAttrs.addFlashAttribute("tipoMensagem", "success");	
            }
        } catch (DadosDuplicadosException error) {
		    redirectAttrs.addFlashAttribute("mensagem", error.getMessage());
		    redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
		}catch (MongoException error) {
		    redirectAttrs.addFlashAttribute("mensagem", error.getMessage());
		    redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
        }  catch(Exception error) {
            redirectAttrs.addFlashAttribute("mensagem", error.getMessage());
            redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
		}

        return "redirect:/usuario/cadastrar";
    }
    
    @GetMapping("/consultar")
    @ResponseBody
    public List<UsuarioDTOConsulta> buscarUsuario(@RequestParam(name = "filtro") String filtro) {
       
        if (filtro == null || filtro.isBlank()) {
            return Collections.emptyList();
        }
    	
    	List<Usuario> listaUsuario = usuarioService.buscarNomeUsername(filtro, filtro); 
    	
        return listaUsuario.stream()
	        .map(usuario -> {
	            UsuarioDTOConsulta dto = new UsuarioDTOConsulta();
	            dto.setId(usuario.getId());
	            dto.setNome(usuario.getNome());
	            dto.setUsername(usuario.getUsername());
	            dto.setEmail(usuario.getEmail());
	            dto.setCelular(usuario.getCelular());
	            return dto;
	        })
	        .collect(Collectors.toList());
    }
	
    @PostMapping("/excluir")
    public String excluir(@RequestParam String id, @RequestParam String username, RedirectAttributes redirectAttributes) {
        try {
        	usuarioService.excluir(id, username);
            redirectAttributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (UsuarioAdministradorException e) {
	        redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
	        redirectAttributes.addFlashAttribute("tipoMensagem", "warning");
	    }   catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao tentar excluir o usuário.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
        }
        return "redirect:/usuario/cadastrar";
    }
}
