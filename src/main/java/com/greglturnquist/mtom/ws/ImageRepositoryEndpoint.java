/*
 * Copyright 2005-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.greglturnquist.mtom.ws;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.greglturnquist.mtom.service.ImageRepository;

import io.spring.guides.gs_producing_web_service.Image;
import io.spring.guides.gs_producing_web_service.LoadImageRequest;
import io.spring.guides.gs_producing_web_service.LoadImageResponse;
import io.spring.guides.gs_producing_web_service.ObjectFactory;
import io.spring.guides.gs_producing_web_service.StoreImageRequest;



/** @author Arjen Poutsma */
@Endpoint
public class ImageRepositoryEndpoint {

    private ImageRepository imageRepository;

    private ObjectFactory objectFactory;

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    public ImageRepositoryEndpoint(ImageRepository imageRepository) {
        Assert.notNull(imageRepository, "'imageRepository' must not be null");
        this.imageRepository = imageRepository;
        this.objectFactory = new ObjectFactory();
    }

    @PayloadRoot(localPart = "storeImageRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public void store(@RequestPayload StoreImageRequest request) throws IOException {
        //Image request = requestElement.getValue();
        imageRepository.storeImage(request.getImage());
    }

//    @PayloadRoot(localPart = "LoadImageRequest", namespace = NAMESPACE_URI)
//    @ResponsePayload
//    public JAXBElement<Image> load(@RequestPayload JAXBElement<String> requestElement) throws IOException {
//        String name = requestElement.getValue();
//        Image response = new Image();
//        response.setName(name);
//        response.setImage(imageRepository.readImage(name));
//        return objectFactory.createLoadImageResponse(response);
//    }

    @PayloadRoot(localPart = "loadImageRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
	public LoadImageResponse load(@RequestPayload LoadImageRequest request) throws IOException {
		String name = request.getName();
		LoadImageResponse response = new LoadImageResponse();
		Image image = new Image();
		image.setName(name);
		image.setImage(imageRepository.readImage(name));
		response.setImage(image);
		return response;
	}

}
