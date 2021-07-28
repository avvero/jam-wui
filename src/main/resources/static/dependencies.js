// Generate the Visualization of the Graph into "svg".
var data = $("#graphSource").val();
var svg = Viz(data, "svg");
$("#graph").html("<hr>"+svg);