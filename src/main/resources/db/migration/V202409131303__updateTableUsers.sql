UPDATE users
SET height = um.height,
    age = um.age
FROM user_measurements um
WHERE user_id = um.user_id;