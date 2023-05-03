DROP DATABASE if exists `csc8019_team15`;
CREATE DATABASE `csc8019_team15`;

USE `csc8019_team15`;

DROP TABLE IF EXISTS `cuisine`;
CREATE TABLE `cuisine` (
     `id` BIGINT UNSIGNED AUTO_INCREMENT,
    `name` VARCHAR(50),
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `cuisine` VALUES 
(1, "American"),
(2, "British"),
(3, "Caribbean"),
(4, "Chinese"),
(5, "Greek"),
(6, "Indian"),
(7, "Italian"),
(8, "Japanese"),
(9, "Korean"),
(10, "Mexican"),
(11, "Spanish"),
(12, "Thai"),
(13, "Turkish"),
(14, "Vietnamese");


DROP TABLE IF EXISTS `restaurant`;
CREATE TABLE `restaurant` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255),
  `phone_number` VARCHAR(255),
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL, 
  `rating` DOUBLE,
  `cuisine_id` BIGINT UNSIGNED,
  `images_link` VARCHAR(2048) NOT NULL,
  `menu_link` VARCHAR(255),
  `website_link` VARCHAR(255),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`cuisine_id`) REFERENCES `cuisine` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `restaurant` (id, name, address, phone_number, latitude, longitude, rating, cuisine_id, images_link, menu_link, website_link) VALUES
