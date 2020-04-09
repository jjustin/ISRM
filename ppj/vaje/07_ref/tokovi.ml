type 'a stream = Cons of 'a * (unit -> 'a stream) ;;

(* Tok iz seznama
   Sestavimo funkcijo from_list tipa α list → α → α stream.
   Izraz from_list [e₁, e₂, ..., eᵢ] r
   naj vrne neskončni tok, ki ima prvih i elementov iz danega seznama, nato pa ponavlja element r:
   e₁, e₂, ..., eᵢ, r, r, r, r, r, r, ...

   Primer:
   # let four = from_list [1;2;3] 4 ;;
   val four : int stream = Cons (1, <fun>) *)

(* let primes = Cons (2, fun () -> Cons (3, fun () -> Cons (5, fun () -> ...))) *)
(* let from_list_a = Cons (e₁, fun () -> Cons (e₂, ... fun () -> Cons (r, fun () -> ...))) *)


let rec from_list l r =
  match l with
  (* | []    -> let rec repeat () = Cons(r, fun () -> repeat ()) in repeat () *)
  | [] -> Cons(r, fun () -> from_list [] r)
  | x::xs -> Cons(x, fun () -> from_list xs r)
;;

let four = from_list [1; 2; 3] 4 ;;


(* Seznam iz toka
   Sestavimo še obratno funkcijo to_list n s, ki vrne seznam prvih n elementov danega toka s. Primer: *)
let rec to_list' n s = match s with
  | Cons (value, stream) when n>0 -> [value] @ to_list' (n-1) (stream ())
  | _ -> [];;

let rec to_list n (Cons (v, s)) = if n > 0 then v :: to_list (n - 1) (s ()) else [] ;;

to_list' 10 four ;;


(* n-ti element toka
   Sestavite funkcijo element_at n s, ki vrne n-ti element danega toka s. Primer: *)
let rec element_at n (Cons (v, s)) = if n = 0 then v else element_at (n - 1) (s ());;
element_at 100 four ;;
element_at 2 four ;;


(* Glava in rep
   Sestavite funkciji head : α stream → α in tail : α stream → α stream, ki vrneta glavo in rep toka. Primer: *)

let head (Cons (a,_))= a;; 
let tail (Cons (_,s))= s();;

head four ;; 
(* - : int = 1 *)
tail four ;;
(* - : int stream = Cons (2, <fun>) *)
head (tail four) ;;
(* - : int = 2 *)


(* Preslikava
   Sestavite funkcijo map, ki sprejme funkcijo f in tok *)

let rec map f (Cons (v, s)) = Cons (f v, fun () -> map f (s ())) ;;

to_list 5 (map (fun x -> x * x) four) ;;
(* - : int list = [1; 4; 9; 16; 16] *)


(* Naravna števila
   Sestavite tok nat naravnih števil 0, 1, 2, 3, ....
   Namig: pomagajte si s pomožno rekurzivno funkcijo from k, ki vrne tok števil od k naprej. *)
let nat = let rec from k = Cons (k, fun() -> from (k+1)) in from 0;;
to_list 10 nat ;;
(* - : int list = [0; 1; 2; 3; 4; 5; 6; 7; 8; 9] *)


(* Fibonaccijeva števila
   Sestavite tok fib Fibonaccijevih števil 0, 1, 1, 2, 3, 5, 8, .... Primer: *)

let fib = let rec fib f fprev = Cons(fprev, fun () -> fib (f + fprev) f) in fib 1 0 ;;

to_list 10 fib ;;
(* - : int list = [0; 1; 1; 2; 3; 5; 8; 13; 21; 34] *)


(* Preslikava parov
   Sestavite funkcijo zip : (α → β → γ) → α stream → β stream → γ stream
   ki sprejme funkcijo f in toka
   d₁, d₂, d₃, ...
   e₁, e₂, e₃, ...
   ter vrne tok
   f d₁ e₁, f d₂ e₂, f d₃ e₃, ... *)

(* to_list 10 (zip (fun x -> fun y -> x + y) nat (tail nat)) ;; *)
(* - : int list = [1; 3; 5; 7; 9; 11; 13; 15; 17; 19] *)


(* Večkratniki
    Sestavite funkcijo veckratniki_stevila : int -> int stream, ki sprejme k in vrne tok večkratnikov k. Na primer, veckratniki_stevila 3 vrne tok 0, 3, 6, 9, 12, ....
    Sestavite tok tokov veckratniki : int stream stream, katerega elementi so tokovi večkratnikov:
    (0, 0, 0, 0, ⋯), (0, 1, 2, 3, ⋯), (0, 2, 4, 6, ⋯), (0, 3, 6, 9, ⋯), ⋯ *)

(* to_list 10 (map (to_list 15) veckratniki) ;; *)
(* - : int list list =
   [[0; 0; 0; 0; 0; 0; 0; 0; 0; 0; 0; 0; 0; 0; 0];
   [0; 1; 2; 3; 4; 5; 6; 7; 8; 9; 10; 11; 12; 13; 14];
   [0; 2; 4; 6; 8; 10; 12; 14; 16; 18; 20; 22; 24; 26; 28];
   [0; 3; 6; 9; 12; 15; 18; 21; 24; 27; 30; 33; 36; 39; 42];
   [0; 4; 8; 12; 16; 20; 24; 28; 32; 36; 40; 44; 48; 52; 56];
   [0; 5; 10; 15; 20; 25; 30; 35; 40; 45; 50; 55; 60; 65; 70];
   [0; 6; 12; 18; 24; 30; 36; 42; 48; 54; 60; 66; 72; 78; 84];
   [0; 7; 14; 21; 28; 35; 42; 49; 56; 63; 70; 77; 84; 91; 98];
   [0; 8; 16; 24; 32; 40; 48; 56; 64; 72; 80; 88; 96; 104; 112];
   [0; 9; 18; 27; 36; 45; 54; 63; 72; 81; 90; 99; 108; 117; 126]] *)


(* ★ Sploščen tok
   Ta naloga ni tako dolgočasna kot ostale. Sestavite funkcijo
   flatten : (α stream) stream → α stream
   ki sprejme tok tokov in vrne tok, v katerem se vsak element iz tokov pojavi natanko enkrat. *)


(* ★ Tok parov
   Sestavite funkcijo
   product : α stream → β stream → (α * β) stream
   ki sprejme tokova
   d₁, d₂, d₃, ...
   e₁, e₂, e₃, ...
   in vrne tok parov (dᵢ, eⱼ), v katerem se vsak par pojavi natanko enkrat. Vrstni red, v katerem se pojavijo pari, ni pomemben. Primer: *)

(* to_list 100 (product nat nat) ;; *)
(* - : (int * int) list =
   [(0, 0); (0, 1); (1, 0); (0, 2); (1, 1); (2, 0); (0, 3); (1, 2); (2, 1);
   (3, 0); (0, 4); (1, 3); (2, 2); (3, 1); (4, 0); (0, 5); (1, 4); (2, 3);
   (3, 2); (4, 1); (5, 0); (0, 6); (1, 5); (2, 4); (3, 3); (4, 2); (5, 1);
   (6, 0); (0, 7); (1, 6); (2, 5); (3, 4); (4, 3); (5, 2); (6, 1); (7, 0);
   (0, 8); (1, 7); (2, 6); (3, 5); (4, 4); (5, 3); (6, 2); (7, 1); (8, 0);
   (0, 9); (1, 8); (2, 7); (3, 6); (4, 5); (5, 4); (6, 3); (7, 2); (8, 1);
   (9, 0); (0, 10); (1, 9); (2, 8); (3, 7); (4, 6); (5, 5); (6, 4); (7, 3);
   (8, 2); (9, 1); (10, 0); (0, 11); (1, 10); (2, 9); (3, 8); (4, 7); (5, 6);
   (6, 5); (7, 4); (8, 3); (9, 2); (10, 1); (11, 0); (0, 12); (1, 11); 
   (2, 10); (3, 9); (4, 8); (5, 7); (6, 6); (7, 5); (8, 4); (9, 3); (10, 2);
   (11, 1); (12, 0); (0, 13); (1, 12); (2, 11); (3, 10); (4, 9); (5, 8);
   (6, 7); (7, 6); (8, ...); ...] *)