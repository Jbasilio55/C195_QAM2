# Customer Management and Appointment Scheduling Application
Completed on 10/20/23 - Written in Java/JavaFX using an existing database.
## Purpose
This GUI desktop application showcases a customer management and appointment scheduling within a company's framework. It is a Java-based client that seamlessly interacts with a MySQL database following a well-defined schema, meticulously designed to meet the criteria of the Software II C195 task.
## How to Run
Upon launching the application for the first time, you will be greeted by a login screen prompting you for user credentials. You can use the following login details: <br><br>
- username: test <br>
- password: test <br><br>
Once successfully logged in, you'll be redirected to the Main Screen, which features two tables: one for managing customers and the other for appointments. <br> <br>
## Customer and Appointment Management
On the Main Screen, you'll find a range of options for viewing, adding, updating, and deleting (CRUD) both customers and appointments. Any changes, such as adding, updating, or deleting a customer or an appointment, will be reflected in the table view and the underlying database.<br><br>
## Viewing and Editing
To view a specific customer or appointment, select the item of interest, and you'll be presented with detailed information. You'll have the option to edit the selected item from the view menu or return to the Main Screen.<br><br>
Editing can be performed from both the Main Screen and the main menu. In the main menu, select the item and click the update button. In the edit menu, you'll find all the data for the selected item, allowing you to make adjustments. Once you save your changes, the updated data will be reflected in the table view and database.<br><br>
## Adding
When adding a new customer or appointment, click the "ADD" button, fill out the required information, and it will be seamlessly added to the database.<br><br>
## Deletion
To delete an appointment, simply click on the delete button. Deleting a customer, however, will prompt you to confirm the action since it will also remove associated appointments for the customer. <br><br>
## Reports
The Main Screen includes a "Reports" button, which, when clicked, redirects you to the Reports menu. This menu provides three informative tables: <br><br>
1. Appointment Type by Month: Filter and view appointments by type for specific months (Jan - Dec).
2. Appointments for Selected Contact: Get insights into all appointments for a chosen contact.
3. Customers per Division: Gain valuable information about all customers organized by division.
## Special Features
- Multilingual Support: The login menu is translated based on the user's computer settings, ensuring a user-friendly experience for users around the world. <br>
- Upcoming Appointment Notifications: Upon logging in, you'll receive notifications about upcoming appointments, helping you stay on top of your schedule.
## Lambdas
Lambda expressions have been utilized in the codebase to enhance code readability and efficiency: <br><br>
1. Lambda Expression #1: Located in ViewAppointmentController's initialize method (lines 151 - 163), this lambda expression streamlines filtering items and performing actions for three different lists (customers, contacts, users). <br>
2. Lambda Expression #2: Found in ReportsController's initialize method (lines 139 - 140), this custom lambda expression simplifies event handling for combo boxes. <br><br>
By utilizing lambda expressions, the application achieves a more concise and maintainable codebase.
## Contact Info
Author: Jorge Basilio <br>
Contact information: https://www.linkedin.com/in/jorge-basilio-180484198/ <br>
Application Version: Appointment_Scheduler_C195 v1 <br>
Date: 10/20/23
## Developement Enviorment
IntelliJ IDEA 2021.13 (Communtity Edition) <br>
Java Version "17.0.1" <br>
Java JDK 17.0.1 <br>
mysql 8.0.25 (MySQL Community Server - GPL)
javafx.runtime.version: 11.0.2+1
