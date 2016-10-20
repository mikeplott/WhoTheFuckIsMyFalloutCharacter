
/* Show Modal of Options */
$('#myModal').modal('show');

/*API Test*/

var serverResponse = function(data){
  for (var i = 0; i < data.length; i++) {
    return data[i];
    console.log(data[i])
  }
}

$.getJSON("http://localhost:4567/skyrim").then(serverResponse)
$.getJSON("http://localhost:4567/fallout").then(serverResponse)


/* Dynamic content blocks*/
//
var falloutContent = ""
  falloutContent += '<div class="text-center">';
  falloutContent +=    "<h1> My SPECIAL is " + serverResponse + "</h1>";
  // falloutContent +=    "<h2> and I am a " + personJSON + "with " + fact1JSON + "</h2>"
  falloutContent +=       "<h1> it works!! </h1>";
  falloutContent += "</div>";

var skyrimContent = ""
  skyrimContent += '<div class="text-center">';
  skyrimContent += "<h1> i love waka flocka</h1>";
  skyrimContent += "<h1> My Race is " + serverResponse.race + "</h1>";
//   skyrimContent += "<h2> and I am a " + PersonJSON  + "with " + fact1JSON "</h2>"
//   skyrimContent += "<h2> and a " + fact2JSON + "</h2>"
  skyrimContent += "</div>";

/*jQuery Functions & Calls*/
$( "#fallout-button" ).click(function() {
  $('#myModal').modal('hide');
  // $.getJSON("mikesurl").then(injectFallout)
  $( "#fallout" ).append( falloutContent );
});
$( "#skyrim-button" ).click(function() {
  $('#myModal').modal('hide');
  // $.getJSON("mikesurl").then(injectFallout)
  $( "#skyrim" ).append( skyrimContent );
});
