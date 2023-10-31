-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 31, 2023 lúc 03:06 PM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `atfood`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `machitietdonhang` int(11) NOT NULL,
  `masanpham` int(11) NOT NULL,
  `soluong` int(11) NOT NULL,
  `gia` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`machitietdonhang`, `masanpham`, `soluong`, `gia`) VALUES
(12, 5, 2, '98000'),
(12, 1, 2, '33000'),
(12, 4, 3, '17000'),
(12, 3, 1, '16000'),
(12, 6, 3, '99000'),
(13, 3, 1, '16000'),
(13, 2, 1, '60000'),
(14, 1, 1, '33000'),
(14, 5, 1, '98000'),
(15, 1, 1, '33000'),
(15, 2, 1, '60000'),
(15, 3, 1, '16000'),
(16, 1, 2, '33000'),
(16, 2, 2, '60000'),
(16, 4, 1, '17000'),
(16, 5, 3, '98000'),
(17, 2, 1, '60000'),
(17, 1, 1, '33000'),
(17, 3, 1, '16000'),
(17, 5, 1, '98000'),
(18, 1, 1, '33000'),
(19, 1, 2, '33000'),
(19, 2, 2, '60000'),
(19, 3, 1, '16000'),
(20, 2, 1, '60000'),
(20, 1, 2, '33000'),
(21, 7, 3, '30000'),
(22, 5, 1, '98000'),
(22, 6, 1, '99000'),
(23, 2, 1, '60000'),
(23, 3, 1, '16000'),
(24, 1, 1, '33000'),
(25, 6, 1, '99000'),
(26, 1, 2, '33000'),
(27, 1, 1, '33000'),
(27, 2, 2, '60000'),
(28, 2, 1, '60000'),
(28, 1, 1, '33000'),
(28, 3, 2, '16000'),
(29, 3, 1, '16000'),
(30, 7, 1, '30000'),
(31, 7, 1, '30000'),
(32, 1, 1, '33000'),
(33, 2, 4, '60000'),
(33, 6, 2, '99000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chucnang`
--

CREATE TABLE `chucnang` (
  `machucnang` int(11) NOT NULL,
  `tenchucnang` varchar(100) NOT NULL,
  `hinhanh` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chucnang`
--

INSERT INTO `chucnang` (`machucnang`, `tenchucnang`, `hinhanh`) VALUES
(1, 'Thông tin tài khoản', 'https://cdn-icons-png.flaticon.com/256/5624/5624093.png'),
(2, 'Lịch sử đặt hàng', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/ImageChucNang/history_order.png'),
(3, 'Đăng xuất', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/ImageChucNang/log-out.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cuahang`
--

CREATE TABLE `cuahang` (
  `macuahang` int(11) NOT NULL,
  `tencuahang` varchar(100) NOT NULL,
  `diachi` varchar(100) NOT NULL,
  `hinhanh` text NOT NULL,
  `danhmuc` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `cuahang`
--

INSERT INTO `cuahang` (`macuahang`, `tencuahang`, `diachi`, `hinhanh`, `danhmuc`) VALUES
(1, 'Bánh tráng cuộn - Minh Trí', '465 Nguyễn văn công', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/ImageCuaHang/AnVatMoiNgay.jpg', 2),
(2, 'SuShi tươi ngon - Thành Tín', '194 Lê Văn Thọ', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/ImageCuaHang/SuShi_ThanhTin.jpg', 2),
(3, 'Cơm Tấm - Tài', '430 Quang Trung', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/ImageCuaHang/ComTam_Tai.jpg', 3),
(4, 'Cơm nhà làm - Vạn Duyên', '336 Nguyễn Thái Sơn', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/ImageCuaHang/Com_VanDuyen.jpg', 3),
(5, 'Bánh mì Kebab - Kim Oanh', '118 Phạm Văn Đồng', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/ImageCuaHang/Com_VanDuyen.jpg', 1),
(6, 'Hambuger - Ngọc Minh', '981 Nguyễn Oanh', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/ImageCuaHang/hamburger_NgocMinh.jpg', 1),
(7, 'Bánh Tráng BoBo', '465 Man Thiện - Thủ Đức', 'https://d1sag4ddilekf6.cloudfront.net/compressed_webp/items/VNITE2023090712385682495/detail/menueditor_item_46efb0b819094af9bb655d8b79301720_1694090281489686136.webp', 2),
(8, 'Ăn Vặt Aki mới', '116 Phạm Văn Đồng - Gò Vấp', 'https://d1sag4ddilekf6.cloudfront.net/compressed_webp/items/VNITE2023010802355255892/detail/menueditor_item_44ae8d6e8ce34194a464fcc2ed298f67_1679048605114181266.webp', 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `madonhang` int(11) NOT NULL,
  `manguoidung` int(11) NOT NULL,
  `tennguoidathang` varchar(100) NOT NULL,
  `sodienthoai` varchar(11) NOT NULL,
  `diachi` text NOT NULL,
  `soluong` int(11) NOT NULL,
  `tongtien` varchar(50) NOT NULL,
  `ngaydathang` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`madonhang`, `manguoidung`, `tennguoidathang`, `sodienthoai`, `diachi`, `soluong`, `tongtien`, `ngaydathang`) VALUES
(12, 1, 'trung', '0898357214', '465/10/12 Nguyễn Văn Công', 11, '626000', '2023-07-08 04:13:05'),
(13, 1, 'trung', '0898357214', '465/10/12 Nguyễn Văn Công', 2, '76000', '2023-09-08 04:13:39'),
(14, 1, 'trung', '0898357214', '194/78/2', 2, '131000', '2023-09-08 04:43:16'),
(15, 1, 'trung', '0898357214', '191', 3, '109000', '2023-09-09 03:59:00'),
(16, 12, 'Anh Trung', '+848983572', '465/10/12 Nguyễn Văn Công', 8, '497000', '2023-07-09 16:35:57'),
(17, 3, 'trung', '0898357214', '111', 4, '207000', '2023-09-10 15:56:12'),
(18, 3, 'trung', '0898357214', '465 NVC', 1, '33000', '2023-09-10 18:07:21'),
(19, 3, 'trung', '0898357214', '111', 5, '202000', '2023-09-22 23:30:09'),
(20, 5, 'trung', '+848983572', '465 NVC', 3, '126000', '2023-09-26 01:57:53'),
(21, 5, 'trung', '+848983572', '534 NLB', 3, '90000', '2023-09-26 03:08:48'),
(22, 5, 'trung', '+848983572', '194 NLB', 2, '197000', '2023-09-27 15:15:39'),
(23, 22, 'khôi', '+843869045', '465 NVC', 2, '76000', '2023-09-27 15:23:20'),
(24, 26, 'i', '+8489835721', 'a', 1, '33000', '2023-09-27 22:44:33'),
(25, 26, 'i', '+8489835721', 'b', 1, '99000', '2023-09-27 22:44:52'),
(26, 26, 'i', '+8489835721', 'v', 2, '66000', '2023-09-27 22:46:29'),
(27, 27, 'm', '+8489835721', 'f', 3, '153000', '2023-07-06 02:20:18'),
(28, 28, 'trung', '+8489835721', 'ttt', 4, '125000', '2023-10-06 02:47:55'),
(29, 31, 't', '+8489835721', 't', 1, '16000', '2023-10-09 04:13:31'),
(30, 31, 't', '+8489835721', 't', 1, '30000', '2023-10-12 02:27:50'),
(31, 31, 't', '+8489835721', 't', 1, '30000', '2023-10-14 03:38:30'),
(32, 32, 'tai', '+8481402727', 't', 1, '33000', '2023-10-15 23:35:02'),
(33, 35, 'Anh Trung', '+8489835721', '465/12/12 nguyễn văn công phường 13 Quận Gò Vấp', 6, '438000', '2023-10-26 03:51:18');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `maloaisanpham` int(11) NOT NULL,
  `tenloaisanpham` varchar(100) NOT NULL,
  `hinhanh` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `loaisanpham`
--

INSERT INTO `loaisanpham` (`maloaisanpham`, `tenloaisanpham`, `hinhanh`) VALUES
(1, 'Đồ ăn sáng', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/Image/breakfast.png'),
(2, 'Đồ ăn nhanh', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/Image/fast_food.png'),
(3, 'Cơm ngon', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/Image/rice.png'),
(4, 'Rau-Củ-Quả', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/Image/salad.png'),
(5, 'Súp nóng hổi', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/Image/soup.png'),
(6, 'Trà sữa', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/Image/milk_tea.png'),
(7, 'Ăn vặt', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/Image/snack.png'),
(8, 'Đồ uống', 'https://raw.githubusercontent.com/nguyenanhtrung1/MyImageAndroid/main/Image/drink.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `masanpham` int(11) NOT NULL,
  `tensanpham` varchar(100) NOT NULL,
  `giasanpham` varchar(100) NOT NULL,
  `hinhanh` text NOT NULL,
  `mota` text NOT NULL,
  `macuahang` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`masanpham`, `tensanpham`, `giasanpham`, `hinhanh`, `mota`, `macuahang`) VALUES
(1, 'ComBo 3 Xúc Xích đức', '33000', 'https://d1sag4ddilekf6.cloudfront.net/compressed_webp/items/VNITE2023011318323867741/photo/menueditor_item_69cde78bb0f64b07bc0b0b8784c545fd_1673634711758664533.webp', 'Nếu chưa hài lòng với món ăn hoặc có vấn đề gì Quý khách vui lòng liên hệ sdt trên hoá đơn để được hỗ trợ ạ.', 1),
(2, 'Cá Viên Chiên Siêu Ngon.', '60000', 'https://d1sag4ddilekf6.cloudfront.net/compressed_webp/items/VNITE2022121505294507081/photo/menueditor_item_62993ce9369f4bc296c851eb2d8b48d3_1671082124834558733.webp', 'Rất giòn ngon mời cả nhà thưởng thức', 1),
(3, 'Hotdog phô mai Hàn Quốc.', '16000', 'https://d1sag4ddilekf6.cloudfront.net/compressed_webp/items/VNITE2023060311125978474/photo/menueditor_item_777c5f94d9c64f62a971f772e3e4482c_1685790745044555579.webp', 'Nóng hổi vừa thổi vừa ăn', 1),
(4, 'Nem nướng.', '17000', 'https://d1sag4ddilekf6.cloudfront.net/compressed_webp/items/VNITE2023082417231868483/photo/menueditor_item_c05e89f67eaf40ada914f04ce90d1b9e_1692897757793311600.webp', 'Được tặng kèm 1 cây chả nhỏ', 1),
(5, 'Hugo hào nhật tính con/phần.', '98000', 'https://d1sag4ddilekf6.cloudfront.net/compressed_webp/items/VNITE2023031904444175778/photo/menueditor_item_b4191413e7ff421a9e2e2f851c6688d1_1679200407474419082.webp', 'béo ngọt ngon', 2),
(6, 'Cá saba nhật ngâm jấm (simisaba)', '99000', 'https://d1sag4ddilekf6.cloudfront.net/compressed_webp/items/VNITE2021102605494101051/photo/menueditor_item_bfca4cc1056d4930a9e4b4d55d6ae240_1635227326590437392.webp', 'Cá tươi mới', 2),
(7, 'Cơm Chiên Má Đùi', '30000', 'https://d1sag4ddilekf6.cloudfront.net/compressed_webp/items/VNITE202304190716201670360/photo/9af282510e674ac991a977dc114311cc_1681888580911937546.webp', 'Mỗi đơn hàng chỉ được áp dụng giảm giá 1 lần', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `taikhoan` varchar(100) NOT NULL,
  `matkhau` varchar(100) NOT NULL,
  `tennguoidung` varchar(100) NOT NULL,
  `sodienthoai` varchar(12) NOT NULL,
  `vaitro` int(11) NOT NULL,
  `uid` text NOT NULL,
  `token` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `taikhoan`, `matkhau`, `tennguoidung`, `sodienthoai`, `vaitro`, `uid`, `token`) VALUES
(1, 'admin', '123', 'trung', '0898357214', 1, '', '0'),
(4, 'a', '1', 'trung', '0898357214', 1, '', '0'),
(28, 't', '1', 'trung', '+84898357214', 0, 'vNlqD8H2gROgYrZ0f18neyFvw093', '0'),
(29, 'b', '1', 'trung', '+84898357214', 1, 'vNlqD8H2gROgYrZ0f18neyFvw093', '0'),
(30, 'c', '1', 't', '+84898357214', 0, 'vNlqD8H2gROgYrZ0f18neyFvw093', 'cjFSgFDWSUKcztEId9g94y:APA91bHIxBKlMJSQZgRoMx3a83HgIzdA3TQYLq4_L9zJ-N32Vo8ZxTZ0ovEDhU5Hn7Wt8XF304ee9jJPUVKYSHsmTcwndlEox4RpaRffgS8e3K96rNgkRCX0E_K_f-THOpAQE_D1WXjg'),
(31, 'd', '1', 't', '+84898357214', 0, 'vNlqD8H2gROgYrZ0f18neyFvw093', 'cjFSgFDWSUKcztEId9g94y:APA91bHIxBKlMJSQZgRoMx3a83HgIzdA3TQYLq4_L9zJ-N32Vo8ZxTZ0ovEDhU5Hn7Wt8XF304ee9jJPUVKYSHsmTcwndlEox4RpaRffgS8e3K96rNgkRCX0E_K_f-THOpAQE_D1WXjg'),
(32, 'tai', '1', 'tai', '+84814027271', 0, 'BNUr9L7hiebKlVcFcjxBitdiibi1', 'cjFSgFDWSUKcztEId9g94y:APA91bHIxBKlMJSQZgRoMx3a83HgIzdA3TQYLq4_L9zJ-N32Vo8ZxTZ0ovEDhU5Hn7Wt8XF304ee9jJPUVKYSHsmTcwndlEox4RpaRffgS8e3K96rNgkRCX0E_K_f-THOpAQE_D1WXjg'),
(33, 'trung', '1', 'trung', '+84898357214', 0, 'vNlqD8H2gROgYrZ0f18neyFvw093', 'dOGTbSHLSbqDqPRj5S5ikv:APA91bFfVtXArlYTWcq_SoQVgQZbDISGclv_K6HVI-1n1k4RbIoDSYhH84r-6Nzdhm9ozkK9ijQ4VK1Y0El_MvjZbSpJlxZoRl45DNdaNTZNWvi6TKHB05qTUFlbUVQvcrOjhca0WQXM'),
(34, 'huyentrinh123', '1', 'trinh', '+84087787539', 0, 'yRLZkhgbAPQBwWyo5CzFxFLg5f12', ''),
(35, 'taikhoan1', '123', 'Anh Trung', '+84898357214', 0, 'vNlqD8H2gROgYrZ0f18neyFvw093', '');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chucnang`
--
ALTER TABLE `chucnang`
  ADD PRIMARY KEY (`machucnang`);

--
-- Chỉ mục cho bảng `cuahang`
--
ALTER TABLE `cuahang`
  ADD PRIMARY KEY (`macuahang`),
  ADD KEY `fk_loai_sp` (`danhmuc`);

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`madonhang`);

--
-- Chỉ mục cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`maloaisanpham`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`masanpham`),
  ADD KEY `fk_ma_sp` (`macuahang`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chucnang`
--
ALTER TABLE `chucnang`
  MODIFY `machucnang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `cuahang`
--
ALTER TABLE `cuahang`
  MODIFY `macuahang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `madonhang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  MODIFY `maloaisanpham` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `masanpham` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `cuahang`
--
ALTER TABLE `cuahang`
  ADD CONSTRAINT `fk_loai_sp` FOREIGN KEY (`danhmuc`) REFERENCES `loaisanpham` (`maloaisanpham`);

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `fk_ma_sp` FOREIGN KEY (`macuahang`) REFERENCES `cuahang` (`macuahang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
