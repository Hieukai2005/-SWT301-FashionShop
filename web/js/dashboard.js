/**
 * HoaNK - xu li ajax gui ve servlet va ghi de du lieu, dong mo popup
 */
//
let overlay = document.querySelector(".all");
// mở
function openPopup(typePopup) {
    let action = document.querySelector(typePopup);
    action.classList.add("openPopup");
    overlay.classList.add("cover");
}
// đóng 
function closePopup(typePopup) {
    let action = document.querySelector(typePopup);
    action.classList.remove("openPopup");
    overlay.classList.remove("cover");
}

// AJAX
function showOrderItems(orderId) {
    console.log("Fetching order details for ID:", orderId);
    fetch("?oid=" + orderId)
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.text();
        })
        .then(data => {
            document.querySelector(".frame_overflow").innerHTML = data;
            openPopup('.popupOrder');
        })
        .catch(error => console.error('Lỗi khi tải chi tiết đơn hàng:', error));

}



