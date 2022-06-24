import React, { useEffect, useState } from "react";

import Table from "./Table";
// @ts-ignore
import moment from 'moment';
import TypingThroughInput from "./TypingThroughInput";

interface PageProps {
    id: string
    title: string
    lyrics: string
    albumArt: string
}
let idxs = [] as number[];
var Filter = require('bad-words'),
    userFilter = new Filter(), userSubstrings = [''], wasProfane = false, lyricFilter = new Filter();
userFilter.removeWords('xxx', 'hell', 'yed');

const TypingPage = ({id, title, lyrics, albumArt}: PageProps) => {

    let censorToggle = document.getElementById("censor-toggle") as HTMLInputElement;

    censorToggle.onchange = function changeCensor() {
        let togInstr = document.getElementById("tell-esc") as HTMLElement;
        togInstr.hidden = false;
    }

    //let censorLabel = document.getElementById("censor-label") as HTMLElement;
    let toggleShorten = document.getElementById("shortToggle") as HTMLInputElement;
    let shortenLabel = document.getElementById("shorten-label") as HTMLElement;
    censorToggle.hidden = true;
    censorToggle.style.display = "none"
    //censorLabel.hidden = true;
    toggleShorten.hidden = true;
    toggleShorten.style.display = "none";
    shortenLabel.hidden = true;

    document.addEventListener('keydown', function(event){
        if(event.key === "Escape"){
            setRowToInsert(new Map())
            let lbButton = document.getElementById("lbButton") as HTMLButtonElement;
            lbButton.style.display = ""
        }
    });


    const [selectedTable, setSelectedTable] = useState<string>("")
    const [rowToInsert, setRowToInsert] = useState<Map<string, string>>(new Map())

    //replace last ' by ' in title with hyphen ("-")
    var n = title.lastIndexOf(" by ");
    title = title.slice(0, n) + title.slice(n).replace("by", "-");

    let shortenToggle = document.getElementById("shorten-toggle") as HTMLInputElement;
    let newlines = lyrics.split("\n");
    if (shortenToggle.checked && newlines.length > 30) {
        lyrics = lyrics.replace("Verse", "V");
        lyrics = lyrics.replace("[Chorus", "[C")
        lyrics = lyrics.replace("Pre-Chorus", "PreC")
        lyrics = lyrics.replace("Post-Chorus", "PostC")
        console.log(lyrics)
        lyrics = lyrics.replace(/(\[Verse)[\s\S]*?(\[)/g, "\["); //remove lyrics labeled under "[Verse _]"
        lyrics = lyrics.replace(/(\[Chorus)[\s\S]*?(\[)/g, "\["); //remove lyrics labeled under "[Chorus]"
        lyrics = lyrics.replace(/(\[Chorus)[\s\S]*?(\[)/g, "\["); //remove lyrics labeled under "[Chorus]"
        lyrics = lyrics.replace(/(\[Chorus)[\s\S]*?(\[)/g, "\["); //remove lyrics labeled under "[Chorus]"
        lyrics = lyrics.replace(/(\[Pre-Chorus)[\s\S]*?(\[)/g, "\["); //remove lyrics labeled under "[Pre-]"
        lyrics = lyrics.replace(/(\[Post-Chorus)[\s\S]*?(\[)/g, "\["); //remove lyrics labeled under "[Post-]"
        lyrics = lyrics.replace(/(\[Intro)[\s\S]*?(\[)/, "\["); //remove [Intro] lyrics"
        lyrics = lyrics.split("\[Outro")[0]; //remove lyrics after the labeled "[Outro]"
        lyrics = lyrics.split("\[Verse")[0]; //remove lyrics after the last label: "[Verse]"
        lyrics = lyrics.split("\[Chorus")[0]; //remove lyrics after the last label: "[Verse]"
        lyrics = lyrics.split("\[Post-Chorus")[0]; //remove lyrics after the last label: "[Verse]"
        console.log(lyrics);
        title += " (shortened)" //change title to shortened
        id = "S" + id;  //add S to beginning of song id so that the shortened leader board so that leaderboard matches shortened song
        console.log(id);
    }

    lyrics = lyrics.replace(/ *\[[^\]]*]/g, ''); //remove all strings in [square brackets] (ie, [intro], [verse 1], etc.)
    while(lyrics.charAt(0) === '\n') { //remove all leading \n line breaks at start of lyrics
        lyrics = lyrics.substring(1);
    }
    lyrics = lyrics.replace(/\n\s*\n/g, '\n') //remove double line breaks
    lyrics = lyrics.toLowerCase(); //set all lyrics to lowercase

    for (let i = 0; i < lyrics.length; i++) { //store location/index of line breaks of string in idxs array
        if (lyrics.charAt(i) === '\n') {
            idxs.push(i);
        }
    }
    lyrics = lyrics.replace(/\n/g, ' ') //replace the line breaks with a space
    lyrics = lyrics.normalize("NFD").replace(/[\u0300-\u036f]/g, "")
    lyrics = lyrics.replace(/ /g, ' ')
    lyrics = lyrics.replace(/’/g, '\'')
    lyrics = lyrics.replace(/е/g, 'e') //replace cyrillic e with regular e
    lyrics = lyrics.replace(/—/g, '-') //replace weird longer hyphen with standard hyphen
    lyrics = lyrics.replace(/[^0-9a-z!\s@#$%^&*()_+={}|:;'"<>,.?/~`-]/gi, '?') //replace any unrecognized characters not on english keyboards with ?


    if (censorToggle.checked) {
        lyrics = lyricFilter.clean(lyrics);
        console.log("cleaned lyrics");
    } else {
        console.log("lyrics left dirty")
    }

    // @ts-ignore
    return (
        <div>
            <h1><a className={"t1"} href='.' onClick={(event: React.MouseEvent<HTMLElement>) => {
                window.location.reload();
            }}>Typeically</a></h1>
            <div className="container mx-auto flex flex-col p-4">
                <h5 id={"escape-instruct"}>Esc to reset</h5>
                <div className="border-2 p-4 rounded-lg">
                    <h1 onMouseOver={showImage} onMouseLeave={hideImage} className="mb-2" id="titleOfSong" >{title}</h1>

                    <div id={"lyrics"} >
                        <TypingThroughInput
                            text={
                                lyrics
                            }
                            indices={
                                idxs
                            }
                        />
                    </div>
                    <div id={"image"} hidden > <img id={"albumArt"}src={albumArt} alt={""} ></img></div>
                    <div id={"caps-alert"} hidden> Warning: Caps Lock Detected </div>
                </div>
            </div >
            <div className="container2">
                <div id={"lbInstructions"}className="typed-out" hidden>Enter a user name and submit to leaderboard</div>
            </div>
            <div id={"lBoardInputs"} className={"lbInputs"}>
                <span><input id="lbInput" className="search2" placeholder="username" autoComplete={"off"} maxLength={12} hidden></input></span>
                <span><button id="lbButton"className={"button2"} onClick={submitUser} hidden>submit</button></span>
            </div>
            <div className="labelContainer">
                <div id={"invalidUserLabel"}className="typed-out2" hidden>profanity detected in username. try another</div>
            </div>

            <Table selectedTable={selectedTable} rowToInsert = {rowToInsert} setRowToInsert={setRowToInsert}/>
        </div>
    );

    /**
     * Handles leaderboard submission logic, and is called when the user clicks the lbButton element!
     */
    function submitUser() {
        let lbInput = document.getElementById("lbInput") as HTMLInputElement;
        let lbButton = document.getElementById("lbButton") as HTMLButtonElement;
        let invalidLabel = document.getElementById("invalidUserLabel") as HTMLElement;
        if (contiguousValid()) {
            lbButton.style.display = "none" //hide button only if the username is valid
            let tableId = id; //'id' is the song id associated with the song, obtained from the Genius API
            let wpm = lbInput.getAttribute("wpm");
            let accuracy = lbInput.getAttribute("acc");
            let duration = lbInput.getAttribute("duration");
            let username = lbInput.value;

            let newAddInfo = new Map(rowToInsert);

            if (username !== ""){
                newAddInfo.set("Username", username.replace(" ","-"))
            } else {
                newAddInfo.set("Username", "anonymous");
            }

            newAddInfo.set("Date", moment().format("MM-DD-YYYY"))

            if (typeof wpm === "string") {
                newAddInfo.set("WPM", wpm)
            }
            if (typeof accuracy === "string") {
                newAddInfo.set("Accuracy (%)", accuracy.replace("%",""))
            }

            if (typeof duration === "string") {
                newAddInfo.set("Duration (s)", duration.replace("s",""))
            }


            setRowToInsert(newAddInfo)
            setSelectedTable(tableId)
            console.log("username: " + username + ", wpm: " + wpm + ", accuracy: " + accuracy + ", duration: " + duration);

            //ADD RESULTS TO LEADERBOARD HERE!!


            //here, we update the color of the border to indicate the user was accepted and leaderboard was updated:
            lbInput.className = lbInput.className.replace(" invalid", "");
            lbInput.className = lbInput.className.replace(" valid", "");
            lbInput.className = lbInput.className + " valid";
            invalidLabel.hidden = true;

            //hide username input and submit button so that users cannot click submit again after successful submission:
            lbInput.hidden = true;
            lbButton.hidden = true;
        } else {
            lbInput.className = lbInput.className.replace(" invalid", "");
            lbInput.className = lbInput.className.replace(" valid", "");
            lbInput.className = lbInput.className + " invalid";
            invalidLabel.hidden = false;
        }
    }

    function delay(time: number) {
        return new Promise(resolve => setTimeout(resolve, time));
    }

    async function hideImage() {
        let img = document.getElementById("image") as HTMLDivElement;
        let spans = document.getElementsByTagName("span");
        img.style.animation = 'fadeOut .65s forwards';
        for (let i = 0; i < spans.length; i++) {
            spans[i].style.opacity = "1";
        }
        await delay(500);
        img.hidden = true;
    }

    async function showImage() {
        let img = document.getElementById("image") as HTMLDivElement;
        let spans = document.getElementsByTagName("span");
        let albumArt = document.getElementById("albumArt") as HTMLImageElement;
        img.hidden = false;
        img.style.animation = 'fadeIn .65s';

        for (let i = 0; i < spans.length; i++) {
            spans[i].style.opacity = "0";
        }
    }

    function contiguousValid() {
        userSubstrings = [];
        let lbInput = document.getElementById("lbInput") as HTMLInputElement;
        contiguousSubstrings(lbInput.value)
        for (let j = 0; j < userSubstrings.length; j++) {
            if (userFilter.isProfane(userSubstrings[j])) {
                wasProfane = true;
                break;
            }
            if (userFilter.isProfane(userSubstrings[j].replace("x", ""))) {
                wasProfane = true;
                break;
            }
            if (userFilter.isProfane(userSubstrings[j].replace("-", ""))) {
                wasProfane = true;
                break;
            }
            if (userFilter.isProfane(userSubstrings[j].replace("_", ""))) {
                wasProfane = true;
                break;
            }
        }
        if (wasProfane) {
            wasProfane = false;
            return false;
        } else {
            return true;
        }
    }

    function contiguousSubstrings(str: string) {
        for (let i = 0; i < str.length; i++) {
            for (let j = i; j < str.length; j++) {
                userSubstrings.push(str.slice(i, j + 1));
            }
        }
    }

}
export default TypingPage