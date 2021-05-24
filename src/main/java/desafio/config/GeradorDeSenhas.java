package desafio.config;

import org.mindrot.jbcrypt.BCrypt;

public class GeradorDeSenhas {

	public static void main(String[] args) {
		String hashed = BCrypt.hashpw("123456", BCrypt.gensalt());
		System.out.println(hashed);
	}

}
