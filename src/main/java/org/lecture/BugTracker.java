package org.lecture;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BugTracker {

    private final List<Bug> bugList = new ArrayList<>();
    private final Bug[] topTenBugArray = new Bug[10];
    private int criticalBugCount = 0;

    public void addBug(Bug bug) {
        bugList.add(bug);

        if (bug.priority == Priority.CRITICAL && criticalBugCount < 10) {
            topTenBugArray[criticalBugCount] = bug;
            criticalBugCount++;
        }  else if (bug.priority == Priority.CRITICAL && criticalBugCount == 10) {
            System.out.println("Warning 10 critical bugs already exist");
        }

    }

    public void removeBug(int index) {

        //remove bug from List
        bugList.remove(index);

        //get Bugobject from list
        Bug bug = bugList.get(index);

        if (bug.priority == Priority.CRITICAL) {
            for (int i = 0; i < criticalBugCount; i++) {
                if (topTenBugArray[i] == bug) {
                    // Shift elements left to fill the gap
                    for (int j = i; j < criticalBugCount - 1; j++) {
                        topTenBugArray[j] = topTenBugArray[j + 1];
                    }
                    topTenBugArray[criticalBugCount - 1] = null;
                    criticalBugCount--;
                    break;
                }
            }

        }
    }


    public void printAllBugs() {
        bugList.sort(Comparator.comparing(bug -> bug.priority));
        for (int i = 0; i < bugList.size(); i++) {
            Bug bug = bugList.get(i);
            System.out.printf("index = " + i + " ");
            bug.printDetails();
        }
    }

    public void printBugsInState(State state) {
        for (Bug bug : bugList) {
            if (bug.state == state) {
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


        for (Bug bug : bugList) {
            if (bug.priority == priority) {
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
            switch (bug.priority) {
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

}
