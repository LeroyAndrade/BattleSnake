export default class GamesService {
    async getGameIds() {
        // fetch alle games van de service, maar geef alleen de ids terug
        const response = await fetch("../restservices/games");

        if (!response.ok) {
            throw new Error("Games ophalen mislukt");
        }

        const games = await response.json();
        // backend geeft volledige game objecten terug roep: gameID op
        return games.map(game => game.gameId);
    }

    async getReplay(gameId) {
        // fetch  details van een game
        const response = await fetch("../restservices/games/" + encodeURIComponent(gameId));

        if (!response.ok) {
            throw new Error("Game details ophalen mislukt");
        }

        return await response.json();
    }

    async removeReplay(gameId) {
        //fetch om  game bij de server te deleten
        const response = await fetch("../restservices/games/" + encodeURIComponent(gameId), {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("Game verwijderen mislukt");
        }

        return await response.json();
    }
}