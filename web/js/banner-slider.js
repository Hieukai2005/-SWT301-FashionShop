// Banner slider tự động chuyển ảnh mỗi  giây
(function () {
    const imgs = document.querySelectorAll('.main-banner-slider .main-banner-img');
    let idx = 0;
    let prevIdx = 0;
    function show(idxShow) {
        imgs.forEach((img, i) => {
            img.classList.toggle('active', i === idxShow);
        });
    }
    function next() {
        prevIdx = idx;
        idx = (idx + 1) % imgs.length;
        show(idx);
    }
    if (imgs.length > 1) {
        setInterval(next, 3000);
    }
    show(idx);
})();
