package com.steve.app.estate;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1925801523257049307L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

	@Version
	private Long version; 
	
	private String estateName = " " ; 
	
	private int price = 0; 
	
	private int sellPrice  = 0 ;
	
	private int sharesCount   = 0 ; 
	
	private String sellDate = " "; 
	
	private String clientName = "none";

	
	private String addedBy = " "; 
	
	private String modifiedBy = " "; 
	
	private String createdAt = " " ;
	
	private String updatedAt = " ";
	
	private boolean selled = false ;

	public Estate(Long version, String estateName, int price, int sellPrice, int sharesCount, String sellDate,
			String clientName, String addedBy, String modefiedBy, String createdAt, String updatedAt, boolean selled) {
		super();
		this.version = version;
		this.estateName = estateName;
		this.price = price;
		this.sellPrice = sellPrice;
		this.sharesCount = sharesCount;
		this.sellDate = sellDate;
		this.clientName = clientName;
		this.addedBy = addedBy;
		this.modifiedBy = modefiedBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.selled = selled;
	}
	
	
}
