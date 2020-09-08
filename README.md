# A-Team28

Course: CS400

Semester: Spring 2020

Project name: COVID-19 Data Visualizer

## Team Members

1. Ankur Garg, agarg34@wisc.edu
2. Eric Ertl, eertl2@wisc.edu
3. Justin Chan,jachan@wisc.edu
4. Sudeep Reddy, sreddy9@wisc.edu
5. Samyu Iyer, siyer8@wisc.edu

## Project Description:

This project displays statistics related to coronavirus to provide a better understanding of how the virus is affecting people across America. The default display will show the various hotspots in America with the highest amount of coronavirus cases. The features on the side-bars (see GUI sketch below) will let the user customize how they see the data.

The user can choose between various modes to display the data (map, table, graph), as well as select which portions of data are shown with the ability to filter by timeframe and location.

Data will be downloaded in CSV format from the 2019 Novel Coronavirus COVID-19 (2019-nCoV) Data Repository by Johns Hopkins CSSE [(link to data)](https://github.com/CSSEGISandData/COVID-19).

### Table Mode

In this mode, the number of COVID-19 deaths and recovered COVID-19 patients are displayed for all locations. Clicking on a column name ("City", "Province/State", "Region", etc.) rearranges the table such that the values in that column are sorted. Clicking once sorts from A-Z order, clicking once more sorts from Z-A order, and clicking a third time clears that column altogether. Additionally, users can filter by names of specific cities, states, or countries in the Filter fields in the left pane. Selecting "Set Filter" after typing in the desired filter will apply selected filters to visible data. 

### Map Mode

In this mode, the various aspects of numerical COVID-19 data are displayed as dots, with the size of the dots pertaining to the size of the number they represent. The dots are placed in the location which they reference. The various checkboxes and radio buttons in the left panel allow for further customization, and the "Choose Time" slider enables users to select the end point in time for which to display data. 

### Graph Mode

In this mode, the various aspects of numerical COVID-19 data are displayed in a line graph. The sliders in the left settings bar allow users to set the start date and end date for which to display data. The radio buttons enable users to filter by particular locations, as well as by which aspect of the numerical data they desire to plot.

### Settings Pane
The left settings pane adjusts according to what mode the user is on. To close/open this settings pane, click "Menu", and then "Toggle Settins Pane". 

### Exiting
To exit out of the program, select "Menu", and then "Exit". 

### File Loading and Saving

To load a file: The user's input indicates the folder name which contains all the CSV files. By default, files will be loaded from a folder labeled time_data, but the user's inputted name will change this. 

To save a file: The user will input a file name, to which the currently selected filters/customizations will be applied and stored as a CSV file.

## For grader:

Which team members were on same xteam together?

None

Other notes or comments to the grader:

For a description on how to use this program, click "Help" in the upper menu bar, and then "Tutorial". 

## Future Work:

One feature we'd like to implement in the future is a suggestion and auto-complete capability when users enter the name of a city/state/country. We'd like to have it such that as the user enters the first few letters, several potential names come up -- similar to auto-complete in a search engine, for example. 

Additionally, we'd like it to be able to extrapolate future predictions for COVID-19 cases. This would require a great deal of analytics and statistics, but the ability to see how the cases could trend would enrich the program. 
