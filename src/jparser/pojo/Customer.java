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
package jparser.pojo;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jparser.xml.adapter.LocalDateAdapter;

/**
 *
 * @author srey
 */
@XmlRootElement
public class Customer {
    private String name;
    private String lastname;
    private String id;
    private String browser;
    private String ipAddress;
    
    private LocalDate birthday;

    public Customer(String name, String lastname, String id, String browser, String ipAddress, LocalDate brithday) {
        super();
        
        this.name = name;
        this.lastname = lastname;
        this.id = id;
        this.browser = browser;
        this.ipAddress = ipAddress;
        this.birthday = brithday;
    }

    public Customer(String name, String lastname, String id) {
        this(name, lastname, id, "", "", null);
    }

    public Customer() {
        this("", "", "");
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    @XmlElement
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    @XmlElement
    public void setId(String id) {
        this.id = id;
    }

    public String getBrowser() {
        return browser;
    }

    @XmlElement
    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @XmlElement
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @XmlElement
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    
}