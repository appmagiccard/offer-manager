package com.magicauction.offermanager.entity;

/**
 * NEAR_MINT, LIGHT_PLAY, HEAVY_PLAY, DAMAGED
 */
public enum CardState {
    NEAR_MINT("NM","NEAR_MINT"),
    LIGHT_PLAY("LP", "LIGHT_PLAY"),
    HEAVY_PLAY("HP", "HEAVY_PLAY"),
    DAMAGED("DG", "DAMAGED");

    private final String label;
    private final String name;

    public String getLabel() {
        return label;
    }

    CardState(String label, String name) {
        this.label = label;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
