package org.me.web;

import org.me.entities.Compte;
import org.me.entities.Operation;
import org.me.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BanqueController {
	
	@Autowired
	private IBanqueMetier banqueMetier;
	
	@RequestMapping("/operations")
	public String index() {
		return "comptes";
		
	}
	
	@RequestMapping("/consulterCompte")
	public String consulter(Model model , String codeCompte) {
		model.addAttribute("codeCompte",codeCompte);
		try {
			System.out.println("#####################");
			Compte cp=banqueMetier.consulterCompte(codeCompte);
			Page<Operation> pageOperations=banqueMetier.listOperation(codeCompte, 0, 4);
			model.addAttribute("listOperations", pageOperations.getContent());
			model.addAttribute("compte", cp);
		}catch (Exception e) {
			model.addAttribute("execption",e);
		}
		
		return "comptes";
		
	}
	
	@RequestMapping(value="/saveOperation", method=RequestMethod.POST)
	public String saveOperation( Model model, String typeOperation, String codeCompte, double montant, String codeCompte2) {
		try {
			
			if (typeOperation.equals("VERS")) {
				banqueMetier.verser(codeCompte, montant);
			}else if (typeOperation.equals("RETR")) {
				banqueMetier.retirer(codeCompte, montant);
			}else if (typeOperation.equals("VIR")) {
				banqueMetier.virement(codeCompte, codeCompte2, montant);
			} 
			
		} catch (Exception e) {
			model.addAttribute("error",e);
			return "redirect:/consulterCompte?codeCompte="+codeCompte+"&error="+e.getMessage();
		}
		
		return "redirect:/consulterCompte?codeCompte="+codeCompte;
	}
	
	

}
