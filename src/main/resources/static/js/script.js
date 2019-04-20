$( document ).ready(function() {
    $('.slide-image').each(function() {
        if($(this).attr('src') == null){
            var num = Math.floor(Math.random() * 10 + 1);
            $(this).attr('src', 'images/' + num + '.jpg');
        }
    });
});

//delete card and go to next
var $carousel = $('#carouselFade');
$('.carousel-control-prev').click(function() {
    var ActiveElement = $carousel.find('.carousel-item.active');
    saveRejectedQuote(ActiveElement);
    ActiveElement.remove();
    if($carousel.find('.carousel-item').length == 0){
        getQuotes();
    } else {
        var NextElement = $carousel.find('.carousel-item').first();
        NextElement.addClass('active');
    }
});


//go to next
$('.carousel-control-next').click(function() {
    var ActiveElement = $carousel.find('.carousel-item.active');
    ActiveElement.remove();

    if($carousel.find('.carousel-item').length == 0){
        getQuotes();
    } else {
        var NextElement = $carousel.find('.carousel-item').first();
        NextElement.addClass('active');
    }
});

//get quotes and add to the carousel
function getQuotes() {
    $.ajax({
        type: 'GET',
        url: '/quotes/getQuotes',
        success: function(data) {
            if(data.length > 0) {
                for (i = 0; i <= data.length; i++){
                    var num = Math.floor(Math.random() * 10 + 1);
                    if (i == 0) {
                        $('<div class="carousel-item active"><img class="d-block w-100 slide-image" src="images/' + num + '.jpg"><div class="carousel-caption">' +
                            '<p class="quote-quote">' + data[i].quote + '</p><p class="quote-id" hidden>' + data[i]._id + '</p></div></div>').appendTo('.carousel-inner');
                    } else {
                        $('<div class="carousel-item"><img class="d-block w-100 slide-image" src="images/' + num + '.jpg"><div class="carousel-caption">' +
                            '<p class="quote-quote">' + data[i].quote + '</p><p class="quote-id" hidden>' + data[i]._id + '</p></div></div>').appendTo('.carousel-inner');
                    }
                }
            } else {
                $('<div class="carousel-item active"><img class="d-block w-100 slide-image" src="images/11.jpg"><div class="carousel-caption">' +
                    '<p style="color: #bf0000">Ops! You are out of quotes! Go to settings and reset your rejected quotes!</p></div></div>').appendTo('.carousel-inner');
                $('.like-dislike-control').hide();
            }
        }
    });
}

function saveRejectedQuote(el) {
    var rejectedQuote = {};
    rejectedQuote["id"] = el[0].getElementsByClassName('carousel-caption')[0].getElementsByClassName('quote-id')[0].innerText;
    rejectedQuote["quote"] = el[0].getElementsByClassName('carousel-caption')[0].getElementsByClassName('quote-quote')[0].innerText;
    $.ajax({
        type: 'POST',
        contentType : "application/json",
        url: '/quotes/saveRejectedQuote',
        data: JSON.stringify(rejectedQuote),
        dataType: 'json',
        success: function(data) {
            console.log("Rejected quote saved.");
        },
        error: function (xhr) {
            console.log(xhr.responseText);
        }
    });
}

$('#reset').click(function(){
    $('#reset-success').hide();
    $.ajax({
        type: 'DELETE',
        url: '/quotes/deleteAllRejectedQuote',
        success: function(data) {
            $('#reset-success').show();
        },
        error: function (xhr) {
            console.log(xhr.responseText);
        }
    });
});

