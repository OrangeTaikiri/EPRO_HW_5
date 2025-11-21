package org.lecture;

public enum Priority {
    LOW(0),
    MEDIUM(1),
    HIGH(2),
    CRITICAL(3);

    private int order;

    Priority(int order) {
        this.order = order;
    }

}
