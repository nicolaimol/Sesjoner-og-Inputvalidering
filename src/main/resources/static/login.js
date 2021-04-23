
function login() {
    const brukernavn = $("#brukernavn").val();
    const passord = $("#passord").val();

    const kunde = {
        brukernavn: brukernavn,
        passord: passord
    }

    $.post("/login", kunde, function () {
        window.location.href="/";
    }).fail(function (data) {
        $("#error").text(data.responseJSON.message)
    })
}
