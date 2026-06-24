<%-- Document : user-profile Created on : Jan 26, 2026, 8:53:28 PM Author : dotha --%>

    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <title>User Profile</title>
                    <link rel="stylesheet"
                        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-profile.css">
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat-ai.css">
                </head>

                <body>
                    <jsp:include page="/header.jsp"></jsp:include>
                    <div class="containerUS">
                        <!-- Sidebar -->
                        <div class="sidebarUS">
                            <div class="${user.getTier()}">
                                <div class="user-greetingUS">Xin chào!</div>
                                <div class="user-phoneUS">${user.email}</div>
                                <div class="user-tierUS">
                                    <span class="tier-labelUS">Hạng thẻ:</span>
                                    <span class="tier-valueUS">${user.getTier()}</span>
                                    <span class="tier-labelUS">Đã Đặt:</span>
                                    <span class="tier-valueUS">${user.totalOrders == -1 ? 0 : user.totalOrders}</span>
                                </div>
                                <c:if test="${neededAmountTopromoted != null}">
                                    <div class="points-infoUS">Cần chi tiêu
                                        <fmt:formatNumber value="${neededAmountTopromoted}"></fmt:formatNumber>đ để
                                        thăng lên hạng ${nextRank}
                                    </div>
                                </c:if>
                                <div class="points-infoUS">Tổng chi tiêu:
                                    <fmt:formatNumber value="${totalSpending}"></fmt:formatNumber>đ
                                </div>
                            </div>

                            <ul class="menu-listUS">
                                <li class="menu-itemUS">
                                    <a href="#" class="menu-linkUS">
                                        <div class="menu-iconUS">
                                            <i class="fas fa-user"></i>
                                        </div>
                                        <div class="menu-contentUS">
                                            <div class="menu-titleUS">
                                                Thông tin tài khoản
                                                <span class="badgeUS badge-redUS">Cập nhật ngay!</span>
                                            </div>
                                            <div class="menu-subtitleUS">Cập nhật thông tin tài khoản</div>
                                        </div>
                                        <span class="menu-arrowUS">
                                            <i class="fas fa-arrow-right"></i>
                                        </span>
                                    </a>
                                </li>

                                <li class="menu-itemUS">
                                    <a href="${pageContext.request.contextPath}/userinfomation?action=viewVouchers"
                                        class="menu-linkUS">
                                        <div class="menu-iconUS">
                                            <i class="fas fa-ticket-alt"></i>
                                        </div>
                                        <div class="menu-contentUS">
                                            <div class="menu-titleUS">
                                                Mã ưu đãi
                                                <span class="badgeUS badge-numberUS">${numberVocher == -1 ? 0 :
                                                    numberVocher}</span>
                                            </div>
                                            <div class="menu-subtitleUS">Quản lý mã dành riêng cho bạn</div>
                                        </div>
                                        <span class="menu-arrowUS">
                                            <i class="fas fa-arrow-right"></i>
                                        </span>
                                    </a>
                                </li>

                                <li class="menu-itemUS">
                                    <a href="${pageContext.request.contextPath}/order" class="menu-linkUS">
                                        <div class="menu-iconUS">
                                            <i class="fas fa-shopping-bag"></i>
                                        </div>
                                        <div class="menu-contentUS">
                                            <div class="menu-titleUS">Đơn hàng</div>
                                            <div class="menu-subtitleUS">Tra cứu thông tin vận chuyển</div>
                                        </div>
                                        <span class="menu-arrowUS">
                                            <i class="fas fa-arrow-right"></i>
                                        </span>
                                    </a>
                                </li>

                                <c:if test="${sessionScope.customer.roleId == 1}">
                                    <li class="menu-itemUS">
                                        <a href="${pageContext.request.contextPath}/generaladmin" class="menu-linkUS">
                                            <div class="menu-iconUS">
                                                <i class="fas fa-user-cog"></i>
                                            </div>
                                            <div class="menu-contentUS">
                                                <div class="menu-titleUS">Admin</div>
                                                <div class="menu-subtitleUS">Quản lí sản phẩm, đơn hàng, khách hàng,...
                                                </div>
                                            </div>
                                            <span class="menu-arrowUS">
                                                <i class="fas fa-arrow-right"></i>
                                            </span>
                                        </a>
                                    </li>
                                </c:if>

                                <li class="menu-itemUS logout-itemUS">
                                    <a href="${pageContext.request.contextPath}/dispatchcontroller?action=logout"
                                        class="menu-linkUS">
                                        <div class="menu-iconUS">
                                            <i class="fas fa-sign-out-alt"></i>
                                        </div>
                                        <div class="menu-contentUS">
                                            <div class="menu-titleUS">Đăng xuất</div>
                                        </div>
                                        <span class="menu-arrowUS">
                                            <i class="fas fa-arrow-right"></i>
                                        </span>
                                    </a>
                                </li>
                            </ul>
                        </div>

                        <!-- Main Content -->
                        <!-- ── VIEW MODE ── -->
                        <div class="view-sectionEDU" id="viewSectionEDU">
                            <h1 class="page-titleUS">Thông tin tài khoản</h1>
                            <p class="page-descriptionUS">Cập nhật thông tin cá nhân để hoàn tất quy trình đăng ký thành
                                viên Vinafa.</p>

                            <div class="info-sectionUS">
                                <div class="info-rowUS">
                                    <div class="info-labelUS">Họ và tên</div>
                                    <div class="info-valueUS" id="view-fullnameEDU">${user.fullName == null ? '-' :
                                        user.fullName}</div>
                                </div>
                                <div class="info-rowUS">
                                    <div class="info-labelUS">Số điện thoại</div>
                                    <div class="info-valueUS" id="view-phoneEDU">${user.phoneNumber == null ? '-' :
                                        user.phoneNumber}</div>
                                </div>
                                <div class="info-rowUS">
                                    <div class="info-labelUS">Email</div>
                                    <div class="info-valueUS" id="view-emailEDU">${user.email == null ? '-' :
                                        user.email}</div>
                                </div>
                                <div class="info-rowUS">
                                    <div class="info-labelUS">Địa chỉ</div>
                                    <div class="info-valueUS" id="view-addressEDU">${user.address == null ? '-' :
                                        user.address}</div>
                                </div>
                                <div class="info-rowUS">
                                    <div class="info-labelUS">Tạo ngày</div>
                                    <div class="info-valueUS">${user.createdAt == null ? '-' : user.createdAt}</div>
                                </div>
                            </div>

                            <div class="notice-sectionUS">
                                <div class="notice-itemUS">Email chỉ được tạo <span class="notice-highlightUS">1 lần duy
                                        nhất và không thể sửa</span>.</div>
                                <div class="notice-itemUS">Vui lòng liên hệ tổng đài <span
                                        class="notice-highlightUS">CSKH 03769 23299</span> để được hỗ trợ thêm.</div>
                            </div>

                            <button class="edit-buttonUS" onclick="openEdit()">
                                <i class="fas fa-edit"></i>
                                <span>Sửa thông tin</span>
                            </button>
                        </div>

                        <!-- ── EDIT MODE ── -->
                        <div class="edit-panelEDU" id="editPanelEDU">
                            <div class="edit-headerEDU">
                                <button class="back-btnEDU" onclick="closeEdit()" title="Quay lại">
                                    <i class="fas fa-arrow-left"></i>
                                </button>
                                <h2 class="edit-titleEDU">Chỉnh sửa thông tin</h2>
                            </div>

                            <form id="profileFormEDU" novalidate action="userinfomation" method="post">
                                <div class="form-gridEDU">
                                    <div class="form-groupEDU">
                                        <label class="form-labelEDU" for="inp-fullnameEDU">Họ và tên <span
                                                style="color:#e53e3e">*</span></label>
                                        <input class="form-inputEDU" name="fullname" id="inp-fullnameEDU" type="text"
                                            placeholder="Nhập họ và tên" value="${user.fullName}">
                                        <span class="field-errorEDU" id="err-fullnameEDU">Vui lòng nhập họ và
                                            tên.</span>
                                    </div>

                                    <div class="form-groupEDU">
                                        <label class="form-labelEDU" for="inp-phoneEDU">Số điện thoại <span
                                                style="color:#e53e3e">*</span></label>
                                        <input class="form-inputEDU" name="phonenumber" id="inp-phoneEDU" type="tel"
                                            placeholder="Nhập số điện thoại" value="${user.phoneNumber}">
                                        <span class="field-errorEDU" id="err-phoneEDU">Số điện thoại không hợp lệ (10
                                            số).</span>
                                    </div>

                                    <div class="form-groupEDU full">
                                        <label class="form-labelEDU" for="inp-emailEDU">Email</label>
                                        <input class="form-inputEDU" " id=" inp-emailEDU" type="email"
                                            value="${user.email}" disabled>
                                        <span class="input-hintEDU lock"><i class="fas fa-lock"
                                                style="font-size:11px;margin-right:4px"></i>Email không thể thay
                                            đổi.</span>
                                    </div>

                                    <div class="form-groupEDU full">
                                        <label class="form-labelEDU">Địa chỉ <i class="fas fa-map-marker-alt"
                                                style="color:#e53935;font-size:12px"></i></label>
                                        <div class="address-grid-edu">
                                            <!-- Province -->
                                            <div class="csel" id="csel-province">
                                                <button type="button" class="csel-trigger" id="csel-province-btn">
                                                    <span class="csel-label">Tỉnh/Thành phố</span>
                                                    <i class="fas fa-chevron-down csel-arrow"></i>
                                                </button>
                                                <div class="csel-dropdown">
                                                    <div class="csel-search-wrap">
                                                        <i class="fas fa-search csel-search-icon"></i>
                                                        <input type="text" class="csel-search"
                                                            placeholder="Tìm tỉnh/thành phố..." autocomplete="off">
                                                    </div>
                                                    <ul class="csel-list"></ul>
                                                </div>
                                            </div>
                                            <!-- District -->
                                            <div class="csel disabled" id="csel-district">
                                                <button type="button" class="csel-trigger" id="csel-district-btn"
                                                    disabled>
                                                    <span class="csel-label">Quận/Huyện</span>
                                                    <i class="fas fa-chevron-down csel-arrow"></i>
                                                </button>
                                                <div class="csel-dropdown">
                                                    <div class="csel-search-wrap">
                                                        <i class="fas fa-search csel-search-icon"></i>
                                                        <input type="text" class="csel-search"
                                                            placeholder="Tìm quận/huyện..." autocomplete="off">
                                                    </div>
                                                    <ul class="csel-list"></ul>
                                                </div>
                                            </div>
                                            <!-- Ward -->
                                            <div class="csel disabled" id="csel-ward">
                                                <button type="button" class="csel-trigger" id="csel-ward-btn" disabled>
                                                    <span class="csel-label">Phường/Xã</span>
                                                    <i class="fas fa-chevron-down csel-arrow"></i>
                                                </button>
                                                <div class="csel-dropdown">
                                                    <div class="csel-search-wrap">
                                                        <i class="fas fa-search csel-search-icon"></i>
                                                        <input type="text" class="csel-search"
                                                            placeholder="Tìm phường/xã..." autocomplete="off">
                                                    </div>
                                                    <ul class="csel-list"></ul>
                                                </div>
                                            </div>
                                        </div>
                                        <input class="form-inputEDU" id="inp-streetEDU" type="text"
                                            placeholder="Số nhà, tên đường (VD: 123 Nguyễn Trãi)" value=""
                                            style="margin-top:10px;">
                                        <input type="hidden" name="address" id="inp-addressEDU" value="${user.address}">
                                        <span class="input-hintEDU" style="margin-top:4px"><i class="fas fa-info-circle"
                                                style="font-size:11px;margin-right:4px"></i>Chọn tỉnh → quận → phường,
                                            sau đó nhập số nhà/đường.</span>
                                    </div>
                                </div>

                                <!-- Password section -->
                                <div class="pw-sectionEDU">
                                    <h3><i class="fas fa-lock"></i> Đổi mật khẩu</h3>
                                    <button type="button" class="toggle-pw-btnEDU" id="togglePwBtnEDU"
                                        onclick="togglePassword()">
                                        + Bấm vào đây để thay đổi mật khẩu
                                    </button>
                                    <div class="pw-fieldsEDU" id="pwFieldsEDU">
                                        <div class="form-groupEDU">
                                            <label class="form-labelEDU">Mật khẩu hiện tại <span
                                                    style="color:#e53e3e">*</span></label>
                                            <div class="input-iconWrapEDU">
                                                <input class="form-inputEDU" name="currentpass" id="inp-pw-currentEDU"
                                                    type="password" placeholder="Nhập mật khẩu hiện tại">
                                                <button type="button" class="eye-btnEDU"
                                                    onclick="toggleEye('inp-pw-currentEDU', this)"><i
                                                        class="fas fa-eye-slash"></i></button>
                                            </div>
                                            <span class="field-errorEDU" id="err-pw-currentEDU">Vui lòng nhập mật khẩu
                                                hiện tại.</span>
                                        </div>
                                        <div class="form-groupEDU">
                                            <label class="form-labelEDU">Mật khẩu mới <span
                                                    style="color:#e53e3e">*</span></label>
                                            <div class="input-iconWrapEDU">
                                                <input class="form-inputEDU" name="newpass" id="inp-pw-newEDU"
                                                    type="password" placeholder="Tối thiểu 6 ký tự">
                                                <button type="button" class="eye-btnEDU"
                                                    onclick="toggleEye('inp-pw-newEDU', this)"><i
                                                        class="fas fa-eye-slash"></i></button>
                                            </div>
                                            <span class="field-errorEDU" id="err-pw-newEDU">Mật khẩu mới tối thiểu 6 ký
                                                tự.</span>
                                        </div>
                                        <div class="form-groupEDU">
                                            <label class="form-labelEDU">Xác nhận mật khẩu mới <span
                                                    style="color:#e53e3e">*</span></label>
                                            <div class="input-iconWrapEDU">
                                                <input class="form-inputEDU" name="newpasscheck" id="inp-pw-confirmEDU"
                                                    type="password" placeholder="Nhập lại mật khẩu mới">
                                                <button type="button" class="eye-btnEDU"
                                                    onclick="toggleEye('inp-pw-confirmEDU', this)"><i
                                                        class="fas fa-eye-slash"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-actionsEDU">
                                    <button type="button" class="btn-cancelEDU" onclick="closeEdit()">Hủy bỏ</button>
                                    <button type="submit" class="btn-saveEDU">
                                        <i class="fas fa-check" style="margin-right:6px;"></i>Lưu thay đổi
                                    </button>
                                    <input type="hidden" name="action" value="edit">
                                </div>
                            </form>
                        </div>

                    </div><!-- /main-contentUS -->
                    </div><!-- /containerUS -->

                    <!-- Toast -->
                    <div class="toastEDU" id="toastEDU">
                        <span id="toastMsgEDU">${message}</span>
                    </div>
                    <c:if test="${message != null}">
                        <script>
                            const toast = document.getElementById('toastEDU');
                            toast.classList.add('show');
                            setTimeout(function () {
                                toast.classList.remove('show');
                            }, 5200);
                        </script>
                    </c:if>


                    <script>
                        const viewSection = document.getElementById('viewSectionEDU');
                        const editPanel = document.getElementById('editPanelEDU');

                        function openEdit() {
                            viewSection.style.display = 'none';
                            editPanel.style.display = 'block';
                            editPanel.classList.add('active');
                            window.scrollTo({ top: 0, behavior: 'smooth' });
                        }

                        function closeEdit() {
                            editPanel.classList.remove('active');
                            editPanel.style.display = 'none';
                            viewSection.style.display = 'block';
                        }

                        document.getElementById('profileFormEDU').addEventListener('submit', function (e) {
                            // Validate phone number
                            var phoneInput = document.getElementById('inp-phoneEDU');
                            var phoneValue = phoneInput.value.trim();
                            var phoneError = document.getElementById('err-phoneEDU');
                            var phoneRegex = /^0\d{9}$/;

                            if (phoneValue !== '' && !phoneRegex.test(phoneValue)) {
                                e.preventDefault();
                                phoneError.style.display = 'block';
                                phoneInput.style.borderColor = '#e53e3e';
                                phoneInput.focus();
                                return;
                            } else {
                                phoneError.style.display = 'none';
                                phoneInput.style.borderColor = '';
                            }

                            if (!pwOpen) {
                                document.getElementById('inp-pw-currentEDU').value = '';
                                document.getElementById('inp-pw-newEDU').value = '';
                                document.getElementById('inp-pw-confirmEDU').value = '';
                            }
                        });

                        // Real-time phone validation
                        document.getElementById('inp-phoneEDU').addEventListener('input', function () {
                            var val = this.value.trim();
                            var phoneError = document.getElementById('err-phoneEDU');
                            if (val === '') {
                                phoneError.style.display = 'none';
                                this.style.borderColor = '';
                            } else if (/^0\d{9}$/.test(val)) {
                                phoneError.style.display = 'none';
                                this.style.borderColor = '#38a169';
                            } else {
                                phoneError.style.display = 'block';
                                this.style.borderColor = '#e53e3e';
                            }
                        });

                        /* Password toggle */
                        let pwOpen = false;
                        function togglePassword() {
                            pwOpen = !pwOpen;
                            const fields = document.getElementById('pwFieldsEDU');
                            const btn = document.getElementById('togglePwBtnEDU');
                            if (pwOpen) {
                                fields.classList.add('open');
                                btn.textContent = '− Ẩn phần đổi mật khẩu';
                            } else {
                                fields.classList.remove('open');
                                btn.textContent = '+ Bấm vào đây để thay đổi mật khẩu';
                                clearPwErrors();
                            }
                        }

                        function toggleEye(inputId, btn) {
                            const input = document.getElementById(inputId);
                            const icon = btn.querySelector('i');
                            if (input.type === 'password') {
                                input.type = 'text';
                                icon.classList.replace('fa-eye-slash', 'fa-eye');
                            } else {
                                input.type = 'password';
                                icon.classList.replace('fa-eye', 'fa-eye-slash');
                            }
                        }

                        /* ── CUSTOM SEARCHABLE SELECT (csel) ── */
                        var API_BASE = 'https://provinces.open-api.vn/api/';
                        var inpStreet = document.getElementById('inp-streetEDU');
                        var inpAddress = document.getElementById('inp-addressEDU');

                        // Pre-fill street from existing address
                        var existingAddr = inpAddress.value || '';
                        if (existingAddr && existingAddr !== '-') {
                            inpStreet.value = existingAddr;
                        }

                        // State for each custom select
                        var cselState = {
                            province: { code: '', name: '', el: document.getElementById('csel-province') },
                            district: { code: '', name: '', el: document.getElementById('csel-district') },
                            ward: { code: '', name: '', el: document.getElementById('csel-ward') }
                        };

                        // ── Toggle dropdown ──
                        function openCsel(key) {
                            var el = cselState[key].el;
                            if (el.classList.contains('disabled')) return;
                            // Close all others first
                            ['province', 'district', 'ward'].forEach(function (k) {
                                if (k !== key) cselState[k].el.classList.remove('open');
                            });
                            el.classList.toggle('open');
                            if (el.classList.contains('open')) {
                                var searchInput = el.querySelector('.csel-search');
                                searchInput.value = '';
                                searchInput.focus();
                                filterCselList(el, '');
                            }
                        }

                        // Setup trigger buttons
                        document.getElementById('csel-province-btn').addEventListener('click', function () { openCsel('province'); });
                        document.getElementById('csel-district-btn').addEventListener('click', function () { openCsel('district'); });
                        document.getElementById('csel-ward-btn').addEventListener('click', function () { openCsel('ward'); });

                        // ── Search filtering ──
                        function removeDiacritics(str) {
                            return str.normalize('NFD').replace(/[\u0300-\u036f]/g, '').replace(/đ/g, 'd').replace(/Đ/g, 'D');
                        }

                        function filterCselList(cselEl, query) {
                            var items = cselEl.querySelectorAll('.csel-item');
                            var q = removeDiacritics(query.toLowerCase().trim());
                            var visibleCount = 0;
                            items.forEach(function (li) {
                                var text = removeDiacritics(li.textContent.toLowerCase());
                                if (q === '' || text.indexOf(q) !== -1) {
                                    li.style.display = '';
                                    visibleCount++;
                                } else {
                                    li.style.display = 'none';
                                }
                            });
                            // Show "no results" hint
                            var noResult = cselEl.querySelector('.csel-no-result');
                            if (!noResult) {
                                noResult = document.createElement('li');
                                noResult.className = 'csel-no-result';
                                noResult.textContent = 'Không tìm thấy kết quả';
                                cselEl.querySelector('.csel-list').appendChild(noResult);
                            }
                            noResult.style.display = visibleCount === 0 ? '' : 'none';
                        }

                        // Attach search events
                        ['province', 'district', 'ward'].forEach(function (key) {
                            var searchInput = cselState[key].el.querySelector('.csel-search');
                            searchInput.addEventListener('input', function () {
                                filterCselList(cselState[key].el, this.value);
                            });
                        });

                        // ── Close on click outside ──
                        document.addEventListener('click', function (e) {
                            ['province', 'district', 'ward'].forEach(function (key) {
                                var el = cselState[key].el;
                                if (!el.contains(e.target)) {
                                    el.classList.remove('open');
                                }
                            });
                        });

                        // ── Populate list items ──
                        function populateCsel(key, items) {
                            var listEl = cselState[key].el.querySelector('.csel-list');
                            listEl.innerHTML = '';
                            items.forEach(function (item) {
                                var li = document.createElement('li');
                                li.className = 'csel-item';
                                li.setAttribute('data-code', item.code);
                                li.setAttribute('data-name', item.name);
                                li.textContent = item.name;
                                li.addEventListener('click', function () {
                                    selectCselItem(key, item.code, item.name);
                                });
                                listEl.appendChild(li);
                            });
                        }

                        // ── Select an item ──
                        function selectCselItem(key, code, name) {
                            cselState[key].code = code;
                            cselState[key].name = name;
                            var el = cselState[key].el;
                            el.querySelector('.csel-label').textContent = name;
                            el.classList.add('selected');
                            el.classList.remove('open');

                            // Mark active
                            el.querySelectorAll('.csel-item').forEach(function (li) {
                                li.classList.toggle('active', li.getAttribute('data-code') === String(code));
                            });

                            // Cascade logic
                            if (key === 'province') {
                                resetCsel('district');
                                resetCsel('ward');
                                if (code) {
                                    fetch(API_BASE + 'p/' + code + '?depth=2')
                                        .then(function (r) { return r.json(); })
                                        .then(function (data) {
                                            populateCsel('district', data.districts);
                                            enableCsel('district');
                                        });
                                }
                            } else if (key === 'district') {
                                resetCsel('ward');
                                if (code) {
                                    fetch(API_BASE + 'd/' + code + '?depth=2')
                                        .then(function (r) { return r.json(); })
                                        .then(function (data) {
                                            populateCsel('ward', data.wards);
                                            enableCsel('ward');
                                        });
                                }
                            }
                            updateHiddenAddress();
                        }

                        function resetCsel(key) {
                            cselState[key].code = '';
                            cselState[key].name = '';
                            var el = cselState[key].el;
                            var defaultLabels = { province: 'Tỉnh/Thành phố', district: 'Quận/Huyện', ward: 'Phường/Xã' };
                            el.querySelector('.csel-label').textContent = defaultLabels[key];
                            el.querySelector('.csel-list').innerHTML = '';
                            el.classList.add('disabled');
                            el.classList.remove('selected', 'open');
                            el.querySelector('.csel-trigger').disabled = true;
                        }

                        function enableCsel(key) {
                            var el = cselState[key].el;
                            el.classList.remove('disabled');
                            el.querySelector('.csel-trigger').disabled = false;
                        }

                        // ── Load provinces on init ──
                        fetch(API_BASE + '?depth=1')
                            .then(function (r) { return r.json(); })
                            .then(function (data) {
                                populateCsel('province', data);
                            });

                        // ── Street input ──
                        inpStreet.addEventListener('input', function () {
                            updateHiddenAddress();
                        });

                        // ── Combine address ──
                        function updateHiddenAddress() {
                            var parts = [];
                            var street = inpStreet.value.trim();
                            if (street) parts.push(street);
                            if (cselState.ward.name) parts.push(cselState.ward.name);
                            if (cselState.district.name) parts.push(cselState.district.name);
                            if (cselState.province.name) parts.push(cselState.province.name);
                            inpAddress.value = parts.join(', ');
                        }

                    </script>
                    <jsp:include page="/chat-AI.jsp"></jsp:include>
                    <jsp:include page="/footer.jsp"></jsp:include>
                </body>

                </html>