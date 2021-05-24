package desafio.exceptions;

@SuppressWarnings("serial")
public class UsuarioNaoEncontradoException extends RuntimeException {
	public UsuarioNaoEncontradoException(String message){
		super(message);
	}
	
	public UsuarioNaoEncontradoException() {}
}
