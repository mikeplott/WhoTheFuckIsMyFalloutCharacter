/* Show Modal of Options */
$('#myModal').modal('show');

/*API Response and Iteration*/
var appContainer = document.querySelector("#app-container")

var newContentConstructor = function(data){
    var falloutChar = data[0];
    var falloutShover = function(){
      document.getElementById("fallout").appendChild(falloutContent);
      var falloutContent = ""
        falloutContent += '<div class="text-center">';
        falloutContent += '<h2>' + falloutBackstory[0] + '</h2>'
        falloutContent +=    "<h1> Your SPECIAL is...</h1>";
        falloutContent +=    '<ul style="text-decoration:none;">';
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
        falloutContent += "</div>";
    }
    console.log(data)
    console.log(data[0]);
    falloutChar.forEach(falloutShover);

    // for (var i = 0; i < falloutChar.length; i++) {
    // var slicedData = falloutChar.slice(',');
    // slicedData.replace('{', '');
    // slicedData.replace('}', '');
    // console.log(slicedData);
    // }
  //   var falloutCharacters = slicedData;
  // if (typeof falloutCharacters === "object"){
  //   return falloutCharacters;
  // } else {
  //   console.log("shit's broke yo")
  // }
}

/*jQuery Functions & Calls*/

$( "#fallout-button" ).click(function() {
  $('#myModal').modal('hide');
  $.getJSON("http://localhost:4567/fallout").then(newContentConstructor)
  $( "#fallout" ).append( falloutContent );
});
$( "#skyrim-button" ).click(function() {
  $('#myModal').modal('hide');
  $( "#skyrim" ).append( skyrimContent );
});

// $.getJSON("http://localhost:4567/skyrim").then(newContentConstructor)
// var frontEndRNG = function(){
//   Math.random() * 10;
//
// }