(1, "TGI Fridays - Eldon Square", "HF, 6 Eldon Square, Newcastle upon Tyne NE1 7AP", "03304605531", 54.97473595737649, -1.6147044007007851, 0, 1, "https://www.dawnvale.com/wp-content/uploads/projects/tgi-fridays-newcastle/TGI-Newcastle-Hero-1920x1080.jpg,https://www.dawnvale.com/wp-content/uploads/projects/tgi-fridays-newcastle/TGI-Newcastle-1540x900-01.jpg", "https://www.tgifridays.co.uk/menu/", "https://www.tgifridays.co.uk/"),
(2, "Meat:Stack", "41-43 Groat Market, Newcastle upon Tyne NE1 1UG", "01912617395", 54.97119573407862, -1.6137792759971241, 0, 1, "https://1.bp.blogspot.com/-xH2y_dAASMM/Xzo_m5z2pyI/AAAAAAABXpo/6JSp1O3RZ6QK6M6q_80snsGngtRnBUrfACLcBGAsYHQ/s2048/1%2BExterior.jpg,https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/335884895_2770307406449727_1301386677779365003_n.jpg?stp=cp6_dst-jpg&_nc_cat=110&ccb=1-7&_nc_sid=8bfeb9&_nc_ohc=rB298mTDfG0AX8Tjas2&_nc_oc=AQneUjeLXtRIAhm9BGCHfvKaqkfnWeXKr17iayOo79bkWUFGa8rXZzYY6BtyNLA1O2Y&_nc_ht=scontent-lhr8-1.xx&oh=00_AfCFMfhK5N8CBZUAdrQpxiepjvSkXk0rSw2OH-QWmIB9aQ&oe=64576690", "https://www.meat-stack.com/newcastle", "https://www.meat-stack.com/"),
(3, "Five Guys Newcastle", "2/4 Northumberland St, Newcastle upon Tyne NE1 7DE", "0191 261 5755", 54.97459704533814, -1.611392352618242, 0, 1, "https://3.bp.blogspot.com/-LhnD8UlH7OA/V8GPoyarhYI/AAAAAAAAEhA/uur0DTQ1G8QEc9jYquJVT9tnPY_L4LwswCLcB/s1600/Interior.jpg,https://4.bp.blogspot.com/-KeLK7SEAIM8/V8GPoBXERGI/AAAAAAAAEg4/bmQTia0RU8ssXkt6DqPpjAE00IeYUUV4ACLcB/s1600/Exterior.jpg", "https://fiveguys.co.uk/menu/?y_source=1_MTI0MzE1NzAtNzE1LWxvY2F0aW9uLm1lbnVfdXJs#tab-burgers", "https://fiveguys.co.uk/" ),
(4, "Fat Hippo Newcastle", "2-6 Shakespeare St, Newcastle upon Tyne NE1 6AQ", "01914471161", 54.97250455309059, -1.611880271339867, 0, 2, "https://scontent-lhr8-2.xx.fbcdn.net/v/t1.6435-9/107810927_1399760186880194_8108891266605767316_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=730e14&_nc_ohc=IQyvUV6eq9gAX8ELWTI&_nc_ht=scontent-lhr8-2.xx&oh=00_AfDZ3Mx6uTXWd_01eyplALrz8H3pcRcGjCfixVRa5DVdpw&oe=6479D6FA,https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/296121154_1969098083279732_5024400020210030108_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=e3f864&_nc_ohc=8njgbXKC-0kAX9DwQv1&_nc_ht=scontent-lhr8-1.xx&oh=00_AfDZrSFWOtw4Zy-Ms9_DOKrMvuCIPicxepU6R1oJmGQULw&oe=645699B4", "https://fathippo.co.uk/menus/food/", "https://fathippo.co.uk/"),
(5, "The Botanist Bar & Restaurant Newcastle", "Monument Mall, Newcastle upon Tyne NE1 7AL", "01912616307", 54.974366160573034, -1.613374842653454, 0, 2, "https://www.northeastfood.co.uk/wp-content/uploads/2019/11/DSC_0107.jpg,https://scontent-lhr8-1.xx.fbcdn.net/v/t1.6435-9/87727713_1292307144289667_7656247700152647680_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=e3f864&_nc_ohc=lUqHXcQRfjEAX_3z0g-&_nc_ht=scontent-lhr8-1.xx&oh=00_AfBfmQaE8RZAcQrHTS90PCEIU-zfbYbjfI41iNlfXGBOQA&oe=6479EBA1", "https://thebotanist.uk.com/locations/newcastle/menus", "https://thebotanist.uk.com/"),
(6, "Hawthorns Restaurant", "Hawthorn Square, Forth Street, NE1 3SA", "0191 562 3333", 54.96723519096263, -1.617916453188272, 0, 2, "https://resizer.otstatic.com/v2/photos/wide-huge/1/24094511.jpg,https://cpnewcastlehotel.co.uk/wp-content/uploads/sites/80/2019/08/Crowne-Plaza-Newcastle-afternoon-tea-restaurant-1536x864.png", "https://cpnewcastlehotel.co.uk/wp-content/uploads/sites/80/2023/04/Hawthorns-Menu-A3.pdf", "https://cpnewcastlehotel.co.uk/dining/hawthorns-restaurant/"),
(7, "Turtle Bay Newcastle", "117 Newgate St, Newcastle upon Tyne NE1 5RZ", "01912304002", 54.97427351582497, -1.616762764174787, 0, 3, "https://2.bp.blogspot.com/-DvpPV6U7vcg/VtLFVNiNtrI/AAAAAAAA3hQ/VyHWDaVx7q0/s1600/DSC_0522.jpg,https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/343820183_280914380929711_3425105956832597222_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=730e14&_nc_ohc=mmnKu47FMvMAX8S1Jhr&_nc_ht=scontent-lhr8-1.xx&oh=00_AfBYLjvRcx8LsWlPK0QCpcn8Ey1DATgOULDsROhv5tvjVQ&oe=6456F406,https://scontent-lhr8-2.xx.fbcdn.net/v/t39.30808-6/344312519_791898025421198_5446631932500156145_n.jpg?_nc_cat=102&ccb=1-7&_nc_sid=730e14&_nc_ohc=hUFgyriLcwkAX_2ourG&_nc_ht=scontent-lhr8-2.xx&oh=00_AfAFMSCUgs2K4mQjl2OggiukaktlXYvAmMJ6MgT0tm74Ag&oe=6457FD13", "https://turtlebay.co.uk/menu/newcastle", "https://turtlebay.co.uk/"),
(8, "Happiness Inn", "91A Percy St, Newcastle upon Tyne NE1 7RW", "01912325969", 54.97694700530153, -1.6157683706138162, 0, 4, "https://foto.sluurpy.com/locali/gb/1662462/51530863.jpg,https://scontent-lhr8-1.xx.fbcdn.net/v/t1.6435-9/119515342_762719694514664_8729429487920454586_n.jpg?_nc_cat=110&ccb=1-7&_nc_sid=cdbe9c&_nc_ohc=U7I6Qly86t0AX_KVZcp&_nc_ht=scontent-lhr8-1.xx&oh=00_AfCBRN_wJIq8WMAziA0MgkXBO85OSNqkyacKjrzIQr-2jA&oe=6479F157,https://scontent-lhr8-1.xx.fbcdn.net/v/t1.6435-9/119595673_762719974514636_6998809928225155173_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=cdbe9c&_nc_ohc=-lVGwwS3M50AX-C0Atz&_nc_ht=scontent-lhr8-1.xx&oh=00_AfDvdWxJlha6J7qyvjkoyCqGy6tSsd6TY7SLQzqt7glsIg&oe=6479E9E8", "https://www.happinessinnrestaurant.com/menus-2", "https://www.happinessinnrestaurant.com/"),
(9, "Little Asia Gen 2.0 Chinese Restaurant Newcastle", "14 Stowell St, Newcastle upon Tyne NE1 4XQ","01912415723", 54.973322658770506, -1.6196848006482263, 0, 4, "https://scontent-lhr8-2.xx.fbcdn.net/v/t1.6435-9/134139539_140117684577912_2171593705511958301_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=e3f864&_nc_ohc=mQUWdhqPNz8AX-3yOtR&_nc_ht=scontent-lhr8-2.xx&oh=00_AfBuqThguqc7gjhL9VDcismX9OUI_F34I0HBjdXK7bVbCA&oe=6479F5D5,http://www.littleasia.co/wp-content/uploads/2021/10/little-Asia_hotpot.jpeg,http://www.littleasia.co/wp-content/uploads/2021/10/King-prawns-with-salt-and-pepper-1-768x1024.jpg", "https://mylittleasia.co.uk/menu.php", "https://mylittleasia.co.uk/index.php"),
(10, "Home Flavour Asian Restaurant Newcastle", "41 Gallowgate, Newcastle upon Tyne NE1 4SG", "01912611962", 54.973782529581904, -1.6191478684193346, 0, 4, "https://scontent-lhr8-2.xx.fbcdn.net/v/t1.6435-9/189801221_172436358215331_8885556325056704525_n.jpg?_nc_cat=102&ccb=1-7&_nc_sid=730e14&_nc_ohc=3Y6Tt1npsOQAX_pvCGe&_nc_ht=scontent-lhr8-2.xx&oh=00_AfBYbX6EGDh3qTa4ff8ETxX-XDJklqETOrKvFMjYLtaf3g&oe=647A0782,https://lh3.googleusercontent.com/p/AF1QipPmWtZrLlHNllDLoDOyJtwDF6znVk85HW4zgVY-=w1080-h608-p-no-v0", "https://www.alleatapp.com/menu-home-flavor-ne1", "https://home-flavour.business.site/"),
(11, "Kafeneon", "8 Bigg Market, Newcastle upon Tyne NE1 1UW", "01912602577", 54.97141158693999, -1.613633247036326, 0, 5, "https://static.wixstatic.com/media/0600a4_11dc43788ea04fc1924b0d354ab9e401~mv2.jpeg/v1/fill/w_1440,h_1539,al_c,q_90,enc_auto/0600a4_11dc43788ea04fc1924b0d354ab9e401~mv2.jpeg,https://static.wixstatic.com/media/0600a4_db31377e0bb7465a94c41a0de3ac1c9c~mv2.jpg/v1/fill/w_828,h_1005,al_c,q_85,enc_auto/0600a4_db31377e0bb7465a94c41a0de3ac1c9c~mv2.jpg,https://static.wixstatic.com/media/0600a4_534c410d3d224e01aef3f8d58f4baea9~mv2.jpeg/v1/fill/w_1254,h_1672,al_c,q_90,usm_0.66_1.00_0.01,enc_auto/0600a4_534c410d3d224e01aef3f8d58f4baea9~mv2.jpeg", "https://www.kafeneon.co.uk/menu", "https://www.kafeneon.co.uk/"),
(12, "My Delhi Newcastle Indian Restaurant", "87A Clayton St, Newcastle upon Tyne NE1 5PY", "01912302302", 54.971423848936595, -1.6176029161910437, 0, 6, "https://static.wixstatic.com/media/cfb8cc_304cdf1b29704fbeb8974a6c1e99a5f1~mv2.jpg/v1/fill/w_1832,h_844,al_c,q_85,enc_auto/cfb8cc_304cdf1b29704fbeb8974a6c1e99a5f1~mv2.jpg,https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/312026626_1057307438298755_3219438048099113619_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=e3f864&_nc_ohc=Dy02DaGucmQAX_YoOaz&_nc_ht=scontent-lhr8-1.xx&oh=00_AfA__mqcJOxINbyDURVyY1R4nhfFjQHK1iCVz3x_HsDOsQ&oe=64572CFA,https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/343759472_169737476034681_4154577412932257767_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=8bfeb9&_nc_ohc=pK9Hg9ulzwAAX-QSPPv&_nc_oc=AQn6psat853pweL8B1xBX9SU0QLR3FCIatjjP7Pej4icHWTcmmzWSS0RwujtIVNap_4&_nc_ht=scontent-lhr8-1.xx&oh=00_AfB-KhWlOnXPjKlHhCtW5eJF41KlSWv8B1fBO7ErFAi6jQ&oe=6457C1CE", "https://www.mydelhistreetfood.com/menus", "https://www.mydelhistreetfood.com/"),
(13, "Ayla @ Grey Street", "17 Grey St, Newcastle upon Tyne NE1 6EE", "01912612299", 54.97135610879014, -1.6113694628645543, 0, 6, "https://aylagreystreet.co.uk/Ayla_-3.jpg,https://scontent-lhr8-2.xx.fbcdn.net/v/t39.30808-6/318559597_572096574691692_1567831137110307649_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=8bfeb9&_nc_ohc=sgI618RIPksAX_i1Nca&_nc_ht=scontent-lhr8-2.xx&oh=00_AfCL_VUWoVMf17HdZu3lxWOsjBjgsv6sfYBSgkCbDSeK9A&oe=6456BBFE,https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/319159399_6044480995585567_8895332651675575584_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=730e14&_nc_ohc=AjDF9We7QR0AX_80kPw&_nc_ht=scontent-lhr8-1.xx&oh=00_AfACzf5CBWvOkT467BSk5vwwDb5YGWFPZX2uN6dANLbJTg&oe=6457F6A2", "https://aylagreystreet.co.uk/Evening-Menu.pdf", "https://aylagreystreet.co.uk/"),
(14, "Simla Restaurant", "39 Side, Newcastle upon Tyne NE1 3JE", "07917391319", 54.969437171838834, -1.6091155600973388, 0, 6, "https://simlarestaurant.net/wp-content/uploads/2019/02/IMG_8216.jpg,https://scontent-lhr8-2.xx.fbcdn.net/v/t39.30808-6/241660397_2195323783980667_5480597114807120517_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=a26aad&_nc_ohc=qQk4Sm_gXcsAX8_jWNZ&_nc_ht=scontent-lhr8-2.xx&oh=00_AfBdL1uFf4gf45LIUrDLD2rd-1siF1Y6GHMxc4fl38S4ZQ&oe=6457761D,https://scontent-lhr8-2.xx.fbcdn.net/v/t39.30808-6/241135326_2188559217990457_6055048439429569689_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=a26aad&_nc_ohc=2v0gmULKu1EAX_S67Ox&_nc_ht=scontent-lhr8-2.xx&oh=00_AfDo7O9I2u5pArjvVbQH7PrF2V-kzrnDiKxonfr2irdBGQ&oe=64583D50", "https://simlarestaurant.net/wp-content/uploads/2022/12/102565-Simla-Restaurant-Food-Menu-dec22.pdf", "https://simlarestaurant.net/"),
(15, "Uno's Trattoria", "18 Sandhill, Newcastle upon Tyne NE1 3AF", "01912615264", 54.97003094178344, -1.6077785652545007, 0, 7, "https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/339284723_231685562678102_6098048111582643772_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=8bfeb9&_nc_ohc=EP-mfNa9FxUAX9yiNBQ&_nc_ht=scontent-lhr8-1.xx&oh=00_AfDudgpEvqAkrt1txZ95IoCENi-0ICMAX1nIX6bx59ZkrQ&oe=64568C42,https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/331082482_1242119993045314_1828850780234703509_n.jpg?_nc_cat=110&ccb=1-7&_nc_sid=730e14&_nc_ohc=DnAWzvjKl1MAX95iZZ3&_nc_ht=scontent-lhr8-1.xx&oh=00_AfCGGcV_wuGMUSX29b6XTRWsEK-uV5YLLG2s7D6GyHnsHQ&oe=64585AD3,https://scontent-lhr8-2.xx.fbcdn.net/v/t39.30808-6/328685782_551032756983671_2762139045323312856_n.jpg?_nc_cat=102&ccb=1-7&_nc_sid=730e14&_nc_ohc=8u-v6gbOCkAAX9GdjpX&_nc_ht=scontent-lhr8-2.xx&oh=00_AfAzY0itfb4SaPPVhRG0s2fUPjUUTV_N7nz96M9IDjUoXg&oe=6458736B", "https://www.unosrestaurant.co.uk/wp-content/uploads/2023/04/unos-menu-mar23.pdf", "https://www.unosrestaurant.co.uk/"),
(16, "Fratello's Jesmond Newcastle", "Jesmond Rd, Jesmond, Newcastle upon Tyne NE2 1PR", "01912125500", 54.98394008492414, -1.602402965403959, 0, 7, "https://static.wixstatic.com/media/afa310_fba8e610d9a44943971296751d32e99e~mv2.jpg/v1/fill/w_1170,h_853,al_c,q_85,enc_auto/afa310_fba8e610d9a44943971296751d32e99e~mv2.jpg,https://scontent-lhr8-1.xx.fbcdn.net/v/t1.6435-9/167268592_5632153773461735_4254271955253453006_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=730e14&_nc_ohc=qkI57LdOY6UAX8C7pfT&_nc_ht=scontent-lhr8-1.xx&oh=00_AfCfWAqi9KM8ebq41t2DvJFEfX6wfH8_Rsko9wRYX-0Xzw&oe=647A1B2E,https://scontent-lhr8-2.xx.fbcdn.net/v/t1.6435-9/87773151_3880653748611755_7233461496175394816_n.jpg?_nc_cat=102&ccb=1-7&_nc_sid=730e14&_nc_ohc=L1Ewv-OhKzAAX90XHkG&_nc_ht=scontent-lhr8-2.xx&oh=00_AfC9eETe5yFKSu4T9G7fbW-K8i4f_JafOLeV32LFffTXXA&oe=6479EDCD", "https://www.opentable.co.uk/fratellos-jesmond-newcastle?ref=4208,", "https://www.fratellosrestaurants.co.uk/jesmond"),
(17, "Gino D'Acampo Restaurant Quayside Newcastle", "The Quayside, Bridge Court, Newcastle upon Tyne NE1 3BE", "01919336994", 54.967421531511214, -1.6108538446886334, 0, 7, "https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/292604535_360785416204445_882292248163946289_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=e3f864&_nc_ohc=kzRLf78QRSQAX9qknlo&_nc_ht=scontent-lhr8-1.xx&oh=00_AfCK0-iJ4vEKwkx-IahyDywxlcWSPum_A1QC6y5HuqwYog&oe=64577499https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/332908383_1544752242678510_6380068570035376378_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=8bfeb9&_nc_ohc=ANR88eBYBDYAX-aA6PL&_nc_ht=scontent-lhr8-1.xx&oh=00_AfAgfMZaIlO8UljVGe1MbgAOS2BTVzKHSNL7J9rZwxA3-g&oe=64586CB9,https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/331077944_1377808429661016_3216768213512251280_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=8bfeb9&_nc_ohc=oOIWQ1fGQ1oAX8JN6k4&_nc_ht=scontent-lhr8-1.xx&oh=00_AfDvAyJZRWCv8b_bjscqbwN5LOoRpT4yXR7Eft87mEYSOQ&oe=6458796B,https://scontent-lhr8-1.xx.fbcdn.net/v/t39.30808-6/326538416_516027020632484_6714052501423017323_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=8bfeb9&_nc_ohc=CSsoBiuJ60oAX-vMYvn&_nc_ht=scontent-lhr8-1.xx&oh=00_AfCeF2lRBMM-9Q-dUrLyOv5IVMsDCEnZJR48v9UmZDxNJQ&oe=6457AFE9", "https://www.opentable.co.uk/r/gino-dacampo-restaurant-newcastle-quayside-newcastle-upon-tyne?ref=4208", "https://ginodacampohotelsandleisure.com/our-restaurants/quayside/");






