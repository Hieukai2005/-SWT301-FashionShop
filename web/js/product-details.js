
// Thêm hàm này vào đầu file hoặc ngoài DOMContentLoaded
function changeMain(src) {
    document.getElementById('mainImage').src = src;
    // Highlight thumbnail đang được chọn
    document.querySelectorAll('.thumbnail').forEach(img => {
        img.classList.remove('active-thumb');
    });
    // Tìm thumbnail có src tương ứng và highlight
    document.querySelectorAll('.thumbnail').forEach(img => {
        if (img.src === src) {
            img.classList.add('active-thumb');
        }
    });
}

document.addEventListener('DOMContentLoaded', function () {

    // Chọn màu
    document.querySelectorAll('.color-swatch').forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.querySelectorAll('.color-swatch').forEach(b => b.classList.remove('selected'));
            btn.classList.add('selected');
        });
    });

    // Chọn size
    document.querySelectorAll('.size-btn').forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.querySelectorAll('.size-btn').forEach(b => b.classList.remove('selected'));
            btn.classList.add('selected');
        });
    });

    // Tăng giảm số lượng
    const qtyInput = document.getElementById('quantity');
    document.getElementById('increase').onclick = function () {
        qtyInput.value = parseInt(qtyInput.value) + 1;
    };
    document.getElementById('decrease').onclick = function () {
        if (parseInt(qtyInput.value) > 1) {
            qtyInput.value = parseInt(qtyInput.value) - 1;
        }
    };

    // *** XÓA đoạn thumbnail cũ dùng class .thumbnail ***
    // *** vì đã dùng onclick="changeMain()" trong JSP rồi ***

    // Auto highlight thumbnail đầu tiên khi load
    const firstThumb = document.querySelector('.thumbnail');
    if (firstThumb) {
        firstThumb.classList.add('active-thumb');
    }

    // Thêm sản phẩm vào giỏ
    document.querySelector('#ok').addEventListener('click', function () {
        document.querySelector('#selectOptionPopup').style.display = 'none';
    });

    document.querySelectorAll('.add-to-cart-btn').forEach(addCart => {
        addCart.addEventListener('click', function () {
            var checkForm = true;
            const params = new URLSearchParams(window.location.search);
            var productId = params.get('id');
            var productColor = params.get('color');
            var productSize = params.get('size');
            var productQuantityMax = parseInt(this.dataset.quantity, 10);
            var productQuantitySelected = parseInt(document.querySelector('#quantity').value, 10);
            var content = "";

            if (!productColor)
                content += "Chưa chọn Màu sắc ";
            if (!productSize)
                content += "Chưa chọn Size ";
            if (productQuantitySelected > productQuantityMax) {
                content += "Mua quá số lượng, Sản phẩm hiện chỉ còn lại " + productQuantityMax + " chiếc trong kho, ";
            }

            if (content) {
                checkForm = false;
                document.querySelector('#content').textContent = content;
                document.querySelector('#selectOptionPopup').style.display = 'flex';
            }

            if (checkForm) {
                window.location.href = 'cart?action=addtocart&id=' + productId
                    + '&color=' + productColor
                    + '&size=' + productSize
                    + '&quantity=' + productQuantitySelected;
            }
        });
    });
});
