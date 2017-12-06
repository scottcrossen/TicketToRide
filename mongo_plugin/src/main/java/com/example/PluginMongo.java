package com.example;

public class PluginMongo implements IPersistanceProvider{

    @Override
    public void initialize() {
        System.out.println("Mongo Plugin initialized");
    }
}
