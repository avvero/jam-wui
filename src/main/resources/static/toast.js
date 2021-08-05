function showToast(message) {
    $('.toast-body').html(message)
    $('.toast').toast({
        'autohide': false
    });
    $('.toast').toast('show');
}
function hideToast() {
    $('.toast').toast('hide')
}
window.onbeforeunload = function(e){
    setTimeout(() => showToast('It takes time to build graph for big issues'), 5000);
    setTimeout(() => hideToast(), 19000);
    setTimeout(() => showToast('It takes a lot of time to build graph for really big issues'), 20000);
    setTimeout(() => hideToast(), 29000);
    setTimeout(() => showToast('So, we a keep waiting, let\'s be patient for now and I will cache it next time for you'), 30000);
};