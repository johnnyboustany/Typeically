import React, {useEffect, useState} from "react"
var geniusLyricsAPI = require("genius-lyrics-api")

// @ts-ignore
var indices: any[] = []

const options = {
    title:  ' ',
    artist: ' ',
    apiKey: '3pTLkXn4VZpZXk3qJ61nkioYxvEaqzadxVGK1FNEN8-katQfRqngvvt1XzA06CaT', // Genius developer access token
    optimizeQuery: true // Setting this to true will optimize the query for best results
}
// @ts-ignore
const Searcher = ({onLoad: handleSearch, onClick, topSongs, topArtists, handleChooseTopSong: handleChooseTopSong}) => {
    //prevent pressing space from scrolling the page (for longer songs)
    document.documentElement.addEventListener('keydown', function (e) {
        // @ts-ignore
        if ( ( e.keyCode || e.which ) == 32
            && document.activeElement !== document.getElementById("search")
            && document.activeElement !== document.getElementById("lbInput")) {
            e.preventDefault();
        }
    }, false);

    let about = document.getElementById("about") as HTMLDivElement;
    let aboutPopup = document.getElementById("about-popup") as HTMLDivElement;


    function showAbout() {
        aboutPopup.hidden = false;
    }
    function hideAbout() {
        aboutPopup.hidden = true;
    }

    const [newTopSongs, setNewTopSongs] = useState<string[]>([])

    async function searchSongs() {
        // @ts-ignore
        indices = await geniusLyricsAPI.searchSong(options)
        console.log(indices)
    }

    const newSongs = async () => {
        let newSongs = []
        let newArtists = []
        console.log(topSongs)

        for (let i = 0; i < topSongs.length; i++) {
            options.title = topSongs[i];
            options.artist = topArtists[i]
            await searchSongs()
            let songTitle = indices[0].title
            let songTitleArr = songTitle.split(" ")
            let apiSongTitleArr = topSongs[i].split(" ")
            if (songTitleArr[0] === apiSongTitleArr[0]) {
                newSongs.push(topSongs[i])
                newArtists.push(topArtists[i])
            }
        }
        console.log(newSongs)

        console.log("hi")
        return newSongs
    }

    useEffect(() => {newSongs().then(r => setNewTopSongs(r))}, [topSongs]);

    return (
        <div id="load-page" >
            <h1 className="t1">Typeically</h1>

            <h3  className="t2">Search for your own song:</h3>

            <div className={"barButton"}>
                <input id="search" className="search" placeholder="Search..." autoComplete={"off"} ></input>
                <button id="searchButton" className="button" onClick={handleSearch}>Enter</button>
            </div>

            <div id ="popup">
                <button className="res" id ="0"onClick={onClick}></button>
                <button className="res" id ="1"></button>
                <button className="res" id ="2"></button>
                <button className="res" id ="3"></button>
                <button className="res" id ="4"></button>
                <button className="res" id ="5"></button>
                <button className="res" id ="6"></button>
                <button className="res" id ="7"></button>
                <button className="res" id ="8"></button>
                <button className="res" id ="9"></button>
            </div>
            <br/>

            <h2  className="t2">Or choose a newly released song:</h2>

            <div id ="popup1">
                {newTopSongs.map((title: string) =>  <button className="top" id="newSongs" onClick = {(event) => handleChooseTopSong(title)}>{(newTopSongs.indexOf(title) +1) + ". " + title + " by " + topArtists[topSongs.indexOf(title)]}</button>)}
            </div>
            <div id={"about"} onMouseOver={showAbout} onMouseLeave={hideAbout} >
                <img id={"about-img"}src={require('./about-us-ICON.png')}></img>
            </div>

            <div id="page">
            </div>
        </div>
    )
}

export default Searcher