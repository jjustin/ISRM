(* podatkovni tip za AVL drevesa <3 *)
(* let q = 2 * begin 1 + 3 end;; *)
(* let e = 3 in e + 2;; *)
(* let ((a, b) as t):: e = [(1, 2); (3, 4)];; *)
(* 
# let six = leaf 6 ;;
val six : avltree = Node (6, 1, Empty, Empty)
# let seven = leaf 7 ;;
val seven : avltree = Node (7, 1, Empty, Empty)
# let answer = node (42, six, seven) ;;
val answer : avltree =
   Node (42, 2, Node (6, 1, Empty, Empty), Node (7, 1, Empty, Empty)) *)

type avltree = Empty | Node of int * int * avltree * avltree ;;

let t = Node (5, 3,
              Node (3, 2,
                    Node (1, 1, Empty, Empty),
                    Node (4, 1, Empty, Empty)),
              Node (8, 1, Empty, Empty)) ;;

let height (t:avltree) = match t with
  | Empty -> 0
  | Node (_, h, _, _)-> h
;;


let height' = function
  | Empty -> 0
  | Node (_, h, _, _)-> h
;;

(* # let six = leaf 6 ;;
   val six : avltree = Node (6, 1, Empty, Empty) *)

let leaf: int -> avltree = fun n -> Node(n, 1, Empty, Empty) ;;
let leaf' n = Node(n, 1, Empty, Empty) ;;

(* let max a b = if a>b then a else b;; *)
let node: int * avltree * avltree -> avltree = fun (n, l, r) -> Node(n, 1 + max (height l) (height r) ,l, r) ;;

let node_currying1 = fun n l r -> Node(n, 1 + max (height l) (height r) ,l, r)
;;
let node_currying2 = fun n -> fun l -> fun r -> Node(n, 1 + max (height l) (height r) ,l, r)

let six = leaf 6 ;;
(* val six : avltree = Node (6, 1, Empty, Empty) *)
let seven = leaf 7 ;;
(* val seven : avltree = Node (7, 1, Empty, Empty) *)
let answer = node (42, six, seven) ;;
(* val answer : avltree =
   Node (42, 2, Node (6, 1, Empty, Empty), Node (7, 1, Empty, Empty)) *)
(* 
    5
   / \
  3   8
 / \
1   4 *)

let t' = node (5, (node (3, leaf 1, leaf 4)), leaf 8);;

(* t' = t;; testiranje ali sta t in t' ekvivalentni drevesi *)


let rec toList: avltree -> int list = function 
  | Empty -> []
  | Node (n, _, l, r) -> toList l @ [n] @ (toList r)
;;

(** bi bilo to ok? - Je ok. Skoraj ekvivalentno afna **)
let rec afna' (l, r) = match l with
  | [] -> r
  | a :: b -> a :: (afna' (b, r))
;;

(* toList t';; *)

type order = Less | Equal | Greater ;;

let cmp x y =
  match compare x y with
  | 0 -> Equal
  | r when r < 0 -> Less
  | _ -> Greater
;;

(* search sprejme par *)
let rec search_pair : int * avltree -> bool = function
  | (_, Empty) -> false
  | (x, Node (n, _, l, r)) ->
    match cmp x n with
    | Equal -> true
    | Less -> search_pair (x, l)
    | Greater -> search_pair (x, r)
;;

(* search sprejme 2 argumenta z uporabo currying-a*)
let rec search x = function
  | Empty -> false
  | Node (n, _, l, r) ->
    match cmp x n with
    | Equal -> true
    | Less -> search x l
    | Greater -> search x r
;;

let rec search' x (t : avltree) = match t with
  | Empty -> false
  | Node (n, _, l, r) ->
    match cmp x n with
    | Equal -> true
    | Less -> search x l
    | Greater -> search x r
;;

(* vrne stopnjo neuravnoteženosti drevesa, tj. razliko med višinama levega in desnega poddrevesa *)
let imbalance: avltree -> int = function
  | Empty -> 0
  | Node (_, _, l, r) -> (height l) - (height r)
;;
(* 
     x                      y
   /   \       levo       /   \
  +     y       ==>      x     +
 /a\   / \              / \   /c\
 ---  +   +     <==    +   +  ---
     /b\ /c\   desno  /a\ /b\
     --- ---          --- --- *)

let rotateLeft: avltree -> avltree = function
  | Node (x, _, a, Node (y, _, b, c)) ->  node (y, node (x, a, b), c)
  | tree -> tree

;;
let rotateRight: avltree -> avltree = function
  | Node (y, _, Node (x, _, a, b), c) -> node (x, a, node (y, b, c))  
  | tree -> tree
;;
(* 
         x                       y
       /   \                   /   \
      /     \                 /     \
     y       +               +        x
   /   \    /h\     ==>     / \     /   \
  +     +   ---            /h+1\   +     +
 / \   / \   c             -----  / \   /h\
/h+1\ /h+1\*                 a   /h+1\  ---
----- -----                      -----   c
  a     b (lahko ima višino h)     b
*)

(* 
        x                       x                       z
      /   \                   /   \                   /   \
     /     \                 /     \                 /     \
    y       +               z       +               y       x
  /   \    /h\    ==>     /   \     /h\    ==>     / \     / \
 +     z   ---           y     +    ---           +   +   +   +
/h\   / \   c           / \   /h\    c           /h\ /h\ /h\ /h\
---  +   +             +   +  ---                --- --- --- ---
 a  /h\ /h\           /h\ /h\  b''                a   b'  b'' c
    --- ---           --- ---
     b'  b''           a   b'

(b' ali b'' lahko ima višino h-1)
*)

let isNode = function 
  | Node _ -> true
  | Empty -> false
;;
(* 
let leftSubTree (Node (_,_,l,_)) = l ;;
let rightSubTree (Node (_,_,_,r)) = r ;;
let rootValue (Node (n,_,_,_)) = n ;; *)

(* tako se ne programira v ocaml-u *)
let rec balance' q: avltree =
  let leftSubTree (Node (_,_,l,_)) = l
  and rightSubTree (Node (_,_,_,r)) = r
  and rootValue (Node (n,_,_,_)) = n in
  match imbalance q with
  | (0 | 1 | -1) -> q
  | 2 -> (match imbalance (leftSubTree q) with
      | (0 | 1) -> rotateRight q
      | -1 -> rotateRight (node (rootValue q, rotateLeft (leftSubTree q), rightSubTree q)) 
      | _ -> failwith "invalid imbalace")
  | -2 -> (match imbalance (rightSubTree q) with
      | (0 | -1) -> rotateLeft q
      | 1 -> rotateLeft (node (rootValue q, leftSubTree q, rotateRight (rightSubTree q))) 
      | _ -> failwith "invalid imbalace")
  | _ -> failwith "imbalace > 2"
;;

let balance = function
  | Empty -> Empty
  | Node (x, _, l, r) as tree ->
    match (imbalance tree, imbalance l, imbalance r) with
    | (2, -1, _) -> rotateRight (node (x, rotateLeft l, r))
    | (2, _, _) -> rotateRight tree
    | (-2, 1, _) -> rotateLeft (node (x, l, rotateRight r))
    | (-2, _, _) -> rotateLeft tree
    | _ -> tree
;;