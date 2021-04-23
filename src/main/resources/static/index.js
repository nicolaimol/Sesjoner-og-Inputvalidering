/** onload function -> Runs when the webpage is ready to be manipulated */
$(function(){
    setupErrorHandler();
    hentAlle();
    valider();
});

const setupErrorHandler = () => {
    $.ajaxSetup({
        error: (data) => {
            $("#error").text(data.responseJSON.message)
        }
    })
}

let validert = false;

function valider() {
    $.get("/validate", function (data) {
        validert = data;
        if (data) {
            $("#login-knapp").text("Logg ut")
        } else {
            $("#login-knapp").text("Logg inn")
        }

    })
}

function handleLogin() {
    if (validert) {
        $.get("/logout",)
    }
    window.location.href="/login.html";
}

/** Fetches all Motorvogn entries */
function hentAlle() {
    $.get( "/hentAlle", function(biler) {
        formaterData(biler);
    });
}

/** Formats fetched Motorvogn entries into a table */
function formaterData(biler) {
    let ut = "<table class='table table-striped'><tr><th>Personnr</th><th>Navn</th><th>Adresse</th>" +
        "<th>Kjennetegn</th><th>Merke</th><th>Type</th><th></th><th></th></tr>";
    for (const bil of biler) {
        ut += "<tr><td>" + bil.personnr + "</td><td>" + bil.navn + "</td><td>" + bil.adresse + "</td>" +
            "<td>" + bil.kjennetegn + "</td><td>" + bil.merke + "</td><td>" + bil.type + "</td>" +
            "<td><button class='btn btn-primary' onclick='idTilEndring(" + bil.id + ")'>Endre</button></td>" +
            "<td><button class='btn btn-danger' onclick='slettEn(" + bil.id + ")'>Slett</button></td></tr>" ;
    }
    ut += "</table>";
    $("#bilene").html(ut);
}

/** Deletes all Motorvogn entries */
function slettAlle() {
    $.get( "/slettAlle", function() {
        hentAlle();
    });
}

/** Deletes one single entry using its ID as identifier.
 * @param {number}  id   Entry id. Hidden in HTML, but available from database/server/client.
 *
 * N.B.: Can also be done with the personnr, if you don't want to include the possibility of a person having multiple cars.
 */
function slettEn(id){
    let url = "/slettEn?id="+id;
    $.get(url, function(){
        hentAlle();
    })
}

const idTilEndring = (id) => {
    window.location.href = "/endring.html?" + id
}
