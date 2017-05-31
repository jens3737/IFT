/**
 * Created by JHNBD42 on 9/03/2017.
 */
var page = 'zoeken';

/* toon new ajax content smoothly
*
* */
function smoothContent(content) {
    $('.inhoudzoeken').fadeOut(500, function(){
        $('#tableBody').html(content);
        paginate(aantalPaginas, huidigePagina);
        $(this).fadeIn(500);
    })
}

/* Pagination functie
* ontvangt aantal pagina's & huidige pagina
*
* */
function paginate(aantalPaginas, huidigePagina) {
    $("#paginationBar").html("");
    for (i = 1; i <= aantalPaginas; i++) {
        /*Is deze pagina pagina 1? zo ja, voeg PREVIOUS button toe*/
        if(i == 1){
            if(huidigePagina == 1) {
                $("#paginationBar").append('<li class="page-item disabled"><a><<</a></li>');
                $("#paginationBar").append('<li class="page-item disabled"><a><</a></li>');

            }
            else{
                var vorigePagina = huidigePagina - 1;
                $("#paginationBar").append('<li><a value="first" class="paginationBtn"><<</a></li>');
                $("#paginationBar").append('<li><a value="prev" class="paginationBtn"><</a></li>');
            }
        }

        /*Is dit de huidige pagina? zo ja, voeg active class toe*/
        if(i == huidigePagina) {
            $("#paginationBar").append('<li class="active"><a >' + i + '</a></li>');
        }
        else{
            $("#paginationBar").append('<li><a value="number" id="pageBtn_' + i + '" class="paginationBtn" >' + i + '</a></li>');
        }

        /*Is deze pagina de laatste? zo ja, voeg NEXT button toe*/
        if(i == aantalPaginas) {
            if(i == huidigePagina){
                $("#paginationBar").append('<li class="page-item disabled"><a>></a></li>');
                $("#paginationBar").append('<li class="page-item disabled"><a>>></a></li>');
            }
            else{
                var volgendePagina = huidigePagina + 1;
                $("#paginationBar").append('<li><a value="next" class="paginationBtn">></a></li>');
                $("#paginationBar").append('<li><a value="last" class="paginationBtn">>></a></li>');
            }
        }
    }
}

function fetchSearchPage(link, pagina, zoekTerm){
    console.log(zoekTerm);
    var postData ={};
    postData['paginaNummer'] = pagina;
    postData['zoekTerm'] = zoekTerm;
    // {paginaNummer: pagina, zoekTerm: zoekTerm};
    $.ajax(
        {
            type: 'POST',
            url: link,
            data: postData,
            dataType : 'json',
            success: function (data) {
                huidigePagina = pagina;
                console.log(data);
                createDynamiSearchedTable(data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown+ "!");
            }
        }
    )
}

function fetchFilterPage(link, pagina, filterCatID, filterSchoolId){
    console.log("schoolID: " + filterSchoolId);
    console.log(link+pagina+"&fc=" + filterCatID );
    if(filterSchoolId == null) {
        $.ajax(
            {
                type: 'GET',
                url: link+pagina+"&fc=" + filterCatID ,
                dataType: 'json',
                success: function (data) {
                    huidigePagina = pagina;
                    console.log(data);
                    createDynamicFilteredTable(data);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown+ "!");
                }
            }
        )
    }
    else{
        $.ajax(
            {
                type: 'GET',
                url: link+pagina+"&fc=" + filterCatID + "&fs=" + filterSchoolId,
                dataType: 'json',
                success: function (data) {
                    huidigePagina = pagina;
                    console.log(data);
                    createDynamicFilteredTable(data);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown+ "!");
                }
            }
        )
    }
}

/*
* AJAX GET call die volgende pagina ophaalt
* */
function fetchPage(link, pagina){
    $.ajax(
        {
            type: 'GET',
            url: link+pagina,
            dataType: 'json',
            success: function (data) {
                aantalPaginas = data.aantalPaginas;
                huidigePagina = pagina;
                createDynamicTable(data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown+ "!");
            }
        }
    )
}


