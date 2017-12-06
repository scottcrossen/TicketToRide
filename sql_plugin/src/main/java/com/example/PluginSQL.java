package com.example;

public class PluginSQL implements IPersistanceProvider {
    @Override
    public void initialize() {
        System.out.println("SQL Plugin initialized");
    }
}
