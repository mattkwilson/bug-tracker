# Bug Tracker

## Proposal

One of the major problems that every developer has to face when working on a project-at any stage of development-is
**bugs**. They are unavoidable, even the best programmers in the world end up with bugs in their code from time to time.
Not only are they unavoidable, but they can wreak havoc on your program and be very frustrating to fix.
 
From the moment you start working on a new project you should have a good solution for tracking your bugs. It is
essential, not just to minimize the defects in your program early on, but to maximize your productivity in the later 
stages. 
 
That is why I've created Bug Tracker, a simple bug tracking tool that allows the user to keep track of all the issues 
that are going on within their projects. Within Bug Tracker you can create profiles for all of your different projects. 
For each individual project there are tools to **view all the current and past bugs**, including the ability to **add**, 
**modify** and **delete** issues as they are handled. With options for **sorting bugs by their severity** and 
**assigning tasks to team members**. All of this within a visually appealing and easy to use desktop application. 

**Who is this project for?**
- Software Developers
- Quality Assurance Teams

*However this project is not just limited to software developers and QA teams, in almost any kind of project that you
can create you may run into problems and issues that you have to deal with, and for that this tool can be invaluable.*

## User Stories

- As a user, I want to be able to create a new project and add it to a list of projects
- As a user, I want to be able to select a project and add a new bug to the project
- As a user, I want to be able to select a bug in a project and mark it as resolved
- As a user, I want to be able to select a project and view a list of the unresolved bugs in that project
- As a user, I want my projects to be automatically saved to file when I exit the application
- As a user, I want to be able to select and load a project that has been previously saved to file

## Phase 4: Task 2

>Test and design a class in your model package that is robust.

**Class:** _ProjectManager_

**Methods:** _getProjectByIndex_, _createNewProject_, _ProjectManager_

## Phase 4: Task 3

**If I had more time...**
- I would try to reduce coupling between the classes in the graphics package that make up the GUI
- I would consider implementing the observer pattern so that the graphics could observe changes to the projects and 
update the UI accordingly
