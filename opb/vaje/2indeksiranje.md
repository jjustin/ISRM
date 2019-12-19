# Binarno drevo

## O notacija

| n         | 10  | 1000 | 1000000 |
| --------- | --- | ---- | ------- |
| o(n)      | 10  | 1000 | 1000000 |
| o(log(n)) | ~3  | ~10  | ~20     |

## visina drevesa

iscemo, ce je 8 v bazi

```

             9              1 = 2^0
        //       \
       4         23         2 = 2^1
      / \\      /   \
    3   8     12    33      4 = 2^2
```

n = b ^ h <- stevilo elementov na h-tem nivoju  
h = logb(n) <- stevilo primerjav potrebnih za dosego h-tega nivoja

# Indeksi

## B+ indeks

- B+ indeks je dinamicno uravnotežno drevo
- ucinkovit pri pogostem posodabljanju zapisov
- celotno drevo se prilagaja pri posodabljanju zapisov
- casovna zahtevnost iskanja zapisov je ≈ O(log n) << O(n)

* koren ima lahko najmanj dva sinova

## ISAM indeks

- ISAM indeks je staticno (ne)uravnoteženo drevo
- ucinkovit pri redkem/občasnem posodabljanju zapisov
- le prelivne strani se prilagajajo pri posodabljanu zapisov
- casovna zahtevnost iskanja zapisov je ≈ O(log n) << O(n

## Bitni indeks

- bitni indeks je urejena binarna matrika
- ucinkovit pri sestavljenih pogojih preko malo vrednosti
- celotna matrika se prilagaja pri posodabljanju zapisov
- casovna zahtevnost iskanja zapisov je bitni O(n)

# Naloge

## 1

### a)

### b)

### c)

Drevo razdelimo na dve podrevesi, ki grest iz korena

Minumum: 2\* ⌈a/2⌉ (d+1)^(h-1)

h-1, ker nimamo korena v drevesu. d+1 dobimo iz b=m+1 in d<=m <=2d  
⌈a/2⌉ je stevilo listov

Maximum:
a \* (2d+1)^h

(2d+1)^h je stevilo listov  
a je stevilo elementov v listu

### d)

Min: 1

Max: Omejitev pomnilinka

ISAM se ne prilagaja

## 2

### a)

Dolocimo primary key, po katerem se ustvari index in se doloci, da se ne morejo ponavlat

```sql
ALTER TABLE wiki
ADD PRIMARY KEY (id);
```

### b)

#### Brez indeksa:

- Prva:

  - 56813 total, Query took 0.1283 seconds
  - O(n)

- Druga:

  - 249033 total, Query took 0.1300 seconds
  - O(n\*log(n))

- Tretja:

  - 1 total, Query took 0.1314 seconds
  - O(n)

#### Z indeksi:

Dodamo indeks

```sql
CREATE INDEX ind_date on wiki(date);
```

- Prva:

  - 56813 total, Query took 0.0010 seconds
  - O(n)

- Druga:

  - 249033 total, Query took 0.0010 seconds
  - O(n\*log(n))

- Tretja:

  - 1 total, Query took 0.0008 seconds
  - O(n)

#### Komentar:

Z indeksi se pridobi cas, vendar se tudi zelo poveca uporaba pomnilnika  
 Indeksi porabijo priblizno toliko kot podatki sami => B+ indeksiranje ima tolkio indeksov v binarnem drevesu z n listi n-1 indeksov

## 4

### a, b)

#### Klicanje funkcij

```sql
SELECT SQL_NO_CACHE * FROM wiki WHERE DATE(date) = '2009-12-12'
```

14 total, Query took 0.1762 seconds

Baza klice funkcij `DATE` po kateri pa ni indeksirano. Zato tu indeks ne pomaga

```sql
EXPLAIN SELECT SQL_NO_CACHE * FROM wiki WHERE DATE(date) = '2009-12-12'
```

Vidimo, da se indeksi ne uporabljajo.  
Naslednji query pa nam pove, da se bodo indeksi uporabili

```sql
EXPLAIN SELECT SQL_NO_CACHE * FROM wiki WHERE date = '2009-12-12'
```

### c)

#### Ustvari indeksiranje po dveh ali vec atributih:

```sql
CREATE INDEX ind_name ON wiki(name, classification);
```

### d)

#### Unique index

```sql
CREATE UNIQUE INDEX unq_ind ON wiki(name);
```

Sprva vrne error, ker so vnosi v trenutni bazi podvojeni - treba jih je zbrisat/uredit, in potem ustvariti index

## 5.

### a)

Ustvarimo tabelo:

```sql
CREATE TABLE subwiki AS SELECT * FROM wiki WHERE date < '1975-12-31' ORDER BY date
```

### b)

Name:
| Embassy Teheran | Embassy Liberville |
| --------------- | ------------------ |
| 1 | 0 |
| 1 | 0 |
| 1 | 0 |
| 1 | 0 |
| 1 | 0 |
| 1 | 0 |
| 0 | 1 |

Classification:
| UNCLASSIFIED| SECRET| CONFIDENTIAL |
|-|-|-|
|1|0|0|
|1|0|0|
|1|0|0|
|1|0|0|
|0|1|0|
|1|0|0|
|0|1|0|
|0|0|1|
|0|0|1|
