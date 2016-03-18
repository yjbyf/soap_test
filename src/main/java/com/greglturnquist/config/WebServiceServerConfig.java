package com.greglturnquist.config;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import com.greglturnquist.learningspringws.NetworkEventConsumer;
import com.greglturnquist.learningspringws.country.CountryEndpoint;
import com.greglturnquist.learningspringws.country.CountryRepository;
import com.greglturnquist.mtom.service.ImageRepository;
import com.greglturnquist.mtom.service.StubImageRepository;
import com.greglturnquist.mtom.ws.ImageRepositoryEndpoint;

@Configuration
@EnableWs
public class WebServiceServerConfig extends WsConfigurerAdapter {

	@Bean
	ServletRegistrationBean dispatcherServlet(ApplicationContext ctx) {

		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(ctx);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "network-events")
	DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema networkEventSchema) {

		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("NetworkEventPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://greglturnquist.com/test");
		wsdl11Definition.setSchema(networkEventSchema);
		return wsdl11Definition;
	}

	@Bean
	XsdSchema networkEventSchema() {
		return new SimpleXsdSchema(new ClassPathResource("network-event.xsd"));
	}

	@Bean
	NetworkEventConsumer networkEventConsumer() {
		return new NetworkEventConsumer();
	}

	@Bean
	PayloadLoggingInterceptor payloadLoggingInterceptor() {
		return new PayloadLoggingInterceptor();
	}

//	@Bean
//	PayloadValidatingInterceptor payloadValidatingInterceptor() {
//		final PayloadValidatingInterceptor payloadValidatingInterceptor = new PayloadValidatingInterceptor();
//		// payloadValidatingInterceptor.setSchema(new
//		// ClassPathResource("network-event.xsd"));
//		//payloadValidatingInterceptor.setSchema(new ClassPathResource("countries.xsd"));
//		return payloadValidatingInterceptor;
//	}

	@Bean
	XwsSecurityInterceptor securityInterceptor() {
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		securityInterceptor.setCallbackHandler(callbackHandler());
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return securityInterceptor;
	}

	@Bean
	SimplePasswordValidationCallbackHandler callbackHandler() {
		SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
		callbackHandler.setUsersMap(Collections.singletonMap("user", "password"));
		return callbackHandler;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(payloadLoggingInterceptor());
		//interceptors.add(payloadValidatingInterceptor());
		interceptors.add(securityInterceptor());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Bean(name = "countries")
	public DefaultWsdl11Definition countriesWsdl11Definition(XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CountriesPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
		wsdl11Definition.setSchema(countriesSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	// 以下是必须定义，在spring io guide中没有此部分，需要加入，以满足webservice security的程序需要
	// 否则会报endpoints找不到错误
	@Bean
	public CountryRepository countryRepository() {
		return new CountryRepository();
	}

	@Bean
	CountryEndpoint countryEndpoint(CountryRepository countryRepository) {
		return new CountryEndpoint(countryRepository);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Bean(name = "image")
	public DefaultWsdl11Definition mtomWsdl11Definition(XsdSchema mtomSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		//wsdl11Definition.setPortTypeName("ImageRepository");
		wsdl11Definition.setPortTypeName("ImagePort");
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
