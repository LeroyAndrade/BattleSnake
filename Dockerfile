# Hoe deze file te gebruiken?
#
# 1. Creëer een lege (echt leeg) repository in je eigen Github-account!
# 2. Voeg deze repository als extra 'remote' toe aan je project (menu Git > Manage Remotes)
# 3. Push (Git > Push) de repository naar de juiste remote (kies de nieuwe remote i.p.v. 'origin')
#
# 4. Kies een platform dat het deployen van Docker-containers ondersteund (bijv. Render.com). Let op: Render kent een
#    free-tier, maar de containers gaan dan na 15 min. inactiviteit in slaapstand - er zijn alternatieven te vinden...
# 5. Voor Render: Login (bijv. via Github) en creëer een nieuw 'project'. Kies daarna een nieuwe 'service' (type
#    'Web Services') en selecteer de repository van stap 1.
#
# 6. Wil je een nieuwe versie deployen, commit en push dan naar je repository van stap 1.
#    Let op; lokaal testen kan ook, zie README punt 2.
#
# Wil je de applicatie lokaal draaien, dan moet je Docker op je machine installeren (https://docs.docker.com/desktop/),
# Voer vervolgens in de directory van dit BattleSnake-project de onderstaande commando's uit. De eerste opdracht creëert
# een image, en de tweede start een container op basis van dit image:
#
#   docker build -t my-tomcat-app .
#   docker run -p 80:8080 my-tomcat-app

# Build stage
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Runtime stage
FROM tomcat:9.0.117-jdk25-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]