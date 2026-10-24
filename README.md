# Number Guessing Game

Das Number Guessing Game ist eine kleine Webanwendung, die ich mit Java und Spring Boot umgesetzt habe.

Ich habe das Projekt hauptsächlich entwickelt, um erste praktische Erfahrungen mit **Spring Boot und der Webentwicklung mit Java** zu sammeln. Bei meinen bisherigen Projekten habe ich überwiegend Konsolenanwendungen geschrieben. Diesmal wollte ich lernen, wie ein Java-Backend mit einer Website zusammenarbeitet und wie Benutzereingaben über den Browser verarbeitet werden.

Außerdem wollte ich meine bisherigen Kenntnisse in **Java, SQL, JDBC und SQLite** weiter anwenden. Der Schwerpunkt des Projekts liegt deshalb auf dem Backend. Für die Benutzeroberfläche nutze ich HTML, CSS und Thymeleaf; bei der Erstellung des Frontends habe ich KI-Unterstützung verwendet.

## Was kann das Programm?

Der Spieler versucht, eine zufällig generierte Zahl zwischen **1 und 100** zu erraten.

- Eine neue Spielrunde starten
- Eine Zahl zwischen 1 und 100 eingeben
- Erfahren, ob der Tipp zu hoch, zu niedrig oder richtig ist
- Die Anzahl der bisherigen Versuche anzeigen
- Nach einem Gewinn einen Spielernamen eingeben und das Ergebnis speichern
- Gespeicherte Highscores in einer Bestenliste anzeigen
- Jederzeit ein neues Spiel starten

Die Highscores werden in einer lokalen **SQLite-Datenbank** gespeichert und bleiben dadurch auch nach dem Neustart der Anwendung erhalten.

## Wie funktioniert das Spiel?

Beim Öffnen der Website wird über den `GameController` ein neues Spiel erstellt, sofern noch keines in der aktuellen Session vorhanden ist.

Der `GameService` generiert eine Zufallszahl zwischen 1 und 100 und speichert die Anzahl der Versuche sowie den Gewinnstatus.

Wenn der Spieler eine Zahl eingibt, schickt das HTML-Formular eine **POST-Anfrage** an den Controller. Dieser übergibt die Zahl an `GameService.checkGuess()`. Die Methode vergleicht den Tipp mit der geheimen Zahl und gibt ein Ergebnis zurück:

- `TOO_HIGH` – die Zahl ist zu hoch
- `TOO_LOW` – die Zahl ist zu niedrig
- `CORRECT` – die Zahl wurde erraten
- `INVALID` – die Zahl liegt außerhalb des erlaubten Bereichs
- `ALREADY_WON` – das Spiel wurde bereits gewonnen

Anschließend übergibt der Controller das Ergebnis und die aktuelle Versuchszahl an die Website, wo sie mit **Thymeleaf** angezeigt werden.

Nach einem Gewinn kann das Ergebnis zusammen mit einem Spielernamen in SQLite gespeichert werden.

## Aufbau des Projekts

### NumberGuessingGameApplication.java

Startet die Spring-Boot-Anwendung. Beim Programmstart wird außerdem die benötigte Datenbanktabelle erstellt, falls sie noch nicht existiert.

### GameController.java

Der Controller verbindet die Website mit der Java-Spiellogik und der Datenbank.

Dabei habe ich folgende Endpunkte verwendet:

| Methode | URL | Aufgabe |
| --- | --- | --- |
| `GET` | `/` | Startseite laden und Spiel bereitstellen |
| `POST` | `/guess` | Eine eingegebene Zahl überprüfen |
| `POST` | `/restart` | Ein neues Spiel starten |
| `POST` | `/score` | Einen gewonnenen Spielstand speichern |

Im Controller verwende ich unter anderem:

- `@Controller`, um die Klasse als Spring-MVC-Controller zu kennzeichnen
- `@GetMapping` und `@PostMapping`, um HTTP-Anfragen zu verarbeiten
- `@RequestParam`, um Werte aus HTML-Formularen entgegenzunehmen
- `HttpSession`, um das aktuelle `GameService`-Objekt zwischen mehreren Anfragen zu speichern
- `Model`, um Java-Werte an das HTML-Template weiterzugeben
- `redirect:/`, um den Browser nach bestimmten Aktionen zur Startseite zurückzuleiten

### GameService.java

Hier befindet sich die eigentliche Spiellogik. Die Klasse verwaltet die geheime Zahl, die Versuche und den Spielstatus.

Wichtige Methoden:

