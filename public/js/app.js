/* Show Modal of Options */

$('#myModal').modal('show');

/*API Response and Iteration*/

var newContentConstructor = function(data){
    console.log(data)
    var falloutChar = data[1]
    for (var i = 0; i < falloutChar.length; i++) {
    var slicedData = falloutChar.slice(',');
    slicedData.replace('{', '');
    slicedData.replace('}', '');
    console.log(slicedData);
    }

    var falloutCharacters = slicedData;
  if (typeof falloutCharacters === "object"){
    return falloutCharacters;
  } else {
    console.log("shit's still broke yo")
  }
}

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

//$.getJSON("http://localhost:4567/skyrim").then(newContentConstructor)
$.getJSON("http://localhost:4567/fallout").then(newContentConstructor)
