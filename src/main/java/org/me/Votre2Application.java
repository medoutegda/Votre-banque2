package org.me;

import java.util.Date;

import org.me.dao.ClientRepository;
import org.me.dao.CompteRepository;
import org.me.dao.OperationRepository;
import org.me.entities.Client;
import org.me.entities.Compte;
import org.me.entities.CompteCourant;
import org.me.entities.CompteEpargne;
import org.me.entities.Retrait;
import org.me.entities.Versement;
import org.me.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Votre2Application implements CommandLineRunner  {
	
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private IBanqueMetier banqueMetier;
	
	
	public static void main(String[] args) {
	SpringApplication.run(Votre2Application.class, args);
	
	
	}

	@Override
	public void run(String... args) throws Exception {
		
		Client c1 = clientRepository.save(new Client("Hassan","hassan@gmail.com"));
		Client c2 = clientRepository.save(new Client("Rachid","Rachid@gmail.com"));
		
		Compte cp1 = compteRepository.save(new CompteCourant("c1", new Date(), 90000, c1, 6000));
		Compte cp2 = compteRepository.save(new CompteEpargne("c2", new Date(), 6000, c2, 5.5));
		
		operationRepository.save(new Versement(new Date(),9000,cp1));
		operationRepository.save(new Versement(new Date(),6000,cp1));
		operationRepository.save(new Versement(new Date(),2300,cp1));
		operationRepository.save(new Retrait(new Date(),9000,cp1));
		
		operationRepository.save(new Versement(new Date(),2300,cp2));
		operationRepository.save(new Versement(new Date(),400,cp2));
		operationRepository.save(new Versement(new Date(),2300,cp2));
		operationRepository.save(new Retrait(new Date(),3000,cp2));
		
		banqueMetier.verser("c1", 11111);
		banqueMetier.virement("c1", "c2", 4000);
		
		
		
	}
}
