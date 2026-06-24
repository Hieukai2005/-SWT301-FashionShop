function openPopup(selector) {
    const popup = document.querySelector(selector);
    const overlay = document.querySelector(".all");

    if (popup) popup.classList.add("openPopup");
    if (overlay) overlay.classList.add("cover");
}

function closePopup(selector) {
    const popup = document.querySelector(selector);
    const overlay = document.querySelector(".all");

    if (popup) popup.classList.remove("openPopup");
    if (overlay) overlay.classList.remove("cover");
}

window.closePopup = closePopup;
window.openPopup = openPopup;

document.addEventListener("DOMContentLoaded", function () {

    window.showOrderItems1 = function (orderId) {
        fetch("ordermanagement?oid=" + orderId)
            .then(res => res.text())
            .then(data => {
                document.querySelector(".frame_overflow").innerHTML = data;
                openPopup(".popupOrder");
            })
            .catch(err => console.error(err));
    };

    // Đổi màu icon nút select theo trạng thái hiện tại
    window.statusColors = {
        "Chờ xác nhận": { bg: "#fff3cd", icon: "%23856404" },
        "Đang giao":    { bg: "#cfe2ff", icon: "%23084298" },
        "Hoàn thành":   { bg: "#d4edda", icon: "%23155724" },
        "Đã hủy":       { bg: "#f8d7da", icon: "%23721c24" }
    };

    window.updateSelectColor = function(sel) {
        var val = sel.value;
        var c = window.statusColors[val];
        if (c) {
            sel.style.backgroundColor = c.bg;
            sel.style.backgroundImage = "url(\"data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' width='16' height='16'%3E%3Cpath fill='" + c.icon + "' d='M3 18h18v-2H3v2zm0-5h18v-2H3v2zm0-7v2h18V6H3z'/%3E%3C/svg%3E\")";
        } else {
            sel.style.backgroundColor = "#fff3e0";
            sel.style.backgroundImage = "";
        }
    }

    document.querySelectorAll(".status-selectDH").forEach(function (sel) {
        window.updateSelectColor(sel);
        sel.addEventListener("change", function () {
            window.updateSelectColor(this);
        });
    });

});

window.confirmStatusChange = function(selectElement, originalValue) {
    var newValue = selectElement.value;
    Swal.fire({
        title: '<div style="font-size: 20px; font-weight: normal; color: #333;">Thông báo</div>',
        html: `<div style="font-size: 16px; margin-top: 5px; color: #333;">Bạn có muốn đổi trạng thái đơn hàng sang <b>${newValue}</b> không?</div>`,
        showCancelButton: true,
        confirmButtonColor: '#8adaa0',
        cancelButtonColor: '#ff6b6b',
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy bỏ',
        width: '400px',
        background: '#f8f9fa',
        customClass: {
            confirmButton: 'swal2-confirm-custom',
            cancelButton: 'swal2-cancel-custom',
            popup: 'swal2-popup-custom'
        }
    }).then((result) => {
        if (result.isConfirmed) {
            selectElement.form.submit();
        } else {
            selectElement.value = originalValue;
            window.updateSelectColor(selectElement);
        }
    });
};

function openEditModal(button) {
    document.getElementById("orderIdED").value = button.dataset.id;
    document.getElementById("customerNameED").value = button.dataset.name;
    document.getElementById("phoneED").value = button.dataset.phone;
    document.getElementById("addressED").value = button.dataset.address;
    document.getElementById("statusED").value = button.dataset.status;

    document.getElementById("editOrderModal").style.display = "flex";
}

function closeEditModal() {
    document.getElementById("editOrderModal").style.display = "none";
}
