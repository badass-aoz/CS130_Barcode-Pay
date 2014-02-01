



CREATE TABLE Account ( UID               VARCHAR(40),
                       Name              VARCHAR(80),
                       Password          VARCHAR(80),
                       CreditCardNumber  VARCHAR(20),
                       Expiration        DATE,
                       CSV               VARCHAR(5),
                       CardType          VARCHAR(20),
                       PRIMARY KEY(UID, Name, cardType));








