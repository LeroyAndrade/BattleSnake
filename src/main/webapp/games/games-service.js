export default class GamesService {
    async getGameIds() {
        const response = await fetch("/restservices/games", {
            headers: {
                "Authorization": "Bearer " + window.sessionStorage.getItem("myJWT")
            }
        });

        if (!response.ok) {
            throw new Error("Games ophalen mislukt");
        }

        const games = await response.json();

        return games.map(game => game.gameId);
    }

    async getReplay(gameId) {
        const response = await fetch("/restservices/games/" + encodeURIComponent(gameId), {
            headers: {
                "Authorization": "Bearer " + window.sessionStorage.getItem("myJWT")
            }
        });

        if (!response.ok) {
            throw new Error("Game details ophalen mislukt");
        }

        return await response.json();
    }

    async removeReplay(gameId) {
        const response = await fetch("/restservices/games/" + encodeURIComponent(gameId), {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + window.sessionStorage.getItem("myJWT")
            }
        });

        if (!response.ok) {
            throw new Error("Game verwijderen mislukt");
        }

        return await response.json();
    }
}