// Import necessary modules
const productController = require('./app/controller/productController');
const products = [
    {
        name: 'Casio EdificeF',
        description: 'Casio là thương hiệu đồng hồ Nhật Bản thành lập năm 1946 bởi Tadao Kashio',
        image: 'https://product.hstatic.net/200000656863/product/screenshot_2023-03-19_200404_0b726d6232184e27bad3f37adfff7be6.png',
        price: 2707.50,
        type: 5,
    },
    {
        name: 'Apple Watch SE 2023',
        description: 'Apple Watch SE 2023 GPS 44mm - Chính hãng VN/A là một sự lựa chọn tốt nhất cho những người mới lần đầu dùng đồng hồ làm thiết bị theo dõi sức khỏe.',
        image: 'https://minhtuanmobile.com/uploads/products/240407093035-88-apple-watch-se-2023-silver-vien-nhom-day-vai-minhtuanmobile-230929105058.jpg',
        price: 6590.00,
        type: 5
    },
    {
        name: 'Fitbit Charge 6',
        description: 'Fitbit là thiết bị chuyên chỉ số nhu cầu sức khỏe và thể thao. Riêng Tính năng Đồng bộ hóa (thông báo tin nhắn , cuộc gọi….) hiện tại ứng dụng của Hãng chưa hoàn thiện nên hoạt động chưa tối ưu, khách hàng cân nhắc khi lựa chọn.',
        image: 'https://lagihitech.vn/wp-content/uploads/2023/11/dong-ho-thong-minh-Fitbit-Charge-6-hinh-3.jpg',
        price: 45678.00,
        type: 5
    },
    {
        name: 'Huawei Watch GT 4',
        description: 'Huawei Watch là một sự lựa chọn đúng đắn dành cho bạn.',
        image: 'https://i.ebayimg.com/images/g/9KEAAOSwxitlu2gS/s-l960.jpg',
        price: 103846.15,
        type: 5
    },
    {
        name: 'SMILE KID',
        description: 'Sản phẩm đồng hồ mang thương hiệu Smile Kid với nhiều mẫu mã năng động, trẻ trung phù hợp với các em nhỏ.',
        image: 'https://cdn.tgdd.vn/Products/Images/7264/230914/smile-kid-sl034-01-tre-em-3-org.jpg',
        price: 190.00,
        type: 5
    },
    {
        name: 'Đồng hồ nữ I&W Carnival IW718L-DT–Quartz',
        description: 'Quốc gia đăng ký thương hiệu: Thụy Sỹ.',
        image: 'https://zenwatch.vn/wp-content/uploads/2023/12/Dong-ho-nu-IW-Carnival-718L-chinh-hang-avt-4-768x768.jpg',
        price: 3456.00,
        type: 5
    },
    {
        name: 'Đồng hồ nữ chính hãng KASSAW K889-2',
        description: 'ĐỒNG HỒ ĐÍNH ĐÁ, MÓN QUÀ NÀNG KHÔNG THỂ CHỐI TỪ Phụ nữ luôn bị cuốn hút bởi những món trang sức lấp lánh',
        image: 'https://thanhhungwatch.vn/uploads/%C4%91%E1%BB%93ng_h%E1%BB%93_kassaw_k889_(18).jpg',
        price: 4490.00,
        type: 5
    },
    {
        name: 'Đồng hồ Saga Stella',
        description: 'Mẫu Saga 71936-RGRDRD-2 phiên bản đồng hồ nghệ thuật và đầy sang trọng của với thiết kế mặt số vuông đính kim cương góc 12 giờ.',
        image: 'https://image.donghohaitrieu.com/wp-content/uploads/2023/10/71936-RGRDRD-2-00138-SVGR-XL-00148-LGBL-RH-2.jpg',
        price: 15275.00,
        type: 5
    },
    {
        name: 'ĐỒNG HỒ CT 124/2 DETAILS & DIMENSIONS',
        description: 'Đồng hồ cổ điển, một tuyệt tác kết hợp của nghệ thuật và kĩ thuật, không chỉ là một công cụ thời gian mà còn là biểu tượng của sự tinh tế và quí phái.',
        image: 'https://cavani.vn/wp-content/uploads/2024/03/DONG-HO-9-1-e1711942262883.jpg',
        price: 25100.00,
        type: 5
    },
    {
        name: 'Đồng hồ Tissot - Nữ ',
        description: 'Tissot là thương hiệu đồng hồ cao cấp nổi tiếng Thụy Sĩ, ra đời năm 1853.',
        image: 'https://wscdn.vn/upload/original-image/uploads/images/T050.207.37.017.04-2-1650859614330.jpg',
        price: 11800.00,
        type: 5
    },
   
];

// Define seeder function
const seedProducts = async () => {
    try {
        for (const product of products) {
            await productController.createProduct(product);
        }
        console.log('Seeder executed successfully');
    } catch (error) {
        console.error('Error seeding products:', error);
    }
};
seedProducts();
