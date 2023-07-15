# Typeically
Typeically is a web-based typing app that allows you to find your favorite songs and type along to the lyrics! I created & developed this app alongside four classmates for my Spring 2022 Software Engineering course.

## Table of Contents
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup & Usage](#setup-&-usage)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)
* [Acknowledgements](#acknowledgements)
* [Contact](#contact)
<!-- * [License](#license) -->

## Technologies Used
Java, JavaScript, React, SQL

## Features

This web application aims to provide entertainment in the form of a typing game that allows you to type along to user selected lyrics. There is currently no application that allows users to type along to lyrics of a song that they inputted, and so our project aims to fulfill the want for such an application. The following demos below showcase the app’s main features.

### Demo of Search Bar & Typing:

You can use the search bar to find a song by artist, song name, or lyric content, and hit 'enter' to generate song options. Once you've found the result for the song you wish to type to, click on it and you'll be directed to the typing page. Upon finishing the song, you will receive your typing stats, including words per minute, accuracy and typing duration. You will have the option to submit your score to that song's specific leaderboard! You've got to be quick—only the top 5 completion times are displayed on the leaderboard!

<p align="center">
    <img src="type-demo.gif" alt="A user searches for a song and types to it, and their typing statistics and place in the leaderboard appear at the end.">
</p>

### Demo of New Releases Feature:

You can also choose a song from the list of new releases. In addition, you can toggle the song length and censorship of explicit lyrics using the switches at the top-right of the screen. Return back to the song search page by clicking the Typeically header at the top left of the page.

<p align="center">
    <img src="type-demo2.gif" alt="A user toggles the shorten and censor options in the top-right corner and chooses a newly released song.">
</p>
     
## Setup & Usage

### To run the Typeically program:
    
1. Preferably use Chrome for your browser and download the [CORS UnBlock extension](https://docs.google.com/document/d/1kAGzs_0YeLkAXbZUFNlNNj2SrcmW8tcc3CuH0Uy6cQ8/edit#heading=h.iiwoysfq2rkn). Activate it by making sure the yellow light on the bug icon is visible.

2. In one terminal:
```
cd frontend    
cd type-client
npm start
```
3. In another terminal:
```    
cd backend
./run --gui
```
### To test the Typeically program:
    
Run the Junit and Selenium tests using:
```
cd backend
mvn test
```
*IMPORTANT:*
- Make sure you activate the CORS UnBlock extension in the browser when the selenium tabs open
- No need to download the extension for testing, it is preloaded.

## Project Status
Project is: Complete

## Room for Improvement
Include areas you believe need improvement / could be improved. Also add TODOs for future development.

Room for improvement:
- Improvement to be done 1
- Improvement to be done 2

To do:
- Feature to be added 1
- Feature to be added 2


## Acknowledgements
Give credit here.
- This project was inspired by...
- This project was based on [this tutorial](https://www.example.com).
- Many thanks to...

## Contact
Created by [@flynerdpl](https://www.flynerd.pl/) - feel free to contact me!
