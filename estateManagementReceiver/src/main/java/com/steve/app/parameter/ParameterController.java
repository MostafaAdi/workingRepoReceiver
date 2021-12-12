package com.steve.app.parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ParameterController {

	@Autowired 
	private ParameterService parameterService;
	
	@RequestMapping(method = RequestMethod.GET , value = "/params/view")
	public ModelAndView getAllParameters() {
		ModelAndView mav = new ModelAndView("params/view");
	    mav.addObject("parameters", this.parameterService.getAllParameters());
		return mav ;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/params/add")
	public ModelAndView addParameter() {
		ModelAndView mav = new ModelAndView("params/add");
		mav.addObject("parameter", new Parameter());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/params/save")
	public ModelAndView saveParameter(@ModelAttribute Parameter parameter) {
		this.parameterService.createParameter(parameter);
		return getAllParameters();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/params/update/{id}")
	public ModelAndView updateParameter(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("params/update");
		mav.addObject("parameter", this.parameterService.getById(id));
		return mav;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/params/update")
	public ModelAndView commitUpdate (@ModelAttribute Parameter parameter) {
		this.parameterService.updateParameter(parameter);
		return getAllParameters();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "params/del/{id}")
	public ModelAndView deleteParameter(@PathVariable int id) {
		
		try {
			this.parameterService.deleteParameter(id);
		}catch(Exception e ) {}
		
		return this.getAllParameters() ;
	}
	
	
	
}