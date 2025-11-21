package org.lecture;

public class Bug {
    String title;
    State state;
    Priority priority;

    public Bug(String title, Priority priority) {
        this.title = title;
        this.state = State.OPEN;
        this.priority = priority;
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
        System.out.printf("Title: %s .... State: %s .... Priority: %s %n", this.title, this.state, this.priority);
    }

}
