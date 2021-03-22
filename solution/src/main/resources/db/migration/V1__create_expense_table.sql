CREATE TABLE IF NOT EXISTS expense
(
    id              SERIAL PRIMARY KEY,
    date            DATE           NOT NULL,
    amount_value    DECIMAL(12, 2) NOT NULL,
    amount_currency CHAR(3)        NOT NULL,
    vat_value       DECIMAL(12, 2) NOT NULL,
    vat_currency    CHAR(3)        NOT NULL,
    reason          VARCHAR(1000)  NOT NULL
);
