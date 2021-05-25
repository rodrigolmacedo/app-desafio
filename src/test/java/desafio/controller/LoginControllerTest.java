package desafio.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import desafio.config.SessionContext;
import desafio.model.Telefone;
import desafio.model.Usuario;
import desafio.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

	@Mock
	private SessionContext sessionContext;
	@Mock
	private ExternalContext externalContext;
	@Mock
	private FacesContext context;

	@Mock
	private UserServiceImpl userService;

	@InjectMocks
	private LoginController controller;

	@BeforeEach
	void testInit() {
		mockFacesContext();
		controller.init();
	}

	@Test
	void testLogar() {
		
		Usuario usuario = new Usuario("Rodrigo", "eu@gmail.com", "123456", new ArrayList<Telefone>());
		controller.setUsuario(usuario);
		String logarRetorno = controller.logar();

		Mockito.verify(userService, Mockito.times(1)).login(usuario);
		assertFalse(logarRetorno.isEmpty());
	}

	@Test
	void testLogarErro() {
		controller.setUsuario(null);
		String logarRetorno = controller.logar();
		assertTrue(logarRetorno.isEmpty());
	}

	@Test
	void testLogout() {
		String logoutRetorno = controller.logout();
		assertFalse(logoutRetorno.isEmpty());
	}

	// necessário para mockar o facesContext
	public void mockFacesContext() {

		// Use Java reflection to set the FacesContext to our Mock, since
		// FacesContext.setCurrentInstance() is protected.
		Mockito.lenient().when(context.getExternalContext()).thenReturn(externalContext);
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
