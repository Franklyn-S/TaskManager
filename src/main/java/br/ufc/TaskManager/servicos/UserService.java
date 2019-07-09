package br.ufc.TaskManager.servicos;

import br.ufc.TaskManager.models.Usuario;
import br.ufc.TaskManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario encontrarPorEmail(String email){
        return userRepository.findByEmail(email);
    }


    public void salvar(Usuario usuario){
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        userRepository.save(usuario);
    }
}
