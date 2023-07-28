-- Coupon

INSERT INTO coupons (name, discount_type, value, generate_type, max_quantity_per_member, quantity, validation_range) VALUES ('1000원 할인 쿠폰', 'FIXED', 1000, 'SIGNUP_THANKS', 1, 1, 30);
INSERT INTO coupons (name, discount_type, value, generate_type, max_quantity_per_member, quantity, validation_range) VALUES ('10% 할인 쿠폰', 'PERCENTAGE', 10, 'SIGNUP_THANKS', 1, 1, 30);
INSERT INTO coupons (name, discount_type, value, generate_type, max_quantity_per_member, quantity, validation_range) VALUES ('15% 할인 쿠폰', 'PERCENTAGE', 15, 'DAILY_UNLIMITED', 1, 1, 10);

