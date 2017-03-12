program exProcedure;
var
   a, x c,  min: integer;
procedure x(x, y, z: integer; var m: integer);
(* Finds the minimum of the 3 values *)

begin
   if x < y then
      m:= x
   else
      m:= y;

   if z < m then
      m:= z;
end; { end of procedure findMin }

begin
   writeln(' Enter three numbers: ');
   readln( a, b, c);
   findMin(a, b, c, min); (* Procedure call *)

   writeln(' Minimum: ', min);
end.
