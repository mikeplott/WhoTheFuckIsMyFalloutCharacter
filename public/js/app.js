
/* Show Modal of Choices */
$('#myModal').modal('show');

/* Dynamic content blocks*/
// var skyrimContent = ""
//   skyrimContent += "<h1> My Race is " + raceJSON + "</h1>"
//   skyrimContent += "<h2> and I am a " + PersonJSON  + "with " + fact1JSON "</h2>"
//   skyrimContent +=

//
// var falloutContent = ""
//   falloutContent += "<h1> My SPECIAL is " + specialJSON + "</h1>"
//   falloutContent += "<h2> and I am a " + personJSON + "with " + fact1JSON + "</h2>"
//   falloutContent +=

/*Backbone routing */
// var AppRouter = Backbone.Router.extend({
//     routes: {
//         "fallout": "falloutContent"
//         "skyrim": "skyrimContent"
//     }
// });
$( "#fallout-button" || "#skyrim-button" ).click(function() {
$('#myModal').modal('hide');
});
// $.getJSON("").then(injectFallout)
// $.getJSON("").then(injectFallout)
