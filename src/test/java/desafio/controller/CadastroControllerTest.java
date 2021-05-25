package desafio.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import desafio.model.Telefone;
import desafio.model.Usuario;
import desafio.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class CadastroControllerTest {

	@Mock
	private FacesContext context;

	@Mock
	private UserServiceImpl userService;

	@InjectMocks
	private CadastroController controller;

	@BeforeEach
	void init() {
		mockFacesContext();
		controller.init();
		Telefone telefone = new Telefone(21, "25805442", "fixo");
		List<Telefone> telefones = new ArrayList<Telefone>();
		Usuario usuario = new Usuario("Teste", "teste@gmail.com", "123456", telefones);
		controller.setUsuario(usuario);
		controller.setTelefone(telefone);
	}

	@Test
	void deveRemoverUmTelefone() {
		controller.removerTelefone();
		assertTrue(controller.getUsuario().getTelefones().isEmpty());
	}

	@Test
	void deveAdicionarUmTelefone() {
		Telefone telefone = new Telefone(21, "25805442", "fixo");
		controller.setTelefone(telefone);
		assertDoesNotThrow(() -> {
			controller.adicionarTelefone();
			assertFalse(controller.getTelefone().equals(telefone));
			assertEquals(1, controller.getUsuario().getTelefones().size());
		});

	}

	@Test
	void deveInformarErroAoAdicionarTelefoneInvalido() {
		controller.setTelefone(null);
		controller.adicionarTelefone();
		Mockito.verify(context, Mockito.times(1)).addMessage(Mockito.any(), Mockito.any(FacesMessage.class));

	}

	@Test
	void deveCadastrarUmUsuario() {
		Usuario usuarioCadastrado = new Usuario("Teste", "teste@gmail.com", "123456", new ArrayList<Telefone>());
		Mockito.when(userService.manter(Mockito.any())).thenReturn(Optional.of(usuarioCadastrado));
		assertDoesNotThrow(() -> {
			controller.cadastrar();
		});

	}

	// necessário para mockar o facesContext
	public void mockFacesContext() {

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
