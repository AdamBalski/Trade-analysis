// let apiKey
// var finalJSON
// var timeSeriesLabel

let mainChartArray = prepareMainChart();
let derivativeChartArray = prepareDerivativesChart();

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    drawMainChart();
    drawDerivativesChart();
}

function drawMainChart() {
    if(finalJSON == "") return null;

    var data = google.visualization.arrayToDataTable(mainChartArray);

    var options = {
        title: 'Main chart',
        curveType: 'none',
        legend: { position: 'bottom' }
    };

    var chart = new google.visualization.LineChart(document.getElementById('main-chart'));

    chart.draw(data, options);
}

function drawDerivativesChart() {
    if(finalJSON == "") return null;

    var data = google.visualization.arrayToDataTable(derivativeChartArray);

    var options = {
        title: 'Derivatives chart',
        curveType: 'none',
        legend: { position: 'bottom' },
        colors: ['orange']
    };

    var chart = new google.visualization.LineChart(document.getElementById('derivatives-chart'));

    chart.draw(data, options);
}

function prepareMainChart() {
    if(finalJSON == "") return null;

    let chart = [ ['Date', 'Prices'] ];

    let dates = Object.keys(finalJSON[timeSeriesLabel]).sort();

    for(let i = 0; i < dates.length; i++) {
        let date = dates[i];

        chart.push([
            new Date(date + " GMT-5"),
            parseFloat(finalJSON[timeSeriesLabel][date]['1. open'])
        ]);
    }

    return chart;
}

function prepareDerivativesChart() {
    if(finalJSON == "") return null;

    let chart = [ ['Date', 'Derivatives'] ];

    let dates = Object.keys(finalJSON[timeSeriesLabel]).sort();

    for(let i = 0; i < dates.length; i++) {
        let date = dates[i];

        chart.push([
            new Date(date + " GMT-5"),
            parseFloat(finalJSON[timeSeriesLabel][date]['derivative'])
        ]);
    }

    return chart;
}