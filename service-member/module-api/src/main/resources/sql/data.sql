-- Coupon

INSERT INTO COUPONS (name, discount_type, value, generate_type, max_quantity_per_member, quantity, validation_range) VALUES ('1000원 할인 쿠폰', 'FIXED', 1000, 'SIGNUP_THANKS', 1, 1, 30);
INSERT INTO COUPONS (name, discount_type, value, generate_type, max_quantity_per_member, quantity, validation_range) VALUES ('10% 할인 쿠폰', 'PERCENTAGE', 10, 'SIGNUP_THANKS', 1, 1, 30);
INSERT INTO COUPONS (name, discount_type, value, generate_type, max_quantity_per_member, quantity, validation_range) VALUES ('15% 할인 쿠폰', 'PERCENTAGE', 15, 'DAILY_UNLIMITED', 1, 1, 10);

--- Member
INSERT INTO MEMBERS (value, password, grade, phone_number, name) VALUES ('admin@test.com', 'test1234!', 'BRONZE', '010-1234-5678', 'admin임둥!');

-- MemberCoupon
INSERT INTO MEMBER_COUPONS (member_id, coupon_id, has_used, used_at, start_at, end_at)
VALUES
    (1, 1, false, NULL, '2023-07-20T10:00:00', '2023-08-20T10:00:00'),
    (1, 2, false, NULL, '2023-07-20T12:00:00', '2023-08-20T12:00:00'),
    (1, 3, false, NULL, '2023-07-20T09:45:00', '2023-08-31T09:45:00');