/**
 * Created by JAHBD44 on 24/04/2017.
 */

// Buiten gebruik wegens niet functioneren
// Dit script is gewoon in de home.html pagina geplaatst en dan werkt dit wel
window.addEventListener("load", function(){
    window.cookieconsent.initialise({
        "palette": {
            "popup": {
                "background": "#216942",
                "text": "#b2d192"
            },
            "button": {
                "background": "#afed71"
            }
        },
        "theme": "edgeless",
        "position": "bottom-left",
        "content": {
            "href": "http://www.belgie.fm/cookies.php"
        }
    })});
