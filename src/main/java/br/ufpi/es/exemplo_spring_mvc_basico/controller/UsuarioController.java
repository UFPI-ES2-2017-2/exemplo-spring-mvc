package br.ufpi.es.exemplo_spring_mvc_basico.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufpi.es.exemplo_spring_mvc_basico.controller.ControladorUsuarios;
import br.ufpi.es.exemplo_spring_mvc_basico.dados.RepositorioListaUsuarios;
import br.ufpi.es.exemplo_spring_mvc_basico.modelo.Usuario;

@Controller
public class UsuarioController {
	private static final long serialVersionUID = 1L;
	private RepositorioListaUsuarios repositorio;
	private ControladorUsuarios controladorDados;
	
	public UsuarioController(){
		this.iniciaControladorDados();
	}
	
	public void iniciaControladorDados(){
        repositorio = new RepositorioListaUsuarios();
        repositorio.populaUsuarios();
        controladorDados = new ControladorUsuarios(repositorio);
	}
	
	//recurso1
	@RequestMapping(value="/recurso1")
	public String recurso1(){
		return "";
	}
	
	//recurso2
	@RequestMapping(value="/bucarUsuario", method=RequestMethod.POST)
	protected String processarBusca(HttpServletRequest request, HttpSession session, Model model) throws ServletException, IOException {
		//recupar os dados passados pelo formulario de busca
		String conteudo = request.getParameter("conteudobusca");
		String tipo = request.getParameter("opcaotipo");
		
		List<Usuario> lista = new LinkedList<Usuario>();
		
		lista = controladorDados.buscaPorConteudoETipo(conteudo, tipo);
		
		//checa se tem uma sessão válida e reencaminha a resposta para exibir o resultado da busca
		if (session.getAttribute("usuario") != null) {
			model.addAttribute("usuarios", lista);
			return "lista-usuarios";
		}else {
			return "principal";
		}
	}
	
	//recurso 3 
	@RequestMapping(value="/formularioBusca", method=RequestMethod.GET)
	public String carregaFormularioBusca(HttpSession session){		
    	if (session.getAttribute("usuario") != null) {
    		return("buscar-usuario");
    	}else {
    		return("home");
    	}
	}
	
	//recurso 4
	@RequestMapping(value="/listarUsuarios", method=RequestMethod.GET)
	public String processarListaUsuarios(HttpServletRequest request, HttpSession session, Model model) throws ServletException, IOException {
		List<Usuario> lista = controladorDados.getUsuarios();

		if (session.getAttribute("usuario") != null) {
			model.addAttribute("usuarios", lista);
			return("lista-usuarios");
		} else {
			return("home");
		}

	}
	
}