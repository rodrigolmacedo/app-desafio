package desafio.service.interfaces;

import java.util.List;
import java.util.Optional;

import desafio.model.Usuario;

public interface UserService {
	public Optional<List<Usuario>> consultar(int first, int pageSize);
	public Optional<Integer> quantidadeUsuariosCadastrados();
	public Optional<Usuario> manter(Usuario usuario);
	public void remover(Usuario usuario);
	public void login(Usuario usuario);
}
