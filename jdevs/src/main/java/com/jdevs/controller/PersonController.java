package com.jdevs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import javax.validation.Valid;

import com.jdevs.Repository.PhoneRepository;
import com.jdevs.Repository.ProfessionRepository;
import com.jdevs.domain.Phone;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.jdevs.Repository.PersonRespository;
import com.jdevs.domain.Person;

@Controller
public class PersonController {
	@Autowired
	private PersonRespository personRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private ReportUtil reportUtil;
    @Autowired
    private ProfessionRepository professionRepository;
    @RequestMapping(method = RequestMethod.GET, value = "/registerperson")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/registerperson");
		modelAndView.addObject("personobj", new Person());
		Iterable<Person> personIt = personRepository.findAll();
		modelAndView.addObject("persons", personIt);
		modelAndView.addObject("profissioes", professionRepository.findAll());
		return modelAndView;
	}
    
	/*
	@RequestMapping(method = RequestMethod.POST, value = "/saveperson")
	public String save( Person person) {
			//@Valid @RequestParam("name") String name ,@Valid @RequestParam("lastname") String lastname
		Person person = new Person();
		person.setName(name);
		person.setLastname(lastname);
		personRepository.save(person);
		return "cadastro/registerperson";
		
	}*/
	
	@RequestMapping(method = RequestMethod.POST, value = "**/saveperson") // ** ignora tudo que vem antes de /saveperson
	public ModelAndView save(@Valid Person person, BindingResult bindingResult) {
	    person.setPhones(phoneRepository.getPhones(person.getId()));

		if (bindingResult.hasErrors()){// se existir erro , entro nessa condição
		    ModelAndView modelAndView= new ModelAndView("cadastro/registerperson");
            Iterable<Person> personIt = personRepository.findAll();
            modelAndView.addObject("persons", personIt);
            modelAndView.addObject("personobj", person);
            List<String> msg = new ArrayList<String>();
            for (ObjectError objectError: bindingResult.getAllErrors()){ // varrendo a lista de erros
                msg.add(objectError.getDefaultMessage()); // getdefault vem das anotações e outras
            }
            modelAndView.addObject("msg",msg);
            return modelAndView;
        }

	    personRepository.save(person);//salva no banco de dados
		ModelAndView andView = new ModelAndView("cadastro/registerperson");
		Iterable<Person> personIt = personRepository.findAll();
		andView.addObject("persons", personIt);
		andView.addObject("personobj", new Person());
		andView.addObject("profissioes", professionRepository.findAll());
		return andView;//retorna para a mesma tela ,/register person
	}
	
	
	//modelos de dados do banco com a tela
	@RequestMapping(method = RequestMethod.GET,value = "/listperson")
	public ModelAndView persons() {
		ModelAndView andView = new ModelAndView("cadastro/registerperson");
		Iterable<Person> personIt = personRepository.findAll();
		andView.addObject("persons", personIt);
		andView.addObject("personobj", new Person());
	
		return andView;
	}
	
	@GetMapping("/editperson/{idperson}")
	public ModelAndView update(@PathVariable("idperson") Long idperson) {
		Person person  =  personRepository.findById(idperson).get();
		
		ModelAndView modelAndView = new ModelAndView("cadastro/registerperson");
		modelAndView.addObject("personobj", person);
		Iterable<Person> personIt = personRepository.findAll();
		modelAndView.addObject("persons", personIt);
		modelAndView.addObject("profissioes", professionRepository.findAll());
		return modelAndView;
	}
	
	@GetMapping("/deleteperson/{id}")
	@ResponseStatus
	public ModelAndView delete(@PathVariable ("id") Long id) {
			if(personRepository.existsById(id)) {
				personRepository.delete(personRepository.findById(id).get());
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
			
			ModelAndView modelAndView = new ModelAndView("cadastro/registerperson");
			Iterable<Person> personIt = personRepository.findAll();
			modelAndView.addObject("persons", personIt);
			modelAndView.addObject("personobj", new Person());
			return modelAndView;
			
	}
	
	@PostMapping("**/searchName")
	public ModelAndView seachName(@RequestParam("text") String text,@RequestParam("pesquisasexo") String pesquisasexo) {
		List<Person> persons = new ArrayList<Person>();
		if (!pesquisasexo.isEmpty() && pesquisasexo !=null){
		    persons= personRepository.findPersonByNameSexo(text,pesquisasexo);
        }else {
		    persons = personRepository.findPersonByName(text);
        }
	    ModelAndView andView = new ModelAndView("cadastro/registerperson");
		andView.addObject("persons", persons);
		andView.addObject("personobj", new Person());
		return andView;
	}

    @GetMapping("**/telefones/{idperson}")
    public ModelAndView telefones(@PathVariable("idperson") Long idperson) {
        Optional<Person> person  =  personRepository.findById(idperson);

        ModelAndView modelAndView = new ModelAndView("cadastro/registerPhone");
        modelAndView.addObject("personobj", person.get());
        modelAndView.addObject("phones", phoneRepository.getPhones(idperson));
        return modelAndView;
    }

    @PostMapping("**/adcPhonePerson/{idperson}")
    public ModelAndView AddPhonePerson(Phone phone, @PathVariable("idperson") Long idperson){
   /*     if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("cadastro/registerPhone");
            modelAndView.addObject("personobj", personRepository.findById(idperson).get());
            modelAndView.addObject("phones", phoneRepository.getPhones(idperson));
            List<String> msg = new ArrayList<String>();
            for (ObjectError objectError: bindingResult.getAllErrors()){
                msg.add(objectError.getDefaultMessage());
            }
            modelAndView.addObject("msg",msg);
            return modelAndView;
        }

*/
	    Optional<Person> person  =  personRepository.findById(idperson);
        phone.setPerson(person.get());
        phoneRepository.save(phone);
        ModelAndView modelAndView = new ModelAndView("cadastro/registerPhone");
        modelAndView.addObject("personobj", person.get());
        modelAndView.addObject("phones", phoneRepository.getPhones(idperson));
        return modelAndView;
    }

    @GetMapping("/deletephone/{idphone}")
    @ResponseStatus
    public ModelAndView deletePhone(@PathVariable ("idphone") Long idphone) {
        Person person =  phoneRepository.findById(idphone).get().getPerson();

        if(phoneRepository.existsById(idphone)){
            phoneRepository.delete(phoneRepository.findById(idphone).get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        ModelAndView modelAndView = new ModelAndView("cadastro/registerPhone");
        Iterable<Person> personIt = personRepository.findAll();
        modelAndView.addObject("personobj", person);
        modelAndView.addObject("phones", phoneRepository.getPhones(person.getId()));
        return modelAndView;

    }
    
    @GetMapping("**/searchName")
	public void imprimePDF(@RequestParam("text") String text,@RequestParam("pesquisasexo") String pesquisasexo,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
    	
    	List<Person> person = new ArrayList<Person>();
    	if(pesquisasexo !=null && !pesquisasexo.isEmpty() && text!=null && !text.isEmpty()) {
    		person = personRepository.findPersonByNameSexo(text, pesquisasexo);
    	}else if(text != null && !text.isEmpty()) { // busca por nome
    		person = personRepository.findPersonByName(text);
    
    	}else if(pesquisasexo !=null && !pesquisasexo.isEmpty()) { // busca por sexo
    		person = personRepository.findPersonBySexoPessoa(pesquisasexo);
    
    	}else {
    		Iterable<Person> personIterator = personRepository.findAll();
    		for (Person person2 : personIterator) {
				person.add(person2);
			}
 
    	}
    	
    	// chama serviço que faz a geração do relatorio
    	byte[] pdf = reportUtil.gerarRelatorio(person,"person",httpServletRequest.getServletContext());
    	
    	// tamanho da resposta pro navegador
    	httpServletResponse.setContentLengthLong(pdf.length);
    	
    	// definir na resposa o tipo de arquivo
    	httpServletResponse.setContentType("application/octet-stream");
    	
    	// definir o cabeçalho da nossa respota
    	String headerKey = "Content-Disposition";
    	String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
    	httpServletResponse.setHeader(headerKey, headerValue);
    	// finaliza a respota pro navegador
    	httpServletResponse.getOutputStream().write(pdf);
    }

}
