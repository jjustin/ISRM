(* Pomožni tip, funkcija in signatura za lepše primerjanje. *)
type order = Less | Equal | Greater

let ocaml_cmp x y =
  let c = Pervasives.compare x y in
  if c < 0 then Less
  else if c > 0 then Greater
  else Equal

module type ORDERED =
  sig
    type t
    val cmp : t -> t -> order
  end

(* Specifikacija podatkovnega tipa množica. *)
module type SET =
  sig
    type element
    val cmp : element -> element -> order
    type set
    val empty : set
    val member : element -> set -> bool
    val add : element -> set -> set
    val remove : element -> set -> set
    val to_list : set -> element list
  end
