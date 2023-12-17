create table eng_word(id int primary key generated always as identity, word int not null unique); --int(100000)
create table eng_translate(row_id int primary key generated always as identity,
                           eng_word_id int references eng_word(id) on delete cascade, translate_word varchar(50));


create table spanish_word(id int primary key generated always as identity, word varchar(4) not null unique);
create table spanish_translate(row_id int primary key generated always as identity,
                               spanish_word_id int references spanish_word(id) on delete cascade, translate_word varchar(50));
