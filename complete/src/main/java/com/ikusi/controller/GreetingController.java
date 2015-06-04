package com.ikusi.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ikusi.model.Greeting;
import com.ikusi.pentahoConnector.model.PentahoQueryParam;
import com.ikusi.pentahoConnector.services.PentahoQueryBuilder;
import com.ikusi.pentahoConnector.services.PentahoRestService;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private PentahoRestService pentahoRestService;
    
    @Autowired
    private PentahoQueryBuilder pentahoQueryBuilder;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping("/waterSeries")
    @ResponseBody
    public String waterSeries(@RequestParam(value="date") String date) {
    	String pentahoDashboar = "2546";
		String pentahoCda = "ConsumoAgua.cda";
		String pentahoQuery = "ConsumoMedioAgua_Comercio_PorHora_DiaVsAVG";
		
		PentahoQueryParam param1 = new PentahoQueryParam();
		param1.setName("paramanyo");
		param1.setValue("2015");
		
		PentahoQueryParam param2 = new PentahoQueryParam();
		param2.setName("parammes");
		param2.setValue("6");
		
		PentahoQueryParam param3 = new PentahoQueryParam();
		param3.setName("paramdia");
		param3.setValue("3");
		
		String createQueryUrl = getPentahoQueryBuilder().createQueryUrl(pentahoDashboar, pentahoCda, pentahoQuery, param1, param2, param3);

        String jsonResponse = getPentahoRestService().call(createQueryUrl);
        
		return jsonResponse;
    }

	public PentahoRestService getPentahoRestService() {
		return pentahoRestService;
	}

	public void setPentahoRestService(PentahoRestService pentahoRestService) {
		this.pentahoRestService = pentahoRestService;
	}

	public PentahoQueryBuilder getPentahoQueryBuilder() {
		return pentahoQueryBuilder;
	}

	public void setPentahoQueryBuilder(PentahoQueryBuilder pentahoQueryBuilder) {
		this.pentahoQueryBuilder = pentahoQueryBuilder;
	}
}
