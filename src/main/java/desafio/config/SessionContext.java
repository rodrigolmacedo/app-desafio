package desafio.config;

import java.util.Objects;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import desafio.model.Usuario;

public class SessionContext {
	private static SessionContext instance;

	private SessionContext() {
	}

	public static SessionContext getInstance() {
		if (Objects.isNull(instance)) {
			instance = new SessionContext();
		}

		return instance;
	}

	private ExternalContext currentExternalContext() {
		FacesContext currentInstance = FacesContext.getCurrentInstance();

		if (Objects.isNull(currentInstance)) {
			throw new IllegalAccessError("O FacesContext não pode ser chamado fora de uma requisição http!");
		} else {
			return currentInstance.getExternalContext();
		}
	}

	public Usuario getUsuarioLogado() {
		return (Usuario) getAttribute("usuarioLogado");
	}

	public void setUsuarioLogado(Usuario usuario) {
		setAttribute("usuarioLogado", usuario);
	}

	public void encerrarSessao() {
		currentExternalContext().invalidateSession();
	}

	public Object getAttribute(String nome) {
		return currentExternalContext().getSessionMap().get(nome);
	}

	public void setAttribute(String nome, Object valor) {
		currentExternalContext().getSessionMap().put(nome, valor);
	}

}
