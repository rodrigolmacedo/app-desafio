package desafio.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import desafio.config.SessionContext;
import desafio.exceptions.UsuarioInvalidoException;
import desafio.model.Usuario;
import desafio.repository.interfaces.UserRepository;
import desafio.service.interfaces.UserService;

@Stateless
public class UserServiceImpl implements UserService {

	@Inject
	private UserRepository userRepository;

	@Override
	public Optional<List<Usuario>> consultar(int first, int pageSize) {
		return Optional.of(userRepository.consultar(first, pageSize));
	}

	@Override
	public Optional<Usuario> manter(Usuario usuario) {
		if (Boolean.TRUE.equals(usuario.isNovo()))
			usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
		return Optional.of(userRepository.manter(usuario));
	}

	@Override
	public void remover(Usuario usuario) {
		userRepository.remover(usuario);
	}

	@Override
	public void login(Usuario usuario) {

		Usuario usuarioEncontrado = userRepository.encontrarLogin(usuario);

		boolean senhaCorreta = BCrypt.checkpw(usuario.getSenha(), usuarioEncontrado.getSenha());

		if (senhaCorreta) {
			SessionContext.getInstance().setAttribute("usuarioLogado", usuarioEncontrado);
		} else {
			throw new UsuarioInvalidoException("Email e/ou Senha inválidos!");
		}

	}

	@Override
	public Optional<Integer> quantidadeUsuariosCadastrados() {
		return Optional.of(userRepository.quantidadeRegistrosConsulta());
	}

}
