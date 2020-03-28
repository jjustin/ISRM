Pri dokazovanju pravilnosti si lahko pomagamo z naslednjimi splošnimi nasveti.

1. Po stavku `x := N` vedno velja `{ x = N }`. Formalno to dokažemo tako:  
`{ N = N } x := N { x = N }`.
2. Pravilo za prireditveni stavek je `{ P[x ↦ e] } x := e { P }`. Pri izpeljevanju najprej vse `x`-e v predpogoju izrazimo z `e`, nato pa `e`-je zamenjamo z `x`.
3. V zančni invarianti se zančni pogoj in lokalne spremenljivke ne pojavljajo, saj mora invarianta veljati tudi po izstopu iz zanke. Ko invarianto »peljemo« čez telo zanke, se nam v njej lahko pojavijo lokalne spremenljivke. Teh se želimo znebiti, ko dosežemo konec zanke.
4. Na koncu zanke se lahko izkaže, da je invarianta prešibka in iz nje ne moremo izpeljati končnega pogoja. V tem primeru lahko v invarianto dodamo še kak pogoj in ga ločeno peljemo čez zanko. To ne bo nikoli pokvarilo obstoječe izpeljave: če velja `A ⇒ C`, potem velja tudi `A ∧ B ⇒ C`.

### Naloga 1 
#### Navodilo
Dokažite parcialno in popolno pravilnost programa glede na dano specifikacijo.
```
	{ x = m ∧ y = n }
	x := x + y;
	y := x - y;
	x := x - y;
	{ x = n ∧ y = m }
```

#### Pravilo za `c₁ ; c₂`

    { P } c₁ { Q }      { Q } c₂ { R }
    —————————————————————————————————–
           { P } c₁ ; c₂ { R } 
#### Pravilo za prirejanje 

    ————————————————————————–
    { P[x ↦ e] } x := e { P }

#### Rešitev
Reševanje je mogoče na dva načina za parcialno pravilnost:
 - Od zgoraj navzdol
    - Predpogoj je potrebno pravilno prirediti
```
    { x = m ∧ y = n } =>
    { x + y - y = m, y = n}
    // vredu tudi { x + y = m + n, y = n}, ampak potem se naprej naredi malo drugace
    x := x + y;
    { x - y = m, y = n }=>
    {x - y = m, x - y = x - n}
    y := x - y;
    { y = m, y = x - n }
    x := x - y;
    { x = n ∧ y = m }
```

 - Od spodaj navzgor
```
    { x = m ∧ y = n }
    x := x + y;
    y := x - y;
    { x-y = n, y = n } = { P[ x -> x-y ] }
	x := x - y;
    { x = n ∧ y = m } = { P }
```
Popolna pravilnost:  
    Zavite oklepaje lahko zamenjamo z oglatimi, ker ni while/for zank.

### Naloga 2
#### Navodilo
Dokažite parcialno in popolno pravilnost programa glede na dano specifikacijo.
```
	{ } = { P }
	if y < x then
        z := x;
        x := y;
        y := z
    else
        skip
	end
	{ x ≤ y } = { Q }
```
#### Pravilo za pogojni stavek

    { P ∧ b } c₁ { Q }       { P ∧ ¬b } c₂ { Q }
    ———————————————————————————————————————————–
        { P } if b then c₁ else c₂ end { Q }

#### Rešitev¸
Parcianlna pravilnost:
```
{ } = { P }
if y < x then
    { true, y < x } =>
    { y <= x }
    z := x;
    { y <= z }
    x := y;
    { x <= z }
    y := z
    {x <= y}
else
    { true, y >= x }
    skip
    { true, y >= x } => { y >= x } =>
    {x <= y}
end
{ x ≤ y } = { Q }
```
Popolna pravilnost:  
    Zavite oklepaje lahko zamenjamo z oglatimi, ker ni while/for zank.


