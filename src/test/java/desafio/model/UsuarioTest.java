package desafio.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UsuarioTest {
	
	
	@Test
	void testeIntanciaModel() {
		Assertions.assertDoesNotThrow(()->{
			
		
		
		Usuario usuario = new Usuario();
		String nome = "Nome";
		String email = "email@email.com";
		String senha = "senha";
		ArrayList<Telefone> telefones = new ArrayList<>();
		
		Usuario usuarioComConstrutor = new Usuario(nome, email, senha, telefones);
		usuarioComConstrutor.setId(1L);
		
		assertEquals(nome, usuarioComConstrutor.getNome());
		assertEquals(email, usuarioComConstrutor.getEmail());
		assertEquals(senha, usuarioComConstrutor.getSenha());
		assertEquals(Long.valueOf("1"), usuarioComConstrutor.getId());
		assertEquals(telefones, usuarioComConstrutor.getTelefones());
		
		usuarioComConstrutor.toString();
		
		
		usuario.setEmail(email);
		usuario.setId(1L);
		usuario.setNome(nome);
		usuario.setSenha(senha);
		usuario.setTelefones(telefones);
		
		usuario.equals(usuarioComConstrutor);
		usuario.equals(usuario);
		usuario.equals(null);
		assertFalse(usuario.isNovo());
		
		assertEquals(usuario.hashCode(), usuarioComConstrutor.hashCode());
		
		assertTrue(usuarioComConstrutor.equals(usuarioComConstrutor));
		
		});
		
	}

}
