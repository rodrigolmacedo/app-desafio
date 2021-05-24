package desafio.repository.interfaces;

import java.util.List;

import desafio.model.Usuario;

public interface UserRepository {
	public Usuario encontrarLogin(Usuario usuario);
	public List<Usuario> consultar(int first, int pageSize);
	public int quantidadeRegistrosConsulta();
	public Usuario manter(Usuario usuario);
	public void remover(Usuario usuario);
}
