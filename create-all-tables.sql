CREATE TABLE "user"
(
    id UUID NOT NULL CONSTRAINT trade_analysis_database_id_key UNIQUE,
    username VARCHAR(30) NOT NULL CONSTRAINT trade_analysis_database_username_key UNIQUE,
    email VARCHAR(90) NOT NULL CONSTRAINT trade_analysis_database_email_key UNIQUE,
    password TEXT NOT NULL,
    user_role VARCHAR(15) NOT NULL,
    api_key VARCHAR(16)
);

CREATE TABLE email_verification_token
(
    token_id UUID NOT NULL CONSTRAINT email_verification_token_pkey PRIMARY KEY UNIQUE,
    user_id UUID NOT NULL CONSTRAINT email_verification_token_user_id_key UNIQUE
        CONSTRAINT email_verification_token_user_id_fkey REFERENCES "user" (id) ON DELETE CASCADE,
    expiration_date date NOT NULL
);

CREATE TABLE stock_prices
(
    stock_prices_id UUID NOT NULL CONSTRAINT stock_prices_pkey PRIMARY KEY UNIQUE,
    final TEXT NOT NULL
);

CREATE TABLE note
(
    note_id UUID NOT NULL CONSTRAINT note_pkey PRIMARY KEY UNIQUE,
    markdown_text VARCHAR(10000) NOT NULL
);

CREATE TABLE stock_prices_history_entry
(
    stock_prices_history_entry_id UUID NOT NULL CONSTRAINT history_entry_pkey PRIMARY KEY UNIQUE,
    user_id UUID NOT NULL
        CONSTRAINT history_entry_user_id_fkey REFERENCES "user" (id),
    stock_prices_id UUID NOT NULL UNIQUE
        CONSTRAINT history_entry_stock_prices_id_fkey REFERENCES stock_prices,
    note_id UUID NOT NULL UNIQUE
        CONSTRAINT history_entry_note_id_fkey REFERENCES note,
    date TIMESTAMP NOT NULL
);