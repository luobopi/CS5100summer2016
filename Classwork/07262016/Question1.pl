/*
 * (a) facts and rules
 */

/*---------------------facts-------------------*/
f(54,53).
f(53,52).
m(53,54).
m(53,68).
m(54,56).
f(56,57).
f(57,58).
f(57,59).
m(59,60).
m(62,83).
m(59,68).
f(83,65).
f(65,68).
m(62,83).
m(68,66).
m(66,69).
f(76,69).
m(64,69).
m(64,63).
m(63,62).
m(60,68).

/*------------------rules---------------------*/
pathF(A,B,V) :-       
  (f(A,X);m(A,X);f(X,A);m(X,A)),
  not(member(X,V)), 
  (B = X; pathF(X,B,[A|V])). 

pathM(A,B,V) :-       
  (m(A,X);m(X,A)),
  not(member(X,V)), 
  (B = X; pathM(X,B,[A|V])).

footpath(X, Y):- f(X,Y);f(Y, X); pathF(X, Y, []).
motorway(X, Y):- m(X,Y);m(Y, X); pathM(X, Y, []).

route(A,B) :-
  footpath(A, B); motorway(A, B).



/**
* (b) queries:
*
* ?- route(62, 64).
*
* ?- route(53, 52).
*
* ?- route(54, 56).
*
* ?- route(59, 60).
*
* ?- footpath(56,66).
* 
* ?- motorway(57,58).
*/



/**
 * (C) prolog clauses.
 * Is there a motorway that connects hall 58 to hall 59?
 * ?- motorway(58,59).
 * 
 * Is there a route that connects hall 66 to hall 83?
 * ?- route(66, 83).
 * 
 * There is a footpath from hall 60 to hall 59.
 * footpath(60, 59).
 *
 * There is a motorway from hall 62 to hall 63.
 * motorway(62, 63).
 * 
 * The route contains footpath and motorway.
 * route(A,B) :- footpath(A, B); motorway(A, B).
 *
 */
