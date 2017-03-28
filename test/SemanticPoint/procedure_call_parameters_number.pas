program exProcedure;
var
   a, b, c,  min: integer;
procedure findMax(x, y, z: integer);
(* Finds the minimum of the 3 values *)
var m : integer;
begin
   if x < y then
      m:= x
   else
      m:= y;

   if z < m then
      m:= z;
end; { end of procedure findMin }

begin
  findMax(a, b);  { Inexistent Procedure call }

end.
