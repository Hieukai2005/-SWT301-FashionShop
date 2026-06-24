// POPUP THÊM,SỬA SẢN PHẨM (truyền kiểu form và kiểu popip)
let cover = document.querySelector(".all"); // lớp phủ khi mở pop up
function openPopup(typePopup) {
    let action = document.querySelector(typePopup);
//                action.classList.remove("closePopup");
    action.classList.add("openPopup");
    cover.classList.add("cover");
}

// đóng popup
function closePopup(typeForm, typePopup) {
    let action = document.querySelector(typePopup);
    cover.classList.remove("cover");
    action.classList.remove("openPopup");
    // reset hết dữ liệu của form
    let form = document.querySelector(typeForm);
    form.reset();
    // xóa class viền đỏ
    let inputs = form.querySelectorAll(".error-data");
    inputs.forEach(input => {
        input.classList.remove("error-data");
    });
    // xóa thống báo lỗi 
    let errorMessages = form.querySelectorAll("#error-info"); // Hoặc class của bạn
    errorMessages.forEach(span => {
        span.innerHTML = "";
    });
    //
    discountSale.style.display = 'block';
}

// CÁC POPUP cho thông báo xóa sủa thành công, thất bại
function openConfirmPopup(typePopup, productId, productName) {
    let action = document.querySelector(typePopup);
    let pid = document.querySelector("#product_id");
    let pname = document.querySelector("#product_name");
    pid.value = productId;
    pname.innerHTML = productName;
    action.classList.add("openPopup");
    cover.classList.add("cover");
}
function closeConfirmPopup(typePopup) {
    let action = document.querySelector(typePopup);
    cover.classList.remove("cover");
    action.classList.remove("openPopup");
}

// MỞ GIẢM GIÁ (PHẦN THÊM SẢN PHẨM, SỬA SẢN PHẨM)
// ấn vào ô bật giảm giá thì mới hiên caissa nhập số % mốn giảm
let isSale = document.querySelectorAll("input[name='is_sale']");
let discountSale = document.querySelector("#discount-sale");
// neu thay doi lau chon thi js se load cai nay 
isSale.forEach(radio => {
    radio.addEventListener("change", function () {
        let valSale = radio.value; // lay gia tri cua option do
        if (valSale === "1") {
            discountSale.style.display = 'block';
        } else if (valSale === "0") {
            discountSale.style.display = 'none';
        }
    });
});

