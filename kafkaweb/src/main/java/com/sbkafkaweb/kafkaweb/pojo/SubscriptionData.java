package com.sbkafkaweb.kafkaweb.pojo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionData {
    public CustomerSubscription customerSubscription;
    @JsonProperty("Customer")
    public ArrayList<Customer> customer;
    @JsonProperty("CustomerProduct")
    public ArrayList<CustomerProduct> customerProduct;
}