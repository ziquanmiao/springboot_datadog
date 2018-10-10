package hello;

import java.util.concurrent.ThreadLocalRandom;

import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import datadog.trace.api.interceptor.MutableSpan;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class BadController {

    @RequestMapping("/bad")
    public String index() {
    	final Tracer tracer = GlobalTracer.get();
    	if (tracer != null && tracer.activeSpan() != null) {
			((MutableSpan)tracer.activeSpan()).getRootSpan().setTag("customer", ThreadLocalRandom.current().nextInt());
	    }
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