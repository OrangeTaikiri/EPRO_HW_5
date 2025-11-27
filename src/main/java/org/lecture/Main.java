package org.lecture;

import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        int id = 1;
        BugTracker tracker = new BugTracker();
        id = addTestData(tracker, id);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                switch (choice) {
                    case 1 -> id = addNewBug(scanner, tracker, id);
                    case 2 -> startProgress(scanner, tracker);
                    case 3 -> markAsFixed(scanner, tracker);
                    case 4 -> closeBug(scanner, tracker);
                    case 5 -> displayAllBugs(tracker);
                    case 6 -> deleteBug(scanner, tracker);
                    case 7 -> displayBugsByStatus(scanner, tracker);
                    case 0 -> {
                        System.out.println("Exiting Bug Tracker. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input
            }
            
            System.out.println();
        }
        
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=== BUG TRACKER MENU ===");
        System.out.println("1 - Add a new bug");
        System.out.println("2 - Start progress on a bug");
        System.out.println("3 - Mark a bug as fixed");
        System.out.println("4 - Close a bug");
        System.out.println("5 - Display all bugs");
        System.out.println("6 - Delete a bug");
        System.out.println("7 - Display bugs by status");
        System.out.println("0 - Exit");
        System.out.print("Enter your choice: ");
    }

    private static int addNewBug(Scanner scanner, BugTracker tracker,int id) {
        System.out.print("Enter bug title: ");
        String title = scanner.nextLine();

        System.out.println("Select priority:");
        System.out.println("1 - LOW");
        System.out.println("2 - MEDIUM");
        System.out.println("3 - HIGH");
        System.out.println("4 - CRITICAL");
        System.out.print("Enter choice: ");
        
        int priorityChoice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        Priority priority = switch (priorityChoice) {
            case 1 -> Priority.LOW;
            case 2 -> Priority.MEDIUM;
            case 3 -> Priority.HIGH;
            case 4 -> Priority.CRITICAL;
            default -> {
                System.out.println("Invalid priority. Setting to LOW.");
                yield Priority.LOW;
            }
        };
        id = id + 1;
        Bug newBug = new Bug(title, priority, id);
        tracker.addBug(newBug);
        System.out.println("Bug added successfully!");
        return id + 1;
    }

    private static void startProgress(Scanner scanner, BugTracker tracker) {
        System.out.println("\n=== ALL BUGS ===");
        tracker.printAllBugs();

        System.out.print("Enter bug id to start progress: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        try {
            tracker.changeStateOfBug(id, State.IN_PROGRESS);
            System.out.println("Bug progress started!");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void markAsFixed(Scanner scanner, BugTracker tracker) {
        System.out.println("\n=== ALL BUGS ===");
        tracker.printAllBugs();

        System.out.print("Enter bug id to mark as fixed: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        try {
            tracker.changeStateOfBug(id, State.FIXED);
            System.out.println("Bug marked as fixed!");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void closeBug(Scanner scanner, BugTracker tracker) {
        System.out.println("\n=== ALL BUGS ===");
        tracker.printAllBugs();

        System.out.print("Enter bug id to close: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        try {
            tracker.changeStateOfBug(id, State.CLOSED);
            System.out.println("Bug closed!");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayAllBugs(BugTracker tracker) {
        System.out.println("\n=== ALL BUGS ===");
        tracker.printAllBugs();
    }

    private static void deleteBug(Scanner scanner, BugTracker tracker) {
        System.out.println("\n=== ALL BUGS ===");
        tracker.printAllBugs();

        System.out.print("Enter bug id to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        try {
            tracker.removeBug(index);
            System.out.println("Bug deleted successfully!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Invalid index!");
        }
    }

    private static void displayBugsByStatus(Scanner scanner, BugTracker tracker) {
        System.out.println("\nSelect status:");
        System.out.println("1 - OPEN");
        System.out.println("2 - IN_PROGRESS");
        System.out.println("3 - FIXED");
        System.out.println("4 - CLOSED");
        System.out.print("Enter choice: ");
        
        int statusChoice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        State state = switch (statusChoice) {
            case 1 -> State.OPEN;
            case 2 -> State.IN_PROGRESS;
            case 3 -> State.FIXED;
            case 4 -> State.CLOSED;
            default -> {
                System.out.println("Invalid status.");
                yield null;
            }
        };

        if (state != null) {
            System.out.println("\n=== BUGS WITH STATUS: " + state + " ===");
            tracker.printBugsInState(state);
        }
    }

    private static int addTestData(BugTracker tracker, int id){

        tracker.addBug(new Bug("Test Bug 1", Priority.LOW, id));
        id++;
        tracker.addBug(new Bug("Test Bug 2", Priority.MEDIUM, id));
        id++;
        tracker.addBug(new Bug("Test Bug 3", Priority.HIGH, id));
        id++;
        tracker.addBug(new Bug("Test Bug 4", Priority.CRITICAL, id));
        id++;
        tracker.addBug(new Bug("Test Bug 5", Priority.CRITICAL, id));
        id++;
        tracker.addBug(new Bug("Test Bug 6", Priority.HIGH, id));
        id++;
        tracker.addBug(new Bug("Test Bug 7", Priority.CRITICAL, id));
        id++;
        tracker.addBug(new Bug("Test Bug 8", Priority.CRITICAL, id));
        id++;
        tracker.addBug(new Bug("Test Bug 9", Priority.LOW, id));
        id++;
        tracker.addBug(new Bug("Test Bug 10", Priority.CRITICAL, id));
        return id;
    }

}
