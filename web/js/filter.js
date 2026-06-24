(function () {
    document.addEventListener("DOMContentLoaded", function () {
        // Accordion filter toggle
        document.querySelectorAll('.filter-section h3').forEach(function (header) {
            header.addEventListener('click', function (e) {
                // Không toggle nếu click vào input
                if (e.target.tagName === 'INPUT' || e.target.tagName === 'LABEL') return;
                const section = header.closest('.filter-section');
                const content = section.querySelector('.filter-content');
                const icon = header.querySelector('.toggle-icon i');
                if (content) {
                    content.classList.toggle('collapsed');
                    header.querySelector('.toggle-icon').classList.toggle('active');
                }
            });
        });
    });
})();
(function () {
    const SIDEBAR_TO_IDS = {
        "Áo khoác"      : [3],
        "Đồ nỉ"         : [2],
        "Áo len"        : [4],
        "Mặc nhà"       : [],
        "Quần"          : [6, 7, 8, 9],
        "Váy"           : [],
        "Áo phông, polo": [1, 5],
        "Khác"          : []
    };

    const ALL_KNOWN_IDS = Object.values(SIDEBAR_TO_IDS).flat();

    document.addEventListener("DOMContentLoaded", function () {
        const allCards = Array.from(document.querySelectorAll(".product-card"));

        function getCheckedTexts(selector) {
            const result = [];
            document.querySelectorAll(selector).forEach(input => {
                if (input.checked) result.push(input.parentElement.textContent.trim());
            });
            return result;
        }

        function applyFilters() {
            const checkedCats      = getCheckedTexts(".filter-section:nth-child(1) input[type='checkbox']");
            const checkedDiscounts = getCheckedTexts(".filter-section:nth-child(2) input[type='checkbox']");

            let visibleCount = 0;

            allCards.forEach(card => {
                const wrapper  = card.closest("a") || card;
                const catId    = parseInt(card.dataset.categoryId || "0");
                const discount = parseInt(card.dataset.discount   || "0");

                // Lọc danh mục
                let passCategory = true;
                if (checkedCats.length > 0) {
                    passCategory = checkedCats.some(label => {
                        if (label === "Khác") return !ALL_KNOWN_IDS.includes(catId);
                        return (SIDEBAR_TO_IDS[label] || []).includes(catId);
                    });
                }

                // Lọc phần trăm giảm
                let passDiscount = true;
                if (checkedDiscounts.length > 0) {
                    passDiscount = checkedDiscounts.some(range => {
                        if (range === "< 20%")     return discount > 0  && discount < 20;
                        if (range === "20% - 30%") return discount >= 20 && discount < 30;
                        if (range === "30% - 40%") return discount >= 30 && discount < 40;
                        if (range === "40% - 50%") return discount >= 40 && discount < 50;
                        if (range === "50%+")      return discount >= 50;
                        return false;
                    });
                }

                const visible = passCategory && passDiscount;
                wrapper.style.display = visible ? "" : "none";
                if (visible) visibleCount++;
            });

            // Cập nhật số sản phẩm
            const countEl = document.querySelector(".product-count");
            if (countEl) {
                const anyChecked = checkedCats.length || checkedDiscounts.length;
                countEl.textContent = (anyChecked ? visibleCount : allCards.length) + " Sản phẩm";
            }
        }

        // Gắn sự kiện cho tất cả checkbox
        document.querySelectorAll(".filter-section input[type='checkbox']")
                .forEach(cb => cb.addEventListener("change", applyFilters));

        // Nút "Bỏ chọn"
        const clearBtn = document.querySelector(".clear-filter");
        if (clearBtn) {
            clearBtn.addEventListener("click", function () {
                document.querySelectorAll(".filter-section:nth-child(1) input[type='checkbox']")
                        .forEach(cb => { cb.checked = false; });
                applyFilters();
            });
        }
    });
})();


