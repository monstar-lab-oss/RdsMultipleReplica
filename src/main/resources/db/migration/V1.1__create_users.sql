CREATE TABLE "users" (
    id bigserial PRIMARY KEY,
    uuid uuid DEFAULT gen_random_uuid(),
    firstname VARCHAR(100),
    lastname VARCHAR(100));