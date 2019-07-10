package br.ufc.TaskManager.controllers;

import br.ufc.TaskManager.models.Tarefa;
import br.ufc.TaskManager.models.Usuario;
import br.ufc.TaskManager.repository.TaskRepository;
import br.ufc.TaskManager.servicos.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/tarefas")
public class TaskControler {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/listar")
    public ModelAndView listar(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("tarefas/listar");
        String emailUsuario = request.getUserPrincipal().getName();
        mv.addObject("tarefas", taskRepository.carregarTarefasUsuarios(emailUsuario));
        return mv;
    }


    @GetMapping("/inserir")
    public ModelAndView inserir(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("tarefas/inserir");
        mv.addObject("tarefa",new Tarefa());
        return mv;
    }


    @PostMapping("/inserir")
    public ModelAndView inserir(@Valid Tarefa tarefa, BindingResult result, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        if(tarefa.getDataExpiracao() == null){
            result.rejectValue("dataExpiracao","tarefa.DataExpiracaoInvalida",
                    "A data de expiração é obrigatória.");
        }else{
            if(tarefa.getDataExpiracao().before(new Date())){
                result.rejectValue("dataExpiracao","tarefa.DataExpiracaoInvalida",
                        "A data de expiração não pdde ser menor que a data atual.");
            }
        }

        if (result.hasErrors()){
            mv.setViewName("tarefas/inserir");
            mv.addObject(tarefa);
        }else{
            String emailUsuario = request.getUserPrincipal().getName();
            Usuario usuarioLogado = userService.encontrarPorEmail(emailUsuario);
            tarefa.setUsuario(usuarioLogado);
            taskRepository.save(tarefa);
            mv.setViewName("redirect:/tarefas/listar");
        }
        return mv;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        Tarefa tarefa = taskRepository.getOne(id);
        mv.setViewName("tarefas/alterar");
        mv.addObject("tarefa",tarefa);
        return mv;
    }

    @PostMapping("/alterar")
    public ModelAndView alterar(@Valid Tarefa tarefa, BindingResult result){
        ModelAndView mv = new ModelAndView();
        if(tarefa.getDataExpiracao() == null){
            result.rejectValue("dataExpiracao","tarefa.DataExpiracaoInvalida",
                    "A data de expiração é obrigatória.");
        }else{
            if(tarefa.getDataExpiracao().before(new Date())){
                result.rejectValue("dataExpiracao","tarefa.DataExpiracaoInvalida",
                        "A data de expiração não pode ser antes que a data atual.");
            }
        }

        if (result.hasErrors()){
            mv.setViewName("tarefas/alterar");
            mv.addObject(tarefa);
        }else{
            mv.setViewName("redirect:/tarefas/listar");
            taskRepository.save(tarefa);
        }
        return mv;
    }


    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id){
        taskRepository.deleteById(id);
        return "redirect:/tarefas/listar";
    }

    @GetMapping("/concluir/{id}")
    public String concluir(@PathVariable("id") Long id){
        Tarefa tarefa = taskRepository.getOne(id);
        tarefa.setConcluida(true);
        taskRepository.save(tarefa);
        return "redirect:/tarefas/listar";
    }

}
