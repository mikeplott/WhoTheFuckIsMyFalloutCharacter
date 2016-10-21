/* Show Modal of Options */

$('#myModal').modal('show');

/*API Response and Iteration*/

var falloutCharacters = {"agi":9, "cha" : 7, "desc" : "blue thursday aljsdfjalsf", "end": 5, "id": 0, "intel": 1, "luck": 3, "per": 8, "str": 1}

var serverResponse = function(data){
  for (var i = 0; i < data.length; i++) {
     var rngContent = data;
     console.log(rngContent);

  }
  return rngContent;
}
/* Dynamic content blocks*/

var falloutContent = ""
  falloutContent += '<div class="text-center">';
  falloutContent +=    "<h1> My SPECIAL is...</h1>";
  falloutContent +=    '<ul style="text-decoration:none;">'
  falloutContent +=      "<li><h4>" + falloutCharacters[8] + "</h4></li>"
  falloutContent +=      "<li><h4>" + falloutCharacters[7] + "</h4></li>"
  falloutContent +=      "<li><h4>" + falloutCharacters[3] + "</h4></li>"
  falloutContent +=      "<li><h4>" + falloutCharacters[1] + "</h4></li>"
  falloutContent +=      "<li><h4>" + falloutCharacters[5] + "</h4></li>"
  falloutContent +=      "<li><h4>" + falloutCharacters[0] + "</h4></li>"
  falloutContent +=      "<li><h4>" + falloutCharacters[6] + "</h4></li>"
  falloutContent +=    "</ul>"
  // falloutContent +=    "<h2> and I am a " + storyContent[3] + " with " + storyContent[4] + "</h2>";
  falloutContent +=    "<h1> some of it works!! </h1>";
  falloutContent += "</div>";

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
