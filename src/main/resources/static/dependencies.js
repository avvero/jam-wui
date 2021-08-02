// Generate the Visualization of the Graph into "svg".
var data = $("#graphSource").val();
var svg = Viz(data, "svg");
$("#output").html(svg);

var svgPanZoom = svgPanZoom('#output svg', {
    viewportSelector: '.svg-pan-zoom_viewport',
    panEnabled: true,
    controlIconsEnabled: true,
    zoomEnabled: true,
    dblClickZoomEnabled: true,
    mouseWheelZoomEnabled: true,
    preventMouseEventsDefault: true,
    zoomScaleSensitivity: 0.2,
    minZoom: 0.5,
    maxZoom: 10,
    fit: false,
    contain: false,
    center: true,
    refreshRate: 'auto',
    beforeZoom: function(){},
    onZoom: function(){},
    beforePan: function(){},
    onPan: function(){},
    onUpdatedCTM: function(){}
});

svgPanZoom.fit()