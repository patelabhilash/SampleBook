package com.sbkafkaweb.kafkaweb.pojo;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Customer{
    public String subscriberType;
    public String providerContactId;
    public String firstName;
    public String lastName;
    public String title;
    public String middleInitialName;
    public String suffix;
    public String preferredlanguage;
    public String businessName;
    public String homePhoneNumber;
    public String cellPhoneNumber;
    public String workPhoneNumber;
    public String primaryEmail;
    public String secondaryEmail;
    public String emergencyContactRelationShip;
    public MailingAddress mailingAddress;
    public HomeAddress homeAddress;
}