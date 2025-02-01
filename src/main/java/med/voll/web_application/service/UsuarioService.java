package med.voll.web_application.service;

import med.voll.web_application.domain.usuario.Usuario;
import med.voll.web_application.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCase(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public void cadastrarUsuario( String nome, String email,  String senha) {
        String passwordEncrypt = passwordEncoder.encode(senha);
        usuarioRepository.save(new Usuario(nome, email, passwordEncrypt));
    }
}
