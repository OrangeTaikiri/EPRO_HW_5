package org.lecture;

public class Bug {
    private int id;
    private String title;
    private State state;
    private Priority priority;

    public Bug(String title, Priority priority, int id) {
        this.title = title;
        this.state = State.OPEN;
        this.priority = priority;
        this.id = id;
    }

    public void startProgress() {
        if (this.state == State.OPEN) {
            this.state = State.IN_PROGRESS;
        } else {
            throw new IllegalStateException("Can't start progress. Bug is in wrong state");
        }
    }

    public void markedFixed() {
        if (this.state == State.IN_PROGRESS) {
            this.state = State.FIXED;
        } else {
            throw new IllegalStateException("Can't mark bug as fixed. Bug is in wrong state");
        }
    }

    public void closeBug() {
        if (this.state == State.FIXED) {
            this.state = State.CLOSED;
        } else {
            throw new IllegalStateException("Can't close bug. Bug is in wrong state");
        }
    }

    public void printDetails() {
        System.out.printf("%-4d | %-25s | %-12s | %-9s%n", this.id, this.title, this.state, this.priority);
    }

    public int getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public Priority getPriority() {
        return priority;
    }

}
