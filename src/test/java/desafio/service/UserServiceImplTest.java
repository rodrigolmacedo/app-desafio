package desafio.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import desafio.exceptions.UsuarioInvalidoException;
import desafio.model.Usuario;
import desafio.repository.UserRepositoryImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private FacesContext context;
	@Mock
	private UserRepositoryImpl repository;
	@InjectMocks
	private UserServiceImpl service;

	@Test
	void deveRetornarUmaListaDeUsuarios() {

		Mockito.when(repository.consultar(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<Usuario>());
		Optional<List<Usuario>> consultar = service.consultar(1, 10);

		Assertions.assertTrue(consultar.get().isEmpty());
	}

	@Test
	void deveRemoverUmUsuario() {
		Usuario usuarioAremover = new Usuario("Fulano", "eu@gmail.com", "", new ArrayList<>());

		service.remover(usuarioAremover);

		Mockito.verify(repository, Mockito.times(1)).remover(usuarioAremover);
	}

	@Test
	void deveRetornarAquantidadeDeUsuariosCadastrados() {
		Integer quantidadeDeRegistrosARetornar = 1;
		Mockito.when(repository.quantidadeRegistrosConsulta()).thenReturn(quantidadeDeRegistrosARetornar);

		Optional<Integer> quantidadeUsuariosCadastrados = service.quantidadeUsuariosCadastrados();

		assertSame(quantidadeUsuariosCadastrados.get(), quantidadeDeRegistrosARetornar);
	}

	@Test
	void deveSalvarUmUsuario() {
		Usuario usuario = new Usuario("Fulano", "eu@gmail.com", "123456", new ArrayList<>());
		Mockito.when(repository.manter(Mockito.any())).thenReturn(usuario);

		Optional<Usuario> manter = service.manter(usuario);

		assertSame(usuario, manter.get());

	}

	@Test
	void deveRealizarOLoginCorretamente() {
		mockFacesContext();
		String senha = "123456";
		String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());
		Usuario usuarioLogin = new Usuario("Fulano", "eu@gmail.com", senha, new ArrayList<>());
		Usuario usuarioRetornado = new Usuario("Fulano", "eu@gmail.com", senhaHash, new ArrayList<>());
		Mockito.when(repository.encontrarLogin(Mockito.any())).thenReturn(usuarioRetornado);
		assertDoesNotThrow(() -> service.login(usuarioLogin));

	}

	@Test
	void naoDeveRealizarOLoginCorretamente() {
		String senhaHash = BCrypt.hashpw("123456", BCrypt.gensalt());
		Usuario usuarioLogin = new Usuario("Fulano", "eu@gmail.com", "1234567", new ArrayList<>());
		Usuario usuarioRetornado = new Usuario("Fulano", "eu@gmail.com", senhaHash, new ArrayList<>());
		Mockito.when(repository.encontrarLogin(Mockito.any())).thenReturn(usuarioRetornado);
		assertThrows(UsuarioInvalidoException.class, () -> service.login(usuarioLogin));

	}

	// necessário para mockar o facesContext
	public void mockFacesContext() {

		// Use Mockito to make our Mocked FacesContext look more like a real one
		// while making it returns other Mocked objects
		ExternalContext externalContext = Mockito.mock(ExternalContext.class);
		Mockito.when(context.getExternalContext()).thenReturn(externalContext);

		// Use Java reflection to set the FacesContext to our Mock, since
		// FacesContext.setCurrentInstance() is protected.
		try {
			Method setter = FacesContext.class.getDeclaredMethod("setCurrentInstance",
					new Class[] { FacesContext.class });
			setter.setAccessible(true);
			setter.invoke(null, new Object[] { context });
		} catch (Exception e) {
			System.err.println("Exception in reflection-based access to FacesContext");
			e.printStackTrace();
		}
	}

}
