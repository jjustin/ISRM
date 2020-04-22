module AVLSet(M : ORDERED) : SET with type element = M.t =
  struct
    type element = M.t
    let cmp = M.cmp

    type set = Empty | Node of element * int * set * set

    let empty = Empty

    let height = function
      | Empty -> 0
      | Node (_, h, _, _) -> h

    let leaf v = Node (v, 1, Empty, Empty)

    let node (v, l, r) = Node (v, 1 + max (height l) (height r), l, r)

    let rec member x = function
      | Empty -> false
      | Node (y, _, l, r) ->
         begin
           match cmp x y with
           | Equal -> true
           | Less -> member x l
           | Greater -> member x r
         end

    let rec to_list = function
      | Empty -> []
      | Node (x, _, l, r) -> to_list l @ [x] @ to_list r

    let rotateLeft = function
      | Node (x, _, a, Node (y, _, b, c)) -> node (y, node (x, a, b), c)
      | t -> t

    (* alternativni zapis s case *)
    let rotateRight = function
      | Node (y, _, Node (x, _, a, b), c) -> node (x, a, node (y, b, c))
      | t -> t

    let imbalance = function
      | Empty -> 0
      | Node (_, _, l, r) -> height l - height r

    let balance = function
      | Empty -> Empty
      | Node (x, _, l, r) as t ->
         begin
           match imbalance t with
           | 2 ->
              (match imbalance l with
               | -1 -> rotateRight (node (x, rotateLeft l, r))
               | _ -> rotateRight t)
           | -2 ->
              (match imbalance r with
               | 1 -> rotateLeft (node (x, l, rotateRight r))
               | _ -> rotateLeft t)
           | _ -> t
         end

    let rec add x = function
      | Empty -> leaf x
      | Node (y, _, l, r) as t ->
         begin
           match cmp x y with
           | Equal -> t
           | Less -> balance (node (y, add x l, r))
           | Greater -> balance (node (y, l, add x r))
         end

    let rec remove x = function
      | Empty -> Empty
      | Node (y, _, l, r) ->
         let rec removeSuccessor = function
           | Empty -> assert false
           | Node (x, _, Empty, r) -> (r, x)
           | Node (x, _, l, r) ->
              let (l', y) = removeSuccessor l in
              (balance (node (x, l', r)), y)
         in
         match cmp x y with
         | Less -> balance (node (y, remove x l, r))
         | Greater -> balance (node (y, l, remove x r))
         | Equal ->
            begin match (l, r) with
            | (_, Empty) -> l
            | (Empty, _) -> r
            | _ ->
               let (r', y') = removeSuccessor r in
               balance (node (y', l, r'))
            end

    let rec fold f x = function
      | Empty -> x
      | Node (y, _, l, r) -> fold f (f (fold f x l) y) r

  end
