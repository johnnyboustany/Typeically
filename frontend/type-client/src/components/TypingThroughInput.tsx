import React, { FC, useEffect, useMemo, useRef, useState } from "react";
import useTyping from "react-typing-game-hook";


interface IState {
    count: number;
}

function delay(time: number) {
    return new Promise(resolve => setTimeout(resolve, time));
}

async function alertCaps(letter : string) {
    let capsAlert = document.getElementById("caps-alert") as HTMLDivElement;
    if (capsAlert.hidden === true && letter === "CapsLock") {
        capsAlert.style.animation = 'fadeIn .65s';
        capsAlert.hidden = false;
    } else if (capsAlert.hidden === false && letter === "CapsLock") {
        capsAlert.style.animation = 'fadeOut .65s';
        await delay(500)
        capsAlert.hidden = true;
    } else{
        capsAlert.hidden = false;
        capsAlert.style.animation = 'fadeIn .65s';
    }
}

const TypeThroughInput: FC<{ text: string, indices: number[]}> = ({ text, indices}) => {
    document.addEventListener('keydown', function(event){
        if(event.key === "Escape"){
            let lbInput = document.getElementById("lbInput") as HTMLInputElement;
            let lbButton = document.getElementById("lbButton") as HTMLButtonElement;
            let lbInstructions = document.getElementById("lbInstructions") as HTMLDivElement;
            lbInstructions.hidden=true;
            lbInput.hidden=true; //hide username input box
            lbButton.hidden=true; //hide username leaderboard submit button
            let spans = document.getElementsByTagName('span');
            resetTyping();
            for (let i = 0; i < spans.length; i++)  {
                spans[i].hidden = false;
            }
            let togInstr = document.getElementById("tell-esc") as HTMLElement;
            togInstr.hidden = true;
            setCounter(0); //resets counter to 0
        }
    });
    const [duration, setDuration] = useState(0);
    const [isFocused, setIsFocused] = useState(false);
    const letterElements = useRef<HTMLDivElement>(null);
    let cnt = -1;
    let letterPrev = "";
    let keypresses = 0;
    const [count, setCounter] = useState(0);
    const state: IState = { count: 0 };

    const increment = (): any => {
        console.log(state);
        setCounter(count+1);


    };

    const decrement = (): any => {
        console.log(state);
        setCounter(count -1);

    };

    const {
        states: {
            charsState,
            currIndex,
            phase,
            correctChar,
            errorChar,
            startTime,
            endTime
        },
        actions: { insertTyping, deleteTyping, resetTyping }
    } = useTyping(text, { skipCurrentWordOnSpace: false, pauseOnError: true, countErrors: 'everytime' });

    // set cursor
    const pos = useMemo(() => {
        if (currIndex !== -1 && letterElements.current) {
            const spanref: any = letterElements.current.children[currIndex];
            let left = spanref.offsetLeft + spanref.offsetWidth - 2;
            let top = spanref.offsetTop - 2;
            return { left, top };
        } else {
            return {
                left: -2,
                top: 2
            };
        }
    }, [currIndex]);



    //set WPM
    useEffect(() => {
        if (phase === 2 && endTime && startTime) {
            setDuration(Math.floor((endTime - startTime) / 1000));
        } else {
            setDuration(0);
        }
    }, [phase, startTime, endTime]);

    //handle key presses
    const handleKeyDown = (letter: string, control: boolean) => {
        let detIdx = 0;
        let spans = document.getElementsByTagName('span');
        console.log(count);
        if (letter === "CapsLock") {
            alertCaps(letter);
        } else if (letter === "Backspace") {
            deleteTyping(control);
            if (count > 0) { //don't decrement count lower than 0
                decrement()
            }
            for (let j = 0; j < indices.length; j++) {
                if (count < indices[j] + 1) {
                    detIdx = j
                    break;
                }
            }
            if (count < indices[0] + 1) {
                for (let i = 0; i < indices[0]+1; i++) {
                    spans[i].hidden = false;
                }
            } else if (count < indices[detIdx] + 1) {
                for (let i = indices[detIdx - 1] + 1; i < indices[detIdx] + 1; i++) {
                    spans[i].hidden = false;
                }
            }
        } else if (letter.length === 1) {
            if (!(letterPrev === "Shift" || letter.toLowerCase() === letter)) {
                alertCaps(letter);
            }
            else {
                let capsAlert = document.getElementById("caps-alert") as HTMLDivElement;
                capsAlert.hidden = true;
                insertTyping(letter);
                keypresses += 1;
                let afterErr = correctChar;
                if (count < afterErr) {
                    increment()
                    keypresses = 0
                }
                if (indices.includes(count + 1)) { //count === indices[curr] - 1
                    for (let i = 0; i < count + 2; i++) { //i < indices[curr] + 1
                        spans[i].hidden = true;
                    }
                }
                if (count === text.length - 1) {
                    for (let i = 0; i < count + 2; i++) { //i < indices[curr] + 1
                        spans[i].hidden = true;
                    }
                }
            }
        }
        letterPrev = letter;
        console.log(letterPrev);
    };

    return (
        <div>
            <div
                tabIndex={0}
                onKeyDown={(e) => handleKeyDown(e.key, e.ctrlKey)}
                onFocus={() => setIsFocused(true)}
                onBlur={() => setIsFocused(false)}
                className={`text-xl outline-none relative font-serif`}
            >
                <div
                    ref={letterElements}
                    className="tracking-wide pointer-events-none select-none mb-4"
                    tabIndex={0}
                >

                    {text.split("").map((letter, index) => {
                        cnt += 1;
                        let state = charsState[index];
                        let color =
                            state === 0
                                ? "text-gray-500"
                                : state === 1
                                ? "text-green-600"
                                : "text-red-500";


                        if (indices.includes(cnt)) {
                            return (
                                <span id={index.toString()} key={letter + index} className={`${color}`}><p></p></span>

                            )
                        }
                        else {
                            return (
                                <span id={index.toString()} key={letter + index} className={`${color}`}>{letter}</span>
                            )
                        }
                    })}

                </div>
                {phase !== 2 && isFocused ? (
                    <span
                        style={{
                            left: pos.left,
                            top: pos.top
                        }}
                        className={`caret border-l-2 border-white`}
                    >
            &nbsp;
          </span>
                ) : null}
            </div>
            <p className="text-sm">
                {phase === 2 && startTime && endTime ? doOnEnded() : null}
                <span className="mr-4" hidden> Current Index: {currIndex}</span>
                <span className="mr-4" hidden> Correct Characters: {correctChar}</span>
                <span className="mr-4" hidden> Error Characters: {errorChar}</span>
            </p>
        </div>
    );

    function doOnEnded() {
        let spans = document.getElementsByTagName('span');
        for (let i = 0; i < text.length; i++) {
            if (spans[i]) {
                spans[i].hidden = true;
            }
        }
        let lbInput = document.getElementById("lbInput") as HTMLInputElement;
        let lbButton = document.getElementById("lbButton") as HTMLButtonElement;
        let lbInstructions = document.getElementById("lbInstructions") as HTMLDivElement;
        if (lbInput) {
            lbInput.hidden = false;
            //set stats as attributes of lbInput so that we can get the info in TypingPage for the leaderboard:
            lbInput.setAttribute("wpm", Math.round(((60 / duration) * correctChar) / 5).toString())
            lbInput.setAttribute("acc", (((correctChar - (errorChar)/4) / text.length) * 100).toFixed(2) + "%")
            lbInput.setAttribute("duration", duration.toString())
        }
        if (lbButton) {
            lbButton.hidden = false;
        }
        if (lbInstructions) {
            lbInstructions.hidden = false;
        }
        return(
            <>
            <span id={"wpmLabel"} className="text-green-500 mr-4">
              WPM: {Math.round(((60 / duration) * correctChar) / 5)}
            </span>
                <span id={"accuracyLabel"} className="text-blue-500 mr-4">
              Accuracy: {(((correctChar - (errorChar)/4) / text.length) * 100).toFixed(2)}%
            </span>
                <span id={"durationLabel"} className="text-yellow-500 mr-4">Duration: {duration}s</span>

            </>
        )
    }


};

export default TypeThroughInput;