package no.oslomet.webprog.Sesjoner.og.Inputvalidering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MotorvognController {

    @Autowired
    HttpSession httpSession;

    @Autowired
    MotorvognRepository rep;

    private boolean valider(Motorvogn motorvogn) {
        boolean valid = true;

        if (!motorvogn.getPersonnr().matches("[1-9][0-9]{10}")) {
            valid = false;
        } else if (!motorvogn.getKjennetegn().toUpperCase().matches("[A-Z]{2}[1-9][0-9]{4}")) {
            valid = false;
        }

        return valid;
    }

    private boolean validerLogin() {
        return httpSession.getAttribute("login") != null;
    }

    @PostMapping("/login")
    public void login(Kunde kunde, HttpServletResponse res) throws IOException {
        boolean ok = rep.login(kunde);
        if (!ok) {
            res.sendError(500, "Login feilet");
        } else {
            httpSession.setAttribute("login", true);
        }
    }

    @GetMapping("/validate")
    public boolean validateUser() {
        return httpSession.getAttribute("login") != null;
    }

    @PostMapping("/lagre")
    public void lagreMotorvogn(Motorvogn motorvogn, HttpServletResponse res) throws IOException {
        if (!validerLogin()) {
            res.sendError(403, "Du må logge inn for å laste opp motorvogn");
            return;
        }
        boolean ok = valider(motorvogn) && rep.lagreMotorvogn(motorvogn);
        if (!ok) {
            res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Det skjedde noe feil, prøv igjen senere");
        }
    }

    @GetMapping("/logout")
    public void logout() {
        httpSession.removeAttribute("login");
    }


    @PostMapping("/endre")
    public void endreMotorvogn(Motorvogn motorvogn, HttpServletResponse res) throws IOException {
        if (httpSession.getAttribute("login") == null) {
            res.sendError(403, "Du må logge inn for å endre opp motorvogn");
        } else {
            boolean ok = valider(motorvogn) && rep.endreMotorvogn(motorvogn);
            if (!ok) {
                res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Det skjedde noe feil, prøv igjen senere");
            }
        }
    }

    @GetMapping("/hentAlle")
    public List<Motorvogn> hentAlle(HttpServletResponse res) throws IOException {
        List<Motorvogn> motorvogner = rep.hentAlle();
        if (motorvogner == null) {
            res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Det skjedde noe feil, prøv igjen senere");
        }
        return motorvogner;
    }

    @GetMapping("/hentEnMotorvogn")
    public Motorvogn hentAlle(int id, HttpServletResponse res) throws IOException {
        Motorvogn motorvogn = rep.hentEnMotorvogn(id);
        if (motorvogn == null) {
            res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Det skjedde noe feil, prøv igjen senere");
        }
        return motorvogn;
    }

    @GetMapping("/hentBiler")
    public List<Bil> hentBiler(HttpServletResponse res) throws IOException {
        List<Bil> bil = rep.hentBiler();
        if (bil == null) {
            res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Det skjedde noe feil, prøv igjen senere");
        }
        return bil;
    }

    @GetMapping("/slettAlle")
    public void slettAlle(HttpServletResponse res) throws IOException {
        if (httpSession.getAttribute("login") == null) {
            res.sendError(403, "Du må logge inn for å slette motorvognregistrert");
        }

        boolean ok = rep.slettAlle();
        if (!ok){
            res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
    }

    @GetMapping("/slettEn")
    public void slettEn(int id, HttpServletResponse res) throws IOException {
        if (httpSession.getAttribute("login") == null) {
            res.sendError(403, "Du må logge inn for å slette motorvogn");
        }

        boolean ok = rep.slettEn(id);
        if (!ok){
            res.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
    }
}
