#  Typeically README

 This is my CSCI0320 Software Engineering final project that I completed with 4 classmates. 
 
 I specifically worked on:
- the backend and frontend aspects of the leaderboard, including setting up APIs to ensure the leaderboard is updated
- testing the leaderboard database from the backend by writting the JUnit tests
- the API handler to send the list of newly-released songs from Spotify to the frontend
- the frontend “choose a newly-released song” feature which allows users to choose a recent song
- testing all aspects of the frontend with Selenium

Read the [Specifications](https://docs.google.com/document/d/1kAGzs_0YeLkAXbZUFNlNNj2SrcmW8tcc3CuH0Uy6cQ8/edit#heading=h.iiwoysfq2rkn)
 document to learn more about Typeically.
 
## Instructions:
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




