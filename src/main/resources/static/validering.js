function sjekkGyldig(data, felt){
    // A couple of variables to avoid repetition
    const felt_lc = felt.toLowerCase();
    const error = "#"+felt_lc+"-error"

    if (data === ""){
        $(error).html(felt + " må fylles.");
        return false;
    }
    if (data === ("--Velg " + felt_lc + "--")){
        $(error).html("Må velge en " + felt_lc + ".");
        return false;
    }

    $(error).html("");
    return true;            // No need for "else" because this line will only be reached if all is good.
}