DROP TABLE IF EXISTS `operation_hour`;
CREATE TABLE `operation_hour` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT,
  `day_of_week` VARCHAR(9) NOT NULL,
  `opening_time` TIME,
  `closing_time` TIME,
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `operation_hour` (id, day_of_week, opening_time, closing_time) VALUES
-- 1
(1, "MONDAY", '11:30:00', '21:30:00'),
(2, "TUESDAY", '11:30:00', '21:30:00'),
(3, "WEDNESDAY", '11:30:00', '21:30:00'),
(4, "THURSDAY", '11:30:00', '21:30:00'),
(5, "FRIDAY", '11:30:00', '22:00:00'),
(6, "SATURDAY", '11:30:00', '22:00:00'),
(7, "SUNDAY", '11:30:00', '21:10:00'),
-- 2
(8, "MONDAY", '11:00:00', '21:00:00'),
(9, "TUESDAY", '11:00:00', '21:00:00'),
(10, "WEDNESDAY", '11:00:00', '21:00:00'),
(11, "THURSDAY", '11:00:00', '22:00:00'),
(12, "FRIDAY", '09:00:00', '22:00:00'),
(13, "SATURDAY", '09:00:00', '22:00:00'),
(14, "SUNDAY", '09:00:00', '22:00:00'),
-- 3
(15, "MONDAY", '11:00:00', '23:00:00'),
(16, "TUESDAY", '11:00:00', '21:00:00'),
(17, "WEDNESDAY", '11:00:00', '21:00:00'),
(18, "THURSDAY", '11:00:00', '23:59:59'),
(19, "FRIDAY", '09:00:00', '23:59:59'),
(20, "SATURDAY", '09:00:00', '23:59:59'),
(21, "SUNDAY", '11:00:00', '23:00:00'),
-- 4
(22, "MONDAY", '11:30:00', '22:30:00'),
(23, "TUESDAY", '11:30:00', '22:30:00'),
(24, "WEDNESDAY", '11:30:00', '22:30:00'),
(25, "THURSDAY", '11:30:00', '22:30:00'),
(26, "FRIDAY", '11:30:00', '23:30:00'),
(27, "SATURDAY", '11:30:00', '23:30:00'),
(28, "SUNDAY", '11:30:00', '22:30:00'),
-- 5
(29, "MONDAY", '11:30:00', '22:30:00'),
(30, "TUESDAY", '11:30:00', '23:59:59'),
(31, "WEDNESDAY", '12:00:00', '23:59:59'),
(32, "THURSDAY", '12:00:00', '23:59:59'),
(33, "FRIDAY", '12:00:00', '01:00:00'),
(34, "SATURDAY", '12:00:00', '01:00:00'),
(35, "SUNDAY", '12:00:00', '23:00:00'),
-- 6
(36, "MONDAY", '06:30:00', '21:30:00'),
(37, "TUESDAY", '06:30:00', '21:30:00'),
(38, "WEDNESDAY", '06:30:00', '21:30:00'),
(39, "THURSDAY", '06:30:00', '21:30:00'),
(40, "FRIDAY", '06:30:00', '21:30:00'),
(41, "SATURDAY", '06:30:00', '21:30:00'),
(42, "SUNDAY", '06:30:00', '21:30:00'),
-- 7
(43, "MONDAY", '11:00:00', '23:30:00'),
(44, "TUESDAY", '11:00:00', '23:30:00'),
(45, "WEDNESDAY", '11:00:00', '23:30:00'),
(46, "THURSDAY", '11:00:00', '00:30:00'),
(47, "FRIDAY", '11:00:00', '01:30:00'),
(48, "SATURDAY", '10:00:00', '01:30:00'),
(49, "SUNDAY", '11:00:00', '23:30:00'),
-- 8
(50, "MONDAY", '11:30:00', '22:00:00'),
(51, "TUESDAY", '11:30:00', '22:00:00'),
(52, "WEDNESDAY", '11:30:00', '22:00:00'),
(53, "THURSDAY", '11:30:00', '22:00:00'),
(54, "FRIDAY", '11:30:00', '22:00:00'),
(55, "SATURDAY", '11:30:00', '22:00:00'),
(56, "SUNDAY", '11:30:00', '22:00:00'),
-- 9
(57, "MONDAY", '12:00:00', '22:00:00'),
(58, "TUESDAY", '12:00:00', '22:00:00'),
(59, "WEDNESDAY", '12:00:00', '22:00:00'),
(60, "THURSDAY", '12:00:00', '22:00:00'),
(61, "FRIDAY", '12:00:00', '23:00:00'),
(62, "SATURDAY", '12:00:00', '23:00:00'),
(63, "SUNDAY", '12:00:00', '22:00:00'),
-- 10
/*(50 - 56)*/
-- 11
(64, "MONDAY", '11:00:00', '22:00:00'),
(65, "TUESDAY", '11:00:00', '22:00:00'),
(66, "WEDNESDAY", '11:00:00', '22:00:00'),
(67, "THURSDAY", '11:00:00', '22:00:00'),
(68, "FRIDAY", '11:00:00', '22:00:00'),
(69, "SATURDAY", '11:00:00', '22:00:00'),
(70, "SUNDAY", '11:00:00', '22:00:00'),
-- 12
(71, "MONDAY", '13:00:00', '22:00:00'),
(72, "TUESDAY", '13:00:00', '22:00:00'),
(73, "WEDNESDAY", '13:00:00', '22:00:00'),
(74, "THURSDAY", '13:00:00', '22:00:00'),
(75, "FRIDAY", '13:00:00', '22:00:00'),
(76, "SATURDAY", '13:00:00', '22:00:00'),
(77, "SUNDAY", '13:00:00', '22:00:00'),
-- 13
(78, "MONDAY", '05:00:00', '22:30:00'),
(79, "TUESDAY", '05:00:00', '22:30:00'),
(80, "WEDNESDAY", '05:00:00', '22:30:00'),
(81, "THURSDAY", '05:00:00', '22:30:00'),
(82, "FRIDAY", '05:00:00', '22:30:00'),
(83, "SATURDAY", '05:00:00', '23:30:00'),
(84, "SUNDAY", '05:00:00', '23:30:00'),
-- 14
(85, "MONDAY", '05:30:00', '23:00:00'),
(86, "TUESDAY", '05:30:00', '23:00:00'),
(87, "WEDNESDAY", '05:30:00', '23:00:00'),
(88, "THURSDAY", '05:30:00', '23:00:00'),
(89, "FRIDAY", '05:30:00', '23:00:00'),
(90, "SATURDAY", '05:30:00', '23:00:00'),
(91, "SUNDAY", '05:30:00', '23:00:00'),
-- 15
(92, "MONDAY", '12:00:00', '22:00:00'),
(93, "TUESDAY", '12:00:00', '22:00:00'),
(94, "WEDNESDAY", '12:00:00', '22:00:00'),
(95, "THURSDAY", '12:00:00', '22:00:00'),
(96, "FRIDAY", '12:00:00', '22:30:00'),
(97, "SATURDAY", '12:00:00', '22:30:00'),
(98, "SUNDAY", '12:00:00', '21:30:00'),
-- 16
(99, "MONDAY", '05:00:00', '22:00:00'),
(100, "TUESDAY", '05:00:00', '22:00:00'),
(101, "WEDNESDAY", '05:00:00', '22:00:00'),
(102, "THURSDAY", '05:00:00', '22:00:00'),
(103, "FRIDAY", '05:00:00', '22:00:00'),
(104, "SATURDAY", '05:00:00', '22:00:00'),
(105, "SUNDAY", '05:00:00', '22:00:00'),
-- 17
(106, "MONDAY", '12:00:00', '22:30:00'),
(107, "TUESDAY", '12:00:00', '22:30:00'),
(108, "WEDNESDAY", '12:00:00', '22:30:00'),
(109, "THURSDAY", '12:00:00', '22:30:00'),
(110, "FRIDAY", '12:00:00', '22:30:00'),
(111, "SATURDAY", '12:00:00', '22:30:00'),
(112, "SUNDAY", '12:00:00', '22:30:00');













