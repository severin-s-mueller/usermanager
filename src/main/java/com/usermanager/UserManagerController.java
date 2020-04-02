package com.usermanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Severin Müller
 * Controller-Klasse
 *
 */
@Controller
public class UserManagerController {
	
	private UserRepository userRepo;
	
	public UserManagerController(UserRepository repo) {
        this.userRepo = repo;
    }
	
	
	
	@RequestMapping("/")
	public String index() {
		return "redirect:/users";
	}
	
	/**
	 * Zeigt entweder alle User oder sucht nach einem User anhand der E-Mail-Adresse
	 * 
	 * @param page Aktuelle Seite
	 * @param direction Sortier-Richtung
	 * @param sortBy Sortierfeld
	 * @param email Parameter für die Suche nach E-Mail-Adresse (findByEmailContaining)
	 * @param model Objekt für Model-Attribute
	 * @return
	 */
	@RequestMapping("/users")
	public String usersPage(@RequestParam(value = "page",required=false,defaultValue="1") int page,
			@RequestParam(value="direction",required=false,defaultValue="ASC") Sort.Direction direction,
			@RequestParam(value="sortBy",required=false,defaultValue="userId") String sortBy,
			@RequestParam(value="email",required=false,defaultValue="") String email,
			Model model) {
	
		// wenn der email (Suchparameter) nicht leer ist, wird eine Suche (findByEmailContaining) ausgeführt und alle entsprechenden User zurückgegeben
		if(email.equals("")){
			model.addAttribute("users",userRepo.findAll(PageRequest.of(page-1,10,Sort.by(direction, sortBy))));
			model.addAttribute("user", new User());
			model.addAttribute("sortByParam",sortBy);
			model.addAttribute("emailParam",email);
		}else {
			model.addAttribute("users",userRepo.findByEmailContaining(email,PageRequest.of(page-1,10,Sort.by(direction, sortBy))));
			model.addAttribute("user", new User());
			model.addAttribute("sortByParam",sortBy);
			model.addAttribute("emailParam",email);

	}
		
		return "users";
	}
	
	/**
	 * 
	 * @param user Das user-Objekt, welches gespeichert werden soll.
	 * @param redirectAttributes Enthält Information, ob Erfassung erfolgreich war oder fehlgeschlagen ist.
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		Date date = new Date();
		user.setCreated(date);
		// try/catch für die Erfassung eines Users. Löst einen "fehlgeschlagenen" oder "erfolgreichen" Alert aus. 
		try {
	    userRepo.save(user);
	    redirectAttributes.addFlashAttribute("result","Benutzererfassung erfolgreich. Benutzer "+user.getEmail()+" hinzugefügt.");
	    redirectAttributes.addFlashAttribute("alertClass", "alert-success alert-dismissible");
		}catch(Exception e){
		redirectAttributes.addFlashAttribute("result","Fehler - Benutzererfassung fehlgeschlagen");
	    redirectAttributes.addFlashAttribute("alertClass", "alert-danger alert-dismissible");
		System.out.println(e.getClass());
		}
		return "redirect:/users";

	
	     
	}
	/**
	 * Löscht den User mit der entsprechenden ID.
	 * @param id Die ID-des Users, der gelöscht werden soll
	 * @return Weiterleitung nach view "/users"
	 */
	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") int id) {
	    userRepo.deleteById(id);
	    return "redirect:/users";       
	}

}
