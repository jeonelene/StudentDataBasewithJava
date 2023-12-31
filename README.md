# StudentDataBase Java Program

## Overview

This Java program, named StudentDataBase, is a console-based application for managing student data in a MariaDB database. It allows users to perform various operations such as viewing all students, retrieving students by surname, semester, updating information, and deleting entries.

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- MariaDB
- JDBC Driver for MariaDB

### Setup

1. Ensure that you have the Java Development Kit installed on your system.
2. Set up a MariaDB database and update the connection details in the `main` method of the `Gewrgoulas1` class.
3. Make sure to have the JDBC Driver for MariaDB included in your project.

## Usage

1. Run the program.
2. Follow the on-screen menu to choose different operations.
3. Input required information when prompted.
4. The program will interact with the MariaDB database to perform the selected operation.

## Menu Options

1. **Print All Items**: Display all student records in the database.
2. **Print Items By Surname**: Retrieve and display student records based on the surname.
3. **Print Items By Semester**: Retrieve and display student records based on the semester.
4. **Update Item By Surname**: Update the number of passed courses for a student based on their surname.
5. **Delete Item By Surname**: Delete a student record based on the surname.
6. **Exit**: Terminate the program.

## Notes

- The program handles SQL exceptions and provides error messages when necessary.
- Always input valid data as per the program prompts.
