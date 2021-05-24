package desafio.config;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import desafio.model.Usuario;

public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		Usuario usuario = null;

		HttpSession sessao = ((HttpServletRequest) request).getSession(false);

		if (!Objects.isNull(sessao)) {
			usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		}

		if (Objects.isNull(usuario)) {
			String contextPath = ((HttpServletRequest) request).getContextPath();

			((HttpServletResponse) response).sendRedirect(contextPath+"/login.xhtml");
		} else {
			chain.doFilter(request, response);
		}

	}

}
