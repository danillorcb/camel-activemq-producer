package br.com.fiap.scj31.amb;

import java.io.File;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RouteProducer {

	public static void main(String[] args) {

		CamelContext context = new DefaultCamelContext();
		context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616/"));

		try {
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					String path = System.getProperty("user.dir");

					from("file:" + path + File.separator + "input?noop=true")
							.to("activemq:queue:vendas");
				}
			});

			context.start();
			Thread.sleep(60 * 1000);
			context.stop();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
