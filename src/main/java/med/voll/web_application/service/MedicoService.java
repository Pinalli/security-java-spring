package med.voll.web_application.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import med.voll.web_application.domain.RegraDeNegocioException;
import med.voll.web_application.domain.medico.*;
import med.voll.web_application.repository.MedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository repository;
    private final UsuarioService   usuarioService;;

    public MedicoService(MedicoRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public Page<DadosListagemMedico> listar(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }

    @Transactional
    public void cadastrar(DadosCadastroMedico dados) {
        if (repository.isJaCadastrado(dados.email(), dados.crm(), dados.id())) {
            throw new RegraDeNegocioException("E-mail ou CRM já cadastrado para outro médico!");
        }

        if (dados.id() == null) {
            repository.save(new Medico(dados));
            usuarioService.cadastrarUsuario(dados.nome(), dados.email(), dados.crm());
        } else {
            var medico = repository.findById(dados.id())
                    .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado para atualização."));

            medico.atualizarDados(dados);
        }
    }


    public DadosCadastroMedico carregarPorId(Long id) {
        var medico = repository.findById(id).orElseThrow();
        return new DadosCadastroMedico(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade());
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public List<DadosListagemMedico> listarPorEspecialidade(Especialidade especialidade) {
        return repository.findByEspecialidade(especialidade).stream().map(DadosListagemMedico::new).toList();
    }

}
