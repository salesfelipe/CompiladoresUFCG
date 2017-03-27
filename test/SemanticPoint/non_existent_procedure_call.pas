program exProcedure;
var
   a, b, c,  min: integer;
procedure findMax(x, y, z: integer; var m: integer);
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
  findMin(a, b, c, min); (* Procedure call *)

end.
