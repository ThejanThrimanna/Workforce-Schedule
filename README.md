# Workforce-Schedule
This is a simple Android native application built using Kotlin and Jetpack Compose.
The application allows users to view available workforce shifts, assign employees to shifts, and manage employee information.

## 🚀 Features
* View all shifts
* Filter shifts by date or date range
* View employee list
* View detailed shift information
* Assign employees to shifts based on availability and required skills

## 🧠 Architecture

This project follows Clean Architecture and MVVM, ensuring a clear separation of concerns

## 🛠️ Tech Stack

Kotlin | Jetpack Compose | Coroutines & Flow | MVVM | Hilt (DI) | Room |JUnit + MockK (Testing)
Clean Architecture

## 🔹Assumptions & Notes

The application currently works with internally stored JSON data for both shifts and employees. On first launch, this data is loaded into a local database, and all subsequent operations are performed based on the locally stored data.

* Application Structure

Based on the requirements, the UI is organized into two main tabs:

* Shifts

Displays the shift schedule
Allows filtering shifts by date or date range

* Employees

Displays the list of all employees

* Shift Management

The Shift Details screen shows complete information about a selected shift.

From this screen, employees can be assigned to the shift if they are not already assigned.

* Assignment Logic

Ideally, assigning employees to shifts should be handled online (server-side) to avoid:

Duplicate assignments

Shift time conflicts

For now, the application uses locally implemented logic to assign employees to shifts based on:

Required skills for the shift

Employee availability during the shift time

Existing shift conflicts
