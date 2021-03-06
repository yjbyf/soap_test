/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.greglturnquist.mtom.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.spring.guides.gs_producing_web_service.Image;

/** @author Arjen Poutsma */
public class StubImageRepository implements ImageRepository {

    private static final Log logger = LogFactory.getLog(StubImageRepository.class);

    private Map<String,  byte[]> images = new HashMap<>();

    @Override
    public  byte[] readImage(String name) throws IOException {
        logger.info("Loading image " + name);
        Path file = Paths.get("D:\\jd.txt");
        try (InputStream in = Files.newInputStream(file);
            BufferedReader reader =
              new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.println(x);
        };
        return images.get(name);
    }

    @Override
    public void storeImage(Image image) throws IOException {
        logger.info("Storing image " + image.getName() );
        images.put(image.getName(), image.getImage());
    }
}