/*Eventhandlers voor paginationbar
* roept fetchPage() op
*
* */
$(document).delegate('.paginationBtn', 'click', function()
{
    var type = $(this).attr("value");
    var pagina;

    if(filter){
        switch (type) {
            case "next":
                pagina = huidigePagina + 1;
                fetchFilterPage(linkFilter, pagina, filterCatID, filterSchoolId);
                break;
            case "last":
                pagina = aantalPaginas;
                fetchFilterPage(linkFilter, pagina, filterCatID, filterSchoolId);
                break;
            case "prev":
                pagina = huidigePagina - 1;
                fetchFilterPage(linkFilter, pagina, filterCatID, filterSchoolId);
                break;
            case "first":
                pagina = 1;
                fetchFilterPage(linkFilter, pagina, filterCatID, filterSchoolId);
                break;
            case "number":
                pagina = $(this).attr("id").split("_").pop();
                fetchFilterPage(linkFilter, pagina, filterCatID, filterSchoolId);
                break;
        }
    }
    else if(search){
        switch (type) {
            case "next":
                pagina = huidigePagina + 1;
                fetchSearchPage(linkSearch, pagina, zoekTerm);
                break;
            case "last":
                pagina = aantalPaginas;
                fetchSearchPage(linkSearch, pagina, zoekTerm);
                break;
            case "prev":
                pagina = huidigePagina - 1;
                fetchSearchPage(linkSearch, pagina, zoekTerm);
                break;
            case "first":
                pagina = 1;
                fetchSearchPage(linkSearch, pagina, zoekTerm);
                break;
            case "number":
                pagina = $(this).attr("id").split("_").pop();
                fetchSearchPage(linkSearch, pagina, zoekTerm);
                break;
        }
    }
    else {
        switch (type) {
            case "next":
                pagina = huidigePagina + 1;
                fetchPage(link, pagina);
                break;
            case "last":
                pagina = aantalPaginas;
                fetchPage(link, pagina);
                break;
            case "prev":
                pagina = huidigePagina - 1;
                fetchPage(link, pagina);
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
    }

});

/*Event handler table row click
* link naar detail pagina
* */
$(document).delegate('.clickable-row', 'click', function(){
    location.href = $(this).attr("id");
});


/* EVENTHANDLER stageopdracht klik*/
function stageopdrachtRowClicked(ID) {

    if(ID == 0) {
    }
    else{
        location.href = "/stageopdracht/"+ID;
    }
}

function schoolRowClicked(ID) {

    if(ID == 0) {
    }
    else{
        location.href = "/school/"+ID;
    }

}

function begleiderRowClicked(ID) {

    if(ID == 0) {
    }
    else{
        location.href = "/begeleider/"+ID;
    }
}

function evaluatieRowClicked(ID) {

    if(ID == 0) {
    }
    else{
        location.href = "/evaluatieformulier/"+ID;
    }
}


/* EVENTHANDLER stagiair klik*/
function stagiairRowClicked(ID) {
    if(ID == 0) {
    }
    else {
        location.href = "/stagiair/" + ID;
    }
}



$(document).ready(function() {

    $("#searchBox").val("");

//setup before functions
    var typingTimer;                //timer identifier
    var doneTypingInterval = 800;  //time in ms, 5 second for example
    var $input = $('#searchBox');

//on keyup, start the countdown
    $input.on('keyup', function () {
        console.log("keyup");
        clearTimeout(typingTimer);
        typingTimer = setTimeout(doneTyping, doneTypingInterval);
    });

//on keydown, clear the countdown
    $input.on('keydown', function () {
        console.log("keydown");
        clearTimeout(typingTimer);
    });

//user is "finished typing," do something
    function doneTyping () {
        if($input.val().length >= 3){
            zoekTerm = $input.val();
            fetchSearchPage(linkSearch, 1, $input.val());
        }
        //
        else if($input.val().length == 0){
            search = false;
            fetchPage(link, 1);
        }

    }

    /* select2 selects maken van de SELECT attributen */
    $('.autocomplete_select2').select2();

    /*EVENTHANDLER stageopdracht zoekresultaat klik*/
    $('#autocomplete_stageopdracht').select2({
        //minimumResultsForSearch: -1
    }).on('select2:select', function(){
        if($('#autocomplete_stageopdracht').select2('val') == 0) {
            <!-- doe niets als niets aangeduid -->
        }
        else{
            console.log("haloazeazeaze");
            location.href = "/stageopdracht/"+$('#autocomplete_stageopdracht').select2('val');
        }
    });

    //On select van dropdown filter, clear de search text
    $('#autocomplete_categorie').on("select2:selecting", function(e) {
        $("#searchBox").val("");
        console.log("selecting");
        // what you would like to happen
    });

    //On select van dropdown filter, clear de search text
    $('#autocomplete_school').on("select2:selecting", function(e) {
        $("#searchBox").val("");
        console.log("selecting");
        // what you would like to happen
    });

    $("#btnFilter").click(function(){
        console.log("btnFilter clicked");
        if($("#autocomplete_categorie option:selected").val() > 0 || $("#autocomplete_school option:selected").val() > 0){
            console.log($("#autocomplete_categorie option:selected").val());
            filterCatId = $("#autocomplete_categorie option:selected").val();
            filterSchoolId = $("#autocomplete_school option:selected").val()
            fetchFilterPage(linkFilter, 1, filterCatId, filterSchoolId);
        }
        else {
            // $('#tableBody').html("<h4>Geen resultaten gevonden</h4>");
        }

    });

    paginate(aantalPaginas, huidigePagina);
});
