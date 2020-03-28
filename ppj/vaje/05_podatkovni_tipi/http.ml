type status = { version : string; code : int } ;;
let mystatus = { version = "HTTP/1.1"; code = 200 } ;;

let string_of_status s =
  s.version ^ " " ^
  string_of_int s.code ^ " " ^
  (match s.code with
   | 101 -> "Switching Protocols"
   | 200 -> "OK"
   | 301 -> "Moved Permanently"
   | 404 -> "Not Found"
   | 500 -> "Internal Server Error"
   | _ -> "");;

type transferEncoding = Chunked | Compress | Deflate | Gzip | Identity;;
let string_of_transferEncoding (te:transferEncoding) : string =
    match te with
    | Chunked ->"chunked"
    | Compress -> "compress"
    | Deflate  -> "deflate"
    | Gzip  -> "gzip"
    | Identity  -> "identity" ;;

type date = {dayOfTheWeek: string; dayOfMonth:int; month:string; year:int; hour:int; minute:int; second:int; timezone:string};;
let string_of_date {dayOfTheWeek=dw; dayOfMonth=dm; month=m; year=y; hour=h; minute=min; second=s; timezone=t} =
    dw ^ ", " ^
    string_of_int dm ^" "^
    m ^" "^
    string_of_int y ^" "^
    string_of_int h ^":"^
    string_of_int min ^":"^
    string_of_int s ^" "^
    t;;

type field =
    | Server of string
    | ContentLength of int
    | ContentType of string
    | TransferEncoding of transferEncoding list 
    | Date of date
    ;;


type response = {status: status; headers: field list; body: string}

(** povezemo vse skupaj **)
let r = {
    status={version="HTTP/1.1"; code=200};
    headers=[
        Server "nginx/1.6.2"; 
        ContentLength 13; 
        ContentType "application/json"; 
        TransferEncoding [Gzip; Identity];
        Date {dayOfTheWeek="Wed"; dayOfMonth=3; month="Mar"; year=2020; hour=14; minute=10; second=30; timezone="GMT"}
    ];
    body="hello world!\n"; 
};;

let string_of_field (f:field) : string = 
    match f with 
    | Server f -> "Server: " ^ f
    | ContentLength f -> "Content-Length: " ^ (string_of_int f) 
    | ContentType f -> "Content-Type: " ^ f  
    | TransferEncoding f -> "Transfer-Encoding: " ^ String.concat ", " (List.map string_of_transferEncoding f) 
    | Date d -> "Date: " ^ (string_of_date d) 
    ;;
let string_of_response (resp:response) =
    string_of_status resp.status ^ "\n" ^
    String.concat "\n" (List.map string_of_field r.headers) ^ "\n\n" ^
    r.body ;;

let print_response (resp:response) =
    print_string(string_of_response resp);;