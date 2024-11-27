--숙소 생성 쿼리
INSERT INTO `accommodation` (price, DESCRIPTION, location, NAME)
VALUES (100000, '엄청 좋은 숙소입니다.', '경주시 ㅇㅇ동', 'A한옥');

INSERT INTO `accommodation` (price, DESCRIPTION, location, NAME)
VALUES (100000, '전통적인 감성을 느낄수 있는 숙소입니다.', '경주시 ㅇㅇ동', 'B한옥');

-- room 생성 쿼리(room_availability 사용 O)
INSERT INTO room (accommodation_id, room_type)
VALUES (1, '싱글룸');

INSERT INTO room (accommodation_id, room_type)
VALUES (1, '더블룸');

-- room_availability 생성 쿼리
INSERT INTO `room_availability` (available_count, total_count, DATE, room_id)
VALUES (5, 5, '2024-11-24', 1);

INSERT INTO `room_availability` (available_count, total_count, DATE, room_id)
VALUES (5, 5, '2024-11-24', 2);

-- 방 예약 쿼리
INSERT INTO reservations (phone_number, total_price, accommodation_id, check_in_date, check_out_date, room_id)
VALUES ("010-1234-1234", 10000, 1, '2024-11-24', '2024-11-24', 1);
