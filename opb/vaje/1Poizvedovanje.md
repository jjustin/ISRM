# 1.

## a)

```sql
SELECT *
FROM stranka;
```

## b)

```sql
SELECT id, ime
FROM agent
WHERE mesto="Kranj";
```

## c)

```sql
SELECT DISTINCT izdelek_id
FROM narocilo;
```

## d)

```sql
SELECT a.ime, s.ime
FROM stranka s, agent a, narocilo n
WHERE s.id=n.stranka_id AND a.id=n.agent_id
```

# 2.

## a)

```sql
SELECT n.kolicina * i.cena * (0.4 - (s.popust + a.marza)*0.01) as dobicek
FROM narocilo n, izdelek i, stranka s, agent a
WHERE s.id=n.stranka_id AND a.id=n.agent_id AND i.id=n.izdelek_id;
```

## b)

```sql
SELECT s1.id, s2.id
FROM stranka s1, stranka s2
WHERE s1.mesto=s2.mesto AND s1.id < s2.id
```

## c)

```sql
SELECT n1.izdelek_id
FROM narocilo n1, narocilo n2
WHERE n1.izdelek_id=n2.izdelek_id AND n1.stranka_id != n2.stranka_id
```

## d)

```sql
SELECT n2.stranka_id
FROM narocilo n1, narocilo n2
WHERE n1.izdelek_id=n2.izdelek_id AND n1.agent_id=6
```

DN tip: SELECT Stranka_stolpec || "kupuje izdelek" || izdelek_stolpec AS "stranka Kupuje"

# 3.

## a)

```sql
SELECT s.ime
FROM narocilo n, stranka s
WHERE n.stranka_id=s.id AND n.izdelek_id=2
```

## b)

```sql
SELECT s.ime
FROM stranka s
WHERE 2 IN (
	SELECT n.izdelek_id
	FROM narocilo n
	WHERE n.stranka_id=s.id
)
```

## c)

```sql
SELECT s.ime
FROM stranka s
WHERE s.id IN (
	SELECT n.stranka_id
	FROM narocilo n
	WHERE n.agent_id IN (
		SELECT a.id
		FROM agent a
		WHERE a.mesto IN ("Kranj", "Koper")
	)
)
```

# 4.

## a)

```
	SELECT a.id
	FROM agent a
	WHERE a.marza <= ALL(
		SELECT marza
		FROM agent
	)
```

ali

```sql
SELECT a.id
FROM agent a
WHERE a.marza = (
	SELECT MIN(marza)
	FROM agent
)
```

## b)

```sql
SELECT a.id
FROM agent a
WHERE a.marza < any (SELECT marza FROM agent)
```

## c)

```sql
SELECT s.id, s.ime
FROM stranka s
WHERE 50 <= ALL (
	SELECT i.cena
	FROM narocilo n, izdelek i
	WHERE i.id = n.izdelek_id AND s.id = n.stranka_id
) AND EXISTS (
        SELECT i.cena
        FROM narocilo n, izdelek i
        WHERE i.id = n.izdelek_id AND s.id = n.stranka_id
)
```

# 5.

## a)

```sql
SELECT t.ime, t.priimek
FROM trgovec t,  dobavnica d
WHERE t.id=d.trgovec_id AND d.kolicina=(
	SELECT MAX(kolicina)
	FROM dobavnica
)
```

# 6.

## a)

```sql
SELECT d.naziv
FROM del d
WHERE d.id NOT IN (
	SELECT del_id
	FROM dobavnica
	)
```

## b)

```sql
SELECT d.naziv
FROM del d
WHERE d.id NOT IN (
	SELECT DISTINCT do.del_id
	FROM dobavnica do
)
```

## d)

```sql
SELECT DISTINCT(d.naziv)
FROM del d, projekt p, dobavnica dob
WHERE d.id=dob.del_id AND dob.projekt_id=p.id AND p.mesto="Kranj" AND d.id IN (
	SELECT dobavnica.del_id
    FROM projekt, dobavnica
	WHERE projekt.id = dobavnica.projekt_id AND projekt.mesto="Maribor"
	)
```

# 7.

## a)

```sql
SELECT COUNT(c.id)
FROM cable c, embassy e, privacy p
WHERE
	c.embassy_id=e.id AND
	c.privacy_id=p.id AND
	p.classification LIKE "CONFIDENTIAL%" AND
    e.name LIKE "%LJUBLJANA%" AND
    c.date >= '2000-01-01'
```

## b)

