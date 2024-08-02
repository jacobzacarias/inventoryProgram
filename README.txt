Desktop Schedule README

Desktop Schedule
made in Java
by Jacob Zacarias
Student Application Version: QAM2
Date: 15 May 2024

This application allows users to track login attempts, manage customers, and
appointment schedules by using a MySQL Server to retrieve the appropriate data.

IDE Version: IntelliJ IDEA 2023.3.6
Full JDK Version: 17.0.10+1-b1087.23 aarch64
JavaFX Version Compatible with JDK: JavaFX-SE-17.0.1

Instructions for Use:
This program is able to run locally by cloning it and opening the project in
IntelliJ IDEA. Once cloned, it will need to be set up to use the JavaFX library.
After that, try to build it. Once it is built, you will now be able to freely
run the program for yourself.

Description of Report Method:
The report method finds appointments by matching them with the contact ID.
It queries the database to retrieve all appointments associated with the specified
contact ID and returns the results to the correct tables in the fxml screen.

MySQL Connector Driver Version: mysql-connector-java-8.0.28
