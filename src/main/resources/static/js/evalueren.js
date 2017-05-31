/**
 * Created by JHNBD42 on 4/04/2017.
 */

/* toon new ajax content smoothly
 *
 * */
function smoothContent(content) {
    $("#submit").addClass("hide");
    $('#kaas').fadeOut(500, function(){
        $('#kaas').html(content);
        if(huidigePagina == aantalPaginas){
            $("#submit").removeClass("hide");
        }
        paginate(aantalPaginas, huidigePagina);
        $(this).fadeIn(500);
        $("html, body").animate({ scrollTop: 0 }, 600);
    })
}

function paginate(aantalPaginas, huidigePagina) {
    $("#paginationBar").html("");
    for (i = 1; i <= aantalPaginas; i++) {
        if(i == 1){
            if(huidigePagina == 1) {
                $("#paginationBar").append('<li class="disabled"><a><</a></li>');
            }
            else{
                $("#paginationBar").append('<li><a value="prev" class="paginationBtn"><</a></li>');
            }
        }

        if(i == aantalPaginas) {
            if(i == huidigePagina){
                $("#paginationBar").append('<li class="page-item disabled"><a>></a></li>');
            }
            else{
                var volgendePagina = huidigePagina + 1;
                $("#paginationBar").append('<li><a value="next" class="paginationBtn">></a></li>');
            }
        }
    }
}

function fetchPage(link, pagina){
    // if(huidigePagina < pagina) {
        if (!checkAnswers()) {
            $.ajax(
                {
                    type: 'GET',
                    url: link + pagina,
                    dataType: 'json',
                    success: function (data) {
                        huidigePagina = pagina;
                        console.log(data);
                        createDynamicFormulier(data);
                        $("#error").addClass("hide");
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown + "!");
                    }
                }
            )
        }
    
    // else{
    //     $.ajax(
    //         {
    //             type: 'GET',
    //             url: link+pagina,
    //             dataType: 'json',
    //             success: function (data) {
    //                 huidigePagina = pagina;
    //                 console.log(data);
    //                 createDynamicFormulier(data);
    //             },
    //             error: function(jqXHR, textStatus, errorThrown) {
    //                 alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown+ "!");
    //             }
    //         }
    //     )
    // }
}

function loadJsonContent(vragen, schalen){
    console.log("vraagIdentiteit: " + vragen);
    var content = "";
    maakSchalenButtons(schalen);
    var tempArray = [];

    //Steek de nodige vraagAntwoordItems in de tempArray
    $.each(vraagAntwoord, function(i, item) {
        $.each(vragen, function(key, value) {
            if(item.vraagID == value.id){
                tempArray.push(item);
            }
        });
    });
    console.log(tempArray);
    maakSchalenButtons(schalen);
    var openvraag = "";
    $.each(tempArray, function(i, item){

        var contentSchaal = "<div class='btn-group MCvragen' data-toggle='buttons'>";
        $.each(schalen, function(j, value){
            if(value.id == item.antwoord){
                contentSchaal = contentSchaal + "<label class='btn btn-success btnRadio active' data-toggle='tooltip' data-placement='bottom' title='" + value.waarde+ "'>" +
                    "<input class='form-control' type='radio' value='" + value.id + "'>" + value.id + "</label>"
            }
            else{
                contentSchaal = contentSchaal + "<label class='btn btn-success btnRadio' data-toggle='tooltip' data-placement='bottom' title='" + value.waarde+ "'>" +
                    "<input class='form-control' type='radio' value='" + value.id + "'>" + value.id + "</label>"
            }
        });
        contentSchaal = contentSchaal + "</div>";

        if(item.type == false){
            content = content + "<div id='overflow' class='col-lg-10 col-md-10 col-sm-10' style=' margin-top: 5px'><div class='container'><div><div id='centerSmallScreen' class='col-lg-6 col-md-6 col-sm-6 col-xs-12 vraagdiv'><strong>" +
                "<p class='vraag'id='" + item.vraagID + "'>" + item.vraagOmschrijving + "</p></strong></div>" +
                "<div id='probeersel' class='col-lg-6 col-md-6 col-sm-6 col-xs-12 text-center'>" + contentSchaal
                + "</div></div></div><hr></div>"
        }
        else{
            openvraag = openvraag + "<div id='overflow' class='col-lg-10 col-md-10 col-sm-10' style=' margin-top: 5px'><div class='container'><div><div class='col-lg-4 col-md-4 col-sm-12'><strong>" +
                "<p class='vraag'id='" + item.vraagID +  "'>" + item.vraagOmschrijving + "</p></strong></div>" +
                "<div class='col-lg-8 col-md-8 col-sm-12'><textarea class='form-control openvraag' rows='5'>" + item.antwoord + "</textarea></div>"
                + "</div></div><hr></div>"
        }
    });
    content = content + openvraag;
    smoothContent(content);
}


