let isSale = document.querySelectorAll(".is_sale");
let discount = document.querySelector(".dis");
//
isSale.forEach(radio => {
    radio.addEventListener("change", function () {
        if (radio.value === "1") {
            discount.classList.add("vis");
        } else if (radio.value === "0") {
            discount.classList.remove("vis")
        }
    })
})

// CÁC POPUP cho thông báo xóa sủa thành công, thất bại
// data: variantId/imageId
// action: khối div 
function openConfirmPopup(typePopup, data, productId) {
    let action = document.querySelector(typePopup);
    //
    let dataId = action.querySelector(".data_id");
    let idNum = action.querySelector(".id_num");
    let pid = action.querySelector(".pidDelete");
    dataId.value = data;
    idNum.innerHTML = data;
    pid.value = productId;

    action.classList.add("openPopup");
    cover.classList.add("cover");
}
let cover = document.querySelector(".all"); // lớp phủ khi mở pop up
function closeConfirmPopup(typePopup) {
    let action = document.querySelector(typePopup);
    cover.classList.remove("cover");
    action.classList.remove("openPopup");
    if (typePopup === ".updatePopup") {
        let divUpdate = document.querySelector(typePopup);
        let errorMessages = divUpdate.querySelectorAll(".error-info"); 
        errorMessages.forEach(span => {
            span.innerHTML = "";
        });
    }
}

function openPopup(typePopup) {
    let action = document.querySelector(typePopup);
//                action.classList.remove("closePopup");
    action.classList.add("openPopup");
    cover.classList.add("cover");
}

// Xử lí phần js bắt các ngoại lệ 
function submitCheck(typeForm) {
    let formUpdate = document.querySelector(typeForm);
    formUpdate.addEventListener("submit", function (e) {
        // khởi tạo cờ check
        let validData = true;
        // lấy ra ô nhập
        if (typeForm === "#updateForm") { // với form update sản phẩm chính
            let name = formUpdate.querySelector("input[name='name']");
            let price = formUpdate.querySelector("input[name='price']");
            let image = formUpdate.querySelector("input[name='image']");
            let isSale = formUpdate.querySelector("input[name=is_sale]:checked");
            let discount = formUpdate.querySelector("input[name='discount']");
            let description = formUpdate.querySelector("textarea[name='description']");

            // tên
            if (!checkIsEmpty(name))
            {
                validData = false;
                showError(name, "* Vui lòng nhập tên sản phẩm");
            } else {
                deleteError(name);
            }
            // tiền
            if (!checkIsEmpty(price))
            {
                validData = false;
                showError(price, "* Vui lòng nhập giá sản phẩm");
            } else if (Number(price.value) === 0) {
                validData = false;
                showError(price, "* Vui lòng nhập giá > 0");
            } else {
                deleteError(price);
            }
            // ảnh
            if (!checkIsEmpty(image))
            {
                validData = false;
                showError(image, "* Vui lòng nhập ảnh sản phẩm");
            } else {
                deleteError(image);
            }
            // mô tả
            if (!checkIsEmpty(description)) {
                validData = false;
                showError(description, "* Vui lòng nhập mô tả sản phẩm");
            } else {
                deleteError(description);
            }
            // giảm giá nếu đang bật mà trống hoặc bằng 0 thì báo lỗi 
            if (isSale.value === "1" && !checkIsEmpty(discount)) {
                validData = false;
                showError(discount, "* Vui lòng nhập mức giảm giá(%)");
            } else if (isSale.value === "1" && Number(discount.value) === 0) {
                validData = false;
                showError(discount, "* Vui lòng nhập mức giảm giá(%) > 0");
            } else {
                deleteError(discount);
            }
        } else if (typeForm === "#addVariantForm") { // với form thêm biến thể
            let quantity = formUpdate.querySelector("input[name='quantity']");
            let image = formUpdate.querySelector("input[name='image_main']");

            // số lượng
            if (!checkIsEmpty(quantity)) {
                validData = false;
                showError(quantity, "* Vui lòng nhập số lượng biến thể");
            } else if (Number(quantity.value) === 0) {
                validData = false;
                showError(quantity, "* Vui lòng nhập số lượng > 0");
            } else {
                deleteError(quantity);
            }
            // anh bien the
            if (!checkIsEmpty(image))
            {
                validData = false;
                showError(image, "* Vui lòng nhập ảnh biến thể sản phẩm");
            } else {
                deleteError(image);
            }
        } else if (typeForm === "#updateVariantForm") { // với form update variant
            let quantity = formUpdate.querySelector("input[name='quantity']");
            let image = formUpdate.querySelector("input[name='image_main']");
            // so luong
            if (!checkIsEmpty(quantity)) {
                validData = false;
                showError(quantity, "* Vui lòng nhập số lượng biến thể");
            } else if (Number(quantity.value) === 0) {
                validData = false;
                showError(quantity, "* Vui lòng nhập số lượng > 0");
            } else {
                deleteError(quantity);
            }
            // anh bien the
            if (!checkIsEmpty(image))
            {
                validData = false;
                showError(image, "* Vui lòng nhập ảnh biến thể sản phẩm");
            } else {
                deleteError(image);
            }
        } else if (typeForm === "#addImagesForm") { // với form thêm ảnh chi tiết
            let imageDetails = formUpdate.querySelector("input[name='image_details']");

            //  ảnh phụ
            if (!checkIsEmpty(imageDetails)) {
                validData = false;
                showError(imageDetails, "* Vui lòng nhập link ảnh chi tiết!");
            } else {
                deleteError(imageDetails);
            }
        }
        // kiểm tra xem cho gửi form hay ko
        if (validData === false) {
            e.preventDefault(); // chặn sự kiện gửi form
        }
    });
}
// check rỗng ô nhập
function checkIsEmpty(input) {
    let inputData = input.value.trim();
    if (inputData === "") {
        return false;
    } else {
        return true;
    }
}
// In lỗi
function showError(input, message) {
    input.classList.add("error-data");
    input.nextElementSibling.innerHTML = message;
}
// xóa lỗi 
function deleteError(input) {
    input.classList.remove("error-data");
    input.nextElementSibling.innerHTML = '';
}
// gọi hàm check form
submitCheck("#updateForm");
submitCheck("#addVariantForm");
submitCheck("#addImagesForm");
submitCheck("#updateVariantForm");

// hàm lấy data chèn vào popup sửa biến thể
function editProductVariant(data, typePopup) {
    let dataForm = data.closest("#operation");
    let size = dataForm.dataset.size;
    let color = dataForm.dataset.color;
    let quantity = dataForm.dataset.quantity;
    let image = dataForm.dataset.image;
    let variantId = dataForm.dataset.pvid;
    let h3Variant = document.querySelector("#idVariant");
    h3Variant.innerHTML = "Sửa biến thể";
    //
    let formUpdate = document.querySelector("#updateVariantForm");
    formUpdate.querySelector("input[name='size']").value = size;
    formUpdate.querySelector("input[name='color']").value = color;
    formUpdate.querySelector("input[name='quantity']").value = quantity;
    formUpdate.querySelector("input[name='image_main']").value = image;
    formUpdate.querySelector("input[name='pvid']").value = variantId;
    h3Variant.innerHTML = h3Variant.textContent + " #" + variantId;
    //mở popup
    openPopup(typePopup);
}