### Naloga 3
NERESENO!!!! 
Te naloga je bila resena brez prisotnosti asistenta - moznost napak.
#### Navodilo
Sestavite program `c`, ki zadošča specifikaciji
```
	[ n ≥ 0 ]
	c
	[ s = 1 + 2 + ... + n ]
```
in dokažite njegovo pravilnost.

#### Resitev
c:
```
s := 0;
i := n;
while i > 0 do
    {s = i+1 + ... + n}
    s := s + i;
    {s = }
    i := i - 1
end
{s = 1 + 2 + 3 + ... + n}
```

### Naloga 4
#### Navodilo
Dokažite parcialno in popolno pravilnost programa glede na dano specifikacijo.
```
{ x ≥ 0 }
y := 0;
z := x;
while 1 < z - y do 
    s := (y + z)/2;
    if s * s < x then
        y := s
    else
        z := s
    end
done
{ y² ≤ x ≤ (y+1)² }
```

#### Pravilo za zanko `while`

            { P ∧ b } c { P }
    —————————————————-————————————————–
    { P } while b do c done { ¬ b ∧ P }

#### Rešitev
```
{ x ≥ 0 }
y := 0;
{ x ≥ 0, y = 0}
z := x;
{ x ≥ 0, y = 0, z = x } =>
{ x <= z^2, x >= y^2 } = { P }

while 1 < z - y do 
    { 1 < z - y, x <= z^2, x >= y^2, e = z - y, e > 0 } // e je zancna invarianta
    s := (y + z)/2;
    { 1 < z - y, x <= z^2, x >= y^2, s = (y + z)/2, e = z - y, e > 0}
    if s * s < x then
        { 1 < z - y, x <= z^2, x >= y^2, s = (y + z)/2, s^2 < x, e = z - y, e > 0 } =>
        // s = (y + z)/2 <=> y = 2s - z
        { 1 < z - (2s - z), x <= z^2, x >= (2s - z)^2, y = 2s - z, s^2 < x, e = 2z - 2s, e > 0 } =>
        { 1 < z - (2s - z), x <= z^2, x >= (2s - z)^2, s^2 < x, e = 2z - 2s, e > 0 }
        y := s
        { 1 < z - (2y - z), x <= z^2, x >= (2y - z)^2, y^2 < x, e = 2(z - y), e > 0 } =>

        { x <= z^2, x >= y^2, e = 2(z - y) }
    else
        { 1 < z - y, x <= z^2, x >= y^2, s = (y + z)/2, s^2 >= x, e = z - y, e > 0 } =>
        // s = (y + z)/2 <=> z = 2s - y
        { 1 < z - y, x <= z^2, x >= y^2, z = 2s - y, s^2 >= x, e = 2s - 2y, e > 0 } =>
        { 1 < (2s - y) - y, x <= (2s - y)^2, x >= y^2, s^2 >= x, e = 2s - 2y, e > 0 }
        z := s
        { 1 < (2z - y) - y, x <= (2z - y)^2, x >= y^2, z^2 >= x, e = 2(z - y), e > 0 } =>
         
        { x <= z^2, x >= y^2, e = 2(z - y) }
    end
    // 
    { x <= z^2, x >= y^2, e = 2(z - y) }
done

{ x <= z^2, x >= y^2, 1 >= z - y} <=>
{ x <= z^2, x >= y^2, 1+y >= z} => // zamenjamo `z` z zgornjo mejo z-ja(1+y)
{ x <= (1+y)^2, x >= y^2, 1+y >= z} =>
{ y² ≤ x ≤ (y+1)² }
``` 
To kar izracunamo je:  y = floor(sqrt(x))

Totalna pravilnost:
#### Pravilo za totalno pravilnost zanke
    [ P ∧ b ∧ e = z ] c [P ∧ e < z ]
    —————————————————————————————————————
    [ P ] while b do c done [ ¬ b ∧ P ]

Smo vstavili e. Sedaj lahko `{}` zamenjamo z `[]`.