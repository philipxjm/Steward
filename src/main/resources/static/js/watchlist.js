/**
 * Created by Philip on 5/6/17.
 */

$(document).ready(function() {
    $('.grid').masonry({
        itemSelector: '.grid-item',
        fitWidth: true
    });

    $('.card').each(function(i, obj) {
        let val = 100 - parseInt(parseFloat(obj.id) * 100);
        let h= Math.floor((100 - val) * 120 / 100);
        let s = Math.abs(val - 50)/50;
        let v = 1;
        $(this).css('background-color', hsv2rgb(h, s, v));
    });

    $('.card').tooltip();
});

let hsv2rgb = function(h, s, v) {
    // adapted from http://schinckel.net/2012/01/10/hsv-to-rgb-in-javascript/
    let rgb, i, data = [];
    if (s === 0) {
        rgb = [v,v,v];
    } else {
        h = h / 60;
        i = Math.floor(h);
        data = [v*(1-s), v*(1-s*(h-i)), v*(1-s*(1-(h-i)))];
        switch(i) {
            case 0:
                rgb = [v, data[2], data[0]];
                break;
            case 1:
                rgb = [data[1], v, data[0]];
                break;
            case 2:
                rgb = [data[0], v, data[2]];
                break;
            case 3:
                rgb = [data[0], data[1], v];
                break;
            case 4:
                rgb = [data[2], data[0], v];
                break;
            default:
                rgb = [v, data[0], data[1]];
                break;
        }
    }
    return '#' + rgb.map(function(x){
            return ("0" + Math.round(x*255).toString(16)).slice(-2);
        }).join('');
};