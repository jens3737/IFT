/**
 * Created by JAHBD44 on 29/03/2017.
 */


/*Event handler table row click
 * link naar detail pagina
 *
$(document).delegate('.clickable-row', 'click', function(){
    location.href = $(this).attr("id");
});
 */

/* EVENTHANDLER stageopdracht klik*/
function profielClicked(ID) {

    if(ID == 0) {
    }
    else{
        location.href = "/useraccount/"+ID;
    }
}