/* Show Modal of Options */

$('#myModal').modal('show');

/*API Response and Iteration*/

var newContentConstructor = function(data){
    console.log(data)
    var falloutChar = data[0]
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
