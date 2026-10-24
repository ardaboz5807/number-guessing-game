package com.numberguessing;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class GameController {

    private Database db = new Database();

    @GetMapping("/")
    public String showDashboard(HttpSession session, Model model) {

        GameService game = (GameService) session.getAttribute("game");

        if (game == null) {
            game = new GameService();
            game.startNewGame();
            session.setAttribute("game", game);
        }

        model.addAttribute("tries", game.getTries());
        model.addAttribute("won", game.isWon());
        model.addAttribute("scores", db.getHighScores());

        return "index";
    }

    @PostMapping("/guess")
    public String submitGuess(@RequestParam int guess, HttpSession session, Model model) {

        GameService game = (GameService) session.getAttribute("game");

        if (game == null) {
            game = new GameService();
            game.startNewGame();
            session.setAttribute("game", game);
        }

        String result = game.checkGuess(guess);

        model.addAttribute("result", result);
        model.addAttribute("tries", game.getTries());
        model.addAttribute("won", game.isWon());
        model.addAttribute("scores", db.getHighScores());

        return "index";
    }

    @PostMapping("/restart")
    public String restartGame(HttpSession session) {

        GameService game = (GameService) session.getAttribute("game");

        if (game == null) {
            game = new GameService();
            session.setAttribute("game", game);
        }

        game.startNewGame();

        return "redirect:/";
    }

    @PostMapping("/score")
    public String saveScore(@RequestParam String playerName, HttpSession session) {

        GameService game = (GameService) session.getAttribute("game");

        if (game == null || !game.isWon()) {
            return "redirect:/";
        }

        db.saveScore(playerName, game.getTries());

        return "redirect:/";
    }

    /*Kurze Erklärung für die, die sich damit nicht auskennen:
    
     Wenn die Website geöffnet wird, ruft Spring showDashboard() auf.
     Zuerst wird in der HttpSession (ähnlich einer HashMap) nach dem
     Schlüssel "game" gesucht.

     Beim ersten Aufruf existiert dieser Schlüssel noch nicht,
     deshalb ist game == null.

     Dadurch wird ein neues GameService-Objekt erstellt, ein Spiel
     gestartet und das Objekt unter dem Schlüssel "game" gespeichert.

     Wenn der Spieler eine Zahl eingibt, wird submitGuess() aufgerufen.
     Die Session enthält bereits das GameService-Objekt, was ja bei showDashboard() "erstellt" wurde, weshalb ist
     game != null ist und die if-Bedingung übersprungen wird.

     So wird das bestehende Spiel mit derselben Zufallszahl
     weiterverwendet, statt bei jedem Klick ein neues zu starten.
     */

}
