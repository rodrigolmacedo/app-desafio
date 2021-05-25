package desafio.controller;

import java.lang.reflect.Method;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import desafio.config.SessionContext;
import desafio.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

	@Mock
	private SessionContext sessionContext;
	@Mock
	private ExternalContext externalContext;
	@Mock
	private FacesContext context;

	@Mock
	private UserServiceImpl userService;

	@InjectMocks
	private UsuarioController controller;

	@BeforeEach
	void testInit() {
		mockFacesContext();
		controller.init();
	}

//	@Test
//	void testNovoCadastro() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testNovoTelefone() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemoverCadastro() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testManterCadastro() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemoverTelefone() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAdicionarTelefone() {
//		fail("Not yet implemented");
//	}

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
