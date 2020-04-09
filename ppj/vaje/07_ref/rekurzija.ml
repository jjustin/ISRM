let r = ref 5 ;;
(* val r : int ref = {contents = 5} *)
!r ;;
(* - : int = 5 *)
!r + 10 ;;
(* - : int = 15 *)

r := 8 ;;
(* - : unit = () *)
!r ;;
(* - : int = 8 *)
!r + 10 ;;
(* - : int = 18 *)

(* 
while ⟨pogoj⟩ do
    ⟨izraz⟩
done

for i = ⟨spodnja-meja⟩ to ⟨zgornja-meja⟩ do
    ⟨izraz⟩
done
*)

let vsota_lihih_42 =
  let v = ref 0 in
  let i = ref 0 in
  while !i < 42 do
    v := !v + (2 * !i + 1) ;
    i := !i + 1
  done ;
  !v


(* Naloga 1
   Sestavite funkcijo vsota1 : int -> int, ki sprejme n in vrne vsoto 1 + 2 + ⋯ + n. Uporabite reference in zanko while. *)
let vsota1 n =
  let sum = ref 0 in
  let i = ref 1 in
  while !i <= n do
    sum := !sum + !i ;
    i := !i +1
  done ;
  !sum
;;

vsota1 100 ;;
(* - : int = 5050 *)


(* Naloga 2
   Sestavite funkcijo fibonacci1 : int -> int, ki sprejme n in vrne n-to Fibonaccijevo število F(n). Nauk: Fibonaccijevo zaporedje je definirano s predpisom:
   F(0) = 0
   F(1) = 1
   F(n) = F(n-1) + F(n-2) *)

let fibonacci1 n =
  let fib = ref 1 in
  let fib_prev = ref 0 in
  let i = ref 1 in
  while !i <= n do
    let temp = !fib in
    fib := !fib + !fib_prev ;
    fib_prev := temp ;
    i := !i + 1
  done ;
  !fib_prev
;;


fibonacci1 0 ;;
fibonacci1 1 ;;
fibonacci1 10 ;;
(* - : int = 55 *)


(* Naloga 3
   Sestavite funkcijo vsota2 : int -> int, ki sprejme n in vrne vsoto 1 + 2 + ⋯ + n. Funkcija naj bo rekurzivna in naj ne uporablja zank in referenc. *)
let rec vsota2 n =
  if n = 0
  then 0
  else n + vsota2 (n-1);;
vsota2 100 ;;


(* Naloga 4
   Sestavite funkcijo fibonacci2 : int -> int, ki sprejme n in vrne n-to Fibonaccijevo število F(n). Funkcija naj bo rekurzivna in naj ne uporablja zank in referenc. *)
let rec fibonacci2 n =
  if n <= 1
  then n
  else fibonacci2 (n-1) + fibonacci2 (n-2);;

fibonacci2 10 ;;

(* v is accumulator *)
let vsota_lihih2 n =
  let rec vsota v i =
    if i < n then
      vsota (v + (2 * i + 1)) (i + 1)
    else
      v
  in
  vsota 0 0

(* Naloga 5
   Po zgornjem receptu predelajte funkcijo vsota1 v funkcijo vsota3, ki uporablja akumulatorje in repno rekurzijo. Nato primerajte delovanje funkcij vsota1, vsota2 in vsota3. Ali lahko vse tri izračunajo npr. vsoto prvih 1000000 števil? *)
let vsota3 n =
  let rec vsota v i =
    if i <= n then
      vsota (v + i) (i + 1)
    else
      v
  in
  vsota 0 0
;;
vsota3 100 ;;

let time f =
  let t = Unix.gettimeofday () in
  let res = f () in
  Printf.printf "Execution time: %f seconds\n"
    (Unix.gettimeofday () -. t);
  res
;;

(* 1000000 -> 100000 *)
let vsota1_runtime = time (fun () -> vsota1 100000) ;;
let vsota2_runtime = time (fun () -> vsota2 100000) ;;
let vsota3_runtime = time (fun () -> vsota3 100000) ;;


(* Naloga 6
   Po zgornjem receptu predelajte funkcijo fibonacci1 v funkcijo fibonacci3, ki uporablja akumulatorje in repno rekurzijo. *)

let fibonacci3 n =
  let rec fibonacci f f_prev i =
    if i <= n then
      fibonacci (f+f_prev) f (i + 1)
    else
      f_prev
  in
  fibonacci 1 0 1
;;
vsota3 100 ;;

fibonacci3 0 ;;
fibonacci3 1 ;;
fibonacci3 10 ;;

(* Splošna pretvorba zanke while v rekurzivno funkcijo
   Premislimo še, ali lahko zanko while v splošnem prevedemo v rekurzivno funkcijo z akumulatorjem in repnim klicom.
   Obravnavajmo zanko while oblike (zapisali smo jo v namišljenem ukaznem programskem jeziku):

   s := s₀
   while p(s) do
   s := f(s)
   done ;
   return r(s) *)


let zanka s0 p f r =
  let rec loop s =
    if p s then loop (f s) else r s
  in
  loop s0
;;

(* Naloga 7
   Sestavite funkcijo vsota4, ki izračuna vsoto števil 1 + 2 + ⋯ + n, tako da uporabite funkcijo zanka. Torej, vaša rešitev mora biti oblike *)
let vsota4 n =
  zanka (0,0) 
    (fun (i,_) -> i<=n)
    (fun (i, sum) -> (i+1, sum + i))
    (fun (_, sum) -> sum) ;;
vsota4 100 ;;
 

(* Naloga 8
   Sestavite funkcijo fibonacci4, ki izračuna n-to Fibonaccijevo, tako da uporabite funkcijo zanka. Torej, vaša rešitev mora biti oblike *)

(* fibonacci4 10 ;; *)


(* Naloga 9
   Sestavite rekurzivno funkcijo forzanka, ki izračuna to, kar izračuna spodnja koda v namišljenem ukaznem programskem jeziku. Pri tem ne uporabljajte zank ali referenc. *)


(* Naloga 10
   Sestavite funkcijo fibonacci5, ki izračuna n-to Fibonaccijevo, tako da uporabite funkcijo forzanka. Torej, vaša rešitev mora biti oblike *)

(* fibonacci5 10 ;; *)