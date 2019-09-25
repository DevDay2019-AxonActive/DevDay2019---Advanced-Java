-- https://dbdiagram.io/d/5d887daeff5115114db4858a

CREATE TABLE "users" (
  "id" SERIAL PRIMARY KEY,
  "full_name" varchar,
  "email" varchar,
  "created_at" timestamp
);

CREATE TABLE "devices" (
  "id" SERIAL PRIMARY KEY,
  "status" varchar,
  "token" varchar,
  "user_id" int
);

CREATE TABLE "books" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar,
  "author" varchar,
  "serial_number" varchar
);

CREATE TABLE "user_books" (
  "id" SERIAL PRIMARY KEY,
  "user_id" int,
  "book_id" int
);

CREATE TABLE "book_details" (
  "id" SERIAL PRIMARY KEY,
  "book_id" int,
  "source" varchar,
  "description" varchar
);

CREATE TABLE "book_comments" (
  "id" SERIAL PRIMARY KEY,
  "book_detail_id" int,
  "comment" varchar,
  "user_id" int,
  "parent_id" int
);
CREATE TABLE "book_tags" (
  "id" SERIAL PRIMARY KEY,
  "book_detail_id" int,
  "tag" varchar
);

CREATE TABLE "book_ratings" (
  "id" SERIAL PRIMARY KEY,
  "book_detail_id" int,
  "rating" int,
  "user_id" int
);

ALTER TABLE "devices" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_books" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_books" ADD FOREIGN KEY ("book_id") REFERENCES "books" ("id");

ALTER TABLE "book_details" ADD FOREIGN KEY ("book_id") REFERENCES "books" ("id");

ALTER TABLE "book_comments" ADD FOREIGN KEY ("book_detail_id") REFERENCES "book_details" ("id");

ALTER TABLE "book_comments" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "book_comments" ADD FOREIGN KEY ("parent_id") REFERENCES "book_comments" ("id");

ALTER TABLE "book_tags" ADD FOREIGN KEY ("book_detail_id") REFERENCES "book_details" ("id");

ALTER TABLE "book_ratings" ADD FOREIGN KEY ("book_detail_id") REFERENCES "book_details" ("id");

ALTER TABLE "book_ratings" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");