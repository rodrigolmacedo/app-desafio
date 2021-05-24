package desafio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import desafio.model.Telefone;
import desafio.model.Usuario;
import desafio.service.interfaces.UserService;

@Named
@ViewScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;

	private LazyDataModel<Usuario> usuariosLazy;

	private Usuario usuarioSelecionado;
	private Telefone telefoneSelecionado;
	private Integer quantidadeUsuariosCadastrados = 0;

	@PostConstruct
	void init() {
		consultar();
		novoCadastro();
	}

	public void novoCadastro() {
		setUsuarioSelecionado(new Usuario());
		novoTelefone();
	}

	public void novoTelefone() {
		setTelefoneSelecionado(new Telefone());
	}

	public void removerCadastro() {
		try {
			Objects.requireNonNull(usuarioSelecionado);
			userService.remover(usuarioSelecionado);
			novoCadastro();
			consultar();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro removido!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao tentar remover o usuário.", null));
		}
	}

	public void manterCadastro() {
		try {
			Objects.requireNonNull(usuarioSelecionado);
			userService.manter(usuarioSelecionado);
			consultar();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, Boolean.TRUE.equals(usuarioSelecionado.isNovo()) ? "Cadastro adicionado!":"Cadastro alterado!", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}

	private void consultar() {
		userService.quantidadeUsuariosCadastrados().ifPresent(this::setQuantidadeUsuariosCadastrados);
		setUsuariosLazy(new LazyDataModel<Usuario>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Usuario> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				List<Usuario> lista = new ArrayList<>();
				setRowCount(quantidadeUsuariosCadastrados);
				Optional<List<Usuario>> optionalUsuarios = userService.consultar(first, pageSize);
				if (optionalUsuarios.isPresent()) {
					lista = optionalUsuarios.get();
				}

				return lista;
			}
		});
	}

	public void removerTelefone() {

		try {
			usuarioSelecionado.getTelefones().remove(telefoneSelecionado);
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
			usuarioSelecionado.getTelefones().add(telefoneSelecionado);
			novoTelefone();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefone adicionado com sucesso.", null));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao tentar adicionar um novo telefone.", null));
		}
	}

	// s&g
	public LazyDataModel<Usuario> getUsuariosLazy() {
		return usuariosLazy;
	}

	public void setUsuariosLazy(LazyDataModel<Usuario> usuariosLazy) {
		this.usuariosLazy = usuariosLazy;
	}

	public Integer getQuantidadeUsuariosCadastrados() {
		return quantidadeUsuariosCadastrados;
	}

	public void setQuantidadeUsuariosCadastrados(Integer quantidadeUsuariosCadastrados) {
		this.quantidadeUsuariosCadastrados = quantidadeUsuariosCadastrados;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public Telefone getTelefoneSelecionado() {
		return telefoneSelecionado;
	}

	public void setTelefoneSelecionado(Telefone telefoneSelecionado) {
		this.telefoneSelecionado = telefoneSelecionado;
	}

}
