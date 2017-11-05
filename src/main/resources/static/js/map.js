$(document).ready(function() {
    $('#map').usmap({
        stateStyles: {fill: 'white'},

        click: function(event, data) {
            $('#clicked-state')
                .text('Billed Claims in '+data.name)
                .parent().effect('highlight', {color: '#C7F464'}, 2000);
        }
    });
});