# Open-Source-Coding-ByteBalance-Budget-App-
Part 2

Demonstration Video link: https://youtu.be/uDEh0RZuFEo?si=EeTLlxDftAKtVjdN 

User Authentication
Start by registering your details into our database
Secure login with username and password

User session management

Expense Management
Create expense entries with:

Date and time (start/end)

Description

Category assignment

Optional photo attachment

View all expenses within a selectable time period

Access attached photos from expense list

Category System
Create custom spending categories

Assign expenses to categories

View spending totals by category for any time period

Budget Goals
Set monthly minimum spending goals

Set monthly maximum spending limits

Track progress against goals

Data Persistence
All data stored locally using Room Database

Offline functionality

Data persists between sessions

Technical Implementation
Architecture
MVVM (Model-View-ViewModel) pattern

Repository pattern for data access

Single Activity with multiple Fragments

Technologies
Kotlin - Primary programming language

Android Jetpack Components:

Room Database - Local data persistence

ViewModel - UI-related data holder

LiveData - Observable data holder

Navigation Component - Fragment management

CameraX - For capturing expense photos

Glide - Image loading library
