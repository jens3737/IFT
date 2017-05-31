var element;
$(document).ready(function() {
    tekenPie();

    /*Handle active menu item dropdown menu */
    $('.analyseOption').on('click', function (){
        $('.analyseOptionLi').removeClass("active");
        $(this).closest("li").addClass("active");

        smoothContent($(this).attr("id"));
    });
})


function smoothContent(functie) {
    $('#chartBody').fadeOut(500, function(){
        $('#chartBody').html("");
        $('#barChartBody').html("");
        $('#barChartBody2').html("");

        $(".chartSpace").addClass("hide");

        switch(functie) {
            case "stagiairPerSchool":
                $('#panelheading_zoeken').html("Stagiaires per school");
                $('#barChartBody').removeClass("hide");
                tekenBar();
                $('#barChartBody2').closest(".chartSpace").removeClass("hide");
                /*Maak assen kleiner en mooier*/
                editAxisLayout();

                break;
            case "stagiairPerCategorie":
                $('#panelheading_zoeken').html("Stagiaires per stagecategorie");
                $('#chartBody').removeClass("hide");
                tekenPie();
                $('#chartBody').closest(".chartSpace").removeClass("hide");
                $('#chartBuddy').closest(".chartSpace").removeClass("hide");
                break;
            case "gemScorePerSchool":
                $('#panelheading_zoeken').html("Gemiddelde evaluatie score per school");
                $('#barChartBody').removeClass("hide");
                tekenBarGemiddeldeScores();

                $('#barChartBody').closest(".chartSpace").removeClass("hide");
                editAxisLayout();
                break;
            default:
                break;
        }
        $(this).fadeIn(500);
    })
}

function editAxisLayout(){
    $(".x > .domain").attr('d', "M0,3V0H900V3");
    $(".y > .domain").attr('d', "M-3,0H0V350H-3");
}

/*Pie chart valon - stagiair per categorie*/
function tekenPie() {
    //ajax get wrapper object stagiairesperschool
    var stagiairPerCategorieData;
    $.ajax(
        {
            type: 'GET',
            url: "/analyseStagiairPerCategorie",
            dataType: 'json',
            async: false,
            success: function (data) {
                stagiairPerCategorieData = data;
                console.log(data);

            },

            error: function (jqXHR, textStatus, errorThrown) {
                alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown + "!");
            }
        }
    )

    var dataset =[];
    var dataNamen = [];
    var data = [{"letter":"q","presses":1},{"letter":"w","presses":5},{"letter":"e","presses":2}];

    $.each(stagiairPerCategorieData , function( key, value ) {
        dataset.push(value.aantalStag);
        dataNamen.push(value.catnaam);
        console.log(value.aantalStag);
    });

    var width = 460,
        height = 480,
        radius = Math.min(width/2, height/2);

    var color = d3.scale.category20();

    var pie = d3.layout.pie()
        .sort(null);

    var arc = d3.svg.arc()
        .innerRadius(radius - 230)
        .outerRadius(radius - 50);

    // define the svg for pie chart
    var svg = d3.select("#chartBody").append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + width /2+ "," + height/2 + ")");
////here
    var g = svg.selectAll(".arc")
        .data(pie(dataset))
        .enter().append("g")
        .attr("class", "arc")
        .on("mouseover",function(d,i) {

        d3.select(this).append("text")
            .attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")"; })
            .attr("dy", "0.6em")
            .style("font-size","1.7em")
            .style("font-style","arial")
            .style("text-decoration","bold")
            .text(dataset[i]);
        })
        .on("mouseout", function(d) {
            g.select("text").remove();
        })

    g.append("path")
        .attr("d", arc)
        .style("fill", function(d,i) { return color(i); })
        .transition()
        .ease("bounce")
        .duration(2000)
        .attrTween("d", tweenPie);

    var legend = d3.select("#chartBuddy").append("svg")
        .style("float","right")
        .style("position","absolute")
        .attr("class", "legend")
        .selectAll("g")
        .data(data)
        .enter().append("g")
        .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

    legend.append("rect")
        .attr("width", 18)
        .attr("height", 18)
        .style("fill", function(d,i) { return color(i); })


    legend.append("text")
        .attr("x", 24)
        .attr("y", 9)
        .attr("dy", ".35em")
        .text(function(d,i) { return dataNamen[i]; });

    function tweenPie(b) {
        b.innerRadius = 0;
        var i = d3.interpolate({startAngle: 0, endAngle: 0}, b);
        return function(t) { return arc(i(t)); };
    }


};

