<%-- 
    Document   : chat-AI
    Created on : Mar 4, 2026, 4:46:06 PM
    Author     : dotha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Floating AI Chat Button -->
<button id="chatBtnAI" class="chat-btn-ai" title="Chat với AI">
    <i class="fas fa-robot"></i>
</button>

<!-- Chat Popup -->
<div id="chatPopupAI" class="chat-popup-ai">

    <!-- Header -->
    <div id="chatHeaderAI" class="chat-header-ai">
        <div class="chat-header-left">
            <div class="chat-header-avatar">🤖</div>
            <div class="chat-header-info">
                <h4>AI Trợ Lý</h4>
                <div class="chat-header-status">
                    <span class="status-dot"></span>
                    <span>Đang hoạt động</span>
                </div>
            </div>
        </div>
        <span id="closeChatAI" class="close-chat-ai">✕</span>
    </div>

    <!-- Chat Body -->
    <div id="chatBodyAI" class="chat-body-ai">
        <div class="chat-welcome">
            <span class="chat-welcome-icon">✨</span>
            <h3>Xin chào! Tôi là AI Trợ Lý</h3>
            <p>Hãy hỏi tôi bất cứ điều gì về sản phẩm, tôi sẽ giúp bạn tìm kiếm nhanh chóng!</p>
        </div>
    </div>

    <!-- Input Area -->
    <div id="chatInputAI" class="chat-input-ai">
        <input type="text" id="msgAI" placeholder="Nhập câu hỏi của bạn..." autocomplete="off">
        <button id="sendBtnAI" title="Gửi tin nhắn">
            <i class="fas fa-paper-plane"></i>
        </button>
    </div>

</div>

<script>
    // DOM elements
    var chatBtnAI = document.getElementById("chatBtnAI");
    var chatPopupAI = document.getElementById("chatPopupAI");
    var closeChatAI = document.getElementById("closeChatAI");
    var sendBtnAI = document.getElementById("sendBtnAI");
    var chatBodyAI = document.getElementById("chatBodyAI");
    var msgAI = document.getElementById("msgAI");

    // Open chat popup
    chatBtnAI.onclick = function () {
        chatPopupAI.style.display = "flex";
        chatBtnAI.style.display = "none";
        msgAI.focus();
    };

    // Close chat popup
    closeChatAI.onclick = function () {
        chatPopupAI.style.display = "none";
        chatBtnAI.style.display = "flex";
    };

    // Send on Enter key
    msgAI.addEventListener("keydown", function (e) {
        if (e.key === "Enter") {
            sendBtnAI.click();
        }
    });

    // Send message
    sendBtnAI.onclick = function () {
        var message = msgAI.value;
        if (message.trim() === "") return;

        // Remove welcome message on first send
        var welcome = chatBodyAI.querySelector(".chat-welcome");
        if (welcome) welcome.remove();

        // Show user message
        chatBodyAI.innerHTML +=
            "<div class='chat-message-user-ai'>" +
            "<div class='chat-bubble-user-ai'>" + escapeHtml(message) + "</div>" +
            "</div>";

        msgAI.value = "";
        chatBodyAI.scrollTop = chatBodyAI.scrollHeight;

        // Show typing indicator
        var typingId = "typing-" + Date.now();
        chatBodyAI.innerHTML +=
            "<div class='chat-message-ai' id='" + typingId + "'>" +
            "<div class='typing-indicator'>" +
            "<span></span><span></span><span></span>" +
            "</div>" +
            "</div>";
        chatBodyAI.scrollTop = chatBodyAI.scrollHeight;

        // Send to server
        fetch("chat-ai", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "message=" + encodeURIComponent(message)
        })
        .then(function (response) {
            return response.text();
        })
        .then(function (data) {
            // Remove typing indicator
            var typingEl = document.getElementById(typingId);
            if (typingEl) typingEl.remove();

            // Show AI response
            chatBodyAI.innerHTML +=
                "<div class='chat-message-ai'>" +
                "<div class='chat-bubble-ai'>" + data + "</div>" +
                "</div>";

            chatBodyAI.scrollTop = chatBodyAI.scrollHeight;
        })
        .catch(function () {
            var typingEl = document.getElementById(typingId);
            if (typingEl) typingEl.remove();

            chatBodyAI.innerHTML +=
                "<div class='chat-message-ai'>" +
                "<div class='chat-bubble-ai'>Xin lỗi, đã có lỗi xảy ra. Vui lòng thử lại!</div>" +
                "</div>";
            chatBodyAI.scrollTop = chatBodyAI.scrollHeight;
        });
    };

    // Escape HTML to prevent XSS
    function escapeHtml(text) {
        var div = document.createElement('div');
        div.appendChild(document.createTextNode(text));
        return div.innerHTML;
    }
</script>