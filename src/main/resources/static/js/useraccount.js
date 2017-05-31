
function useraccountUpdate(){
    var postData ={};
    postData['naam'] = $("#ucNaam").val();
    postData['email'] = $("#ucEmail").val();
    postData['gsm'] = $("#ucGsm").val().toString();
    postData['geboortedatum'] = $("#geboortedatum").val();
    postData['adres'] = $("#ucAdres").val();
    if(rol == "ROLE_STAGECOORDINATOR" || rol == "ROLE_BEGELEIDER"){
        console.log("in if stagecoordinator/begeleider");
        postData['functie'] = $("#ucFunctie").val();
    }
    postData['id'] = useraccountID;
    console.log(postData);

    $.ajax(
        {
            url: "/useraccountupdate",
            type: 'POST',
            data: postData,
            dataType : 'json',
            success: function (data) {
                console.log("UseraccountUpdate Success");
                $('#updateAlert').fadeIn(4000, function(){
                    // setTimeout(5000);
                    $(this).fadeOut(4000);
                })

            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown+ "!");
            }
        });
}

function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}

function changePassword(huidigWachtwoord, nieuwWachtwoord, herhaalWachtwoord){
    $("#passwordError").addClass("hide");
    $("#passwordErrorTxt").html("");
    var error = 0;

    /*-----VALIDATIE*+++++*/
    if(huidigWachtwoord.length == 0 || nieuwWachtwoord.length == 0 || herhaalWachtwoord.length == 0){
        $("#passwordErrorTxt").append("<li>Gelieve alle velden in te vullen. </li>");
        $("#passwordError").removeClass("hide");
        return;
    }

    /*wachtwoord en herhaal wachtwoord moeten overeenkomen*/
    if(nieuwWachtwoord !== herhaalWachtwoord){
        $("#passwordErrorTxt").append("<li>Wachtwoorden komen niet overeen.</li>");
        error = 1;
    }

    /*Hoofdletter controleren*/
    if ( !nieuwWachtwoord.match(/[A-Z]/) ) {
        $("#passwordErrorTxt").append("<li>Wachtwoord moet minstens 1 hoofdletter bevatten.</li>");
        error = 1;
    }

    /*Cijfer controleren*/
    if ( !nieuwWachtwoord.match(/\d/) ) {
        $("#passwordErrorTxt").append("<li>Wachtwoord moet minstens 1 cijfer bevatten.</li>");
        error = 1;
    }

    /*Lengte wachtwoord controleren*/
    if ( nieuwWachtwoord.length < 8 || nieuwWachtwoord.length > 16) {
        $("#passwordErrorTxt").append("<li>Wachtwoord moet minstens 8 of maximaal 16 karakters bevatten.</li>");
        error = 1;
    }

    if(error > 0){
        $("#passwordError").removeClass("hide");
        return;
    }


    /*-----AJAX CALL-----*/
    var postData ={};
    console.log("huidig ww: " + huidigWachtwoord);
    console.log("nieuw ww: " + nieuwWachtwoord);
    console.log("herhaal ww: " +herhaalWachtwoord);
    postData['huidigWachtwoord'] = huidigWachtwoord;
    postData['nieuwWachtwoord'] = nieuwWachtwoord;
    postData['herhaalWachtwoord'] = herhaalWachtwoord;

    $.ajax(
        {
            url: "/useraccountpasswordchange",
            type: 'POST',
            data: postData,
            dataType : 'json',
            success: function (data) {
                console.log("UseraccountUpdate Success");

                switch(data) {
                    case 0:
                        hideModal();
                        break;
                    case 1:
                        $("#passwordErrorTxt").html("<li>Huidig wachtwoord en nieuw wachtwoord mogen niet overeenkomen.</li>");
                        $("#passwordError").removeClass("hide");
                        break;
                    case 2:
                        $("#passwordErrorTxt").html("<li>Controleer uw wachtwoord en probeer het opnieuw.</li>");
                        $("#passwordError").removeClass("hide");
                        break;
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown+ "!");
            }
        });

}

function hideModal() {
    $('#paswoordModal').modal('hide');
    console.log("kaas");
    $('#passwordAlert').fadeIn(4000, function(){
        // setTimeout(5000);
        $(this).fadeOut(4000);
    })
}

$( document ).ready(function() {

    $("#gsmlength").attr('maxlength','4');

    /* Edit buttons event handler */
    $(".editBtn").on('click', function () {
        switch($(this).attr("value")) {
            case "account":
                $(this).fadeOut(500, function () {
                    $(".accountBtn").fadeIn(500);
                    $(".accountInput").prop("disabled", false);
                    $("#inputPW").addClass("hide");
                    $("#btnAanpassenPW").removeClass("hide");
                    })
                break;
        }
    });



    /* Cancel buttons event handler */
    $(".cancelBtn").on('click', function () {
        switch($(this).attr("value")) {
            case "account":
                $(".accountBtn").fadeOut(500, function () {
                    $('.editBtn[value="account"]').fadeIn(500);
                    $(".accountInput").prop("disabled", true);
                    $("#inputPW").removeClass("hide");
                    $("#btnAanpassenPW").addClass("hide");
                })

                break;
        }

    });

    $("#btnPWSubmit").on('click', function () {
        changePassword($("#txtWachtwoord").val(), $("#txtNieuwWachtwoord").val(), $("#txtHerhaalWachtwoord").val())
    });


    /* Submit buttons event handler */
    /* Cancel buttons event handler */
    $(".submitBtn").on('click', function () {
        useraccountUpdate();
        switch($(this).attr("value")) {
            case "account":
                $(".accountBtn").fadeOut(500, function () {
                    $('.editBtn[value="account"]').fadeIn(500);
                    $(".accountInput").prop("disabled", true);
                    $("#inputPW").removeClass("hide");
                    $("#btnAanpassenPW").addClass("hide");
                })
                break;
        }

    })
});
