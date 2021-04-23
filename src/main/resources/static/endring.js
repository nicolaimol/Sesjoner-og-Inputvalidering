/** onload function -> Runs when the webpage is ready to be manipulated */
$(function(){
    setupErrorHandler();
    hentAlleBiler();
    hentEnMotorvogn();
});

const setupErrorHandler = () => {
    $.ajaxSetup({
        error: (data) => {
            $("#error").text(data.responseJSON.message)
        }
    })
}

let cars = [];              // Stores all cars so that we can use them for both merke and type

/** Fetches all cars (models+types) from server */
function hentAlleBiler() {
    $.get( "/hentBiler", function( biler ) {
        cars = biler;
        formaterMerke(biler);
    });
}

/** Formats merke */
function formaterMerke(biler){
    let merkeList = [];                         // Using array (instead of forrige_merke, as in motorvogn3.zip solution)
                                                // to handle Biler regardless of registration order in array on server

    let ut = "<select id='merke' onchange='formaterTyper()'>";
    ut+="<option>--Velg merke--</option>";
    for (const bil of biler){
        if(!merkeList.includes(bil.merke)){
            ut+="<option>"+bil.merke+"</option>";
        }
        merkeList.push(bil.merke);
    }
    ut+="</select>";
    $("#merke-div").html(ut);
}

/** Formats car types */
function formaterTyper(){
    const valgtMerke = $("#merke").val();
    let ut = "<select id='type'>";
    ut+="<option>--Velg type--</option>";
    for(const bil of cars ){
        if(bil.merke === valgtMerke){
            ut+="<option>"+bil.type+"</option>";
        }
    }
    ut+="</select>";
    $("#type-div").html(ut);
}

const hentEnMotorvogn = () => {
    const id = window.location.search.substring(1)
    const url = '/hentEnMotorvogn?id=' + id

    $.get(url, data => {
        console.log(data)
        $('#id').val(data.id)
        $('#personnr').val(data.personnr)
        $('#navn').val(data.navn)
        $('#adresse').val(data.adresse)
        $('#kjennetegn').val(data.kjennetegn)
        $('#merke').val(data.merke)
        // Venter litt med å sette type siden onchange funksjonen til merke må kjøre først
        setTimeout(() => $('#type').val(data.type), 1000)
    })
}

/** Entry registration
 *  Checks for errors in user input and performs get request if all is good.
 */
function endreMotorvogn() {
    const id = $("#id").val();
    const pnr = $("#personnr").val();
    const nvn = $("#navn").val();
    const add = $("#adresse").val();
    const kjt = $("#kjennetegn").val();
    const mrk = $("#merke").val();
    const typ = $("#type").val();

    // Did you know you can do maths with boolean values? True = 1; False = 0.
    // If any of the fields is false, it will be a multiplication by 0. Therefore the result is 0 = false.
    let riktig = sjekkGyldig(pnr, "Personnummer") *  sjekkGyldig(nvn, "Navn") *  sjekkGyldig(add, "Adresse") *  sjekkGyldig(kjt, "Kjennetegn") *  sjekkGyldig(mrk, "Merke") *  sjekkGyldig(typ, "Type");

    if (riktig){
        const motorvogn = {
            id: id,
            personnr : pnr,
            navn : nvn,
            adresse : add,
            kjennetegn : kjt,
            merke : mrk,
            type : typ,
        };
        $.post("/endre", motorvogn, function(){

        });
        window.location.href="/"
    }
}