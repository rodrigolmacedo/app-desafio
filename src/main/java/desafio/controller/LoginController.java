package desafio.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import desafio.config.SessionContext;
import desafio.model.Usuario;
import desafio.service.interfaces.UserService;

@Named
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UserService service;

	private transient Usuario usuario;

	@PostConstruct
	void init() {
		setUsuario(new Usuario());
		SessionContext.getInstance().encerrarSessao();
	}

	public String logar() {
		try {
			service.login(usuario);
			return "/pages/index.xhtml?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return "";
		}

	}

	public String logout() {
		SessionContext.getInstance().encerrarSessao();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sessão encerrada.", null));
		return "/login.xhtml?faces-redirect=true";
	}

	// g&s
	public Usuario getUsuario() {
		return usuario;
	}

	public Usuario getUsuarioLogado() {
		return SessionContext.getInstance().getUsuarioLogado();
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
