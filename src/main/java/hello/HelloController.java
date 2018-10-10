package hello;

import java.util.concurrent.ThreadLocalRandom;

import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import datadog.trace.api.interceptor.MutableSpan;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.timgroup.statsd.ServiceCheck;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;

@RestController
public class HelloController {
	private static final StatsDClient statsd = new NonBlockingStatsDClient(
		"my.prefix",                          /* prefix to any stats; may be null or empty string */
		System.getenv("DD_AGENT_SERVICE_HOST"),                        /* common case: localhost */
		Integer.parseInt(System.getenv("DD_AGENT_STATSD_PORT")),                                 /* port */
		new String[] {"test:tag"}            /* Datadog extension: Constant tags, always applied */
	);
	
    @RequestMapping("/")
    public String index() {
    	statsd.incrementCounter("foo");

    	final Tracer tracer = GlobalTracer.get();
    	if (tracer != null && tracer.activeSpan() != null) {
			((MutableSpan)tracer.activeSpan()).getRootSpan().setTag("customer", ThreadLocalRandom.current().nextInt());
	    }
        return "Greetings from Spring Boot!";
    }

}