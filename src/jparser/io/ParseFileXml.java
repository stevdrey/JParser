/*
 * Copyright 2016 srey.
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
package jparser.io;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import jparser.pojo.Customer;
import jparser.xml.adapter.LocalDateAdapter;

/**
 *
 * @author srey
 */
public class ParseFileXml implements FileParseable {

    @Override
    public Optional<Customer> read(Path source) {
        Optional<Customer> customer= Optional.empty();
        
        if (Files.exists(source, LinkOption.NOFOLLOW_LINKS) && source.toString().endsWith(".xml")) {
            try {
                JAXBContext ctx= JAXBContext.newInstance(Customer.class);
                Unmarshaller unmarchaller= ctx.createUnmarshaller();
                //unmarchaller.setAdapter(new LocalDateAdapter());
                
                customer= Optional.of((Customer) unmarchaller.unmarshal(source.toFile()));
            } catch (JAXBException ex) {
                Logger.getLogger(ParseFileXml.class.getSimpleName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        return customer;
    }

    @Override
    public boolean write(Customer customer, Path target) {
        boolean result= false;
        
        try {
            JAXBContext ctx= JAXBContext.newInstance(Customer.class);
            Marshaller marshaller= ctx.createMarshaller();
            
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(customer, target.toFile());
            
            result= true;
        } catch (JAXBException ex) {
            Logger.getLogger(ParseFileXml.class.getSimpleName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return result;
    }
    
}