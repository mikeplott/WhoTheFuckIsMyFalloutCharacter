/* Show Modal of Options */
$('#myModal').modal('show');

/*API Response and Iteration over Returned Random Array*/
var newContentConstructor = function(response){
  var falloutChar = response.falloutCharacters;

  var falloutContent = "";
    falloutContent += '<div class="special-container">';
    falloutContent +=    "<h1> YOUR SPECIAL IS...</h1>";
    falloutContent +=    '<ul class="SPECIAL">';
    falloutContent +=      "<li><h2> STRENGTH:" + falloutChar[0].str + "</h2></li>";
    falloutContent +=      "<li><h2> PERCEPTION:" + falloutChar[0].per + "</h2></li>";
    falloutContent +=      "<li><h2> ENDURANCE:" + falloutChar[0].end + "</h2></li>";
    falloutContent +=      "<li><h2> CHARISMA:" + falloutChar[0].cha + "</h2></li>";
    falloutContent +=      "<li><h2> INTELLIGENCE:" + falloutChar[0].intel + "</h2></li>";
    falloutContent +=      "<li><h2> AGILITY:" + falloutChar[0].agi + "</h2></li>";
    falloutContent +=      "<li><h2> LUCK:" + falloutChar[0].luck + "</h2></li>";
    falloutContent +=    "</ul>";
    falloutContent +=    '<h1 class="btn btn-success refresh-random"> Try again? </h1>';
    falloutContent += "</div>";
    var falloutEl = document.getElementById("fallout").innerHTML = falloutContent;
}

var specialDiv = document.getElementsByClassName('special-container');

var backstoryCreator = function(){
  document.specialDiv.appendChild("falloutBack");
  falloutBack = ""
  falloutBack +=    "<h1> " + falloutBackstory[0] + " </h1>";
  falloutBack +=    "<h1> " + falloutBackstory[1] + " </h1>";
  falloutBack +=    "<h1> " + falloutBackstory[2] + " </h1>";
}
var updateContent = function(){
  // document.specialDiv.innerHTMl
  $.ajax({
    url: 'http://localhost:4567/fallout',
    type: 'GET',
    dataType: 'json',
    data: {
      param1: 'falloutCharacters[0]',
      param2: 'falloutBackstory[0]'
    },
  })
}

/*jQuery Functions & Calls*/
$( "#fallout-button" ).click(function() {
  $('#myModal').modal('hide');
  $.getJSON("http://localhost:4567/fallout").then(newContentConstructor);
  $.getJSON("http://localhost:4567/background").then(backstoryCreator);
});

$( ".refresh-random").click(function() {
  $.getJSON("http://localhost:4567/fallout").then(updateContent);
});
