INSERT INTO gift_certificate (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('1', 'Woman', 'gift certificate for women', '50', '365', '2018-08-29T06:12:15', '2018-08-29T06:12:15');
INSERT INTO gift_certificate (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('2', 'Man', 'gift certificate for men', '60', '180', '2021-07-25 23:15:18', '2021-07-25 23:15:18');
INSERT INTO gift_certificate (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('3', 'Child', 'gift certificate for children', '30', '50', '2021-08-04T07:56:47', '2021-08-04T07:56:47');
INSERT INTO gift_certificate (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES ('4', 'Happy Birhday', 'gift certificate', '80', '360', '2021-08-07 06:18:08', '2021-08-07 06:18:08');
INSERT INTO tag (`id`, `name`) VALUES ('1', 'woman');
INSERT INTO tag (`id`, `name`) VALUES ('2', 'man');
INSERT INTO tag (`id`, `name`) VALUES ('3', 'relax');
INSERT INTO gift_certificate_tag_connection (`gift_certificate_id`, `tag_id`) VALUES ('1', '1');
INSERT INTO gift_certificate_tag_connection (`gift_certificate_id`, `tag_id`) VALUES ('1', '3');