var vraagAntwoord = [];
//telkens je op een radio button klikt, steek je vraag + antwoord samen in een array vraagAntwoord.
$(document).delegate('.btnRadio', 'click', function(){
    var antwoordWaarde = $(this).find('input').attr('value');
    var vraagId = $(this).closest(".container").find('p').attr('id');
    var vraagOmschrijving = $(this).closest(".container").find('p').text();
    var isAnswered = false;

    //Indien vraag al eens beantwoord is, update array geen push
    $.each(vraagAntwoord, function(i, item) {
        if(vraagId == item.vraagID){
            isAnswered = true;
            //edit
            item.antwoord = antwoordWaarde;
        }
    });

    //indien vraag nog niet beantwoord, push nieuw vraag + antwoord pair
    if(!isAnswered){
        vraagAntwoord.push(
            {vraagID: vraagId, vraagOmschrijving: vraagOmschrijving, antwoord: antwoordWaarde, type: false, stagiairID: stagiairID}
        );
    }

    console.log(vraagAntwoord);
});

//telkens wanneer je in de textarea typt, steek je vraag + antwoord samen in een array vraagAntwoord.
$(document).delegate('.openvraag', 'input propertychange', function() {
    var vraagId = $(this).closest(".container").find('p').attr('id');
    var vraagOmschrijving = $(this).closest(".container").find('p').text();
    var isAnswered = false;
    var antwoordWaarde = $(this).val();
    console.log(antwoordWaarde);

    //Indien vraag al eens beantwoord is, update array geen push
    $.each(vraagAntwoord, function(i, item) {
        if(vraagId == item.vraagID){
            isAnswered = true;
            //edit
            item.antwoord = antwoordWaarde;
        }
    });

    //indien vraag nog niet beantwoord, push nieuw vraag + antwoord pair + type = true voor openvraag
    if(!isAnswered){
        vraagAntwoord.push(
            {vraagID: vraagId, vraagOmschrijving: vraagOmschrijving, antwoord: antwoordWaarde, type: true}
        );
    }

    console.log(vraagAntwoord);

});

$(document).delegate('.paginationBtn', 'click', function() {
    var type = $(this).attr("value");
    var pagina;
    $("#submit").addClass("hide");



    switch (type) {
        case "next":
            if(huidigePagina < aantalPaginas){
                pagina = huidigePagina + 1;
                fetchPage(link, pagina);

                break;
            }
        case "last":
            pagina = aantalPaginas;
            fetchPage(link, pagina);
            break;
        case "prev":
            console.log(huidigePagina);
            if(huidigePagina > 1){
                pagina = huidigePagina - 1;
                fetchPage(link, pagina);
                break;
            }
            break;
        case "first":
            pagina = 1;
            fetchPage(link, pagina);
            break;
        case "number":
            pagina = $(this).attr("id").split("_").pop();
            fetchPage(link, pagina);
            break;
    }
});

function checkAnswers() {
    $("error").addClass("hide");
    var j = 1;
    var hasChecked = false;
    var error = false
    $('.MCvragen label').each(function (i, obj) {
        if($(this).hasClass("active")){
            hasChecked = true;
        }
        if (j % 6 == 0 && j != 0) {
            if(hasChecked == false){
                error = true;
                $("#error").removeClass("hide");
            }
            else{
                hasChecked = false;
            }
        }
        j++;
    });

    $('.openvraag').each(function (i, obj) {
        if(!$(this).val()){
            error = true;
            $("#error").removeClass("hide");
        }
    });
    return error;
}

$(document).ready(function(){
    // $('[data-toggle="tooltip"]').tooltip({
    //     trigger : 'hover'
    // });

    $('body').tooltip({
        selector: '[data-toggle="tooltip"]',
        trigger: 'hover'
    });

    $("#saveEval").on("click", function(){
        //Data voor POST, stagiair ID meegeven voor CREATE in controller

        //check indien  alle vragenn beantwoord zijn
        if(!checkAnswers()){
            $.ajax(
                {
                    url: "/evaluatieformulieropslaan",
                    type: 'POST',
                    contentType : 'application/json',
                    //'; charset=utf-8',
                    data: JSON.stringify(vraagAntwoord),
                    dataType : 'json',
                    success: function (data) {
                        console.log(data);
                        location.href = "/evaluatieformulier/"+data.id;
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown+ "!");
                    }
                });
        }

        console.log("goed gedaan, Jens. Je klik-functie werkt naar Behoren!")
    });

    paginate(aantalPaginas,huidigePagina);
});

