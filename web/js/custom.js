

document.addEventListener("DOMContentLoaded", function () {
    // Chuyển panel đăng nhập/đăng ký/forgot
    document.querySelectorAll('.lnk-toggler').forEach(function (el) {
        el.addEventListener('click', function (e) {
            e.preventDefault();
            var panel = el.getAttribute('data-panel');
            document.querySelectorAll('.authfy-panel.active').forEach(function (p) {
                p.classList.remove('active');
            });
            var showPanel = document.querySelector(panel);
            if (showPanel)
                showPanel.classList.add('active');
        });
    });

    // Hiện/ẩn mật khẩu
    document.querySelectorAll('.pwd-toggle').forEach(function (toggle) {
        toggle.addEventListener('click', function (e) {
            e.preventDefault();
            toggle.classList.toggle('fa-eye-slash');
            toggle.classList.toggle('fa-eye');
            document.querySelectorAll('.pwdMask > .form-control').forEach(function (input) {
                if (toggle.classList.contains('fa-eye')) {
                    input.type = 'text';
                } else {
                    input.type = 'password';
                }
            });
        });
    });

    // Xử lý khi bấm quên mật khẩu (nếu có tab)
    var forgetLnk = document.getElementById('forget-lnk');
    if (forgetLnk) {
        forgetLnk.addEventListener('click', function () {
            var navTabs = document.querySelectorAll('.authfy-login .nav-tabs li');
            navTabs.forEach(function (li) {
                li.classList.remove('active');
            });
        });
    }
});

window.addEventListener('load', function () {
    // Loader
    document.querySelectorAll('.square-block').forEach(function (el) {
        el.style.display = 'none';
    });
    var preload = document.getElementById('preload-block');
    if (preload) {
        preload.style.transition = 'opacity 0.6s';
        preload.style.opacity = 0;
        setTimeout(function () {
            if (preload.parentNode)
                preload.parentNode.removeChild(preload);
        }, 600);
    }
});