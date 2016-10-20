/* Show Modal of Options */

$('#myModal').modal('show');
var shit = []
/*API Response and Iteration*/
var serverResponse = function(data){
  for (var i = 0; i < data.length; i++) {
     shit = data;
     console.log(shit)
  }
}
/* Dynamic content blocks*/
//
var falloutContent = ""
  falloutContent += '<div class="text-center">';
  falloutContent +=    "<h1> My SPECIAL is " + shit[2] + "</h1>";
  falloutContent +=    "<h2> and I am a " + shit[3] + " with " + shit[4] + "</h2>";
  falloutContent +=    "<h1> it works!! </h1>";
  falloutContent += "</div>";

var skyrimContent = ""
  skyrimContent += '<div class="text-center">';
  skyrimContent +=   "<h1> i love waka flocka</h1>";
  skyrimContent +=   "<h1> Your race is " + shit.race + ".</h1>";
  // skyrimContent += "<h2> You are a " + serverResponse[1]  + "with " + serverResponse[2] "</h2>"
  skyrimContent +=   "<h2> and a " + shit[3] + "</h2>"
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

$.getJSON("http://localhost:4567/skyrim").then(serverResponse)
$.getJSON("http://localhost:4567/fallout").then(serverResponse)
