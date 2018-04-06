# Pickle
A calorie free daily food tracker for android created in week 9 of my software development course at [CodeClan](http://codeclan.com)

## Brief
### Goal
Practice OO and UI design with what we learned in Java / Android weeks

### MVP
You are required to write an Android app that allows a user to track the food they eat. Users should be able to enter what they eat and when (date/time) and for what meal.

### Project Extension
The app should show the user a record of what they have eaten over a given period e.g. In March I drank lots of Irn-Bru.

## My Approach
The brief for this project was fairly open, allowing me to make a number of design decisions whilst still fulfilling the requirements. In terms of functionality, the app allows users to add, edit and delete meals, rag rate those meals, add foods to each meal, delete meals, and view the percentage of red amber or green meals they have eaten by week and month.

The app uses one activity where the drawer menu and main ui space are defined. Fragments are swapped in and out of this space depending on user actions. Alert dialogues are used to edit meals and add foods. 

## Screenshots
### Landing page
The landing page for the app displays an expandable list view of Meals and the foods within those meals. Each meals is selectable.

![Landing page](https://dl.dropboxusercontent.com/s/ubmkenpt3fnmtcj/food_tracker_5.png?dl=0)


### Menu
The drawer menu appears when the hamburger icon in the top left of any page is selected.

![Menu](https://dl.dropboxusercontent.com/s/849wspgo6hjs9di/food_tracker_8.png?dl=0)


### Meal page
Each meal can be selected on the main list.

![Meal page](https://dl.dropboxusercontent.com/s/2qbm4111no1y2b4/food_tracker_6.png?dl=0)


### Add a food
Individula foods can be added to meals from that meal's detail page.

![Add a food](https://dl.dropboxusercontent.com/s/6mbtdpa40nljrsz/food_tracker_7.png?dl=0)