CREATE TABLE `restaurant_operation_hour` (
  `restaurant_id` BIGINT UNSIGNED NOT NULL,
  `operation_hour_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`restaurant_id`, `operation_hour_id`),
  FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`operation_hour_id`) REFERENCES `operation_hour` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `restaurant_operation_hour` (restaurant_id, operation_hour_id) VALUES 
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),

(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14),

(3, 15),
(3, 16),
(3, 17),
(3, 18),
(3, 19),
(3, 20),
(3, 21),

(4, 22),
(4, 23),
(4, 24),
(4, 25),
(4, 26),
(4, 27),
(4, 28),

(5, 29),
(5, 30),
(5, 31),
(5, 32),
(5, 33),
(5, 34),
(5, 35),

(6, 36),
(6, 37),
(6, 38),
(6, 39),
(6, 40),
(6, 41),
(6, 42),

(7, 43),
(7, 44),
(7, 45),
(7, 46),
(7, 47),
(7, 48),
(7, 49),

(8, 50),
(8, 51),
(8, 52),
(8, 53),
(8, 54),
(8, 55),
(8, 56),

(9, 57),
(9, 58),
(9, 59),
(9, 60),
(9, 61),
(9, 62),
(9, 63),

(10, 50),
(10, 51),
(10, 52),
(10, 53),
(10, 54),
(10, 55),
(10, 56),

(11, 64),
(11, 65),
(11, 66),
(11, 67),
(11, 68),
(11, 69),
(11, 70),


(12, 71),
(12, 72),
(12, 73),
(12, 74),
(12, 75),
(12, 76),
(12, 77),

(13, 78),
(13, 79),
(13, 80),
(13, 81),
(13, 82),
(13, 83),
(13, 84),

(14, 85),
(14, 86),
(14, 87),
(14, 88),
(14, 89),
(14, 90),
(14, 91),

(15, 92),
(15, 93),
(15, 94),
(15, 95),
(15, 96),
(15, 97),
(15, 98),

(16, 99),
(16, 100),
(16, 101),
(16, 102),
(16, 103),
(16, 104),
(16, 105),

(17, 106),
(17, 107),
(17, 108),
(17, 109),
(17, 110),
(17, 111),
(17, 112);

/*
(18, 120),
(18, 121),
(18, 122),
(18, 123),
(18, 124),
(18, 125),
(18, 126),

(19, 127),
(19, 128),
(19, 129),
(19, 130),
(19, 131),
(19, 132),
(19, 133),

(20, 134),
(20, 135),
(20, 136),
(20, 137),
(20, 138),
(20, 139),
(20, 140); */

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
	`id` BIGINT UNSIGNED AUTO_INCREMENT,
    `item_name` VARCHAR(255) NOT NULL,
    `price` DOUBLE NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `menu`(id, item_name, price) VALUES 
-- 1
(1, "Six Wings", 14.50),
(2, "Nine Wings", 19.00),
(3, "Ultimate Cheese Dipper", 19.25),
(4, "The Deluxe Wagyu Burger", 19.25),
(5, "To Vegan and Beyond", 15.25),
(6, "Fridays® Glazed Chicken Burger", 15.50),
(7, "Chicken Quesadilla", 14.75),
(8, "Tex-Mex Salad", 9.25),
(9, "Chicken Fingers", 15.35),
(10, "Cajun Spiced Chicken", 15.85),
(11, "Meatless Chicken", 15.85),
(12, "Plant-Based Cheese & Bacon", 8.00),
(13, "Plant-Based Chicken Nachos", 7.55),
(14, "Meatless Chicken", 15.85),
-- 2
(15, "West Coast Classic", 11.00),
(16, "Brooklyn's Finest Cheesburger", 10.50),
(17, "New Mexico", 12.00),
(18, "French Canadian Og", 13.00),
(19, "Spicy Chicken Rancher", 9.50),
(20, "Quarter Pounder", 11.50),
(21, "Texan Bbq Bacon", 12.50),
(22, "Yellowstone", 12.00),
(23, "Slugger", 9.50),
-- 3
(24, "Hamburger", 8.65),
(25, "Cheeseburger", 9.95),
(26, "Bacon Burger", 10.45),
(27, "Bacon Cheeseburger", 10.65),
(28, "Cheese Dog", 7.35),
(29, "Bacon Dog", 7.85),
(30, "Veggie Sandwich", 5.50),
-- 4
(31, "Wild Bill", 13.90),
(32, "Swiss Tony", 13.90),
(33, "Fat Hippo", 14.90),
(34, "Born Slippy", 14.90),
(35, "Honey Monsta", 17.90),
(36, "Semi-Parmed Life", 13.90),
(37, "Hangover III", 13.90),
(38, "Great White Buffalo", 13.90),
(39, "Kula Shaker", 13.90),
(40, "Five Lies", 12.90),
(41, "Harlem Fake", 13.90),
(42, "Notorious Vfc 2.0", 13.90),
-- 5
(43, "Chicken & Chorizo Hanging Kebab", 16.50),
(44, "Chicken Hanging Kebab", 15.50),
(45, "Salt and Pepper Pork Belly Hanging Kebab", 16.50),
(46, "Crispy Halloumi Hanging Kebab", 15.50),
(47, "Lamb Kofta Hanging Kebab", 16.50),
(48, "Superfood Salad", 11.95),
(49, "Steak, Stout and Stilton Pie", 14.25),
(50, "Katsu Curry", 15.50),
(51, "Ribeye Steak", 23.50),
(52, "Cheeseburger", 15.50),
(53, "Grilled Nduja Chicken Burger", 15.50),
(54, "Sharing Kebab", 29.95),
(55, "Crispy Chicken Burger", 15.50),
(56, "Plant Based Burger", 15.50),
(57, "Fish and Chips", 14.95),
-- 6
(58, "Panko Breaded Chicken", 16.95),
(59, "Shredded Chicken Tagliatelle", 15.95),
(60, "Slow Cooked Pork Belly", 19.95),
(61, "Fish & Chunky Chips", 18.95),
(62, "Pan Seared Seabass", 16.95),
(63, "Thai Red Vegetable Curry", 14.95),
(64, "Four Cheese Pizza", 14.95),
(65, "Pepperoni Pizza", 15.95),
-- 7
(66, "Curry Goat Hash", 13.30),
(67, "Jamaican Run Down", 13.30),
(68, "Fish Fry", 15.30),
(69, "Kingston Fried Chicken with Spiced Fries", 13.30),
(70, "Kingston Fried Chicken with Mac & Cheese", 13.30),
(71, "Spicy Mac & Cheese with Jerk Mushrooms", 13.30),
(72, "Spicy Mac & Cheese with Jerk Chicken", 13.30),
(73, "Spicy Mac & Cheese with Jerk Bacon", 13.30),
-- 8
(74, "Stir-fried Pig's Livers", 11.80),
(75, "Stir-Fried Spicy Oden Mix", 15.80),
(76, "Special Fried Pig's Kidney", 12.50),
(77, "Fish Fillet In Sichuan Peppercorn Chili Oil", 14.50),
(78, "Chopped Chicken With Green & Red Pepper In Special Chili Sauce", 14.80),
(79, "Steam Fish Fillet On Soft Noodle In Special Chili Sauce", 14.80),
(80, "Sliced Beef & Enoki Mushroom In Sichuan Chili Sauce", 12.80),
(81, "Deep Fried Butternut Squash In Egg Tolk Sauce", 11.80),
(82, "Chef's Special Braised Pork Belly", 13.80),
(83, "Stir-Fried Tomato With Egg", 8.50),
(84, "Kong Pao Dicked Chicken", 9.80),
(85, "Deep-Fried Chicken With Oyster Sauce And Fruit Syrup", 10.50),
-- 9
(86, "King Prawns In Black Pepper Sauce", 10.90),
(87, "King Prawns With Ginger And Spring Onion", 10.90),
(88, "Crispy King Prawns In Szechuan Style", 10.90),
(89, "King Prawns With Mixed Vegetables", 10.90),
(90, "Lobster With Ginger And Spring Onion", 35.80),
(91, "Lobster With Green Peppers In Black Bean Sauce", 35.80),
(92, "Crispy Lobster In Szechuan Style", 35.80),
(93, "Special In Curry Sauce", 10.90),
(94, "Crispy Special In Honey Chilli Sauce", 10.90),
(95, "Crispy Special In Kung Po Sauce", 10.90),
(96, "Crispy Special In Capital Sauce", 10.90),
(97, "Crispy Shredded Chicken In Capital Sauce", 9.90),
(98, "Crispy Shredded Chicken With Lemon Sauce", 9.90),
(99, "Crispy Shredded Chicken In Sweet And Sour Sauce", 9.90),
(100, "Chicken In Curry Sauce", 9.90),
(101, "Cripsy Shredded Beef With Chilli", 9.90),
(102, "Beef With Green Pepper", 9.90),
(103, "Squid In Black Pepper Sauce", 10.90),
(104, "Squid With Ginger And Spring Onion", 10.90),
-- 10
(105, "64 Sauteed Beef with Chilli Sauce", 11.50),
(106, "Smoked Chicken", 11.50),
(107, "Fried Fish Fillets in Black Vinegar", 12.50),
(108, "Braised Pork Belly with Preserved Mustard Green", 12.80),
(109, "Braised Pork Belly", 12.80),
(110, "Twice-Cooked Pork Belly", 12.80),
(111, "Red Vinesse Pork", 12.80),
(112, "Braised Pig's Trotters with Fermented Red Bean Curb", 12.80),
(113, "Stir Fried Mixed Seafoods", 13.80),
-- 11
(114, "Chicken Souvlaki", 12.95),
(115, "Pork Souvlaki", 12.95),
(116, "Beef Stifado", 12.95),
(117, "Moussaka", 11.95),
(118, "Vegetarian Moussaka", 11.95),
(119, "Big Fat Greek Gyros", 15.95),
(120, "Yemista", 11.95),
(121, "Lamb Kleftiko", 16.95),
(122, "Pastitsio", 12.95),
(123, "Lamb & Halloumi Burger", 12.95),
(124, "Chicken & Halloumi Salad", 10.95),
(125, "Greek Salad", 8.95),
-- 12
(126, "The Emperor's Stew", 13.50),
(127, "Railway Station Lamb Curry", 13.00),
(128, "Delhi Kadhai Gosht", 13.00),
(129, "Butter Chicken 1950s", 12.00),
(130, "Kozi Curry", 12.00),
(131, "South Indian Fish Curry", 12.00),
(132, "Grandma's Aloo Matar", 9.90),
(133, "Harappa Smoked Aubergines", 11),
(134, "Dilli Paneer Butter Masala", 10.5),
(135, "Lucknow's Royal Chicken Biriyani", 13.5),
(136, "Punjabi Dal Tadka", 7.8),
-- 13
(137, "Chilli Mango Garlic Grilled Murgh", 11.95),
(138, "Paneer Shashlick", 10.95),
(139, "Aylas Mixed Grill Platter", 16.95),
(140, "Kachi Chops", 15.90),
(141, "Paneer Shashlick", 10.95),
(142, "Haldi Chilli Grilled Seabass", 16.50),
(143, "Murgh Tikka Kebab", 12.50),
(144, "Palak Chingri", 18.90),
(145, "Lasooni Grilled Chingri Salon", 18.90),
(146, "Meen Molee Kerala Fish Curry", 18.95),
(147, "Goan Fish Curry", 17.95),
(148, "Salmon Tenga", 17.00),
(149, "King Prawn Biriyani", 17.95),
(150, "Vegetable Biriyani", 13.55),
-- 14
(151, "Lamb shank kashmiri rogan josh", 20.00),
(152, "Bengal mustard salmon", 16.00),
(153, "Jardaloo lamb", 16.00),
(154, "Goan fish curry", 16.00),
(155, "Lamb tadka", 16.00),
(156, "Chingri malai curry", 19.00),
(157, "Nawabi biryani chicken", 15.00),
(158, "Nawabi biryani lamb", 17.00),
(159, "Jinga moolie", 19.00),
(160, "Murgh Malbari Maharaja", 14.00),
(161, "Chicken (on the bone)", 13.00),
(162, "Lamb chops", 17.00),
(163, "Grilled masala fish", 16.00),
(164, "Mixed khazana (our mixed grill)", 20.00),
(165, "Madras chicken", 12.00),
(166, "Madras lamb", 14.00),
-- 15
(167, "Chicken Roma", 14.50),
(168, "Chicken Kiev", 12.95),
(169, "Chicken au Poivre", 11.95),
(170, "Chicken Uno Style", 11.95),
(171, "Chicken Milanese", 11.95),
(172, "Chicken Neptune", 15.50),
(173, "Pork Uno Style", 11.95),
(174, "Pressed Belly Pork", 13.95),
(175, "Sirloin Steak", 16.50),
(176, "Fillet Steak", 19.50),
(177, "Surf ‘n’ Turf", 23.50),
(178, "Salmon Steak", 13.95),
(179, "Sea Bass", 15.50),
(180, "Gamberoni", 15.95),
-- 16 
(181, "Verdura", 11.50),
(182, "Fratello's", 13.50),
(183, "Barbabietola", 12.50),
(184, "Polpette di Bolognese", 12.50),
(185, "Royale", 13.00),
(186, "Picante", 12.50),
(187, "Carne", 13.00),
(188, "Fungi di Basilica", 12.0),
(189, "Pancetta di Pollo", 12.00),
(190, "Quattro Fromaggi", 12.00),
(191, "Pizza Napoli", 11.50),
(192, "Pomodoro", 9.95),
(193, "Classic Carbonara", 12.00),
(194, "Fratello's", 12.00),
(195, "Bolognese", 12.00),
(196, "Crema di Funghi", 11.95),
(197, "Verdura di Crema", 10.95),
(198, "Gamberoni Picante", 13.00),
(199, "Butterfly Chicken Breast", 13.50),
(200, "8oz Sirloin", 22.50),
-- 17
(201, "Pino Spritz", 8.85),
(202, "Little Italy", 8.95),
(203, "Strawberry & Nectarine Spritz", 8.85),
(204, "Mango Fandango", 8.85),
(205, "Negroni", 8.25),
(206, "Aperol Spritz", 8.50),
(207, "The Pineapple Express", 8.75),
(208, "Grapefruit & Raspberry Spritz", 8.85),
(209, "Dunky Dunky Tomato Scarpetta", 13.25),
(210, "Fantastico", 25.00),
(211, "Cicchetti 4 You", 22.50),
(212, "Arancine", 8.95),
(213, "King Prawn Bruschetta", 9.95),
(214, "Calamari", 9.50),
(215, "Ribollita Soup", 7.50),
(216, "Mussel & Clam Stew", 10.50),
(217, "Carpaccio", 12.50),
(218, "Burrata with Fresh Tomato & Chilli Salsa", 9.95),
(219, "Chicken Liver Pâté", 8.25),
(220, "King Prawn Bruschetta", 9.95),
(221, "Ribollita Soup", 7.50);



-- 18





    
DROP TABLE IF EXISTS `restaurant_menu`;
CREATE TABLE `restaurant_menu` (
  `restaurant_id` BIGINT UNSIGNED NOT NULL,
  `menu_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`restaurant_id`, `menu_id`),
  FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `restaurant_menu` VALUES 