/**/
function tekenBarGemiddeldeScores() {
    //ajax get wrapper object gemiddelde score stagiaires per school
    var categorie = 1;
    var barData;
    $.ajax(
        {
            type: 'GET',
            url: "/analyseGemiddeldeScorePerSchool" + "?c=" + categorie,
            dataType: 'json',
            async: false,
            success: function (data) {
                barData = data;
                console.log(data);

            },

            error: function (jqXHR, textStatus, errorThrown) {
                alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown + "!");
            }
        }
    )

    var maxY = 5;
    console.log(maxY);

    var margin = {top: 20, right: 20, bottom: 30, left: 40},
        width = 960 - margin.left - margin.right,
        height = 400 - margin.top - margin.bottom;

    var y = d3.scale.linear()
        .domain([0,maxY])
        .range([height, 0]);

    var x = d3.scale.ordinal()
        .rangeRoundBands([0, width], .1)
        .domain(d3.entries(barData).map(function(d) { return d.value.schoolNaam; }));

    var xAxis = d3.svg.axis()
        .scale(x)
        .orient("bottom")


    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left")

    /*D3 tips */
    var tip = d3.tip()
        .attr('class', 'd3-tip')
        .offset([-10, 0])
        .html(function(d) {
            return "<strong>x̄:</strong> <strong><span style='color:black'>" + d.value.gemiddelde + "</span></strong>";
        })

    var svg = d3.select("#barChartBody").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("id", "barScoresPerSchool")
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    svg.call(tip);

    svg.append("g")
    //stond data
        .data(d3.entries(barData))
        .attr("class", "x axis")
        .attr("transform", "translate(0," + height + ")")
        .style("height", "500px")
        .call(xAxis)
        .selectAll("text")
        .attr("y", 10)
        .attr("x", 10)
        .attr("dy", ".35em")
        .attr("transform", "rotate(50)")
        .style("text-anchor", "start");

    svg.append("g")
        .attr("class", "y axis")
        .call(yAxis)
        .append("text")
        .attr("transform", "rotate(-90)")
        .attr("y", 6)
        .attr("dy", ".71em")
        .style("text-anchor", "end")
        .text("Gemiddelde score")
    // .path()

    // svg.select('.x.axis').transition().duration(300).call(xAxis);
    //
    // // same for yAxis but with more transform and a title
    // svg.select(".y.axis").transition().duration(300).call(yAxis)

    var bar = svg.selectAll(".barsuccess")
        .data(d3.entries(barData))
        .enter().append("rect")
        .attr("class", "barsuccess")
        .attr("x", 0)
        .on('mouseover', tip.show)
        .on('mouseout', tip.hide)


    bar.transition().duration(300)
        .attr("x", function(d) { return x(d.value.schoolNaam); }) // (d) is one item from the data array, x is the scale object from above
        .attr("width", x.rangeBand()/1.2) // constant, so no callback function(d) here
        // .attr("width", 41) // constant, so no callback function(d) here
        .attr("y", function(d) { return y(d.value.gemiddelde );})
        .attr("height", function(d) { return height - y(d.value.gemiddelde ); }) // flip the height, because y's domain is bottom up, but SVG renders top down
        .style( "fill", randomColor);


    bar.append("text")
        // .attr("x", function(d) { return x(d.key) + x.rangeBand() / 2; })
        //                    .attr("y", function(d) { return y(d.value.fail); })
        .attr("dx", -3) // padding-right
        .attr("dy", ".35em") // vertical-align: middle
        .attr("text-anchor", "end") // text-align: right
    // .text( function(d) { console.log( d.value.period ); return d.value.period; });


    $("#barScoresPerSchool").css('height', '600px');
    $("#barScoresPerSchool").css('width', '1100px');

}


