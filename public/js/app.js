/* Show Modal of Options */

$('#myModal').modal('show');
<<<<<<< HEAD

/*API Response and Iteration*/

var falloutCharacters = {"agi":9, "cha" : 7, "desc" : "blue thursday aljsdfjalsf", "end": 5, "id": 0, "intel": 1, "luck": 3, "per": 8, "str": 1}

/*API Response and Iteration*/

/* Dynamic content blocks*/


// var skyrimContent = ""
//   skyrimContent += '<div class="text-center">';
//   skyrimContent +=   "<h1> i love waka flocka</h1>";
//   skyrimContent +=   "<h1> My race is " + skyrimCharacters[race] + ".</h1>";
//   // skyrimContent += "<h2> You are a " + serverResponse[1]  + "with " + serverResponse[2] "</h2>"
//   skyrimContent +=   "<h2> and a " + SkyrimCharacters[3] + "</h2>"
//   skyrimContent += "</div>";

/*jQuery Functions & Calls*/

$( "#fallout-button" ).click(function() {
  $('#myModal').modal('hide');
  $( "#fallout" ).append( falloutContent );
});
$( "#skyrim-button" ).click(function() {
  $('#myModal').modal('hide');
  $( "#skyrim" ).append( skyrimContent );
});

$.getJSON("http://localhost:4567/skyrim").then(serverResponse)
$.getJSON("http://localhost:4567/fallout").then(serverResponse)
