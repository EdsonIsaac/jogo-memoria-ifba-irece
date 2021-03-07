$(document).ready(() => {

    /* Desativa action do Form */
    var form = document.querySelector('form');
    
    form.addEventListener('submit', function(e){
        e.preventDefault();
    });
});

$('#formCadastrarUsuario').submit(() => {

    $.ajax({
        url: '/user/save',
        method: 'POST',
        data: $('#formCadastrarUsuario').serialize(),
        success: () => {
            $('#modalFormCadastrarUsuario').modal('hide');
            cleanModal();
            show_alert("Usuário cadastrado com sucesso!", true);
            reload_users();
        },
        error: (jqXHR, textStatus, errorThrown) => {
            $('#modalFormCadastrarUsuario').modal('hide');
            show_alert(jqXHR.responseJSON.message, false);
        }
    });
});

function cleanModal() {
    $('#name').val(null);
    $('#username').val(null);
    $('#password').val(null);
    $('#confirmPassword').val(null);
}

function delete_user(id) {
    
    $.ajax({
        url: '/user/delete',
        method: 'DELETE',
        data: {
            id: id
        },
        success: () => {
            show_alert('Usuário excluido com sucesso!', true);
            reload_users();
        },
        error: (jqXHR, textStatus, errorThrown) => {
            show_alert(jqXHR.responseJSON.message, false);
        }
    });
}

function reload_users () {

    $.ajax({
        url: '/user/findAll',
        method: 'GET',
        success: (data) => {
            $('#card-users-body').html(data);
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