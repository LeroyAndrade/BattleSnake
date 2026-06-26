// Deze javascript file is puur een 'voorbeeld-frontend' en -hoeft- niet aangepast te worden. In principe hoef je alleen
// de ...-service.js files in te vullen met de juiste promise-logica en dan zou de UI moeten werken.
// Uiteraard -mag- je deze files wel aanpassen, want heel fraai is deze UI nou ook weer niet:)

import GamesService from "./games-service.js?v11";

let gamesList = document.getElementById('games');
let gamesService = new GamesService();
let details = document.getElementById('details');

window.addEventListener('hashchange', () => {
    loadGame(window.location.hash)
});

function loadGame(hashId) {
    let id = hashId.substring(1);

    return gamesService.getReplay(id).then(gameDetails => {
        if (gameDetails) {
            let text = details.querySelector('pre');

            // Hier toon ik de opgeslagen game-data netjes bij Details
            let movesText = "";

            // Als er moves zijn, zet ik ze onder elkaar met een beurt nummer
            if (gameDetails.moves) {
                for (let i = 0; i < gameDetails.moves.length; i++) {
                    movesText += "Beurt " + (i + 1) + ": " + gameDetails.moves[i] + "\n";
                }
            }

            text.innerText =
                "Game id: " + gameDetails.gameId + "\n" +
                "Aantal beurten: " + gameDetails.turns + "\n" +
                "Finished: " + gameDetails.finished + "\n\n" +
                "Moves:\n" +
                movesText;

            details.classList.remove('hidden');
        } else {
            details.classList.add('hidden');
        }
    });
}

function refresh() {
    gamesList.innerHTML = '';
    return gamesService.getGameIds().then(ids => {
        for (let id of ids) {
            let li = document.createElement('li');
            li.innerHTML = `<a href='#${id}'>Game ${id}</a><span class="delete">&#x2716;</span>`;

            li.querySelector('.delete').addEventListener("click", () => {
                gamesService.removeReplay(id).then(() => {
                    return refresh();
                });
            })
            gamesList.appendChild(li);
        }
        // Als er games zijn en er is nog geen game geselecteerd,
        // toon ik automatisch de eerste game bij Details
        if (ids.length > 0 && !window.location.hash) {
            window.location.hash = ids[0];
            return loadGame("#" + ids[0]);
        }
    });
}

refresh().then(() => {
    if (window.location.hash) {
        loadGame(window.location.hash)
    }
});