(1, 1),   
(1, 2),   
(1, 3),   
(1, 4),   
(1, 5),   
(1, 6),   
(1, 7),   
(1, 8),   
(1, 9),   
(1, 10),   
(1, 11),   
(1, 12),   
(1, 13),   
(1, 14),

(2, 15),
(2, 16),
(2, 17),
(2, 18),
(2, 19),
(2, 20),
(2, 21),
(2, 22),
(2, 23),

(3, 24),
(3, 25),
(3, 26),
(3, 27),
(3, 28),
(3, 29),
(3, 30),

(4, 31),
(4, 32),
(4, 33),
(4, 34),
(4, 35),
(4, 36),
(4, 37),
(4, 38),
(4, 39),
(4, 40),
(4, 41),
(4, 42),

(5, 43),
(5, 44),
(5, 45),
(5, 46),
(5, 47),
(5, 48),
(5, 49),
(5, 50),
(5, 51),
(5, 52),
(5, 53),
(5, 54),
(5, 55),
(5, 56),
(5, 57),

(6, 58),
(6, 59),
(6, 60),
(6, 61),
(6, 62),
(6, 63),
(6, 64),
(6, 65),

(7, 66),
(7, 67),
(7, 68),
(7, 69),
(7, 70),
(7, 71),
(7, 72),
(7, 73),

