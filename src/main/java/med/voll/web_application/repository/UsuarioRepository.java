package med.voll.web_application.repository;

import med.voll.web_application.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Optional: é um container que pode ou não conter um valor não nulo. Se um valor estiver presente, é lançada uma exceção NullPointerException.
 */
public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByEmailIgnoreCase(String email);

}
