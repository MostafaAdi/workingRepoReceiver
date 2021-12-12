package com.steve.app.parameter;

import java.io.Serializable;

import javax.persistence.Column;
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
public class Parameter implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1671912937878919304L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "parameter_name", nullable = false, length = 45)
	private String parameterName;
	
	@Column(name = "parameter_value", nullable = false)
	private int ParameterValue;
	
	@Column(nullable = true, length = 50)
	private String addedBy;
	
	@Column(nullable = true, length = 50)
	private String modifiedBy;
	
	@Column(nullable = true, length = 40)
	private String createdAt;
	
	@Column(nullable = true, length = 40)
	private String updatedAt;
	
	@Version
	private long version;
}
