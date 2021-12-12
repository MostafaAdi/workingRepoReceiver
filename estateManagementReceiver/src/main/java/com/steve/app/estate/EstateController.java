package com.steve.app.estate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.steve.app.parameter.ParameterService;

@RestController
public class EstateController {

	@Autowired 
	private ParameterService parameterService;
	
	@Autowired
	private EstateService estateService;
	
	@RequestMapping(method = RequestMethod.GET , value = "/estates/all")
	public ModelAndView getAllEstates() {
		ModelAndView mav = new ModelAndView("estates/all");
	    mav.addObject("estates", this.estateService.getAll());
		return mav ;
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/estates/notsold")
	public ModelAndView getnonSelled() {
		ModelAndView mav = new ModelAndView("estates/notsold");
		mav.addObject("estates", this.estateService.getNonSelled());
	    return mav ;
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/estates/sold")
	public ModelAndView getSelled() {
		ModelAndView mav = new ModelAndView("estates/sold");
		mav.addObject("estates", this.estateService.getSelled());
	    return mav ;
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/estates/sell/{id}")
	public ModelAndView sellEstate(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("estates/sell");
		Estate estate = this.estateService.getById(id);
		estate.setSellPrice(estate.getPrice() * parameterService.getParameterValue("price_ratio"));
	    mav.addObject("estate",estate);
	    return mav ;
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/estates/checkout")
	public ModelAndView checkout(@ModelAttribute Estate estate ){
		this.estateService.sellEstate(estate);
		return this.getSelled();
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/estates/add")
	public ModelAndView addGet() {
		ModelAndView mav = new ModelAndView("estates/add");
	    mav.addObject("estate",new Estate());
	    return mav ;
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/estates/save")
    public ModelAndView SaveEstate(@ModelAttribute Estate estate ) {
        this.estateService.addEstate(estate);
        return getAllEstates(); 
    }
	
	@RequestMapping(method = RequestMethod.GET , value = "/estates/update/{id}")
	public ModelAndView updateEstate(@PathVariable int id ){
		ModelAndView mav = new ModelAndView("estates/update");
	    mav.addObject("estate",this.estateService.getById(id));
	    return mav ;
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/estates/update")
    public ModelAndView update(@ModelAttribute Estate estate) {
        this.estateService.updateEstate(estate);
        return getAllEstates(); 
    }
	
	@RequestMapping(method = RequestMethod.GET , value = "/estates/del/{id}")
	public ModelAndView deleteEstate(@PathVariable int id){
		try {
			this.estateService.deleteEstate(id);
		}catch(Exception e ) {}
		return this.getAllEstates() ;
	}
	
//	@RequestMapping(method = RequestMethod.GET , value = "/export/view")
//	public ModelAndView exportSettings(){
//		ModelAndView mav = new ModelAndView("export");
//		mav.addObject("export", new Details());
//		return mav ; 
//	}
//	
//	@RequestMapping(method = RequestMethod.POST , value = "/export/send")
//	public ModelAndView export(@ModelAttribute Details details ){
//		this.carService.export(details);
//		return getAllCars();
//	}
}