```sql
SELECT p.classification AS Nivo, COUNT(c.id) AS Stevilo
FROM cable c, privacy p
WHERE c.privacy_id=p.id
GROUP BY p.id
```

## c)

```sql
SELECT c.date, COUNT(c.id) AS Stevilo
FROM cable c, privacy p
WHERE
	c.privacy_id=p.id AND
	p.classification LIKE "SECRET%"
GROUP BY c.date
HAVING Stevilo > 30
```

## d)

```sql
SELECT c.date, COUNT(c.id) AS Stevilo
FROM cable c, privacy p
WHERE
	c.privacy_id=p.id AND
	p.classification LIKE "SECRET%"
GROUP BY c.date
ORDER BY Stevilo DESC
LIMIT 1
```

## e)

```sql
SELECT c.date, COUNT(c.id) AS Stevilo
FROM cable c, privacy p
WHERE c.privacy_id=p.id AND p.classification LIKE "SECRET%"
GROUP BY c.date
HAVING COUNT(c.id) = (
	SELECT MAX(Stevilo) FROM (
		SELECT c.date, COUNT(c.id) AS Stevilo
		FROM cable c, privacy p
		WHERE c.privacy_id=p.id AND p.classification LIKE "SECRET%"
		GROUP BY c.date
		) t
    )
```

# 8.

## a)

```sql
SELECT e.name, c.date, COUNT(c.id) AS Stevilo
FROM cable c, embassy e
WHERE c.embassy_id = e.id
GROUP BY c.date, e.name
ORDER BY Stevilo DESC
LIMIT 10
OFFSET 10
```

## b)

```sql
SELECT r.cable_id
FROM cable c, embassy e, reference r
WHERE
	e.id = c.embassy_id AND
    r.ref_cable_id = c.id AND
    e.name LIKE "%Ljubljana%" AND
    r.cable_id NOT IN (
		SELECT r.cable_id
		FROM cable c, embassy e, reference r
		WHERE
			e.id = c.embassy_id AND
			r.ref_cable_id = c.id AND
			e.name NOT LIKE "%Ljubljana%"
    )
```

# Dodajanje baze/tabele/podatkov

## Ustvarjanje baze

```sql
-- naredi bazo social
CREATE DATABASE social;
```

## Izberemo bazo

```sql
-- izberemo podatkovno bazo
USE social;
```

## Ustvarjanje tabele

```sql
-- naredimo tabelo oseba
CREATE TABLE oseba (
	id INT PRIMARY KEY AUTO_INCREMENT, -- stevilo, ki je primarni kljuc in se samo povecuje ob dodajanju vrstic
	ime VARCHAR(50) NOT NULL, -- string do 50 znakov
	rojstni_dan DATE -- datum
);

-- naredimo tabelo facebook
CREATE TABLE facebook(
	oseba_id INT,
	prijatelj_id INT,
	PRIMARY KEY (oseba_id, prijatelj_id), -- uporabimo vec primarnih kljucov
	FOREIGN KEY (oseba_id) REFERENCES oseba(id), -- oseba_id je tuji kljuc iz tabele oseba
	CONSTRAINT fb_fk1 FOREIGN KEY (oseba_id) REFERENCES oseba(id) --ustvarimo tuji kljuc fb_fk1 povezan z oseba(id)
	ON DELETE CASCADE, -- ki se zbrise, ko zbrisemo iz tabele oseba(id) (???? nv tocno, ce je taka funkcionalnost - preveri tocno na netu)
);
```

## Dodajanje/brisanje podatkov

```sql

INSERT INTO oseba(ime, rojstni_dan) VALUES ("Jože", "2006-04-12"), ("Jovane", NULL), ...;-- Dodamo vnose v tabele

DELETE FROM facebook WHERE (oseba_id = 3 AND prijatelj_id = 4) -- zbrisemo prijateljstvo  med 3 in 4
```

## Uporabniki

```sql
CREATE USER "Misko"@"10.3.5.56" IDENTIFIED BY "PASSWORD SUPER SECURE XD 3000"; -- ustvarimo uporabnika misko dostopen iz 10.3.5.56

GRANT ALL PRIVILEGES ON social.* TO "Misko"@"localhost"; -- da dostop misku do social tabele
FLUSH PRIVILEGES; -- posodbi dovoljenja
```

# Funkcije

```sql
CALL ime_funkcije();
```

# NOTES

```sql
EXPLAIN PLAN(...)
```

ali pa mogoce

```sql
EXECUTION PLAN(...)
```

izpise potek izvedbe queryja
