package hello;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private PentahoService pentahoService;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping("/waterSeries")
    @ResponseBody
    public String waterSeries(@RequestParam(value="date") String date) {
    	String webPage = "http://smartcity.ikusi.com:8080/pentaho/content/cda/doQuery?solution=ikusi&path=/dashboardmanager/dashboard/2546&file=ConsumoAgua.cda&dataAccessId=ConsumoMedioAgua_Comercio_PorHora_DiaVsAVG&paramanyo=2015&parammes=1&paramdia=1";
        String jsonResponse = getPentahoService().call(webPage);
        
//        ObjectMapper mapper = new ObjectMapper();
//        Object json;
//		try {
//			json = mapper.readValue(jsonResponse, Object.class);
//			jsonResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
//			System.out.println(jsonResponse);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
        
		return jsonResponse;
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update() {
    	String webPage = "https://ik-cdm:8443/iklogin/datasource/update";
        String jsonResponse = getPentahoService().call(webPage);
        
		return jsonResponse;
    }

	public PentahoService getPentahoService() {
		return pentahoService;
	}

	public void setPentahoService(PentahoService pentahoService) {
		this.pentahoService = pentahoService;
	}
}
