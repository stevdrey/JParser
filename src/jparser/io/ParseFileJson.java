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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import jparser.json.adapter.LocalDateAdapter;
import jparser.pojo.Customer;

/**
 *
 * @author srey
 */
public class ParseFileJson implements FileParseable {

    @Override
    public Optional<Customer> read(Path source) {
        Optional<Customer> customer= Optional.empty();
        
        if (Files.exists(source, LinkOption.NOFOLLOW_LINKS) && source.toString().endsWith(".json")) {
            Gson gson= new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
            
            try (Reader reader= new FileReader(source.toFile())) {
                customer= Optional.of(gson.fromJson(reader, Customer.class));
                
            } catch (IOException ex) {
                Logger.getLogger(ParseFileJson.class.getSimpleName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        return customer;
    }

    @Override
    public boolean write(Customer customer, Path target) {
        boolean result= false;
        
        try (JsonWriter writer= new JsonWriter(new FileWriter(target.toFile()))) {
            Gson gson= new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).
                    create();
            Type typeSrc= new TypeToken<Customer>(){}.getType();
            
            gson.toJson(customer, typeSrc, writer);
            
            result= true;
            
        } catch (IOException ex) {
                Logger.getLogger(ParseFileJson.class.getSimpleName()).log(Level.SEVERE, ex.getMessage(), ex);
         }
        
        return result;
    }
    
}
 