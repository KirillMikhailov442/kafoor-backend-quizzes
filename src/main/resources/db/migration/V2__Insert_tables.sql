INSERT INTO `quizzes` (`id`, `created_at`, `ended_at`, `max_members`, `name`, `started_at`, `updated_at`, `user_id`) 
VALUES 
(2, '2026-01-30 04:31:36.000000', '2026-01-31 22:57:28.000000', 5, 'Новая викторина', '2026-01-31 22:56:52.000000', '2026-01-31 22:57:28.000000', 2);

INSERT INTO `options` (`id`, `img`, `slug`, `text`) 
VALUES 
(1, NULL, 'option@e4472a67-7f5c-4d1b-8321-dbd1a3db9f2b', 'Ф'),
(3, NULL, 'option@f2ef8b24-5536-4b88-a2b8-37b57b957a78', 'Б'),
(4, NULL, 'option@9438b3df-09b6-44d4-bc70-92c7f012ed9e', 'А'),
(5, NULL, 'option@bcf381e7-74ac-4a9a-b5c6-536e02472b32', 'С'),
(6, NULL, 'option@8dcb1566-16ec-41b5-adc5-1f8ea028293a', 'И'),
(7, NULL, 'option@db9a6bce-1b78-4e1d-9334-86c5fd438808', 'А');

INSERT INTO `questions` (`id`, `img`, `scores`, `slug`, `text`, `time_limit`, `video`, `quiz_id`) 
VALUES 
(1, NULL, 10, 'question@1b9a17e0-aa42-49ab-b898-b82510be8e14', 'Привет', 20, NULL, 2),
(5, NULL, 10, 'question@ecbbde2b-f77e-4d88-80e7-8488d4692817', 'Что-то', 10, NULL, 2);

INSERT INTO `questions_option` (`id`, `created_at`, `is_correct`, `updated_at`, `option_id`, `question_id`) 
VALUES 
(3, '2026-01-30 07:48:45.000000', b'0', '2026-01-30 07:48:45.000000', 3, 1),
(4, '2026-01-30 07:48:45.000000', b'1', '2026-01-30 07:49:10.000000', 4, 1),
(6, '2026-01-30 07:50:08.000000', b'1', '2026-01-30 07:50:12.000000', 7, 5),
(7, '2026-01-30 07:50:08.000000', b'0', '2026-01-30 07:50:08.000000', 6, 5);

INSERT INTO `members` (`id`, `created_at`, `user_id`, `quiz_id`) 
VALUES 
(2, '2026-01-31 22:56:52.000000', 4, 2);

INSERT INTO `member_answers` (`id`, `answer_id`, `member_id`) 
VALUES 
(3, 4, 2),
(4, 7, 2);