(8, 74),
(8, 75),
(8, 76),
(8, 77),
(8, 78),
(8, 79),
(8, 80),
(8, 81),
(8, 82),
(8, 83),
(8, 84),
(8, 85),

(9, 86),
(9, 87),
(9, 88),
(9, 89),
(9, 90),
(9, 91),
(9, 92),
(9, 93),
(9, 94),
(9, 95),
(9, 96),
(9, 97),
(9, 98),
(9, 99),
(9, 100),
(9, 101),
(9, 102),
(9, 103),
(9, 104),

(10, 105),
(10, 106),
(10, 107),
(10, 108),
(10, 109),
(10, 110),
(10, 111),
(10, 112),
(10, 113),

(11, 114),
(11, 115),
(11, 116),
(11, 117),
(11, 118),
(11, 119),
(11, 120),
(11, 121),
(11, 122),
(11, 123),
(11, 124),
(11, 125),

(12, 126),
(12, 127),
(12, 128),
(12, 129),
(12, 130),
(12, 131),
(12, 132),
(12, 133),
(12, 134),
(12, 135),
(12, 136),

(13, 137), 
(13, 138), 
(13, 139), 
(13, 140), 
(13, 141), 
(13, 142),
(13, 143), 
(13, 144), 
(13, 145), 
(13, 146), 
(13, 147), 
(13, 148), 
(13, 149), 
(13, 150),

