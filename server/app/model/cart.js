const { db } = require('../db/db');

// Thêm sản phẩm vào giỏ hàng

const addToCart = (username, productId, quantity) => {
    return new Promise((resolve, reject) => {
        db.run('INSERT INTO cart (productId, quantity, username) VALUES (?, ?, ?)', [productId, quantity, username], function (err) {
            if (err) {
                reject(err);
            } else {
                resolve(this.lastID); // Trả về ID của mục vừa thêm
            }
        });
    });
};

// Lấy tất cả các mục trong giỏ hàng
const getCartItems = () => {
    return new Promise((resolve, reject) => {
        db.all('SELECT * FROM cart', (err, rows) => {
            if (err) {
                reject(err);
            } else {
                resolve(rows);
            }
        });
    });
};

// Cập nhật số lượng của một mục trong giỏ hàng
const updateCartItem = (cartItemId, newQuantity) => {
    return new Promise((resolve, reject) => {
        db.run('UPDATE cart SET quantity = ? WHERE id = ?', [newQuantity, cartItemId], function (err) {
            if (err) {
                reject(err);
            } else {
                resolve(`Cart item ${cartItemId} updated successfully`);
            }
        });
    });
};

// Xóa một mục khỏi giỏ hàng
const removeCartItem = (cartItemId) => {
    return new Promise((resolve, reject) => {
        db.run('DELETE FROM cart WHERE id = ?', [cartItemId], function (err) {
            if (err) {
                reject(err);
            } else {
                resolve(`Cart item ${cartItemId} removed successfully`);
            }
        });
    });
};

// Xóa tất cả các mục trong giỏ hàng
const clearCart = () => {
    return new Promise((resolve, reject) => {
        db.run('DELETE FROM cart', function (err) {
            if (err) {
                reject(err);
            } else {
                resolve('Cart cleared successfully');
            }
        });
    });
};
const getCartItemsByUsername = async (username) => {
    return new Promise((resolve, reject) => {
        const sql = 'SELECT * FROM cart WHERE username = ?';
        db.all(sql, [username], (err, rows) => {
            if (err) {
                reject(err);
                return;
            }
            resolve(rows);
        });
    });
};

module.exports = {
    addToCart,
    getCartItems,
    updateCartItem,
    removeCartItem,
    clearCart,
    getCartItemsByUsername
};
