function checkSliders(slider) {
    att = parseInt($('#attaque').val());
    vit = parseInt($('#vitesse').val());
    pv = parseInt($('#pv').val());
    points = 100 - att - vit - pv;
    if(points >= 0) {
        $('#distrib').html(points);
        $('#v'+slider.attr('id')).html(parseInt(slider.val())+100);
        
    } else {
        switch(slider.attr('id')) {
            case 'attaque':
                slider.val(100 - vit - pv);
                break;
            case 'vitesse':
                slider.val(100 - pv - att);
                break;
            case 'pv':
                slider.val(100 - vit - att);
                break;
        }
    }
}