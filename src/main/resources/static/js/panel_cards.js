$(document).ready(() => {

    /* Desativa action do Form */
    var form = document.querySelector('form');
    
    form.addEventListener('submit', function(e){
        e.preventDefault();
    });
});

$('#cardOne').change(() => {
    
    $('#labelCardOne').html($('#cardOne')[0].value);
    let card = $('#cardOne')[0].files[0];
    let img = $('#imgCardOne');

    read_image(img, card);
});

$('#cardTwo').change(() => {
    
    $('#labelCardTwo').html($('#cardTwo')[0].value);
    let card = $('#cardTwo')[0].files[0];
    let img = $('#imgCardTwo');

    read_image(img, card);
});

$('#formCadastrarCartas').submit(() => {

    $.ajax({
        url: '/card/save',
        method: 'POST',
        data: {
            cardOne: $('#imgCardOne').attr('src'),
            cardTwo: $('#imgCardTwo').attr('src'),
        },
        success: (data) => {
            $('#modalFormCadastrarCartas').modal('hide');
            cleanModal();
            show_alert("Cartas cadastradas com sucesso!", true);
            reload_cards();  
        },
        error: (jqXHR, textStatus, errorThrown) => {
            $('#modalFormCadastrarCartas').modal('hide');
            show_alert(jqXHR.responseJSON.message, false);
        }
    });
});

function cleanModal() {
    $('#labelCardOne').html('Selecionar arquivo');
    $('#cardOne').val(null);
    $('#imgCardOne').attr('src', null);

    $('#labelCardTwo').html('Selecionar arquivo');
    $('#cardTwo').val(null);
    $('#imgCardTwo').attr('src', null);
}

function delete_card (id) {

    $.ajax({
        url: '/card/delete',
        method: 'DELETE',
        data: {
            id: id
        },
        success: () => {
            show_alert('Cartas excluídas com sucesso!', true);
            reload_cards();
        },
        error: (jqXHR, textStatus, errorThrown) => {
            show_alert(jqXHR.responseJSON.message, false);
        }
    });
}

function read_image (img, card) {
    const fileReader = new FileReader();

    fileReader.onloadend = () => {
        $(img).attr('src', fileReader.result);
    }

    fileReader.readAsDataURL(card);
}

function reload_cards () {

    $.ajax({
        url: '/card/findAll',
        method: 'GET',
        success: (data) => {
            $('#card-cards-body').html(data);
        },
        error: (jqXHR, textStatus, errorThrown) => {
            show_alert(jqXHR.responseJSON.message, false);
        }
    });
}

function show_alert (message, type) {
    
    $('#alert').html(null);
    
    if (type) {
        $('#alert').append(
            "<div class='alert alert-success alert-dismissible fade show' role='alert'>" + message +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                    "<span aria-hidden='true'>&times;</span>" +
                "</button>" +
            "</div>"
        );
    }
    
    else {
        $('#alert').append(
            "<div class='alert alert-danger alert-dismissible fade show' role='alert'>" + message +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                    "<span aria-hidden='true'>&times;</span>" +
                "</button>" +
            "</div>"
        );
    }
}