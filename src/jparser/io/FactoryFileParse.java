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

import java.util.stream.Stream;

/**
 *
 * @author srey
 */
public final class FactoryFileParse {
    
    public enum TypeParce {
        JSON("json"), XML("xml"), NULL("null");
        
        private final String value;

        private TypeParce(String value) {
            this.value = value;
        }
        
        public static TypeParce fromValue(String value) {
            return Stream.<TypeParce>of(TypeParce.values()).
                    filter(t -> t.value.equals(value)).
                    findFirst().
                    orElse(NULL);
        }
    };
    
    public static FileParseable createNewInstance(TypeParce type) {
        switch (type) {
            case JSON:
                return new ParseFileJson();
                
            case XML:
                return new ParseFileXml();
        }
        
        return null;
    }
}