function tekenBar() {
    //ajax get wrapper object stagiairesperschool
    var barData;
    $.ajax(
        {
            type: 'GET',
            url: "/analyseStagiairPerSchool",
            dataType: 'json',
            async: false,
            success: function (data) {
                barData = data;
                console.log(data);

            },

            error: function (jqXHR, textStatus, errorThrown) {
                alert("We are sorry, something went wrong..." + textStatus + " " + errorThrown + "!");
            }
        }
    )

    var maxY = barData[0].maxStagiaires;
    console.log(maxY);


    var margin = {top: 20, right: 20, bottom: 30, left: 40},
        width = 780 - margin.left - margin.right,
        height = 400 - margin.top - margin.bottom;

    var y = d3.scale.linear()
        .domain([0,(maxY+5)])
        .range([height, 0]);

    var x = d3.scale.ordinal()
        .rangeRoundBands([0, width], .1)
        .domain(d3.entries(barData).map(function(d) { return d.value.schoolNaam; }));

    var xAxis = d3.svg.axis()
        .scale(x)
        .orient("bottom")
        .tickSize(-height, 0, 0)


    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left")
        .tickFormat(d3.format("d"));;

    /*D3 tips */
    var tip = d3.tip()
        .attr('class', 'd3-tip')
        .offset([-10, 0])
        .html(function(d) {
            return "<strong>x̄:</strong> <strong><span style='color:black'>" + d.value.aantalStagiaires + "</span></strong>";
        })

    var svg = d3.select("#barChartBody2").append("svg")
        .attr("width", width + margin.left + margin.right)
        .style("margin-right", "10px")
        .attr("id", "barStagiairesPerSchool")
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    svg.append("g")
        //stond data
            .data(d3.entries(barData))
            .attr("class", "x axis")
            .attr("transform", "translate(0," + height + ")")
            .style("height", "500px")
            .call(xAxis)
        .selectAll("text")
            .attr("y", 10)
            .attr("x", 5)
            .attr("dy", ".35em")
            .attr("transform", "rotate(50)")
            .style("text-anchor", "start");

    svg.append("g")
        .attr("class", "y axis")
        .call(yAxis)
        .append("text")
        .attr("transform", "rotate(-90)")
        .attr("y", 6)
        .attr("dy", ".71em")
        .style("text-anchor", "end")
        .text("# Stagiaires")
        // .path()

    // svg.select('.x.axis').transition().duration(300).call(xAxis);
    //
    // // same for yAxis but with more transform and a title
    // svg.select(".y.axis").transition().duration(300).call(yAxis)

    var bar = svg.selectAll(".barsuccess")
        .data(d3.entries(barData))
        .enter().append("rect")
        .attr("class", "barsuccess")
        .attr("x", 0)
        .on('mouseover', tip.show)
        .on('mouseout', tip.hide)

    svg.call(tip);


    bar.transition().duration(300)
        .attr("x", function(d) { return x(d.value.schoolNaam)+7; }) // (d) is one item from the data array, x is the scale object from above
        .attr("width", x.rangeBand()/1.2) // constant, so no callback function(d) here
        .attr("width", 45) // constant, so no callback function(d) here
        .attr("y", function(d) { return y(d.value.aantalStagiaires );})
        .attr("height", function(d) { return height - y(d.value.aantalStagiaires ); }) // flip the height, because y's domain is bottom up, but SVG renders top down
        .style( "fill", randomColor);


    bar.append("text")
        // .attr("x", function(d) { return x(d.key) + x.rangeBand() / 2; })
        //                    .attr("y", function(d) { return y(d.value.fail); })
        .attr("dx", -3) // padding-right
        .attr("dy", ".35em") // vertical-align: middle
        .attr("text-anchor", "end") // text-align: right
        // .text( function(d) { console.log( d.value.period ); return d.value.period; });


    $("#barStagiairesPerSchool").css('height', '600px');
    $("#barStagiairesPerSchool").css('width', '1000px');

}

/*Genereert random kleuren voor de bar chart*/
var randomColor = (function(){
    var golden_ratio_conjugate = 0.618033988749895;
    var h = Math.random();

    var hslToRgb = function (h, s, l){
        var r, g, b;

        if(s == 0){
            r = g = b = l; // achromatic
        }else{
            function hue2rgb(p, q, t){
                if(t < 0) t += 1;
                if(t > 1) t -= 1;
                if(t < 1/6) return p + (q - p) * 6 * t;
                if(t < 1/2) return q;
                if(t < 2/3) return p + (q - p) * (2/3 - t) * 6;
                return p;
            }

            var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            var p = 2 * l - q;
            r = hue2rgb(p, q, h + 1/3);
            g = hue2rgb(p, q, h);
            b = hue2rgb(p, q, h - 1/3);
        }

        return '#'+Math.round(r * 255).toString(16)+Math.round(g * 255).toString(16)+Math.round(b * 255).toString(16);
    };

    return function(){
        h += golden_ratio_conjugate;
        h %= 1;
        return hslToRgb(h, 0.5, 0.60);
    };


})();

