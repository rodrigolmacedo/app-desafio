package desafio.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import desafio.model.Telefone;
import desafio.model.Usuario;
import desafio.service.interfaces.UserService;

@Named
@ViewScoped
public class CadastroController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UserService userService;

	private Usuario usuario;	
	private Telefone telefone;

	@PostConstruct
	void init() {
		novoCadastro();
	}

	public void novoCadastro() {
		setUsuario(new Usuario());
		novoTelefone();
	}
	
	public void novoTelefone() {
		setTelefone(new Telefone());
	}
	
	public void removerTelefone() {
		
		try {
			usuario.getTelefones().remove(telefone);
			novoTelefone();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefone removido.", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao tentar remover telefone.", null));
		}
	}
	
	public void adicionarTelefone() {
		try {
			usuario.getTelefones().add(telefone);
			novoTelefone();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefone adicionado com sucesso.", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao tentar adicionar um novo telefone.", null));
		}
	}

	public void cadastrar() {
		try {
			userService.manter(usuario);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso.", null));
			novoCadastro();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}

	// s&g
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

}
