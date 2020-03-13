# 1 Mrtva koda

## 1

```diff
print("Kdor to bere je ");
a = 3;
+ // a^2 = 9 < 10
if (a * a < 10) {
    print("mula");
- } else {
-    print("osel");
}
```

## 2

```diff
k = n + (n - 1);
+// k nikoli deljivo z 2
- if (k % 2 == 0) {
    print("foo");
- } else {
-     print("bar");
- }
m = k * (-k);
+// m vedno negativno
- while (m > 0) {
-     m = m - 1;
-     print(m);
- }

```

## 3

```diff
i = 0;
while (i < 100) {
    if (i % 2 == 0) {
        p = i * (i - 1) * (i - 2);
+       // p mod3 == 0
    } else {
        p = i * (i + 1) * (i + 2);
+       // p mod3 == 0
    }

-   if (p % 3 == 0) {
        break;
-   } else { }
+   // vedno se aktivira break
-   i = i + 1;
}

+ // while se konca v prvi rudni => i = 0
- if (i >= 100) {
-    print("ni zanimivih števil");
- } else {
    print("našel sem zanimivo število " + i);
- }
+ // lahko se odstrani se celoten while (se šteje kot optimizacija, ne pa odstranjevanje mrtve kode)
```

## 4

```diff
+// neskoncen loop, ni mrtva koda
s = 0;
i = 0;
while (i < 100) {
    s = s + i;
}
print(s);
```

# 2 Odstranjevanje `break`ov

## 1

```diff
+ cont = true;
- while (n > 0) {
+ while (n > 0 && cont) {
    digit = n % 10;
    if (digit == 7) {
        print(n + " vsebuje števko 7");
-        break;
+        cont = false;
    } else { }
+   if(cont){
        n = n / 10;
+   }
}
```

## 2

```diff
// najdi najmanjše popolno število
n = 1;
+u = true
+while (true && u) {
-while (true) {
    d = 1;
    vsota = 0; // vsota deliteljev števila n
+   v = true;
+   while (d < n && v ) {
-   while (d < n) {
        if (n % d == 0) {
            vsota = vsota + d;
        } else { }
        if (vsota > n) {
-           break;
+           v = false;
        } else { }
+        if(v){
            d = d + 1;
+        }
    }
    if (vsota == n) {
-       break;
+       u = false;
    } else { }
+   if(u){
        print(n + " ni popolno število");
        n = n + 1;
+   }
}
print("našel popolno število: " + n);
```

## 3. Splošni postopek odstranjevanja

```
// ker se lahko pojavi problem pri (A;B), ker stop ne bi bil definiran, ga definiramo pred izvedbo
stop = false;

UNBREAK{
    (print(e)) := print(e)

    (x=e) := x=e

    (A; B) := {
        UNBREAK(A);
        if(!stop){
            UNBREAK(B);
        } else {}
    }

    (if(P) {A} else {B}) :=if {UNBREAK(A)} else {UNBREAK(B)}

    (while(P){A}) :=
        stop = false; // predpostavimo, da Stop ni porabljen
        while(P && !stop){
            UNBREAK(A);
            }
        stop = false;
    }

    (break) := stop = true
}

```

### Random clutter, ki baje pomaga pri tem 🤷🏿‍♂️

- `f(x) := x + 1` ekvivalentno `f(x-1) = x`
- (a <=> false) <=> !a
