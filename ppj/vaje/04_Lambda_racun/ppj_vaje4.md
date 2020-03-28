# λ-račun

glej tudi dokument na ucilnici, ker ta ni popoln

## Intro

### if stavek

```ocaml
true  := λ x y . x ;
false := λ x y . y ;
if := λ p x y . p x y ;
```

### and

and lahko definiramo z

```ocaml
and true  y ≡ y
and false y ≡ false
```

kar se implementiramo z `if`:

```ocaml
-- ce x drzi, potem poglej y, sicer je false
and' := λ x y . if x y false
```

če upoštevamo definicjo if:

```ocaml
and := λ x y . x y false
```

## Naloga

Definirajte še disjunkcijo `or`, implikacijo `imply`, ekvivalenco `iff` in negacijo `not`. Rešitve poskusite zapisati brez uporabe `if`.

### or

```
or := λ x y . if x true y
```

### implikacija

```ocaml
implies := λ x y . if x y true
```

### not

```ocaml
not := λ x . if x false true
```

### iff

```ocaml
iff := λ x y . and (x implies y) (y implies x)
--ali
iff := λ x y . or (and(x)(y)) (and(not x)(not y))
```

## Scott-Churchova števila

Podobna kot `Churchova števila` vendar bolj programiranju prijazna

```
0 := λ f x . x ;
1 := λ f x . f 0 x ;
2 := λ f x . f 1 (f 0 x) ;
3 := λ f x . f 2 (f 1 (f 0 x)) ;
4 := λ f x . f 3 (f 2 (f 1 (f 0 x))) ;
5 := λ f x . f 4 (f 3 (f 2 (f 1 (f 0 x)))) ;
```

vsako naslednje stevilo dobimo tako, da pokicemo f, vzamemo prejsnje stevilo in ga apliciramo z `f x`. Torej:

```
0 := λ f x . x ;
1 := λ f x . f 0 x ;
2 := λ f x . f 1 (1 f x) ;
3 := λ f x . f 2 (2 f x) ;
4 := λ f x . f 3 (3 f x) ;
5 := λ f x . f 4 (4 f x) ;
-- in drzi
n+1 := λ f x . f n (n f x)
```

## Naslednjik

Na ucilnici definirano kot: (tako kot scoot churchova stevila)

```
succ n :=  \ f x . f n (n f x) ;
```

Nas jezik ne podpira n-ja na levi strani. Zato ga premaknemo na desno stran:

```
succ :=  \ n . f x . f n (n f x) ;
```

## Seštevanje

```
+ := \ n  k . n (\ m r . succ r) k;
```

## Množenje

```
* := \ n  k . n (\ m r .  + k r) 0;
```

## Odstevanje

```
- := \ n k . k (\ m r . pred r) n ;
```