(14, 151),
(14, 152),
(14, 153),
(14, 154),
(14, 155),
(14, 156),
(14, 157),
(14, 158),
(14, 159),
(14, 160),
(14, 161),
(14, 162),
(14, 163),
(14, 164),
(14, 165),
(14, 166),

(15, 167),
(15, 168),
(15, 169),
(15, 170),
(15, 171),
(15, 172),
(15, 173),
(15, 174),
(15, 175),
(15, 176),
(15, 177),
(15, 178),
(15, 179),
(15, 180),

(16, 181),
(16, 182),
(16, 183),
(16, 184),
(16, 185),
(16, 186),
(16, 187),
(16, 188),
(16, 189),
(16, 190),
(16, 191),
(16, 192),
(16, 193),
(16, 194),
(16, 195),
(16, 196),
(16, 197),
(16, 198),
(16, 199),
(16, 200),

(17, 201),
(17, 202),
(17, 203),
(17, 204),
(17, 205),
(17, 206),
(17, 207),
(17, 208),
(17, 209),
(17, 210),
(17, 211),
(17, 212),
(17, 213),
(17, 214),
(17, 215),
(17, 216),
(17, 217),
(17, 218),
(17, 219),
(17, 220),
(17, 221);








   

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` CHAR(68),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
	`id` BIGINT AUTO_INCREMENT,
    `comment` VARCHAR(255),
    `rating` INT,
    `user_id` BIGINT UNSIGNED,
    `restaurant_id` BIGINT UNSIGNED,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL, 
    FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


	




