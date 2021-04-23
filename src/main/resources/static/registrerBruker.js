$(function () {
    validate();
})

function validate() {
    $.get("/validateAdmin", function (data) {
        if (!data) {
            window.location.href="/";
        }
    })
}


function registrer() {
    const brukernavn = $("#brukernavn").val();
    const passord = $("#passord").val();

    const kunde = {
        brukernavn: brukernavn,
        passord: passord,
        rettighet: "user"
    }

    $.post("/registrerBruker", kunde, function () {
        window.location.href="/";
    }).fail(function (data) {
        $("#error").text(data.responseJSON.message);
    })
}
