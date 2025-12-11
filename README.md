Website Thương Mại Điện Tử Mini

Dự án này là một website bán quần áo đơn giản được xây dựng bằng HTML – CSS – JavaScript thuần, mô phỏng các tính năng cơ bản của một trang TMĐT:

Hiển thị danh sách 27 sản phẩm quần áo

Ảnh minh hoạ cho từng sản phẩm (dạng placeholder)

Giỏ hàng (thêm, xoá, tính tổng tiền)

Nút thanh toán (gửi dữ liệu lên API backend)

Hộp chat với người bán (gửi tin nhắn lên backend)

Dự án phù hợp cho mục đích:

Học Frontend cơ bản

Làm mẫu giao diện

Tích hợp thử nghiệm với API Backend

🚀 Tính năng chính
✔️ 1. Danh sách sản phẩm (27 sản phẩm)

Gồm đủ các loại quần áo: áo thun, sơ mi, hoodie, váy, jean, short, vest, maxi,...

Hiển thị dạng lưới (grid) đẹp mắt.

Có ảnh minh họa phù hợp từng loại trang phục.

✔️ 2. Giỏ hàng

Thêm sản phẩm vào giỏ.

Tự động tăng số lượng nếu thêm trùng sản phẩm.

Xoá từng sản phẩm.

Tính tổng số tiền.

Giao diện giỏ hàng luôn hiển thị bên phải.

✔️ 3. Thanh toán

Nút “Thanh toán” sẽ gửi dữ liệu POST đến API:

POST http://localhost:8080/checkout


Nếu thành công → thông báo + reset giỏ hàng.

✔️ 4. Chat với người bán

Hộp chat mini ở góc dưới màn hình.

Gửi tin nhắn đến server:

POST http://localhost:8080/chat


Hiển thị tin nhắn của người dùng ngay lập tức.

📁 Cấu trúc dự án
project/
│── index.html      # File web chính
│── README.md       # Mô tả dự án

🖼️ Giao diện chính

🔹 Danh sách sản phẩm dạng grid
🔹 Giỏ hàng cố định bên phải
🔹 Chatbox ở góc dưới
🔹 Giao diện màu xanh tím chủ đạo (#3f51b5)

🔧 Công nghệ sử dụng

HTML5

CSS3 (Flexbox + Grid Layout)

JavaScript thuần

API backend giả lập qua:

POST /checkout

POST /chat

Không sử dụng framework – dễ học, dễ tùy chỉnh.

🛠️ Cách chạy dự án
1️⃣ Mở trực tiếp

Chỉ cần mở file index.html bằng trình duyệt.

2️⃣ Chạy kèm backend giả lập (tùy chọn)

Bạn có thể tạo backend Node.js như sau:

npm init -y
npm install express cors


Tạo file server.js:

const express = require("express");
const app = express();
app.use(express.json());
app.use(require("cors")());

app.post("/checkout", (req, res) => {
    console.log("Checkout data:", req.body);
    res.json({status: "success"});
});

app.post("/chat", (req, res) => {
    console.log("Chat message:", req.body);
    res.json({reply: "Người bán đã nhận được tin nhắn!"});
});

app.listen(8080, () => console.log("Server chạy tại http://localhost:8080"));


Chạy:

node server.js


📌 Tác giả

Dự án được tạo từ yêu cầu người dùng và được tối ưu bởi vũ-giáp
