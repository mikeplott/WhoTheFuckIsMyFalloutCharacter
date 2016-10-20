console.log('ready to go')
console.log($)
console.log(_)
console.log(Backbone)
/* Show Modal of Choices */
$('#myModal').modal('show');

/* Dynamic content blocks*/
// var skyrimContent = ""
//   skyrimContent +=
//   skyrimContent +=
//   skyrimContent +=
//   skyrimContent +=
//   skyrimContent +=
//
// var falloutContent = ""
//   falloutContent +=
//   falloutContent +=
//   falloutContent +=
//   falloutContent +=
//   falloutContent +=
//   falloutContent +=
/*Backbone routing */
// var AppRouter = Backbone.Router.extend({
//     routes: {
//         "fallout": "falloutContent"
//         "skyrim": "skyrimContent"
//     }
// });
var clickListener = function(){
  $( "#fallout-button" || "#skyrim-button" ).click(function() {
  $('#myModal').modal('hide');
  });
}
// Initiate the router
// var app_router = new AppRouter;
//
// app_router.on('route:defaultRoute', function(actions) {
//     alert(actions);
// });
// Backbone.history.start();

// $.getJSON("").then(injectFallout)
// $.getJSON("").then(injectFallout)
