package ui;

import model.Bug;
import model.Project;
import model.ProjectManager;

import java.util.List;
import java.util.Scanner;

// Bug tracking application
public class BugTracker {

    private Scanner scanner;
    private ProjectManager projectManager;
    private InputState inputState;
    private Project selectedProject;
    private Bug selectedBug;

    // EFFECTS: runs the Bug Tracker application
    public BugTracker() {
        runBugTracker();
    }

    // EFFECTS: runs the Bug Tracker application
    private void runBugTracker() {
        String input = null;
        boolean terminated = false;

        initialize();

        do {
            input = scanner.next();
            switch (input) {
                case "help":
                    help();
                    break;
                case "exit":
                    terminated = true;
                    break;
                default:
                    processInput(input);
                    break;
            }
        } while (!terminated);

        System.out.println("\nExiting");
    }

    // MODIFIES: this
    // EFFECTS: initializes the project manager
    private void initialize() {
        System.out.println("initializing...");
        scanner = new Scanner(System.in);
        projectManager = new ProjectManager();
        setInputState(InputState.MAIN_MENU);
    }

    // EFFECTS: prints the current list of available commands
    private void help() {
        switch (inputState) {
            case MAIN_MENU:
                System.out.println("\nYou are currently in the main menu.");
                printMainMenuCommands();
                break;
            case IN_PROJECT:
                System.out.println("\nYou are currently in a project view.");
                printProjectCommands();
                break;
            case IN_BUG:
                System.out.println("\nYou are currently in a bug view.");
                printBugCommands();
                break;
        }
        System.out.println("Type 'exit' to quit.");
    }

    // EFFECTS: prints a list of the current main menu commands
    private void printMainMenuCommands() {
        System.out.println("Type 'new' to create a new project.");
        if (projectManager.getSize() > 0) {
            System.out.println("Type 'select' to select a project.");
        }
    }

    // EFFECTS: prints a list of the current project commands
    private void printProjectCommands() {
        System.out.println("Type 'add' to add a new bug to the project.");
        if (selectedProject.getTotalNumberOfBugs() > 0) {
            System.out.println("Type 'view' to view a list of all the unresolved bugs in the project.");
            System.out.println("Type 'select' to select a bug.");
        }
        System.out.println("Type 'back' to go back to the main menu.");
    }

