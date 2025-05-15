package br.edu.utfpr.td.tsi.agencia.digital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodb.MongoException;

import br.edu.utfpr.td.tsi.agencia.digital.dto.UsuarioDTO;
import br.edu.utfpr.td.tsi.agencia.digital.exception.DadosDuplicadosException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.SenhasDiferentesException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Usuario;
import br.edu.utfpr.td.tsi.agencia.digital.services.UsuarioServices;


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
    public String salvar(UsuarioDTO usuarioDTO, Model model, RedirectAttributes redirectAttrs) {
        try {
            // Verifica se as senhas coincidem
            if (!usuarioDTO.getPassword().equals(usuarioDTO.getConfirmarSenha())) {
                throw new SenhasDiferentesException("As senhas não coincidem.");
            }

            Usuario usuario = usuarioService.converterParaUsuario(usuarioDTO);
            
            boolean novoCadastro = (usuario.getId() == null || usuario.getId().isEmpty());
            usuarioService.salvar(usuario);

            if(novoCadastro) {
    	        redirectAttrs.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
    	        redirectAttrs.addFlashAttribute("tipoMensagem", "success");	
            }else {
    	        redirectAttrs.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");
    	        redirectAttrs.addFlashAttribute("tipoMensagem", "success");	
            }
        } catch (DadosDuplicadosException e) {
		    redirectAttrs.addFlashAttribute("mensagem", "Há dados que já está sendo utilizado por outro usuário");
		    redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
		}catch (MongoException e) {
		    redirectAttrs.addFlashAttribute("mensagem", "Erro ao acessar o banco de dados.");
		    redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
        }  catch(Exception e) {
            redirectAttrs.addFlashAttribute("mensagem", "Erro inesperado: " + e.getMessage());
            redirectAttrs.addFlashAttribute("tipoMensagem", "danger");
		}

        return "redirect:/usuario/cadastrar";
    }
    
	@GetMapping("/consultar")
	public String consultar(@RequestParam(name = "filtro", required = false) String filtro, Model model) {
	    List<Usuario> listaUsuario = null;

	    if (filtro != null && !filtro.isEmpty()) {
	    	listaUsuario = usuarioService.buscarPorNomeOuUsername(filtro);
	    }
 
	    model.addAttribute("usuarios", listaUsuario);    
	    return "formConsultaUsuarios"; 
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") String id, RedirectAttributes redirectAttributes, Model model) {
	    try {
	        Usuario usuario = usuarioService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
	        UsuarioDTO usuarioDTO = usuarioService.converterParaDTO(usuario);
	              
	        model.addAttribute("usuarioDTO", usuarioDTO);
	        
	        return "formCadastroUsuario";

	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("mensagem", "Ocorreu um erro ao tentar editar o usuário.");
	        redirectAttributes.addFlashAttribute("tipoMensagem", "danger");

	        return "redirect:/usuario/consultar";
	    }
	}
	
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
        	usuarioService.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");
            redirectAttributes.addFlashAttribute("tipoMensagem", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao tentar excluir o usuário.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "danger");
        }
        return "redirect:/usuario/consultar";
    }
}
