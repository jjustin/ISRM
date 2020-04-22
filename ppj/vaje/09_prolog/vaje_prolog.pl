% ext install VSC-Prolog
% https://codeq.si/?s=prolog

% kvantificirane spremenljivke pisemo z velikimi črkami
% konstante, predikate in funkcije pišemo z malimi črkami
% na koncu vsake formule zapišemo piko !!!

% _     anonimna spremenljivka (singeltoni)
% %     komentar
% /*    tudi komentar
% /* */   blocni komentar 
% :-    implikacija v levo (<=)
% ;     disjunkcija
% ,     konjunkcija
% =     zdruzevanje
% ==    enakost
% \==   neenakost (pazi) (dif/2 je prijazen do uporabnika)
% \+    negacija  (pazi !!)
% ( )
% aritmetika
    % >/2
    % </2
    % =</2
    % >=/2
    % =\=/2
    % =:=/2
is_two_or_three(2). 
is_two_or_three(3). 
% to sta enaki definiciji
is_two_or_three(L) :-
    (   L=2
    ;   L=3
    ).

not_two(L) :-
    L\=2.

not_two_or_three(L) :-
    \+ is_two_or_three(L).



% Datoteko famrel.pl v prolog naložimo z ukazom
    % le ta definira dejstva v obliki parent/2, male/1 in female/1.
:- ['famrel.pl'].


% Definirajte predikat mother(X, Y), ki velja, ko je X mama od Y.
    % ?- mother(tina, william).
    % true.
    % ?- mother(nevia, Y).
    % Y = luana ;
    % Y = daniela.
mother(X, Y) :-
    female(X),
    parent(X, Y).

% Definirajte predikat grandparent(X, Y), ki velja, ko je X stari starš od Y.
    % ?- grandparent(tina, vanessa).
    %   true.
    % ?- grandparent(tina, Y).
    %   Y = vanessa ;
    %   Y = patricia.
grandparent(X, Y) :-
    parent(X, Z),
    parent(Z, Y).
grandmother(X, Y) :-
    mother(X, Z),
    parent(Z, Y).

% Definirajte predikat sister(X, Y), ki velja, ko je X sestra od Y.
    % ?- sister(vanessa, Y).
    %     Y = patricia.
sister(X, Y) :-
    parent(Z, X),
    parent(Z, Y),
    female(X),
    dif(X, Y).

% Definirajte predikat aunt(X, Y), ki velja, ko je X teta od Y.
    % ?- aunt(sally, Y).
    %   Y = vanessa ;
    %   Y = patricia.
aunt(X, Y) :-
    parent(Z, Y),
    sister(X, Z).

% Definirajte predikat ancestor(X, Y), ki velja, ko je X prednik (starš / stari starš / …) od Y.
    % ?- ancestor(patricia, Y).
    %   Y = john ;
    %   Y = michael ;
    %   Y = michelle.
ancestor(X, Y) :-
    (   parent(X, Y)
    ;   parent(Z, Y),
        ancestor(X, Z)
    ).

% Definirajte predikat descendant(X, Y), ki velja, ko je X potomec (otrok / vnuk / …) od Y.
    % ?- descendant(patricia, Y).
    %   Y = william ;
    %   Y = tina ;
    %   Y = thomas.
descendant(X, Y) :-
    (   parent(Y, X)
    ;   parent(Y, Z),
        descendant(X, Z)
    ).

descendant2(X, Y) :-
    ancestor(Y, X).

% Definirajte predikat ancestor(X, Y, L), ki deluje enako kot ancestor/2, le da vrne še seznam oseb na poti od X do Y.
    % ?- ancestor(thomas, vanessa, L).
    %   L = [thomas, william, vanessa].
    % ?- ancestor(thomas, _, L).
    %   L = [thomas, william] ;
    %   L = [thomas, sally] ;
    %   L = [thomas, jeffrey] ;
    %   L = [thomas, william, vanessa] ;
    %   L = [thomas, william, patricia] ;
    %   L = [thomas, william, vanessa, susan] ;
    %   L = [thomas, william, patricia, john] ;
    %   L = [thomas, william, patricia, john, michael] ;
    %   L = [thomas, william, patricia, john, michelle] ;
    %   L = [thomas, sally, andrew] ;
    %   L = [thomas, sally, melanie] ;
    %   L = [thomas, sally, andrew, joanne] ;
    %   L = [thomas, sally, andrew, joanne, steve] ;
head_tail([Glava|Rep], Glava, Rep).  % preveri, ce je seznam tak kot glava in rep skupaj
ancestor(X, Y, L) :-
    (   parent(X, Y),
        head_tail(L, X, [Y])
    ; % L = [X, Y]
   parent(Z, Y),
        ancestor(X, Z, L2),
        append(L2, [Y], L)
    ).

% asistentova resitev:
% ancestor(Y, X, [Y, X]) :-
%     parent(Y, X).
% ancestor(X, Y, [X|L]) :-
%     parent(X, OtorkOdX),
%     ancestor(OtorkOdX, Y, L).


% Definirajte predikat my_member(X, L), ki velja, ko je X element seznama L.
    % ?- my_member(X, [1,2,3]).
    % X = 1 ;
    % X = 2 ;
    % X = 3.
    % ?- my_member(1, [3,2,X]).
    % X = 1.

% Dagarin resitev: my_member(X, L):-L=[Y|R], (Y=X;my_member(X,R)). 

my_member(X, [X | _]).
my_member(X, [_ | Y]) :- my_member(X,Y).

% Definirajte predikat insert(X, L1, L2), ki velja, ko seznam L2 dobimo tako, da v L1 vstavimo X na poljubno mesto.
    % ?- insert(1, [2,3], L).
    % L = [1,2,3] ;
    % L = [2,1,3] ;
    % L = [2,3,1].
    
insert(X, [], [X]).
% insert(X, [Y], [X, Y]).
% insert(X, [Y], [Y, X]).
insert(X, [Glava|Rep], L2) :-
    (   L2=[X, Glava|Rep] % X dodnamo na zacetek
    ;   insert(X, Rep, RepZX), % X dodamo na poljubno mesto, ki ni prvo
        L2=[Glava|RepZX]
    ).


% Definirajte predikat reverse(A, B), ki velja, ko vsebuje seznam B elemente seznama A v obratnem vrstnem redu.
% ?- reverse([1,2,3], X).
% X = [3,2,1].
% ?- reverse([], []).
% true.
reverse([], []).

reverse([GlavaA|RepA], B_) :-
    reverse(RepA, B),
    append(B, [GlavaA], B_).

% append/3 (glej predavanja join)
my_append([], B, B).

my_append([G|A], B, C) :-  my_append(A,B,X), C=[G|X].