    // EFFECTS: prints a list of the current bug commands
    private void printBugCommands() {
        System.out.println("Type 'resolve' to mark the bug as resolved.");
        System.out.println("Type 'back' to go back to the project menu.");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void processInput(String input) {
        boolean isInputProcessed = false;
        switch (inputState) {
            case MAIN_MENU:
                isInputProcessed = processMainMenuCommands(input);
                break;
            case IN_PROJECT:
                isInputProcessed = processProjectCommands(input);
                break;
            case IN_BUG:
                isInputProcessed = processBugCommands(input);
                break;
        }
        if (!isInputProcessed) {
            System.out.println("Unrecognized input... "
                    + "type 'help' for the list of available commands");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes commands given in the main menu; returns true if input is recognized as a command
    private boolean processMainMenuCommands(String input) {
        switch (input) {
            case "new":
                newProject();
                return true;
            case "select":
                if (projectManager.getSize() > 0) {
                    selectProject();
                } else {
                    System.out.println("There are currently no projects to select.");
                }
                return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: processes commands given in the project view; returns true if input is recognized as a command
    private boolean processProjectCommands(String input) {
        switch (input) {
            case "add":
                addBug();
                return true;
            case "view":
                if (selectedProject.getTotalNumberOfBugs() > 0) {
                    viewBugs();
                } else {
                    System.out.println("There are currently no bugs in the project to view.");
                }
                return true;
            case "select":
                if (selectedProject.getTotalNumberOfBugs() > 0) {
                    selectBug();
                } else {
                    System.out.println("There are currently no bugs in the project to select.");
                }
                return true;
            case "back":
                back();
                return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: processes commands given in the bug view; returns true if input is recognized as a command
    private boolean processBugCommands(String input) {
        switch (input) {
            case "resolve":
                resolveBug();
                return true;
            case "back":
                back();
                return true;
        }
        return false;
    }

    // REQUIRES: inputState == InputState.MAIN_MENU
    // MODIFIES: this
    // EFFECTS: prompts the user to create a new project with a name and description
    private void newProject() {
        System.out.println("\nEnter a new name for the project: ");
        String projectName = scanner.next();
        while (projectName.isEmpty()) {
            System.out.println("The project name cannot be empty, please try again.");
            projectName = scanner.next();
        }

        System.out.println("Enter a description for the project: ");
        String projectDescription = scanner.next();
        projectDescription += scanner.nextLine();

        projectManager.createNewProject(projectName, projectDescription);
        System.out.println("\nA new project has been created titled: " + projectName);
        if (!projectDescription.isEmpty()) {
            System.out.println("With the description: " + projectDescription);
        }
    }

    // REQUIRES: inputState == InputState.MAIN_MENU && projectManager.getSize() > 0
    // MODIFIES: this
    // EFFECTS: prints the list of project names if any exist and prompts the user for selection
    private void selectProject() {
        System.out.println("\nHere is the current list of projects:");
        int size = projectManager.getSize();
        for (int i = 0; i < size; i++) {
            Project project = projectManager.getProjectByIndex(i);
            System.out.println(i + " : " + project.getName());
        }
        System.out.println("\nType the index of the project you would like to select: ");
        int index = scanner.nextInt();
        while (index >= size) {
            System.out.println("That is an invalid index, please enter an index from the current list.");
            index = scanner.nextInt();
        }
        selectedProject = projectManager.getProjectByIndex(index);
        if (selectedProject != null) {
            setInputState(InputState.IN_PROJECT);
        } else {
            System.out.println("\nNo project exists with that name... returning to main menu.");
            setInputState(InputState.MAIN_MENU);
        }
    }

    // REQUIRES: inputState == InputState.IN_PROJECT && selectedProject != null
    // MODIFIES: this
    // EFFECTS: prompts the creation of a new bug with a name and information to the selected project
    private void addBug() {
        System.out.println("\nEnter a title for the bug: ");
        String bugName = scanner.next();
        while (bugName.isEmpty()) {
            System.out.println("The bug name cannot be empty, please try again.");
            bugName = scanner.next();
        }

        System.out.println("Enter some info about the bug: ");
        String bugInfo = scanner.next();
        bugInfo += scanner.nextLine();

        selectedProject.addNewBug(bugName, bugInfo);
        System.out.println("\nA new bug has been added to the project titled: " + bugName);
        if (!bugInfo.isEmpty()) {
            System.out.println("Information: " + bugInfo);
        }
    }

    // REQUIRES: inputState == InputState.IN_PROJECT && selectedProject != null  &&
    //           selectedProject.getTotalNumberOfBugs() > 0
    // EFFECTS: prints a list of the names of all of the unresolved bugs of the selected project if any exist
    private void viewBugs() {
        List<Bug> bugs = selectedProject.getUnresolvedBugsList();
        if (bugs.isEmpty()) {
            System.out.println("\nThere are currently no unresolved bugs in this project.");
        }
        System.out.println("\nHere is the current list of unresolved bugs:");
        for (Bug bug : bugs) {
            System.out.println("Title: " + bug.getTitle() + " - Info: " + bug.getInfo());
        }
    }

    // REQUIRES: inputState == InputState.IN_PROJECT && selectedProject != null &&
    //           selectedProject.getTotalNumberOfBugs() > 0
    // MODIFIES: this
    // EFFECTS: prints the list of the names of all the bugs in the selected project if any exist and prompts
    //           the user for a selection
    private void selectBug() {
        System.out.println("\nHere is the current list of bugs:");
        int numberOfBugs = selectedProject.getTotalNumberOfBugs();
        for (int i = 0; i < numberOfBugs; i++) {
            Bug bug = selectedProject.getBugByIndex(i);
            System.out.println(i + " : " + bug.getTitle());
        }
        System.out.println("\nType the index of the bug you would like to select: ");
        int index = scanner.nextInt();
        while (index >= numberOfBugs) {
            System.out.println("That is an invalid index, please enter an index from the current list.");
            index = scanner.nextInt();
        }
        selectedBug = selectedProject.getBugByIndex(index);
        if (selectedBug != null) {
            setInputState(InputState.IN_BUG);
        } else {
            System.out.println("\nNo bug exists with that name... returning to project.");
            setInputState(InputState.IN_PROJECT);
        }
    }

    // REQUIRES: inputState == InputState.IN_BUG && selectedBug != null
    // MODIFIES: this
    // EFFECTS: marks the currently selected bug as resolved
    private void resolveBug() {
        selectedBug.setResolved();
        System.out.println("\n" + selectedBug.getTitle() + " has been set as resolved.");
    }

    // REQUIRES: inputState != InputState.MAIN_MENU
    // MODIFIES: this
    // EFFECTS: switches back to the previous input state
    private void back() {
        switch (inputState) {
            case IN_PROJECT:
                System.out.println("\nMoving back to main menu...");
                setInputState(InputState.MAIN_MENU);
                break;
            case IN_BUG:
                System.out.println("\nMoving back to project menu...");
                setInputState(InputState.IN_PROJECT);
                break;
        }
    }

    // REQUIRES: if inputState == InputState.IN_PROJECT then selectedProject != null
    //           else if inputState == InputState.IN_BUG then selectedBug != null
    // MODIFIES: this
    // EFFECTS: set the input state and print related information about the state
    private void setInputState(InputState inputState) {
        this.inputState = inputState;
        switch (inputState) {
            case MAIN_MENU:
                selectedProject = null;
                selectedBug = null;
                System.out.println("\nWelcome to the Bug Tracker main menu!");
                break;
            case IN_PROJECT:
                selectedBug = null;
                System.out.println("\nSelected project: " + selectedProject.getName());
                break;
            case IN_BUG:
                System.out.println("\nSelected bug: " + selectedBug.getTitle());
                break;
        }
        System.out.println("Type 'help' at any time for the list of available commands.");
    }

}
