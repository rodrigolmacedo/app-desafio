package desafio.repository;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import desafio.exceptions.UsuarioInvalidoException;
import desafio.exceptions.UsuarioNaoEncontradoException;
import desafio.model.Usuario;
import desafio.repository.interfaces.UserRepository;

@Stateless
public class UserRepositoryImpl implements UserRepository, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	@Override
	public List<Usuario> consultar(int first, int pageSize) {
		TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u", Usuario.class);
		query.setFirstResult(first);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	@Override
	public Usuario manter(Usuario usuario) {
		return em.merge(usuario);
	}

	@Override
	public void remover(Usuario usuario) {
		em.remove(em.getReference(Usuario.class, usuario.getId()));
	}

	@Override
	public Usuario encontrarLogin(Usuario usuario) {
		try {
			TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u where u.email = :email", Usuario.class);
			query.setParameter("email", usuario.getEmail());

			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new UsuarioNaoEncontradoException("Usuário não encontrado.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsuarioInvalidoException("Não foi possível continuar esta operação.");
		}
	}

	@Override
	public int quantidadeRegistrosConsulta() {
		return ((Long) em.createQuery("Select count(*) from Usuario").getSingleResult()).intValue();
	}

}
