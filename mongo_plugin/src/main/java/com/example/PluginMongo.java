package com.example;

public class PluginMongo implements IPersistanceProvider{
    MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    DB db = mongo.getDB("TicketToRide");

    @Override
    public void initialize() {
        System.out.println("Mongo Plugin initialized");
    }
}
