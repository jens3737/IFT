/**
 * Created by JHNBD42 on 9/03/2017.
 */

$(document).ready(function() {
    var sideslider = $('[data-toggle=collapse-side]');
    var sel = sideslider.attr('data-target');
    var sel2 = sideslider.attr('data-target-2');
    sideslider.click(function(event){
        $(sel).toggleClass('in');
        $(sel2).toggleClass('out');
    });
});

function stagiairEvaluerenClicked(ID) {
    if(ID == 0) {

    }
    else {
        location.href = "/evaluatie/" + ID;
    }
}