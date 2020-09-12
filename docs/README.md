# User Guide
This is a greenfield Java project based on the generic project called 
[***Project Duke***](https://nus-cs2103-ay2021s1.github.io/website/se-book-adapted/projectDuke/index.html). 
<br>
The result of this project is a personal assistant chat-bot named *Cipher*
capable of keeping track of tasks given by the user.

* [Quick start](#quick-start)
* [Features](#features)
  * [Help](#help-help)
  * [Adding tasks](#adding-tasks)
  * [Marking tasks as done](#marking-tasks-as-done-done)
  * [Deleting tasks](#deleting-tasks-delete)
  * [Listing tasks](#listing-tasks-list)
  * [Finding tasks](#finding-tasks-find)
  * [Exiting the program](#exiting-the-program-bye)
  * [Saving the data](#saving-the-data)
* [Command summary](#command-summary)
  
## Quick start
1. Ensure you have `Java 11` or above installed in your Computer.
2. Download the latest `duke.jar` from [here](https://github.com/erinmayg/ip/releases).
3. Copy the file to the folder you want to use as the *home folder* for *Cipher*.
4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds.
<p align="center"><img src="https://github.com/erinmayg/ip/blob/master/docs/Ui-start.png" height="500px"></p>

## Features

### Help: `help`
Displays a list of commands

<hr>

### Adding tasks
*Cipher* provides three types of tasks: ToDo, Event, and Deadline.

For Events and Deadlines, *Cipher* accepts the following date time format:
* `YYY-MM-DD`T`HH:MM:SS`
* `YYYY-MM-DD`T`HH:MM`
* `YYYY-MM-DD`
* `HH:MM:SS`
* `HH:MM`

<hr>

#### Adding a ToDo: `todo`
Adds a ToDo task to the task list.

Format: `todo TASK_DESCRIPTION`

Example: `todo study for test`

Expected outcome:

`Got it. I've added this task:`<br>
`[T][✗] study for test`<br>
`You have 1 task on your list.`

<hr>

#### Adding an Event: `event`
Adds an Event to the task list.

Format: `event TASK_DESCRIPTION /at DATE_TIME_FORMAT`

Example: `event mom's bday /at 2020-11-27`

Expected outcome:

`Got it. I've added this task:`<br>
`[E][✗] mom's bday (27 Nov 2020)`<br>
`You have 2 tasks on your list.`

<hr>

#### Adding a Deadline: `deadline`
Adds a Deadline to the task list.

Format: `deadline TASK_DESCRIPTION /by DATE_TIME_FORMAT`

Example: `deadline cs2103 project /by 2020-09-18T23:59`

Expected outcome:

`Got it. I've added this task:`<br>
`[D][✗] cs2103 project (18 Sep 2020, 11:59 pm)`<br>
`You have 3 tasks on your list.`

<hr>

### Marking tasks as done: `done`
Marks tasks with the given index as done.

Format: `done INDEX...`

Example: `done 1 3`

Expected outcome:

`Nice! I've marked these tasks as done:`<br>
`[T][✓] study for test`<br>
`[D][✓] cs2103 project (18 Sep 2020, 11:59 pm)`

<hr>

### Deleting tasks: `delete`
Deletes tasks with the given index.

Format: `delete INDEX...`

Example: `delete 1 3`

Expected outcome:

`Noted. I've removed these tasks:`<br>
`[T][✓] study for test`

<hr>

### Listing tasks: `list`
List the tasks on the task list.

Format: `list [DATE_FORMAT]`

Example: `list`

Expected outcome:

`1. [E][✗] mom's bday (27 Nov 2020)`<br>
`2. [D][✓] cs2103 project (18 Sep 2020, 11:59 pm)`

Example: `list 2020-11-27`

Expected outcome:

`Here's your list on 27 Nov 2020:`<br>
`1. [E][✗] mom's bday (27 Nov 2020)`

<hr>

### Finding tasks: `find`
Find tasks with the givnen keyword(s).

Format: `find KEYWORD...`

Example: `find die hard`

Expected outcome:

`Here are the tasks with the given keyword:`<br>
`1. [T][✗] die`<br>
`2. [T][✗] die young`<br>
`3. [T][✗] cry hard`

<hr>

### Exiting the program: `bye`
Exits the program.

<hr>

### Saving the data
*Cipher*'s data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

## Command summary
Action | Format | Examples
------------ | ------------- | -------------
Add a **ToDo** | `todo TASK_DESCRIPTION` | `todo study`
Add an **Event** | `event TASK_DESCRIPTION /at DATE_TIME_FORMAT` | `event bday /at 2020-12-31`
Add a **Deadline** | `deadline TASK_DESCRIPTION /by DATE_TIME_FORMAT` | `deadline project /by 2020-09-18T23:59`
Mark as **Done** | `done INDEX...` | `done 1`, `done 1 2 3`
**Delete** | `delete INDEX...` | `delete 1`, `delete 1 2 3`
**List** | `list [DATE_FORMAT]` | `list`, `list 2020-12-31`
**Find** | `find KEYWORD...` | `find die`, `find die cry`
**Help** | `help`
**Exit** | `bye`