- `startNewGame()` – generiert eine neue Zufallszahl und setzt den Spielstand zurück
- `checkGuess(int guess)` – vergleicht den Tipp mit der geheimen Zahl
- `getTries()` – gibt die Anzahl der Versuche zurück
- `isWon()` – gibt zurück, ob das Spiel gewonnen wurde

Für die Zufallszahl nutze ich `Random`.

### Database.java

Diese Klasse ist für die Verbindung zur SQLite-Datenbank zuständig.

Sie enthält drei wichtige Methoden:

- `createTable()` – erstellt die Tabelle `scores`, falls sie noch nicht existiert
- `saveScore(String playerName, int attempts)` – speichert einen Highscore
- `getHighScores()` – liest die Highscores aus und sortiert sie nach der Anzahl der Versuche

Die Datenbankanbindung habe ich mit **JDBC** umgesetzt. Dabei verwende ich `DriverManager`, `Connection`, `Statement`, `PreparedStatement` und `ResultSet`.

Die Verbindungen und Statements werden mit **try-with-resources** automatisch geschlossen. SQL-Fehler werden mit `SQLException` abgefangen.

### Score.java

Die Klasse `Score` stellt einen einzelnen Eintrag in der Bestenliste als Java-Objekt dar.

Ein Score enthält die ID, den Spielernamen, die Anzahl der Versuche und den Zeitpunkt des Spiels.

### index.html

Die Website enthält das Eingabeformular, die Hinweise zum Spiel, den Versuchszähler und die Bestenliste.

Für die Darstellung verwende ich HTML, CSS und **Thymeleaf**. Thymeleaf ermöglicht es, die Werte aus dem Spring-`Model` direkt in der HTML-Seite anzuzeigen.

## Datenbank

Für das Projekt verwende ich SQLite. Die Daten werden lokal in der Datei `scores.db` gespeichert.

Es gibt eine Tabelle namens `scores` mit folgenden Spalten:

| Spalte | Beschreibung |
| --- | --- |
| `id` | Automatisch vergebene ID |
| `player_name` | Name des Spielers |
| `attempts` | Anzahl der benötigten Versuche |
| `played_at` | Zeitpunkt des gespeicherten Ergebnisses |

Zum Speichern verwende ich `INSERT INTO`. Die Bestenliste wird mit `SELECT` und `ORDER BY attempts ASC` ausgelesen, sodass Ergebnisse mit weniger Versuchen zuerst erscheinen.

## Verwendete Technologien

- Java
- Spring Boot / Spring MVC
- Thymeleaf
- HTML und CSS
- SQLite
- JDBC
- SQL
- Maven

## Was ich bei dem Projekt gelernt habe

Das Projekt war für mich vor allem ein Einstieg in die Webentwicklung mit Spring Boot.

Dabei habe ich mich unter anderem mit folgenden Themen beschäftigt:

- Aufbau einer Spring-Boot-Anwendung
- Kommunikation zwischen einer Website und einem Java-Backend
- HTTP-Anfragen mit GET und POST
- Controller und Mapping-Annotationen
- Übergabe von Formulareingaben mit `@RequestParam`
- Speicherung eines Spielstands mit `HttpSession`
- Übergabe von Daten an HTML mit `Model` und Thymeleaf
- Trennung von Controller, Spiellogik und Datenbankzugriffen
- Objektorientierte Programmierung
- JDBC und SQLite
- SQL-Abfragen mit `Statement` und `PreparedStatement`
- Verarbeitung von Datenbankergebnissen mit `ResultSet`
- Fehlerbehandlung und try-with-resources

Besonders interessant war für mich das Zusammenspiel von **`GameController`, `GameService` und `HttpSession`**. Ich habe dabei gelernt, warum bei einer Webanwendung die einzelnen Button-Klicks getrennte HTTP-Anfragen auslösen und wie der Spielstand trotzdem über mehrere Anfragen hinweg erhalten bleibt.

## Projekt starten

Voraussetzungen sind eine passende Java-Version für das Spring-Boot-Projekt und Maven beziehungsweise die Maven-Unterstützung der IDE.

1. Repository herunterladen oder klonen.
2. Das Projekt in **IntelliJ IDEA** oder einer anderen Java-IDE öffnen.
3. Maven-Abhängigkeiten laden lassen.
4. `NumberGuessingGameApplication.java` starten.
5. Im Browser **http://localhost:8080** öffnen.

Die SQLite-Tabelle wird beim Start automatisch erstellt. Ein separater Datenbankserver ist nicht nötig.

## Mögliche Erweiterungen

Später könnte ich das Spiel noch erweitern, beispielsweise unterschiedliche Schwierigkeitsgrade usw. 
