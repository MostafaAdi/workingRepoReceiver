package com.steve.app.estate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.steve.app.parameter.ParameterService;
import com.steve.app.security.User;
import com.steve.app.security.UserRepository;

@Service
public class EstateService {

	@Autowired
	private EstateRepository estateRepo;
	
	@Autowired
	private ParameterService parameterService;
	
	@Transactional
	public int addEstate(Estate estate ) {
		try{
			if(estate.getSharesCount() == 0 ) {
				estate.setSharesCount(this.parameterService.getParameterValue("default_shares"));
			}
			estate.setCreatedAt(LocalDate.now().toString());
			estate = this.estateRepo.save(estate);
			return 0 ; 
		}catch(Exception e){
			throw new ServiceException("could not add estate!") ;
		}
	}
	
	
	public Estate getById(int estateId ) {
		Optional<Estate> estate = this.estateRepo.findById(estateId);
		if(estate.isPresent()) {
			return estate.get();
		}else {
			return null ; 
		}
	}
	
	
	public List<Estate> getAll(){
		return this.estateRepo.findAll(); 
	}
	
	public List<Estate> getNonSelled(){
		List<Estate> result = new ArrayList<>();
		for(Estate estate : this.estateRepo.findAll()) {
			if(!estate.isSelled()) {
				estate.setSellPrice(estate.getPrice() * this.parameterService.getParameterValue("price_ratio"));
				result.add(estate);
			}
		}
		return result ; 
	}
	
	public List<Estate> getSelled(){
		List<Estate> result = new ArrayList<>();
		for(Estate estate : this.estateRepo.findAll()) {
			if(estate.isSelled()) {
				result.add(estate);
			}
		}
		return result ; 
	}

	
	@Transactional
	public int updateEstate(Estate estate) {
		Optional<Estate> optional = this.estateRepo.findById(estate.getId());
		if(optional.isPresent()) {
			if(estate.getVersion() != optional.get().getVersion()) {
//				throw new ConflictException();
			}
			Estate estateFromDb = optional.get();
		 
		
			estateFromDb.setEstateName(estate.getEstateName());
			estateFromDb.setClientName(estate.getClientName());
			estateFromDb.setSellDate(estate.getSellDate());
			estateFromDb.setPrice(estate.getPrice());
			estateFromDb.setSharesCount(estate.getSharesCount());
			estateFromDb.setSellPrice(estate.getSellPrice());
			estateFromDb.setUpdatedAt(LocalDate.now().toString());
			estateFromDb.setModifiedBy(this.get_current_User().getUsername());
			this.estateRepo.save(estateFromDb);
			return 0 ;
		}else {
			throw new ServiceException("cannot update Estate!") ; 
		}
	}
	
	@Transactional
	public int deleteEstate(int estateId ) {
		try {
			this.estateRepo.deleteById(estateId);
			return 0 ; 
		}catch(Exception e ) {
			throw new ServiceException("cannot delete estate!") ; 
		}
	}
	
	
	public List<Estate> getNotSelledEstates(){
		List<Estate> estatesList = new ArrayList<Estate>() ; 
		for(Estate estate : this.estateRepo.findAll()) {
			if(estate.getClientName().equalsIgnoreCase("none")) {
				estatesList.add(estate);
			}
		}
		return estatesList ; 
	}
	
	@Transactional
	public int sellEstate(Estate estate) {
		
		Optional<Estate> optional = this.estateRepo.findById(estate.getId());
		if(optional.isPresent()) {
			if(estate.getVersion() != optional.get().getVersion()) {
//				throw new ConflictException();
			}
			Estate dbEstate = optional.get();
		
			dbEstate.setClientName(estate.getClientName());
			dbEstate.setSellPrice(this.parameterService.getParameterValue("price_ratio") * estate.getPrice());
			dbEstate.setSellDate(LocalDate.now().toString());
			dbEstate.setSelled(true);
			dbEstate = this.estateRepo.save(dbEstate);
//			msgSender.sendOrderCar(dbEstate);
			return 0 ;
		}else {
			throw new ServiceException("cannot sell estate") ; 
		}
	}
	
	
	
	@Autowired
	private UserRepository userRepo ; 
	
	private User get_current_User() {
		String username ; 
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Object principal =  auth.getPrincipal();
	    if(principal instanceof UserDetails) {
	    	username = ((UserDetails) principal).getUsername() ; 
		    for(User user : this.userRepo.findAll()) {
		    	if(user.getUsername().equalsIgnoreCase(username)) {
		    		return user ; 
		 		}
		 	}
	    }
	    return null  ; 
    }

}
