

INSERT INTO [dbo].[Discount] ([code], [description], [discount_percentage], [start_date], [end_date])
VALUES
-- Giảm giá 10% cho mùa hè
('SUMMER10', 'Giảm giá 10% cho tất cả sản phẩm mùa hè.', 10.00, '2024-06-01', '2024-08-31'),

-- Giảm giá 15% nhân dịp sinh nhật
('BDAY15', 'Giảm giá 15% cho tất cả đơn hàng nhân dịp sinh nhật.', 15.00, '2024-01-01', '2024-12-31'),

-- Giảm giá 20% trong tuần lễ Black Friday
('BLACK20', 'Giảm giá 20% trong tuần lễ Black Friday.', 20.00, '2024-11-22', '2024-11-29'),

-- Giảm giá 5% cho các khách hàng mới
('NEW5', 'Giảm giá 5% cho lần mua hàng đầu tiên.', 5.00, '2024-01-01', '2024-12-31'),

-- Khuyến mãi đặc biệt 50% cho dịp Tết Nguyên Đán
('TET50', 'Giảm giá 50% cho tất cả sản phẩm dịp Tết Nguyên Đán.', 50.00, '2024-01-30', '2024-02-10');
