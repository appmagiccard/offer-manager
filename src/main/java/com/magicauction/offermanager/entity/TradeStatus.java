package com.magicauction.offermanager.entity;

public enum TradeStatus {
    STARTED("S"), IN_PROGRESS("P"), FINISHED("F");

    private final String label;

    public String getLabel() {
        return label;
    }

    TradeStatus(String label) {
        this.label = label;
    }
}
