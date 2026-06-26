export default class SnakeService {
    async getSnake() {
        const response = await fetch("/restservices/snake");

        if (!response.ok) {
            throw new Error("Snake ophalen mislukt");
        }

        return await response.json();
    }

    async updateSnake(snake) {
        const response = await fetch("/restservices/snake", {
            method: "PATCH",
            body: JSON.stringify(snake),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + window.sessionStorage.getItem("myJWT")
            }
        });

        if (!response.ok) {
            throw new Error("Snake aanpassen mislukt");
        }

        return await response.json();
    }
}