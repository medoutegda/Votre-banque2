package org.me.metier;

import org.me.entities.Compte;
import org.me.entities.Operation;
import org.springframework.data.domain.Page;

public interface IBanqueMetier {
	public Compte consulterCompte(String codeCpte);
	public void verser(String codeCpte,double montant);
	public void retirer(String codecpte, double montant);
	public void virement(String codecpte1 , String codecpte2, double montant);
	public Page<Operation> listOperation(String codecpte,int page, int size);
	

}
