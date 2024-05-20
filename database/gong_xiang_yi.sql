/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : gong_xiang_yi

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 16/05/2024 17:19:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_num` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (0, '所有', 22);
INSERT INTO `category` VALUES (1, '衣物', 8);
INSERT INTO `category` VALUES (2, '书', 6);
INSERT INTO `category` VALUES (3, '家具', 8);

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `category_id` int(0) NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `color` varchar(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `size` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `shape` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `purchase_time` datetime(0) NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cover_img` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES (1, 1, 1, '牛仔裤', 'd啊撒旦阿斯顿', '', '啊沙达式', '', '2024-04-20 00:00:00', '裤子，牛仔裤，99新', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/73c923d2-6f5f-4a63-adaf-4c1ba3be1e23.png', '1513222222222', '', '撒地方', '2024-04-18 15:54:24', '2024-05-06 18:43:45');
INSERT INTO `good` VALUES (2, 1, 1, '长筒牛仔裤', '', '1.7', '人形', '7-8成新', '2023-04-01 00:00:00', '<p>长筒牛仔裤</p>', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/cc1f59ff-b63e-4c54-a025-742279478493.png', '111222333', '222@qq.com', '123', '2024-04-18 16:01:22', '2024-05-06 18:44:21');
INSERT INTO `good` VALUES (3, 1, 3, 'T恤', '黑色', '小', '无', '9-10成新', '2021-04-01 00:00:00', '<p>这是一个恤</p>', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/24b90fdf-8f22-4b00-b575-3210003d1577.png', '111222333', '222@aaa.com', '啊啊啊啊啊', '2024-04-18 16:16:56', '2024-05-06 18:44:53');
INSERT INTO `good` VALUES (4, 1, 0, '钱包', '蓝绿', '大', '长方形', '9-10成新', '2024-04-05 00:00:00', '<p>破旧钱包</p>', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/5a431c29-a2da-43e0-80d0-08db89889cd3.png', '111222333', 'qqqeee@dd', '无', '2024-04-18 16:18:42', '2024-05-06 18:46:25');
INSERT INTO `good` VALUES (5, 1, 3, '背包', '蓝白', '中', '长方形', '9-10成新', '2022-04-01 00:00:00', '<p>一个蓝色书包</p>', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/d96c8c00-4314-4834-8dc1-cc5040f911b2.png', '11223344', '444@qq.cm', 'fadsfdsa ', '2024-04-18 16:43:10', '2024-05-06 18:46:50');
INSERT INTO `good` VALUES (6, 1, 3, '水杯', '棕黑', '小', '心', '9-10成新', '2021-04-07 00:00:00', '<p>水杯</p>', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/46f3fa5d-0078-445c-8cbb-d624afabe288.png', '11223344', '33@qq.com', 'asdf ', '2024-04-18 16:44:53', '2024-05-06 19:21:34');
INSERT INTO `good` VALUES (7, 1, 2, '书2', '白', '小', '长方形', '9-10成新', '2024-04-01 00:00:00', '', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/25da937e-db2f-4d3c-95a3-40c479ac994c.png', '11222333', '44@qq.com', 'asdf ', '2024-04-18 16:50:09', '2024-05-06 19:21:52');
INSERT INTO `good` VALUES (8, 1, 1, '盘子', '绿色', '小', '长方形', '9-10成新', '2023-04-29 00:00:00', '', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/b954585f-0cb8-452d-86b9-1d5b4f55ce55.webp', '1122112', '45@qq.com', 'sdaf a', '2024-04-18 16:51:07', '2024-05-06 19:25:15');
INSERT INTO `good` VALUES (19, 1, 2, '盘子', '淡黄', '小', '正方形', '9-10成新', '2024-04-30 00:00:00', '无', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/77cca271-84c0-4bd2-ae7c-90f9413d0443.webp', '121212121', 'Lhw258333@gmail.com', '无', '2024-04-20 14:49:20', '2024-05-06 19:26:53');
INSERT INTO `good` VALUES (20, 1, 1, '展物台', 'sa', '', '', '', '2024-04-18 00:00:00', '', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/5329d4ac-fa4c-4a11-9971-f28bbf4f09e7.png', '', '', '', '2024-04-20 15:49:54', '2024-05-06 19:27:32');
INSERT INTO `good` VALUES (21, 1, 1, '围巾', '', '', '', '', '2024-05-14 00:00:00', '啊啊啊', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/8cebdb9a-fc52-4b64-92cf-bd0acd3f0a52.png', '', '', '', '2024-04-20 16:03:35', '2024-05-06 19:28:06');
INSERT INTO `good` VALUES (25, 3, 3, '紫色果盆', '', '', '', '', '2024-05-17 00:00:00', '', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/09a907eb-c2ba-4644-95a4-5794b44c808a.png', '', '', '', '2024-04-21 21:30:55', '2024-05-06 19:35:21');
INSERT INTO `good` VALUES (27, 2, 3, '地毯', '', '', '', '7-8成新', '2024-05-01 00:00:00', '', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/08f34bac-9452-41b5-b0cc-11bf0e57d3bb.png', '11111111', '111111@qq.com', 'qq', '2024-04-23 22:36:54', '2024-05-06 19:08:05');
INSERT INTO `good` VALUES (28, 4, 1, '瓷碗', '', '', '', '', NULL, '瓷碗', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/713972a0-67f6-46b9-8437-e6ec2f027352.png', '', '', '', '2024-04-24 20:52:07', '2024-05-06 19:29:04');
INSERT INTO `good` VALUES (29, 2, 3, '锅', '', '', '', '7-8成新', '2024-05-07 00:00:00', '瓷锅', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/69813745-b0d0-48c6-ba92-f0a6fad98afd.png', '11111111111', '111111111@qq.ocm', '11111', '2024-05-06 19:07:36', '2024-05-06 19:07:36');
INSERT INTO `good` VALUES (30, 2, 3, '玩偶', '', '', '', '9-10成新', '2024-04-30 00:00:00', '玩偶一只', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/c3b66c73-8227-4028-a8ae-e74b87ab4122.png', '1111111', '11111@qq.com', 'qq.com', '2024-05-06 19:08:51', '2024-05-06 19:08:51');
INSERT INTO `good` VALUES (31, 2, 2, '书1', '', '', '', '9-10成新', '2024-04-30 00:00:00', '一本外国书', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/1661e065-6daa-46c3-b356-f6e755feae4c.png', '11111', '111111@qq.com', '1231', '2024-05-06 19:18:48', '2024-05-06 19:18:48');
INSERT INTO `good` VALUES (32, 2, 3, '抹布', '', '', '', '7-8成新', '2024-05-07 00:00:00', '粉色抹布', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/48bca91a-b0f5-49b2-bed1-26f01f12f3c5.png', '11111', '11@qq.comqq.com', '1', '2024-05-06 19:19:22', '2024-05-06 19:19:22');
INSERT INTO `good` VALUES (33, 4, 2, '书3', '', '', '', '9-10成新', '2024-05-13 00:00:00', '不错的书', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/eca5fbb8-bcf4-4ba5-868c-9d3171fc7cbf.png', '2222', '', '222', '2024-05-06 19:31:41', '2024-05-06 19:31:41');
INSERT INTO `good` VALUES (34, 4, 1, '红帽', '', '', '', '', NULL, '11', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/c1c3022d-6038-45ad-807a-8391f4101634.webp', '', '', '', '2024-05-06 19:32:51', '2024-05-06 19:32:51');
INSERT INTO `good` VALUES (35, 4, 3, '古瓷盘', '', '', '', '9-10成新', NULL, '', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/99887bc4-7904-484f-acd4-ea35687328d9.png', '', '', '', '2024-05-06 19:33:12', '2024-05-06 19:33:12');
INSERT INTO `good` VALUES (36, 4, 2, '小说', '', '', '', '7-8成新', '2024-05-08 00:00:00', '', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/1e3b7f7f-b8fd-4156-a98b-58a14d9d76c7.webp', '', '', '', '2024-05-06 19:33:26', '2024-05-06 19:33:26');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `send_id` int(0) NOT NULL,
  `receive_id` int(0) NOT NULL,
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('0194f28a-7a24-4019-b6af-1ab9917efd56', 3, 4, '你好\n', '2024-04-24 21:50:16');
INSERT INTO `message` VALUES ('085397c6-e966-4233-bcac-205d65a90b96', 3, 2, '3', '2024-04-24 16:21:05');
INSERT INTO `message` VALUES ('0cc1222e-5a6c-4060-9e32-a51599a84f4b', 5, 12, 'nihao ', '2024-05-06 19:34:22');
INSERT INTO `message` VALUES ('1ed85fd3-d146-4a7a-8890-8e073bf846f6', 1, 1, '', '2024-05-07 16:07:33');
INSERT INTO `message` VALUES ('2729eb78-7964-4a09-b769-9f1f3ce6dae0', 2, 3, '6', '2024-04-24 17:32:53');
INSERT INTO `message` VALUES ('2fa7243d-e98a-4022-a55f-1369fd5b6699', 3, 3, 'nihao', '2024-05-07 14:52:16');
INSERT INTO `message` VALUES ('3b9fbbbc-119e-419e-af12-37c546045b66', 2, 3, '5', '2024-04-24 17:32:52');
INSERT INTO `message` VALUES ('4feeb3ae-88d4-4676-832f-8b798db1d8bf', 3, 4, '', '2024-04-24 22:15:28');
INSERT INTO `message` VALUES ('54034e47-9dab-44fe-8d6a-aadcfb13c827', 3, 2, '2', '2024-04-24 16:21:03');
INSERT INTO `message` VALUES ('5955679c-f44d-49b9-8f7c-6728e4f4e83e', 1, 2, '这个地毯不错', '2024-05-06 19:47:57');
INSERT INTO `message` VALUES ('598d094a-4763-4be2-9358-3d56cf6db235', 3, 1, '你好', '2024-05-07 15:06:13');
INSERT INTO `message` VALUES ('5ecc8122-7b97-4fb8-bc00-ed8288bf7fa7', 2, 3, '4', '2024-04-24 17:32:50');
INSERT INTO `message` VALUES ('7ee3d541-598c-481d-8224-506963fab9b3', 1, 1, 'nihao\'', '2024-05-07 16:07:33');
INSERT INTO `message` VALUES ('8c44b610-b616-406e-a1fb-a95c2ddb446b', 3, 2, '1', '2024-04-24 16:21:01');
INSERT INTO `message` VALUES ('905908e6-568a-42d0-8e47-da3c4a6cc918', 1, 4, '你好', '2024-04-24 21:18:32');
INSERT INTO `message` VALUES ('954ec580-3065-4a18-8768-d847f2a7d9cf', 3, 2, '你好', '2024-04-26 20:38:20');
INSERT INTO `message` VALUES ('955f7fed-f96c-4023-9c70-814b338b5c3d', 2, 3, '你不好', '2024-04-24 17:32:47');
INSERT INTO `message` VALUES ('a251759a-8a16-44ca-8047-636bbce2d481', 1, 4, '你好', '2024-04-24 21:19:29');
INSERT INTO `message` VALUES ('ca871c5b-19a8-4e81-a540-52ee1bcac875', 3, 2, '你好\n', '2024-04-24 16:20:41');
INSERT INTO `message` VALUES ('da2f9f5e-2603-48bb-8e39-540c284c0921', 3, 4, '1\n', '2024-04-24 22:31:17');
INSERT INTO `message` VALUES ('f381d4e3-55bc-47c0-94fb-6719c5ccb6f8', 3, 4, '', '2024-04-24 22:14:44');
INSERT INTO `message` VALUES ('fd8321fd-1b76-4b2a-a4b6-8d34feb9480d', 3, 2, '333在吗', '2024-04-26 20:15:47');
INSERT INTO `message` VALUES ('sdafasdfdsafsafsdafwefdxz', 3, 3, 'asd', '2024-04-24 20:31:46');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` int(0) NOT NULL DEFAULT 0,
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `nickname` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_pic` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `points` int(0) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '111111', '96e79218965eb72c92a549dd5a330112', 0, '李宏威', '258333', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/c65a5fb0-8373-4bc9-8305-e27e6a77f163.jpg', 'Lhw258333@gmail.com', '18832122809', 65, '2024-04-17 19:48:21', '2024-04-21 21:14:06');
INSERT INTO `user` VALUES (2, '333333', '1a100d2c0dab19c4430e7d73762b3423', 0, '333333', '333333', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/9d188df5-e8ea-4884-b4e8-5abb71f364b5.png', '3333@q.b', '333333', 65, '2024-04-20 11:08:57', '2024-04-21 21:20:43');
INSERT INTO `user` VALUES (3, '222222', 'e3ceb5881a0a1fdaad01296d7554868d', 1, '李', '258', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/5dd27d80-c851-46c2-a930-375b08a39356.png', '3052681940@qq.com', '18535415207', 50, '2024-04-20 11:28:15', '2024-04-21 21:27:23');
INSERT INTO `user` VALUES (4, '444444', '73882ab1fa529d7273da0db6b49cc4f3', 0, '444', '444444', 'https://big-event-258333.oss-cn-beijing.aliyuncs.com/faf6480e-a1a1-4f31-a0d2-1ea7a9d73534.png', '4444@qq.com', '44444444', 45, '2024-04-20 11:28:33', '2024-04-24 20:51:45');
INSERT INTO `user` VALUES (5, '555555', '5b1b68a9abf4d2cd155c81a9225fd158', 0, NULL, NULL, NULL, NULL, NULL, 10, '2024-04-20 11:36:43', '2024-04-20 11:36:43');

SET FOREIGN_KEY_CHECKS = 1;
