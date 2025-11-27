package org.lecture;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BugTracker {

    private final List<Bug> bugList = new ArrayList<>();
    private final Bug[] topTenBugArray = new Bug[10];
    private final Set<Integer> knownIds = new HashSet<>();
    private int criticalBugCount = 0;

    public void addBug(Bug bug) {
        if (knownIds.contains(bug.getId())) {
            throw new IllegalArgumentException("Bug ID already exists: " + bug.getId());
        }
        knownIds.add(bug.getId());
        bugList.add(bug);

        if (bug.getPriority() == Priority.CRITICAL && criticalBugCount < 10) {
            topTenBugArray[criticalBugCount] = bug;
            criticalBugCount++;
        } else if (bug.getPriority() == Priority.CRITICAL && criticalBugCount == 10) {
            System.out.println("Warning 10 critical bugs already exist");
        }

    }

    public void removeBug(int id) {
        Bug bug = bugList.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElseThrow(IndexOutOfBoundsException::new);
        bugList.remove(bug);
        knownIds.remove(bug.getId());

        if (bug.getPriority() == Priority.CRITICAL) {
            for (int i = 0; i < criticalBugCount; i++) {
                if (topTenBugArray[i] != null && topTenBugArray[i].getId() == bug.getId()) {
                    if (criticalBugCount - 1 - i >= 0) {
                        System.arraycopy(topTenBugArray, i + 1, topTenBugArray, i, criticalBugCount - 1 - i);
                    }
                    topTenBugArray[criticalBugCount - 1] = null;
                    criticalBugCount--;
                    break;
                }
            }

        }
    }


    public void printAllBugs() {
        bugList.sort(Comparator.comparing(Bug::getPriority));
        printHeader();
        for (Bug bug : bugList) {
            bug.printDetails();
        }
    }

    public void printBugsInState(State state) {
        printHeader();
        for (Bug bug : bugList) {
            if (bug.getState() == state) {
                bug.printDetails();
            }
        }
    }

    public void changeStateOfBug(int index, State destinationState) {
        switch (destinationState) {
            case IN_PROGRESS:
                bugList.get(index).startProgress();
                break;
            case FIXED:
                bugList.get(index).markedFixed();
                break;
            case CLOSED:
                bugList.get(index).closeBug();
                break;
            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

    public void printBugsByPriority(Priority priority) {

        printHeader();
        for (Bug bug : bugList) {
            if (bug.getPriority() == priority) {
                bug.printDetails();
            }
        }
    }

    public void printCountOfBugsByPriority(Priority priority) {

        int countLow = 0;
        int countMedium = 0;
        int countHigh = 0;
        int countCritical = 0;

        for (Bug bug : bugList) {
            switch (bug.getPriority()) {
                case LOW:
                    countLow++;
                    break;
                case MEDIUM:
                    countMedium++;
                    break;
                case HIGH:
                    countHigh++;
                    break;
                case CRITICAL:
                    countCritical++;
                    break;
            }
        }

        System.out.println("Critical bugs = " + countCritical);
        System.out.println("High bugs = " + countHigh);
        System.out.println("Medium bugs = " + countMedium);
        System.out.println("Low bugs = " + countLow);
    }

    private void printHeader() {
        System.out.printf("%-4s | %-25s | %-12s | %-9s%n", "ID", "Title", "State", "Priority");
        System.out.println("--------------------------------------------------------");
    }

}
