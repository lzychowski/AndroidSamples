// Copyright © 2016, YOLOnerds - Leszek Zychowski, All rights reserved

package com.yolonerds.dstreasurechest;

import java.util.ArrayList;

/**
 * Based on http://stackoverflow.com/questions/4878159/whats-the-best-way-to-share-data-between-activities
 */
public class GeneratorOptions {

    GeneratorOptions(){
        expansions.add("DKQ");
        expansions.add("AC");
        expansions.add("RTV");
        expansions.add("IC");
        expansions.add("WOG");
        expansions.add("TOH");
    }

    private ArrayList<String> expansions = new ArrayList<String>();
    private int level;
    private boolean limitLevels;

    public ArrayList<String> getExpansions() {
        return expansions;
    }

    public void addExpansion(String expansion){
        expansions.add(expansion);
    }

    public void removeExpansion(String expansion) {
        expansions.remove(expansion);
    }

    public int getLevels(){
        return level;
    }

    public void setLevels(int level){
        this.level = level;
    }

    public boolean getLimitLevels(){
        return limitLevels;
    }

    public void setLimitLevels(boolean limitLevels){
        this.limitLevels = limitLevels;
    }

    private static final GeneratorOptions options = new GeneratorOptions();

    public static GeneratorOptions getInstance(){
        return options;
    }
}
