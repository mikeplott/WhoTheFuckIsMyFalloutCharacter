/* Show Modal of Options */
$('#myModal').modal('show');

/*API Response and Iteration*/
var appContainer = document.querySelector("#app-container");

var newContentConstructor = function(response){
  var falloutParent = data;
  var data = JSON.parse(response.body);
  console.log(typeof data);
  // var falloutCharacters = parsedData.falloutCharacters[0].desc
  var falloutShover = function(){
    var falloutContent = ""
      falloutContent += '<div class="text-center">';
      falloutContent +=    "<h1> Your SPECIAL is...</h1>";
      falloutContent +=    '<ul>';
      falloutContent +=      "<li><h2>" + falloutCharacters[8] + "</h2></li>";
      falloutContent +=      "<li><h2>" + falloutCharacters[7] + "</h2></li>";
      falloutContent +=      "<li><h2>" + falloutCharacters[3] + "</h2></li>";
      falloutContent +=      "<li><h2>" + falloutCharacters[1] + "</h2></li>";
      falloutContent +=      "<li><h2>" + falloutCharacters[5] + "</h2></li>";
      falloutContent +=      "<li><h2>" + falloutCharacters[0] + "</h2></li>";
      falloutContent +=      "<li><h2>" + falloutCharacters[6] + "</h2></li>";
      falloutContent +=    "</ul>"
      falloutContent +=    "<h1> " + falloutBackstory[0] + " </h1>";
      falloutContent +=    "<h1> " + falloutBackstory[1] + " </h1>";
      falloutContent +=    "<h1> " + falloutBackstory[2] + " </h1>";
      falloutContent +=    '<h1 class="btn btn-success">' + falloutBackstory[2] + " </h1>";
      falloutContent += "</div>";

    document.getElementById("fallout").appendChild(falloutContent);
  }
  falloutCharacters.forEach(falloutShover);
}
// invocation.open('GET', url, true);

/*jQuery Functions & Calls*/

$( "#fallout-button" ).click(function() {
  $('#myModal').modal('hide');
  $.getJSON("http://localhost:4567/fallout").then(newContentConstructor)
  // $( "#fallout" ).append( falloutContent );
});
// $( "#skyrim-button" ).click(function() {
//   $('#myModal').modal('hide');
//   $( "#skyrim" ).append( skyrimContent );
// });

// $.getJSON("http://localhost:4567/skyrim").then(newContentConstructor);
