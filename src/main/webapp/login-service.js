export default class LoginService {
    isLoggedIn() {
        // Ik check of er een JWT token in sessionStorage staat
        return window.sessionStorage.getItem("myJWT") !== null;
    }

    async login(user, password) {
        // Inloggen met POST naar mijn authentication endpoint
        const response = await fetch("restservices/authentication", {
            method: "POST",
            body: JSON.stringify({
                username: user,
                password: password
            }),
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Inloggen mislukt");
        }

        const data = await response.json();

        // De backend geeft { "JWT": "..." } terug
        // Deze sla ik op zodat andere service files hem kunnen gebruiken
        window.sessionStorage.setItem("myJWT", data.JWT);
        window.sessionStorage.setItem("username", user);

        return data;
    }

    async getUser() {
        // Eerst check ik of er überhaupt een token is
        const jwt = window.sessionStorage.getItem("myJWT");

        if (jwt === null) {
            return null;
        }

        // Ik test server-side of mijn JWT nog geldig is.
        // /games is beveiligd, dus als deze werkt, is mijn token geldig.
        const response = await fetch("restservices/games", {
            headers: {
                "Authorization": "Bearer " + jwt
            }
        });

        if (!response.ok) {
            // Token is verlopen of niet geldig
            window.sessionStorage.removeItem("myJWT");
            window.sessionStorage.removeItem("username");
            return null;
        }

        // Als de token geldig is, geef ik de username terug die ik bij login heb opgeslagen
        return window.sessionStorage.getItem("username");
    }

    async logout() {
        // Uitloggen = token verwijderen uit sessionStorage
        window.sessionStorage.removeItem("myJWT");
        window.sessionStorage.removeItem("username");

        return Promise.resolve();
    }
}