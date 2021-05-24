package desafio.exceptions;

@SuppressWarnings("serial")
public class UsuarioInvalidoException extends RuntimeException {
	public UsuarioInvalidoException(String message) {
		super(message);
	}

	public UsuarioInvalidoException() {
	}

}
