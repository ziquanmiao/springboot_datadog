package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class BadController {

    @RequestMapping("/bad")
    public String index() {
    	try 
		{
		    Thread.sleep(4);
		} 
		catch(InterruptedException e)
			{
	    }
	    return "Greetings from Spring Boot!";
    }

}