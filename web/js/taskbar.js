/* 
 * @author dotha
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
(function () {
    var contextPathEl = document.querySelector("input[name='contextPath']");
    var contextPath = contextPathEl ? contextPathEl.value : "";

    var logoSection = document.querySelector('.logo-sectionDB');
    if (logoSection) {
        logoSection.addEventListener('click', function (e) {
            window.location.assign(contextPath + "/home");
        });
    }

    var currentUrl = window.location.href;
    var currentPath = window.location.pathname;
    var navLinks = document.querySelectorAll('.nav-linkDB');
    var activeFound = false;

    navLinks.forEach(function (link) {
        link.classList.remove('activeDB');
    });

    navLinks.forEach(function (link) {
        // link.href returns the absolute URL like "http://localhost:9999/fashionShop/generaladmin"
        var linkUrl = link.href;

        // We ensure we don't accidentally match the base path alone with other paths
        // For example, if linkUrl is exactly "http://localhost/fashionShop/", it shouldn't match "http://localhost/fashionShop/productadmin"
        // But here the links are specific like /generaladmin, /productadmin, etc.
        if (linkUrl && linkUrl.length > 0) {
            // Check if current URL starts with this link's URL (useful for sub-pages like /productadmin/edit)
            // or if it matches exactly.
            if (currentUrl.startsWith(linkUrl) || currentUrl === linkUrl) {
                link.classList.add('activeDB');
                activeFound = true;
            }
        }
    });

    // Fallback: If we are at the root of admin or generaladmin and nothing was found
    if (!activeFound && (currentPath.endsWith("/admin") || currentPath.endsWith("/admin/"))) {
        var tongquan = document.getElementById('tongquan');
        if (tongquan) {
            tongquan.classList.add('activeDB');
        }
    }
})();