// XỦ LÍ NHẬP ĐÚNG DỮ LIỆU CHO POPUP THÊM SỬA (truyền kiểu form và nút án)
// bắt tất cả các thẻ thuộc tính trong form và kiểm tra điều kiện
function submitCheck(typeForm, typeBtn) {
    let btnForm = document.querySelector(typeBtn); // nút thêm sản phẩm
    let form = document.querySelector(typeForm);

    form.addEventListener("submit", function (e) { // bắt sự kiện gửi form
        // kiểu nhập chuỗi( ko rỗng)
        let name = form.querySelector('input[name="name"]');
        let image = form.querySelector('input[name="image"]');
        let description = form.querySelector('textarea[name="description"]');

        // kiểu nhập số (kiểm rỗng , parse > 0 )
        let price = form.querySelector('input[name="price"]');
        let discount = form.querySelector('input[name="discount"]');
        let quantity = form.querySelector('input[name="quantity"]');
        // kiểu select thì chỉ cần kiểm tra rỗn = 0 có value
        let gender = form.querySelector('select[name="gender"]');
        let category = form.querySelector('select[name="category"]');
        let size = form.querySelector('select[name="size"]');
        let color = form.querySelector('select[name="color"]');
        //
        let isSaleChecked = form.querySelector("input[name='is_sale']:checked");

        // Các lỗi sẽ bắt với string
        let validData = true; // khởi tạo check lỗi
        // 1. tên sản phẩm
        if (checkEmptyString(name) == false) {
            validData = false;
            showError(name, "* Vui lòng nhập tên sản phẩm!");
        } else {
            deleteError(name);
        }

        // 2. ảnh
        if (checkEmptyString(image) == false) {
            validData = false;
            showError(image, "* Vui lòng nhập ảnh sản phẩm!");
        } else {
            deleteError(image);
        }
        // 3. miêu tả
        if (checkEmptyString(description) == false) {
            validData = false;
            showError(description, "* Vui lòng nhập mô tả sản phẩm!");
        } else {
            deleteError(description);
        }
        // 4. gender
        if (checkEmptyString(gender) == false) {
            validData = false;
            showError(gender, "* Vui lòng chọn giới tính phù hợp!");
        } else {

            deleteError(gender);
        }
        // 5. danh mục
        if (checkEmptyString(category) == false) {
            validData = false;
            showError(category, "* Vui lòng chọn danh mục sản phẩm!");
        } else {

            deleteError(category);
        }
        // 6. kích thước
        if (checkEmptyString(size) == false) {
            validData = false;
            showError(size, "* Vui lòng chọn kích thước sản phẩm!");
        } else {

            deleteError(size);
        }
        // 7. màu sắc
        if (checkEmptyString(color) == false) {
            validData = false;
            showError(color, "* Vui lòng chọn màu sắc sản phẩm!");
        } else {
            deleteError(color);
        }
        // 8. giá tiền
        if (checkEmptyString(price) == false) {
            validData = false;
            showError(price, "* Vui lòng nhập giá tiền sản phẩm!");
        } else if (checkNumberString(price) == false) {
            validData = false;
            showError(price, "* Giá tiền sản phẩm phải là số!");
        } else if (parseFloat(price.value) <= 0) {
            validData = false;
            showError(price, "* Vui lòng nhập giá tiền sản phẩm > 0!");
        } else {

            deleteError(price);
        }
        // 9. số lượng
        if (checkEmptyString(quantity) == false) {
            validData = false;
            showError(quantity, "* Vui lòng nhập số lượng sản phẩm!");
        } else if (checkNumberString(quantity) == false) {
            validData = false;
            showError(quantity, "* Số lượng sản phẩm phải là số!");
        } else if (parseInt(quantity.value) <= 0) {
            validData = false;
            showError(quantity, "* Vui lòng nhập số lượng sản phẩm > 0!");
        } else {

            deleteError(quantity);
        }
        // 11. discount
        if (isSaleChecked.value === '1') {
            if (checkEmptyString(discount) == false) {
                validData = false;
                showError(discount, "* Vui lòng nhập % giảm giá sản phẩm!");
            } else if (checkNumberString(discount) == false) {
                validData = false;
                showError(discount, "* % giảm giá sản phẩm phải là số!");
            } else if (parseInt(discount.value) <= 0 || parseInt(discount.value) > 100) {
                validData = false;
                showError(discount, "* Vui lòng nhập %: 0 < discount <=100!");
            } else {
                deleteError(discount);
            }
        }
        // nếu tất cả hợp lệ thì cho gửi form ko thì ngăn lại
        if (validData === false) {
            e.preventDefault();
            btnForm.nextElementSibling.innerHTML = "* Vui lòng nhập dữ liệu hợp lệ!";
            btnForm.nextElementSibling.style.color = 'red';
        } else {
            btnForm.nextElementSibling.innerHTML = "Đang xử lí...";
            btnForm.nextElementSibling.style.color = 'blue';
        }
    });
}
// Xử lí với data nhập kiểu String 
function checkEmptyString(input) {
    let inputData = input.value.trim();
    if (inputData === "") {
        return false;
    } else {
        return true;
    }
}
// Xử lí với data dạng số
function checkNumberString(input) {
    let inputData = input.value;
    if (!isNaN(inputData)) { // là số 
        return true;
    } else {
        return false;
    }
}
// Xử lí với data là số và >< = 0
function checkNumberData(input) {
    let inputData = input.value;
    if (parseFloat(inputData) <= 0) {
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
// form của thêm sản phẩm
submitCheck("#addForm", "#addBtn");
//submitCheck("#updateForm", "#updateBtn");




