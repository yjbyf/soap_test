package com.greglturnquist.mtom.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurationSupport;
import org.springframework.ws.server.endpoint.adapter.DefaultMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import com.greglturnquist.mtom.service.ImageRepository;
import com.greglturnquist.mtom.service.StubImageRepository;
import com.greglturnquist.mtom.ws.ImageRepositoryEndpoint;

/**
 * @author Arjen Poutsma
 */
//@EnableWs
//@Configuration
public class MtomServerConfiguration extends WsConfigurationSupport {

	@Bean
	@Override
	public DefaultMethodEndpointAdapter defaultMethodEndpointAdapter() {
		List<MethodArgumentResolver> argumentResolvers =
				new ArrayList<MethodArgumentResolver>();
		argumentResolvers.add(methodProcessor());

		List<MethodReturnValueHandler> returnValueHandlers =
				new ArrayList<MethodReturnValueHandler>();
		returnValueHandlers.add(methodProcessor());

		DefaultMethodEndpointAdapter adapter = new DefaultMethodEndpointAdapter();
		adapter.setMethodArgumentResolvers(argumentResolvers);
		adapter.setMethodReturnValueHandlers(returnValueHandlers);

		return adapter;
	}

	@Bean
	public MarshallingPayloadMethodProcessor methodProcessor() {
		return new MarshallingPayloadMethodProcessor(marshaller());
	}

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		//marshaller.setContextPath("org.springframework.ws.samples.mtom.schema");
		marshaller.setContextPath("com.greglturnquist.mtom.schema");
		marshaller.setMtomEnabled(true);
		return marshaller;
	}

	@Bean
	public DefaultWsdl11Definition mtom(XsdSchema mtomSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("ImageRepository");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
		wsdl11Definition.setSchema(mtomSchema);
		return wsdl11Definition;
	}

	@Bean
	public SimpleXsdSchema mtomSchema() {
		return new SimpleXsdSchema(new ClassPathResource("mtom.xsd"));
	}

	@Bean
	public ImageRepository imageRepository() {
		return new StubImageRepository();
	}

	@Bean
	public ImageRepositoryEndpoint imageRepositoryEndpoint(ImageRepository imageRepository) {
		return new ImageRepositoryEndpoint(imageRepository);
	}

}
