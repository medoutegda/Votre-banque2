package org.me.metier;

import java.util.Date;

import org.me.dao.CompteRepository;
import org.me.dao.OperationRepository;
import org.me.entities.Compte;
import org.me.entities.CompteCourant;
import org.me.entities.Operation;
import org.me.entities.Retrait;
import org.me.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.*;

@Service
@Transactional
public class BanqueMeitierImpl implements IBanqueMetier {
	
	@Autowired
	private CompteRepository compteRespository;
	@Autowired
	private OperationRepository operationRespository;
	@Override
	public Compte consulterCompte(String codeCpte) {
		Compte cp = compteRespository.getOne(codeCpte);
		if(cp==null) throw new RuntimeException("Compte introvable");
		return cp;
	}

	@Override
	public void verser(String codeCpte, double montant) {
		Compte cp=consulterCompte(codeCpte);
		Versement v=new Versement(new Date(),montant,cp);
		operationRespository.save(v);
		cp.setSolde(cp.getSolde()+montant);
		compteRespository.save(cp);
		
		
	}

	@Override
	public void retirer(String codeCpte, double montant) {
		Compte cp=consulterCompte(codeCpte);
		double facilitesCaisse=0;
		if(cp instanceof CompteCourant)
			facilitesCaisse=((CompteCourant) cp).getDecouvert();
		if(cp.getSolde()+facilitesCaisse < montant)
			throw new RuntimeException("Solde insuffisant");
		Retrait r=new Retrait(new Date(),montant,cp);
		operationRespository.save(r);
		cp.setSolde(cp.getSolde()-montant);
		compteRespository.save(cp);
		
	}

	@Override
	public void virement(String codecpte1, String codecpte2, double montant) {
		retirer(codecpte1, montant);
		verser(codecpte2, montant);
		
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public Page<Operation> listOperation(String codecpte, int page, int size) {
		
		return operationRespository.listOperation(codecpte, new PageRequest(page, size));
	}

}
