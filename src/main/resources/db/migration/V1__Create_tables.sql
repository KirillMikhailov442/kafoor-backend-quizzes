CREATE TABLE IF NOT EXISTS `quizzes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `ended_at` datetime(6) DEFAULT NULL,
  `max_members` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `started_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_quiz_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `options` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `img` varchar(255) DEFAULT NULL,
  `slug` varchar(255) NOT NULL,
  `text` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK8q9ahneqiscvwif1y5gcvqahk` (`slug`),
  KEY `idx_option_slug` (`slug`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `questions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `img` varchar(255) DEFAULT NULL,
  `scores` int(11) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `text` varchar(255) NOT NULL,
  `time_limit` tinyint(4) NOT NULL,
  `video` varchar(255) DEFAULT NULL,
  `quiz_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKd3mfu7jqrorkl34rax8csf7np` (`slug`),
  KEY `idx_question_slug` (`slug`),
  CONSTRAINT `FKn3gvco4b0kewxc0bywf1igfms` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `questions_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `is_correct` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `option_id` bigint(20) NOT NULL,
  `question_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKab4sogtbh1rlyh8vsnvcam5nj` FOREIGN KEY (`option_id`) REFERENCES `options` (`id`),
  CONSTRAINT `FK89jcdehgkp84pugy6q8j47jub` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `members` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `quiz_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_member_user_quiz` (`user_id`,`quiz_id`),
  CONSTRAINT `FK7i87vyy8k2rlp3qdu5hah3pda` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `member_answers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer_id` bigint(20) NOT NULL,
  `member_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_member_answer` (`member_id`,`answer_id`),
  CONSTRAINT `FK4mxddvsd381d5fs9eusqkiv6d` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
  CONSTRAINT `FKqqgt6bwjg40c68wmycaf9htmh` FOREIGN KEY (`answer_id`) REFERENCES `questions_option` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;